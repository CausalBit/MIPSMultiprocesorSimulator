/**
 * Created by irvin on 11/12/17.
 */
public class System {


    public static void main(String [ ] args) {
        //Aquí se van a crear todos las partes simuladas del sistema. Como es la memoria, buses, etc.
        //También es la entrada del toda las simulación.
        //Después de crear estas, se puede hacer el boot up.
        Cache cacheInst0= new Cache(Constant.INSTRUCTION_CACHE_TYPE);
        Cache cacheInst1= new Cache(Constant.INSTRUCTION_CACHE_TYPE);
        Cache cacheInst2= new Cache(Constant.INSTRUCTION_CACHE_TYPE);

        Cache cacheData0= new Cache(Constant.DATA_CACHE_TYPE);
        Cache cacheData1=new Cache(Constant.DATA_CACHE_TYPE);
        Cache cacheData2= new Cache(Constant.DATA_CACHE_TYPE);

        Directory directory1= new Directory(16,0);
        Directory directory2=new Directory(8,16);
        String file;
        Core core0= new Core();
        Core core1= new Core();
        Core core2= new Core();
        Bootup bup= new Bootup(file,CacheInst);
        //metodo de boot up
        //lee la memoria de instrucciones y la alamcena en la de cache instrucciones
        //preguntar al usuario por el quantum para cada hilo/procesador

    }
}
