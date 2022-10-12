package RadarPlot;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author sunnyveerla
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Plots pl = new Plots();
        String inFilePath = "/Users/sunnyveerla/Sunny/Projects/Others/Ingrid H/PROMIX_PAM50_corr_for_spider_plots_updated.txt";
        //new ParsePROMIXFile(inFilePath);
        pl.createAndSaveRadarPlot(inFilePath);
        pl.setVisible(true);

    }
}
