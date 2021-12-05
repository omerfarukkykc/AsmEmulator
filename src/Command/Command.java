package Command;

import Command.CommandType.CommandCategory;
import VirtualMachine.CoderController;
import VirtualMachine.MemoryController;
import java.util.ArrayList;

public class Command {
    
    public final CommandType commandType;
    public final CommandCategory commandCategory; 
    public final String PesudoCode;
    public final String tag;    
    public final String adress;
    public final boolean Indirect; 
    public static int length = 0;

    public final int id;
    public Command(CommandType type,boolean Indirect, String adress, String tag,String AsemblyCode) {
            this.commandType = type;
            this.adress = adress;
            this.tag = tag;
            this.PesudoCode = AsemblyCode;
            this.Indirect = Indirect;
            length++;
            this.id=length;
            this.commandCategory = commandType.getCategory();
    }
    public Command(CommandType type,boolean Indirect,String adress, String AsemblyCode) {
            this.commandType = type;
            this.adress = adress;
            this.tag = null;
            this.PesudoCode = AsemblyCode;
            this.Indirect = Indirect;
            length++;
            this.id=length;
            this.commandCategory = commandType.getCategory();
    }
    public String toHex(){
         return this.commandType.getHex(Indirect);
    }
    public int [] toBinary(){
        int[] buyruk = new int[9];
        switch(this.commandCategory){
                case STACK ->{
                    buyruk[0] = 1;
                    String binaryValue = CoderController.hexToBinary(this.toHex());
                    int arr[] =CoderController.BinaryStringToIntArr(binaryValue);
                    for (int i = 5; i < buyruk.length; i++) {
                        buyruk[i] = arr[i-5];
                    }
                    return buyruk;
                }
                case MEMORY ->{
                    buyruk[0] = (Indirect)?1:0;
                    String binaryValue = CoderController.hexToBinary(this.toHex());
                    int arr[] =CoderController.BinaryStringToIntArr(binaryValue);
                    for (int i = 0; i < arr.length; i++) {
                        buyruk[i+1] = arr[i];
                    }
                    int adres = MemoryController.DS.getAdress(adress);
                    int[] adresValue = CoderController.toBinaryArray(adres);
                    
                    for (int i = 0; i < adresValue.length-5; i++) {
                        buyruk[i+5] = adresValue[i+5];
                    }
                    return buyruk;                 
                }
                case REGISTER ->{
                    buyruk[0] = 0;
                    String binaryValue = CoderController.hexToBinary(this.toHex());
                    int arr[] =CoderController.BinaryStringToIntArr(binaryValue);
                    for (int i = 0; i < arr.length; i++) {
                        buyruk[i+5] = arr[i];
                    }
                    return buyruk;
                }
                case IO ->{
                    buyruk[0] = 1;
                    String binaryValue = CoderController.hexToBinary(this.toHex());
                    int arr[] =CoderController.BinaryStringToIntArr(binaryValue);
                    for (int i = 5; i < buyruk.length; i++) {
                        buyruk[i] = arr[i-5];
                    }
                    return buyruk;

                }
                default -> {
                    return null;
                }
            }
        
        
    }
    public static ArrayList<Command> commandParser(ArrayList<String> lines){
        ArrayList<Command> commands = new ArrayList<>();
        lines = clearInfo(lines);
        for(String line : lines) {
            Command command;
            if(!line.matches("(.*),(.*)")){
                commands.add(noTag(line));
               
            }else{
                commands.add(withTag(line));
            }
        }
        return commands;
    }
    private static Command noTag(String line){
        String[] explodedLine = line.split(" ");
        String buyruk = explodedLine[0];        
        if (CommandType.isExsist(buyruk)) {
            CommandType type = CommandType.valueOf(buyruk);
            if(explodedLine.length!=1){
                String adress = explodedLine[1];
                if (explodedLine.length ==3) {
                    if(explodedLine[2].equals("I")){
                         return new Command(type,true,adress,line);
                    }else{
                        if (type == CommandType.ORG) {
                            return new Command(type,false,explodedLine[2],explodedLine[1],line);
                        }else{
                            return new Command(type,false,null,line);
                        }
                    }
                }else{
                    return new Command(type,false,adress,line);
                }
            }else{
                return new Command(type,false,null,line);
            }
        }
        return null;
    }
    private static Command withTag(String line){
        String[] parsedLineTag = line.split(",");
        String[] parsedLine = parsedLineTag[1].split(" ");
        String tag = parsedLineTag[0];
        String buyruk = parsedLine[1];
        if(parsedLine.length == 3){
             String adress = parsedLine[2];
             if(CommandType.isExsist(buyruk)){
                CommandType type = CommandType.valueOf(buyruk);
                if (parsedLine.length == 4) {
                    if(parsedLine[3].equals("I")){
                        return new Command(type,true,adress,tag,line);
                    }
                }else{
                    return new Command(type,false,adress,tag,line);
                }
            }
        }else{
            if(CommandType.isExsist(buyruk)){
                CommandType type = CommandType.valueOf(buyruk);
                return new Command(type,false,null,tag,line);
            }
        }
       
        
        return null;
    }
    private static ArrayList<String> clearInfo(ArrayList<String> lines){
        ArrayList<String> filtered = new ArrayList<>();
        for(String line : lines) {
            if (line == null)continue;
            if(!line.startsWith("/")) {
                if(!line.matches("(.*)/(.*)")){
                    filtered.add(line.toUpperCase());
                }else{
                    filtered.add(line.split("/")[0].toUpperCase());	
                }	
            }
        }
        return filtered;
    }
    public static ArrayList<Command> decompositionCommands(ArrayList<Command> commands){
         ArrayList<Command> cm = new  ArrayList();
         commands.forEach(command->{
             if(command.commandType.getCategory() != CommandCategory.PESUDO ){
                 cm.add(command);
             }
         });
        return cm;
    }
    
}
