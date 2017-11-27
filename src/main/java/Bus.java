import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.System;

/**
 * Created by J.A Rodriguez on 13/11/2017.
 */
public class Bus {
    private HashMap<String, Lock> buses;
    private Map<String, Processor> processors;

    /**
     * Creates a bus structure. It has an entry to every cache, memory and directories in the architecture. Each entry will
     * be available to be requested and locked by a core.
     */
    public Bus(Map<String, Processor> processors){
        buses = new HashMap<String, Lock>();
        buses.put(Constant.DATA_CACHE_0, new ReentrantLock());
        buses.put(Constant.INSTRUCTIONS_CACHE_0, new ReentrantLock());
        buses.put(Constant.DATA_CACHE_1, new ReentrantLock());
        buses.put(Constant.INSTRUCTIONS_CACHE_1, new ReentrantLock());
        buses.put(Constant.DATA_CACHE_2, new ReentrantLock());
        buses.put(Constant.INSTRUCTIONS_CACHE_2, new ReentrantLock());
        buses.put(Constant.DIRECTORY_0, new ReentrantLock());
        buses.put(Constant.DIRECTORY_1, new ReentrantLock());
        buses.put(Constant.INSTRUCTIONS_MEMORY_0, new ReentrantLock());
        buses.put(Constant.INSTRUCTIONS_MEMORY_1, new ReentrantLock());
        buses.put(Constant.SHARED_DATA_MEMORY_0, new ReentrantLock());
        buses.put(Constant.SHARED_DATA_MEMORY_1, new ReentrantLock());
        this.processors = processors;
    }

    /**
     * Try to lock the specified structure in the architecture to let a core to access to it individually.
     * @param busRequested is the requested bus' entry key to lock.
     * @return true if the bus can be locked, false if not
     */
    public Boolean request(String busRequested){
        //Simulation.out.println("requesting: "+busRequested);
        boolean wasLocked = buses.get(busRequested).tryLock();
       /*if(wasLocked){
           Simulation.out.println("locked: "+busRequested);
       }else{Simulation.out.println("unable to get: "+busRequested);}*/

        return wasLocked;
    }

    /**
     * Unlocks the specified structure in the architecture to let another core to access to it.
     * @param busToSetFree is the specified bus' entry key to set free.
     */
    public void setFree(String busToSetFree){
      // System.out.println("Freeing "+busToSetFree);
        buses.get(busToSetFree).unlock();
    }

    public Processor getProcessor(String processodId){
        return processors.get(processodId);
    }

    public Processor getProcessorById(String processorId){
        return processors.get(processorId);
    }
}
