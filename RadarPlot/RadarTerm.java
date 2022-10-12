/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RadarPlot;

/**
 *
 * @author Sunnyveerla
 */
public class RadarTerm {

    double data[][];
    String radarLabels[];
    String valueLabels[];
    String annLabels[];
    String annValues[];

    RadarTerm(double data[][], String[] radarLabels, String[] valueLabels, String[] annLabels,String annValues[]) {
        this.data = data;
        this.radarLabels = radarLabels;
        this.valueLabels = valueLabels;
        this.annLabels = annLabels;
        this.annValues = annValues;
    }
}
