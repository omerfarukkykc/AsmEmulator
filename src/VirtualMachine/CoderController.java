package VirtualMachine;
import VirtualMachine.Memory.CodeSegment;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class CoderController {
    
    public CoderController(CodeSegment memory){
        
    }
    public static int [] toBinaryArray(int n){
        String s = "";
        if(n>=0){
            while (n > 0)
            {
                s =  ( (n % 2 ) == 0 ? "0" : "1") +s;
                n = n / 2;
            }
        }else{
            n=n+1;
            while (n < 0)
            {
                s =  ( (n % 2 ) == 0 ? "1" : "0") +s;
                n = n / 2;
                
            }
            while (s.length()!=9) {
               s = "1"+s;
            }
        }
        
        int[] arr = BusController.getNewBus();
        int j = arr.length-1;
        for (int i = s.length()-1; i >= 0; i--) {
           arr[j] = Character.getNumericValue(s.charAt(i));
           j--;
        }
        return arr;
    }
    public static int[] toBinaryArray(String val){
       return toBinaryArray(Integer.parseInt(val));
    }
    public static LinkedHashMap<String, Integer> BinaryToInt(LinkedHashMap<String, int[]> memory){
        LinkedHashMap<String, Integer>  mem = new LinkedHashMap();
        memory.entrySet().forEach( entry -> {
            mem.put(entry.getKey(), CoderController.BinaryToInt(entry.getValue()));
        });
        return mem;
    }
    public static int BinaryToInt(int arr[]){
        String binaryString = "";
        for(int i : arr){
            binaryString += i;
        }
        if(binaryString.charAt(0) == '1'){
            while (binaryString.length()!=32) {
                
               binaryString = "1"+binaryString;
                
            }
        }
        long longValue = Long.parseLong(binaryString, 2);
        int value = (int)longValue;
        return value;
    }
    public static int[] BinaryStringToIntArr(String value){
        char[] arr = value.toCharArray();
        int[] result = new int[arr.length];
        for(int i = 0;i<arr.length;i++)
            result[i] = Character.getNumericValue(arr[i]);
        return result;
    }
    public static String hexToBinary(String hex){
        String binary = "";
        HashMap<Character, String> hexMap = new HashMap();
        hexMap.put('0', "0000");
        hexMap.put('1', "0001");
        hexMap.put('2', "0010");
        hexMap.put('3', "0011");
        hexMap.put('4', "0100");
        hexMap.put('5', "0101");
        hexMap.put('6', "0110");
        hexMap.put('7', "0111");
        hexMap.put('8', "1000");
        hexMap.put('9', "1001");
        hexMap.put('A', "1010");
        hexMap.put('B', "1011");
        hexMap.put('C', "1100");
        hexMap.put('D', "1101");
        hexMap.put('E', "1110");
        hexMap.put('F', "1111");
        int i;
        char ch;
        for (i = 0; i < hex.length(); i++) {
            ch = hex.charAt(i);
            if (hexMap.containsKey(ch))
                binary += hexMap.get(ch);
        }
        return binary;
    }
    public static boolean [] decoder4x16(int[] arr){
        boolean D[] = new boolean[16];
        String binaryString = "";
        for(int i : arr){
            binaryString += i;
        }
        long longValue = Long.parseLong(binaryString, 2);
        int value = (int)longValue;
        D[value] = true;
        return D;
    }
}
