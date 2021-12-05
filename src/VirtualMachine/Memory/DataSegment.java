package VirtualMachine.Memory;

import VirtualMachine.CoderController;
import VirtualMachine.Root.Memory;
import java.util.LinkedHashMap;

public class DataSegment extends Memory{
    protected final LinkedHashMap<String, Integer>  TagList = new LinkedHashMap();
    
    public DataSegment(int size,int bitSize){
       super(size, bitSize);
    }
    public LinkedHashMap<String, int[]> getUsedPart(){
         LinkedHashMap<String, int[]> mem = new LinkedHashMap();
         this.TagList.entrySet().forEach( entry -> {
             mem.put(entry.getKey(), this.ram[this.adressList.get(entry.getValue())]);
         });
        return mem;
    }
    public void Set(String Tag){
        this.Set();
        this.TagList.put(Tag,lastAdress);
    }
    public int getAdress(String Tag){
        return this.adressList.get(this.TagList.get(Tag));
    }
    @Override
    public void reset(){
        this.TagList.clear();
        this.ram = new int[this.ram.length][this.ram[0].length];
        this.lastAdress = -1;
        this.adressList.clear();
    }
    @Override
    public int[] toBinary(String Tag){
        return ram[adressList.get(TagList.get(Tag))];
    }
    @Override
    public void Update(String Tag, int[] Binary){
        ram[adressList.get(TagList.get(Tag))] = Binary;
    }
    
    @Override
    public int toInt(String Tag){
        return CoderController.BinaryToInt(ram[adressList.get(TagList.get(Tag))]);
    }
   
    
   
}

