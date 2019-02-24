package Objects;

import DataTypes.Pair_4;
import DataTypes.Rotator_3D;
import DataTypes.Vector_3D;
import Exceptions.AlreadyDestroyedException;
import Exceptions.InvalidColorException;
import World.Level;

import java.awt.*;
import java.util.ArrayList;

public class Cube extends DisplayObjectWithTick {
    //EFFECT: Copy a Cube
    public Cube(Cube c){
        super(c);
        //tick.cancel();
    }

    //EFFECT: GENERATE A NEW CUBE
    public Cube(Vector_3D location, String name, Rotator_3D r, double length,Color c, Level l){
        super(location,new Vector_3D[]{new Vector_3D(length, length, -length), new Vector_3D(length, length, length),
                        new Vector_3D(length, -length, -length), new Vector_3D(length, -length, length), new Vector_3D(-length, length, -length),
                        new Vector_3D(-length, length, length), new Vector_3D(-length, -length, -length), new Vector_3D(-length, -length, length)},
                new int[][]{{0,1},{0,2},{1,3},{2,3},{0,4},{1,5},{3,7},{2,6},{4,5},{4,6},{5,7},{6,7}},name,r,l);
        if(c==null){
            throw new InvalidColorException();
        }
        createSurfaces(c);
        this.allSurfaces = generateSurfaces(this.surfaces);
        //tick.cancel();
    }

    //Modifies: this
    //Effect: create surfaces with color c
    private void createSurfaces(Color c) {
        ArrayList<Pair_4<Integer,Integer,Integer, Color>> surfaces = new ArrayList<>();
        surfaces.add(new Pair_4<>(0,1,2,c));
        surfaces.add(new Pair_4<>(1,2,3,c));
        surfaces.add(new Pair_4<>(2,3,6,c));
        surfaces.add(new Pair_4<>(0,1,4,c));
        surfaces.add(new Pair_4<>(4,5,6,c));
        surfaces.add(new Pair_4<>(5,6,7,c));
        surfaces.add(new Pair_4<>(6,7,3,c));
        surfaces.add(new Pair_4<>(4,0,2,c));
        surfaces.add(new Pair_4<>(6,2,4,c));
        surfaces.add(new Pair_4<>(1,3,5,c));
        surfaces.add(new Pair_4<>(3,7,5,c));
        surfaces.add(new Pair_4<>(1,4,5,c));
        this.surfaces= surfaces;
    }

    @Override
    public void destroy(){
        if(l!=null)
            l.destroy(this);
    }
}
