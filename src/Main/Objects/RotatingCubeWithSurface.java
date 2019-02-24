package Objects;

import DataTypes.Pair_3;
import DataTypes.Pair_4;
import DataTypes.Rotator_3D;
import DataTypes.Vector_3D;
import World.Level;
import javafx.util.Pair;

import java.awt.*;
import java.util.List;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

public class RotatingCubeWithSurface extends RotatingCube {
    //EFFECTS: create a RotatingCubeWithSurface
    public RotatingCubeWithSurface(Vector_3D location, String name, Rotator_3D r, Level l){
        super(location,name,r,l);
        List<Pair_4<Integer,Integer,Integer, Color>>surfaces = new CopyOnWriteArrayList<>();
        surfaces.add(new Pair_4<>(0,1,2,Color.red));
        surfaces.add(new Pair_4<>(1,2,3,Color.red));
        surfaces.add(new Pair_4<>(2,3,6,Color.red));
        surfaces.add(new Pair_4<>(0,1,4,Color.red));
        surfaces.add(new Pair_4<>(4,5,6,Color.red));
        surfaces.add(new Pair_4<>(5,6,7,Color.red));
        surfaces.add(new Pair_4<>(6,7,3,Color.red));
        surfaces.add(new Pair_4<>(4,0,2,Color.red));
        surfaces.add(new Pair_4<>(6,2,4,Color.red));
        surfaces.add(new Pair_4<>(1,3,5,Color.red));
        surfaces.add(new Pair_4<>(3,7,5,Color.red));
        surfaces.add(new Pair_4<>(1,4,5,Color.red));

        this.surfaces= surfaces;
        this.allSurfaces = generateSurfaces(this.surfaces);
    }
}
