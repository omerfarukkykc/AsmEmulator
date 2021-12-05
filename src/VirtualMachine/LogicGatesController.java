package VirtualMachine;

import AsmMain.Log;
import java.util.Arrays;

public class LogicGatesController {
    RegisterController RC;
    boolean D[] = new boolean[16];
    boolean R[] = new boolean[16];
    boolean I = false;
    static int startAdress;

    public LogicGatesController(RegisterController RC){
        this.RC = RC;
    }
    public Log Step(){
        switch(ClockController.getClock()){
            case 0->{
                return this.zero();
            }
            case 1->{
                return this.one();
            }
            case 2->{
                return this.two();
            }
            case 3->{
                return this.three();
            }
            case 4->{
                return this.four();
            }
            case 5->{
                return this.five();
            }
            case 6->{
                return this.six();
            }
            case 7->{
                return null;
            }
            case 8->{
                return null;
            }
            case 9->{
                return null;
            }
            case 10->{
                return null;
            }
            case 11->{
                return null;
            }
            case 12->{
                return null;
            }
            case 13->{
                return null;
            }
            case 14->{
                return null;
            }
            case 15->{
                return null;

            }
            default->{
                return null;
            }
        }
    }
    public Log zero(){
        BusController.setBus(this.RC.pc.toArray());
        this.RC.ar.Load();
        ClockController.increament();
        return new Log(this.RC.pc.getValue()-startAdress,"Fetch AR <- PC");
    }
    public Log one(){
        BusController.setBus(MemoryController.CS.toBinary(this.RC.ar.getValue()));
        this.RC.ir.Load();
        this.RC.pc.Increament();
        ClockController.increament();
        return new Log("Fetch IR <- M[AR], PC <- PC + 1");
    }
    public Log two(){
        int arr[] = this.RC.ir.toArray();
        I = (arr[0] == 1);  
        this.D = CoderController.decoder4x16(Arrays.copyOfRange(arr,  1,5));
        this.R = CoderController.decoder4x16(Arrays.copyOfRange(arr, 5, 9));
        BusController.setBus(Arrays.copyOfRange(arr, 5, 9));
        this.RC.ar.Load();
        ClockController.increament();
        
        return new Log("D0, ..., D15 <- Decode IR(4 ~ 8),\n AR <- IR(0 ~ 4), I <- IR(9)");
    }
    public Log three(){
        if(I&&!D[0]){
            BusController.setBus(MemoryController.DS.toBinary(this.RC.ar.getValue()));
            this.RC.ar.Load();
            ClockController.increament();
            return new Log("Indirect AR <- M[AR]");
        }else if(!I&&!D[0]){
            ClockController.increament();
            return this.Step();
        }
        if(!I&&D[0]){
            if(R[1]){
                this.RC.ac.Clear();
                ClockController.clear();
                return new Log("AC <- 0");
            }
            if(R[2]){
                if(this.RC.ac.getValue() == 0){
                    this.RC.pc.Increament();
                }
                ClockController.clear();
                return new Log("If(AC = 0) then (PC <- PC + 1)");
            }
            if(R[3]){
                if(this.RC.ac.getValue() < 0){
                    this.RC.pc.Increament();
                }
                ClockController.clear();
                return new Log("Indirect AR <- M[AR]");
            }
            if(R[4]){
                this.RC.ac.logic.COMPLEMENT();
                ClockController.clear();
                return new Log("AC <- AC");
            }
            if(R[5]){
                this.RC.ac.Increament();
                ClockController.clear();
                return new Log("AC <- AC + 1");
            }
            if(R[7]){
                this.RC.ac.arithmetic.ASHR();
                ClockController.clear();
                return new Log("Aritmetik Sağa kaydırma");
            }
            if(R[8]){
                this.RC.ac.arithmetic.ASHL();
                ClockController.clear();
                return new Log("Aritmetik Sola kaydırma");
            }
            if(R[0]){
                if(!this.RC.ac.E){
                    this.RC.pc.Increament();
                }
                ClockController.clear();
                return new Log("If(E=0) then (PC <- PC + 1)");
            }
            
            if (R[9]) {
                
                ClockController.HLT();
                return new Log("Bilgisayar durduruldu").stop();
            }
        }
        if(I&&D[0]){
            if(R[1]||R[9]){
               
                BusController.setBus(this.RC.dr.toArray());
                MemoryController.SS.Set(this.RC.sp.getValue());
                this.RC.sp.Increament();
                ClockController.clear();
                return new Log("SP<- SP + 1 ,SS[SP]<- DR ");
            }
            if(R[2]||R[10]){
                this.RC.sp.Decrease();
                BusController.setBus(MemoryController.SS.toBinary(this.RC.sp.getValue()));
                this.RC.dr.Load();
                MemoryController.SS.clear(this.RC.sp.getValue());
                ClockController.clear();
                return new Log("DR <- SS[SP] ,SP <- SP + 1 ");
            }
            if(R[3]||R[11]){
                if(this.RC.sp.getValue() == 0){
                    this.RC.pc.Increament();
                }
                ClockController.clear();
                return new Log("If(SP=0) then (PC <- PC + 1)");
            }
            if(R[4]||R[12]){
                if(this.RC.sp.getValue() == 7){
                    this.RC.pc.Increament();
                }
                ClockController.clear();
                return new Log("If(E=7) then (PC <- PC + 1)");
            }
            
        }
        return null;
        
    }
    public Log four(){
        if(D[1]){
            BusController.setBus(MemoryController.DS.toBinary(this.RC.ar.getValue()));
            this.RC.dr.Load();
            ClockController.increament();
            return new Log("DR <- M[AR]");
        }
        if(D[2]){
            BusController.setBus(MemoryController.DS.toBinary(this.RC.ar.getValue()));
            this.RC.dr.Load();
            ClockController.increament();
            return new Log("DR <- M[AR]");
        }
        if(D[3]){
            BusController.setBus(MemoryController.DS.toBinary(this.RC.ar.getValue()));
            this.RC.dr.Load();
            ClockController.increament();
            return new Log("DR <- M[AR]");
        }
        if(D[4]){
            BusController.setBus(MemoryController.DS.toBinary(this.RC.ar.getValue()));
            this.RC.dr.Load();
            ClockController.increament();
            return new Log("DR <- M[AR]");
        }
        if(D[5]){
            BusController.setBus(MemoryController.DS.toBinary(this.RC.ar.getValue()));
            this.RC.dr.Load();
            ClockController.increament();
            return new Log("DR <- M[AR]");
        }
        if(D[6]){
            BusController.setBus(this.RC.ac.toArray());
            MemoryController.DS.Update(this.RC.ar.getValue());
            ClockController.clear();
            return new Log("M[AR] <- AC, SC <- 0");
        }
        if(D[8]){
            BusController.setBus(MemoryController.DS.toBinary(this.RC.ar.getValue()));
            this.RC.pc.Load();
            ClockController.clear();
            return new Log("PC <- AR, SC <- 0");
        }
        if(D[9]){
            BusController.setBus(this.RC.pc.toArray());
            MemoryController.DS.Update(this.RC.ar.getValue());
            this.RC.ar.Increament();
            ClockController.increament();
            return new Log("M[AR] <- PC, AR <- AR + 1");
        }
        if(D[15]){
            BusController.setBus(MemoryController.DS.toBinary(this.RC.ar.getValue()));
            this.RC.dr.Load();
            ClockController.increament();
            return new Log("DR <- M[AR]");
        }
        
        return null;
        
    }
    public Log five(){
        if(D[1]){
            this.RC.ac.logic.OR(this.RC.dr.toArray());
            ClockController.clear();
            return new Log("AC <- AC or DR, SC <- 0");
        }
        if(D[2]){
            this.RC.ac.logic.AND(this.RC.dr.toArray());
            ClockController.clear();
            return new Log("AC <- AC and DR, SC <- 0");
        }
        if(D[3]){
            this.RC.ac.logic.XOR(this.RC.dr.toArray());
            ClockController.clear();
            return new Log("AC <- AC xor DR, SC <- 0");
        }
        if(D[4]){
            this.RC.ac.arithmetic.ADD(this.RC.dr.toArray());
            ClockController.clear();
            return new Log("AC <- AC add DR, SC <- 0");
        }
        if(D[5]){
            BusController.setBus(this.RC.dr.toArray());
            this.RC.ac.Load();
            ClockController.clear();
            return new Log("AC <- DR, SC <- 0");
        }
        if(D[9]){
            BusController.setBus(this.RC.ar.toArray());
            this.RC.pc.Load();
            ClockController.clear();
            return new Log("PC <- AR, SC <- 0");
        }
        if(D[15]){
            this.RC.dr.Increament();
            ClockController.increament();
            return new Log("DR <- DR + 1");
        }
        return null;
    }
    public Log six(){
        if(D[15]){
            BusController.setBus(this.RC.dr.toArray());
            MemoryController.DS.Update(this.RC.ar.getValue());
            if(this.RC.dr.getValue() == 0){
                this.RC.pc.Increament();
            }
            ClockController.clear();
            return new Log("M[AR] <- DR, if(DR=0) then (PC <- PC + 1),SC <- 0");
        }
        return null;
    }
    public void reset(){
        D = new boolean[D.length];
        R = new boolean[R.length];
        I = false;
    }
}
