package VirtualMachine.Root;

import VirtualMachine.BusController;
import java.util.Arrays;

public class Register {
    public int[] memory;
    protected Register(int bit){
        memory = new int[bit];
    }
    public void Load() {
        this.Clear();
        int arr[] = BusController.getBus();
        int j = arr.length-1;
        for(int i =this.memory.length-1;i>=0;i-- ){
            this.memory[i] = arr[j];
            j--;
        }
        
    }
    public void Increament(){
        boolean flag = true;
            int [] sum = new int[this.memory.length];
            for (int i = this.memory.length-1; i >= 0; i--) {
                if(flag){
                    if(this.memory[i]==1){
                        sum[i] = 0;
                    }else{
                        sum[i] = 1;
                        flag = false;
                    }
                }else{
                    sum[i] = this.memory[i];
                }
            }
            this.memory = sum.clone();
    }
    public void Increament(int d){
        for (int j = 0; j < d; j++) {
             boolean flag = true;
            int [] sum = new int[this.memory.length];
            for (int i = this.memory.length-1; i >= 0; i--) {
                if(flag){
                    if(this.memory[i]==1){
                        sum[i] = 0;
                    }else{
                        sum[i] = 1;
                        flag = false;
                    }
                }else{
                    sum[i] = this.memory[i];
                }
            }
            this.memory = sum.clone();
        }
    }
    public void Decrease() {
        this.COMPLEMENT();
        this.Increament();
        this.COMPLEMENT();
    }
    private void COMPLEMENT(){
        int [] sonuc = new int[this.memory.length]; 
        for(int i=this.memory.length-1;i>=0;i--){
            if(this.memory[i] == 0){
                sonuc[i] = 1;
            }else{
                 sonuc[i] = 0;
            }
        }
         this.memory = sonuc.clone();
    }
    public void Clear(){
        memory = new int[this.memory.length];
    }
    @Override
    public String toString() {
        return Arrays.toString(memory);
    }
    public int[] toArray(){
        return this.memory;
    }
    public int getValue(){
        String binaryString = "";
        for(int i : this.toArray()){
            binaryString += i;
        }
        long longValue = Long.parseLong(binaryString, 2);
        int value = (int)longValue;
        return value;
        
    }

    
}
