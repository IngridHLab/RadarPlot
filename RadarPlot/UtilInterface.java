package RadarPlot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.channels.FileChannel;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTable;

public class UtilInterface {

    FileReader fr;
    BufferedReader br;
    String line = null;
    int len;
    int rowCount = 0;
    int colCount = 0;

    public String getQueryString(String input, String fileType) {
        String data = null;

        len = 0;
        if (fileType.equals("file")) {
            try {
                fr = new FileReader(input);
                br = new BufferedReader(fr);
                while ((line = br.readLine()) != null) {
                    //System.out.println(line);
                    if (line.equals("")) {
                        continue;
                    }
                    if (data == null) {
                        data = "'" + line + "'";
                    } else {
                        data += ",'" + line + "'";
                    }
                    len++;
                }
            } catch (Exception e) {
                System.out.println("Error occured while parsing input file" + e);
            }
            //               System.out.println("Its Here getQueryString");
        } else if (fileType.equals("str")) {
            StringTokenizer st = new StringTokenizer(input, ",");
            while (st.hasMoreElements()) {
                //System.out.println(st.nextElement());

                if (line.equals("")) {
                    continue;
                }
                if (data == null) {
                    data = "'" + st.nextToken() + "'";
                } else {
                    data += ",'" + st.nextToken() + "'";
                }
                len++;
            }
        }
        //System.out.println(input);
        return data;
    }

    public String getCommaSeparatedString(String input, String fileType) {
        String data = "";
        len = 0;
        if (fileType.equals("file")) {
            try {
                fr = new FileReader(input);
                br = new BufferedReader(fr);
                while ((line = br.readLine()) != null) {
                    //System.out.println(line);
                    data += line + ",";
                    len++;
                }
            } catch (Exception e) {
                System.out.println("Error occured while parsing input file" + e);
            }
            //               System.out.println("Its Here getQueryString");
        } else if (fileType.equals("str")) {
            StringTokenizer st = new StringTokenizer(input, ",");
            while (st.hasMoreElements()) {
                //System.out.println(st.nextElement());
                data += st.nextToken() + ",";
                len++;
            }
        }
        //System.out.println(input);
        return data.substring(0, data.length() - 1);
    }

    public String getFileInStringFormat(String infile) {
        String data = "";
        try {
            fr = new FileReader(infile);
            br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                //System.out.println(line);
                data += line + "\n";
            }
        } catch (Exception e) {
            System.out.println("Error occured while parsing input file" + e);
        }

        return data;
    }

    public void getStringInFileFormat(String data, String file) {
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(file));
            String sp[] = data.split(",");
            for (int i = 0; i < sp.length; i++) {
                bw.write(sp[i] + "\n");
                bw.flush();
            }
            bw.close();
        } catch (Exception e) {
            System.out.println("Error occured while parsing data string" + e);
        }
    }

    public int getLength() {
        return this.len;
    }

    public String getQueryString(String input) {
        return getQueryString(input, "file");
    }

    public String getReverseStream(String ups) {
        //System.out.println(ups);
        StringBuffer sb = new StringBuffer(ups);
        ups = sb.reverse().toString();
        return ups;
    }

    public String getComplementStream(String ups) {
        char[] c = new char[ups.length()];
        ups.getChars(0, ups.length(), c, 0);
        String cups = "";
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 'A') {
                c[i] = 'T';
            } else if (c[i] == 'G') {
                c[i] = 'C';
            } else if (c[i] == 'T') {
                c[i] = 'A';
            } else if (c[i] == 'C') {
                c[i] = 'G';
            }
            cups += c[i];
        }
        return cups;
    }

    public int lineCount(String file) {
        int count = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = br.readLine()) != null) {
                if (count == 0) {
                    this.colCount = line.split("\t").length;
                }
                count++;
            }
            this.rowCount = count;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int rowCount() {
        return this.rowCount;
    }

    public int colCount() {
        return this.colCount;

    }

    public String[] getLine(String file, int arrLen, int ln) {
        String tmp[] = new String[arrLen];
        int count = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = br.readLine()) != null && !line.equals("")) {
                String ts[] = line.split("\t");
                tmp[count++] = ts[ln];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tmp;
    }

    public String getLine(String file, String text) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = br.readLine()) != null && !line.equals("")) {
                if (line.indexOf(text) != -1) {
                    return line;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getHeader(String infile) {
        String line = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(infile));
            int count = 0;
            while ((line = br.readLine()) != null && !line.equals("")) {
                if (count == 0) {
                    break;
                }
                count++;
            }

            br.close();
            br = null;
            System.gc();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    public String[] getRows(String infile, int[] row) {
        String[] cell = new String[row.length];
        int count = 0;
        int fl = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(infile));
            String line = null;
            while ((line = br.readLine()) != null) {
                for (int i = 0; i < row.length; i++) {
                    // System.out.println(line);
                    if (count == row[i]) {
                        cell[i] = line;
                        //System.out.println(line+"**********util"+count);
                        fl++;
                    }
                }
                count++;
                if (fl == row.length) {
                    break;
                }

            }

            br.close();
            br = null;
            System.gc();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("Getting cell data");
        return cell;
    }

    public String[] getColumnData(String[] data, int arrLen, int cn) {
        String tmp[] = new String[arrLen];
        for (int i = 0; i < arrLen && !data[i].equals(""); i++) {
            String[] sp = data[i].split("\t");
            tmp[i] = sp[cn];
        }
        return tmp;
    }

    public String[] getColumnDataByCmd(String[] data, int retrieveCol, String cmd, int cmdCol) {
        Vector v = new Vector();
        int arrLen = data.length;
        //String tmp[] = new String[arrLen];
        for (int i = 0; i < arrLen && !data[i].equals(""); i++) {
            String[] sp = data[i].split("\t");
            if (sp[cmdCol].equals(cmd)) {
                v.add(sp[retrieveCol]);
            }

        }
        return convertObjectArrayToStringArray(v.toArray());
    }

    public String[] convertObjectArrayToStringArray(Object[] objs) {
        int olen = objs.length;
        String tmp[] = new String[olen];
        for (int i = 0; i < olen; i++) {
            tmp[i] = objs[i].toString();
        }
        return tmp;
    }

    public void transformFile(String filename, String outfile) {
        String cdata[][] = transformFile(filename);
        int lc = cdata[0].length;
        int cc = cdata.length;
        //System.out.println(filename);
        //System.out.println(cc);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(outfile));
            for (int i = 0; i < cc; i++) {
                for (int j = 0; j < lc; j++) {
                    bw.write(cdata[i][j] + "\t");
                }
                bw.write("\n");
                bw.flush();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[][] transformFile(String filename) {
        int lc = lineCount(filename);
        int cc = colCount;
        String cdata[][] = new String[cc][lc];
        System.out.println(cc);
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));

            String line = null;

            int count = 0;
            while ((line = br.readLine()) != null && !line.equals("")) {
                String st[] = line.split("\t");

                for (int k = 0; k < cc; k++) {
                    cdata[k][count] = st[k];
                }
                count++;
                //System.out.println(count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cdata;
    }

    public int lineCountOnCmd(String file, String cmd, int cmdCol) {
        int count = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = br.readLine()) != null && !line.equals("")) {
                if (count == 0) {
                    this.colCount = line.split("\t").length;
                }
                String st[] = line.split("\t");
                if (cmdCol != -1 && st[cmdCol].equals(cmd) || count == 0) {
                    count++;

                } else if (cmdCol == -1 || count == 0) {
                    count++;
                }

            }
            //this.rowCount = count;
            //System.out.println(count);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (count - 1); //-1 because of header
    }

    public String[] getColumnDataFromFile(int col[], String filename) {
        //Vector vt = new Vector();
        boolean flag = false;
        String[] cdata = new String[lineCount(filename)];
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = null;
            String tmp = "";

            int count = 0;
            int scount = 0;
            String ctmp = "";
            if (col.length > 1) {
                ctmp = "\t";
            }
            while ((line = br.readLine()) != null && !line.equals("")) {
                //line.replaceAll("\\t\\t", "\tnull\t");
                String st[] = line.split("\t");
                //System.out.println(line);
                if (st.length - 1 < col[0]) {
                    System.out.println(line);
                    break;
                }
                cdata[count] = "";
                for (int i = 0; i < col.length; i++) {
                    if (st[col[i]].equals("")) {
                        flag = true;
                        cdata[count++] = "";
                        break;
                    }

                    //tmp += st[col[i]]+"\t";
                    cdata[count] += st[col[i]] + ctmp;
                    //System.out.println(cdata[count]);
                }
                // System.out.println(count);
                count++;
                if (flag) {
                    break;
                }
                //                vt.add(tmp);
                //                tmp = "";
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return(vt.toArray());
        return cdata;
    }

    public String[] getColumnDataFromFile(int col[], String filename, boolean withoutHeader) {
        //Vector vt = new Vector();
        boolean flag = false;
        int lc = lineCount(filename);
        if (withoutHeader) {
            lc = lc - 1;
        }
        String[] cdata = new String[lc];
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = null;
            String tmp = "";

            int count = 0;
            int scount = 0;
            String ctmp = "";
            if (col.length > 1) {
                ctmp = "\t";
            }
            while ((line = br.readLine()) != null && !line.equals("")) {
                //line.replaceAll("\\t\\t", "\tnull\t");
                if (withoutHeader) {
                    withoutHeader = false;
                    continue;
                }
                String st[] = line.split("\t");
                //System.out.println(line);
                if (st.length - 1 < col[0]) {
                    System.out.println(line);
                    break;
                }
                cdata[count] = "";
                for (int i = 0; i < col.length; i++) {
                    if (st[col[i]].equals("")) {
                        flag = true;
                        cdata[count++] = "";
                        break;
                    }

                    //tmp += st[col[i]]+"\t";
                    cdata[count] += st[col[i]] + ctmp;
                    //System.out.println(cdata[count]);
                }
                // System.out.println(count);
                count++;
                if (flag) {
                    break;
                }
                //                vt.add(tmp);
                //                tmp = "";
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return(vt.toArray());
        return cdata;
    }

    public String[] getColumnDataFromFile(int col, String filename) {
        int tcol[] = new int[1];
        tcol[0] = col;
        System.out.println("its here");
        return (getColumnDataFromFile(tcol, filename));
    }

    public double[] getColumnDataFromFile(int col, String filename, boolean header) {
        int tcol[] = new int[1];
        tcol[0] = col;
        String data[] = getColumnDataFromFile(tcol, filename);
        int j = 0;
        if (header) {
            j = 1;
        }
        double tdata[] = new double[data.length];

        for (int i = j; i < tdata.length; i++) {
            tdata[i] = Double.parseDouble(data[i].trim());
            //System.out.println(tdata[i]);
        }
        return (tdata);
    }

    public String[] getColumnDataFromFile(int col[], String filename, String cmd, int cmdCol) {
        //Vector vt = new Vector();
        boolean flag = false;
        String[] cdata = new String[lineCountOnCmd(filename, cmd, cmdCol) + 1];
        System.out.println(cdata.length);
        if (cdata.length > 0) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(filename));
                String line = null;
                String tmp = "";

                int count = 0;
                int scount = 0;
                String ctmp = "";
                if (col.length > 1) {
                    ctmp = "\t";
                }
                while ((line = br.readLine()) != null && !line.equals("")) {

                    String st[] = line.split("\t");

                    if (st.length - 1 < col[0]) {
                        System.out.println(line);
                        break;
                    }
                    //System.out.println(count);
                    if (cmdCol != -1 && st[cmdCol].equals(cmd) || count == 0) {
                        cdata[count] = "";
                        for (int i = 0; i < col.length; i++) {
                            cdata[count] += st[col[i]] + ctmp;
                            System.out.println(cdata[count] + "\t" + count);
                            //flag = true;
                        }
                        count++;
                    } else if (cmdCol == -1 || count == 0) {
                        cdata[count] = "";
                        for (int i = 0; i < col.length; i++) {
                            cdata[count] += st[col[i]] + ctmp;
                            //System.out.println(st[col[i]] + "\t" + count);
                            // flag = true;
                        }
                        count++;
                    }
                    if (count > cdata.length) {
                        break;
                    }

                }
                //System.out.println(count);
                br.close();
            } catch (IOException e) {
                System.out.println("Stopped Abruptly ***********");
                System.exit(1);
                e.printStackTrace();
            }
        }
        //return(vt.toArray());
        return cdata;
    }

    public String[] getColumnDataFromFile(int col[], int ln, String filename) {
        return getColumnDataFromFile(col, ln, filename, false);
    }

    public String[] getColumnDataFromFile(int col[], int ln, String filename, boolean removeHeader) {
        //Vector vt = new Vector();
        boolean flag = false;
        String[] cdata = new String[ln];
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = null;

            int count = 0;
            String ctmp = "";
            if (col.length > 1) {
                ctmp = "\t";
            }

            while ((line = br.readLine()) != null && !line.equals("")) {
                if (removeHeader) {
                    removeHeader = false;
                    continue;
                }
                String st[] = line.split("\t");
                cdata[count] = "";
                for (int i = 0; i < col.length; i++) {
                    cdata[count] += st[col[i]] + ctmp;
                }
                System.out.println(cdata[count]);
                count++;
                if (count >= ln) {
                    break;
                }

                //                vt.add(tmp);
                //                tmp = "";
            }
            br.close();
            br = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Get col data");
        return cdata;
    }

    public String[] getDataFromFile(String filename) {
        //Vector vt = new Vector();
        boolean flag = false;
        String[] cdata = new String[lineCount(filename)];
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = null;
            int c = 0;

            while ((line = br.readLine()) != null) {
                line = line.replaceAll("\"", " ").trim();
                //System.out.println(line);
                if (line.equals("")) {
                    cdata[c++] = "---";
                    continue;
                }
                cdata[c++] = line.trim();
            }
            br.close();
            br = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        // System.out.println("Get all col data");
        return cdata;
    }

    public String[] getDataFromFile(String filename, boolean withoutHeader) {
        //Vector vt = new Vector();
        boolean flag = false;
        int lc = lineCount(filename);
        if (withoutHeader) {
            lc = lc - 1;
        }
        if (lc == 0) {
            return null;
        }
        String[] cdata = new String[lc];
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = null;
            int c = 0;
            while ((line = br.readLine()) != null && !line.equals("")) {
                if (c == 0 && withoutHeader) {
                    withoutHeader = false;
                    continue;
                }
                cdata[c++] = line.trim();
            }
            br.close();
            br = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("Get all col data");
        return cdata;
    }

    public double[] getDataFromFileToOneColumn(String filename, boolean headerNcolumn, int dataStart) {
        //Vector vt = new Vector();
        int h = 0;
        if (headerNcolumn) {
            h = 1;
        }
        double[] cdata = new double[((lineCount(filename) - h) * (colCount - dataStart))];
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = null;
            int c = 0;
            //int l = 0;
            while ((line = br.readLine()) != null && !line.equals("")) {
                if (h == 1) {
                    h++;
                    continue;
                }
                String st[] = line.split("\t");
                for (int i = dataStart; i < st.length; i++) {
                    cdata[c++] = Double.parseDouble(st[i].trim());
                    //System.out.println(cdata[c - 1]);
                }
            }
            br.close();
            br = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("Get all col data");
        return cdata;
    }

    public int[][] getColumnIntegerDataFromFile(int col[], String filename) {
        Vector vt = new Vector();
        boolean flag = false;
        int[][] cdata = new int[lineCount(filename)][col.length];
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = null;
            String tmp = "";

            int count = 0;
            int rcount = 0;
            String ctmp = "";
            if (col.length > 1) {
                ctmp = "\t";
            }
            while ((line = br.readLine()) != null && !line.equals("")) {
                String st[] = line.split("\t");

                if (st.length - 1 < col[0]) {
                    System.out.println(line);
                    break;
                }
                //                cdata[count] = "";
                for (int i = 0; i < col.length; i++) {
                    //                    if(st[col[i]].equals("")){
                    //                        flag = true;
                    //                        cdata[count++] = "";
                    //                        break;
                    //                    }
                    //
                    //                    //tmp += st[col[i]]+"\t";

                    cdata[count][i] = Integer.parseInt(st[col[i]]);
                }
                count++;
                if (flag) {
                    break;
                }
                //                vt.add(tmp);
                //                tmp = "";
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return(vt.toArray());
        return cdata;
    }

    public String getColumnDataFromFileAsString(int col[], String filename) {
        Vector vt = new Vector();
        boolean flag = false;
        String cdata = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = null;
            String tmp = "";

            int count = 0;
            String ctmp = "\t";
            if (col.length > 1) {
                ctmp = "\t";
            }
            while ((line = br.readLine()) != null && !line.equals("")) {
                String st[] = line.split("\t");
                //System.out.println(col.length);
                if (st.length - 1 < col[0]) {
                    break;
                }
                //System.out.println(st[col[0]]);
                for (int i = 0; i < col.length; i++) {
                    if (st[col[i]].equals("")) {
                        flag = true;
                        cdata += "\t";
                        break;
                    }

                    //tmp += st[col[i]]+"\t";
                    cdata += st[col[i]] + ctmp;
                }
                if (flag) {
                    break;
                }
                //                vt.add(tmp);
                //                tmp = "";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        //return(vt.toArray());
        return cdata;
    }

    public int[] getColumnNumbersByColumnNames(String[] col, String header) {
        String nc = "";

        String st[] = header.split("\t");
        //System.out.println(line);
        //                System.exit(0);
        for (int j = 0; j < col.length; j++) {
            for (int i = 0; i < st.length; i++) {
                if (col[j].trim().equals(st[i].trim())) {
                    nc += i + "\t";

                    //System.out.println(col[j]+"\t"+j);
                }
            }
        }

        //System.out.println(nc);
        String sp[] = nc.trim().split("\t");
        int ncol[] = new int[sp.length];
        for (int i = 0; i < sp.length && !sp[i].equals(""); i++) {
            ncol[i] = Integer.parseInt(sp[i]);
        }
        return ncol;
    }

    public String replaceDoubleQuotes(String line) {
        return line = line.replaceAll("\"", " ").trim();
    }

    public int[] getColumnDataByColumnNames(String[] col, String filename) {
        boolean flag = false;
        String nc = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = null;
            //String tmp = "";
            while ((line = br.readLine()) != null && !line.equals("")) {
                line = replaceDoubleQuotes(line);
                String st[] = line.split("\t");
                //System.out.println(line);
                //                System.exit(0);
                for (int j = 0; j < col.length; j++) {
                    for (int i = 0; i < st.length; i++) {
                        if (col[j].trim().equals(st[i].trim())) {
                            nc += i + "\t";
                            //System.out.println(col[j]+"\t"+j);
                        }
                    }
                }
                break;
                // System.exit(0);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(nc);
        String sp[] = nc.trim().split("\t");
        int ncol[] = new int[sp.length];
        for (int i = 0; i < sp.length && !sp[i].equals(""); i++) {
            ncol[i] = Integer.parseInt(sp[i]);
        }
        return ncol;
    }

    public String[] getDataFromColumns(String qfilename, String filename) {
        int lc = lineCount(filename);
        int cc = colCount();
        Hashtable ht = new Hashtable();
        String coltitle[] = new String[cc];
        int cn[] = new int[1];
        try {
            BufferedReader br = new BufferedReader(new FileReader(qfilename));
            String line = null;

            while ((line = br.readLine()) != null) {
                ht.put(line.toUpperCase().trim(), line.toUpperCase().trim());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < cc; i++) {
            cn[0] = i;
            String list[] = getColumnDataFromFile(cn, filename, "", -1);
            coltitle[i] = "";
            coltitle[i] += list[0] + "\t";
            //System.out.println(list.length);
            for (int j = 1; j < list.length; j++) {
                if (list[j] != null) {
                    if (ht.contains(list[j].toUpperCase())) {
                        coltitle[i] += list[j] + "\t";
                        //System.out.println(list[j]);
                    }
                }
            }
        }
        return coltitle;
    }

    public int getStringCount(String str, String file) {
        int count = 0;
        int lc = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = br.readLine()) != null && !line.equals("")) {
                lc++;
                if (line.indexOf(str) != -1) {
                    //  System.out.println(line);
                    count++;
                }
            }
            rowCount = lc;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    public String[] getUnique(String d[]) {
        Hashtable ht = new Hashtable();
        for (int i = 0; i < d.length; i++) {
            ht.put(d[i], d[i]);
        }
        Object[] obj = ht.keySet().toArray();
        int olen = obj.length;
        String[] udata = new String[olen];
        for (int j = 0; j < olen; j++) {
            udata[j] = obj[j].toString();
        }
        return udata;
    }

    public Object[] unionFiles(String FolderPath, boolean header) {
        File fd = new File(FolderPath);
        String files[] = fd.list();
        Hashtable ht = new Hashtable();
        int col[] = new int[1];
        col[0] = 0;
        for (int i = 0; i < files.length; i++) {
            System.out.println("Collecting first row information:" + files[i]);
            int lc = 0;
            String srt[] = getColumnDataFromFile(col, FolderPath + files[i]);
            for (int j = 0; j < srt.length; j++) {
                if (header && lc == 0) {
                    lc++;
                    continue;
                }
                //System.out.println(srt[j]);
                ht.put(srt[j].trim(), srt[j].trim());
            }
        }
        return ht.keySet().toArray();
    }

    public void mergeFilesToSingleFile(String inPath, String outFile) {

        //String outFile = inPath + study + "_GeneExpData.txt";
        String flist[] = new File(inPath).list(getFilesWithExtension(inPath, ".txt"));
        String sampleFile = inPath + flist[0];
        String genes[] = getColumnDataFromFile(0, sampleFile);
        int lc = genes.length;
        Hashtable<String, String> hGenes = new Hashtable(lc);
        int count = 0;
        for (String g : genes) {
            hGenes.put(g, "" + (count++));
        }
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(outFile));
            for (String f : flist) {
                System.out.println(f);
                String tmpData[] = getDataFromFile(inPath + f);
                for (String d : tmpData) {
                    String sp[] = d.split("\t");
                    if (hGenes.containsKey(sp[0].trim())) {
                        int rowNum = Integer.parseInt(hGenes.get(sp[0].trim()).toString());
                        genes[rowNum] += "\t" + d.substring(d.indexOf("\t"), d.length()).trim();
                    }
                }
            }
            for (String gex : genes) {
                bw.write(gex + "\n");
                bw.flush();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mergeTwoFilesIntoSingleFile(String file1, String file2, String outFile) {
        String[] file1Data = getDataFromFile(file1);
        Hashtable<String, String> ht = new Hashtable(file1Data.length);
        int h = 0;
        for (String tmp : file1Data) {
            if (h == 0) { //skipping header
                h++;
                continue;
            }
            String[] sp = tmp.split("\t");
            ht.put(sp[0], tmp);
        }

        String file1Header = getHeader(file1);
        String file2Header = getHeader(file2);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file2));
            BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
            bw.write(file1Header + "\t" + file2Header + "\n");
            bw.flush();
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] st = line.split("\t");
                if (ht.containsKey(st[0].trim())) {
                    bw.write(ht.get(st[0].trim()) + "\t" + line + "\n");
                    bw.flush();
                }
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public File onBrowseClick(File defaultDirectory, String title, String cmd, final String fileFormat) {
        JFileChooser jfc = new JFileChooser();
        jfc.setCurrentDirectory(defaultDirectory);

        if (title.indexOf("directory") != -1) {
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        } else {
            jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

            jfc.setFileFilter(new javax.swing.filechooser.FileFilter() {

                public boolean accept(java.io.File f) {
                    return f.isDirectory() || f.getName().toLowerCase().endsWith("." + fileFormat);
                }

                public String getDescription() {
                    return "." + fileFormat + " files";
                }
            });
        }
        int action = -1;
        jfc.setDialogTitle(title);
        if (cmd.equals("Save")) {
            action = jfc.showSaveDialog(new JDialog());
        } else {
            action = jfc.showOpenDialog(new JDialog());
        }
        //jfc.show
        jfc.setSize(500, 500);
        jfc.setVisible(true);
        if (action == jfc.CANCEL_OPTION) {
            return null;
        }
        return (new File(jfc.getSelectedFile().getAbsolutePath()));
    }
    File indefaultDirectory = null;

    public String fileChooser(String cmd, String fileFormat) {
        String filename = null;
        indefaultDirectory = onBrowseClick(indefaultDirectory, "File name", cmd, fileFormat);
        if (indefaultDirectory != null) {
            filename = indefaultDirectory.getAbsolutePath();
        }
        return filename;

    }

    public void writeStringArrayToFile(String data[], String filename) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            for (int i = 0; i < data.length; i++) {
                if (i == 0) {
                    bw.write(data[i]);
                } else {
                    bw.write("\n" + data[i]);
                }
                bw.flush();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void arrangeDataByColumnOrder(int col[], String infile, boolean rowHeader, String outfile) {
        BufferedReader br = null;
        BufferedWriter bw = null;
        String line = null;
        try {
            br = new BufferedReader(new FileReader(infile));
            bw = new BufferedWriter(new FileWriter(outfile));
            while ((line = br.readLine()) != null) {
                String sp[] = line.split("\t");
                if (rowHeader) {
                    bw.write(sp[0] + "\t");
                }
                for (int i = 0; i < col.length; i++) {
                    if (i == 0) {
                        bw.write(sp[col[i]]);
                    } else {
                        bw.write("\t" + sp[col[i]]);
                    }
                }
                bw.write("\n");
                bw.flush();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeStringDoubleArrayToFile(String data[][], String filename) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[0].length; j++) {
                    bw.write(data[i][j] + "\t");
                }
                bw.write("\n");
                bw.flush();
            }

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[][] sort(String[][] stmp, boolean header, int[] col, int qcol, String scmd) {
        int a = 0;
        if (header) {
            header = false;
            a = 1;
        } else {
            header = true;
        }
        int lc = stmp.length;
        int cc = stmp[0].length;
        String dstmp[][] = new String[lc - a][cc];
        if (stmp.length == 1) {
            return stmp;
        }
        //System.out.println(lc+"\t"+cc);
        for (int i = lc - 1; i >= a; i--) {
            for (int j = a; j < i; j++) {
                if (scmd.equals("ASC")) {
                    if (qcol == 0) {
                        if (stmp[j][qcol].compareTo(stmp[j + 1][qcol]) > 0) {
                            for (int k = 0; k < cc; k++) {
                                dstmp[j][k] = stmp[j + 1][k];
                                stmp[j + 1][k] = stmp[j][k];
                                stmp[j][k] = dstmp[j][k];
                                //System.out.println(stmp[j][k]);
                            }
                        }
                    } else if (Float.parseFloat(stmp[j][qcol]) > Float.parseFloat(stmp[j + 1][qcol])) {
                        for (int k = 0; k < cc; k++) {
                            dstmp[j][k] = stmp[j + 1][k];
                            stmp[j + 1][k] = stmp[j][k];
                            stmp[j][k] = dstmp[j][k];
                            //System.out.println(stmp[j][k] + "asdkfjalksdjflasdjfajsdlfkja");
                        }
                    }
                } else {
                    if (qcol == 0) {
                        if (stmp[j][qcol].compareTo(stmp[j + 1][qcol]) < 0) {
                            for (int k = 0; k < cc; k++) {
                                dstmp[j][k] = stmp[j + 1][k];
                                stmp[j + 1][k] = stmp[j][k];
                                stmp[j][k] = dstmp[j][k];
                            }
                        }
                    } else if (Float.parseFloat(stmp[j][qcol]) < Float.parseFloat(stmp[j + 1][qcol])) {
                        for (int k = 0; k < cc; k++) {
                            dstmp[j][k] = stmp[j + 1][k];
                            stmp[j + 1][k] = stmp[j][k];
                            stmp[j][k] = dstmp[j][k];
                            //System.out.println(stmp[j][k]);
                        }
                    }
                }
            }
        }
        return stmp;
    }

    public String[][] sort(String infile, boolean header, int[] col, int qcol, String scmd) {
        String[][] stmp = getColumnStringDataFromFile(col, infile, header);
        return sort(stmp, header, col, qcol, scmd);
    }

    public int[][] getColumnIntegerDataFromFile(int col[], String filename, boolean header, boolean rowTitle) {
        int colLen = col.length;
        int rowLen = lineCount(filename);
        int ls = 0;
        int cs = 0;
        if (header) {
            rowLen = rowLen - 1;
        }

        int[][] cdata = new int[rowLen][colLen];

        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = null;
            int count = 0;
            while ((line = br.readLine()) != null && !line.equals("")) {
                if (header && ls == 0) {
                    ls++;
                    continue;
                }
                String st[] = line.split("\t");

                if (st.length - 1 < col[0]) {
                    break;
                }

                for (int i = 0; i < col.length; i++) {
                    cdata[count][i] = Integer.parseInt(st[col[i]]);
                }
                count++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cdata;
    }

    public double[][] getColumnDoubleDataFromFile(int col[], String filename, boolean header) {
        int colLen = col.length;
        int rowLen = lineCount(filename);
        int ls = 0;
        int cs = 0;
        if (header) {
            rowLen = rowLen - 1;
        }

        double[][] cdata = new double[rowLen][colLen];

        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = null;
            int count = 0;
            while ((line = br.readLine()) != null && !line.equals("")) {
                if (header && ls == 0) {
                    ls++;
                    continue;
                }
                String st[] = line.split("\t");

                if (st.length - 1 < col[0]) {
                    break;
                }

                for (int i = 0; i < col.length; i++) {
                    cdata[count][i] = Double.parseDouble(st[col[i]]);
                }
                count++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cdata;
    }

    public double[][] getColumnDoubleDataFromFile(String filename, boolean header, boolean rowTitle) {
        int rowLen = lineCount(filename);
        int colLen = colCount;

        int i = 0;
        if (rowTitle) {
            i = 1;
        }
        int col[] = new int[colLen - i];
        //System.out.println(colLen);
        for (int j = 0; j < col.length; j++) {
            col[j] = i + j;
        }
        return getColumnDoubleDataFromFile(col, filename, header);
    }

    public float[][] getColumnFloatDataFromFile(int col[], String filename, boolean header) {
        int colLen = col.length;
        int rowLen = lineCount(filename);
        int ls = 0;
        int cs = 0;
        if (header) {
            rowLen = rowLen - 1;
        }

        float[][] cdata = new float[rowLen][colLen];

        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = null;
            int count = 0;
            while ((line = br.readLine()) != null && !line.equals("")) {
                if (header && ls == 0) {
                    ls++;
                    continue;
                }
                String st[] = line.split("\t");

                if (st.length - 1 < col[0]) {
                    break;
                }

                for (int i = 0; i < col.length; i++) {
                    cdata[count][i] = Float.parseFloat(st[col[i]]);
                    //System.out.println(cdata[count][i]);
                }
                count++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cdata;
    }

    public float[][] getColumnFloatDataFromFile(String filename, boolean header, boolean rowTitle, int dataStartCol) {
        int rowLen = lineCount(filename);
        int colLen = colCount;

        int i = 0;
        if (rowTitle) {
            i = dataStartCol;
        }
        int col[] = new int[colLen - i];
        //System.out.println(colLen);
        for (int j = 0; j < col.length; j++) {
            col[j] = i + j;
        }
        return getColumnFloatDataFromFile(col, filename, header);
    }

    public String[][] getColumnStringDataFromFile(int col[], String filename, boolean header) {
        int colLen = col.length;
        int rowLen = lineCount(filename);
        if (header) {
            rowLen = rowLen - 1;
        }
        int ls = 0;
        String[][] cdata = new String[rowLen][colLen];

        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = null;
            int count = 0;
            while ((line = br.readLine()) != null && !line.equals("")) {
                if (header && ls == 0) {
                    ls++;
                    continue;
                }
                String st[] = line.split("\t");

                if (st.length - 1 < col[0]) {
                    break;
                }

                for (int i = 0; i < col.length; i++) {
                    cdata[count][i] = st[col[i]];
                }
                count++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cdata;
    }

    public Vector getUniqueDataFromColumn(int col, String filename) {
        Vector vobj = new Vector();
        int c[] = new int[1];
        c[0] = col;
        String data[] = getAllDataFromColumn(c, filename);
        for (int i = 0; i < data.length; i++) {
            if (!vobj.contains(data[i])) {
                vobj.add(data[i]);
                //System.out.println(data[i]);
            }
        }
        return vobj;
    }

    public String[] getAllDataFromColumn(int col[], String filename) {
        //Vector vt = new Vector();
        //boolean flag = false;
        String[] cdata = new String[lineCount(filename)];
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = null;
            //String tmp = "";
            int count = 0;
            // int scount = 0;
            String ctmp = "";
            if (col.length > 1) {
                ctmp = "\t";
            }
            while ((line = br.readLine()) != null && !line.equals("")) {
                String st[] = line.split("\t");

                cdata[count] = "";
                for (int i = 0; i < col.length; i++) {
                    /* if (st[col[i]].equals("")) {
                     flag = true;
                     cdata[count++] = "";
                     break;
                     }*/
                    //System.out.println(st[col[i]]);
                    //tmp += st[col[i]]+"\t";
                    cdata[count] += st[col[i]].trim() + ctmp;
                }
                count++;
                /* if (flag) {
                 break;
                 }*/
                //                vt.add(tmp);
                //                tmp = "";
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return(vt.toArray());
        return cdata;
    }

    public void getDataByQueryFile(String qFile, int qCol, String dataFile, int dataStart, int dCol, String outputFile, boolean inOrder) {
        writeStringArrayToFile(getDataByQueryList(getColumnDataFromFile(qCol, qFile), dataFile, dataStart, dCol), outputFile);
    }

    public void getDataFromTwoFiles(String qFile, int qCol, String dataFile, int dataStart, int dCol, String outputFile) {
        String qData[] = getDataFromFile(qFile);
        String oData[] = getDataByQueryListByOrder(getColumnDataFromFile(qCol, qFile), dataFile, dataStart, dCol);
        for (int i = 0; i < qData.length; i++) {
            oData[i] = qData[i] + "\t" + oData[i];
        }
        writeStringArrayToFile(oData, outputFile);
    }

    public String[] getDataByQueryList(String[] queries, String[] data, int dataStart, int dCol, int getHeader) {
        //dataStart = dataStart - 1;//Since array starts from zero
        Vector<String> v = new Vector();
        if (getHeader == 1 && dataStart > 0) {
            for (int i = 0; i < dataStart; i++) {
                v.add(data[i]);
            }
        }
        Vector qV = new Vector(queries.length);
        for (String q : queries) {
            qV.add(q.trim());
        }
        for (String d : data) {
            String tmp[] = d.split("\t");
            //System.out.println(tmp[dCol - 1]);
            if (qV.contains(tmp[dCol].trim())) {
                v.add(d);
            }
        }
        return convertObjectArrayToStringArray(v.toArray());
    }

    public String[] getDataByQueryList(String[] queries, String dataFile, int dataStart, int dCol) {
        return (getDataByQueryList(queries, getDataFromFile(dataFile), dataStart, dCol, 1));
    }

    public String[] getDataByQueryListByOrder(String[] queries, String dataFile, int dataStart, int dCol) {
        //dataStart = dataStart - 1;
        int qLen = queries.length;
        Hashtable ht = new Hashtable(qLen);
        String outData[] = new String[qLen + dataStart];
        for (int i = 0; i < qLen; i++) {
            ht.put(queries[i].trim().toUpperCase(), "" + i);
            outData[dataStart + i] = queries[i].trim();
        }

        String line = null;
        int count = 0;
        try {
            br = new BufferedReader(new FileReader(dataFile));
            while ((line = br.readLine()) != null) {
                if (count < dataStart || line.equals("")) {
                    if (dataStart > 0) {
                        outData[count] = line;
                    }
                    count++;
                    continue;
                }
                String ts[] = line.split("\t");
                if (ht.containsKey(ts[dCol].trim().toUpperCase())) {
                    outData[Integer.parseInt(ht.get(ts[dCol].trim().toUpperCase()).toString()) + dataStart] += "\t" + line;
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outData;
    }

    public String[] getDataFromRowOfFile(String dataFile, int dataStart) {
        int lc = 0;
        String line = null;
        BufferedReader br = null;
        String data[] = new String[lineCount(dataFile) - dataStart];
        int i = 0;
        try {
            br = new BufferedReader(new FileReader(dataFile));
            while ((line = br.readLine()) != null) {
                if (lc < dataStart) {
                    lc++;
                    continue;
                }

                data[i++] = line;
                //System.out.println(lc + "\t" + i);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public String[] getRowsBeforeDataStartFromFile(String dataFile, int dataStart) {
        int lc = 0;
        String line = null;
        BufferedReader br = null;
        String data[] = new String[dataStart];
        int i = 0;
        try {
            br = new BufferedReader(new FileReader(dataFile));
            while ((line = br.readLine()) != null) {
                if (++lc < dataStart) {
                    data[i++] = line;
                    continue;
                } else {
                    break;
                }

            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public Vector<String> getColumnDataInVectorByLowerCase(String file, int col) {
        Vector<String> v = new Vector();
        String[] cData = getColumnDataFromFile(col - 1, file);
        for (String s : cData) {
            //System.out.println(s);
            v.add(s.toLowerCase());
        }
        return v;
    }

    public int getColumnDataByColumnNames(String colName, String filename) {
        String st[] = new String[1];
        st[0] = colName;
        int cn[] = getColumnDataByColumnNames(st, filename);
        return cn[0];
    }

    public int[] getColumnDataByColumnNames(String col[], String st[]) {
        String nc = "";
        for (int j = 0; j < col.length; j++) {
            for (int i = 0; i < st.length; i++) {
                // System.out.println(col[j] + "\t" + st[i]);
                if (col[j].trim().equals(st[i].trim())) {
                    nc += i + "\t";
                    //System.out.println(col[j] + "\t" + j);
                }
            }
        }
        String sp[] = nc.trim().split("\t");
        int ncol[] = new int[sp.length];
        for (int i = 0; i < sp.length && !sp[i].equals(""); i++) {
            ncol[i] = Integer.parseInt(sp[i]);
        }
        return ncol;
    }

    // Write a collection using each item's toString() method with a header to a file
    public static void writeFile(String filename, String header, Collection<?> outputCollection) {
        FileWriter fw = null;
        try {
            File file = new File(filename);
            fw = new FileWriter(file);
            if (header != null) {
                fw.write(header + "\n");
            }
            for (Object o : outputCollection) {
                fw.write(o.toString() + "\n");
            }
        } catch (Exception e) {
            System.err.println("Unable to write file: " + filename);
            e.printStackTrace();
        } finally {
            try {
                fw.close();
            } catch (IOException e) {
                System.err.println("Unable to close file: " + filename);
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<String> readFile(String filename) {
        String s;
        ArrayList<String> fileLines = new ArrayList<String>();

        BufferedReader in = null;

        try {
            in = new BufferedReader(new FileReader(filename));

            while ((s = in.readLine()) != null) {
                fileLines.add(s.trim());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Unable to find file: " + filename);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Unable to read file: " + filename);
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                System.err.println("Unable to close file: " + filename);
                e.printStackTrace();
            }
        }

        return fileLines;
    }

    public Color[] getDifferentColors() {
        Color[] colorlist = new Color[20];
        colorlist[0] = Color.RED;
        colorlist[1] = Color.BLUE;
        colorlist[2] = Color.GREEN;
        colorlist[3] = Color.ORANGE;
        colorlist[4] = Color.PINK;
        colorlist[5] = Color.MAGENTA;
        colorlist[6] = Color.CYAN;
        colorlist[7] = Color.GRAY;
        colorlist[8] = Color.BLACK;
        colorlist[9] = Color.DARK_GRAY;

        for (int i = 10; i < 20; i++) {
            colorlist[i] = Color.getHSBColor((float) 255 / (i + 1), (float) 255 / ((i + 1) * 2), (float) 255 / ((i + 1) * 3));
        }
        return colorlist;
    }

    public Color[] getDifferentColors(int x) {
        Color[] colorlist = new Color[x];
        colorlist[0] = Color.RED;
        colorlist[1] = Color.BLUE;
        colorlist[2] = Color.GREEN;
        colorlist[3] = Color.ORANGE;
        colorlist[4] = Color.PINK;
        colorlist[5] = Color.MAGENTA;
        colorlist[6] = Color.CYAN;
        colorlist[7] = Color.GRAY;
        colorlist[8] = Color.BLACK;
        colorlist[9] = Color.DARK_GRAY;
        for (int i = 10; i < x; i++) {
            colorlist[i] = Color.getHSBColor((float) 255 / (i + 1), (float) 255 / ((i + 1) * 2), (float) 255 / ((i + 1) * 3));
        }
        return colorlist;
    }

    public JTable getJTableFromFile(String infile) {
        int lc = lineCount(infile) - 1;
        int cc = colCount() - 2;
        String header[] = getHeader(infile).split("\t");
        Vector<String> vCols = new Vector(header.length);
        for (int i = 0; i < header.length; i++) {
            vCols.add((header[i].trim()));
        }
        Vector vRows = new Vector();

        try {
            BufferedReader br = new BufferedReader(new FileReader(infile));
            String line = null;
            int count = 0;
            while ((line = br.readLine()) != null) {
                if (count == 0) {
                    count++;
                    continue;
                }

                String sp[] = line.split("\t");
                Vector<String> data = new Vector(sp.length);
                for (int i = 0; i < sp.length; i++) {
                    data.add(sp[i]);
                }
                vRows.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JTable table = new JTable(vRows, vCols);
        table.setOpaque(false);
        table.setAutoCreateRowSorter(true);
        return table;
    }

    private static void copyFile(File sourceFile, File destFile)
            throws IOException {
        if (!sourceFile.exists()) {
            return;
        }
        if (!destFile.exists()) {
            destFile.createNewFile();
        }
        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }
    }

    public FilenameFilter getFilesWithExtension(String folderName, final String ext) {
        File f = new File(folderName);
        FilenameFilter textFilter = new FilenameFilter() {

            public boolean accept(File dir, String name) {
                String lowercaseName = name.toLowerCase();
                if (lowercaseName.endsWith(ext)) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        return textFilter;
    }

    public float log2Transform(float value) {
        float lvalue = Float.NaN;
        if (!Float.isNaN(value)) {
            if (value > 0) {
                lvalue = (float) (Math.log(value) / 0.69314718);
            } else {

                lvalue = (float) 0.0;
            }
        }
        return lvalue;
    }

    public float unLog2Transform(float logValue) {
        float lvalue = Float.NaN;
        if (!Float.isNaN(logValue)) {
            lvalue = (float) Math.pow(2, logValue);
        }
        return lvalue;
    }

    public void convertToLog2(String inFilePath, String outFilePath) {
        String header = getHeader(inFilePath);
        String rownames[] = getColumnDataFromFile(0, inFilePath);
        float fdata[][] = getColumnFloatDataFromFile(inFilePath, true, true, 1);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(outFilePath));
            bw.write(header + "\n");
            for (int i = 0; i < fdata.length; i++) {
                bw.write(rownames[i + 1]);
                for (int j = 0; j < fdata[0].length; j++) {
                    bw.write("\t" + log2Transform(fdata[i][j]));
                    bw.flush();
                }
                bw.write("\n");
                bw.flush();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 public void convertToUnLog2(String inFilePath, String outFilePath) {
        String header = getHeader(inFilePath);
        String rownames[] = getColumnDataFromFile(0, inFilePath);
        float fdata[][] = getColumnFloatDataFromFile(inFilePath, true, true, 1);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(outFilePath));
            bw.write(header + "\n");
            for (int i = 0; i < fdata.length; i++) {
                bw.write(rownames[i + 1]);
                for (int j = 0; j < fdata[0].length; j++) {
                    bw.write("\t" + unLog2Transform(fdata[i][j]));
                    bw.flush();
                }
                bw.write("\n");
                bw.flush();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Vector getFiles(String folder, String ext) {
        File files[] = new File(folder).listFiles();
        Vector<File> vFiles = new Vector();
        for (File f : files) {
            if (f.isDirectory()) {
                vFiles.addAll(getFiles(f.getAbsolutePath(), ext));
            } else {
                if (ext != null) {
                    String lowercaseName = f.getName().toLowerCase();
                    if (lowercaseName.endsWith(ext)) {
                        vFiles.add(f);
                    }
                } else {
                    vFiles.add(f);
                }
            }
        }
        return vFiles;
    }

    public Vector getFolders(String folder) {
        File files[] = new File(folder).listFiles();
        Vector<File> vFiles = new Vector();
        for (File f : files) {
            if (f.isDirectory()) {
                vFiles.add(f);
            }
        }
        return vFiles;
    }

    public File[] sortFilesByModifiedPeriod(String resultPath) {
        File sortedFiles[] = new File(resultPath).listFiles(getFilesWithExtension(resultPath, ".txt"));
        Arrays.sort(sortedFiles, new NumberedFileComparator());
        return sortedFiles;
    }

    public class NumberedFileComparator implements Comparator {

        public int compare(Object f1, Object f2) {
            int val = (int) (((File) f1).lastModified() - ((File) f2).lastModified());
            return val;
        }

    }

    public int[] getRandomNumbers(int maxLimit, int setSize) {
        int[] rn = new int[setSize];
        Vector rv = new Vector(setSize);
        Random rndm = new Random();
        int i = 0;
        while (rv.size() < setSize) {
            int tmp = rndm.nextInt(maxLimit);
            if (!rv.contains("" + tmp)) {
                rn[i] = tmp;
                rv.add("" + tmp);
                i++;
            }
        }
        return rn;
    }

    public double[] getMinMax(String infile) {
        double[] minmax = new double[2];
        double[][] data = getColumnDoubleDataFromFile(infile, true, true);
        double min = 0;
        double max = 0;
        for (int i = 0; i < data[0].length; i++) {
            for (int j = 0; j < data.length; j++) {
                if (min > data[i][j]) {
                    min = data[i][j];
                }
                if (max < data[i][j]) {
                    max = data[i][j];
                }
            }
        }
        minmax[0] = min;
        minmax[1] = max;
        return minmax;
    }

    public void saveComponentAsPNG(String filename, JPanel plots) {
        //MessageBox msg = new MessageBox();
        //msg.showMessage("Image saving in process", 1);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gs = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gs.getDefaultConfiguration();
        //BufferedImage myImage = new BufferedImage(getWidth(), getHeight(),BufferedImage.TYPE_4BYTE_ABGR);
        BufferedImage myImage = gc.createCompatibleImage(plots.getWidth(), plots.getHeight(), Transparency.TRANSLUCENT);
        Graphics2D g2 = myImage.createGraphics();
        plots.paint(g2);
        g2.dispose();
        try {
            ImageIO.write(myImage, "png", new File(filename + ".png"));
        } catch (Exception e) {
            System.out.println(e);
        }
        //msg.hide();
       // msg.showMessage("Image saving process is finished", 1);
    }
}
