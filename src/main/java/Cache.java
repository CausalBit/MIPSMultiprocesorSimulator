import com.google.common.base.Preconditions;

import java.util.ArrayList;

/**
 * Created by irvin on 11/9/17.
 */
public class Cache {
    ArrayList<int[]> columnZero;
    ArrayList<int[]> columnOne;
    ArrayList<int[]> columnTwo;
    ArrayList<int[]> columnThree;

    int[] tags;
    int[] blockNumbers;


    public Cache(int cacheType) throws IllegalArgumentException {
        Preconditions.checkArgument(cacheType == Constant.INSTRUCTION_CACHE || cacheType == Constant.DATA_CACHE, "Cache type \""+cacheType+"\" is not recognized.");

        initializeCache(cacheType);

    }

    public void initializeCache(int cacheType ){
        int[] initialTags = {Constant.I, Constant.I, Constant.I, Constant.I};
        tags = initialTags;

        int[] initialBlockNumbers = {Constant.NULL_BLOCK_NUMBER,Constant.NULL_BLOCK_NUMBER,Constant.NULL_BLOCK_NUMBER,Constant.NULL_BLOCK_NUMBER};
        blockNumbers = initialBlockNumbers;

        for(int i = 0; i < Constant.WORDS_IN_BLOCK; i++){
            int[] emptyWord = cacheType == Constant.INSTRUCTION_CACHE ? Constant.INSTRUCTION_EMPTY_BLOCK: Constant.DATA_EMPTY_BLOCK;
            columnOne.add(i, emptyWord);
        }
    }

   
}
