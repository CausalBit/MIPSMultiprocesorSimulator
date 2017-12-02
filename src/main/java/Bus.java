import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.System;

/**
 * Created by J.A Rodriguez on 13/11/2017.
 */
public class Bus {
    private HashMap<String, ReentrantLock> buses;
    private Map<String, Processor> processors;
    private HashMap<String, Integer> uses;

    /**
     * Creates a bus structure. It has an entry to every cache, memory and directories in the architecture. Each entry will
     * be available to be requested and locked by a core.
     */
    public Bus(Map<String, Processor> processors){
        buses = new HashMap<String, ReentrantLock>();
        uses = new HashMap<String, Integer>();

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

        uses.put(Constant.DATA_CACHE_0,0);
        uses.put(Constant.INSTRUCTIONS_CACHE_0, 0);
        uses.put(Constant.DATA_CACHE_1, 0);
        uses.put(Constant.INSTRUCTIONS_CACHE_1, 0);
        uses.put(Constant.DATA_CACHE_2, 0);
        uses.put(Constant.INSTRUCTIONS_CACHE_2, 0);
        uses.put(Constant.DIRECTORY_0, 0);
        uses.put(Constant.DIRECTORY_1, 0);
        uses.put(Constant.INSTRUCTIONS_MEMORY_0, 0);
        uses.put(Constant.INSTRUCTIONS_MEMORY_1, 0);
        uses.put(Constant.SHARED_DATA_MEMORY_0, 0);
        uses.put(Constant.SHARED_DATA_MEMORY_1, 0);
        this.processors = processors;
    }

    /**
     * Try to lock the specified structure in the architecture to let a core to access to it individually.
     * @param busRequested is the requested bus' entry key to lock.
     * @return true if the bus can be locked, false if not
     */
    public Boolean request(String busRequested){
        //Simulation.out.println("requesting: "+busRequested);
        boolean wasLocked = true;
        try {
             wasLocked = buses.get(busRequested).tryLock(500, TimeUnit.MILLISECONDS);
        }catch(InterruptedException i){
            i.printStackTrace();
        }
       if(wasLocked){
           uses.put(busRequested, uses.get(busRequested)+1);
          // System.out.println("locked: "+busRequested);
       }else{
           System.out.println("unable to get: "+busRequested);
       }

        return wasLocked;
    }

    /**
     * Unlocks the specified structure in the architecture to let another core to access to it.
     * @param busToSetFree is the specified bus' entry key to set free.
     */
    public void setFree(String busToSetFree){
     // System.out.println("Freeing "+busToSetFree);
        if(buses.get(busToSetFree).isLocked()) {
            if(uses.get(busToSetFree)==1) {
                uses.put(busToSetFree,0);
                buses.get(busToSetFree).unlock();
            }else{
                uses.put(busToSetFree,uses.get(busToSetFree)-1);
            }

        }
    }

    public Processor getProcessor(String processodId){
        return processors.get(processodId);
    }

    public Processor getProcessorById(String processorId){
        return processors.get(processorId);
    }

    public void freeOwnedByCurrentThread(){
        for (Map.Entry<String, ReentrantLock> entry : buses.entrySet())
        {
            if(entry.getValue().isHeldByCurrentThread() && entry.getValue().isLocked()){
                entry.getValue().unlock();
            }
        }
    }
}
