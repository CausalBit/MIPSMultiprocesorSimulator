import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by J.A Rodríguez on 13/11/2017.
 */
public class Buses {
    private HashMap<String, Lock> buses;

    public Buses(){
        buses.put("Cache0", new ReentrantLock());
        buses.put("Cache1", new ReentrantLock());
        buses.put("Cache2", new ReentrantLock());
        buses.put("Directory0", new ReentrantLock());
        buses.put("Directory1", new ReentrantLock());
        buses.put("InstructionsMemory", new ReentrantLock());
        buses.put("SharedDataMemory", new ReentrantLock());
    }

    public Boolean request(String busRequested){
        try{
            buses.get(busRequested).lock();
            return true;
        } finally {
            buses.get(busRequested).unlock();
            return false;
        }
    }

    public void setFree(String busToSetFree){
        buses.get(busToSetFree).unlock();
    }
}
