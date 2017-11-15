import java.util.ArrayList;
import java.util.List;

/**
 * Created by irvin on 11/13/17.
 */
public class Processor {
    List<Core> cores;
    List<HashMap<String, Integer>> registers;
    List <Cache> cacheData;
    Lsit<Cache>  cacheInst;
    PhysicalMemory memLocal;
    PhysicalMemory memExternal;
    Queue<Context> coreContexts;
    int quantum;
    int pc;
    int clock;
    Semaphore barrier;
    Buses buses;

    public Processor(int numberOfCore1, int numberOfCore2, PhysicalMemory memLocal, PhysicalMemory memExternal, int quantum , Buses buses, List <Cache> cacheData){
        this.memLocal = memLocal;
        this.memExternal= memExternal;
        this.quantum=quantum;
        this.cacheData= cacheData;
        this.Buses= buses;
        registers = new ArrayList<HashMap<String, Integer>>();
        registers.add(new HashMap<String, Integer>());
        registers.add(new HashMap<String, Integer>());
        cores = new ArrayList<Core>();
        coreContexts = new Queue<Context>();
        //create cache inst
        cacheInst = cacheInst= new ArrayList<Caches>();
        cacheInst.add( new Cache(Constant.INSTRUCTION_CACHE_TYPE));
        cacheInst.add( new Cache(Constant.INSTRUCTION_CACHE_TYPE));
        //create cores
        cores.add(new Core(barrier,clock,pc,quantum,numberOfCore1,coreContexts,this.cacheInst.get(0),this.cacheData,memLocal, memExternal,buses));
        cores.add(new Core(barrier,clock,pc,quantum,numberOfCore2,coreContexts,this.cacheInst.get(1),this.cacheData,memLocal, memExternal,buses));
    }

    public Processor(int numberOfCore,PhysicalMemory memLocal, PhysicalMemory memExternal, int quantum , Buses buses, List <Cache> cacheData){
        this.memLocal = memLocal;
        this.memExternal= memExternal;
        this.quantum= quantum;
        this.cacheData= cacheData;
        this.buses= buses;
        registers = new ArrayList<HashMap<String, Integer>>();
        registers.add(new HashMap<String, Integer>());
        cores = new ArrayList<Core>();
        coreContexts = new Queue<Context>;
        //create cache
        cacheInst= new ArrayList<Caches>();
        cacheInst.add(new Cache(Constant.INSTRUCTION_CACHE_TYPE));
        //create context
        cores.add(new Core(barrier,clock,pc,quantum,numberOfCore,coreContexts,this.cacheInst.get(2),this.cacheData,memLocal, memExternal,buses));//pasa la cache de instrucciones 2
    }


    /**
     * open each instruction file in a folder, read each  file truncating every 4 lines to store it in memory of instructions
     * @param path is the direction of folder that contains the files for the'hilillos'
     */
    public void bootUp(String path){
        //Tomar un directorio y tomar todos los archivos de ese directorio como hilillos a subir.
        //Subir a los hilillos a memoria de intruciones
        //para cada hilillo.
        //Crear nuevos contextos para la cola de contextos del procesos que comparten los núcleo.
        //Cada dirección en la memoria de instruciones donde inicia un hilillo, es el PC inicial un contexto
        //inicial de la cola.
        //String path= "C:\Users\Tony\Desktop\p1MIPS";//path of files for a specific core
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
        //correr bloque
        cores.get(numberOfCore).run();

      }
}
