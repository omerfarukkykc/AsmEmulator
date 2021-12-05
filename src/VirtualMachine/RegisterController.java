package VirtualMachine;
import VirtualMachine.Registers.IR;
import VirtualMachine.Registers.AR;
import VirtualMachine.Registers.DR;
import VirtualMachine.Registers.SP;
import VirtualMachine.Registers.AC;
import VirtualMachine.Registers.PC;
public class RegisterController {
    AR ar;
    PC pc;
    DR dr;
    AC ac;
    IR ir;
    SP sp;
    public static enum RegisterType{
        AR,PC,DR,AC,IR,SP
    }
    public RegisterController(){
         ar = new AR(4);
         pc = new PC(4);
         dr = new DR(9);
         ac = new AC(9);
         ir = new IR(9);
         sp = new SP(3);
    }
    public void reset(){
        ar.Clear();
        pc.Clear();
        dr.Clear();
        ac.Clear();
        ir.Clear();
        sp.Clear();
    }
    
    
    
    
    
    
    
    
}
