/**
 * SMART: Sunny's Motif analysis retrieval tool.
 * Author:Srinivas Veerla (Sunny).
 * Description: 
 * Constructor: 
 * Description:
 *
 * Methods:
 * 
 * 
 * 
 *
 */

package RadarPlot;

//import javax.swing.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MessageBox extends JFrame{
   
    public MessageBox(){
       setDefaultLookAndFeelDecorated(true);
        
    }
    public void showMessage(String message,int kind){
        JOptionPane.showMessageDialog(this,message);
    }
                /*public static void main(String str[]){
                        new MessageBox().showMessage("Hi Sunny","i");
                        }*/
}