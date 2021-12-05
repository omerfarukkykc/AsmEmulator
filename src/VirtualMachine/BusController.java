
package VirtualMachine;
public class BusController {
    private static int[] bus;
    public BusController(){
       bus = new int[9];       
    }
    public static int[] getBus(){
        int[] busCopy = bus.clone(); 
        for(int i = 0; i<BusController.bus.length;i++){
            BusController.bus[i] = 0;
        }
        return busCopy;
    }
    public static int[] getNewBus(){
        return new int[bus.length];
    }
    
    public static void setBus(int[] bus){
        int[] arr = getNewBus();
        int j = arr.length-1;

        for(int i = bus.length-1; i>=0;i--){
            arr[j] = bus[i];
            j--;
        }
        VirtualMachine.BusController.bus = arr.clone();
    }
    public void clear(){
        BusController.bus = new int[BusController.bus.length];
    }
    
}