/**
 * Created by irvin on 11/9/17.
 */
public final class Constant {

    public static final String INSTRUCTION_MEMORY_TYPE = "instructionMemoryType";
    public static final String SHARED_MEMORY_TYPE = "sharedMemoryType";

    //Dictionary & Cache Tags.
    public static final int I = -1; //Invalid
    public static final int U = 0; //Uncached, used as Dictionary state only.
    public static final int M = 1; //Modified
    public static final int C = 2; //Shared in Spanish = Compartido.

    public static final int DICTIONARY_STATE = 0; //State index in the dictionary data structure.

    //Processors ids
    public static final int PROCESSOR_1 = 1;
    public static final int PROCESSOR_2 = 2;
    public static final int PROCESSOR_3 = 3;

    //Processor dictionary existence
    public static final int OFF = 0;
    public static final int ON = 1;



    private Constant(){
    }
}
