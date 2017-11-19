import javax.swing.*;
import java.awt.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.Arrays;
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
        int[] cacheStates0 = new int[8];
        int[] cacheStates1 = new int[8];
        int[] cacheStates2 = new int[8];
        ArrayList<int[]> directory0 = new ArrayList<int[]>();
        ArrayList<int[]> directory1 = new ArrayList<int[]>();

        int[][] allCacheStates = new int[3][8];
        allCacheStates[0] = cacheStates0;
        allCacheStates[1] = cacheStates1;
        allCacheStates[2] = cacheStates2;

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

        //-------------------Methods to call in rw to fill the tables and labels:-------------------

        rw.saveFinalRegisters(registersPerThread);
        rw.writeMemoryData(procesor0SharedMem, procesor1SharedMem);
        rw.writeCacheData(dataCache0, dataCache1, dataCache2, allCacheStates);
        rw.writeDirectories(directory0, directory1);
    }
}
