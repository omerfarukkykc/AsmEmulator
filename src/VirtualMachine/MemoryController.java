package VirtualMachine;

import VirtualMachine.Memory.*;

public class MemoryController {
    public static DataSegment DS;
    public static CodeSegment CS;
    public static StackSegment SS;
    public MemoryController(){
        DS = new DataSegment(16,9);
        CS = new CodeSegment(16,9);
        SS = new StackSegment(8,9);
    }
    public void reset(){
       DS.reset();
       CS.reset();
       SS.reset();
    }
}
