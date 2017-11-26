import java.util.ArrayList;
import java.util.List;

/**
 * Created by J.A Rodríguez on 25/11/2017.
 */
public class DataManagerStore {
    private Bus bus;
    private Cache myCacheData;
    int coreID;
    PhysicalMemory memLocal;
    List<String> blockedStructures;
    int duration;

    public DataManagerStore(Bus bus, Cache myCacheData, int coreID, PhysicalMemory memLocal){
        this.duration = 0;
        this.bus = bus;
        this.myCacheData = myCacheData;
        this.coreID = coreID;
        this.memLocal = memLocal;
        this.blockedStructures = new ArrayList<String>();
    }

    // Operación de store
    // Este método requiere el número de core en el cual se ejecuta la instrucción
    // Posiblemente sea buena idea colocar el ID del core como global y, al igual que las cachés locales, colocar una variable que represente al directorio local (por otro lado igual puede accederse por medio del bus).

    public int SW(int blockRequested, int wordRequested, int dataToWrite) {
        // Tenemos una lista local de bloqueos, pues se debe conocer el ID de todo lo bloqueado

        // Obtener el nombre de mi propia caché basado en el ID del core
        String myCacheName = this.getCacheName(coreID);

        // NOTA: Ahora todas las preguntas se harán como "¿no se puede bloquear X?", debido a que si preguntamos primero por el sí, cada vez se indenta más el código y no se ve bien. Si se pregunta por el no y se cumple, se retorna -1 para indicar al método padre que el store falló. Si no se cumple simplemente no entra al no y sigue directo al resto del código. Así no es necesario evaluar el sí en un if

        // ¿No se puede bloquear caché local?
        if (!(this.bus.request(myCacheName))) {
            return -1;
        }

        // Sí se puede bloquear, continúa:
        blockedStructures.add(myCacheName);

        // Se debe averiguar si la caché tiene algún bloque en la posición necesaria. De no ser así, no habría bloque víctima, sino que se escribe directamente en esa posición de la caché y solo se hacen los cálculos del bloque fuente que viene por parámetro

        // En la posición no hay ningún bloque

        // Obtener bloque en caché que está en la posición que necesito (víctima)
        int victimBlock = this.myCacheData.getBlockNumberInCachePosition(blockRequested);

        if (victimBlock == Constant.NULL_BLOCK_NUMBER) {
            this.sourceBlockCalculations(blockRequested, victimBlock, wordRequested, dataToWrite);
        }
        // Sí hay algo en la posición
        else {
            // ¿Es miss (no es el mismo bloque o es inválido)?
            if (victimBlock != blockRequested || (this.myCacheData.getBlockState(blockRequested) == Constant.I)) {
                // Calcular directorio de bloque víctima

                String victimDirectoryName = this.getDirectoryName(victimBlock);
                Directory victimDirectory = this.getBlockDirectory(victimDirectoryName);

                // ¿No se puede bloquear el directorio víctima?
                if (!(this.bus.request(victimDirectoryName))) {
                    while(blockedStructures.size() > 0){
                        this.bus.setFree(blockedStructures.remove(0));
                    }
                    return -1;
                }
                // Directorio bloqueado
                blockedStructures.add(victimDirectoryName);

                //¿El bloque de la caché víctima no está en U?
                if (victimDirectory.getBlockState(victimBlock) != Constant.U) {
                    /******************************************/
                    this.resolveOnVictim(victimBlock, victimDirectoryName, victimDirectory);
                    /******************************************/
                }
                // Tanto el no como el sí llegan aquí

                /******************************************/
                this.sourceBlockCalculations(blockRequested, victimBlock, wordRequested, dataToWrite);
                /******************************************/
            } else {
                //¿El bloque en caché está en M?

                if (this.myCacheData.getBlockState(blockRequested) == Constant.M) {
                    // Escribir en la caché el contenido del registro
                    try {
                        //int blockOfMem[] = this.memLocal.readSharedMemory(blockRequested);
                        //this.writeOnCache("Data", blockOfMem, victimBlock);
                        int[] registerData = new int[1];
                        registerData[0] = dataToWrite;
                        this.myCacheData.writeWordOnCache(victimBlock, wordRequested, registerData);
                    } catch (Exception e) {
                        return -1;
                    }
                    // FIN
                    while(blockedStructures.size() > 0){
                        this.bus.setFree(blockedStructures.remove(0));
                    }
                    return 0;
                } else {
                    // Ya se tiene el bloque fuente

                    // (NO) Calcular directorio de bloque víctima

                    // Calcular directorio de bloque fuente
                    String sourceDirectoryName = this.getDirectoryName(victimBlock);
                    Directory sourceDirectory = this.getBlockDirectory(sourceDirectoryName);

                    // ¿No se puede bloquear el directorio?
                    if(!(this.bus.request(sourceDirectoryName))){
                        while(blockedStructures.size() > 0){
                            this.bus.setFree(blockedStructures.remove(0));
                        }
                        return -1;
                    }

                    // Directorio bloqueado
                    blockedStructures.add(sourceDirectoryName);

                    /******************************************/
                    this.shareBlocksCycle(sourceDirectory, victimBlock);
                    /******************************************/

                    /******************************************/
                    this.finishStore(sourceDirectory, sourceDirectoryName, blockRequested, wordRequested, dataToWrite);
                    /******************************************/
                    // Nota: en este caso el mismo que el víctima porque el bloque dio hit*/
                }
            }
        }
        return 0;
    }

    ///////////////////////////////////////////////////
    // Submétodos para ejecución de la lógica del SW //
    ///////////////////////////////////////////////////

    private int resolveOnVictim(int victimBlock, String victimDirectoryName, Directory victimDirectory) {
        //¿El estado del bloque víctima es M en el directorio?
        if(victimDirectory.getBlockState(victimBlock) == Constant.M){

            // Obtener la memoria del bloque víctima
            String memoryName = this.getMemoryName(victimDirectoryName);
            PhysicalMemory memory = this.getMemory(memoryName);

            //¿No se puede bloquear memoria?
            if(!(this.bus.request(memoryName))){
                while(blockedStructures.size() > 0){
                    this.bus.setFree(blockedStructures.remove(0));
                }
                return -1;
            }
            // Memoria bloqueada
            this.blockedStructures.add(memoryName);

            // Guardar el bloque modificado de la caché
            this.writeFromCacheToMemory(this.myCacheData, victimBlock, memory);

            // Desbloquear memoria
            this.bus.setFree(memoryName);
            this.blockedStructures.remove(memoryName);

            // Colocar U en el bloque del directorio y 0 en bits
            victimDirectory.setBlockBits(victimBlock, victimDirectory.getModifiedCache(victimBlock) - 1, 0);
            victimDirectory.setBlockState(victimBlock, Constant.U);
        }else{
            // Indicar U o C y cambiar bits en el directorio */
            int state;
            if(victimDirectory.getTotalCachesWithBlock(victimBlock) == 1){
                state = Constant.U;
            }else{
                state = Constant.C;
            }
            victimDirectory.setBlockState(victimBlock, state);
            victimDirectory.setBlockBits(victimBlock, coreID, 0);
        }

        // Poner I el bloque en caché víctima
        this.myCacheData.setBlockState(victimBlock, Constant.I);
        return 0;
    }

    private int sourceBlockCalculations(int sourceBlock, int victimBlock, int wordRequested, int dataToWrite) {
        // Calcular el directorio fuente del bloque que quiero cargar
        String sourceDirectoryName = this.getDirectoryName(sourceBlock);
        Directory sourceDirectory = this.getBlockDirectory(sourceDirectoryName);

        // ¿No se puede bloquear el directorio del bloque fuente?
        if (!(this.bus.request(sourceDirectoryName))) {
            while(blockedStructures.size() > 0){
                this.bus.setFree(blockedStructures.remove(0));
            }
            return -1;
        }

        // Directorio fuente bloqueado
        this.blockedStructures.add(sourceDirectoryName);

        // ¿El bloque fuente del directorio está en U?
        if (sourceDirectory.getBlockState(sourceBlock) == Constant.U) {
            /******************************************/
            this.finishStore(sourceDirectory, sourceDirectoryName, sourceBlock, wordRequested, dataToWrite);
            /******************************************/
        } else {
            // ¿El bloque fuente en el directorio está en M?
            if (sourceDirectory.getBlockState(sourceBlock) == Constant.M) {
                // Obtener el nombre de la memoria del bloque fuente con el fin de almacenarlo
                String memoryName = this.getMemoryName(sourceDirectoryName);
                PhysicalMemory memory = this.getMemory(memoryName);
                // ¿No se puede bloquear la memoria?

                if (!(this.bus.request(memoryName))){
                    while(blockedStructures.size() > 0){
                        this.bus.setFree(blockedStructures.remove(0));
                    }
                    return -1;
                }
                // Memoria bloqueada
                this.blockedStructures.add(memoryName);

                // Averiguar caché fuente con directorio
                String sourceCacheName = this.getCacheName(sourceDirectory.getModifiedCache(sourceBlock) - 1);
                Cache sourceCache = this.getCache(sourceCacheName);

                // ¿No se puede bloquear la caché fuente?
                if(!(this.bus.request(sourceCacheName))){
                    while(blockedStructures.size() > 0){
                        this.bus.setFree(blockedStructures.remove(0));
                    }
                    return -1;
                }
                // Caché fuente bloqueada
                this.blockedStructures.add(sourceCacheName);

                // Copiar el bloque de la caché fuente en memoria y en caché target luego
                this.writeFromCacheToMemory(sourceCache, sourceBlock, memory);
                try {
                    int blockOfMem[] = memory.readSharedMemory(sourceBlock);
                    int realBlock = 0;
                    //if(victimBlock != -1) {
                    //    realBlock = victimBlock;
                    //}else{
                        realBlock = sourceBlock;
                    //}
                    this.myCacheData.setBlockNumberInCachePosition(realBlock);
                    this.writeOnCache("Data", blockOfMem, realBlock);
                }catch (Exception e){

                }

                // Actualizar directorio del modificado a U, de M a I en caché del modificado,
                // actualizar el directorio del fuente a M
                sourceDirectory.setBlockBits(sourceBlock, sourceDirectory.getModifiedCache(sourceBlock) - 1, 0);
                sourceDirectory.setBlockState(sourceBlock, Constant.U);
                sourceCache.setBlockState(sourceBlock, Constant.I);
                sourceDirectory.setBlockState(sourceBlock, Constant.M);
                sourceDirectory.setBlockBits(sourceBlock, this.coreID, 1);

                /******************************************/
                this.lastWrite(sourceDirectoryName, sourceBlock, wordRequested, dataToWrite);
                /******************************************/
            } else {
                /******************************************/
                int flag = this.shareBlocksCycle(sourceDirectory, sourceBlock);
                /******************************************/

                if(flag == -1){
                    return flag;
                }else {
                    /******************************************/
                    return this.finishStore(sourceDirectory, sourceDirectoryName, sourceBlock, wordRequested, dataToWrite);
                    /******************************************/
                }
            }
        }
        return 0;
    }

    private int shareBlocksCycle(Directory sourceDirectory, int victimBlock){
        int result = 0;

        // Averiguar cuántas cachés tienen el bloque compartido
        int totalShared;

        do{
            totalShared = totalShared = sourceDirectory.getTotalCachesWithBlock(victimBlock);

            // Averiguar cuáles son las cachés
            String sourceCacheName = this.getCacheName(sourceDirectory.getModifiedCache(victimBlock) - 1);
            Cache sourceCache = this.getCache(sourceCacheName);
            int cacheID = this.getCacheID(sourceCacheName);

            // ¿No se puede bloquear esta caché fuente?
            if(!(this.bus.request(sourceCacheName))){
                while(blockedStructures.size() > 0){
                    this.bus.setFree(blockedStructures.remove(0));
                }
                result = -1;
                break;
            }
            // Caché bloqueada
            this.blockedStructures.add(sourceCacheName);

            // Actualizar de C a I
            sourceCache.setBlockState(victimBlock, Constant.I);

            // Actualizar los bits del directorio y poner C o U
            int state;
            if(sourceDirectory.getTotalCachesWithBlock(victimBlock) == 1){
                state = Constant.U;
            }else{
                state = Constant.C;
            }
            sourceDirectory.setBlockState(victimBlock, state);
            sourceDirectory.setBlockBits(victimBlock, cacheID, 0);

            // Liberar caché
            this.bus.setFree(sourceCacheName);
            this.blockedStructures.remove(sourceCacheName);
        }while(totalShared > 1);

        return result;
    }

    public int finishStore(Directory sourceDirectory, String sourceDirectoryName, int sourceBlock, int wordRequested, int dataToWrite){
        // Calcular la memoria a utilizar
        String memoryName = this.getMemoryName(sourceDirectoryName);
        PhysicalMemory memory = this.getMemory(memoryName);

        // ¿No se puede bloquear la memoria?
        if(!(this.bus.request(memoryName))){
            while(blockedStructures.size() > 0){
                this.bus.setFree(blockedStructures.remove(0));
            }
            return -1;
        }

        // Memoria bloqueada
        this.blockedStructures.add(memoryName);

        // Copiar el bloque a la caché
        try {
            int blockOfMem[] = memory.readSharedMemory(sourceBlock);
            this.myCacheData.setBlockNumberInCachePosition(sourceBlock);
            this.writeOnCache("Data", blockOfMem, sourceBlock);
        } catch (Exception e) {
            return -1;
        }

        //Desbloquear memoria
        this.bus.setFree(memoryName);
        this.blockedStructures.remove(memoryName);

        // Actualizar directorio del bloque fuente a M
        sourceDirectory.setBlockState(sourceBlock, Constant.M);
        sourceDirectory.setBlockBits(sourceBlock, this.coreID, 1);
        /******************************************/
        this.lastWrite(sourceDirectoryName, sourceBlock, wordRequested, dataToWrite);
        /******************************************/
        return 0;
    }

    private void lastWrite(String directoryName, int blockNumber, int wordRequested, int dataToWrite){
        // Desbloquear directorio
        this.bus.setFree(directoryName);
        this.blockedStructures.remove(directoryName);

        // Estado en la caché víctima como M.
        this.myCacheData.setBlockNumberInCachePosition(blockNumber);
        this.myCacheData.setBlockState(blockNumber, Constant.M);

        // Escribir en la caché el contenido del registro
        int[] registerData = new int[1];
        registerData[0] = dataToWrite;
        this.myCacheData.writeWordOnCache(blockNumber, wordRequested, registerData);

        // Liberar caché
        // this.bus.setFree(this.getCacheName(this.coreID));

        while(blockedStructures.size() > 0){
            this.bus.setFree(blockedStructures.remove(0));
        }

        //FIN
    }


    ////////////////////////////////////////////////////////////////////////
    // Submétodos relacionados con obtener acceso a estructuras de datos: //
    ////////////////////////////////////////////////////////////////////////
    /**
     * Método que permite obtener el nombre de un directorio basado en el número de bloque. Se obtiene el
     * nombre del directorio al cual pertenece el bloque.
     */
    private String getDirectoryName(int block) {
        Directory directory = this.bus.getProcessor(Constant.PROCESSOR_1).getLocalDirectory();
        if(block < directory.getInitBlock()){
            return Constant.DIRECTORY_0;
        } else {
            return Constant.DIRECTORY_1;
        }
    }

    /**
     * Método que permite obtener un directorio basado su nombre.
     */
    private Directory getBlockDirectory(String name) {
        if (name.equals(Constant.DIRECTORY_0)) {
            if(this.coreID == 0 || this.coreID == 1){
                duration += Constant.LOCAL_DIRECTORY_ACCESS;
            }else{
                duration += Constant.REMOTE_DIRECTORY_ACCESS;
            }
            return this.bus.getProcessor(Constant.PROCESSOR_0).getLocalDirectory();
        } else {
            if(this.coreID == 0 || this.coreID == 1){
                duration += Constant.REMOTE_DIRECTORY_ACCESS;
            }else{
                duration += Constant.LOCAL_DIRECTORY_ACCESS;
            }
            return this.bus.getProcessor(Constant.PROCESSOR_1).getLocalDirectory();
        }
    }

    /**
     * Para obtener el nombre de alguna caché de datos basado en el ID del core o de la caché
     */
    private String getCacheName(int ID) {
        String name = "";
        switch (ID) {
            case 0:
                name = Constant.DATA_CACHE_0;
                break;
            case 1:
                name = Constant.DATA_CACHE_1;
                break;
            case 2:
                name = Constant.DATA_CACHE_2;
                break;
        }
        return name;
    }

    /**
     * Método que permite escribir en la caché de datos un bloque desde la memoria
     * @param type
     * @param blockOfMem
     */
    private void writeOnCache (String type, int[] blockOfMem, int victimBlock){
        for(int i = 0; i < Constant.WORDS_IN_BLOCK; i++){
            int wordDataToWrite[] = new int[Constant.DATA_CACHE_REAL_WORD_SIZE];
            java.lang.System.arraycopy(blockOfMem, i,  wordDataToWrite, 0, 1);//sacar una palabra del bloque
            myCacheData.writeWordOnCache (victimBlock, i, wordDataToWrite);//escribir palabra
        }
    }

    /**
     * Para obtener el nombre de la memoria a partir del nombre del directorio
     * @param directoryName
     * @return
     */
    private String getMemoryName(String directoryName){
        if(directoryName.equals(Constant.DIRECTORY_0)) {
            return Constant.SHARED_DATA_MEMORY_0;
        }else{
            return Constant.SHARED_DATA_MEMORY_1;
        }
    }

    /**
     * Para obtener la caché con base en su nombre
     * @param name
     * @return
     */
    private Cache getCache(String name){
        if(name.equals(Constant.DATA_CACHE_0) || name.equals(Constant.DATA_CACHE_1)){
            return this.bus.getProcessor(Constant.PROCESSOR_0).getCaches().get(name);
        }else{
            return this.bus.getProcessor(Constant.PROCESSOR_1).getCaches().get(name);
        }
    }

    /**
     * Para escribir un bloque de la caché de datos hacia la memoria
     * @param cache
     * @param block
     * @param memory
     */
    private void writeFromCacheToMemory(Cache cache, int block, PhysicalMemory memory){
        int[] blockOfMem = new int[4];
        for(int i = 0; i < 4; i++){
            int[] wordDataToWrite = cache.readWordFromCache(block, i);
            java.lang.System.arraycopy(wordDataToWrite, 0, blockOfMem, i, 1);//sacar una palabra del bloque
        }
        try {
            memory.writeSharedMemory(block, blockOfMem);
        }catch (Exception e){

        }
    }

    /**
     * Para obtener la memoria basándose en su nombre
     * @param name
     * @return
     */
    private PhysicalMemory getMemory(String name){
        if(name.equals(Constant.SHARED_DATA_MEMORY_0)){
            if(this.coreID == 0 || this.coreID == 1){
                duration += Constant.LOCAL_MEMORY_ACCESS;
            }else{
                duration += Constant.REMOTE_MEMORY_ACCESS;
            }
            return this.bus.getProcessor(Constant.PROCESSOR_0).getLocalPhysicalMemory();
        }else{
            if(this.coreID == 0 || this.coreID == 1){
                duration += Constant.REMOTE_MEMORY_ACCESS;
            }else{
                duration += Constant.LOCAL_MEMORY_ACCESS;
            }
            return this.bus.getProcessor(Constant.PROCESSOR_1).getLocalPhysicalMemory();
        }
    }

    private int getCacheID(String name){
        if(name.equals(Constant.DATA_CACHE_0)){
            return 0;
        }else if(name.equals(Constant.DATA_CACHE_1)){
            return 1;
        }else{
            return 2;
        }
    }

    public int getDuration(){
        return this.duration;
    }
}
