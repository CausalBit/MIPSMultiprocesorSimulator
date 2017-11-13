/**
 * Created by tonyViquez on 11/12/17.
 */
 import java.io.BufferedReader;
 import java.io.FileNotFoundException;
 import java.io.FileReader;
 import java.io.IOException;

public class Bootup {
    private int ite;
    private String file;
    private Cache cacheInst;
    public Bootup(String file, Cache cacheInst){
        ite=0;
        this.file = file;
        this.cacheInst=cacheInst;
    }

    public static void readData(String file) throws FileNotFoundException, IOException {
        String line;
        FileReader f = new FileReader(file);
        BufferedReader b = new BufferedReader(f);
        while((line = b.readLine())!=null) {
              //store in instructios cach
            String[] tokens = line.split(" ");
            for(int i = 0 ; i < 3;i++) {

                cacheInst.writeWordOnCache(0,i,Integer.parseInt(tokens[i]));
            }
        }
        b.close();
    }

}
