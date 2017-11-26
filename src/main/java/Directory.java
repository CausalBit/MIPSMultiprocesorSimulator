import com.google.common.base.Preconditions;

import java.util.ArrayList;

/**
 * Created by irvin on 11/9/17.
 */
public class Directory {
    private ArrayList<int[]> directory;
    private int initBlock;
    private String directoryID;

    public Directory(int numberOfBlocksInDirectory, int initialBlock, String directoryID ){
        this.directoryID = directoryID;
        this.initBlock = initialBlock;
        directory = new ArrayList<int[]>();
        initializeDictionary(numberOfBlocksInDirectory);

    }

    /**
     * Initializes the dictionary with uncached blocks.
     * @param numberOfBlocksInDirectory is the number of blocks in the direcotry.
     */
    public void initializeDictionary(int numberOfBlocksInDirectory){
        for(int i = 0; i < numberOfBlocksInDirectory; i++){
            int[] stateAndProcessorIds = {Constant.U, 0,0,0}; //Uncached state.

            directory.add(i, stateAndProcessorIds);
        }
    }

    public int getModifiedCache(int blockNumber){
        Preconditions.checkArgument(blockNumber >= initBlock || blockNumber < initBlock+directory.size(),
                "The block number \""+blockNumber+"\" does not belong to this Directory. Initial Block Number in Directory: "+initBlock);

        int actualIndex = blockNumber - initBlock;

        int cache = 0;

        for(int i = 1; i < directory.get(actualIndex).length; i++){
            if(directory.get(actualIndex)[i] == 1){
                cache = i;
                break;
            }
        }

        return cache;
    }

    public int getBlockState(int blockNumber) throws IllegalArgumentException {

        Preconditions.checkArgument(blockNumber >= initBlock || blockNumber < initBlock+directory.size(),
                "The block number \""+blockNumber+"\" does not belong to this Directory. Initial Block Number in Directory: "+initBlock);

        int actualIndex = blockNumber - initBlock;
        return directory.get(actualIndex)[Constant.DICTIONARY_STATE];
    }

    public void setBlockBits(int blockNumber, int cache, int bit){
        int actualIndex = blockNumber - initBlock;
        directory.get(actualIndex)[cache+1] = bit;
    }

    public int getBlockBit(int blockNumber, int cache){
        int actualIndex = blockNumber - initBlock;
        return directory.get(actualIndex)[cache+1];
    }

    public void setBlockState(int blockNumber, int State) throws IllegalArgumentException {
        Preconditions.checkArgument(blockNumber >= initBlock || blockNumber < initBlock+directory.size(),
                "The block number \""+blockNumber+"\" does not belong to this Directory. Initial Block Number in Directory: "+initBlock);

        Preconditions.checkArgument(State == Constant.C || State == Constant.M || State == Constant.U, "The state is not recognized in the dictionary");

        int actualIndex = blockNumber - initBlock;
        directory.get(actualIndex)[Constant.DICTIONARY_STATE] = State;
    }

    public boolean getExistenceInCore(int blockNumber, int coreId) throws IllegalArgumentException {
        Preconditions.checkArgument(blockNumber >= initBlock || blockNumber < initBlock+directory.size(),
                "The block number \""+blockNumber+"\" does not belong to this Directory. Initial Block Number in Directory: "+initBlock);

        Preconditions.checkArgument(coreId == Constant.CORE_0 || coreId == Constant.CORE_1 || coreId == Constant.CORE_2, "The core id \""+coreId+"\" is not identified." );

        int actualIndex = blockNumber - initBlock;
        return directory.get(actualIndex)[coreId+1] == Constant.ON ? true: false;

    }

    public void setExistenceInCore(int blockNumber, int coreId, int existenceInCore ) throws IllegalArgumentException {
        Preconditions.checkArgument(blockNumber >= initBlock || blockNumber < initBlock+directory.size(),
                "The block number \""+blockNumber+"\" does not belong to this Directory. Initial Block Number in Directory: "+initBlock);

        Preconditions.checkArgument(coreId == Constant.CORE_0 || coreId == Constant.CORE_1 || coreId == Constant.CORE_2, "The processor id \""+coreId+"\" is not identified." );

        Preconditions.checkArgument(existenceInCore == Constant.ON || existenceInCore == Constant.OFF, "The value for processor existence \""+existenceInCore+"\" is not recognized.");

        int actualIndex = blockNumber - initBlock;
        directory.get(actualIndex)[coreId+1] = existenceInCore;
    }

    public String getDirectoryID(){
        return directoryID;
    }

    public boolean existsInDirectory(int blockNumber){
        if(blockNumber >= initBlock && blockNumber < initBlock+directory.size()){
            return true;
        }
        return false;
    }

    public int getTotalCachesWithBlock(int blockNumber){
        int actualIndex = blockNumber - initBlock;

        int total = 0;

        for(int i = 1; i < directory.get(actualIndex).length; i++){
            if(directory.get(actualIndex)[i] == 1){
                total++;
            }
        }

        return total;
    }

    public int getInitBlock(){
        return this.initBlock;
    }
}
