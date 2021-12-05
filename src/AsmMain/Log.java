package AsmMain;
public class Log {
    int index =5555;
    int nextIndex;
    String description;
    String nextDescription;
    int clock;
    public boolean stop = false;
    public Log(int index,String descripton) {
        this.description = descripton;
        this.index = index;
    }
    public Log(String descripton) {
        this.description = descripton;
    }
    public Log stop(){
      stop = true;
      return this;
    }
    
}
