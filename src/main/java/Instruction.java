import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.System;
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
    private int positionInCache;
    private HashMap<String, Integer> registers;
    private Bus bus;
    private Cache myCacheInst;
    private Cache myCacheData;
    private PhysicalMemory memLocal;
    private boolean progIsFinished;
    private List<Directory> directories;
//
    private String cacheIns;
    /*
    constructor of class
    */
    public Instruction(int pc, HashMap<String, Integer> registers, Bus bus, String proccesor , String cacheInst, String cacheData) {
        this.pc = pc;
        this.registers = registers;
        this.bus = bus;
        this.myCacheInst = (bus.getProcessor(proccesor).getCaches().get(cacheInst));
        this.memLocal = (bus.getProcessor(proccesor).getLocalPhysicalMemory());
        this.cacheIns= cacheInst;
        this.myCacheData = (bus.getProcessor(proccesor).getCaches().get(cacheData));
        this.directories = new ArrayList<Directory>();
        this.directories.add(bus.getProcessor(Constant.PROCESSOR_0).getDirectory());
        this.directories.add(bus.getProcessor(Constant.PROCESSOR_1).getDirectory());
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
        System.out.println(cacheIns+"  codop+"+codOP+"wordtoREad "+word);
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
                LW(reg1, reg2orRd, RDorImmediate);
                break;
            case Constant.CODOP_SW:
                //
                break;
            case Constant.CODOP_FIN:
                //
                FIN();
                break;

        }
        duration += Constant.DURATION_OF_INSTRUCTION_ALU;
        //switch.
        //calcular la cantidad de cicles que se llevo en la instrucción (aunque no haya terminaod).
        return duration;
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
     * @param address is the instruction number that the Core wants to read
    * */
    public int fetchInstruction(int address) throws Exception{
        int duration = 0;
        this.block = getBlockNumber(address);
        this.word = getWordNumber(address);/*word is instruction*/
        this.positionInCache = getPositionInCache(address);

        //find out if this a hit or miss
        boolean hit = isAHit(block, positionInCache);
        duration += Constant.ACCESS_TO_CACHE;
        if(!hit){
            duration += Constant.LOCAL_MEMORY_ACCESS;
            int blockOfMem[] = memLocal.readInstructionMemory(block+memLocal.localInstMemInitBlock); //cargar bloque
            String s= "";
            for(int t = 0; t <16;t++){
                s+=" "+blockOfMem[t];
            }
            System.out.println("bloque de metodo:"+block+"\tinst :: "+cacheIns+"\t"+s);
            //copiar de 4 en 4
            myCacheInst.setBlockNumberInCachePosition(block);
            for(int i = 0; i <Constant.INSTRUCTION_CACHE_REAL_WORD_SIZE ; i++){
                int count= 4*i;
                int wordDatatoWrite[] = new int[Constant.WORDS_IN_BLOCK];
                java.lang.System.arraycopy(blockOfMem, count,  wordDatatoWrite, 0, 4);//sacar una palabra del bloque
                myCacheInst.writeWordOnCache (block, i, wordDatatoWrite);//escribir palabra
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
        if(blockNumber != myCacheInst.getBlockNumberInCachePosition(positionInCache)){
            hit = false;
        }
        return hit;
    }

    private boolean isADataHit(int blockNumber, int positionInCache)
    {
        return blockNumber == myCacheData.getBlockNumberInCachePosition(positionInCache);
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
        //return 4*(wordPlacement/16);
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
        registers.put(Integer.toString( regTarget) , registers.get(""+regSource)+num);
    }

    private void DADD ( int regTarget, int regSource1, int regSource2){
        registers.put(Integer.toString(regTarget), registers.get(""+regSource1)+registers.get(""+regSource2));
    }


    private void DSUB( int regTarget, int regSource1, int regSource2){
        registers.put(Integer.toString(regTarget), (registers.get(""+regSource1)-registers.get(""+regSource2)) );
    }


    public void DMUL( int regTarget, int regSource1, int regSource2){
        registers.put(Integer.toString(regTarget) ,( registers.get(""+regSource1)*registers.get(""+regSource2)));
    }

    private void DDIV( int regTarget, int regSource1, int regSource2){
        registers.put(Integer.toString(regTarget) ,( registers.get(""+regSource1) / registers.get(""+regSource2)));
    }


    private void BEQZ ( int regTarget, int etiqueta){
        if(registers.get(""+regTarget) == 0 ){
            pc= etiqueta;
        }
    }


    private void BNEQZ ( int regTarget,  int etiqueta){
        if (registers.get(""+regTarget) != 0){
            pc= etiqueta;
        }

    }


    private void JAL(int etiqueta){
        registers.put(Integer.toString(31), pc+1);
        pc= etiqueta;
    }


    private void JR (int regSource){
        pc= registers.get(""+regSource);
    }


    private void FIN(){
        this.progIsFinished = true;
        java.lang.System.out.println("terminado");
    }

    //---------------------------------------------------------------------------------------------Load (LW)-------------------------------------------------------------------------------------//

    private void LW(int reg, int reg2, int address){
        int blockNumber = this.getBlockNumber(address);
        int wordNumber = this.getWordNumber(address);
        List<String> lockedList = new LinkedList<String>();

        //Se bloquea caché?
        if(cacheIns.equals(Constant.INSTRUCTIONS_CACHE_0)){
            if(bus.request(Constant.DATA_CACHE_0)){
                lockedList.add(Constant.DATA_CACHE_0);
            }
        }else if(cacheIns.equals(Constant.INSTRUCTIONS_CACHE_1)){
            if(bus.request(Constant.DATA_CACHE_1)){
                lockedList.add(Constant.DATA_CACHE_1);
            }
        }else{
            if(bus.request(Constant.INSTRUCTIONS_CACHE_2)){
                lockedList.add(Constant.DATA_CACHE_2);
            }
        }

        int positionInCache = getPositionInCache(address);

        if(lockedList.size() == 1){
            //Es miss?

            //No
            if(isADataHit(blockNumber, positionInCache)){
                //Agregar a registros
                bus.setFree(lockedList.remove(0));

            //Sí
            }else{
                int victimDirectory = -1;

                //El bloque en caché está en I?
                if(myCacheData.getBlockState(positionInCache) != Constant.I){

                    //No

                    //Averigua el directorio víctima
                    for(int i = 0; i < 2; i++){
                        if(this.directories.get(i).getExistenceInProcesor(blockNumber, i)){
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

                    //Se puede bloquear el directotio víctima?

                    //No
                    if(lockedList.size() != 2){
                        bus.setFree(lockedList.remove(1));
                        bus.setFree(lockedList.remove(0));

                    //Sí
                    }else{

                        //El bloque víctima en directorio está en M?

                        //Sí
                        if(this.directories.get(victimDirectory).getBlockState(blockNumber) == Constant.M){
                            //Guardar bloque víctima en memoria

                            //Cambiar el bloque a U y modificar los bits.
                            this.directories.get(victimDirectory).setBlockState(blockNumber, Constant.U);
                            //Cambiar los bits a 0 todos

                        //No
                        }else{
                            //Cambiar los bits y a partir de ello cambiar el estado
                        }
                        bus.setFree(lockedList.remove(1));
                        //Obtener caché en la cual se encuentra el bloque e invalidarlo en ella
                    }
                }
                int sourceDirectory = -1;
                for(int i = 0; i < 2; i++){
                    if(this.directories.get(i).getExistenceInProcesor(blockNumber, i)){
                        sourceDirectory = i;
                        break;
                    }
                }

                //Obtiene el directorio fuente
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

                //Se puede bloquear el directorio fuente?

                //No
                if(lockedList.size() != 2){
                    bus.setFree(lockedList.remove(1));
                    bus.setFree(lockedList.remove(0));

                //Sí
                }else{

                    //El bloque en el directorio está en M?

                    //Sí
                    if(this.directories.get(sourceDirectory).getBlockState(blockNumber) == Constant.M){
                        //Bloquear bus a memoria y almacenar el bloque modificado

                        int sourceCacheID = this.directories.get(sourceDirectory).getModifiedCache(blockNumber);
                        String processorName = "";
                        String sourceCacheName = "";
                        switch (sourceCacheID){
                            case 0:
                                if(bus.request(Constant.DATA_CACHE_0)){
                                    lockedList.add(Constant.DATA_CACHE_0);
                                    processorName = Constant.PROCESSOR_0;
                                    sourceCacheName = Constant.DATA_CACHE_0;
                                }
                                break;
                            case 1:
                                if(bus.request(Constant.DATA_CACHE_1)){
                                    lockedList.add(Constant.DATA_CACHE_1);
                                    processorName = Constant.PROCESSOR_0;
                                    sourceCacheName = Constant.DATA_CACHE_1;
                                }
                                break;
                            case 2:
                                if(bus.request(Constant.DATA_CACHE_2)){
                                    processorName = Constant.PROCESSOR_1;
                                    lockedList.add(Constant.DATA_CACHE_2);
                                    sourceCacheName = Constant.DATA_CACHE_2;
                                }
                                break;
                        }

                        Cache sourceCache = bus.getProcessor(processorName).getCaches().get(sourceCacheName);

                        int fullBlocktoWrite [] ;
                        fullBlocktoWrite = new int[Constant.DATA_EMPTY_BLOCK.length];

                        ArrayList<int[]> data = sourceCache.getCorrespondingColumn(positionInCache);
                        fullBlocktoWrite[0] = data.get(0)[0];
                        fullBlocktoWrite[1] = data.get(1)[0];
                        fullBlocktoWrite[2] = data.get(2)[0];
                        fullBlocktoWrite[3] = data.get(3)[0];

                        try{
                            this.memLocal.writeSharedMemory(blockNumber, fullBlocktoWrite);
                        }catch(Exception e){

                        }

                        for(int i = 0; i < 4; i++) {
                            myCacheData.writeWordOnCache(blockNumber, i, data.get(i));
                        }

                        //Actualizar el estado del directorio
                        this.directories.get(sourceDirectory).setBlockState(blockNumber, Constant.C);
                        //Actualizar los bits

                    //No
                    }else{
                        //El bloque en el directorio está en C?

                        //Sí
                        if(this.directories.get(sourceDirectory).getBlockState(blockNumber) == Constant.C){
                            //Actualizar bits

                        //No
                        }else{
                            this.directories.get(sourceDirectory).setBlockState(blockNumber, Constant.C);
                            //Actualizar bit
                        }
                        //Cargar de memoria a caché
                        int[] memoryData;
                        memoryData = new int[Constant.DATA_EMPTY_BLOCK.length];
                        try {
                            memoryData = this.memLocal.readSharedMemory(blockNumber);
                        }catch(Exception e){

                        }

                        this.myCacheData.writeWordOnCache(blockNumber, wordNumber, memoryData);
                    }
                }
                //Estado C en caché víctima
                this.myCacheData.setBlockState(blockNumber, Constant.C);

                bus.setFree(lockedList.remove(1));
                bus.setFree(lockedList.remove(0));

                //Enviar a registros lo cargado en caché
                //registers.put()
            }
        }
    }
}
