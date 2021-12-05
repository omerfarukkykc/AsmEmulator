package VirtualMachine;

import AsmMain.Log;
import Command.Command;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class VirtualMachineController {
    private final CommandController CC;
    private ArrayList<Command> commands;
    private final RegisterController registers;
    private final MemoryController memory;
    private final BusController bus;
    private final LogicGatesController LGC;

    
    public VirtualMachineController(ArrayList<Command> commands){
        this.bus = new BusController();
        this.memory = new MemoryController();
        this.registers = new RegisterController();
        this.CC = new CommandController(registers);
        this.commands =  commands;
        this.LGC = new LogicGatesController(registers);
    }
    public VirtualMachineController(){
        this.bus = new BusController();
        this.memory = new MemoryController();
        this.registers = new RegisterController();
        this.CC = new CommandController(registers);
        this.LGC = new LogicGatesController(registers);
    }
    public void setCommands(ArrayList<Command> commands){
        this.commands = commands;
    }
    public void Execute(ArrayList<Command> commands){
        commands.forEach(command -> {
            Step(command);
        });
    }
    public void Step(Command command){
        CC.Fetch();
        CC.Decode();
        CC.Interrupt();
        switch(command.commandType){
            case ORG -> CC.ORG(this.commands);
            case END -> CC.END();
            case OR -> CC.OR(command);
            case AND -> CC.AND(command);
            case XOR -> CC.XOR(command);
            case ADD -> CC.ADD(command);
            case LDA -> CC.LDA(command);
            case STA -> CC.STA(command);
            case BUN -> CC.BUN(command);
            case BSA -> CC.BSA();
            case ISZ -> CC.ISZ(command);
            case CLA -> CC.CLA();
            case SZA -> CC.SZA();
            case SNA -> CC.SNA();
            case CMA -> CC.CMA();
            case INC -> CC.INC();
            case ASHR -> CC.ASHR();
            case ASHL -> CC.ASHL();
            case SZE -> CC.SZE();
            case HLT -> CC.HLT();
            case INP -> CC.INP();
            case PUSH -> CC.PUSH();
            case POP -> CC.POP();
            case SZEMPTY -> CC.SZEmpty();
            case SZFULL -> CC.SZFull();
            default -> {
                
            }
        }
    }
    public Log Step(){
        return this.LGC.Step();
    }
    public LinkedHashMap<String, Integer> getLabels(){
       return CoderController.BinaryToInt(memory.DS.getUsedPart());
    }
    public LinkedHashMap<RegisterController.RegisterType, String> getRegisters(){
        LinkedHashMap<RegisterController.RegisterType, String> registersInfo = new LinkedHashMap();
        registersInfo.put(registers.ac.type,registers.ac.toString());
        registersInfo.put(registers.ar.type,registers.ar.toString());
        registersInfo.put(registers.dr.type,registers.dr.toString());
        registersInfo.put(registers.ir.type,registers.ir.toString());
        registersInfo.put(registers.pc.type,registers.pc.toString());
        registersInfo.put(registers.sp.type,registers.sp.toString());
        return registersInfo;
    }
    public LinkedHashMap<Integer,String> getCodeSegment(){
        return MemoryController.CS.toHashMap();
    }
    public LinkedHashMap<Integer,String> getDataSegment(){
        return MemoryController.DS.toHashMap();
    }
    public LinkedHashMap<Integer,String> getStackSegment(){
        return MemoryController.SS.toHashMap();
    }
    public String getBus(){
        return Arrays.toString(BusController.getBus());
    }
    public boolean getE(){
        return registers.ac.E;
    }
    public int getClock(){
        return ClockController.getClock();
    }
    public void resetVirtualMachine(){
        this.memory.reset();
        this.registers.reset();
        this.bus.clear();
        if(this.commands != null)
            this.commands.clear();
        ClockController.reset();
        this.LGC.reset();
    }
}
    