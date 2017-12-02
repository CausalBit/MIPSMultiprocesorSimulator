import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.lang.System;


/**
 * Created by irvin on 11/12/17.
 */
public class Simulation {

    public static String getPublicPath(){
        String filanPath = "";
        try {

            File jarPath=new File(Simulation.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String propertiesPath=jarPath.getParentFile().getParentFile().getAbsolutePath();
            //System.out.println(" propertiesPath-"+propertiesPath);
            filanPath = propertiesPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filanPath;
    }

    public static void main(String [ ] args) {
        //Aquí se van a crear todos las partes simuladas del sistema. Como es la memoria, bus, etc.
        //TODO cola de semaphore general para el barrier para los 3 cores
        //pide quantum
        //es necesario crearlas cachesdata aqui ya que se deben compartir para cada procesador , se pueden compartir en algun momento
        System.out.println("Please enter the quantum: ");
        Scanner scanner = new Scanner(System.in);
        int inQuantum = scanner.nextInt();
        System.out.println("Your quantum is " + inQuantum+"\n\n");

        System.out.println("Indicate the  mode of execution, fast (0), slow (1): ");
        int modeExecution = scanner.nextInt();

        boolean master = ( modeExecution == 1 );

        int clock = 0;
        int quantum = inQuantum;

        AtomicInteger numberActiveCores = new AtomicInteger(3);
        AtomicInteger waitingCores = new AtomicInteger(0);

       // CyclicBarrier barrier = new CyclicBarrier(3);


        PhysicalMemory P0memory = new PhysicalMemory(16,0,24,16, Constant.SHARED_DATA_MEMORY_0);
        PhysicalMemory P1memory = new PhysicalMemory(8,16,16,8, Constant.SHARED_DATA_MEMORY_1);

        Cache cacheD0 = new Cache(Constant.DATA_CACHE_TYPE , Constant.DATA_CACHE_0);
        Cache cacheD1 = new Cache(Constant.DATA_CACHE_TYPE, Constant.DATA_CACHE_1);
        Cache cacheD2 = new Cache(Constant.DATA_CACHE_TYPE, Constant.DATA_CACHE_2);
        Cache cacheI0 = new Cache(Constant.INSTRUCTION_CACHE_TYPE, Constant.INSTRUCTIONS_CACHE_0);
        Cache cacheI1 = new Cache(Constant.INSTRUCTION_CACHE_TYPE, Constant.INSTRUCTIONS_CACHE_1);
        Cache cacheI2 = new Cache(Constant.INSTRUCTION_CACHE_TYPE, Constant.INSTRUCTIONS_CACHE_2);

        Map<String, Cache> processorOneCaches = new HashMap<String, Cache>();
        processorOneCaches.put(Constant.DATA_CACHE_0, cacheD0);
        processorOneCaches.put(Constant.DATA_CACHE_1, cacheD1);
        processorOneCaches.put(Constant.INSTRUCTIONS_CACHE_0, cacheI0);
        processorOneCaches.put(Constant.INSTRUCTIONS_CACHE_1, cacheI1);

        Map<String, Cache> processorTwoCaches = new HashMap<String, Cache>();
        processorTwoCaches.put(Constant.DATA_CACHE_2, cacheD2);
        processorTwoCaches.put(Constant.INSTRUCTIONS_CACHE_2, cacheI2);

        Directory directory0 = new Directory(16, 0, Constant.DIRECTORY_0);
        Directory directory1 = new Directory(8, 16,Constant.DIRECTORY_1);

        String parentPath = getPublicPath();

        //Jar paths!

        //String path1 = parentPath+Constant.PATH_1;
        //String path2 = parentPath+Constant.PATH_2;

        String path1 = "/home/irvin/Documents/Universidad/VIIISemester/Archi/MIPSMultiprocesorSimulator/threads/hilillosP0";
        String path2 = "/home/irvin/Documents/Universidad/VIIISemester/Archi/MIPSMultiprocesorSimulator/threads/hilillosP1";
        Processor processor0 = new Processor(processorOneCaches, P0memory, directory0, path1);
        Processor processor1 = new Processor(processorTwoCaches, P1memory, directory1, path2);

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

       // P0memory.printInstMem();
        //P1memory.printInstMem();


        Bus bus = new Bus(processors);

        Core core0 = new Core(Constant.PROCESSOR_0, clock, quantum, numberActiveCores, waitingCores, bus, Constant.CORE_0,Constant.INSTRUCTIONS_CACHE_0, Constant.DATA_CACHE_0, master);
        Core core1 = new Core(Constant.PROCESSOR_0, clock, quantum, numberActiveCores, waitingCores,  bus, Constant.CORE_1,Constant.INSTRUCTIONS_CACHE_1, Constant.DATA_CACHE_1, false);
        Core core2 = new Core(Constant.PROCESSOR_1, clock, quantum, numberActiveCores, waitingCores,  bus, Constant.CORE_2,Constant.INSTRUCTIONS_CACHE_2, Constant.DATA_CACHE_2, false);

            Thread runningCore0 = new Thread(core0);
            Thread runningCore1 = new Thread(core1);
            Thread runningCore2 = new Thread(core2);

            runningCore0.start();
            runningCore1.start();
            runningCore2.start();
        //}
        try {
            runningCore0.join();
            runningCore1.join();
            runningCore2.join();

            directory0.printDirectoryData();
            directory1.printDirectoryData();
            P0memory.printSharedMem();
            P1memory.printSharedMem();
            cacheD0.printCacheData();
            cacheD1.printCacheData();
            cacheD2.printCacheData();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        //También es la entrada del toda las simulación.
        //Después de crear estas, se puede hacer el boot up.


    }
}
