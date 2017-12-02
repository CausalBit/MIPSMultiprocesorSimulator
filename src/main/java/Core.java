import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.lang.System;
import java.util.concurrent.atomic.AtomicBoolean;
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
    private AtomicInteger waitingCores;
    private HashMap<String, Integer> registers;
    private ConcurrentLinkedQueue<Context> coreContexts;
    private String parentProcessorId;
    private int clock;
    private int myCoreNumber;
    private String myNameCache;
    private Instruction instruction;
    private String myDataCacheName;
    private String programFileId;
    private int programTime;
    private int programInitTime;
    private boolean master;


    private boolean currentProgramIsFinished;
    private int currentQuantumDuration = 0;
    private int instructionDuration = 0;
    private boolean coreFinished = false;

    public Core(String parentProcessorId,
                int clock, int quantum,
                AtomicInteger numberActiveCores,
                AtomicInteger waitingCores,
                Bus bus,
                int myCoreNumber,
                String myNameCache, String myDataCacheName, boolean master) {


        this.bus = bus;
        this.quantum = quantum;
        this.numberActiveCores = numberActiveCores;
        this.parentProcessorId = parentProcessorId;
        this.clock = clock;
        this.myCoreNumber = myCoreNumber;
        this.myNameCache = myNameCache;
        this.pc = 0;
        this.myDataCacheName = myDataCacheName;
        this.waitingCores = waitingCores;

        this.currentProgramIsFinished = false;
        this.currentQuantumDuration = 0;
        this.instructionDuration = 0;
        this.coreFinished = false;
        this.programFileId = "";
        this.programTime = 0;
        this.master = master;


        registers = new HashMap<String, Integer>();
        registers.put("0", Constant.REGISTER_ZERO);
        for (int i = 1; i < Constant.NUMBER_OF_REGISTERS_PER_CORE; i++) {
            registers.put(Integer.toString(i), Constant.REGISTER_NULL_VALUE);
        }

        setInitialContext();

        this.instruction = new Instruction( this.registers, bus, parentProcessorId,myNameCache, myCoreNumber);


    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (!coreFinished) {

               touchBarrier();
                /*fetch new and decodeAndExceute*/

               if(instructionDuration == 0){
                   instruction.setPC(pc);
                    try {
                        int[] currentIntruction = instruction.fetchInstruction();//Get Instruction
                        instruction.decodeAndExecute(currentIntruction);
                        instructionDuration = instruction.getDuration();
                       // System.out.println("DURATION: "+instructionDuration);
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                    pc = instruction.getPC();
                    currentProgramIsFinished = instruction.programIsFinished();
               }
                //Cuando termine de correr una instrucción, o parte de esta, se incrementa el reloj.
                clock++;
                this.programTime++;
                instructionDuration--;
                if(instructionDuration == 0){ //Cuando un instruccion termine en el ciclo acutal, entonces agregar a duracion de tipo quantum
                    currentQuantumDuration++;
                }

                if(master){
                    System.out.println("\n\n Presione cualquier número > 0 para la siguiente instrucción\n o presione 0 para salir rápido: \n\n");
                    int modeExecution = scanner.nextInt();
                    if(modeExecution==0){
                        master = false;
                    }
                    printGuts();
                }

                //La verificación del cuantum está aquí.
                if (instructionDuration == 0 && currentQuantumDuration >= quantum || currentProgramIsFinished) {//aqui va quauntum TODO
                    //java.lang.Simulation.out.println("------------quantum acabado------------");
                    if(!currentProgramIsFinished){//Store the current context if program is not finished (current instruction is not FIN).
                        Context contextToSave = new Context(registers,pc);
                        contextToSave.setIdHilillo(this.programFileId);
                        contextToSave.setProgramTime(this.programTime);
                        contextToSave.setInitTime(this.programInitTime);
                        coreContexts.add(contextToSave);
                    }else{
                        //Imprimir registros
                        //int lastUpdateOneProgramDuration = this.programTime+currentQuantumDuration;
                        java.lang.System.out.println("\nProgramFile (Hilillo): "+this.programFileId+
                                " finished in Prcessor: "+parentProcessorId+" |     Initial Time: "+this.programInitTime+" |    Final Time: "+this.clock+
                                " |     Program Time "+this.programTime);

                        String finalRegisters = "";
                        for(HashMap.Entry<String, Integer> entry: registers.entrySet()){
                            //if(entry.getValue() != -1){
                                finalRegisters+=" R"+entry.getKey()+": "+entry.getValue()+", ";
                           // }

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
                        java.lang.System.out.println("Running core "+myCoreNumber+" now switching to: "+this.programFileId);
                        this.programTime = initialContext.getProgramTime();
                        this.programInitTime = initialContext.getInitTime() < 0? this.clock: initialContext.getInitTime();
                        instruction.setRegisters(this.registers);
                        currentQuantumDuration = 0;
                    }
                }


        }
    }


    public void setInitialContext() {
        //Set up the fist conte
        this.coreContexts = bus.getProcessor(parentProcessorId).getCoreContext();
        if(coreContexts.isEmpty()){// if none, then exit
            numberActiveCores.decrementAndGet();
            coreFinished=true;
        }else {
            /*circle queue, poll head of queue then insert again, this insert se realizara al final*/
            Context initialContext = coreContexts.poll();
            this.pc = initialContext.getPc();
            this.registers = initialContext.getRegisters();
            this.programFileId = initialContext.getIdHilillo();
            java.lang.System.out.println("Running core "+myCoreNumber+" with: "+this.programFileId);
            this.programTime = 0;
            this.programInitTime = 0;

        }
    }

    public void touchBarrier(){
        waitingCores.incrementAndGet();
        while(waitingCores.get() < numberActiveCores.get() && numberActiveCores.get() > 1){
        }
        waitingCores.decrementAndGet();
    }

    public void printGuts(){

        if(master) {
            Processor p0 = bus.getProcessor(Constant.PROCESSOR_0);
            Processor p1 = bus.getProcessor(Constant.PROCESSOR_1);

            Directory directory0 = p0.getLocalDirectory();
            Directory directory1 = p1.getLocalDirectory();
            PhysicalMemory P0memory = p0.getLocalPhysicalMemory();
            PhysicalMemory P1memory = p1.getLocalPhysicalMemory();
            Map<String, Cache> cachep0 = p0.getCaches();
            Map<String, Cache> cachep1 = p1.getCaches();


            directory0.printDirectoryData();
            directory1.printDirectoryData();
            P0memory.printSharedMem();
            P1memory.printSharedMem();

            for (Map.Entry<String, Cache> e : cachep0.entrySet()) {
                if (e.getValue().cacheType == Constant.DATA_CACHE_TYPE) {
                    e.getValue().printCacheData();
                }
            }
            for (Map.Entry<String, Cache> e : cachep1.entrySet()) {
                if (e.getValue().cacheType == Constant.DATA_CACHE_TYPE) {
                    e.getValue().printCacheData();
                }
            }


        }
        String finalRegisters = "";
        for (HashMap.Entry<String, Integer> entry : registers.entrySet()) {
            //if(entry.getValue() != -1){
            finalRegisters += " R" + entry.getKey() + ": " + entry.getValue() + ", ";
            // }

        }
        System.out.println("\nBY STEP. Core: "+myCoreNumber+" in cycle: "+this.clock+" |  Registers :"+finalRegisters+"\n");
    }



}