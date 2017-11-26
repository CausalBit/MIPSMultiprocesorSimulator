import com.google.common.base.Preconditions;

import java.lang.System;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by irvin on 11/9/17.
 */
public class PhysicalMemory {
    private ArrayList<int[]> localInstMem;
    private ArrayList<int[]> sharedDataMem;

    private int sharedDataInitBlock;
    private int localInstMemInitBlock;
    private String sharedMemId;

    public PhysicalMemory(int sharedDataMemTotalBlocks, int sharedDataInitBlock,
                           int localInstMemTotalBlocks, int localInstMemInitBlock, String sharedMemId){

        this.localInstMem = new ArrayList<int[]>();
        this.sharedDataMem = new ArrayList<int[]>();
        this.sharedDataInitBlock = sharedDataInitBlock;
        this.localInstMemInitBlock = localInstMemInitBlock;
        this.sharedMemId = sharedMemId;
        initializeMemorySizeAndValues(sharedDataMemTotalBlocks, localInstMemTotalBlocks);

    }

    /**
     * Initializes the memory with zeros.
     * @param sharedDataMemTotalBlocks is the number of total blocks in the shared memory (capacity).
     * @param localInstMemTotalBlocks is the number of total blocks in the local instruction memory (capacity).
     */
    public void initializeMemorySizeAndValues(int sharedDataMemTotalBlocks, int localInstMemTotalBlocks){

        for(int i = 0; i < sharedDataMemTotalBlocks; i++){
            //Byte is represented a 1 Integer.
            sharedDataMem.add(i,Constant.DATA_EMPTY_BLOCK);
        }

        for(int i = 0; i < localInstMemTotalBlocks; i++){
            //Local Instruction Memory Byte Size: Remember, instructions are actually 4 Integers.
            localInstMem.add(i,Constant.INSTRUCTION_EMPTY_BLOCK);
        }

    }

    public int[] readBlockInstructionMemory(int blockNumber) throws Exception{
        checkPrecontionsBlockNumber(blockNumber, Constant.INSTRUCTION_MEMORY_TYPE);
        int actualIndex = blockNumber - localInstMemInitBlock;
        return localInstMem.get(actualIndex);
    }

    public int[] readSharedMemory(int blockNumber) throws Exception{
        checkPrecontionsBlockNumber(blockNumber, Constant.SHARED_MEMORY_TYPE);

        int actualIndex = blockNumber - sharedDataInitBlock;
        return sharedDataMem.get(actualIndex);
    }

    public void writeBlockInstructionMemory(int blockNumber, int[] block) throws Exception{
        Preconditions.checkArgument(block.length == Constant.INSTRUCTION_EMPTY_BLOCK.length, "Error: Trying to write a block with a different real size of 4 integers in Instruction Memory.");
        checkPrecontionsBlockNumber(blockNumber, Constant.INSTRUCTION_MEMORY_TYPE);

        int actualIndex = blockNumber - localInstMemInitBlock;
        localInstMem.set(actualIndex,block);
    }

    public void writeSharedMemory(int blockNumber, int[] block) throws Exception{
        Preconditions.checkArgument(block.length == Constant.DATA_EMPTY_BLOCK.length, "Error: Trying to write a block with a different real size of 1 integer in Shared Memory.");
        checkPrecontionsBlockNumber(blockNumber, Constant.SHARED_MEMORY_TYPE);

        int actualIndex = blockNumber - sharedDataInitBlock;
        sharedDataMem.set(actualIndex,block);
    }

    public int getSharedDataInitBlock(){
        return sharedDataInitBlock;
    }



    public int getLocalInstMemInitBlock(){
        return localInstMemInitBlock;
    }

    public int getLocalInstMemInitAddress(){
        return localInstMemInitBlock*Constant.INSTRUCTION_EMPTY_BLOCK.length;
    }

    public int getLocalInstMemBlockNumber(int address ){
        return address/16;
    }


    public int getSharedDataInitAddress(){
        return sharedDataInitBlock*Constant.DATA_EMPTY_BLOCK.length;
    }


    /**
     * Checks if the block number is a valid block number for the PhysicalMemory instance.
     * Also checks the type of memory.
     * @param blockNumber is the block number to verify.
     * @param type the type of memory within the PhysicalMemory.
     * @throws IllegalArgumentException
     */
    public void checkPrecontionsBlockNumber(int blockNumber, String type) throws IllegalArgumentException {

        if(type.equals(Constant.INSTRUCTION_MEMORY_TYPE)){

            Preconditions.checkArgument(blockNumber >= localInstMemInitBlock || blockNumber < localInstMemInitBlock+localInstMem.size(),
                    "The block number does not belong to this local physical Instructions Memory");

        }else if(type.equals(Constant.SHARED_MEMORY_TYPE)){

            Preconditions.checkArgument(blockNumber >= sharedDataInitBlock || blockNumber < sharedDataInitBlock+sharedDataMem.size(),
                    "The block number does not belong to this local physical Shared Memory");

        }else{
            Preconditions.checkArgument(false, "Unknown memory type \""+type+"\" for memory." );
        }
    }


    public void printInstMem(){
        String memoryDump = "Instructions Memory: \n";
        for(int i = 0; i < localInstMem.size(); i++){
            memoryDump += "Block #"+i+": "+ Arrays.toString(localInstMem.get(i))+"\n";
        }
        System.out.println(memoryDump);
    }
    public String getIdSharedMem(){
        return sharedMemId;
    }
    public boolean getExistenceBlockInSharedMemory(int block){
        if( (block >= sharedDataInitBlock) && ( block <  (sharedDataInitBlock+sharedDataMem.size()) ) ){
            return true;
        }
        return false;
    }

    public void printDataMem(){
        String memoryDump = "Instructions Memory: \n";
        for(int i = 0; i < sharedDataMem.size(); i++){
            memoryDump += "Block #"+(i+sharedDataInitBlock)+": "+ Arrays.toString(sharedDataMem.get(i))+"\n";
        }
        System.out.println(memoryDump);
    }
}
