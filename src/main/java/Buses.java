import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by J.A Rodriguez on 13/11/2017.
 */
public class Buses {
    private HashMap<String, Lock> buses;

    /**
     * Creates a bus structure. It has an entry to every cache, memory and directories in the architecture. Each entry will
     * be available to be requested and locked by a core.
     */
    public Buses(){
        buses.put("DCache0", new ReentrantLock());
        buses.put("ICache0", new ReentrantLock());
        buses.put("DCache1", new ReentrantLock());
        buses.put("ICache1", new ReentrantLock());
        buses.put("DCache2", new ReentrantLock());
        buses.put("ICache2", new ReentrantLock());
        buses.put("Directory0", new ReentrantLock());
        buses.put("Directory1", new ReentrantLock());
        buses.put("InstructionsMemory0", new ReentrantLock());
        buses.put("InstructionsMemory1", new ReentrantLock());
        buses.put("SharedDataMemory0", new ReentrantLock());
        buses.put("SharedDataMemory1", new ReentrantLock());
    }

    /**
     * Try to lock the specified structure in the architecture to let a core to access to it individually.
     * @param busRequested is the requested bus' entry key to lock.
     * @return true if the bus can be locked, false if not
     */
    public Boolean request(String busRequested){
        return buses.get(busRequested).tryLock();
    }

    /**
     * Unlocks the specified structure in the architecture to let another core to access to it.
     * @param busToSetFree is the specified bus' entry key to set free.
     */
    public void setFree(String busToSetFree){
        buses.get(busToSetFree).unlock();
    }
}
