import java.util.ArrayList;
import java.util.List;

/**
 * Created by irvin on 11/13/17.
 */
public class Processor {
    List<Core> cores;
    List<HashMap<String, Integer>> registers;
    PhysicalMemory memP;
    Cache cacheInst0;
    Cache cacheInst1;
    Cache cacheData0;
    Cache cacheData1;
    Queue<Context> coreContexts;

    public Processor(int numberOfCore1, int numberOfCore2, PhysicalMemory memP){
        this.memP = memP;
        registers = new ArrayList<HashMap<String, Integer>>;
        registers.add(new HashMap<String, Integer>());
        registers.add(new HashMap<String, Integer>());
        cores = new ArrayList<Core>();
        coreContexts = new Queue<Context>();
        //create cores
        cores.add(new Core(barrier,clock,0,quantum,0,coreContexts));
        cores.add(new Core(barrier,clock,0,quantum,1,coreContexts));
        //create cach
        cacheInst0 = new Cache(Constant.INSTRUCTION_CACHE_TYPE);
        cacheInst1 = new Cache(Constant.INSTRUCTION_CACHE_TYPE);
        cacheData0 = new Cache(Constant.DATA_CACHE_TYPE);
        cacheData1 = new Cache(Constant.DATA_CACHE_TYPE);
    }

    public Processor(int numberOfCore,PhysicalMemory memP){
        this.memP = memP;
        registers = new ArrayList<HashMap<String, Integer>>;
        registers.add(new HashMap<String, Integer>());
        cores = new ArrayList<Core>();
        coreContexts = new Queue<Context>;
        //create context
        cores.add(new Core(barrier,clock,0,quantum,2,coreContexts));
        //create cach
        cacheInst2 = new Cache(Constant.INSTRUCTION_CACHE_TYPE);
        cacheData2 = new Cache(Constant.DATA_CACHE_TYPE);
    }

    /*open each instruction file in a folder, read each  file truncating every 4 lines to store it in memory of instructions
    */
    public void bootUp(){
        //Tomar un directorio y tomar todos los archivos de ese directorio como hilillos a subir.
        //Subir a los hilillos a memoria de intruciones
        //para cada hilillo.
        //Crear nuevos contextos para la cola de contextos del procesos que comparten los núcleo.
        //Cada dirección en la memoria de instruciones donde inicia un hilillo, es el PC inicial un contexto
        //inicial de la cola.
        String path= "C:\Users\Tony\Desktop\p1MIPS";//path of files for a specific core
        File folder = new File(path);//take folder
        File[] listOfFiles = folder.listFiles(); //list of files for a specific core
        int instMemBlockNumber=0;
        int p=0;

        for (int i = 0; i < listOfFiles.length; i++){//for each file
            coreContexts.add(new Context( registers.get(0),instMemBlockNumber));//context for each file
            if (listOfFiles[i].isFile()){
                files = listOfFiles[i].getName();
                FileReader f = new FileReader(listOfFiles[i].getAbsolutePath());//get path of file
                BufferedReader b = new BufferedReader(f);
                int wordNumber=0;
                int blockInst [] = new int [Constant.INSTRUCTION_EMPTY_BLOCK.length];//its size is of 16 words for block ie  4 lines of file
                while((line = b.readLine())!=null) {
                    String[] tokens = line.split(" ");
                    //TODO RECORDAR GUARDAR CUATRO LINEAS POR BLOQUE
                    for(int i =0 ; i < Constant.INSTRUCTION_EMPTY_WORD.length; i++){//read a line of instruction file
                      blockInst[wordNumber] = Integer.parseInt(tokens[i]) ;
                      wordNumber++;
                    }
                    p++;
                    if(p==4){
                      memP.writeInstructionMemory(instMemBlockNumber,blockInst);
                      instMemBlockNumber=+16;
                      p=0;
                    }
                }
                b.close();
              }
          }
      }

      public void runCore(int numberOfCore){
        //cargar bloque
        cores.get(numberOfCore).run();

      }
}
