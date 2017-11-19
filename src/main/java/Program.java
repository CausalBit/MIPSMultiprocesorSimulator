import javax.swing.*;
import java.awt.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by J.A Rodríguez on 18/11/2017.
 */
public class Program {

    public static void main (String args[]){
        final HashMap<String, int[]> registersPerThread = new HashMap<String, int[]>();
        ArrayList<int[]> procesor0SharedMem = new ArrayList<int[]>();
        ArrayList<int[]> procesor1SharedMem = new ArrayList<int[]>();
        ArrayList<ArrayList<int[]>> dataCache0 = new ArrayList<ArrayList<int[]>>();
        ArrayList<ArrayList<int[]>> dataCache1 = new ArrayList<ArrayList<int[]>>();
        ArrayList<ArrayList<int[]>> dataCache2 = new ArrayList<ArrayList<int[]>>();


        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Failed to set the program appearance to the\n system default.",
                    "Appearance error", JOptionPane.ERROR_MESSAGE);
        } catch (InstantiationException e) {
            JOptionPane.showMessageDialog(null, "Failed to set the program appearance to the\n system default.",
                    "Appearance error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalAccessException e) {
            JOptionPane.showMessageDialog(null, "Failed to set the program appearance to the\n system default.",
                    "Appearance error", JOptionPane.ERROR_MESSAGE);
        } catch (UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(null, "Failed to set the program appearance to the\n system default.",
                    "Appearance error", JOptionPane.ERROR_MESSAGE);
        }

        ConfigurationWindow cw = new ConfigurationWindow();
        DataViewerWindow dw = new DataViewerWindow();
        final ResultsWindow rw = new ResultsWindow();

        GuiManager gui = new GuiManager(cw, dw, rw);

        Thread guiManager = new Thread(gui);
        guiManager.start();

        try {
            guiManager.join();
        }catch(InterruptedException e){

        }

        while(gui.cw.isVisible()){
            //Waits until the user press the Run! button;
            //No sé por qué no se sale del ciclo si no se mantiene imprimiendo algo en cada iteración
            java.lang.System.out.println();
        }

        //System simulation = new System(cw.getQuantum());
        //Thread runningSimulation = new Thread(simulation);
        //runningSimulation.start();

        /*int[] reg1 = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,256,0,87,87};
        int[] reg2 = {-1,-1,-1,-1,-1,5,9,8,7,41,1,2,5,4,6,1,-1,-1,-1,-1,-1,-1,-1,0,1,2,40,0,0,0,0,7,348,1,45,200};
        int[] reg3 = {0,0,0,0,5,5,7,89,9,6,3,1,-1,-1,-1,-1,5,4,-1,-1,-1,-1,-1,98,7,5,2,0,3,-1,-1,0,490,2,40,300};

        registersPerThread.put("1", reg1);
        registersPerThread.put("2", reg2);
        registersPerThread.put("3", reg3);*/

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    rw.setLocationRelativeTo(null);
                    rw.setVisible(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Failed to run the program. Cannot set the thread to execute it.",
                            "Execution error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        rw.saveFinalRegisters(registersPerThread);
    }
}
