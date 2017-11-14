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



    public Core(Semaphore barrier, int clock, int pc, int quantum, int numberOfCoresSystem, Queue<Context> coreContexts) {
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

        this.coreContexts = coreContexts;

        //TODO Ademas de tener copias de la cola de contexto, aquí se carga
        /*circle queue, poll head of queue then insert again, this insert se realizara al final*/
        Context temp = coreContexts.poll(0);
        this.pc= temp.getPc;
        this.registers = temp.getRegisters;
        //TODO el contexto inicial desde esa cola.

    }

    public void run(){
        int currentProgramDuration = 0;
        int instructionDuration = 0; //Cuando finaliza una instrucción, esta se modifica.

        while(running){


            if(instructionDuration == 0) {

                //Cuando se hace fetch, intructionIsFinished se = false;
                //El PC indica si se avanza o no!
                //Se hace usando la clase Instruction
                //instructionDuration = lo que dura el fetch.
                //Fetch recibe el Pc que es la instruccion que se quiere hacer 
                //TODO hacer fetch en instruction class , este retorna duracion para restarselo a quantum, el fecth en caso de abortar modifica el pc en -4
            }

            if(instructionDuration == 0) {

                //Instrucción corre aquí.
                instructionDuration = 40; //Lo que retorna la instucion.
            }

            //Cuando termine de correr una instrucción, o parte de esta, se incrementa el reloj.
            clock++;
            currentProgramDuration++;
            instructionDuration--;

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
            if(instructionDuration == 0 && currentProgramDuration >= quantum){
                //Store the current context if program is not finished (current instruction is not FIN).
                //Take the next context from the queue.
                //If the next context is null, then the core shuts down.
                //The shut down implies decreasing the numberOfCoresSystem by one. :D
                //TODO add to queue context ie new context because before ha sido eliminada
            }
        }
    }


}
