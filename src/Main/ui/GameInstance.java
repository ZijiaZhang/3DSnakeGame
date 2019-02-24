package ui;

import DataTypes.Rotator_3D;
import Players.PlayerManager;

import java.awt.*;
import java.util.Scanner;

import static java.lang.Math.toRadians;

public class GameInstance {
   private  TestWindow tw;// =new TestWindow();
    private PlayerManager playerManager = new PlayerManager();
    private static GameInstance game;
    private static MainMenu sw;
    private Printer p = new Printer();

    public static void main(String[] args){
        getInstance();
        sw = new MainMenu();

        //Level l = new Level();
        //l.load();
        //l.save();
        //l.destroy();

    }
    //MODIFIES: this
    //EFFECT: obtain command line input and print solution to users
    private void recognizeInput(){
        printMessage("'left' to turn left");
        printMessage("'right' to turn right");
        printMessage("'quit' to quit");
        printMessage("'save' to save");
        printMessage("'load' to load");
        String operation = "";
        Scanner scanner = new Scanner(System.in);
        while (true){
            printMessage("Please select an option");
            operation = scanner.nextLine();
            int direction=0;
            if(operation.equals("left")) {
                direction = -1;
            }
            else if(operation.equals("right")){
                direction = 1;
            }
            else if(operation.equals("quit")){
                    tw.dispose();
                break;
            }else if(operation.equals("save")){
                printMessage("Please give a name to this save");
                String save = scanner.nextLine();
                tw.save(save);
            }
            else if(operation.equals("load")){
                printMessage("Please  enter the name to load");
                String load = scanner.nextLine();
                tw.load(load);
            }
            else{
                printError();
                break;
            }
            tw.getControlledObject().addRotationOffset(new Rotator_3D(toRadians(direction*10.0),0,0));
            tw.getPw().repaint();

        }

    }

    //EFFECT: get the Test Window
    public  TestWindow getTw() {
     return tw;
   }

    //EFFECT: Print 'Error'
    private void printError(){
        p.printError("");
    }
    //EFFECT: Print a Message to User
    private void  printMessage(String m){
        p.printMessage(m);
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public static GameInstance getInstance(){
        if(game==null)
            game = new GameInstance();
        return game;
    }

    public void endGame(){
       sw.gameEnd();
    }

    public void quit() {
        for (Frame T: Frame.getFrames()){
            T.dispose();
        }
    }
}
