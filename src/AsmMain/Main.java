package AsmMain;

public class Main {
    public static void main(String[] args) {
      DebuggerWindow Dw = new DebuggerWindow();
      DebuggerController Dc = new DebuggerController();
      Dw.setDebuggerController(Dc);
    }
}
