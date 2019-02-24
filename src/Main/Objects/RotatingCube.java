package Objects;

import DataTypes.Rotator_3D;
import DataTypes.Vector_3D;
import Interfaces.Rotatable;
import World.Level;

public class RotatingCube extends DisplayObjectWithTick implements Rotatable {
    private boolean rotating = false;
    //REQUIRE: location and r is not null
    //MODIFIES: this
    //EFFECT: construct a RotatingCube
    public RotatingCube(String name, Level l){
        super(name,l);
    }
    //REQUIRE: location and r is not null
    //MODIFIES: this
    //EFFECT: construct a RotatingCube
    public RotatingCube(Vector_3D location,String name,Rotator_3D r,Level l){
        super(location,new Vector_3D[]{new Vector_3D(100, 100, -100), new Vector_3D(100, 100, 100),
                new Vector_3D(100, -100, -100), new Vector_3D(100, -100, 100), new Vector_3D(-100, 100, -100),
                new Vector_3D(-100, 100, 100), new Vector_3D(-100, -100, -100), new Vector_3D(-100, -100, 100)},
                new int[][]{{0,1},{0,2},{1,3},{2,3},{0,4},{1,5},{3,7},{2,6},{4,5},{4,6},{5,7},{6,7}},name,r,l);

    }
    //EFFECT: getRotating
    public boolean getRotating() {
        return rotating;
    }
    //EFFECT: setRotating
    public void setRotating(boolean rotating) {
        this.rotating = rotating;
    }

    //EFFECT: Rotate the Cube When clock ticks
    @Override
    public void tick() {
        if(rotating) {
            addRotationOffset(new Rotator_3D(0.01, 0.01, 0));
        }
    }


}
