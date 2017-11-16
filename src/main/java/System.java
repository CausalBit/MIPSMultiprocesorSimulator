import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by irvin on 11/12/17.
 */
public class System {


    public static void main(String [ ] args) {
        //Aquí se van a crear todos las partes simuladas del sistema. Como es la memoria, bus, etc.
        //TODO cola de semaphore general para el barrier para los 3 cores
        //pide quantum
        //es necesario crearlas cachesdata aqui ya que se deben compartir para cada procesador , se pueden compartir en algun momento
        int clock = 0;
        int quantum = 40;

        AtomicInteger numberActiveCores = new AtomicInteger(3);

        CyclicBarrier barrier = new CyclicBarrier(3);


        PhysicalMemory P0memory = new PhysicalMemory(16,0,24,16);
        PhysicalMemory P1memory = new PhysicalMemory(8,16,16,8);

        Cache cacheD0 = new Cache(Constant.DATA_CACHE_TYPE);
        Cache cacheD1 = new Cache(Constant.DATA_CACHE_TYPE);
        Cache cacheD2 = new Cache(Constant.DATA_CACHE_TYPE);
        Cache cacheI0 = new Cache(Constant.INSTRUCTION_CACHE_TYPE);
        Cache cacheI1 = new Cache(Constant.INSTRUCTION_CACHE_TYPE);
        Cache cacheI2 = new Cache(Constant.INSTRUCTION_CACHE_TYPE);

        Map<String, Cache> processorOneCaches = new HashMap<String, Cache>();
        processorOneCaches.put(Constant.DATA_CACHE_0, cacheD0);
        processorOneCaches.put(Constant.DATA_CACHE_1, cacheD1);
        processorOneCaches.put(Constant.INSTRUCTIONS_CACHE_0, cacheI0);
        processorOneCaches.put(Constant.INSTRUCTIONS_CACHE_1, cacheI1);

        Map<String, Cache> processorTwoCaches = new HashMap<String, Cache>();
        processorTwoCaches.put(Constant.DATA_CACHE_2, cacheD2);
        processorTwoCaches.put(Constant.INSTRUCTIONS_CACHE_2, cacheI2);

        Directory directory0 = new Directory(16, 0);
        Directory directory1 = new Directory(8, 16);

        Processor processor0 = new Processor(processorOneCaches, P0memory, directory0, Constant.PATH_1);
        Processor processor1 = new Processor(processorTwoCaches, P1memory, directory1, Constant.PATH_2);

        Map<String, Processor> processors = new HashMap<String, Processor>();
        processors.put(Constant.PROCESSOR_0, processor0);
        processors.put(Constant.PROCESSOR_1, processor1);


        //TODO hacer booteo para crear contextos en los procesadores.
        try{
            processor0.bootUp();
            processor1.bootUp();
            //prueba

        }catch(Exception ex){
            ex.printStackTrace();
        }

        P0memory.printInstMem();
        P1memory.printInstMem();

        Bus bus = new Bus(processors);


        Core core0 = new Core(Constant.PROCESSOR_0, barrier, clock, quantum, numberActiveCores, bus, 0,Constant.INSTRUCTIONS_CACHE_0);
        Core core1 = new Core(Constant.PROCESSOR_0, barrier, clock, quantum, numberActiveCores, bus, 1,Constant.INSTRUCTIONS_CACHE_1);
        Core core2 = new Core(Constant.PROCESSOR_1, barrier, clock, quantum, numberActiveCores, bus, 2,Constant.INSTRUCTIONS_CACHE_2);

        //if(processor0.getCoreContext().size()>0 && processor1.getCoreContext().size()>0){
            Thread runningCore0 = new Thread(core0);
            Thread runningCore1 = new Thread(core1);
            Thread runningCore2 = new Thread(core2);

            runningCore0.start();
            runningCore1.start();
            runningCore2.start();
        //}

        //También es la entrada del toda las simulación.
        //Después de crear estas, se puede hacer el boot up.




    }





}
