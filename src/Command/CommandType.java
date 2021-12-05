package Command;

import java.math.BigInteger;

public enum CommandType {
        // Memory Refrence
        OR , AND, XOR, ADD, LDA, STA, BUN, BSA, ISZ,
        // Register Refrence
        CLA, SZA, SNA, CMA, INC, ASHR, ASHL, SZE, HLT,
        // I/O Refrence
        INP,
        // Stack Refrence
        PUSH, POP, SZEMPTY, SZFULL, 
        // Pesudo Code
        HEX, DEC,ORG, END,
	;
        public enum CommandCategory{
            REGISTER,IO,MEMORY,PESUDO,STACK
        }
        public CommandCategory getCategory(){
            return switch(this){
                case OR -> CommandCategory.MEMORY;
                case AND -> CommandCategory.MEMORY;
                case XOR -> CommandCategory.MEMORY;
                case ADD -> CommandCategory.MEMORY;
                case LDA -> CommandCategory.MEMORY;
                case STA -> CommandCategory.MEMORY;
                case BUN -> CommandCategory.MEMORY;
                case BSA -> CommandCategory.MEMORY;
                case ISZ -> CommandCategory.MEMORY;
                    
                case CLA -> CommandCategory.REGISTER;
                case SZA -> CommandCategory.REGISTER;
                case SNA -> CommandCategory.REGISTER;
                case CMA -> CommandCategory.REGISTER;
                case INC -> CommandCategory.REGISTER;
                case ASHR -> CommandCategory.REGISTER;
                case ASHL -> CommandCategory.REGISTER;
                case SZE -> CommandCategory.REGISTER;
                case HLT -> CommandCategory.REGISTER;
                    
                case INP ->CommandCategory.IO;
                    
                case PUSH ->CommandCategory.STACK;
                case POP -> CommandCategory.STACK;
                case SZEMPTY -> CommandCategory.STACK;
                case SZFULL -> CommandCategory.STACK;
                    
                case HEX -> CommandCategory.PESUDO;
                case DEC -> CommandCategory.PESUDO;
                case ORG -> CommandCategory.PESUDO;
                case END -> CommandCategory.PESUDO;
                default -> CommandCategory.PESUDO;
                    
            };
        }
        public static boolean isExsist(String val){
            for(CommandType value :CommandType.values()){
                if (value.name().equals(val)) {
                    return true;
                }
                
            }
            return false;
        }
        
        public String getHex(boolean I){
            return switch (this) {
                case OR -> "1";
                case AND -> "2";
                case XOR -> "3";
                case ADD -> "4";
                case LDA -> "5";
                case STA -> "6";
                case BUN -> "8";
                case BSA -> "9";
                case ISZ -> "F";
                    
                case CLA -> "1";
                case SZA -> "2";
                case SNA -> "3";
                case CMA -> "4";
                case INC -> "5";
                case ASHR -> "7";
                case ASHL -> "8";
                case SZE -> "0";
                case HLT -> "9";
                    
                case INP ->(I)?"F":"7";
                    
                case PUSH ->(I)?"9":"1";
                case POP -> (I)?"A":"2";
                case SZEMPTY -> (I)?"B":"3";
                case SZFULL -> (I)?"C":"4";
                    
                case HEX -> null;
                case DEC -> null;
                case ORG -> null;
                case END -> null;
                default -> null;
            };
        }
        public int [] getBinary(boolean I){
            String BinS;
            int[] binary = new int[4];
            binary[0] = (I)?1:0;

            switch(getCategory()){
                
                case PESUDO ->{
                    return null;
                }
                case STACK ->{
                    String hexCode = this.getHex(I);
                    BinS = (hexCode != null ) ? new BigInteger(this.getHex(I), 16).toString(2): null;
                }
                case MEMORY ->{
                    String hexCode = this.getHex(I);
                    BinS = (hexCode != null ) ? new BigInteger(this.getHex(I), 16).toString(2): null;
                }
                case REGISTER ->{
                    BinS =  new BigInteger(this.getHex(I), 16).toString(2);
                }
                case IO ->{
                    String hexCode = this.getHex(I);
                    BinS = (hexCode != null ) ? new BigInteger(this.getHex(I), 16).toString(2): null;
                }
                default -> {
                    return null;
                }
            }
            char[] arr = BinS.toCharArray();
            int j= 3;
            for(int i = arr.length-1;i>0;i--){
                binary[j] = Character.getNumericValue(arr[i]);
                j--;
            }
               
            return binary;
        } 
}
