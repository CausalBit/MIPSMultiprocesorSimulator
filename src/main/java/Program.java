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

        //-------------Comentarios sobre las estructuras-------------

        // Registros: se almacenan los registros para cada uno de los hilillos que se ejecutaron.
        // Si por ejemplo todos los hilillos corrieron en un solo core (lo cual no ocurre), los mismos.
        // registros se almacenarán varias veces con su estado en el tiempo.
        //
        // Poseen el siguiente formato:
        // String: nombre del hilillo, es decir, el nombre del archivo.
        // int[]: un arreglo que contiene lo siguiente:
        // Posiciones 0 - 31 posiciones son los registros
        // Posición 32: registro PC
        // Posición 33: número de core en el cual corrión el hilillo
        // Posición 34: total de ciclos que duró el hilillo
        // Posición 35: ciclo en el cual terminó el hilillo
        HashMap<String, int[]> registersPerThread = new HashMap<String, int[]>();

        // Para la memoria compartida se recibe un array donde cada posición contiene el vector
        // de 4 espacios palabras de un dígito.
        //
        // procesor0SharedMem debe tener 16 posiciones.
        // procesor1SharedMem debe tener 8 posiciones.
        ArrayList<int[]> procesor0SharedMem = new ArrayList<int[]>();
        ArrayList<int[]> procesor1SharedMem = new ArrayList<int[]>();

        // Para las cachés, se recibe un array que contiene cada columna de la caché.
        // Dentro de cada columna hay un vector de las palabras de 1 dígito.
        //
        // Arreglos son de tamaño 4 con un arreglo de tamaño 4 en su interior.
        ArrayList<ArrayList<int[]>> dataCache0 = new ArrayList<ArrayList<int[]>>();
        ArrayList<ArrayList<int[]>> dataCache1 = new ArrayList<ArrayList<int[]>>();
        ArrayList<ArrayList<int[]>> dataCache2 = new ArrayList<ArrayList<int[]>>();

        // El número de bloque en la columna y su estado se almacena en estos vectore.
        // Se van alternando las posiciones, es decir, (número, estado, número, estado...).
        // En las posiciones impares van los números de bloque en las 4 columnas y en las pares
        // el estado a través de las 4 clumnas.
        int[] cacheStates0 = new int[8];
        int[] cacheStates1 = new int[8];
        int[] cacheStates2 = new int[8];

        // Para los directorios se recibe un array que contiene una fila de este con el estado
        // y los bits. El primero se espera que tenga 16 posiciones y el segundo 8.
        ArrayList<int[]> directory0 = new ArrayList<int[]>();
        ArrayList<int[]> directory1 = new ArrayList<int[]>();

        //-------------Fin de comentarios sobre las estructuras-------------

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

        //--------------------------------------------------------------------------------------------------------
        //--------------------------Métodos para llenar las estructuras aquí presentes----------------------------
        //--------------------------------------------------------------------------------------------------------

        // Se le envía el quantum a la simulación
        //System simulation = new System(cw.getQuantum());
        //Thread runningSimulation = new Thread(simulation);
        //runningSimulation.start();

        // Espera a que el hilo de la simulación termine
        //try{
        //    runningSimulation.join();
        //}catch (InterruptedException e){
        //
        //}

        // registersPerThread = simulator.getRegisters();
        // procesor0SharedMem = simulator.getProcessor(Constant.PROCESSOR_0);
        // procesor1SharedMem = simulator.getProcessor(Constant.PROCESSOR_1);
        // dataCache0 = simulator.getDataCache(Constant.DATA_CACHE_0);
        // dataCache1 = simulator.getDataCache(Constant.DATA_CACHE_1);
        // dataCache2 = simulator.getDataCache(Constant.DATA_CACHE_2);
        // cacheStates0 = simulator.getCacheBlocksAndStates(Constant.DATA_CACHE_0);
        // cacheStates1 = simulator.getCacheBlocksAndStates(Constant.DATA_CACHE_1);
        // cacheStates2 = simulator.getCacheBlocksAndStates(Constant.DATA_CACHE_2);
        // directory0 = simulator.getDirectory(Constant.PROCESSOR_0);
        // directory1 = simulator.getDirectory(Constant.PROCESSOR_1);

        //--------------------------------------------------------------------------------------------------------
        //----------------------Fin de métodos para llenar las estructuras aquí presentes-------------------------
        //--------------------------------------------------------------------------------------------------------

        int[][] allCacheStates = new int[3][8];
        allCacheStates[0] = cacheStates0;
        allCacheStates[1] = cacheStates1;
        allCacheStates[2] = cacheStates2;

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
