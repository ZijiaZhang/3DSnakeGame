package Objects;

import DataTypes.Rotator_3D;
import DataTypes.Vector_3D;
import Interfaces.Destroyable;
import World.Level;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Object extends Observable {
    protected String name;
    protected Vector_3D location = new Vector_3D();
    protected Rotator_3D rotation= new Rotator_3D();
    protected Level l = null;

    //EFFECT: get current Location
    public Vector_3D getLocation() {
        return location;
    }
    //EFFECT: get original Rotation
    public Rotator_3D getRotation() {
        return rotation;
    }
    //EFFECT: set Location
    public void setLocation(Vector_3D location) {
        this.location = location;
    }


}
