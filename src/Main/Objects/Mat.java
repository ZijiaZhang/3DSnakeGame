package Objects;

import DataTypes.Pair_3;
import DataTypes.Rotator_3D;
import DataTypes.Vector_3D;
import Exceptions.IndexException;
import Interfaces.Rotatable;
import World.Level;
import ui.GameInstance;
import ui.Printer;

import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

public class Mat extends DisplayObjectWithTick implements Rotatable {
    private boolean rotating = false;
    private int length=0;
    private int gridNumber=0;
    private Snake s=null;
    private int size =0;
    private Food f=null;
    private List<Pair_3<Integer,Integer,Vector_3D>> allLoc = new CopyOnWriteArrayList<>();
    private Printer p = new Printer();
    //EFFECT: Construct a Basic Mat instance
    public Mat(String name, Level l){
        super(name,l);
    }

    //EFFECT: Construct a mat with given length and grid
    public Mat(int length, int gridNumber, Vector_3D location, String name, Rotator_3D r, Level l){
        super(location,new Vector_3D[]{new Vector_3D(length/2, length/2, -length/2), new Vector_3D(length/2, length/2, length/2),
                        new Vector_3D(length/2, -length/2, -length/2), new Vector_3D(length/2, -length/2, length/2), new Vector_3D(-length/2, length/2, -length/2),
                        new Vector_3D(-length/2, length/2, length/2), new Vector_3D(-length/2, -length/2, -length/2), new Vector_3D(-length/2, -length/2, length/2)},
                new int[][]{{0,1},{0,2},{1,3},{2,3},{0,4},{1,5},{3,7},{2,6},{4,5},{4,6},{5,7},{6,7}},name,r,l);
        lines.add(new int[]{0,5});
        lines.add(new int[]{1,4});
        lines.add(new int[]{2,7});
        lines.add(new int[]{3,6});
        initialize(length, gridNumber, location, name, r, l);
        updatePoints();
        allLines = generateLines(lines);
//        tick.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                tick();
//            }
//        }, 10, 20);

    }

    //Modifies: this
    //Effects: initialize the mat
    private void initialize(int length, int gridNumber, Vector_3D location, String name, Rotator_3D r, Level l) {
        this.allLines = generateLines(this.lines);
        this.length = length;
        this.gridNumber = gridNumber;
        this.size = length/gridNumber;
        this.s = new Snake(size, this,l);
        Cube c = new Cube(new Vector_3D(0,0,location.getZ()-length/2 - size/2),name,new Rotator_3D(),size/2, Color.red,l);
        s.addCube(c);
        s.location_x.add(0);
        s.location_y.add(0);
        for(int x=0; x<4*gridNumber+4;x++){
            for(int y=-gridNumber/2; y<=gridNumber/2;y++){
                Pair_3<Integer,Integer,Vector_3D> temp = new Pair_3<>(x,y,getLocation(x,y));
                allLoc.add(temp);
            }
        }
        generateFood();
        this.name = name;
        this.rotation = r;
        this.location = location;
    }

    //MODIFIES: this
    //EFFECTS: rotate object
    @Override
    public void addRotationOffset(Rotator_3D r) {
        super.addRotationOffset(r);
        for (int i=0;i<s.getLength();i++){
            Cube c = s.getBody().get(i);
            int X = s.location_x.get(i);
            int Y = s.location_y.get(i);
            c.addRotationOffset(r);
            c.addLocationOffset(obtainLocation(X,Y).minus(c.getLocation()));
        }
        f.addRotationOffset(r);
        f.addLocationOffset(obtainLocation(f.getX(),f.getY()).minus(f.getLocation()));
        for(Pair_3<Integer,Integer,Vector_3D> l : allLoc){
            Vector_3D v = l.getThird();
            Vector_3D p = v.minus(location);
            rotateXY(p,r.getXy());
            rotateXZ(p,r.getXz());
            rotateYZ(p,r.getYz());
            l.getThird().setX(p.getX()+location.getX());
            l.getThird().setY(p.getY()+location.getY());
            l.getThird().setZ(p.getZ()+location.getZ());
        }
    }

    //EFFECTS: set Rotating
    @Override
    public void setRotating(boolean rotating) {
        this.rotating = rotating;
    }

    @Override
    public boolean getRotating() {
        return rotating;
    }

    //EFFECT: Rotate the Cube when ticks
    @Override
    public void tick() {
        if(rotating) {
            addRotationOffset(new Rotator_3D(0, -0.005, 0));
            s.start();
        }
    }

    //EFFECT: Calculate the 3D location by the Coordinate on the Cube
    public Vector_3D getLocation(int x, int y){
        if((double)x/(double)(gridNumber+1)<=0.5){
            return new Vector_3D(x*size,y*size,location.getZ()-length/2 - length/(gridNumber+1)/2);
        }else if((double)x/((double)gridNumber+1)<=1.5){
            return new Vector_3D(length/2+size/2,y*size,location.getZ()-length/2 - length/(gridNumber+1)/2+(x-(gridNumber+1)/2)*size);
        }else if((double)x/(double)(gridNumber+1)<=2.5){
            return new Vector_3D(length/2+size/2 - ((x-(gridNumber+1)/2)-(gridNumber+1))*size,y*size,location.getZ()+length/2+size/2);

        }else if((double)x/(double)(gridNumber+1)<=3.5){
            return new Vector_3D(-length/2-size/2,y*size,location.getZ()+length/2+size/2-((x-(gridNumber+1)/2)-(gridNumber+1)-(gridNumber+1))*size);
        }
        else if((double)x/(double)(gridNumber+1)<=4){
            return new Vector_3D((x-((gridNumber+1)*4))*size,y*size,location.getZ()-length/2-size/2);
        }

        return null;
    }

    //EFFECT: Retrieve the 3D location by the Coordinate on the Cube
    public Vector_3D obtainLocation(int x, int y){
        for(Pair_3<Integer,Integer,Vector_3D> l : allLoc){
            int X = l.getFirst();
            int Y = l.getSecond();
            if(x==X && y==Y){
                return l.getThird();
            }
        }
        p.printError("no info for location");
        throw new IndexException();
    }

    //EFFECT: return the Grid Number
    public int getGridNumber() {
        return gridNumber;
    }
    //EFFECT: generate food at Random location.
    public void generateFood(){
        Random rand = new Random();
        int X = rand.nextInt(4*gridNumber+4);
        int Y = rand.nextInt(gridNumber)-gridNumber/2;
        f = new Food(obtainLocation(X,Y), "food",new Rotator_3D(),size/5,l,this,X,Y);
        p.printMessage("Food Generated");
    }
    //Effects: Return the food
    public Food getFood() {
        return f;
    }

    //Effects: Destroy the food and generate new ones.
    public void foodEaten() {
        if(l!=null)
        l.addScore();
        f.destroy();
        generateFood();
        f.start();
        p.printMessage("Food Eaten Score Added");
    }
}
