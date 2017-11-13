import java.util.HashMap;

/**
 * Created by irvin on 11/13/17.
 */
public class Instruction {
    int PC;
    HashMap<String, Integer> registers;

    /*
    constructor of class
    */
    public Instruction(int PC, HashMap<String, Integer> registers) {
        this.PC = PC;
        this.registers = registers;
    }

    public

    /*
    */
    private void DADDI(int regTarget, int regSource ,int num) {
        registers.put(Integer.toString( regTarget) , registers.get(regSource)+num);
        PC=PC+1;
    }


    /*

    */
    private void DADD ( int regTarget, int regSource1, int regSource2){
        registers.put(Integer.toString(regTarget), registers.get(regSource1)+registers.get(regSource2));
        PC=PC+1;
    }

    /*

    */
    private void DSUB( int regTarget, int regSource1, int regSource2){
        registers.put(Integer.toString(regTarget), (registers.get(regSource1)-registers.get(regSource2)) );
        PC=PC+1;
    }

    /*

    */
    public void DMUL( int regTarget, int regSource1, int regSource2){
        registers.put(Integer.toString(regTarget) ,( registers.get(regSource1)*registers.get(regSource2)));
        PC++;
    }

    /*

    */
    private void DDIV( int regTarget, int regSource1, int regSource2){
        registers.put(Integer.toString(regTarget) ,( registers.get(regSource1) / registers.get(regSource2)));
        PC++;
    }

    /*

    */
    private void BEQZ ( int regTarget, int etiqueta){
        if(registers.get(regTarget) == 0 ){
            PC= etiqueta;
        }else{
            PC++;
        }
    }

    /*

    */
    private void BNEQZ ( int regTarget,  int etiqueta){
        if (registers.get(regTarget) != 0){
            PC= etiqueta;
        }else{
            PC++;
        }

    }

    /*

    */
    private void JAL(int etiqueta){
        registers.put(Integer.toString(31), PC+1);
        PC= etiqueta;
    }

    /*

    */
    private void JR (int regSource){
        PC= registers.get(regSource);
    }

    /*

    */
    private void FIN(){
        //saveContext();
        //running = false;
    }
}
