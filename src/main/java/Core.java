import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

/**
 * Created by irvin on 11/12/17.
 */
public class Core implements Runnable {
    private int clock;
    private int pc;
    private boolean running;
    Semaphore barrier;
    HashMap<String, Integer> registers;

    Queue<Context> coreContexts;



    public Core(Semaphore barrier, int clock, int pc) {
        this.barrier = barrier;
        this.clock = clock;
        this.pc = pc;

        this.running = true;

        registers = new HashMap<String, Integer>();
        registers.put("0", Constant.REGISTER_ZERO);
        for(int i = 1; i < Constant.NUMBER_OF_REGISTER_PER_CORE; i++){
            registers.put(Integer.toString(i), Constant.REGISTER_NULL_VALUE);
        }

        coreContexts = new LinkedList<Context>();

    }

    public void run(){
        while(running){


        }
    }


}
