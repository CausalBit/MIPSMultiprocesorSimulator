import java.lang.*;
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

       //Bock the local cache!
       if (!bus.request(cacheLocalId)) {
           releaseAllResources();
           return null;
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
               return null; //We couldn't get the resource, so we ABORT!
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
                       return null;
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
               if(victimBlockDirectory.getBlockState(Constant.CORE_0)  == Constant.OFF &&
                       victimBlockDirectory.getBlockState(Constant.CORE_1)  == Constant.OFF &&
                       victimBlockDirectory.getBlockState(Constant.CORE_2) == Constant.OFF){
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
               return null; //We couldn't get the resource, so we ABORT!
           }
           addLockableResource(targetDirectory.getDirectoryID());
       }

       PhysicalMemory targetMemory = getPhysicalMemoryByBlock(blockNumber);
       if(!bus.request(targetMemory.getIdSharedMem())){
           releaseAllResources();
           return null; //We couldn't get the resource, so we ABORT!
       }
       addLockableResource(targetMemory.getIdSharedMem());
       if(targetDirectory.getBlockState(blockNumber) == Constant.M){

           Cache targetCache = getCacheFromDirectoryBlockOnModified(targetDirectory,blockNumber);
           //TODO precondition with null targetCache
           if(!bus.request(targetCache.getCacheID()) ){
               releaseAllResources();
               return null;
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
           //bus.setFree(targetMemory.getIdSharedMem());//TODO, duda de si es necesario liberar ya, ya está en la cola y ahi lo libera

       }


       localCache.setBlockState(blockNumber,Constant.C);
       int [] resultingWord = localCache.readWordFromCache(blockNumber,wordNumber);
       releaseAllResources();
       return resultingWord;
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
    for(int resourcesQuantity = 0 ; resourcesQuantity < lockedResources.size(); resourcesQuantity++){
        bus.setFree(lockedResources.pop());//WARNING
    }
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
       //TODO validate for wrong block that doesnt exist in any physical memory.
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

}


