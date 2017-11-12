import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

/**
 * Created by irvin on 11/12/17.
 */
public class Core implements Runnable {
    private int clock;
    private int quantum;
    private int pc;
    private int numberOfCoresSystem;
    private boolean running;
    Semaphore barrier;
    HashMap<String, Integer> registers;

    Queue<Context> coreContexts;



    public Core(Semaphore barrier, int clock, int pc, int quantum, int numberOfCoresSystem) {
        this.barrier = barrier;
        this.clock = clock;
        this.pc = pc;
        this.quantum = quantum;
        this.numberOfCoresSystem = numberOfCoresSystem;

        this.running = true;

        registers = new HashMap<String, Integer>();
        registers.put("0", Constant.REGISTER_ZERO);
        for(int i = 1; i < Constant.NUMBER_OF_REGISTER_PER_CORE; i++){
            registers.put(Integer.toString(i), Constant.REGISTER_NULL_VALUE);
        }

        coreContexts = new LinkedList<Context>();

    }

    public void run(){
        int instructionDuration = 0;
        boolean instructionIsFinished = false; //Cuando finaliza una instrucción, esta se modifica.
        while(running){

            //Instrucción corre aquí.

            //Cuando termine de correr una instrucción, o parte de esta, se incrementa el reloj.
            clock++;
            //La barrera va aquí para que sea necesario correr otro ciclo si ya no hay quantum.
            if(barrier.getQueueLength() == numberOfCoresSystem - 1){
                //es el último en llegar a la barrera, liberar todos.
                barrier.release(numberOfCoresSystem-1);
            }else{
                try {
                    barrier.acquire();
                }catch( InterruptedException e ){
                    e.printStackTrace();
                }
            }
            //La verificación del cuantum está aquí.
            if(instructionIsFinished && instructionDuration >= quantum){
                //Store the current context if program is not finished (current instruction is not FIN).
                //Take the next context from the queue.
                //If the next context is null, then the core shuts down.
                //The shut down implies decreasing the numberOfCoresSystem by one. :D
            }
            //TODO preguntarle a profe que si un hilo corre 40 ciclos en una instrucción (número de ejemplo)
            //y otro hilo corre una instrucción por 20, y el otro por 10. Entonces los dos primeros
            //hilos tiene que tiene que esperarse 20 y 30 respectivamente antes de seguir con la siguiente instrucción?
        }
    }


}

