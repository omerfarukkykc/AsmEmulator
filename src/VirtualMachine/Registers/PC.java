package VirtualMachine.Registers;

import VirtualMachine.Root.Register;
import VirtualMachine.RegisterController;
import VirtualMachine.RegisterController.RegisterType;

public class PC extends Register{
    public RegisterType type;
    public PC(int size){
        super(size);
        type = RegisterController.RegisterType.PC;
    }
    

}