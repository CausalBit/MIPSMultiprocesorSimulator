import java.io.IOException;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.lang.System;

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
    private CyclicBarrier barrier;
    private HashMap<String, Integer> registers;
    private Queue<Context> coreContexts;
    private String parentProcessorId;
    private int clock;
    private int myCoreNumber;
    private String myNameCache;
    public Core(String parentProcessorId, CyclicBarrier barrier,
                int clock, int quantum,
                int numberOfCoresSystem, Bus bus, int myCoreNumber, String myNameCache) {

        this.barrier = barrier;
        this.bus = bus;
        this.quantum = quantum;
        this.numberOfCoresSystem = numberOfCoresSystem;
        this.parentProcessorId = parentProcessorId;
        this.clock = clock;
        this.myCoreNumber = myCoreNumber;
        this.myNameCache = myNameCache;
        this.running = true;
        this.pc = 0;

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
        boolean currentProgramIsFinished = false;
        while (running) {
            /*fecth new and decodeAndExceute*/
           if (instructionDuration == 0) {
               java.lang.System.out.println("pc de inst: "+this.pc+", "+parentProcessorId+", core: "+myCoreNumber);
                String nameCache="";
                Instruction currentInstruction = new Instruction(this.pc, this.registers, bus, parentProcessorId,myNameCache);
                try {
                    instructionDuration += currentInstruction.fetchInstruction(this.pc);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                pc = pc + 4;
                instructionDuration += currentInstruction.decodeAndExecute();//retorna una duracion
               currentProgramIsFinished = currentInstruction.programIsFinished();
           }
                //Cuando termine de correr una instrucción, o parte de esta, se incrementa el reloj.
                clock++;
                currentProgramDuration++;
                instructionDuration--;
            //La barrera va aquí para que sea necesario correr otro ciclo si ya no hay quantum.
            try {
             this.barrier.await();
            }
            catch ( Exception ex) {

            }
            this.barrier.reset();

            //La verificación del cuantum está aquí.
           if (instructionDuration == 0 && currentProgramDuration >= quantum) {//aqui va quauntum TODO
               java.lang.System.out.println("------------quantum acabado------------");
                if(! currentProgramIsFinished){//Store the current context if program is not finished (current instruction is not FIN).
                    java.lang.System.out.println("------------no acabo programa------------");
                    coreContexts.add(new Context(registers,pc));
                }
                if(coreContexts.isEmpty()){//If the next context is null, then the core shuts down.
                    java.lang.System.out.println("------------context vacio------------");
                    numberOfCoresSystem --;//The shut down implies decreasing the numberOfCoresSystem by one. :D
                    running=false;
                }else{  //Take the next context from the queue.
                    Context initialContext = coreContexts.poll();
                    currentProgramIsFinished = false; //update state of program
                    this.pc = initialContext.getPc();
                    this.registers = initialContext.getRegisters();
                    currentProgramDuration = 0;
                }
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