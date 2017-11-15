import java.io.IOException;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.Semaphore;

/**
 * Created by irvin on 11/12/17.
 * modified by tonyViquez
 */
public class Core implements Runnable {
    private Bus bus;
    private int quantum;
    private int pc;
    private int numberOfCoresSystem;
    private boolean running;
    private Semaphore barrier;
    private HashMap<String, Integer> registers;
    private Queue<Context> coreContexts;
    private String parentProcessorId;
    private int clock;


    public Core(String parentProcessorId, Semaphore barrier,
                int clock, int quantum,
                int numberOfCoresSystem, Bus bus) {

        this.barrier = barrier;
        this.bus = bus;
        this.quantum = quantum;
        this.numberOfCoresSystem = numberOfCoresSystem;
        this.parentProcessorId = parentProcessorId;
        this.clock = clock;

        this.running = true;

        registers = new HashMap<String, Integer>();
        registers.put("0", Constant.REGISTER_ZERO);
        for (int i = 1; i < Constant.NUMBER_OF_REGISTERS_PER_CORE; i++) {
            registers.put(Integer.toString(i), Constant.REGISTER_NULL_VALUE);
        }


    }

    public void run() {

        setInitialContext();

        int currentProgramDuration = 0;
        int instructionDuration = 0; //Cuando finaliza una instrucción, esta se modifica.

        while (running) {

            String nameCache="";
            Instruction currentInstruction = new Instruction(this.pc, this.registers, bus, parentProcessorId,nameCache);
            if (instructionDuration == 0) {

                //Cuando se hace fetch, intructionIsFinished es = false;
                //TODO averiguar nombre de cache;
                //TODO actualizar el pc a +4 se hace en el fetch
                try {
                    instructionDuration += currentInstruction.fetchInstruction(this.pc);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                pc = pc + 4;
            }

            if (instructionDuration == 0) {

                //Instrucción corre aquí.
                instructionDuration = currentInstruction.decodeAndExecute();//retorna una duracion

            }

            //Cuando termine de correr una instrucción, o parte de esta, se incrementa el reloj.
            clock++;
            currentProgramDuration++;
            instructionDuration--;

            //La barrera va aquí para que sea necesario correr otro ciclo si ya no hay quantum.
            if (barrier.getQueueLength() == numberOfCoresSystem - 1) {
                //es el último en llegar a la barrera, liberar todos.
                barrier.release(numberOfCoresSystem - 1);
            } else {
                try {
                    barrier.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //La verificación del cuantum está aquí.
            if (instructionDuration == 0 && currentProgramDuration >= quantum) {
                //Store the current context if program is not finished (current instruction is not FIN).
                //Take the next context from the queue.
                //If the next context is null, then the core shuts down.
                //The shut down implies decreasing the numberOfCoresSystem by one. :D
                coreContexts.add(new Context(registers,pc));
            }
        }
    }


    public void setInitialContext() {

        //Set up the fist conte
        this.coreContexts = bus.getProcessor(parentProcessorId).getCoreContext();

        /*circle queue, poll head of queue then insert again, this insert se realizara al final*/
        Context initialContext = coreContexts.poll();
        this.pc = initialContext.getPc();
        this.registers = initialContext.getRegisters();

    }

}