import java.lang.*;
import java.lang.System;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by irvin on 11/24/17.
 */

public class DataManager {
    Bus bus;
    Cache localCache;
    PhysicalMemory localMem;
    Directory localDirectory;
    String cacheLocalId;
    int myCoreId;
    Stack<String> lockedResources;
    Processor myProcessor;
    Processor externalProcessor;
    int processorId ;
    int duration;

   public DataManager(Bus bus, String processorId, int myCoreId ){
       duration = 0;
       this.bus = bus;
       this.myCoreId= myCoreId;
       setProcessor(processorId);//define parent and external processor
       this.cacheLocalId = bus.getProcessor(processorId).getCacheIdbyCoreId(myCoreId);
       this.localCache  = bus.getProcessorById(processorId).getDataCacheByCoreId(myCoreId);
       this.localDirectory = bus.getProcessorById(processorId).getLocalDirectory();
       this.localMem = bus.getProcessor(processorId).getLocalPhysicalMemory();
       lockedResources = new Stack<String>();
   }
   public int[] loadWordProcedure(int wordNumber, int blockNumber){
        this.duration = 0;
       //Bock the local cache!
       if (!bus.request(cacheLocalId)) {
           bus.freeOwnedByCurrentThread();
           return Constant.ABORT;
       }

       int blockInCache = localCache.getBlockNumberInCachePosition(blockNumber);

       if(blockInCache == blockNumber){
           int blockState = localCache.getBlockState(blockInCache);
           if(blockState == Constant.M && blockState == Constant.C){
               //THIS IS A HIT!!
               int [] resultingWord = localCache.readWordFromCache(blockNumber,wordNumber);
               bus.freeOwnedByCurrentThread();
               return resultingWord;
           }

           //Now we are dealing with an invalidated block.
           //Let's check the directory to see if it is in some other cache.
           Directory targetDirectory = getDirectoryByBlock(blockNumber);
           if(!bus.request(targetDirectory.getDirectoryID())){
               bus.freeOwnedByCurrentThread();
               return Constant.ABORT;
           }

           //We need to pull the block from memory to local cache.
           PhysicalMemory targetMemory = getPhysicalMemoryByBlock(blockNumber);
           if(!bus.request(targetMemory.getIdSharedMem())){
               bus.freeOwnedByCurrentThread();
               return Constant.ABORT;
           }

           increaseDurationOnDirectoryAccess(targetDirectory);
           if(targetDirectory.getBlockState(blockNumber) == Constant.M){

               //Let's get the cache where it modified.
               Cache otherCache = getCacheFromDirectoryBlockOnModified(targetDirectory,blockNumber);

               if(!bus.request(otherCache.getCacheID())){
                   bus.freeOwnedByCurrentThread();
                   return Constant.ABORT;
               }

               //Since this block is modified, we need to store back in to memory,
               increaseDurationOnMemoryRW(targetMemory);
               writeBlockToMemory(blockNumber, targetMemory,otherCache);
               otherCache.setBlockState(blockNumber,Constant.C);
               bus.setFree(otherCache.getCacheID());


           }

           increaseDurationOnMemoryRW(targetMemory);
           writeFromMemoryToCache(blockNumber,targetMemory,localCache);
           localCache.setBlockState(blockInCache,Constant.C);
           bus.setFree(targetMemory.getIdSharedMem());
           targetDirectory.setExistenceInCore(blockNumber,myCoreId,Constant.ON);
           targetDirectory.setBlockState(blockNumber,Constant.C); //if it's already in C, it doesn't matter.

           int [] resultingWord = localCache.readWordFromCache(blockNumber,wordNumber);
           bus.freeOwnedByCurrentThread();
           return resultingWord;

       }

       //Now we have a miss based on a different block number in cache.
       //So first, we need to decide what to do about this other block number.
       //Though, we have to make sure we are not talking about a miss because the cache is empty (NULL BLOCK NUMBER)

       /****LET's HANDLE OUT A VICTIM NUMBER****/
       Directory victimDirectory = null;
       PhysicalMemory victimMemory = null;

       if(blockInCache != Constant.NULL_BLOCK_NUMBER) {
           int blockInCacheState = localCache.getBlockState(blockInCache);

           if(blockInCacheState == Constant.M){

                //We need to get the directory to update to U.
               victimDirectory = getDirectoryByBlock(blockInCache);
               if(!bus.request(victimDirectory.getDirectoryID())){
                   bus.freeOwnedByCurrentThread();
                   return Constant.ABORT;
               }

               //We need to pull the block from memory to local cache.
               victimMemory = getPhysicalMemoryByBlock(blockInCache);
               if(!bus.request(victimMemory.getIdSharedMem())){
                   bus.freeOwnedByCurrentThread();
                   return Constant.ABORT;
               }

               increaseDurationOnMemoryRW(victimMemory);
               writeBlockToMemory(blockInCache,victimMemory,localCache);
               bus.setFree(victimMemory.getIdSharedMem()); //We won't need the memory any longer.
               increaseDurationOnDirectoryAccess(victimDirectory);
               victimDirectory.setBlockState(blockInCache,Constant.U);

           }else if( blockInCacheState == Constant.C ){

               victimDirectory = getDirectoryByBlock(blockInCache);
               if(!bus.request(victimDirectory.getDirectoryID())){
                   bus.freeOwnedByCurrentThread();
                   return Constant.ABORT;
               }

               increaseDurationOnDirectoryAccess(victimDirectory);
               victimDirectory.setExistenceInCore(blockInCache,myCoreId,Constant.OFF);

               if (victimDirectory.getExistenceInCore(blockInCache, Constant.CORE_0)
                       && victimDirectory.getExistenceInCore(blockInCache, Constant.CORE_1)
                       && victimDirectory.getExistenceInCore(blockInCache, Constant.CORE_2)) {

                   victimDirectory.setBlockState(blockInCache, Constant.U);
               }
           }

       }


       //At this point, we have a block in cache which either null, or invalidated. We can just
       //pull the block from memory and read it from there.
       //However, before we read from memory, we have to deal with a block number in other cases in
       //states like M or C.

       /****LET's HANDLE OUT ACTUAL BLOCK NUMBER****/
       //Let's get our target directory if haven't done so.
       Directory targetDirectory = getDirectoryByBlock(blockNumber);
       if(victimDirectory == null || !targetDirectory.getDirectoryID().equals(victimDirectory.getDirectoryID())){
           if(victimDirectory != null ){
               bus.setFree(victimDirectory.getDirectoryID()); //Release victim block's directory because it is no longer needed.
           }
           if(!bus.request(targetDirectory.getDirectoryID())){
               bus.freeOwnedByCurrentThread();
               return Constant.ABORT;
           }

       }

       //Let's get our target memory if haven't done so.
       PhysicalMemory targetMemory = getPhysicalMemoryByBlock(blockNumber);
       if(victimMemory == null || !targetMemory.getIdSharedMem().equals(victimDirectory.getDirectoryID())){
           if(victimMemory != null) {
               bus.setFree(victimMemory.getIdSharedMem()); //Release victim block's memory because it is no longer needed.
           }
           if(!bus.request(targetMemory.getIdSharedMem())){
               bus.freeOwnedByCurrentThread();
               return Constant.ABORT;
           }
       }

       //The block number can actually be shared (C), or modified(M) in other caches
       increaseDurationOnDirectoryAccess(targetDirectory);
       if(targetDirectory.getBlockState(blockNumber) == Constant.M){

           //Let's get the cache where it modified.
           Cache otherCache = getCacheFromDirectoryBlockOnModified(targetDirectory,blockNumber);

           if(!bus.request(otherCache.getCacheID())){
               bus.freeOwnedByCurrentThread();
               return Constant.ABORT;
           }

           //Since this block is modified, we need to store back in to memory
           increaseDurationOnMemoryRW(targetMemory);
           writeBlockToMemory(blockNumber, targetMemory,otherCache);
           otherCache.setBlockState(blockNumber,Constant.C);
           bus.setFree(otherCache.getCacheID());


       }

       increaseDurationOnMemoryRW(targetMemory);
       writeFromMemoryToCache(blockNumber,targetMemory,localCache);
       localCache.setBlockState(blockNumber,Constant.C);
       bus.setFree(targetMemory.getIdSharedMem());
       targetDirectory.setExistenceInCore(blockNumber,myCoreId,Constant.ON);
       targetDirectory.setBlockState(blockNumber,Constant.C); //if it's already in C, it doesn't matter.

       int [] resultingWord = localCache.readWordFromCache(blockNumber,wordNumber);
       bus.freeOwnedByCurrentThread();
       return resultingWord;

   }

   public int[] storeWordProcedure(int wordNumber, int blockNumber, int[] data) {
       duration = 0;
       //Let's block our cache
       if (!bus.request(localCache.getCacheID())) {
           return Constant.ABORT;
       }

       int blockNumberInCache = localCache.getBlockNumberInCachePosition(blockNumber);
       if (blockNumber == blockNumberInCache) {
           int blockInCacheState = localCache.getBlockState(blockNumberInCache);

           //We need to get the directory to invalidate the block in other caches.
           Directory targetDirectory = getDirectoryByBlock(blockNumber);
           if (!bus.request(targetDirectory.getDirectoryID())) {
               bus.freeOwnedByCurrentThread();
               return Constant.ABORT;
           }

           if (blockInCacheState == Constant.C) {

               //Invalidate other caches! This will return incomplete if it is unable to obtain all necessary caches.
               increaseDurationOnDirectoryAccess(targetDirectory);
               int[] updateResult = propateStateInCaches(Constant.I, blockNumber, targetDirectory);
               if (updateResult == Constant.ABORT) {
                   bus.freeOwnedByCurrentThread();
                   return Constant.ABORT;
               }


           }else if (blockInCacheState == Constant.I) {
               //We need to use the directory to see if the target block number is actually
               //being used some place else, and then update it.

               //And since the block is invalid in the local cache, we need to bring back
               //the block from memory, even if this is a store.
               //Since we are doing a "Write-Allocate" policy.
               PhysicalMemory targetMemory = getPhysicalMemoryByBlock(blockNumber);
               if (!bus.request(targetMemory.getIdSharedMem())) {
                   bus.freeOwnedByCurrentThread();
                   return Constant.ABORT;
               }

               increaseDurationOnDirectoryAccess(targetDirectory);
               int blockStateOnDirectory = targetDirectory.getBlockState(blockNumber);
               if (blockStateOnDirectory == Constant.C) {
                   //This means that we need to invalidate this block in other caches.
                   int[] updateResult = propateStateInCaches(Constant.I, blockNumber, targetDirectory);
                   if (updateResult == Constant.ABORT) {
                       bus.freeOwnedByCurrentThread();
                       return Constant.ABORT;
                   }
               }

               if (blockStateOnDirectory == Constant.M) {
                   //This means that we need to invalidate this block in the other cache where it is modified,
                   //Although, we need to store that block in memory, and allocate this block back into our cache.
                   Cache targetCache = getCacheFromDirectoryBlockOnModified(targetDirectory, blockNumber);
                   if (!bus.request(targetCache.getCacheID())) {
                       bus.freeOwnedByCurrentThread();
                       return Constant.ABORT;
                   }
                   //Change cache from M to I
                   increaseDurationOnDirectoryAccess(targetDirectory);
                   targetCache.setBlockState(blockNumber, Constant.I);
                   increaseDurationOnMemoryRW(targetMemory);
                   writeBlockToMemory(blockNumber, targetMemory, targetCache);
                   int coreId = getCoreIdByCache(targetCache);
                   targetDirectory.setExistenceInCore(blockNumber, coreId, Constant.OFF);
                   bus.setFree(targetCache.getCacheID());

               }

               //The last case would be having block U (uncached), so we need to update the directory.
               if (blockInCacheState == Constant.U) {
                   increaseDurationOnDirectoryAccess(targetDirectory);
                   targetDirectory.setExistenceInCore(blockNumber, myCoreId, Constant.ON);
               }

               increaseDurationOnMemoryRW(targetMemory);
               writeFromMemoryToCache(blockNumber, targetMemory, localCache); //WRITE ALLOCATE!
               bus.setFree(targetMemory.getIdSharedMem());

           }

           //For everyone, we just write on the cache after making the previous, conditional changes.
           localCache.writeWordOnCache(blockNumber, wordNumber, data);

           if(blockInCacheState != Constant.M) {
               localCache.setBlockState(blockNumber, Constant.M);
               targetDirectory.setBlockState(blockNumber, Constant.M);
           }

           bus.freeOwnedByCurrentThread(); //Release all blocked resources!
           return Constant.COMPLETED;
       }

       Directory victimDirectory = null;
       PhysicalMemory victimMemory = null;

       if(blockNumberInCache != Constant.NULL_BLOCK_NUMBER) {
           /**LET'S HANDLE THE VICTIM BLOCK**/
           //Ok, so at this point we a a different block in the cache, A.K.A. the victim block.
           //blockNumberInCache is the victim block. Depending on it's state on the cache, we do different things.
           int blockInCacheState = localCache.getBlockState(blockNumberInCache); //NOTICE: STATE FROM CACHE.
           victimDirectory = getDirectoryByBlock(blockNumberInCache);
            victimMemory = getPhysicalMemoryByBlock(blockNumberInCache);

           //NOTICE: STATE FROM CACHE.
           if (blockInCacheState == Constant.M) {

               if (!bus.request(victimDirectory.getDirectoryID())) {
                   bus.freeOwnedByCurrentThread();
                   return Constant.ABORT;
               }
               //We need to store this block to the memory and update it's directory entry.
               if (!bus.request(victimMemory.getIdSharedMem())) {
                   bus.freeOwnedByCurrentThread();
                   return Constant.ABORT;
               }

               increaseDurationOnMemoryRW(victimMemory);
               writeBlockToMemory(blockNumberInCache, victimMemory, localCache);
               bus.setFree(victimMemory.getIdSharedMem());

               increaseDurationOnDirectoryAccess(victimDirectory);
               victimDirectory.setExistenceInCore(blockNumberInCache, myCoreId, Constant.OFF);
               victimDirectory.setBlockState(blockNumberInCache, Constant.U);

           } else if (blockInCacheState == Constant.C) {
               //We just need to update the directory.
               if (!bus.request(victimDirectory.getDirectoryID())) {
                   bus.freeOwnedByCurrentThread();
                   return Constant.ABORT;
               }
               increaseDurationOnDirectoryAccess(victimDirectory);
               victimDirectory.setExistenceInCore(blockNumberInCache, myCoreId, Constant.OFF);

               if (victimDirectory.getExistenceInCore(blockNumberInCache, Constant.CORE_0)
                       && victimDirectory.getExistenceInCore(blockNumberInCache, Constant.CORE_1)
                       && victimDirectory.getExistenceInCore(blockNumberInCache, Constant.CORE_2)) {

                   victimDirectory.setBlockState(blockNumberInCache, Constant.U);
               }
           }

           //If the directory of the victim block is uncached, we would just write on the cache.
       }

       /****LET's HANDLE OUT ACTUAL BLOCK NUMBER****/
       //Let's get our target directory if haven't done so.
       Directory targetDirectory = getDirectoryByBlock(blockNumber);
       if(victimDirectory == null || !targetDirectory.getDirectoryID().equals(victimDirectory.getDirectoryID())){
           if(victimDirectory != null ){
               bus.setFree(victimDirectory.getDirectoryID()); //Release victim block's directory because it is no longer needed.
           }
           if(!bus.request(targetDirectory.getDirectoryID())){
               bus.freeOwnedByCurrentThread();
               return Constant.ABORT;
           }

       }

       //Let's get our target memory if haven't done so.
       PhysicalMemory targetMemory = getPhysicalMemoryByBlock(blockNumber);
       if(victimMemory == null || !targetMemory.getIdSharedMem().equals(victimDirectory.getDirectoryID())){
           if(victimMemory != null) {
               bus.setFree(victimMemory.getIdSharedMem()); //Release victim block's memory because it is no longer needed.
           }
           if(!bus.request(targetMemory.getIdSharedMem())){
               bus.freeOwnedByCurrentThread();
               return Constant.ABORT;
           }
       }

       increaseDurationOnDirectoryAccess(targetDirectory);
       int blockStateDirectory = targetDirectory.getBlockState(blockNumber);

       //The block number can actually be shared (C), or modified(M) in other caches
       //So we need to invalidate and even store to memory if necessary.

       if (blockStateDirectory == Constant.C) {
           //This means that we need to invalidate this block in other caches.
           int[] updateResult = propateStateInCaches(Constant.I, blockNumber, targetDirectory);
           if (updateResult == Constant.ABORT) {
               bus.freeOwnedByCurrentThread();
               return Constant.ABORT;
           }
       }

       if (blockStateDirectory == Constant.M) {
           //This means that we need to invalidate this block in the other cache where it is modified,
           //Although, we need to store that block in memory, and allocate this block back into our cache.
           Cache targetCache = getCacheFromDirectoryBlockOnModified(targetDirectory, blockNumber);
           if (!bus.request(targetCache.getCacheID())) {
               bus.freeOwnedByCurrentThread();
               return Constant.ABORT;
           }
           //Change cache from M to I
           targetCache.setBlockState(blockNumber, Constant.I);
           increaseDurationOnMemoryRW(targetMemory);
           writeBlockToMemory(blockNumber, targetMemory, targetCache);
           int coreId = getCoreIdByCache(targetCache);
           targetDirectory.setExistenceInCore(blockNumber, coreId, Constant.OFF);
           bus.setFree(targetCache.getCacheID());

       }

       //The last case would be having block U (uncached), so we need to update the directory.
       if (blockStateDirectory == Constant.U) {
           targetDirectory.setExistenceInCore(blockNumber, myCoreId, Constant.ON);
       }

       increaseDurationOnMemoryRW(targetMemory);
       writeFromMemoryToCache(blockNumber, targetMemory, localCache); //WRITE ALLOCATE!
       bus.setFree(targetMemory.getIdSharedMem());

       //For the rest of the states, we just write on the cache after making the previous, conditional changes.
       localCache.writeWordOnCache(blockNumber, wordNumber, data);

       if(blockStateDirectory != Constant.M) {
          //Update the directory of this block number because we are storing it. 
           localCache.setBlockState(blockNumber, Constant.M);
           targetDirectory.setBlockState(blockNumber, Constant.M);
       }

       bus.freeOwnedByCurrentThread(); //Release all blocked resources!
       return Constant.COMPLETED;
   }


   public void writeBlockToMemory(int block, PhysicalMemory memory, Cache cache){
       int [] blockInCacheInM =  readBlockFromCache(block,cache );
       try {
           memory.writeSharedMemory(block, blockInCacheInM);
       }catch (Exception e){
            e.printStackTrace();
       }
   }

    public int [] readBlockFromCache(int block, Cache cache){
        int [] blockFromCache= new int[(Constant.DATA_EMPTY_BLOCK.length)];
        for(int numWord = 0 ; numWord <Constant.WORDS_IN_BLOCK; numWord++ ){
            int [] word = cache.readWordFromCache(block, numWord);
            java.lang.System.arraycopy(word,0,blockFromCache,numWord,1);
        }
        return blockFromCache;
    }



   public void addLockableResource(String resource){
       lockedResources.add(resource);
   }



    /**
     * Check if it is a hit, miss
     * @param block
     * @param cache
     * @return can return the invalid state, block number
     */
   public int checkCache(int block, Cache cache){
       int blockInCache = cache.getBlockNumberInCachePosition(block);
       if(blockInCache == Constant.I){ //If no block exists
           return Constant.I;
       }
       int state = cache.getBlockState(blockInCache);

       if(state == Constant.I){
           return Constant.I; //-1
       }
       if(blockInCache != block){
           return blockInCache; //mayor que o igual zero.
       }
       return Constant.HIT_DATA_CACHE; // -2
   }

    public Directory getDirectoryByBlock(int block){
        if(myProcessor.getLocalDirectory().existsInDirectory(block)){
            return myProcessor.getLocalDirectory();
        }
        return externalProcessor.getLocalDirectory();
    }

   public void setProcessor(String parentProcesorId){
       myProcessor = bus.getProcessorById(parentProcesorId);
       if(parentProcesorId == Constant.PROCESSOR_0){
           externalProcessor= bus.getProcessor(Constant.PROCESSOR_1);
           processorId = 0;
       }else{
           externalProcessor= bus.getProcessor(Constant.PROCESSOR_0);
           processorId = 1;
       }
   }

   public void increaseDurationOnDirectoryAccess(Directory dir){
       if(dir.getDirectoryID().equals(localDirectory.getDirectoryID())){
           duration += Constant.LOCAL_DIRECTORY_ACCESS;
       }else{
           duration+= Constant.REMOTE_DIRECTORY_ACCESS;
       }
   }

   public void increaseDurationOnMemoryRW(PhysicalMemory mem){
       if(mem.getIdSharedMem().equals(localMem.getIdSharedMem())){
           duration+= Constant.LOCAL_MEMORY_ACCESS;
       }else{
           duration+= Constant.REMOTE_MEMORY_ACCESS;
       }
   }

   public PhysicalMemory getPhysicalMemoryByBlock(int block){
       if ( myProcessor.getLocalPhysicalMemory().getExistenceBlockInSharedMemory(block)){
           return myProcessor.getLocalPhysicalMemory();
       }
       return externalProcessor.getLocalPhysicalMemory();
   }

    public Cache getCacheFromDirectoryBlockOnModified(Directory directory, int blockNumber){
        if(directory.getBlockState(blockNumber)==Constant.M) {
            if (directory.getExistenceInCore(blockNumber, Constant.CORE_0)) {
                return bus.getProcessorById(Constant.PROCESSOR_0).getDataCacheByCoreId(Constant.CORE_0);
            } else if (directory.getExistenceInCore(blockNumber, Constant.CORE_1)) {
                return bus.getProcessorById(Constant.PROCESSOR_0).getDataCacheByCoreId(Constant.CORE_1);
            }

            return bus.getProcessorById(Constant.PROCESSOR_1).getDataCacheByCoreId(Constant.CORE_2);
        }else{
            return null;
        }
    }

    public void writeBlockCacheToCache(Cache originCache, Cache destionationCache, int blockNumber){
        destionationCache.setBlockNumberInCachePosition(blockNumber);
        for(int word = 0; word < Constant.WORDS_IN_BLOCK; word++){
            destionationCache.writeWordOnCache(blockNumber, word,originCache.readWordFromCache(blockNumber, word));
        }

    }

    public void writeFromMemoryToCache(int block, PhysicalMemory memory, Cache cache){
        try {
            int[] blockReadFromMemory = memory.readSharedMemory(block);
            int [] wordReadFromMemory = new int[1];
            cache.setBlockNumberInCachePosition(block);
            for(int word = 0; word < Constant.WORDS_IN_BLOCK; word++){
                wordReadFromMemory[0] = blockReadFromMemory[word];
                cache.writeWordOnCache(block,word,wordReadFromMemory);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public int getDuration() {

        return duration;
    }

    public int[] propateStateInCaches(int state, int blockNumber, Directory targetDirectory){

        List<Integer> cores = new LinkedList<Integer>();
        cores.add(Constant.CORE_0);
        cores.add(Constant.CORE_1);
        cores.add(Constant.CORE_2);

        for(int i = 0; i < cores.size(); i++) {
            int currentCore = cores.get(i);
            if(currentCore != myCoreId && targetDirectory.getExistenceInCore(blockNumber, currentCore)){
                Cache dataCache;
                if(currentCore == Constant.CORE_0){
                    dataCache = bus.getProcessorById(Constant.PROCESSOR_0).getDataCacheByCoreId(Constant.CORE_0);
                }else if(currentCore == Constant.CORE_1){
                    dataCache = bus.getProcessorById(Constant.PROCESSOR_0).getDataCacheByCoreId(Constant.CORE_1);
                }else{
                    dataCache = bus.getProcessorById(Constant.PROCESSOR_1).getDataCacheByCoreId(Constant.CORE_2);
                }

                if(!bus.request(dataCache.getCacheID())){
                   //We are freeing resources outside.
                    return Constant.ABORT;
                }

                if(dataCache.existsInCache(blockNumber)) {
                    dataCache.setBlockState(blockNumber, state);
                }
                targetDirectory.setExistenceInCore(blockNumber,currentCore,Constant.OFF);
                bus.setFree(dataCache.getCacheID());

            }
        }
        return Constant.COMPLETED;

    }

    public int getCoreIdByCache(Cache cache){
        String cacheId = cache.getCacheID();
        if(cacheId.equals(Constant.DATA_CACHE_0) || cacheId.equals(Constant.INSTRUCTIONS_CACHE_0)){
            return Constant.CORE_0;
        }else if(cacheId.equals(Constant.DATA_CACHE_1) || cacheId.equals(Constant.INSTRUCTIONS_CACHE_1)){
            return Constant.CORE_1;
        }else{
            return Constant.CORE_2;
        }
    }
}


