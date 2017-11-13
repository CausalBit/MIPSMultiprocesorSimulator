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

    public static void readData(String file) throws Exception {
        String line;
        FileReader f = new FileReader(file);
        BufferedReader b = new BufferedReader(f);
        while((line = b.readLine())!=null) {
              //store in instructios cach
            String[] tokens = line.split(" ");
            int blockInst [] = new int [4];
            for(int i =0 ; i < 4; i++)
            blockInst[i] = Integer.parseInt(tokens[i]) ;
            memInst.writeInstructionMemory(ite,blockInst);
            ite++;
        }
        b.close();
    }

}
