import com.google.common.base.Preconditions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by irvin on 11/13/17.
 */
public class Processor {
    private Map<String, Cache> caches;
    private PhysicalMemory localPhysicalMemory;
    private Directory localDirectory;
    private ConcurrentLinkedQueue<Context> coreContext;
    private String programFilesPath;
    private int currentPC;


  public Processor(Map<String, Cache> caches, PhysicalMemory localPhysicalMemory,
                   Directory localDirectory, String programFilesPath){
      this.caches = caches;
      this.localPhysicalMemory = localPhysicalMemory;
      this.localDirectory = localDirectory;
      this.programFilesPath = programFilesPath;
      this.coreContext = new ConcurrentLinkedQueue<Context>();
  }

    public Map<String, Cache> getCaches() {
        return caches;
    }

    public Cache getDataCacheByCoreId(int coreId){
        String cacheID = getCacheIdbyCoreId( coreId);
        Preconditions.checkArgument(caches.keySet().contains(cacheID), "Core "+coreId+" does not belong to processor");

        return caches.get(cacheID);
    }
    public String getCacheIdbyCoreId(int coreId){
        String cacheID = Constant.DATA_CACHE_0;
        switch(coreId){
            case Constant.CORE_1:
                cacheID = Constant.DATA_CACHE_1;
                break;
            case Constant.CORE_2:
                cacheID = Constant.DATA_CACHE_2;
                break;
        }
        return cacheID;
    }

    public Directory getLocalDirectory() { return localDirectory; }

    public PhysicalMemory getLocalPhysicalMemory() {
        return localPhysicalMemory;
    }

    //public String setLocalDirectory() {return caches.get();}

    public ConcurrentLinkedQueue<Context> getCoreContext() {
        return coreContext;
    }

    /**
     * open each instruction file in a folder, read each  file truncating every 4 lines to store it in memory of instructions
     *  is the direction of folder that contains the files for the'hilillos'
     */
    public void bootUp() throws Exception {
        //Tomar un directorio y tomar todos los archivos de ese directorio como hilillos a subir.
        //Subir a los hilillos a memoria de intruciones
        //para cada hilillo.
        //Crear nuevos contextos para la cola de contextos del procesos que comparten los núcleo.
        //Cada dirección en la memoria de instruciones donde inicia un hilillo, es el PC inicial un contexto
        //inicial de la cola.
        //String path= "C:\Users\Tony\Desktop\p1MIPS";//path of files for a specific core


        File folder = new File(programFilesPath);//take folder
        File[] listOfFiles = folder.listFiles(); //list of files for a specific core
        String line;
        int currentPC = this.localPhysicalMemory.getLocalInstMemInitAddress();//inicia en 0 para p1 y en 16 para p0 segun el enunciado

        for (int i = 0; i < listOfFiles.length; i++) {//for each file
            File currentFile = listOfFiles[i];

            int numberOfWords = 0;
            ArrayList<int[]> instructions = new ArrayList<int[]>();

            if (currentFile.isFile()) {

                try {
                    BufferedReader file = new BufferedReader(new FileReader(currentFile));
                    line = file.readLine();

                    while (line != null) {
                        numberOfWords++;
                        String[] tokens = line.split(" ");
                        int[] instruction = lexicalAnalysis(tokens); //instrucion == word.
                        instructions.add(instruction);

                        line = file.readLine();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            /*iniciar carga a bloque de instrucciones*/
                //int totalBlocks = (int) Math.ceil(numberOfWords/Constant.WORDS_IN_BLOCK);
                int remainingWords = numberOfWords%Constant.WORDS_IN_BLOCK;
                int totalFullBlocks = ( ( numberOfWords - remainingWords ) / Constant.WORDS_IN_BLOCK );
                int totalBlocksToWrite = 0;

                int fullBlocktoWrite [];
                int instructionNumberToRead=0;
                for(int b = 0; b < totalFullBlocks; b++){
                    fullBlocktoWrite  = new int[Constant.INSTRUCTION_EMPTY_BLOCK.length];//total de full bloques
                    for(int w = 0; w < Constant.WORDS_IN_BLOCK; w++){//guardar una instruccion
                        fullBlocktoWrite[Constant.INSTRUCTION_EMPTY_WORD.length*w+0] = instructions.get(b*4+w)[0];
                        fullBlocktoWrite[Constant.INSTRUCTION_EMPTY_WORD.length*w+1] = instructions.get(b*4+w)[1];
                        fullBlocktoWrite[Constant.INSTRUCTION_EMPTY_WORD.length*w+2] = instructions.get(b*4+w)[2];
                        fullBlocktoWrite[Constant.INSTRUCTION_EMPTY_WORD.length*w+3] = instructions.get(b*4+w)[3];

                    }
                    int blockNumber = localPhysicalMemory.getLocalInstMemBlockNumber(currentPC+(b*Constant.INSTRUCTION_EMPTY_BLOCK.length));
                    localPhysicalMemory.writeBlockInstructionMemory(blockNumber, fullBlocktoWrite);
                }
                //instrucciones sobrantes
                if( remainingWords > 0) {
                    int blocktoWrite []= new int[Constant.INSTRUCTION_EMPTY_BLOCK.length];//if codop es 63 es fin
                    int subWordIndex = 0;
                    for (int n = totalFullBlocks*Constant.WORDS_IN_BLOCK; n < totalFullBlocks*Constant.WORDS_IN_BLOCK+remainingWords; n++) {
                        blocktoWrite[Constant.INSTRUCTION_EMPTY_WORD.length*subWordIndex] = instructions.get(n)[0];
                        blocktoWrite[Constant.INSTRUCTION_EMPTY_WORD.length*subWordIndex+ 1] = instructions.get(n)[1];
                        blocktoWrite[Constant.INSTRUCTION_EMPTY_WORD.length*subWordIndex+ 2] = instructions.get(n)[2];
                        blocktoWrite[Constant.INSTRUCTION_EMPTY_WORD.length*subWordIndex+ 3] = instructions.get(n)[3];
                        subWordIndex++;
                    }
                    int blockNumber = localPhysicalMemory.getLocalInstMemBlockNumber(currentPC+(totalFullBlocks*Constant.INSTRUCTION_EMPTY_BLOCK.length));
                    localPhysicalMemory.writeBlockInstructionMemory(blockNumber, blocktoWrite);
                    totalBlocksToWrite++;
                }
                totalBlocksToWrite += totalFullBlocks;
                Context newContext = new Context(getNewEmptyRegistersSet(), currentPC);
                newContext.setIdHilillo(currentFile.getName());
                coreContext.add(newContext);//context for each file
                currentPC += totalBlocksToWrite*Constant.INSTRUCTION_EMPTY_BLOCK.length;
            }
        }


    }


    public HashMap<String, Integer> getNewEmptyRegistersSet(){
        HashMap<String, Integer> registers = new HashMap<String, Integer>();
        registers.put("0", Constant.REGISTER_ZERO);
        for (int i = 1; i < Constant.NUMBER_OF_REGISTERS_PER_CORE; i++) {
            registers.put(Integer.toString(i), Constant.REGISTER_NULL_VALUE);
        }
        return registers;
    }

    public int[] lexicalAnalysis(String[] stringTokes ){
        int size = stringTokes.length;
        int[] result  = new int[size];

        for(int i = 0; i < size; i++){
            result[i] = Integer.parseInt(stringTokes[i]);
        }
        return result;
    }




}
