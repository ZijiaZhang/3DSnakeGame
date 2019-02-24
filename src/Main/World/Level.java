package World;

import DataTypes.Rotator_3D;
import DataTypes.Vector_3D;
import Exceptions.AlreadyDestroyedException;
import Interfaces.Destroyable;
import Interfaces.Saveable;
import Objects.*;
import ui.PaintWindow;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.lang.Math.negateExact;

public class Level implements Saveable, Destroyable {
    private List<DisplayObjectWithTick> allObj = new CopyOnWriteArrayList<>();
    private List<Destroyable> destroyList = new CopyOnWriteArrayList<>();
    private Snake snake;
    private ScoreKeeper scoreKeeper;
    private static Level l;
    //EFFECT: construct a new Level
    public Level(){
        //RotatingCube o = new RotatingCube(new Vector_3D(0,0,500),"Basic_Cube",new Rotator_3D(0,0,0),this);
        l=this;
        Mat p = new Mat(450,9,new Vector_3D(0,0,500),"Basic_Cube",new Rotator_3D(0,0,0),this);
        p.setRotating(true);
        scoreKeeper = new ScoreKeeper();
        scoreKeeper.resetScore();
    }
    //EFFECT: get all objects
    public List<DisplayObjectWithTick> getAllObj() {
        return allObj;
    }

    //EFFECT: destroy all object
    public void destroy() throws AlreadyDestroyedException{
        while(destroyList.size()>0){
                destroyList.get(0).destroy();
        }
    }
    //Modifies: this
    //EFFECTS: load Level From given data.
    public void load(List<String> data){
        if (Reset(data)) return;
        DisplayObjectWithTick temp=null;
            List<String> s = new CopyOnWriteArrayList<>();
            for(String l :data){
                if(l.startsWith("class:")) {
                    if (temp != null) {
                        temp.load(s);
                        s.clear();
                    }
                    temp = createObj(temp, l);
                }else {
                    s.add(l);
                }
            }
         if(temp!=null){
             temp.load(s);
             allObj.add(temp);
         }
    }

    private DisplayObjectWithTick createObj(DisplayObjectWithTick temp, String l) {
        if (l.contains("RotatingCube")) {
            temp = new RotatingCube(l.substring(6),this);
        } else if (l.contains("DisplayObjectWithTick")) {
            //temp = new DisplayObjectWithTick(l.substring(6),this);
        } else if (l.contains("Mat")) {
            temp = new Mat(l.substring(6),this);
        }
        return temp;
    }

    private boolean Reset(List<String> data) {
        if(data.size()==0)
            return true;
        try {
            destroy();
        }catch(AlreadyDestroyedException e){
            System.out.println("Redundant Destroy on" +e.getMessage());
        }
        allObj.clear();
        return false;
    }

    //EFFECT: save level to the file
    public void save(String filename){
        try {
            for(DisplayObjectWithTick d: allObj){
                d.save(d.getName());
            }
            SaveFile(filename);
        }catch (Exception e){
            System.out.println("Can't Save file");
        }
    }

    private void SaveFile(String filename) throws IOException {
        PrintWriter writer = new PrintWriter(filename, "UTF-8");
        for(DisplayObjectWithTick d: allObj) {
            writer.println("ObjectName:" +d.getName());
            List<String> lines = Files.readAllLines(Paths.get(d.getName()+".txt"));
            for(String s: lines){
                writer.println(s);
            }
        }
        writer.close();
    }

    //Modifies: this
    //EFFECT: Load file
    private List<String> readFile(String filename) {
        try {
            List<String> lines = new CopyOnWriteArrayList<>(Files.readAllLines(Paths.get(filename)));
            return lines;
        } catch (Exception e) {
            System.out.println("Error reading Files");
            return new CopyOnWriteArrayList<>();
        }
    }

    //Modifies: this
    //EFFECT: load data from file
    public void loadData(String filename){
        load(readFile(filename));
    }

    //Modifies: this
    //EFFECT: add object to Destroy list.
    public void addToDestroy(Destroyable d){
        destroyList.add(d);
    }

    //Modifies: this
    //EFFECT: add object to AllObject and Destroy list.
    public void addToAllObj(DisplayObjectWithTick o){
        o.addObserver(PaintWindow.getInstance());
        allObj.add(o);
        addToDestroy(o);
    }
    //EFFECT: return the snake
    public Snake getSnake() {
        return snake;
    }

    //Modifies: this, snake
    //EFFECT: set the snake
    public void setSnake(Snake snake) {
        if(this.snake==null) {
            this.snake = snake;
            this.snake.addLevel(this);
        }
    }

    //Modifies: this, snake
    //EFFECT: remove snake
    public void removeSnake(){
        if(snake!=null){
            Snake temp = snake;
            snake=null;
            temp.removeLevel();
        }

    }

    //EFFECT: Destroy all instances.
    public void destroy(Destroyable d){
        Iterator<DisplayObjectWithTick> iter = allObj.iterator();
        ArrayList<DisplayObjectWithTick> remove = new ArrayList<DisplayObjectWithTick>();
        while (iter.hasNext()) {
            DisplayObjectWithTick o = iter.next();
            if (o==d)
                remove.add(o);
        }
        allObj.removeAll(remove);

        Iterator<Destroyable> i = destroyList.iterator();
        ArrayList<Destroyable> destroyables = new ArrayList<Destroyable>();
        while (i.hasNext()) {
            Destroyable o = i.next();
            if (o==d)
                destroyables.add(o);
        }
        destroyList.removeAll(destroyables);
    }

    public static Level getInstance(){
        return l;
    }

    public int getScore(){
        return scoreKeeper.getScore();
    }
    public void resetScore(){
        scoreKeeper.resetScore();
    }
    public void addScore(){
        scoreKeeper.addScore();
    }
}
