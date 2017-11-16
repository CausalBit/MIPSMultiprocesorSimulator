import java.util.ArrayList;
import java.util.HashMap;
import java.lang.System;

/**
 * Created by irvin on 11/13/17.
 * modified by tonyViquez
 */
public class Instruction {
    int pc;
    private int block;
    private int word;
    private int positionInCache;
    private HashMap<String, Integer> registers;
    private Bus bus;
    private Cache myCacheInst;
    private PhysicalMemory memLocal;
    private boolean progIsFinished;
    private int duration;
//
    private String cacheIns;
    /*
    constructor of class
    */
    public Instruction( HashMap<String, Integer> registers, Bus bus, String proccesor , String cacheInst ) {
        this.pc = 0;
        this.registers = registers;
        this.bus = bus;
        this.myCacheInst = (bus.getProcessor(proccesor).getCaches().get(cacheInst));
        this.memLocal = (bus.getProcessor(proccesor).getLocalPhysicalMemory());
        this.cacheIns= cacheInst;
        this.duration = 0;
    }

    /**
     *
     * @return clock cycles that the instruction lasts
     */
    public void decodeAndExecute(int[] instruction){

        int codOP= instruction[0]; // the first position is the cod operation
        System.out.println(cacheIns+"  codop+"+codOP+"wordtoREad "+word);
        int reg1 = instruction[1];
        int reg2orRd = instruction[2];
        int RDorImmediate = instruction[3];
        //read instruction

        switch(codOP){
            case Constant.CODOP_DADDI:
                DADDI(reg2orRd,reg1,RDorImmediate);
                break;
            case Constant.CODOP_DADD:
                DADDI(RDorImmediate,reg1,reg2orRd);
                break;
            case Constant.CODOP_DSUB:
                DSUB(RDorImmediate,reg1,reg2orRd);
                break;
            case Constant.CODOP_DMUL:
                DMUL(RDorImmediate,reg1,reg2orRd);
                break;
            case Constant.CODOP_DDIV:
                DDIV(RDorImmediate,reg1,reg2orRd);
                break;
            case Constant.CODOP_BEQZ:
                BEQZ(reg1,RDorImmediate);

                break;
            case Constant.CODOP_BNEQZ:
                BNEQZ(reg1,RDorImmediate);
                break;
            case Constant.CODOP_JAL:
                JAL(RDorImmediate);
                break;
            case Constant.CODOP_JR:
                JR(reg1);
                break;
            case Constant.CODOP_LW:
                //
                break;
            case Constant.CODOP_SW:
                //
                break;
            case Constant.CODOP_FIN:
                //
                FIN();
                break;

        }
        //duration += Constant.DURATION_OF_INSTRUCTION_ALU;
        //calcular la cantidad de cicles que se llevo en la instrucción (aunque no haya terminaod).
    }

    /**
     * @return if the last instruction of program that was read is FIN is true
     */
    public boolean programIsFinished( ) {
        return progIsFinished;
    }
    /**
     * Find out if the instruction block is a hit or miss.
     * in case of hit:
     * calculate from the instruction number, the instruction memory block that you must load into the instruction cache
     * and load it, then calculate the word number to read for that block. remember that the instruction block has 16 words
     * then TODO
     * return duration
    * */
    public int[] fetchInstruction() throws Exception{
        progIsFinished = false;
        duration = 0;
        int block = memLocal.getLocalInstMemBlockNumber(pc);
        int word = getWordNumber(pc);/*word is instruction*/

        //find out if this a hit or miss
        boolean hit = isAHit(block);
        duration += Constant.ACCESS_TO_CACHE;
        if(!hit){
            duration += Constant.LOCAL_MEMORY_ACCESS;
            //TODO usar bus para tener acceso a
            int realBlockNumber = block;
            int blockOfMem[] = memLocal.readBlockInstructionMemory(block); //cargar bloque

            String s= "";
            for(int t = 0; t <16;t++){
                s+=" "+blockOfMem[t];
            }
            System.out.println("bloque de metodo:"+block+"\tinst :: "+cacheIns+"\t"+s);

            //copiar de 4 en 4
            myCacheInst.setBlockNumberInCachePosition(block);
            myCacheInst.setBlockState(block, Constant.C);

            for(int i = 0; i <Constant.INSTRUCTION_CACHE_REAL_WORD_SIZE ; i++){
                int numberWord= Constant.WORDS_IN_BLOCK*i;
                int wordDatatoWrite[] = new int[Constant.WORDS_IN_BLOCK];
                java.lang.System.arraycopy(blockOfMem, numberWord,  wordDatatoWrite, 0, 4);//sacar una palabra del bloque
                myCacheInst.writeWordOnCache (block, i, wordDatatoWrite);//escribir palabra
            }
        }
        pc += 4;
        return myCacheInst.readWordFromCache(block, word);
    }

    /**
     * return true if is a hit
     * @param blockNumber number of block of the instruction that the Core wants to read
     * @return hit
     */
    private boolean isAHit(int blockNumber){
        boolean hit = true;
        if(blockNumber != myCacheInst.getBlockNumberInCachePosition(blockNumber)){
            hit = false;
        }
        return hit;
    }

    /**
     * calc instruction memory block that you must load into the instruction cache
     * @param address is the instruction number that the Core wants to read
     */
    public int getBlockNumber(int address){
        return (int) Math.floor( address / 16 );
    }


    /**
     * calc word number in a specific block that you must read
     * @param address is the instruction number that the Core wants to read
     */
    public int getWordNumber(int address){
        return ((address%16)/4);
    }

    /**
     * Find out  the position in cache block for the specific instruction block
     * @param address is the instruction number that the Core wants to read
     * @return
     */
    public int getPositionInCache(int address){
        int blockNumber = getBlockNumber(address);
        return blockNumber%4;
    }

    //---------------------------------------------------------------------------------------------instruction type ALU-------------------------------------------------------------------------------------//

    private void  DADDI(int regTarget, int regSource ,int num) {
        registers.put(Integer.toString( regTarget) , registers.get(Integer.toString(regSource))+num);
        duration += Constant.DURATION_OF_INSTRUCTION_ALU;
    }

    private void DADD ( int regTarget, int regSource1, int regSource2){
        registers.put(Integer.toString(regTarget), registers.get(Integer.toString(regSource1))+registers.get(Integer.toString(regSource2)));
        duration += Constant.DURATION_OF_INSTRUCTION_ALU;
    }


    private void DSUB( int regTarget, int regSource1, int regSource2){
        registers.put(Integer.toString(regTarget), (registers.get(Integer.toString(regSource1))-registers.get(Integer.toString(regSource2))) );
        duration += Constant.DURATION_OF_INSTRUCTION_ALU;
    }


    public void DMUL( int regTarget, int regSource1, int regSource2){
        registers.put(Integer.toString(regTarget) ,( registers.get(Integer.toString(regSource1))*registers.get(Integer.toString(regSource2))));
        duration += Constant.DURATION_OF_INSTRUCTION_ALU;
    }

    private void DDIV( int regTarget, int regSource1, int regSource2){
        registers.put(Integer.toString(regTarget) ,( registers.get(Integer.toString(regSource1)) / registers.get(Integer.toString(regSource2))));
        duration += Constant.DURATION_OF_INSTRUCTION_ALU;
    }


    private void BEQZ ( int regTarget, int tag){
        if(registers.get(Integer.toString(regTarget) ) == 0 ){
            pc += (tag)*4;
        }
        duration += Constant.DURATION_OF_INSTRUCTION_ALU; //TODO revisar con la profe sobre.
    }


    private void BNEQZ( int regTarget,  int tag){
        if (registers.get(Integer.toString(regTarget)) != 0){
           pc+= (tag)*4;
        }
        duration += Constant.DURATION_OF_INSTRUCTION_ALU; //TODO revisar con la profe sobre.
    }


    private void JAL(int immediate){
        registers.put(Integer.toString(31), pc);
        pc +=immediate;
        duration += Constant.DURATION_OF_INSTRUCTION_ALU; //TODO revisar con la profe sobre.
    }


    private void JR (int regSource){
        pc= registers.get(Integer.toString(regSource));
        duration += Constant.DURATION_OF_INSTRUCTION_ALU; //TODO revisar con la profe sobre.
    }


    private void FIN(){
        this.progIsFinished = true;
        java.lang.System.out.println("terminado");
    }

    public void setPC(int pc){
        this.pc = pc;
    }

    public int getPC(){
        return this.pc;
    }


    public int getDuration(){
        return this.duration;
    }
}
