import java.util.Arrays;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.CyclicBarrier;
import java.lang.System;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by irvin on 11/12/17.
 * modified by tonyViquez
 */
public class Core implements Runnable {
    private Bus bus;
    private int quantum;
    private int pc;
    private AtomicInteger numberActiveCores;
    private CyclicBarrier barrier;
    private HashMap<String, Integer> registers;
    private Queue<Context> coreContexts;
    private String parentProcessorId;
    private int clock;
    private int myCoreNumber;
    private String myNameCache;
    private Instruction instruction;
    private String myDataCacheName;
    private String programFileId;
    private int programTime;


    private boolean currentProgramIsFinished;
    private int currentProgramDuration = 0;
    private int instructionDuration = 0;
    private boolean coreFinished = false;

    public Core(String parentProcessorId,
                CyclicBarrier barrier,
                int clock, int quantum,
                AtomicInteger numberActiveCores,
                Bus bus,
                int myCoreNumber,
                String myNameCache, String myDataCacheName) {

        this.barrier = barrier;
        this.bus = bus;
        this.quantum = quantum;
        this.numberActiveCores = numberActiveCores;
        this.parentProcessorId = parentProcessorId;
        this.clock = clock;
        this.myCoreNumber = myCoreNumber;
        this.myNameCache = myNameCache;
        this.pc = 0;
        this.myDataCacheName = myDataCacheName;

        this.currentProgramIsFinished = false;
        this.currentProgramDuration = 0;
        this.instructionDuration = 0;
        this.coreFinished = false;
        this.programFileId = "";
        this.programTime = 0;


        registers = new HashMap<String, Integer>();
        registers.put("0", Constant.REGISTER_ZERO);
        for (int i = 1; i < Constant.NUMBER_OF_REGISTERS_PER_CORE; i++) {
            registers.put(Integer.toString(i), Constant.REGISTER_NULL_VALUE);
        }

        setInitialContext();

        this.instruction = new Instruction( this.registers, bus, parentProcessorId,myNameCache, myDataCacheName, myCoreNumber);


    }

    public void run() {

        while (numberActiveCores.get()>0) {

            if(!coreFinished) {
                /*fecth new and decodeAndExceute*/

               if (instructionDuration == 0) {

                   //java.lang.System.out.println("pc de inst: "+this.pc+", "+parentProcessorId+", core: "+myCoreNumber);

                   instruction.setPC(pc);
                    try {
                        int[] currentIntruction = instruction.fetchInstruction();

                        instruction.decodeAndExecute(currentIntruction);
                        instructionDuration = instruction.getDuration();

                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                    pc = instruction.getPC();
                    currentProgramIsFinished = instruction.programIsFinished();

               }
                //Cuando termine de correr una instrucción, o parte de esta, se incrementa el reloj.

                clock++;
                instructionDuration--;

                if(instructionDuration == 0){ //Cuando un instruccion termine en el ciclo acutal, entonces agregar a duracion de tipo quantum
                    currentProgramDuration++;
                }

                //La verificación del cuantum está aquí.
                if (instructionDuration == 0 && currentProgramDuration >= quantum || currentProgramIsFinished) {//aqui va quauntum TODO
                    //java.lang.System.out.println("------------quantum acabado------------");
                    if(!currentProgramIsFinished){//Store the current context if program is not finished (current instruction is not FIN).
                       // java.lang.System.out.println("------------no acabo programa------------ del core :"+myCoreNumber);
                        Context contextToSave = new Context(registers,pc);
                        contextToSave.setIdHilillo(this.programFileId);
                        contextToSave.setProgramTime(this.programTime+currentProgramDuration);
                        coreContexts.add(contextToSave);
                    }else{
                        //Imprimir registros
                        int lastUpdateOneProgramDuration = this.programTime+currentProgramDuration;
                        java.lang.System.out.println("ProgramFile (Hilillo) finished: "+this.programFileId+" |   Global Clock (cycles): "+this.clock+" |   Program Time "+lastUpdateOneProgramDuration);
                        String finalRegisters = "";
                        for(HashMap.Entry<String, Integer> entry: registers.entrySet()){
                            if(entry.getValue() != -1){
                                finalRegisters+=" R"+entry.getKey()+": "+entry.getValue()+", ";
                            }

                        }
                        System.out.println("Core: "+myCoreNumber+" |  Registers :"+finalRegisters+"\n");
                    }

                    if(coreContexts.isEmpty()){//If the next context is null, then the core shuts down.
                        java.lang.System.out.println("------------All programs finished for core: "+myCoreNumber+"------------\n\n");
                        numberActiveCores.decrementAndGet();//The shut down implies decreasing the numberActiveCores by one. :D
                        coreFinished = true;
                    }else{  //Take the next context from the queue.
                        Context initialContext = coreContexts.poll();
                        currentProgramIsFinished = false; //update state of program
                        this.pc = initialContext.getPc();
                        this.registers = initialContext.getRegisters();
                        this.programFileId = initialContext.getIdHilillo();
                        this.programTime = initialContext.getProgramTime();
                        instruction.setRegisters(this.registers);
                        currentProgramDuration = 0;
                    }
                }
            }

            //Barrera para todos los threads.
            try {
                this.barrier.await();
            } catch (Exception ex) {

            }


        }
    }


    public void setInitialContext() {
        //Set up the fist conte
        this.coreContexts = bus.getProcessor(parentProcessorId).getCoreContext();
        if(coreContexts.isEmpty()){// if only exit
            numberActiveCores.decrementAndGet();
            coreFinished=true;
        }else {
            /*circle queue, poll head of queue then insert again, this insert se realizara al final*/
            Context initialContext = coreContexts.poll();
            this.pc = initialContext.getPc();
            this.registers = initialContext.getRegisters();
            this.programFileId = initialContext.getIdHilillo();
            this.programTime = 0;
        }
    }



}