package ui;

import java.util.Observable;
import java.util.Observer;

public class Printer implements Observer {
    private int i =0;
    private static Printer p;
    public Printer(){
        p = this;
    }
    public void printMessage(String m){
        System.out.println(m);
    }
    public void printError(String m){
        System.out.println("Error: " + m);
    }

    @Override
    public void update(Observable o, Object arg) {
        if("Dir Change".equals(arg)) {
            i++;
            printMessage(arg + " " + i);
        }
    }

    public static Printer getInstance(){
        if(p==null){
            p = new Printer();
        }
        return p;
    }
}
