/**
 * Created by irvin on 11/12/17.
 */
public class System {

    public static void main(String [ ] args) {
        //Aquí se van a crear todos las partes simuladas del sistema. Como es la memoria, buses, etc.
        //TODO cola de semaphore general para el barrier para los 3 cores
        //pide quantum
        PhysicalMemory memP0 = new PhysicalMemory(16,0,24,0);
        PhysicalMemory memP1 = new PhysicalMemory(8,16,16,24);
        Buses buses= new Buses();
        Processor p1 = new Processor(0,1,memP0);//aqui se crean los nucleos, las caches I D y los directorios
        p1.bootUp();
        Processor p2 = new Processor(2,memP1);
        p2.bootUp();
        //run cores
        p1.runCore(0);
        p1.runCore(1);
        p2.runCore(2);
        //También es la entrada del toda las simulación.
        //Después de crear estas, se puede hacer el boot up.




    }
}
