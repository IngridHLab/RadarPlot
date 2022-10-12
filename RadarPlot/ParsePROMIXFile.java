/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RadarPlot;

import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author Sunnyveerla
 */
public class ParsePROMIXFile {

    public Vector<RadarTerm> parsePROMIXFile(String inFilePath) {
        UtilInterface util = new UtilInterface();
        String columns[] = {"Normal", "LumA", "LumB", "HER2", "Basal"};
        int colLen = columns.length;
        String corrLabels[] = {"BL", "T2", "OP"};
        int corrLabelsLen = corrLabels.length;
        String[] annColumnLabels = {"Sample ID", "diseasestatus", "metastaticdisease", "PCR_yes_no_EoT_IJ", "Axill.nodes.verif", "Regional.nodes.BL", "IHC_sub_BL_ki67_20_IJ"};
        int[] annColumnNum = util.getColumnDataByColumnNames(annColumnLabels, inFilePath);
        int dLen = annColumnLabels.length;

        Hashtable<String, int[]> ht = new Hashtable<String, int[]>(3);
        for (int i = 0; i < corrLabelsLen; i++) {
            String tmp = "";
            for (int j = 0; j < colLen; j++) {
                if (tmp.equals("")) {
                    tmp += columns[j] + "." + corrLabels[i];
                } else {
                    tmp += "," + columns[j] + "." + corrLabels[i];
                }
            }
            //System.out.println(tmp);
            String sp[] = tmp.split(",");
            ht.put(corrLabels[i], util.getColumnDataByColumnNames(sp, inFilePath));
        }

        String fdata[] = util.getDataFromFile(inFilePath, true);
        int fLen = fdata.length;
        Vector<RadarTerm> vRT = new Vector<RadarTerm>(fLen);

        for (int i = 0; i < fLen; i++) {
            double data[][] = new double[corrLabelsLen][colLen];
            String sp[] = fdata[i].split("\t");
            for (int j = 0; j < corrLabelsLen; j++) {
                //System.out.println(corrLabels[j]);
                int[] corrColumns = ht.get(corrLabels[j]);
                for (int x = 0; x < colLen; x++) {
                    if (sp[corrColumns[x]].equals("NA")) {
                        data[j][x] = Double.NaN;
                        //System.out.print(data[j][x] + "\t");
                    } else {
                        data[j][x] = Double.parseDouble(sp[corrColumns[x]]);
                        //System.out.print(data[j][x] + "\t");
                    }
                }
                System.out.println();
            }
            String[] annValues = new String[dLen];
            for (int d = 0; d < dLen; d++) {
                annValues[d] = sp[annColumnNum[d]];
            }
            /*double data[][];
             String radarLabels[];
             String valueLabels[];
             String annValues[];*/
            RadarTerm rt = new RadarTerm(data, columns, corrLabels, annColumnLabels, annValues);
            vRT.add(rt);
        }
        return vRT;
    }
}
