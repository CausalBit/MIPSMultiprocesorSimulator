import java.util.HashMap;

/**
 * Created by irvin on 11/13/17.
 */
public class Instruction {
    int PC;
    HashMap<String, Integer> registers;
    //TODO


    /*
    constructor of class
    */
    public Instruction(int PC, HashMap<String, Integer> registers) {
        //TODO pasar la referencia de BUSES.
        this.PC = PC;
        this.registers = registers;
    }

    public int decodreAndExecute(int[] instruction){
        int duration = 0;
        //switch.
        //calcular la cantidad de cicles que se llevo en la instrucción (aunque no haya terminaod).
        return duration;
    }

    /*
    */
    private int  DADDI(int regTarget, int regSource ,int num) {
        registers.put(Integer.toString( regTarget) , registers.get(regSource)+num);
        PC=PC+1; //TODO la pc se actualiza a la hora de leer insturcción en la clase core, para
        //TODO abortar entonces se quita -4.

        return 1; //TODO hacer que retorne enteros de cantidades de ciclos que se tomó.
    }


    //TODO HACER "FETCH" para cache de instrucciones que retorna una cantidad de ciclose que dura.


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
