package VirtualMachine.Registers;

import VirtualMachine.Root.Register;
import VirtualMachine.RegisterController;
import VirtualMachine.RegisterController.RegisterType;

public class DR extends Register{
    public RegisterType type;
    public DR(int size){
        super(size);
        type = RegisterController.RegisterType.DR;
    }
    

}