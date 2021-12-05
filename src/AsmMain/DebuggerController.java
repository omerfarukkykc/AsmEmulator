package AsmMain;
import Command.Command;
import static AsmMain.DebuggerWindow.*;
import VirtualMachine.*;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Element;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DebuggerController {
    
    private VirtualMachineController Vmc;
    private ArrayList<Command> commands;
    private ArrayList<Command> pesudoCommands;
    private String pesudoCommandsString = "";    
    private int nextCommand = 0;
    private File selectedFile;

    public DebuggerController(){
        Vmc = new VirtualMachineController();
        try {
            this.loadEditor();
            this.loadInfo();
            this.updateView();
        } catch (Exception ex) {
            Logger.getLogger(DebuggerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void loadEditor(){
        lines = new JTextArea("1");
        lines.setBackground(Color.LIGHT_GRAY);
        lines.setEditable(false);
        txtCode.getDocument().addDocumentListener(new DocumentListener() {
        public String getText() {
            int caretPosition = txtCode.getDocument().getLength();
            Element root = txtCode.getDocument().getDefaultRootElement();
            String text = "1" + System.getProperty("line.separator");
               for(int i = 2; i < root.getElementIndex(caretPosition) + 2; i++) {
                  text += i + System.getProperty("line.separator");
               }
            return text;
         }
         @Override
         public void changedUpdate(DocumentEvent de) {
            lines.setText(getText());
         }
         @Override
         public void insertUpdate(DocumentEvent de) {
            lines.setText(getText());
         }
         @Override
         public void removeUpdate(DocumentEvent de) {
            lines.setText(getText());
         }
      });
      scrlCodeArea.getViewport().add(txtCode);
      scrlCodeArea.setRowHeaderView(lines);
    } 
    private void loadInfo() throws Exception{
            Object obj = new JSONParser().parse(new FileReader("helper.json"));
            JSONObject jo = (JSONObject) obj;
            jo.keySet().forEach(Category ->
            {
                Object Commands = jo.get(Category);
                JSONArray array = (JSONArray) Commands;
                array.forEach(CodeObject -> {
                    JSONObject item = (JSONObject) CodeObject;
                    switch((String)Category){
                        case "Directives" -> {
                            directivesReferenceModel.addRow(new Object[]{item.get("Code"),item.get("Hex"),item.get("Descripton"),item.get("Notation")});
                        }
                        case "MemoryReference" -> {
                             memoryReferenceModel.addRow(new Object[]{item.get("Code"),item.get("Hex"),item.get("Descripton"),item.get("Notation")});
                        }
                        case "RegisterReference" -> {
                            registersReferenceModel.addRow(new Object[]{item.get("Code"),item.get("Hex"),item.get("Descripton"),item.get("Notation")});
                        }
                        case "InputOutputReference" -> {
                            inputAndOutputReferenceModel.addRow(new Object[]{item.get("Code"),item.get("Hex"),item.get("Descripton"),item.get("Notation")});
                        }
                        default -> {
                        }
                                    
                    }
                });
            });
    }
    public boolean openFile(){
        this.resetAll();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lütfen dosya seçiniz.");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.asm ve *.basm dosyalarını açabilirsiniz.", new String[]{"asm","basm"});
        fileChooser.setFileFilter(filter);
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
            selectedFile = fileChooser.getSelectedFile();
             try {
               
                ArrayList<String> lines;
                try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                    lines = new ArrayList<>();
                    while(reader.ready()) {
                        String line =reader.readLine();
                        lines.add(line);
                        pesudoCommandsString += line + "\n";
                    }
                }
                this.pesudoCommands = Command.commandParser(lines);
                this.commands = Command.decompositionCommands(pesudoCommands);
                if (DebuggerMiddleware.codeControl(pesudoCommands)) {
                    Vmc.setCommands(pesudoCommands);
                    Vmc.Step(pesudoCommands.get(0));
                }
                commands.forEach(item -> {
                    runtimeCodeListModel.addElement(item.PesudoCode);
                });
                txtCode.setText(pesudoCommandsString);
                asmblyList.setSelectedIndex(0);
                btnStep.setEnabled(true);
                btnRun.setEnabled(true);
                btnReset.setEnabled(true);
                btnAssemble.setEnabled(true);
                this.updateView();
                return true;

            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            }    
        }
        return false;
    }
    public boolean openFile(String codeString){
        this.resetAll();
        try {
            Reader inputString = new StringReader(codeString);
            ArrayList<String> lines;
            try (BufferedReader reader = new BufferedReader(inputString)) {
                lines = new ArrayList<>();
                String line = "";
                while((null!=line)) {
                    line =reader.readLine();
                    if(line == null)continue;
                    lines.add(line);
                    pesudoCommandsString += line + "\n";
                }
            }
            this.pesudoCommands = Command.commandParser(lines);
            this.commands = Command.decompositionCommands(pesudoCommands);
            if (DebuggerMiddleware.codeControl(pesudoCommands)) {
                Vmc.setCommands(pesudoCommands);
                Vmc.Step(pesudoCommands.get(0));
            }
            for(int i = 0; i<labelListModel.getRowCount()-1;i++){
                labelListModel.removeRow(i);
            }
            commands.forEach(item -> {
                runtimeCodeListModel.addElement(item.PesudoCode);
            });
            txtCode.setText(pesudoCommandsString);
            asmblyList.setSelectedIndex(0);
            btnStep.setEnabled(true);
            btnRun.setEnabled(true);
            btnReset.setEnabled(true);
            
            this.updateView();
            return true;
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        }    
        return false;
    }
    
    public boolean nextStep(){
        /*
        if(nextCommand !=commands.size()){
            Vmc.Step(commands.get(nextCommand));
            nextCommand++;
            this.updateView();
            return true;
        }else{
            DebuggerController.infoBoxView("Assembly kodunun sonuna geldiniz.", " Program Sonu");
            return false;
        }
        */
        Log log =Vmc.Step();
        if (log!=null) {
            txtMicrooperation1.setText(log.description);
            this.updateView(log);
            return true;
        }
        return false;
        
    }
    
    public void run() {
        /*
        Vmc.Execute(commands);
        this.updateView();
        */
        
        Log log;
        log =Vmc.Step();
        while(log.stop){
            log =Vmc.Step();
            if (log!=null) {
                txtMicrooperation1.setText(log.description);
                this.updateView(log);
            }
        }
        
    }
    
    
    
    public void updateView(){
        this.clearFormView(true);
        updateLabelsView();
        updateRegistersView();
        updateDataSegmentView();
        updateCodeSegmentView();
        updateStackSegmentView();
        
    }
    public void updateView(Log log){
        if(log.index!=5555)
        asmblyList.setSelectedIndex(log.index);
        this.clearFormView(true);
        updateLabelsView();
        updateRegistersView();
        updateDataSegmentView();
        updateCodeSegmentView();
        updateStackSegmentView();
        updateClockView();
        
        
    }
    public void updateClockView(){
        txtClock.setText(String.valueOf(Vmc.getClock()));
        txtElde.setText(String.valueOf(Vmc.getE()));
        txtBus.setText(Vmc.getBus());
    }
    public void updateLabelsView(){
        Vmc.getLabels().entrySet().forEach( entry -> {
            labelListModel.addRow(new Object[]{entry.getKey(), entry.getValue()});
        });
    }
    public void updateRegistersView(){
        Vmc.getRegisters().entrySet().forEach( entry -> {
            switch(entry.getKey()){
                case AC -> {
                    txtAC.setText(entry.getValue());
                    
                }
                case AR -> {
                    txtAR.setText(entry.getValue());
                }
                case DR -> {
                    txtDR.setText(entry.getValue());

                }
                case PC -> {
                    txtPC.setText(entry.getValue());
                }
                case SP -> {
                    txtSP.setText(entry.getValue());
                }
                case IR -> {
                    txtIR.setText(entry.getValue());
                }
                default -> {
                }

            }
        });
    }
    public void updateDataSegmentView(){
        Vmc.getDataSegment().entrySet().forEach(entry->{
             dataTableModel.addRow(new Object[]{entry.getKey(),entry.getValue()});
        });
    }
     public void updateStackSegmentView(){
        Vmc.getStackSegment().entrySet().forEach(entry->{
             stackTableModel.addRow(new Object[]{entry.getKey(),entry.getValue()});
        });
    }
    public void updateCodeSegmentView(){
        Vmc.getCodeSegment().entrySet().forEach(entry->{
            codeTableModel.addRow(new Object[]{entry.getKey(),entry.getValue()});
           
        });
    }
    public static void infoBoxView(String infoMessage, String titleBar){
        JOptionPane.showMessageDialog(null, infoMessage, "Information: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
    public void resetAll(){
        if(pesudoCommands!=null)
        pesudoCommands.clear();
        if(commands!=null)
        commands.clear();
        pesudoCommandsString = "";
        nextCommand = 0;
        txtMicrooperation1.setText("");
        txtBus.setText("");
        txtClock.setText("0");
        txtElde.setText("false");
        clearFormView(false);
        Vmc.resetVirtualMachine();
        this.updateView();
        
    }
    void clearFormView(boolean update){
        if(!update)
        runtimeCodeListModel.clear();
        this.clearLabelsView();
        this.clearDataSegmentView();
        this.clearCodeSegmentView();
        this.clearStackSegmentView();
        txtAC.setText("");
        txtAR.setText("");
        txtDR.setText("");
        txtPC.setText("");
        txtSP.setText("");
        txtIR.setText("");
        
        
    }
    void clearLabelsView(){
        int rowsToRemove = labelListModel.getRowCount();
        //remove rows from the bottom one by one
        for (int i = rowsToRemove - 1; i >= 0; i--) {
            labelListModel.removeRow(i);
        }
    }
    void clearDataSegmentView(){
        int rowsToRemove = codeTableModel.getRowCount();
        //remove rows from the bottom one by one
        for (int i = rowsToRemove - 1; i >= 0; i--) {
            codeTableModel.removeRow(i);
        }
    }
    void clearCodeSegmentView(){
        int rowsToRemove = dataTableModel.getRowCount();
        //remove rows from the bottom one by one
        for (int i = rowsToRemove - 1; i >= 0; i--) {
            dataTableModel.removeRow(i);
        }
    }
    void clearStackSegmentView(){
        int rowsToRemove = stackTableModel.getRowCount();
        //remove rows from the bottom one by one
        for (int i = rowsToRemove - 1; i >= 0; i--) {
            stackTableModel.removeRow(i);
        }
    }
    public void saveMif(){
//        File file = new File("text.mif");//proje içinde text.txt adında bir txt oluşturun.
//        try(BufferedWriter br = new BufferedWriter(new FileWriter(file))){
//        br.write("This is line one");
//            br.newLine();
//            br.write("This is line two");
//            br.newLine();
//            br.write("Last line.");
//        } catch (IOException e) {
//            System.out.println("Unable to read file " +file.toString());
//        }
    }
}
