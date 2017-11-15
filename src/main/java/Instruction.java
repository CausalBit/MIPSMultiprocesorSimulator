import java.util.HashMap;

/**
 * Created by irvin on 11/13/17.
 * modified by tonyViquez
 */
public class Instruction {
    int pc;
    private int block;
    private int word;
    int positionInCache
    HashMap<String, Integer> registers;
    Cache cacheInst;
    List <Cache> cacheData;
    Buses buses;
    PhysicalMemory memLocal;
    PhysicalMemory memExternal;
    //TODO


    /*
    constructor of class
    */
    public Instruction(int pc, HashMap<String, Integer> registers, Buses buses, Cache inst, List <Cache> cacheData, PhysicalMemory memLocal, PhysicalMemory memExternal) {
        this.pc = pc;
        this.registers = registers;
        this.cacheData=cacheData;
        this.cacheInst= cacheInst;
        this.buses = buses;
        this.memExternal= memExternal;
        this.memLocal=memLocal;
    }

    /**
     *
     * @return clock cycles that the instruction lasts
     */
    public int decodeAndExecute(){
        int duration = 0;
        ArrayList<int[]> blockOfCache = cacheInst.getCorrespondingColumn(positionInCache);
        int[] wordOfCacheBlock = blockOfCache.get(this.word);
        int codOp= wordOfCacheBlock[0]; // the first position is the cod operation
        int reg1 = wordOfCacheBlock[1];
        int reg2orRd = wordOfCacheBlock[2];
        int RDorImmediate = wordOfCacheBlock[3];
        //read instruction

        switch(codOP){
            case 1: codOP = Constant.CODOP_DADDI;
                DADDI(reg2orRd,reg1,immediate);
                break;
            case 2: codOP = Constant.CODOP_DADD;
                DADDI(RDorImmediate,reg1,reg2orRd);
                break;
            case 3: codOP = Constant.CODOP_DSUB;
                DSUB(RDorImmediate,reg1,reg2orRd);
                break;
            case 4: codOP = Constant.CODOP_DMUL;
                DMUL(RDorImmediate,reg1,reg2orRd);
                break;
            case 5: codOP = Constant.CODOP_DDIV;
                DDIV(RDorImmediate,reg1,reg2orRd);
                break;
            case 6: codOP = Constant.CODOP_BEQZ;
                BEQZ(reg1,immediate);
                break;
            case 7: codOP = Constant.CODOP_BNEQZ;
                BNEQZ(reg1,immediate);
                break;
            case 8: codOP = Constant.CODOP_JAL;
                JAL(immediate);
                break;
            case 9: codOP = Constant.CODOP_JR;
                JR(reg1);
                break;
            case 10: codOP = Constant.CODOP_LW;
                //
                break;
            case 11: codOP = Constant.CODOP_SW;
                //
                break;
            case 12: codOP = Constant.CODOP_FIN;
                //
                break;

        }
        //switch.
        //calcular la cantidad de cicles que se llevo en la instrucción (aunque no haya terminaod).
        return duration;
    }

    /*
    */

    /**
     * Find out if the instruction block is a hit or miss.
     * in case of hit:
     * calculate from the instruction number, the instruction memory block that you must load into the instruction cache
     * and load it, then calculate the word number to read for that block. remember that the instruction block has 16 words
     * then TODO
     * return duration
     * @param address is the instruction number that the Core wants to read
    * */
    private int fetchInstruction(int address){
        int duration = 0;
        this.block = getBlockNumber(address);
        this.word = getWordNumber(address);
        this.positionInCache = getPositionInCache(address);
        int [] blockOfMem;
        //find out if this a hit or miss
        bool hit = isAHit(block, positionInCache);
        duration+=//TODO
        if(!hit){
            duration+=//TODO hacer sumas correctas
            blockofMem = memLocal.readInstructionMemory(block); //TODO cargar de mem a cache
            //copiar de 4 en 4
            for(int i = 0; i <Constant.INSTRUCTION_CACHE_REAL_WORD_SIZE ; i++){
                int oount= 4*i;
                int [] wordData;
                wordData = new int {blockOfMem[count+0],blockOfMem[count+1],blockOfMem[count+2],blockOfMem[count+3] }
                cacheInst.writeWordOnCache (positionInCache, i, wordData);
            }
        }
        //ejecutar instruccion
        return duration;
    }

    /**
     * return true if is a hit
     * @param blockNumber number of block of the instruction that the Core wants to read
     * @param positionInCache cache position from where the instruction should read
     * @return
     */
    private bool isAHit(int blockNumber, int positionInCache){
        bool hit = true;
        if(blockNumber != cacheInst.getBlockState(positionInCache)){
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
        int wordPlacement = address/16 - Math.floor(address/16);
        return 4*(wordPlacement/16);
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

    private int  DADDI(int regTarget, int regSource ,int num) {
        registers.put(Integer.toString( regTarget) , registers.get(regSource)+num);
        pc=pc+1; //TODO la pc se actualiza a la hora de leer insturcción en la clase core, para
        //TODO abortar entonces se quita -4.

        return 1; //TODO hacer que retorne enteros de cantidades de ciclos que se tomó.
    }

    private void DADD ( int regTarget, int regSource1, int regSource2){
        registers.put(Integer.toString(regTarget), registers.get(regSource1)+registers.get(regSource2));
        pc=pc+1;
    }


    private void DSUB( int regTarget, int regSource1, int regSource2){
        registers.put(Integer.toString(regTarget), (registers.get(regSource1)-registers.get(regSource2)) );
        pc=pc+1;
    }


    public void DMUL( int regTarget, int regSource1, int regSource2){
        registers.put(Integer.toString(regTarget) ,( registers.get(regSource1)*registers.get(regSource2)));
        pc++;
    }

    private void DDIV( int regTarget, int regSource1, int regSource2){
        registers.put(Integer.toString(regTarget) ,( registers.get(regSource1) / registers.get(regSource2)));
        pc++;
    }


    private void BEQZ ( int regTarget, int etiqueta){
        if(registers.get(regTarget) == 0 ){
            pc= etiqueta;
        }else{
            pc++;
        }
    }


    private void BNEQZ ( int regTarget,  int etiqueta){
        if (registers.get(regTarget) != 0){
            pc= etiqueta;
        }else{
            pc++;
        }

    }


    private void JAL(int etiqueta){
        registers.put(Integer.toString(31), pc+1);
        pc= etiqueta;
    }


    private void JR (int regSource){
        pc= registers.get(regSource);
    }


    private void FIN(){
        //saveContext();
        //running = false;
    }
}
