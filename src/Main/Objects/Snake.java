package Objects;

import DataTypes.Rotator_3D;
import DataTypes.Vector_3D;
import Exceptions.AlreadyDestroyedException;
import Interfaces.Destroyable;
import World.Level;
import ui.GameInstance;
import ui.Printer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class Snake extends TickObject {
    private ArrayList<Cube> body = new ArrayList<Cube>();
    private boolean grow =false;
    private Mat m;
    ArrayList<Integer> location_x = new ArrayList<>();
    ArrayList<Integer> location_y = new ArrayList<>();
    private double size = 0;
    private int direction =0;
    private int length =0;

    private Printer p  = new Printer();
    //EFFECTS: Generate new Snake
    public Snake(double size, Mat m, Level l){
        super(200,l);
        this.size= size;
        this.m = m;
        addObserver(p);
        if(l!=null){
            l.setSnake(this);
            l.addToDestroy(this);
        }
    }

    //EFFECTS: add new Body Block.
    public void addCube(Cube c){
        body.add(c);
        length++;
    }
    //Modifies: this, m
    //EFFECTS: handle the movement of the snake when clock ticks.
    public void tick(){
        if(body.size()==0) return;
        int location_xx =location_x.get(0);
        int location_yy =location_y.get(0);
        if(direction == 0){
            location_xx +=1;
        }else if(direction == 1){
            location_xx -=1;
        } else if(direction == 2){
            location_yy +=1;
        } else if(direction == 3) {
            location_yy -= 1;
        }
        if(location_xx<0)
            location_xx+=m.getGridNumber()*4+4;
        location_xx%=m.getGridNumber()*4+4;
        if(!isDead(location_xx,location_yy)) {
            Movement(location_xx, location_yy);
        }else{
            //Do sth WHen dead
            GameInstance.getInstance().endGame();
            try {
                destroy();
            }catch(AlreadyDestroyedException e){

            }
        }
    }

    private void Movement(int location_xx, int location_yy) {
        Cube c = body.get(body.size() - 1);
        body.add(0, c);
        if(grow){
            growing(location_xx, location_yy, c);
        }else {
            notGrowing(location_xx, location_yy);
        }
        c.addLocationOffset(m.obtainLocation(location_xx, location_yy).minus(c.getLocation()));
        if (isHavingFood(location_xx,location_yy)){
            m.foodEaten();
            grow();
        }
    }

    private void notGrowing(int location_xx, int location_yy) {
        body.remove(body.size() - 1);
        location_x.add(0, location_xx);
        location_y.add(0, location_yy);
        location_x.remove(location_x.size() - 1);
        location_y.remove(location_y.size() - 1);
    }

    private void growing(int location_xx, int location_yy, Cube c) {
        Cube d= new Cube(c);
        body.set(body.size() - 1,d);
        location_x.add(0, location_xx);
        location_y.add(0, location_yy);
        length++;
        d.start();
        grow = false;
    }

    //Modifies: this
    //Effects: destroy all the bodies
    @Override
    public void destroy() throws AlreadyDestroyedException {
        for(Cube c: body){
            c.destroy();
        }
        super.destroy();
    }

    //EFFECT: Return true if the state of the snake is already dead, false otherwise
    private boolean isDead(int x, int y){
        if(y>m.getGridNumber()/2 || -y>m.getGridNumber()/2){
            return true;
        }
        int counter =0;
        for(int i=0; i<location_x.size();i++){
            int X = location_x.get(i);
            int Y = location_y.get(i);
            if (X ==x && Y==y){
                counter++;
            }
        }
        return counter>=1;
    }

    //Effects: return all the body cubes.
    public ArrayList<Cube> getBody() {
        return body;
    }

    //Effects: change the direction of the snake moving.
    public void setDirection(int direction) {
        if(this.direction!=direction) {
            this.direction = direction;
            setChanged();
            notifyObservers("Dir Changed");
        }
    }

    //Modifies: this
    //Effects: change the length by 1
    public void addLength(){
        length++;
    }
    //Effects: Get the current length of the snake.
    public int getLength() {
        return length;
    }

    //Effects: Return true if the snake head has the same location as the food, false otherwise.
    private boolean isHavingFood(int x, int y){
        return (x==m.getFood().getX() && y==m.getFood().getY());
    }
    //Modifies: this
    //Effects: Change the growth to true.
    private void grow(){
        grow = true;
    }

    //Requires: l is not null
    //Modifies: this
    //Effects: setLevel to the Snake and do the same to the Level
    public void addLevel(Level l){
        if(this.l==null) {
            this.l = l;
            l.setSnake(this);
        }
    }

    public void removeLevel(){
        if(this.l!=null) {
            Level temp = l;
            this.l = null;
            temp.removeSnake();
        }
    }

}
