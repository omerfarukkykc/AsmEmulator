package VirtualMachine.Registers;

import VirtualMachine.Root.Register;
import VirtualMachine.RegisterController;
import VirtualMachine.RegisterController.RegisterType;

public class SP extends Register{

    public RegisterType type;
    public SP(int size){
        super(size);
        type = RegisterController.RegisterType.SP;
    }
    
    
  
    


}
