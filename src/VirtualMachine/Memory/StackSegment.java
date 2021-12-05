package VirtualMachine.Memory;

import VirtualMachine.BusController;
import VirtualMachine.Root.Memory;

public class StackSegment extends Memory{
    public StackSegment(int size,int bitSize){
      super(size, bitSize);
   }
    public void Set(int id){
        ram[id] = BusController.getBus().clone();
    }

    public void clear(int value) {
        ram[value] = new int[ram[value].length];
    }
   
    
}
