import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by irvin on 11/13/17.
 * modified by tonyViquez
 */
public class Instruction {
    int pc;
    private int block;
    private int word;
    int positionInCache;
    HashMap<String, Integer> registers;
    Bus bus;
    Cache myCacheInst;
    PhysicalMemory memLocal;

    /*
    constructor of class
    */
    public Instruction(int pc, HashMap<String, Integer> registers, Bus bus, String proccesor , String cacheInst) {
        this.pc = pc;
        this.registers = registers;
        this.bus = bus;
        this.myCacheInst = (bus.getProcessor(proccesor).getCaches().get(cacheInst));
        this.memLocal = (bus.getProcessor(proccesor).getLocalPhysicalMemory());
    }

    /**
     *
     * @return clock cycles that the instruction lasts
     */
    public int decodeAndExecute(){
        int duration = 0;
        ArrayList<int[]> blockOfCache = myCacheInst.getCorrespondingColumn(positionInCache);
        int[] wordOfCacheBlock = blockOfCache.get(this.word);
        int codOP= wordOfCacheBlock[0]; // the first position is the cod operation
        int reg1 = wordOfCacheBlock[1];
        int reg2orRd = wordOfCacheBlock[2];
        int RDorImmediate = wordOfCacheBlock[3];
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
                break;

        }
        duration += Constant.DURATION_OF_INSTRUCTION_ALU;
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
    public int fetchInstruction(int address) throws Exception{
        int duration = 0;
        this.block = getBlockNumber(address);
        this.word = getWordNumber(address);
        this.positionInCache = getPositionInCache(address);

        //find out if this a hit or miss
        boolean hit = isAHit(block, positionInCache);
        duration += Constant.ACCESS_TO_CACHE;
        if(!hit){
            duration += Constant.LOCAL_MEMORY_ACCESS;
            int [] blockOfMem = memLocal.readInstructionMemory(block); //cargar de mem a cache
            //copiar de 4 en 4
            for(int i = 0; i <Constant.INSTRUCTION_CACHE_REAL_WORD_SIZE ; i++){
                int count= 4*i;
                int [] wordData = {blockOfMem[count+0],blockOfMem[count+1],blockOfMem[count+2],blockOfMem[count+3] };
                myCacheInst.writeWordOnCache (positionInCache, i, wordData);
            }
        }
        return duration;
    }

    /**
     * return true if is a hit
     * @param blockNumber number of block of the instruction that the Core wants to read
     * @param positionInCache cache position from where the instruction should read
     * @return hit
     */
    private boolean isAHit(int blockNumber, int positionInCache){
        boolean hit = true;
        if(blockNumber != myCacheInst.getBlockState(positionInCache)){
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
        int wordPlacement = (int) (address/16 - Math.floor(address/16));
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

    //---------------------------------------------------------------------------------------------Load (LW)-------------------------------------------------------------------------------------//

    /*private int load(int address, int coreID, int procesorID){
        int blockNumber = this.getBlockNumber(address);
        int wordNumber = this.getWordNumber(address);
        List<String> lockedList = new LinkedList<String>();

        switch (coreID){
            case 0:
                if(bus.request(Constant.DATA_CACHE_0)){
                    lockedList.add(Constant.DATA_CACHE_0);
                }
                break;
            case 1:
                if(bus.request(Constant.DATA_CACHE_1)){
                    lockedList.add(Constant.DATA_CACHE_1);
                }
                break;
            case 2:
                if(bus.request(Constant.DATA_CACHE_2)){
                    lockedList.add(Constant.DATA_CACHE_2);
                }
                break;
        }

        int positionInCache = getPositionInCache(address);

        if(lockedList.size() == 1){
            if(isAHit(blockNumber, positionInCache)){
                //Agregar a registros
                bus.setFree(lockedList.remove(0));
            }else{
                int victimDirectory = -1;
                if(cacheData.get(coreID).getBlockState(positionInCache) != Constant.I){
                    for(int i = 0; i < 2; i++){
                        if(this.directories.get(i).getExistenceInProcesor(blockNumber, procesorID)){
                            victimDirectory = i;
                            break;
                        }
                    }

                    switch (victimDirectory){
                        case 0:
                            if(bus.request(Constant.DIRECTORY_0)){
                                lockedList.add(Constant.DIRECTORY_0);
                            }
                            break;
                        case 1:
                            if(bus.request(Constant.DIRECTORY_1)){
                                lockedList.add(Constant.DIRECTORY_1);
                            }
                            break;
                    }

                    if(lockedList.size() != 2){
                        bus.setFree(lockedList.remove(1));
                        bus.setFree(lockedList.remove(0));
                    }else{
                        if(this.directories.get(victimDirectory).getBlockState(blockNumber) == Constant.M){
                            //Guardar bloque víctima en memoria
                            this.directories.get(victimDirectory).setBlockState(blockNumber, Constant.U);
                            //Cambiar los bits a 0 todos
                        }else{
                            //Cabiar los bits y a partir de ello cambiar el estado
                        }
                        bus.setFree(lockedList.remove(1));
                        //Obtener caché en la cual se encuentra el bloque e invalidarlo en ella
                    }
                }
                int sourceDirectory = -1;
                for(int i = 0; i < 2; i++){
                    if(this.directories.get(i).getExistenceInProcesor(blockNumber, procesorID)){
                        sourceDirectory = i;
                        break;
                    }
                }

                switch (sourceDirectory){
                    case 0:
                        if(bus.request(Constant.DIRECTORY_0)){
                            lockedList.add(Constant.DIRECTORY_0);
                        }
                        break;
                    case 1:
                        if(bus.request(Constant.DIRECTORY_1)){
                            lockedList.add(Constant.DIRECTORY_1);
                        }
                        break;
                }

                if(lockedList.size() != 2){
                    bus.setFree(lockedList.remove(1));
                    bus.setFree(lockedList.remove(0));
                }else{
                    if(this.directories.get(sourceDirectory).getBlockState(blockNumber) == Constant.M){
                        //Bloquear bus a memoria y almacenar el bloque modificado
                        cacheData.get(coreID).writeWordOnCache(blockNumber, wordNumber, );
                    }
                }
            }
        }
    }*/

    //---------------------------------------------------------------------------------------------instruction type ALU-------------------------------------------------------------------------------------//

    private void  DADDI(int regTarget, int regSource ,int num) {
        registers.put(Integer.toString( regTarget) , registers.get(regSource)+num);
        //pc=pc+1;
        //return 1; //TODO hacer que retorne enteros de cantidades de ciclos que se tomó.
    }

    private void DADD ( int regTarget, int regSource1, int regSource2){
        registers.put(Integer.toString(regTarget), registers.get(regSource1)+registers.get(regSource2));
        //pc=pc+1;
    }


    private void DSUB( int regTarget, int regSource1, int regSource2){
        registers.put(Integer.toString(regTarget), (registers.get(regSource1)-registers.get(regSource2)) );
        //pc=pc+1;
    }


    public void DMUL( int regTarget, int regSource1, int regSource2){
        registers.put(Integer.toString(regTarget) ,( registers.get(regSource1)*registers.get(regSource2)));
     //   pc++;
    }

    private void DDIV( int regTarget, int regSource1, int regSource2){
        registers.put(Integer.toString(regTarget) ,( registers.get(regSource1) / registers.get(regSource2)));
     //   pc++;
    }


    private void BEQZ ( int regTarget, int etiqueta){
        if(registers.get(regTarget) == 0 ){
            pc= etiqueta;
        }else{
  //          pc++;
        }
    }


    private void BNEQZ ( int regTarget,  int etiqueta){
        if (registers.get(regTarget) != 0){
            pc= etiqueta;
        }else{
//            pc++;
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
