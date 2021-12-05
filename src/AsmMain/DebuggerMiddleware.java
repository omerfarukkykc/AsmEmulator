package AsmMain;

import Command.Command;
import Command.CommandType;
import java.util.ArrayList;

public class DebuggerMiddleware {
    
    public static boolean codeControl(ArrayList<Command> commands){
        String erString="";
        boolean error =false;
        if(DebuggerMiddleware.orgControl(commands.get(0))!=true){
            erString = "Girilen ilk komut ORG komutu olmalıdır";
            error = true;
        }
        
        
        if(error)
        DebuggerController.infoBoxView( erString,"Error");
        return !error;
    }
    private static boolean orgControl(Command command){
        return command.commandType == CommandType.ORG;
    }
}
