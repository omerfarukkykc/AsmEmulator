package VirtualMachine.Registers;

import VirtualMachine.Root.Register;
import VirtualMachine.RegisterController;
import VirtualMachine.RegisterController.RegisterType;

public class AC extends Register{

    
    public Logic logic;    
    public Arithmetic arithmetic;
    public RegisterType type;
    public boolean E;

    public AC(int bit){
        super(bit);
        logic = new Logic(this);
        arithmetic = new Arithmetic(this);
        type = RegisterController.RegisterType.AC;
        E = false;
    }
    public class Arithmetic{
        AC AC;
        public Arithmetic(AC AC){
            this.AC = AC;
        }
        public void ADD(int[] B){
            boolean flag = false;
            int[] sum = new int[AC.memory.length];
            for(int i = AC.memory.length-1;i>0;i--){
                if(flag){
                    if(B[i]==0 && AC.memory[i]==0){
                        sum[i] = 1;
                        flag = false;
                    }else if(B[i]==1 && AC.memory[i]==1){
                        sum[i] = 1;
                        flag = true;
                    }else{
                        sum[i] = 0;
                        flag= true;
                    }
                }else{
                    if(B[i]==0 && AC.memory[i]==0){
                        sum[i] = 0;
                        flag = false;
                    }else if(B[i]==1 && AC.memory[i]==1){
                        sum[i] = 0;
                        flag = true;
                    }else{
                        sum[i] = 1;
                        flag= false;
                    }
                }
            }
            AC.E = flag;
            AC.memory = sum.clone();
        }
        public void INC(){
            boolean flag = true;
            int [] sum = new int[AC.memory.length];
            for (int i = AC.memory.length-1; i >= 0; i--) {
                if(flag){
                    if(AC.memory[i]==1){
                        sum[i] = 0;
                    }else{
                        sum[i] = 1;
                        flag = false;
                    }
                }else{
                    sum[i] = AC.memory[i];
                }
            }
            AC.E = flag;
            AC.memory = sum.clone();
        }
        public void ASHL(){
            for (int i = 1; i< AC.memory.length; i++ ) {
                AC.memory[i-1]=AC.memory[i];
            }
            AC.memory[AC.memory.length-1]=0;
        }
        public void ASHR(){
            for(int i = AC.memory.length-2; i>0; i-- ) {
                AC.memory[i+1]=AC.memory[i];
            }
             AC.memory[0]=0;
        }
    }
    public class Logic{
        AC AC;
        private Logic(AC AC){
            this.AC = AC;
        }
        public void AND(int[] B){
            if(B.length != AC.memory.length){
                return;
            }
            int [] sonuc = new int[AC.memory.length]; 
            for(int i=AC.memory.length-1;i>=0;i--){
                if(AC.memory[i]==1&&B[i]==1){
                    sonuc[i]=1;
                } else{
                    sonuc[i]=0;
                }
            }
            AC.memory = sonuc.clone();
        }
        public void OR(int[] B){
            if(B.length != AC.memory.length){
                return;
            }
            int [] sonuc = new int[AC.memory.length]; 
            for(int i=AC.memory.length-1;i>=0;i--){
                if(AC.memory[i]==0&&B[i]==0){
                    sonuc[i]=0;
                } else{
                    sonuc[i]=1;
                }
            }
            AC.memory = sonuc.clone();
        }
        public void XOR(int[] B){
            if(B.length != AC.memory.length){
                return;
            }
            int [] sonuc = new int[AC.memory.length]; 
            for(int i=AC.memory.length-1;i>=0;i--){
                if(AC.memory[i]==0&&B[i]==0){
                    sonuc[i]=0;
                }else if(AC.memory[i]==1&&B[i]==1){
                    sonuc[i]=0;
                }else{
                    sonuc[i]=1;
                }

            }
            AC.memory = sonuc.clone();
        }
        public void COMPLEMENT(){
            int [] sonuc = new int[AC.memory.length]; 
            for(int i=AC.memory.length-1;i>=0;i--){
                if(AC.memory[i] == 0){
                    sonuc[i] = 1;
                }else{
                     sonuc[i] = 0;
                }
            }
             AC.memory = sonuc.clone();
        }
    }
    @Override
    public void Clear(){
        memory = new int[this.memory.length];
        this.E = false;
    }
    

    
}