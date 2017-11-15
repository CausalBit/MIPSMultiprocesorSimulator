/**
 * Created by irvin on 11/12/17.
 */
public class System {

    public static void main(String [ ] args) {
        //Aquí se van a crear todos las partes simuladas del sistema. Como es la memoria, buses, etc.
        //TODO cola de semaphore general para el barrier para los 3 cores
        //pide quantum
        //es necesario crearlas cachesdata aqui ya que se deben compartir para cada procesador , se pueden compartir en algun momento
        List <Cache> cacheData;
        int pc;
        cacheData = new ArrayList<Cache>();
        cacheData.add(new Cache(Constant.DATA_CACHE_TYPE));
        cacheData.add(new Cache(Constant.DATA_CACHE_TYPE));
        cacheData.add(new Cache(Constant.DATA_CACHE_TYPE));
        int quantum;
        PhysicalMemory memP0 = new PhysicalMemory(16,0,24,0);
        PhysicalMemory memP1 = new PhysicalMemory(8,16,16,24);
        Buses buses= new Buses();
<<<<<<< HEAD
        //TODO averiguar path del directorio donde estoy
        Processor p1 = new Processor(0,1,memP0,memP1,quantum,buses, cacheData);//aqui se crean los nucleos, las caches I D y los directorios
        p1.bootUp("Path");
        Processor p2 = new Processor(2,memP1,memP0, quantum,buses,cacheData);
        p2.bootUp("Path");
=======
        Processor p1 = new Processor(0,1,memP0);//aqui se crean los nucleos, las caches I D y los directorios
        p1.bootUp("./src/main/threads/t1");
        Processor p2 = new Processor(2,memP1);
        p2.bootUp("./src/main/threads/t2");
>>>>>>> f74d3c9e034ddf1416c4c2fb6b1e4e567e2c2333
        //run cores
        p1.runCore(0);
        p1.runCore(1);
        p2.runCore(2);
        //También es la entrada del toda las simulación.
        //Después de crear estas, se puede hacer el boot up.




    }
}
