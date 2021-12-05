package VirtualMachine;

import Command.Command;
import AsmMain.DebuggerController;
import Command.CommandType;
import Command.CommandType.CommandCategory;
import static Command.CommandType.ORG;
import java.util.ArrayList;

public class CommandController {
    RegisterController registers;
    public CommandController(RegisterController RGC){
        this.registers = RGC;
    }
    public void ORG(ArrayList<Command> commands){
        commands.forEach(command->{ 
            if(command.commandType == ORG){
                if(command.tag!=null&&command.adress!=null){
                    if(command.tag.equals("C")){
                        MemoryController.CS.setStartAdress(Integer.parseInt(command.adress));
                        this.registers.pc.Increament(Integer.parseInt(command.adress));
                        LogicGatesController.startAdress = Integer.parseInt(command.adress);
                    }else if (command.tag.equals("D")) {
                        MemoryController.DS.setStartAdress(Integer.parseInt(command.adress));
                    }else{
                         MemoryController.SS.setStartAdress(Integer.parseInt(command.adress));
                    }
                }
            }
        });
        commands.forEach(command->{ 
            if(command.tag != null && command.commandCategory == CommandCategory.PESUDO&&command.commandType != CommandType.ORG){
                int[] binary = CoderController.toBinaryArray(command.adress);
                BusController.setBus(binary);
                MemoryController.DS.Set(command.tag);
            }else{
                if(command.commandType== CommandType.DEC||command.commandType== CommandType.HEX){
                    int[] binary = CoderController.toBinaryArray(command.adress);
                    BusController.setBus(binary);
                    MemoryController.DS.Set();
                }
            }
        });
        commands.forEach(command->{ //Komutları yükle
            if(command.commandCategory != CommandCategory.PESUDO){
                if(command.tag != null){
                    //Etiketli Komut Set edilir
                    int[] binary = command.toBinary();
                    if(binary !=null){
                        BusController.setBus(binary);
                        MemoryController.CS.Set();
                    }
                    int arr[] = CoderController.toBinaryArray(MemoryController.CS.getLastAdress());
                    BusController.setBus(arr);
                    MemoryController.DS.Set(command.tag);
                }else{
                    //Komutlarr Set Edilir
                    int[] binary = command.toBinary();
                    if(binary !=null){
                        BusController.setBus(binary);
                        MemoryController.CS.Set();
                    }
                }
            }
        });
        
        
    }
    public void END(){
       DebuggerController.infoBoxView("END komutu çalıştı program bitti.", " Program Sonu");
    }
    public void OR(Command command){
        registers.ac.logic.OR(MemoryController.DS.toBinary(command.adress));
    }
    public void AND(Command command){ 
        registers.ac.logic.AND(MemoryController.DS.toBinary(command.adress));
    }
    public void XOR(Command command){
        registers.ac.logic.XOR(MemoryController.DS.toBinary(command.adress));
    }
    public void ADD(Command command){
        registers.ac.arithmetic.ADD(MemoryController.DS.toBinary(command.adress));
    }
    public void LDA(Command command){
        BusController.setBus(MemoryController.DS.toBinary(command.adress));
        registers.ac.Load();
        ClockController.increament();
    }
    public void STA(Command command){
        MemoryController.DS.Update(command.adress, registers.ac.toArray());
        

    }
    public void BUN(Command command){
        BusController.setBus(MemoryController.DS.toBinary(command.adress));
        registers.pc.Load();
    }
    public void BSA(){
        
    }
    public void ISZ(Command command){
        BusController.setBus(MemoryController.DS.toBinary(command.adress));
        registers.ac.Load();
        registers.ac.Increament();
        if(registers.ac.getValue()==0){
            registers.pc.Increament();
        }
                
    }
    public void CLA(){
       
        registers.ac.Clear();
        
    }
    public void SZA(){
        if(registers.ac.getValue() == 0){
            registers.pc.Increament();
        }
    }
    public void SNA(){
        if(registers.ac.getValue() < 0){
            registers.pc.Increament();
        }
    }
    public void CMA(){
        registers.ac.logic.COMPLEMENT();
    }
    public void INC(){
        registers.ac.arithmetic.INC();
    }
    public void ASHR(){
        registers.ac.arithmetic.ASHR();
    }
    public void ASHL(){
        registers.ac.arithmetic.ASHL();
    }
    public void SZE(){
        
    }
    public void HLT(){
        DebuggerController.infoBoxView("Program bitti.","HLT");
    }
    public void INP(){
        
    }
    public void PUSH(){
        registers.sp.Increament();
        BusController.setBus(registers.dr.toArray());
        MemoryController.SS.Set();
    }
    public void POP(){
        BusController.setBus(MemoryController.SS.toBinary(registers.sp.getValue()));
        registers.dr.Load();
        registers.sp.Decrease();
    }
    public void SZEmpty(){
        if(registers.sp.getValue() == 0){
            registers.pc.Increament();
        }
    }
    public void SZFull(){
         if(registers.sp.getValue() == 7){
            registers.pc.Increament();
        }
    }
    public void Fetch(){
        
    }
    public void Decode(){
        
    }
    public void Interrupt(){
        
    }
    
    
}
