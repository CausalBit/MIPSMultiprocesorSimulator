import java.util.ArrayList;
import java.util.List;

/**
 * Created by irvin on 11/13/17.
 */
public class Processor {
    List<Core> cores;

    public Processor(int numberOfCores){
        cores = new ArrayList<Core>();
        //cores.add()
        //constuir caches!
    }

    public void bootUp(){
        //Tomar un directorio y tomar todos los archivos de ese directorio como hilillos a subir.
        //Subir a los hilillos a memoria de intruciones
        //para cada hilillo.
        //Crear nuevos contextos para la cola de contextos del procesos que comparten los núcleo.
        //Cada dirección en la memoria de instruciones donde inicia un hilillo, es el PC inicial un contexto
        //inicial de la cola.
    }
}
