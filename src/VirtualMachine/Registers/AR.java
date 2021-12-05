package VirtualMachine.Registers;

import VirtualMachine.Root.Register;
import VirtualMachine.RegisterController;
import VirtualMachine.RegisterController.RegisterType;

public class AR extends Register{
    public RegisterType type;
    public AR(int size){
        super(size);
        type = RegisterController.RegisterType.AR;
    }
    
    
}