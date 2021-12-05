package VirtualMachine.Registers;

import VirtualMachine.Root.Register;
import VirtualMachine.RegisterController;
import VirtualMachine.RegisterController.RegisterType;

public class IR extends Register{
    
    public RegisterType type;
    public IR(int size){
        super(size);
        type = RegisterController.RegisterType.IR;
    }
    



}