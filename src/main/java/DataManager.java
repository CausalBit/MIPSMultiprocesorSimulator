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
       lockedResources = new Stack<String>();
   }
   public int[] loadWordProcedure(int wordNumber, int blockNumber){
        this.duration = 0;
       //Bock the local cache!
       if (!bus.request(cacheLocalId)) {
           releaseAllResources();
           return Constant.ABORT;
       }
       addLockableResource(cacheLocalId);
       int victimBlock = checkCache(blockNumber, localCache);


       if (victimBlock == Constant.HIT_DATA_CACHE) {
           int[] resultingWord = localCache.readWordFromCache(blockNumber, wordNumber);
           return resultingWord; //return the desired word, because it is a HIT!
       }

       //From this point on, we are dealing with a MISS :(
       String victimBlockDirectoryId = "";

       if (victimBlock != Constant.I){//If it is invalid, victimBlock is a -1 for invalid.

           Directory victimBlockDirectory = getDirectoryByBlock(victimBlock);//Let's identify the directory of the desired block.
           victimBlockDirectoryId =  victimBlockDirectory.getDirectoryID();
           //Now, let's attempt
           if(!bus.request(victimBlockDirectoryId)){
               releaseAllResources();
               return Constant.ABORT; //We couldn't get the resource, so we ABORT!
           }
           addLockableResource(victimBlockDirectoryId); //Because we are going to secure another secure, we need
           //to add the previous obtain resource in a stack to make sure to free it before aborting.

           int victimBlockStateInDirectory = victimBlockDirectory.getBlockState(victimBlock);

           if(victimBlockStateInDirectory==Constant.M){
               if(victimBlockDirectory.getExistenceInCore(victimBlock, myCoreId)){
                    //Save in memory the victim block, before writing on the cache.
                   String victimBlockMemoryId = getIdPhysicalMemory(victimBlock);
                   if(!bus.request(victimBlockMemoryId)){
                       releaseAllResources();
                       return Constant.ABORT;
                   }
                   //We are not going to request any other resource, allow this one memory outside of the resources stack.
                   writeBlockToMemory(victimBlock,getPhysicalMemoryByBlock(victimBlock), localCache);
                   victimBlockDirectory.setExistenceInCore(victimBlock,myCoreId, Constant.OFF);//solo es necesario para el que estaba en M on bit igual a 1
                   victimBlockDirectory.setBlockState(victimBlock,Constant.U);
                   bus.setFree(victimBlockMemoryId);
               }//If it's in the M state, on a different cache than the local cache, we don't care.
           }else{//This is actually in the C state.
               if(victimBlockDirectory.getExistenceInCore(victimBlock, myCoreId)){
                   victimBlockDirectory.setExistenceInCore(victimBlock,myCoreId, Constant.OFF);
               }
               //TODO refactor this boy!
               if(!victimBlockDirectory.getExistenceInCore(victimBlock,Constant.CORE_0) &&
                       !victimBlockDirectory.getExistenceInCore(victimBlock, Constant.CORE_1) &&
                       !victimBlockDirectory.getExistenceInCore(victimBlock,Constant.CORE_2)){
                   victimBlockDirectory.setBlockState(victimBlock,Constant.U);
               }

           }
           localCache.setBlockState(victimBlock,Constant.I);//Has the requirement of actually having the victimBlock to set the state.
       }//fin de no invalido
       //FOR ALL!!

       Directory targetDirectory = getDirectoryByBlock(blockNumber);
       if(!victimBlockDirectoryId.equals(targetDirectory.getDirectoryID())){
           if(!victimBlockDirectoryId.equals("")) {
               removeLockableResource(victimBlockDirectoryId);
               bus.setFree(victimBlockDirectoryId);
           }

           if(!bus.request(targetDirectory.getDirectoryID())){
               releaseAllResources();
               return Constant.ABORT; //We couldn't get the resource, so we ABORT!
           }
           addLockableResource(targetDirectory.getDirectoryID());
       }

       PhysicalMemory targetMemory = getPhysicalMemoryByBlock(blockNumber);
       if(!bus.request(targetMemory.getIdSharedMem())){
           releaseAllResources();
           return Constant.ABORT; //We couldn't get the resource, so we ABORT!
       }

       if(targetDirectory.getBlockState(blockNumber) == Constant.M){
           addLockableResource(targetMemory.getIdSharedMem());
           Cache targetCache = getCacheFromDirectoryBlockOnModified(targetDirectory,blockNumber);
           //TODO precondition with null targetCache
           if(!bus.request(targetCache.getCacheID()) ){
               releaseAllResources();
               return Constant.ABORT;
           }

           writeBlockToMemory(blockNumber, targetMemory, targetCache);
           writeBlockCacheToCache(targetCache,localCache,blockNumber);
           targetDirectory.setBlockState(blockNumber, Constant.C);
           targetDirectory.setExistenceInCore(blockNumber,myCoreId,Constant.ON);
           bus.setFree(targetCache.getCacheID());

       }else{//if state of cache is C or I
          // se averigua la memoria y se intenta bloquear , all afuera del if
           if(targetDirectory.getBlockState(blockNumber) != Constant.C){
               targetDirectory.setBlockState(blockNumber,Constant.C);
           }
           targetDirectory.setExistenceInCore(blockNumber, myCoreId, Constant.ON);
           writeFromMemoryToCache(blockNumber, targetMemory,localCache);
           bus.setFree(targetMemory.getIdSharedMem());//TODO, duda de si es necesario liberar ya, ya está en la cola y ahi lo libera

       }


       localCache.setBlockState(blockNumber,Constant.C);
       int [] resultingWord = localCache.readWordFromCache(blockNumber,wordNumber);
       releaseAllResources();
       return resultingWord;
   }

   public int[] storeWordProcedure(int wordNumber, int blockNumber, int[] data){
        duration=0;
       //Let's block our cache
       if(!bus.request(localCache.getCacheID())){
           return Constant.ABORT;
       }
       addLockableResource(localCache.getCacheID());

       int resultCacheCheckBlock = checkCache(blockNumber, localCache);
       String victimBlockDirectoryId = "";

       if(resultCacheCheckBlock != Constant.I){

           if(resultCacheCheckBlock == Constant.HIT_DATA_CACHE){

               Directory targetDirectory = getDirectoryByBlock(blockNumber);
               if(!bus.request(targetDirectory.getDirectoryID())){
                   releaseAllResources();
                   return Constant.ABORT;
               }
               addLockableResource(targetDirectory.getDirectoryID());

               //Update caches with Shared status.
               int[] updateResult = propateStateInCaches(Constant.I,blockNumber,targetDirectory);
               if(updateResult==Constant.ABORT){
                   releaseAllResources();
                   return Constant.ABORT;
               }

               targetDirectory.setBlockState(blockNumber,Constant.M);
               targetDirectory.setExistenceInCore(blockNumber,myCoreId,Constant.ON);
               localCache.setBlockState(blockNumber,Constant.M);
               localCache.writeWordOnCache(blockNumber,wordNumber,data);
               releaseAllResources();
               return Constant.COMPLETED;
           }//hit end
           int victimBlock = resultCacheCheckBlock;
           Directory victimDirectory = getDirectoryByBlock(victimBlock);
           victimBlockDirectoryId = victimDirectory.getDirectoryID();

           if(!bus.request(victimDirectory.getDirectoryID())){
               releaseAllResources();
               return Constant.ABORT;
           }
           addLockableResource(victimDirectory.getDirectoryID());
           int stateVictimBlock = victimDirectory.getBlockState(victimBlock);

           if(stateVictimBlock== Constant.M && victimDirectory.getExistenceInCore(victimBlock,myCoreId)){
               PhysicalMemory memoryVictimBlock = getPhysicalMemoryByBlock(victimBlock);
               if(!bus.request(memoryVictimBlock.getIdSharedMem())){
                   releaseAllResources();
                   return  Constant.ABORT;
               }
                   //dont add to stack
               writeBlockToMemory(victimBlock,memoryVictimBlock,localCache);
               localCache.setBlockState(victimBlock,Constant.I);
               victimDirectory.setBlockState(victimBlock,Constant.U);
               victimDirectory.setExistenceInCore(victimBlock,myCoreId,Constant.OFF);
               bus.setFree(memoryVictimBlock.getIdSharedMem());

           }else{
               victimDirectory.setExistenceInCore(victimBlock,myCoreId,Constant.OFF);
               if(!victimDirectory.getExistenceInCore(victimBlock,Constant.CORE_0) &&
                       !victimDirectory.getExistenceInCore(victimBlock, Constant.CORE_1) &&
                       !victimDirectory.getExistenceInCore(victimBlock,Constant.CORE_2)){
                   victimDirectory.setBlockState(victimBlock,Constant.U);

               }
               localCache.setBlockState(victimBlock,Constant.I);

           }
       }//not invalid end
       Directory targetDirectory = getDirectoryByBlock(blockNumber);

       //Do we need to get ad lock a different directory?
       if(!victimBlockDirectoryId.equals(targetDirectory.getDirectoryID())){
           if(!victimBlockDirectoryId.equals("")){
               //Let's ask for what we want
               removeLockableResource(victimBlockDirectoryId);
               bus.setFree(victimBlockDirectoryId);
           }

           if(!bus.request(targetDirectory.getDirectoryID())){
               releaseAllResources();
               return Constant.ABORT;
           }
           addLockableResource(targetDirectory.getDirectoryID());
       }

       PhysicalMemory targetMemory = getPhysicalMemoryByBlock(blockNumber);
       if(!bus.request(targetMemory.getIdSharedMem())){
           releaseAllResources();
           return Constant.ABORT;
       }
       addLockableResource(targetMemory.getIdSharedMem());

       if(targetDirectory.getBlockState(blockNumber) == Constant.M){
           Cache targetCache = getCacheFromDirectoryBlockOnModified(targetDirectory, blockNumber);
           //TODO preconditions for handling null targetCache.
           if(!bus.request(targetCache.getCacheID())){
               releaseAllResources();
               return Constant.ABORT;
           }
           //Change cache from M to I
           targetCache.setBlockState(blockNumber, Constant.I);
           writeBlockToMemory(blockNumber,targetMemory,targetCache);
           writeBlockCacheToCache(targetCache,localCache,blockNumber);

           targetDirectory.setExistenceInCore(blockNumber,myCoreId,Constant.OFF);
           bus.setFree(targetCache.getCacheID());
       }else{
           //We are C or I
           //Update caches with Shared status.
           if(targetDirectory.getBlockState(blockNumber)==Constant.C ) {
               int[] updateResult = propateStateInCaches(Constant.I, blockNumber, targetDirectory);
               if (updateResult == Constant.ABORT) {
                   releaseAllResources();
                   return Constant.ABORT;
               }
           }
           targetDirectory.setBlockState(blockNumber,Constant.M);
           targetDirectory.setExistenceInCore(blockNumber, myCoreId, Constant.ON);
           writeFromMemoryToCache(blockNumber,targetMemory,localCache);
       }

       localCache.setBlockState(blockNumber, Constant.M);
       localCache.writeWordOnCache(blockNumber,wordNumber,data);
       releaseAllResources();
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

   public void releaseAllResources(){
       int initSize = lockedResources.size();
   // for(int resourcesQuantity = 0 ; resourcesQuantity < initSize ; resourcesQuantity++){
   //     bus.setFree(lockedResources.pop());//WARNING
   // }
       bus.freeOwnedByCurrentThread();
   }

   public void addLockableResource(String resource){
       lockedResources.add(resource);
   }

   public void removeLockableResource(String resource){
       lockedResources.remove(resource);
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
            duration += Constant.LOCAL_DIRECTORY_ACCESS;
            return myProcessor.getLocalDirectory();
        }
        duration+= Constant.REMOTE_DIRECTORY_ACCESS;
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

   public PhysicalMemory getPhysicalMemoryByBlock(int block){
       if ( myProcessor.getLocalPhysicalMemory().getExistenceBlockInSharedMemory(block)){
           duration+= Constant.LOCAL_MEMORY_ACCESS;
           return myProcessor.getLocalPhysicalMemory();
       }
       duration+= Constant.REMOTE_MEMORY_ACCESS;
       return externalProcessor.getLocalPhysicalMemory();
   }
    public String getIdPhysicalMemory(int block){
        if ( myProcessor.getLocalPhysicalMemory().getExistenceBlockInSharedMemory(block)){
            return myProcessor.getLocalPhysicalMemory().getIdSharedMem();
        }
          return externalProcessor.getLocalPhysicalMemory().getIdSharedMem();

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
                    releaseAllResources();
                    return Constant.ABORT;
                }
                if(dataCache.existsInCache(blockNumber)) {
                    dataCache.setBlockState(blockNumber, state);
                }
                targetDirectory.setExistenceInCore(blockNumber,currentCore,Constant.OFF);
                addLockableResource(dataCache.getCacheID());
            }
        }
        return Constant.COMPLETED;

    }
}


