import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * Created by irvin on 11/13/17.
 */
public class Processor {
    private Map<String, Cache> caches;
    private PhysicalMemory localPhysicalMemory;
    private Directory localDirectory;
    private Queue<Context> coreContext;
    private String programFilesPath;



  public Processor(Map<String, Cache> caches, PhysicalMemory localPhysicalMemory,
                   Directory localDirectory, String programFilesPath){
      this.caches = caches;
      this.localPhysicalMemory = localPhysicalMemory;
      this.localDirectory = localDirectory;
      this.programFilesPath = programFilesPath;
      this.coreContext = new LinkedList<Context>();
  }

    public Map<String, Cache> getCaches() {
        return caches;
    }

    public PhysicalMemory getLocalPhysicalMemory() {
        return localPhysicalMemory;
    }

    public Directory getLocalDirectory() {
        return localDirectory;
    }

    public Queue<Context> getCoreContext() {
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
        int currentPC = 0;

        for (int i = 0; i < listOfFiles.length; i++) {//for each file
            File currentFile = listOfFiles[i];

            int numberOfWords = 0;
            ArrayList<int[]> instructions = new ArrayList<int[]>();

            if (currentFile.isFile()) {

                try{
                    BufferedReader file = new BufferedReader(new FileReader(currentFile));
                    line = file.readLine();

                    while(line != null){
                        numberOfWords++;
                        String[] tokens = line.split(" ");
                        int[] instruction = lexicalAnalysis(tokens); //instrucion == word.
                        instructions.add(instruction);

                        line = file.readLine();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }

                //int totalBlocks = (int) Math.ceil(numberOfWords/Constant.WORDS_IN_BLOCK);
                int remainingWords = numberOfWords%Constant.WORDS_IN_BLOCK;
                int totalFullBlocks = ( ( numberOfWords - remainingWords ) / Constant.WORDS_IN_BLOCK );
                int fullBlocktoWrite [] = new int[Constant.INSTRUCTION_EMPTY_BLOCK.length];

                int instructionNumberToRead=0;
                for(int b = 0; b < totalFullBlocks; b++){
                    for(int w = 0; w < Constant.WORDS_IN_BLOCK; w++){//guardar una instruccion
                        fullBlocktoWrite[4*w+0] = instructions.get(instructionNumberToRead)[0];
                        fullBlocktoWrite[4*w+1] = instructions.get(instructionNumberToRead)[1];
                        fullBlocktoWrite[4*w+2] = instructions.get(instructionNumberToRead)[2];
                        fullBlocktoWrite[4*w+3] = instructions.get(instructionNumberToRead)[3];
                        instructionNumberToRead++;
                    }
                    localPhysicalMemory.writeInstructionMemory(currentPC+b, fullBlocktoWrite);
                }
                //instrucciones sobrantes
                if( (numberOfWords - (totalFullBlocks*4)) > 0) {
                    int blocktoWrite []= new int[Constant.INSTRUCTION_EMPTY_BLOCK.length];
                    for (int n = 0; n < numberOfWords - (totalFullBlocks * 4); n++) {
                        //instructions.get(instructionNumberToRead)[0];
                        blocktoWrite[(4 * n) + 0] = instructions.get(instructionNumberToRead)[0];
                        blocktoWrite[4 * n + 1] = instructions.get(instructionNumberToRead)[1];
                        blocktoWrite[4 * n + 2] = instructions.get(instructionNumberToRead)[2];
                        blocktoWrite[4 * n + 3] = instructions.get(instructionNumberToRead)[3];
                    }
                    localPhysicalMemory.writeInstructionMemory(currentPC + totalFullBlocks, blocktoWrite);
                }
                coreContext.add(new Context(getNewEmptyRegistersSet(), currentPC));//context for each file
                currentPC += totalFullBlocks;
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
