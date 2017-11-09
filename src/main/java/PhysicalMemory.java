import com.google.common.base.Preconditions;

import java.util.ArrayList;

/**
 * Created by irvin on 11/9/17.
 */
public class PhysicalMemory {
    ArrayList<int[]> localInstMem;
    ArrayList<int[]> sharedDataMem;

    int sharedDataInitBlock;
    int localInstMemInitBlock;


    public PhysicalMemory(int sharedDataMemTotalBlocks, int sharedDataInitBlock,
                           int localInstMemTotalBlocks, int localInstMemInitBlock){

        this.localInstMem = new ArrayList<int[]>();
        this.sharedDataMem = new ArrayList<int[]>();
        this.sharedDataInitBlock = sharedDataInitBlock;
        this.localInstMemInitBlock = localInstMemInitBlock;

        initializeMemorySizeAndValues(sharedDataMemTotalBlocks, localInstMemTotalBlocks);

    }

    /**
     * Initializes the memory with zeros.
     * @param sharedDataMemTotalBlocks is the number of total blocks in the shared memory (capacity).
     * @param localInstMemTotalBlocks is the number of total blocks in the local instruction memory (capacity).
     */
    public void initializeMemorySizeAndValues(int sharedDataMemTotalBlocks, int localInstMemTotalBlocks){

        for(int i = 0; i < sharedDataMemTotalBlocks; i++){
            int[] emptyBlock = {0}; //Byte is represented a 1 Integer.
            sharedDataMem.add(i,emptyBlock);
        }

        for(int i = 0; i < localInstMemTotalBlocks; i++){
            int[] emptyBlock = {0,0,0,0};  //Local Instruction Memory Byte Size: Remember, instructions are actually 4 Integers.
            localInstMem.add(i,emptyBlock);
        }

    }

    public int[] readInstructionMemory(int blockNumber) throws Exception{
        checkPrecontionsBlockNumber(blockNumber, Constant.INSTRUCTION_MEMORY_TYPE);

        int actualIndex = blockNumber - localInstMemInitBlock;
        return localInstMem.get(actualIndex);
    }

    public int[] readSharedMemory(int blockNumber) throws Exception{
        checkPrecontionsBlockNumber(blockNumber, Constant.SHARED_MEMORY_TYPE);

        int actualIndex = blockNumber - sharedDataInitBlock;
        return sharedDataMem.get(actualIndex);
    }

    public void writeInstructionMemory(int blockNumber, int[] block) throws Exception{
        Preconditions.checkArgument(block.length == 4, "Error: Trying to write a block with a different real size of 4 integers in Instruction Memory.");
        checkPrecontionsBlockNumber(blockNumber, Constant.INSTRUCTION_MEMORY_TYPE);

        int actualIndex = blockNumber - localInstMemInitBlock;
        localInstMem.set(actualIndex,block);
    }

    public void writeSharedMemory(int blockNumber, int[] block) throws Exception{
        Preconditions.checkArgument(block.length == 1, "Error: Trying to write a block with a different real size of 1 integer in Shared Memory.");
        checkPrecontionsBlockNumber(blockNumber, Constant.SHARED_MEMORY_TYPE);

        int actualIndex = blockNumber - sharedDataInitBlock;
        sharedDataMem.set(actualIndex,block);
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

}
