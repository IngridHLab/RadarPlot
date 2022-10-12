package RadarPlot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Comparator;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author sunnyveerla
 */
public class Plots extends JFrame implements ActionListener {

    /**
     * @param args the command line arguments
     */
    JPanel plots = null;
    UtilInterface util = new UtilInterface();
    //String infileA = null;
    JScrollPane rightscrollPane = null;
    String Header = "";

    public Plots() {

        initComponents();
    }

    private void initComponents() {
        String ftitle = "Plots";
        setTitle(ftitle);
        JLabel lbl_title = new JLabel(ftitle);
        lbl_title.setFont(new Font("SUNNY", Font.BOLD, 24));
        add(lbl_title, BorderLayout.NORTH);
        setJMenuBar(createMenuBar());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        rightscrollPane = new JScrollPane();
        rightscrollPane.setPreferredSize(new Dimension(500, 500));
        plots = new JPanel();
        plots.setSize(1000, 800);
        plots.setBackground(Color.WHITE);
        setSize(500, 500);

    }

    public void showRadarPlot(String inFilePath) {
        /*String fdata[] = util.getDataFromFile(inFilePath);
         String columns[] = fdata[0].trim().split("\t");
         String rows[] = new String[fdata.length - 1];
         String data[] = new String[rows.length];
         for (int i = 1; i < fdata.length; i++) {
         String sp[] = fdata[i].split("\t");
         rows[i - 1] = sp[0];
         data[i-1] = fdata[i].substring(fdata[i].indexOf("\t"),fdata[i].length()).trim();
         //System.out.println(data[i-1]);
         }*/
        Vector<RadarTerm> vRT = new ParsePROMIXFile().parsePROMIXFile(inFilePath);
        RadarTerm rt = vRT.get(1);
        RadarPlot rp = new RadarPlot(rt.radarLabels, rt.valueLabels, rt.annLabels, rt.annValues, rt.data);
       // rp.setPreferredSize(new Dimension(800, 800));
        plots.add(rp);
        display();
    }

    public void createAndSaveRadarPlot(String inFilePath) {
        /*String fdata[] = util.getDataFromFile(inFilePath);
         String columns[] = fdata[0].trim().split("\t");
         String rows[] = new String[fdata.length - 1];
         String data[] = new String[rows.length];
         for (int i = 1; i < fdata.length; i++) {
         String sp[] = fdata[i].split("\t");
         rows[i - 1] = sp[0];
         data[i-1] = fdata[i].substring(fdata[i].indexOf("\t"),fdata[i].length()).trim();
         //System.out.println(data[i-1]);
         }*/
        Vector<RadarTerm> vRT = new ParsePROMIXFile().parsePROMIXFile(inFilePath);
        //RadarTerm rt = vRT.get(1);
        for (Object ort : vRT.toArray()) {
            RadarTerm rt = (RadarTerm) ort;
            RadarPlot rp = new RadarPlot(rt.radarLabels, rt.valueLabels, rt.annLabels, rt.annValues, rt.data);
            String filePath = new File(inFilePath).getParentFile().getAbsolutePath();
            JPanel tmpPlot = new JPanel();
            tmpPlot.setBackground(Color.WHITE);
            tmpPlot.setSize(1000, 800);
            tmpPlot.add(rp);
            util.saveComponentAsPNG(filePath + "/" + rt.annValues[0], tmpPlot);
        }

    }
    /*public void displayPlots(String infileA) {

     File inf = new File(infileA);
     plots.setLayout(new GridLayout(2, 4));
     String dirName = inf.getAbsolutePath();
     if (inf.isDirectory()) {
     //String flist[] = inf.list();
     File flist[] = new File(infileA).listFiles();
     Arrays.sort(flist, new NumberedFileComparator());
     plots.setLayout(new GridLayout(0, 4));
     for (int i = 0; i < flist.length; i++) {
     String fileName = flist[i].getName();
     if (fileName.indexOf(".txt") != -1) {
     Header = fileName;
     makePlot(dirName + "/" + fileName);
     }
     }
     } else {
     plots.setLayout(new GridLayout());
     makePlot(infileA);
     }
     display();
     }*/

    /*public void displaySigPlots(String infile, String colx, String coly) {
     UtilInterface util = new UtilInterface();
     int col[] = new int[1];
     col[0] = 0;
     String gene[] = util.getColumnDataFromFile(col, infile);
     String symbols[] = new String[gene.length - 1];
     for (int i = 1; i < gene.length - 1; i++) {
     symbols[i - 1] = gene[i];
     }
     String colNames[] = new String[2];
     colNames[0] = colx;
     colNames[1] = coly;
     col = util.getColumnDataByColumnNames(colNames, infile);
       
     String X[] = util.getColumnDataFromFile(col[0], infile);
     float xdata[] = new float[X.length - 1];
     xdata[0] = (float) 0.0;
     for (int i = 1; i < X.length - 1; i++) {
     xdata[i - 1] = Float.parseFloat(X[i]);
     }
        
     String Y[] = util.getColumnDataFromFile(col[1], infile);
     float ydata[] = new float[Y.length - 1];
     ydata[0] = (float) 0.0;
     for (int i = 1; i < Y.length - 1; i++) {
     ydata[i - 1] = Float.parseFloat(Y[i]);
     }

     plots.add(getXYplot(symbols, xdata, ydata, new File(infile).getName(), X[0], Y[0]));
     display();
     }*/
    /*public void makePlot(String infileA) {
     String line = null;
     String header[] = {""};
     int cc = util.lineCount(infileA) - 1; //No of Samples

     int lc = util.colCount() - 1; //No of genes
     String labels[] = new String[lc];
     int rc = 0;
     String rownames[] = new String[cc];
     double fdata[][] = new double[lc][cc];

     try {
     BufferedReader br = new BufferedReader(new FileReader(infileA));
     System.out.println(infileA);
     while ((line = br.readLine()) != null) {
     if (rc == 0) {
     String st[] = line.split("\t");
     for (int l = 0; l < lc; l++) {
     labels[l] = st[l + 1];
     }
     rc++;
     continue;
     }
     String d[] = line.trim().split("\t");

     rownames[rc - 1] = d[0];
     for (int i = 0; i < lc && (i + 1) < d.length; i++) {

     fdata[i][rc - 1] = Float.parseFloat(d[i + 1]);

     }
     rc++; //Increment row count
     }

     } catch (IOException ex) {
     ex.printStackTrace();
     }
     // linePlots.setSize(1000, 2000);
     //linePlots.setLayout(new GridLayout(2, 4));        //System.out.println(obj.length);
     System.out.println("line plot");
     String fileName = new File(infileA).getName();
     fileName = fileName.substring(0, fileName.indexOf(".txt"));
     plots.add(getLinePlot(fdata, rownames, labels, fileName)); //Plot line plots
     //LinePlot lp = new LinePlot();
     //plots.add(lp.drawPlot(fdata, labels, header));
     display();
     }

     public void displayBarPlots_new(String path) {
     // System.out.println(path);
     File fPath = new File(path);
     if (fPath.isDirectory()) {
     File flist[] = util.sortFilesByModifiedPeriod(path);
     plots.setLayout(new GridLayout(0, 4));
     for (int i = 0; i < flist.length; i++) {
     String fileName = flist[i].getName();
     if (fileName.indexOf(".txt") != -1) {
     makeBarPlot_new(path + "/" + fileName);
     }
     }
     }
     display();
     }

     public void makeBarPlot_new(String infileB) {
     System.out.println(infileB);
     String percentData[][] = util.transformFile(infileB);
     int mlen = percentData.length;
     String header[] = util.getHeader(infileB).split("\t");
     Hashtable<String, Integer> cs = new Hashtable();
     int tmp = 0;
     for (int i = 1; i < mlen; i++) {
     int clusters = Integer.parseInt(percentData[i][1]);
     //System.out.println(clusters);
     cs.put(header[i], new Integer(clusters));
     if (tmp < clusters) {
     tmp = clusters;
     }
     }
     int clen = percentData[0].length;
     Hashtable<String, int[]> pd = new Hashtable();
     for (int i = 1; i < percentData.length; i++) {
     int[] percentage = new int[cs.get(percentData[i][0].trim()) + 1];
     int noRep[] = new int[percentage.length];
     for (int j = 2; j < clen; j++) {
     int tClust = Integer.parseInt(percentData[i][j].trim());
     if (noRep[tClust] != 1) {
     percentage[tClust] = Integer.parseInt(percentData[0][j].trim());
     noRep[tClust] = 1;
     }
     }
     pd.put(percentData[i][0], percentage);
     }
     String title = percentData[0][1];
     Arrays.sort(header, Collections.reverseOrder());
     //System.out.println(header[header.length-1]);
     PercentBarGraph pbg = new PercentBarGraph(header, cs, tmp, pd, title);
     pbg.setPreferredSize(new Dimension(1000, 1000));
     plots.add(pbg); //Plot line plots
     //LinePlot lp = new LinePlot();
     //plots.add(lp.drawPlot(fdata, labels, header));

     }*/

    /*public void makeHistogram(String infileB) {
     System.out.println(infileB);
     Hashtable<String, Hashtable<String, Integer>> ht = new Hashtable();
     Hashtable<String, String> htp = new Hashtable();
        
     Color color[] = {new Color(255, 0, 0), new Color(255, 0, 204), new Color(0, 102, 204), new Color(0, 255, 255), new Color(255, 204, 51), Color.LIGHT_GRAY};
     String data[] = util.getDataFromFile(infileB, true);
     int cc = 0;
     for (String c : data) {
     String sp[] = c.split("\t");
     System.out.println(c);
     String pamL = sp[2].trim();
     if (ht.containsKey(sp[1].trim())) {
     Hashtable<String, Integer> hp = ht.get(sp[1].trim());
     if (hp.containsKey(pamL)) {
     hp.put(pamL, hp.get(pamL) + 1);
     } else {
     hp.put(pamL, new Integer(1));
     if (!htp.containsKey(pamL)) {
     htp.put(pamL, pamL);
     }
     }
     ht.put(sp[1].trim(), hp);
     } else {
     Hashtable<String, Integer> hp = new Hashtable();
     hp.put(pamL, new Integer(1));
     ht.put(sp[1].trim(), hp);
     if (!htp.containsKey(pamL)) {
     htp.put(pamL, pamL);
     }
     }
     }
     Object htObj[] = ht.keySet().toArray();
     Hashtable<String, Hashtable<String, Double>> hr = new Hashtable();
     for (Object o : htObj) {
     Hashtable<String, Integer> pht = ht.get(o.toString());
     Object pObj[] = pht.keySet().toArray();
     int sum = 0;
     for (Object po : pObj) {
     sum += pht.get(po.toString());
     }

     Hashtable<String, Double> hpr = new Hashtable();
     for (Object po : pObj) {
     hpr.put(po.toString(), ((pht.get(po.toString())) / (double) sum));
     //System.out.println(o.toString()+"_"+po.toString()+"_"+((pht.get(po.toString())) / (double) sum));
     }
     hr.put(o.toString(), hpr);
     }

     Hashtable<String, Color> htc = new Hashtable();
     Object op[] = htp.keySet().toArray();
     Arrays.sort(op);
     for (Object o : op) {
     htc.put(o.toString(), color[cc++]);
     System.out.println(o.toString() + "_" + color[cc - 1]);
     }
     String header = "";
     String title = "Histogram";

     //System.out.println(header[header.length-1]);
     Histogram pbg = new Histogram(hr, htc, header, title);
     pbg.setPreferredSize(new Dimension(1000, 1000));
     plots.add(pbg);
     display();
     //saveComponentAsPNG(pbg, infileB.substring(0, infileB.indexOf(".txt")));
     //plots.add(pbg); //Plot line plots
     //LinePlot lp = new LinePlot();
     //plots.add(lp.drawPlot(fdata, labels, header));

     }*/

    /*public void makeConsistencyPlot(String infileA, String title) {
     String data[] = util.getDataFromFile(infileA);
     int len = data.length;
     Hashtable<String, Vector<int[]>> hc = new Hashtable(len - 1);
     String st[] = data[0].split("\t");
     System.out.println(st.length);
     int max = 0;
     for (int i = 1; i < st.length; i++) {
     Vector cv = new Vector(len - 1);
     for (int j = 1; j < len; j++) {
     String sp[] = data[j].split("\t");
     String c[] = sp[i].split("_");

     int v[] = new int[3];
     if (c.length > 1) {
     //c[1] = c[1].substring(0, c[1].length() - 1);
     v[0] = Integer.parseInt(c[0]);
     if (i == 1) {
     max += v[0];
     }
     v[1] = (int) (Float.parseFloat(c[2]) * 100);
     v[2] = (int) (Float.parseFloat(c[4]) * 100);
     }
     cv.add((j - 1), v);
     }
     hc.put("" + Float.parseFloat(st[i].trim()), cv);
     }

     ConsistencyPlot cp = new ConsistencyPlot(hc, max, title);
     cp.setPreferredSize(new Dimension(2500, 1200));
     plots.add(cp);
     display();
     }*/
    private void display() {
        rightscrollPane.setViewportView(plots);
        rightscrollPane.setIgnoreRepaint(
                true);
        rightscrollPane.setWheelScrollingEnabled(
                true);
        rightscrollPane.setVisible(
                true);
        rightscrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        rightscrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        rightscrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {

            public void adjustmentValueChanged(AdjustmentEvent arg0) {
                rightscrollPane.getViewport().repaint(rightscrollPane.getVisibleRect());
            }
        });

        rightscrollPane.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {

            public void adjustmentValueChanged(AdjustmentEvent arg0) {
                rightscrollPane.getViewport().repaint(rightscrollPane.getVisibleRect());
            }
        });

        rightscrollPane.setMinimumSize(new Dimension(100, 100));
        rightscrollPane.setBackground(Color.WHITE);
        getContentPane().add(rightscrollPane);
        setSize(1000, 1000);
    }

    /*public JPanel getLinePlot(double[][] fdata, String[] labels, String[] header, String title) {

     LinePlot lp = new LinePlot();
     int ng = fdata.length;
     int ns = fdata[0].length;
     int min = 100000;
     int max = 0;
     for (int i = 0; i < ng; i++) {
     for (int j = 0; j < ns; j++) {
     if (max < (int) fdata[i][j]) {
     max = (int) fdata[i][j];
     }
     if (min > (int) fdata[i][j]) {
     min = (int) fdata[i][j];
     }
     }
     }
     lp.setPreferredSize(new Dimension(1000, 1000));
     lp.setTitle(title);
     System.out.println("Drawing line plot");
     if (min > 1) {
     min -= 1;
     }
     lp.drawPlot(fdata, labels, min, max, header);
     return lp;
     }*/
    /*public JPanel getXYplot(String[] symbols, float[] xdata, float[] ydata, String infile, String X, String Y) {
     SigPlot sjp = new SigPlot();
     sjp.setPreferredSize(new Dimension(1000, 1000));
     sjp.setTitle("X Y Plot");
     //sjp.drawSigPlot(symbols, xdata, ydata, new File(infile).getName(), X, Y);
     return sjp;
     }*/
    //Creating menu bar
    JMenuItem saveItem;

    public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu,
                tools;

//Create the menu bar.
        menuBar = new JMenuBar();
        //Build the first menu.
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(menu);

        saveItem = new JMenuItem("Save as PNG", KeyEvent.VK_S);
        //saveItem.setEnabled(false);
        saveItem.addActionListener(this);
        menu.add(saveItem);
        return menuBar;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("Save as PNG")) {
            saveComponentAsPNG(util.fileChooser("Save", "PNG"));
        }
    }

    public void saveComponentAsPNG(String filename) {
        MessageBox msg = new MessageBox();
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
        msg.showMessage("Image saving process is finished", 1);
    }

    public class NumberedFileComparator implements Comparator {

        public int compare(Object f1, Object f2) {
            int val = (int) (((File) f1).lastModified() - ((File) f2).lastModified());
            return val;
        }

    }
}
