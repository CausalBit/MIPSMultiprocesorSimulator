import java.util.concurrent.Semaphore;

/**
 * Created by irvin on 11/12/17.
 */
public class Core implements Runnable {
    private int clock;
    private boolean running;
    Semaphore barrier;

    

    public Core(Semaphore barrier, int clock){
        this.barrier = barrier;
        this.clock = clock;
        this.running = true;

    }

    public void run(){
        while(running){


        }
    }


}
