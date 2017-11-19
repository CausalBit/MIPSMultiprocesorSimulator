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
        int[] cacheStates = new int[8];
        ArrayList<int[]> directory0 = new ArrayList<int[]>();
        ArrayList<int[]> directory1 = new ArrayList<int[]>();

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

        //-------------------Dummy to add registers-------------------

        int[] reg1 = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,256,0,87,87};
        int[] reg2 = {-1,-1,-1,-1,-1,5,9,8,7,41,1,2,5,4,6,1,-1,-1,-1,-1,-1,-1,-1,0,1,2,40,0,0,0,0,7,348,1,45,200};
        int[] reg3 = {0,0,0,0,5,5,7,89,9,6,3,1,-1,-1,-1,-1,5,4,-1,-1,-1,-1,-1,98,7,5,2,0,3,-1,-1,0,490,2,40,300};

        registersPerThread.put("1", reg1);
        registersPerThread.put("2", reg2);
        registersPerThread.put("3", reg3);

        ////-------------------Dummy to add memory-------------------

        for(int i = 0; i < 16; i++){
            int[] array = {i,i+1,i+2,i+3};
            procesor0SharedMem.add(array);
        }

        for(int i = 0; i < 8; i++){
            int[] array = {i,i+20,i+20,i+20};
            procesor1SharedMem.add(array);
        }

        ////-------------------Dummy to add caches-------------------

        int index = 0;

        for(int i = 0; i < 4; i++){
            int[] word0 = {index};
            int[] word1 = {index+1};
            int[] word2 = {index+2};
            int[] word3 = {index+3};

            ArrayList<int[]> a = new ArrayList<int[]>();
            a.add(word0);
            a.add(word1);
            a.add(word2);
            a.add(word3);

            dataCache0.add(a);
            index++;
        }

        index = 100;

        for(int i = 0; i < 4; i++){
            int[] word0 = {index};
            int[] word1 = {index+1};
            int[] word2 = {index+2};
            int[] word3 = {index+3};

            ArrayList<int[]> a = new ArrayList<int[]>();
            a.add(word0);
            a.add(word1);
            a.add(word2);
            a.add(word3);

            dataCache1.add(a);
            index++;
        }

        index = 200;

        for(int i = 0; i < 4; i++){
            int[] word0 = {index};
            int[] word1 = {index+1};
            int[] word2 = {index+2};
            int[] word3 = {index+3};

            ArrayList<int[]> a = new ArrayList<int[]>();
            a.add(word0);
            a.add(word1);
            a.add(word2);
            a.add(word3);

            dataCache2.add(a);
            index++;
        }

        //-------------------Dummy to add cache states-------------------

        int[][] testCacheStates = {{5,-1,2,1,15,-1,7,2},{5,-1,2,1,15,-1,7,2},{5,-1,2,1,15,-1,7,2}};

        //-------------------Dummy to add directories-------------------

        int[] a = {0,0,0,0};
        int[] b = {1,1,0,0};
        int[] c = {2,1,0,1};
        int[] d = {0,0,0,0};
        directory0.add(a);
        directory0.add(b);
        directory0.add(c);
        directory0.add(d);

        int[] e = {2,1,1,1};
        int[] f = {2,1,1,0};
        int[] g = {0,0,0,0};
        int[] h = {1,0,0,1};
        directory1.add(e);
        directory1.add(f);
        directory1.add(g);
        directory1.add(h);

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
        rw.writeCacheData(dataCache0, dataCache1, dataCache2, testCacheStates);
        rw.writeDirectories(directory0, directory1);
    }
}
