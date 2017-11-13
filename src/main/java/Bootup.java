/**
 * Created by tonyViquez on 11/12/17.
 */
 import java.io.BufferedReader;
 import java.io.FileNotFoundException;
 import java.io.FileReader;
 import java.io.IOException;

public class Bootup {
    private static int ite;
    private String file;
    private static PhysicalMemory memInst;
    public Bootup(String file, PhysicalMemory memInst){
        ite=0;
        this.file = file;
        this.memInst=memInst;
    }

    //TODO lo mete en Processor
    public static void readData(String file) throws Exception {
        String line;
        FileReader f = new FileReader(file);
        BufferedReader b = new BufferedReader(f);
        while((line = b.readLine())!=null) {
              //store in instructios cach
            String[] tokens = line.split(" ");
            //TODO RECORDAR GUARDAR CUATRO LINEAS POR BLOQUE!!!!!!!!
            int blockInst [] = new int [Constant.INSTRUCTION_EMPTY_BLOCK.length];
            for(int i =0 ; i < Constant.INSTRUCTION_EMPTY_BLOCK.length; i++)
            blockInst[i] = Integer.parseInt(tokens[i]) ;
            memInst.writeInstructionMemory(ite,blockInst);
            ite++;
        }
        b.close();
    }

}
