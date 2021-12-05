package VirtualMachine.Root;

import VirtualMachine.BusController;
import VirtualMachine.CoderController;
import VirtualMachine.Memory.CodeSegment;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class Memory {
   
    protected int[][] ram;
    protected int lastAdress = -1;
    protected final LinkedHashMap<Integer, Integer>  adressList = new LinkedHashMap();
    public Memory(int size,int bitSize){
        ram = new int[size][bitSize];
    }
    public void Set(){
        lastAdress++;
        ram[lastAdress] = BusController.getBus().clone();
        adressList.put(lastAdress, lastAdress);
    }
    

    public void Update(String Tag, int[] Binary){
        ram[adressList.get(Tag)] = Binary;
    }
    public int[] toBinary(String Tag){
        return ram[adressList.get(Tag)];
    }
    public int[] toBinary(int adress){
        return ram[adress];
    }
    public int toInt(String Tag){
        return CoderController.BinaryToInt(ram[adressList.get(Tag)]);
    }
    public int getLastAdress(){
        return lastAdress;
    }
    public int getValue(int adress){
        return CoderController.BinaryToInt(ram[adress]);
    }
    public LinkedHashMap<Integer, String> toHashMap(){
         LinkedHashMap<Integer, String> ram = new LinkedHashMap();
         for (int i = 0; i < this.ram.length; i++) {
            ram.put(i, Arrays.toString(this.ram[i]));
         }
         return ram;
    }
    public int getAdress(int Tag){
        return this.adressList.get(Tag);
    }
    public void setStartAdress(int adress){
        this.lastAdress = adress-1;
    }
    public void Update(int adress){
        ram[adressList.get(adress)] = BusController.getBus();
    }
    
    
    public void reset(){
        this.ram = new int[this.ram.length][this.ram[0].length];
        this.lastAdress = -1;
        this.adressList.clear();
    }
    public String toStringAll(){
        String satır = "";
        String all = "";
        for(int[]arr :this.ram ){
            for(int a : arr){
                satır += String.valueOf(a);
            }
            satır += "\n";
        }
        return all;
    }
}
