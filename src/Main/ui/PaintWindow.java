package ui;

import DataTypes.Pair_4;
import DataTypes.Vector_2D;
import Exceptions.AlreadyDestroyedException;
import Interfaces.Destroyable;
import Objects.DisplayObjectWithTick;
import DataTypes.Vector_3D;
import World.Level;
import javafx.util.Pair;
import Objects.TickObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class PaintWindow extends Frame implements Destroyable, Observer,Runnable {
    private Thread t;
    private long prevTime = 0;
    private int width = 600;
    private int height = 600;
    private Level level;
    private int tickRate = 30;
    private List<TickObject> waitList = new ArrayList<>();
    private static PaintWindow paintWindow;
    //EFFECTS: Construct a new PaintWindow
    public PaintWindow() {
        paintWindow = this;
        level = new Level();
        setSize(width, height);
        setTitle("Paint Window");
        setLayout(null);
        setVisible(true);
        repaint();
        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                processKeyEvent(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };
        addKeyListener(keyListener);


    }
    //EFFECTS: get current level
    public Level getLevel() {
        return level;
    }




    //MODIFIES: this
    //EFFECTS: dispose the window
    @Override
    public void dispose() {
        destroy();
        try {
            level.destroy();
        }catch (AlreadyDestroyedException e){
            System.out.println("Redundant Destroy on" +e.getMessage());
        }
        super.dispose();
    }


    @Override
    protected void processKeyEvent(KeyEvent e) {
        switch (e.getKeyChar()){
            case 's': getLevel().getSnake().setDirection(2);
                break;
            case 'a': getLevel().getSnake().setDirection(1);
                break;
            case 'd': getLevel().getSnake().setDirection(0);
                break;
            case 'w': getLevel().getSnake().setDirection(3);
                break;
        }
    }

    @Override
    public void destroy() {}

    public int getScore(){
        return level.getScore();
    }

    public void resetScore(){
        level.resetScore();
    }

    @Override
    public void update(Observable o, Object arg) {
        if("Finished".equals(arg)) {
            if (!waitList.contains(o)) {
                waitList.add((TickObject) o);
            }
            //System.out.println(Thread.getAllStackTraces().size());
            //System.out.println(waitList.size() + "  " + level.getAllObj().size());
            if (waitList.size() >= level.getAllObj().size()) {
                start();
            }
        }else if("Destroyed".equals(arg)){
            start();
        }
    }

    public void callChange(){
        waitList = new ArrayList<>();
        Iterator<DisplayObjectWithTick> iter = level.getAllObj().iterator();
        while (iter.hasNext()) {
            DisplayObjectWithTick i = iter.next();
            i.start();
        }
    }

    public static PaintWindow getInstance(){
        if(paintWindow==null){
            paintWindow = new PaintWindow();
        }
        return paintWindow;
    }

    @Override
    public void run() {
        while (!(prevTime == 0 || System.currentTimeMillis() - prevTime > tickRate)) {
            try{Thread.sleep(1);}catch (Exception e){}
        }
            prevTime = System.currentTimeMillis();
            repaint();
    }
    public void start(){
            t = new Thread(this, "");
            t.start();

    }


    @Override
    public void paint(Graphics g) {
        Iterator<DisplayObjectWithTick> iter = level.getAllObj().iterator();
        while (iter.hasNext()) {
            DisplayObjectWithTick i = iter.next();
            List<Pair<Vector_3D,Vector_3D>> lines = i.getAllLines();
            for (Pair<Vector_3D,Vector_3D> j : lines) {
                drawLine(g, j.getKey(), j.getValue());
            }
            for(Pair_4<Vector_3D,Vector_3D,Vector_3D,Color> t: i.getAllSurfaces()){
                drawSurface(g,t.getFirst(),t.getSecond(),t.getThird(),t.getForth());
            }
        }
        callChange();
    }


    //REQUIRE: Graphics is not null
    //EFFECTS: Draw the line on teh screen, based on 3D coordinate
    private void drawLine(Graphics g, Vector_3D start, Vector_3D finish) {
        Vector_2D startLocation = locationToScreen(start);
        Vector_2D finishLocation = locationToScreen(finish);
        int startLocation_x = (int) startLocation.getX();
        int startLocation_y = (int) startLocation.getY();
        int finishLocation_x = (int) finishLocation.getX();
        int finishLocation_y = (int) finishLocation.getY();
        g.setColor(Color.BLACK);
        g.drawLine(startLocation_x, startLocation_y, finishLocation_x, finishLocation_y);
    }

    private void drawSurface(Graphics g, Vector_3D P1, Vector_3D P2,Vector_3D P3, Color c) {
        Vector_2D P1Location = locationToScreen(P1);
        Vector_2D P2Location = locationToScreen(P2);
        Vector_2D P3Location = locationToScreen(P3);
        int P1Location_x = (int) P1Location.getX();
        int P1Location_y = (int) P1Location.getY();
        int P2Location_x = (int) P2Location.getX();
        int P2Location_y = (int) P2Location.getY();
        int P3Location_x = (int) P3Location.getX();
        int P3Location_y = (int) P3Location.getY();
        g.setColor(c);
        g.fillPolygon(new int[]{P1Location_x,P2Location_x,P3Location_x},new int[]{P1Location_y,P2Location_y,P3Location_y},3);
    }

    //Effects Return the 2D coordinate location on screen.
    private Vector_2D locationToScreen(Vector_3D start) {
        double x = start.getX();
        double z = start.getY();
        double d = start.getZ();
        double sceneWidth = d * 2;
        double x_off = x / (sceneWidth) * width;
        double y_off = z / ((((double)height) /((double)width))* sceneWidth) * height;
        double x_p = width / 2.0 + x_off;
        double y_p = height / 2.0 + y_off;
        return new Vector_2D(x_p, y_p);
    }
}
