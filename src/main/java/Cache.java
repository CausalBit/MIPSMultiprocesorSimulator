import com.google.common.base.Preconditions;

import java.util.ArrayList;

/**
 * Created by irvin on 11/9/17.
 */
public class Cache {
    //These are lists of words (four words).
    ArrayList<int[]> columnZero;
    ArrayList<int[]> columnOne;
    ArrayList<int[]> columnTwo;
    ArrayList<int[]> columnThree;

    int[] tags;
    int[] blockNumbers;

    int cacheType=0;
    int realWordSize;


    /**
     * Creates a simulation of a cache data structure. The specs of the caches is indicated by providing a cache type.
     * @param cacheType is the type of cache to build.
     * @throws IllegalArgumentException
     */
    public Cache(int cacheType) throws IllegalArgumentException {
        Preconditions.checkArgument(cacheType == Constant.INSTRUCTION_CACHE_TYPE || cacheType == Constant.DATA_CACHE_TYPE, "Cache type \""+cacheType+"\" is not recognized.");
        this.cacheType = cacheType;
        this.realWordSize = cacheType == Constant.INSTRUCTION_CACHE_TYPE? Constant.INSTRUCTION_CACHE_REAL_WORD_SIZE: Constant.DATA_CACHE_REAL_WORD_SIZE;
        columnZero = new ArrayList<int[]>();
        columnOne = new ArrayList<int[]>();
        columnThree = new ArrayList<int[]>();
        columnTwo= new ArrayList<int[]>();
        initializeCache(cacheType);

    }

    /**
     * Initializes cache with empty words, null block numbers, and invalid states.
     * @param cacheType specifies the type of Cache (Instruction or Data).
     */
    private void initializeCache(int cacheType ){
        int[] initialTags = {Constant.I, Constant.I, Constant.I, Constant.I};
        tags = initialTags;

        int[] initialBlockNumbers = {Constant.NULL_BLOCK_NUMBER,Constant.NULL_BLOCK_NUMBER,Constant.NULL_BLOCK_NUMBER,Constant.NULL_BLOCK_NUMBER};
        blockNumbers = initialBlockNumbers;

        for(int i = 0; i < Constant.WORDS_IN_BLOCK; i++){
            int[] emptyWord = cacheType == Constant.INSTRUCTION_CACHE_TYPE ? Constant.INSTRUCTION_EMPTY_WORD: Constant.DATA_EMPTY_WORD;
            columnZero.add(i, emptyWord);
            columnOne.add(i, emptyWord);
            columnTwo.add(i, emptyWord);
            columnThree.add(i, emptyWord);
        }
    }

    /**
     * Gets the state of a block in the cache. Requires a block number of a block present in the cache, independent from the block's state.
     * Use getBlockNumberInCachePosition to know the block number in a specific position in the cache if necessary.
     * @param blockNumberInCache is the block number that exists in the cache.
     * @return the block state in the cache.
     */
    public int getBlockState(int blockNumberInCache) throws IllegalArgumentException {
        Preconditions.checkArgument(blockNumberInCache == blockNumbers[getBlockPositionInCache(blockNumberInCache)],"Block not in cache "+blockNumberInCache);
        return tags[getBlockPositionInCache(blockNumberInCache)];
    }

    /**
     * Gets the block number in a cache's position indicated by a block number. The block number is not required
     * to be that of a block currently in cache. It is only used in order to get the desired position inside the cache.
     * @param incomingBlockNumber is the block number used to get the desired position inside the cache.
     * @return the actual block number in a position inside cache as indicated by the incomingBlockNumber.
     */
    public int getBlockNumberInCachePosition(int incomingBlockNumber )throws IllegalArgumentException {
        Preconditions.checkArgument(incomingBlockNumber > -1,"Invalid block number "+incomingBlockNumber);
        return blockNumbers[getBlockPositionInCache(incomingBlockNumber)];
    }

    /**
     * Sets the state of a block inside the cache.
     * @param blockNumberInCache is the block number of a block inside the cache. Will throw exception if block is not in the cache.
     * @param state is the state for the block inside the cache. Can only be Invalid (I), Shared (C) or Modified (M).
     */
    public void setBlockState(int blockNumberInCache, int state)throws IllegalArgumentException {
        Preconditions.checkArgument(blockNumberInCache == blockNumbers[getBlockPositionInCache(blockNumberInCache)],"Block not in cache "+blockNumberInCache);
        Preconditions.checkArgument(state == Constant.I || state == Constant.C || state == Constant.M,"Invalid state while setting state of block in cache");
        tags[getBlockPositionInCache(blockNumberInCache)] = state;
    }

    /**
     * Sets the block number for block/position inside the cache.
     * @param incomingBlockNumber indicates the block number to set in the cache, which also implies the position in the cache.
     */
    public void setBlockNumberInCachePosition(int incomingBlockNumber ) throws IllegalArgumentException {
        Preconditions.checkArgument(incomingBlockNumber > -1,"Invalid block number "+incomingBlockNumber);
        blockNumbers[getBlockPositionInCache(incomingBlockNumber)] = incomingBlockNumber;
    }

    /**
     * Reads a valid word from a specific valid block inside the cache.
     * @param blockNumber indicates the block to read inside the cache. Will throw exception if the block is not in the cache.
     * @param wordNumber indicates the word to read from the valid block.
     * @return the word read from the cache.
     */
    public int[] readWordFromCache(int blockNumber, int wordNumber) throws IllegalArgumentException {
        Preconditions.checkArgument(blockNumber == blockNumbers[getBlockPositionInCache(blockNumber)],"Block not in cache "+blockNumber);
        Preconditions.checkArgument(wordNumber > -1 && wordNumber < Constant.WORDS_IN_BLOCK, "Word number is out of bounds");

        ArrayList<int[]> cacheColumn = getCorrespondingColumn(getBlockPositionInCache(blockNumber));
        return cacheColumn.get(wordNumber);
    }

    /**
     * Writes a word on a specific valid block inside the cache.
     * @param blockNumber indicates the block to wrote on inside the cache. Will throw exception if the block is not in the cache.
     * @param wordNumber indicates the position inside block. It has to be a valid position inside the block.
     * @param wordData is the value for the word to write. It has to have a valid size depending on the type of cache (instruction or data).
     */
    public void writeWordOnCache(int blockNumber, int wordNumber, int[] wordData) throws IllegalArgumentException {
        Preconditions.checkArgument(blockNumber == blockNumbers[getBlockPositionInCache(blockNumber)],"Block not in cache "+blockNumber);
        Preconditions.checkArgument(wordNumber > -1 && wordNumber < Constant.WORDS_IN_BLOCK, "Word number is out of bounds");
        Preconditions.checkArgument(wordData.length == realWordSize, "The incoming word size differs the cache's word size");
        ArrayList<int[]> cacheColumn = getCorrespondingColumn(getBlockPositionInCache(blockNumber));
        cacheColumn.set(wordNumber, wordData);
    }

    /**
     * Algorithm to get the corresponding position inside the cache of a block based on its number.
     * @param blockNumber is the number block for which the position is requested.
     * @return the position of the block number inside the cache.
     */
    private int getBlockPositionInCache(int blockNumber){
        return blockNumber % 4;
    }

    /**
     * Useful private method to get the corresponding (real) data structure representing a block inside the
     * simulated cache.
     * @param position indicating the column in the cache.
     * @return the actual data structure that simulates the column in a cache.
     */
    public ArrayList<int[]> getCorrespondingColumn(int position){
        switch(position){
            case 0:
                return columnZero;
            case 1:
                return columnOne;
            case 2:
                return columnTwo;
            default:
                return columnThree;
        }
    }

   
}
