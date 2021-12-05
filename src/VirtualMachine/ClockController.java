package VirtualMachine;
public class ClockController {
    private static int i = 0;
    private static boolean hlt =  false;
    public static void increament(){
        if(ClockController.i < 16){
            ClockController.i++;
        }else{
            ClockController.i = 0;
        }
    }
    public static void clear(){
        ClockController.i = 0;
    }
    public static int getClock(){
        if(!hlt){
           return ClockController.i; 
        }else{
           return 15;
        }
    }
    public static void HLT(){
        hlt = true;
    }
    public static void reset(){
        hlt = false;
        ClockController.i = 0;
    }
    
    
}
