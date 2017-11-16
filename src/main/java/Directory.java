import com.google.common.base.Preconditions;

import java.util.ArrayList;

/**
 * Created by irvin on 11/9/17.
 */
public class Directory {
    private ArrayList<int[]> directory;
    int initBlock;

    public Directory(int numberOfBlocksInDirectory, int initialBlock ){
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

    public void setBlockState(int blockNumber, int State) throws IllegalArgumentException {

        Preconditions.checkArgument(blockNumber >= initBlock || blockNumber < initBlock+directory.size(),
                "The block number \""+blockNumber+"\" does not belong to this Directory. Initial Block Number in Directory: "+initBlock);

        Preconditions.checkArgument(State == Constant.C || State == Constant.M || State == Constant.U, "The state is not recognized in the dictionary");

        int actualIndex = blockNumber - initBlock;
        directory.get(actualIndex)[Constant.DICTIONARY_STATE] = State;
    }

    public boolean getExistenceInProcesor(int blockNumber, int processorId) throws IllegalArgumentException {
        Preconditions.checkArgument(blockNumber >= initBlock || blockNumber < initBlock+directory.size(),
                "The block number \""+blockNumber+"\" does not belong to this Directory. Initial Block Number in Directory: "+initBlock);

        Preconditions.checkArgument(processorId == Constant.CORE_0 || processorId == Constant.CORE_1 || processorId == Constant.CORE_2, "The processor id \""+processorId+"\" is not identified." );

        int actualIndex = blockNumber - initBlock;
        return directory.get(actualIndex)[processorId] == Constant.ON ? true: false;

    }

    public void setExistenceInProcesor(int blockNumber, int processorId, int existenceInProcessor ) throws IllegalArgumentException {
        Preconditions.checkArgument(blockNumber >= initBlock || blockNumber < initBlock+directory.size(),
                "The block number \""+blockNumber+"\" does not belong to this Directory. Initial Block Number in Directory: "+initBlock);

        Preconditions.checkArgument(processorId == Constant.CORE_0 || processorId == Constant.CORE_1 || processorId == Constant.CORE_2, "The processor id \""+processorId+"\" is not identified." );

        Preconditions.checkArgument(existenceInProcessor == Constant.ON || existenceInProcessor == Constant.OFF, "The value for processor existence \""+existenceInProcessor+"\" is not recognized.");

        int actualIndex = blockNumber - initBlock;
        directory.get(actualIndex)[processorId] = existenceInProcessor;

    }
}
