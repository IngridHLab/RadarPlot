/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RadarPlot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.swing.JPanel;

/**
 *
 * @author Sunnyveerla
 */
public class RadarPlot extends JPanel {

    Graphics2D g2d;
    String[] radarLabels;
    String[] valueLabels;
    String[] annLabels;
    String[] annValues;
    double[][] data;

    public RadarPlot(String[] radarLabels, String[] valueLabels, String[] annLabels, String[] annValues, double[][] data) {
        this.radarLabels = radarLabels;
        this.valueLabels = valueLabels;
        this.annLabels = annLabels;
        this.annValues = annValues;
        this.data = data;
        setSize(800, 600);
    }

    public void paintComponent(Graphics2D g) {

        update(g);
    }

    public void paint(Graphics g) {
        double min = -1.0;
        double max = 1.0;
        g2d = (Graphics2D) g;
        int tx = 600;
        int ty = 600;
        int by = 200;
        //AffineTransform orig = g2d.getTransform();
        g2d.translate(by, tx);

        int ncol = data[0].length;

        //g2d.rotate(Math.toRadians(-90));
        g2d.rotate(-Math.PI / 2.0);
        int pwidth = by;
        int iwidth = pwidth / (ncol - 1);
        int xcentriod = 0;
        int ycentriod = 0;
        int posArray[][][] = new int[ncol - 1][ncol][2];
        for (int j = 0; j < ncol - 1; j++) {
            Polygon p = new Polygon();
            xcentriod = 0;
            ycentriod = 0;
            for (int i = 0; i < ncol; i++) {
                p.addPoint((int) (by + (pwidth - j * iwidth) * Math.cos(i * 2 * Math.PI / ncol)), (int) (by + (pwidth - j * iwidth) * Math.sin(i * 2 * Math.PI / ncol)));
                posArray[j][i][0] = (int) (by + (pwidth - j * iwidth) * Math.cos(i * 2 * Math.PI / ncol));
                posArray[j][i][1] = (int) (by + (pwidth - j * iwidth) * Math.sin(i * 2 * Math.PI / ncol));
                xcentriod += (int) (by + (pwidth - j * iwidth) * Math.cos(i * 2 * Math.PI / ncol));
                ycentriod += (int) (by + (pwidth - j * iwidth) * Math.sin(i * 2 * Math.PI / ncol));
                //System.out.println((int) (200 + (pwidth - j * iwidth) * Math.cos(i * 2 * Math.PI / ncol))+"\t"+ (int) (200 + (pwidth - j * iwidth) * Math.sin(i * 2 * Math.PI / ncol)));
            }
            g2d.drawPolygon(p);
        }
        xcentriod = xcentriod / ncol;
        ycentriod = ycentriod / ncol;

        for (int x = 0; x < ncol; x++) {
            g2d.drawLine(xcentriod, ycentriod, posArray[0][x][0], posArray[0][x][1]);
            //g2d.drawString("HERE" + x, posArray[0][x][0], posArray[0][x][1]);          
            //System.out.println(xcentriod + "\t" + ycentriod + "\t" + posArray[0][x][0] + "\t" + posArray[0][x][1]);
        }

        //double d[] = {-0.7436, -0.3867, 0.5054, 0.6603, -0.0526};
        //g2d.setColor(Color.red);
        g2d.setStroke(new BasicStroke(5));
        UtilInterface util = new UtilInterface();
        Color c[] = util.getDifferentColors();
        for (int y = 0; y < data.length; y++) {
            Polygon pd = new Polygon();
            //String st[] = data[y].split("\t");
            g2d.setColor((Color) c[y]);
            //System.out.println(c[y]);
            for (int x = 0; x < ncol; x++) {
                double d = data[y][x]; //Double.parseDouble(st[x]);
                double p = pwidth - ((d - min) / (max - min)) * pwidth;
                pd.addPoint((int) (by + (pwidth - p) * Math.cos(x * 2 * Math.PI / ncol)), (int) (by + (pwidth - p) * Math.sin(x * 2 * Math.PI / ncol)));
            }
            g2d.drawPolygon(pd);

        }

        //g2d.setTransform(orig);
        //g2d.translate(20, 500);
        //String str = "Sunny";
        g2d.rotate(Math.PI / 2.0);
        g2d.setColor(Color.black);

        for (int r = 0; r < ncol; r++) {
            int x = by + (pwidth - r * iwidth);
            int y = by;
            String rangeValue = "" + (1 - (r * 0.5));
            //g2d.drawString(rangeValue, posArray[r][0][1] - 20 - (rangeValue.length() * 3), -posArray[r][0][0]);
            g2d.drawString(rangeValue, y - 20 - (rangeValue.length() * 3), -x);
        }

        for (int y = 0; y < data.length; y++) {
            int xcorr = by - (20 * y);
            g2d.setColor((Color) c[y]);
            g2d.drawLine(tx - 50, -xcorr, tx, -xcorr);
            g2d.drawString(valueLabels[y], tx - 60 - (valueLabels[y].length() * 10), -xcorr + 5);

        }

        g2d.setColor(Color.black);
        for (int y = 0; y < annLabels.length; y++) {
            int xcorr = tx - 10 - (20 * y);
            //g2d.setColor((Color) c[y]);
            // g2d.drawLine(tx - 50, -xcorr, tx, -xcorr);
            g2d.setFont(new Font("SUNNY", Font.BOLD, 12));
            g2d.drawString(annLabels[y] + ":", -200, -xcorr + 5);
            g2d.setFont(new Font("SUNNY", Font.PLAIN, 12));
            g2d.drawString(annValues[y], -200 + (annLabels[y].length() * 8), -xcorr + 5);

        }

        g2d.setFont(new Font("SUNNY", Font.BOLD, 16));
        for (int x = 0; x < ncol; x++) {
            if (posArray[0][0][1] <= posArray[0][x][1]) {
                g2d.drawString(radarLabels[x], posArray[0][x][1], -posArray[0][x][0]);
            } else {
                g2d.drawString(radarLabels[x], posArray[0][x][1] - (radarLabels[x].length() * 7), -posArray[0][x][0]);
            }
            //System.out.println(posArray[0][x][1] + "\t" + -posArray[0][x][0]);

        }

    }
}
