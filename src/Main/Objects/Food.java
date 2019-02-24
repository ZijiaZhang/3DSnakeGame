package Objects;

import DataTypes.Rotator_3D;
import DataTypes.Vector_3D;
import World.Level;

import java.awt.*;

public class Food extends Cube {
    private Mat m;
    private int X;
    private int Y;

    //Effects: Construct a new food
    public Food(Vector_3D location, String name, Rotator_3D r, double length , Level l, Mat m, int X, int Y){
        super(location,name,r,length, Color.green,l);
        this.m = m;
        this.X = X;
        this.Y = Y;
    }
    //Effects: Return the X location
    public int getX() {
        return X;
    }
    //Effects: Return the Y location
    public int getY() {
        return Y;
    }
}
