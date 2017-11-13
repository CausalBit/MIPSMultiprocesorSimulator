/**
 * Created by tonyViquez on 11/12/17.
 */
 import java.util.HashMap;

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

   /*

   */
   public void DADDI(int regTarget, int regSource ,int num) {
   registers.put(Integer.toString( regTarget) , registers.get(regSource)+num);
   PC=PC+1;
   }


   /*

   */
   public void DADD ( int regTarget, int regSource1, int regSource2){
   registers.put(Integer.toString(regTarget), registers.get(regSource1)+registers.get(regSource2));
   PC=PC+1;
   }

   /*

   */
   public void DSUB( int regTarget, int regSource1, int regSource2){
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
   public void DDIV( int regTarget, int regSource1, int regSource2){
   registers.put(Integer.toString(regTarget) ,( registers.get(regSource1) / registers.get(regSource2)));
   PC++;
   }

   /*

   */
   public void BEQZ ( int regTarget, int etiqueta){
       if(registers.get(regTarget) == 0 ){
            PC= etiqueta;
        }else{
            PC++;
        }
   }

   /*

   */
   public void BNEQZ ( int regTarget,  int etiqueta){
       if (registers.get(regTarget) != 0){
            PC= etiqueta;
        }else{
                PC++;
            }

   }

   /*

   */
   public void JAL(int etiqueta){
   registers.put(Integer.toString(31), PC+1);
   PC= etiqueta;
   }

   /*

   */
   public void JR (int regSource){
   PC= registers.get(regSource);
   }

   /*

   */
   public void FIN(){
    //saveContext();
    //running = false;
   }



 }
