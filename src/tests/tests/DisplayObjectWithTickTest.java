package tests;

import DataTypes.Rotator_3D;
import DataTypes.Vector_3D;
import Exceptions.InvalidVectorException;
import Objects.DisplayObjectWithTick;
import World.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DisplayObjectWithTickTest {

    DisplayObjectWithTick o;
    @BeforeEach
    void setUp() {
        o=new DisplayObjectWithTick(new Vector_3D(0,0,600), new Vector_3D[]{new Vector_3D(100, 100, -100), new Vector_3D(100, 100, 100)},
                new int[][]{{0,1}},"a1",new Rotator_3D(),null);
    }

    @Test
    void generateLines() {
        assertEquals(o.getAllLines().get(0).getKey().getX(),100);
        assertEquals(o.getAllLines().get(0).getKey().getY(),100);
        assertEquals(o.getAllLines().get(0).getKey().getZ(),500);
        assertEquals(o.getAllLines().get(0).getValue().getX(),100);
        assertEquals(o.getAllLines().get(0).getValue().getY(),100);
        assertEquals(o.getAllLines().get(0).getValue().getZ(),700);
    }

    @Test
    void updatePoints() {
        assertEquals(o.getAllPoints().get(0).getX(),100);
        assertEquals(o.getAllPoints().get(0).getY(),100);
        assertEquals(o.getAllPoints().get(0).getZ(),500);
        assertEquals(o.getAllPoints().get(1).getX(),100);
        assertEquals(o.getAllPoints().get(1).getY(),100);
        assertEquals(o.getAllPoints().get(1).getZ(),700);
    }

    @Test
    void addRotationOffset() {
        o.addRotationOffset(new Rotator_3D(Math.PI,0,0));
        assertEquals(o.getAllPoints().get(0).getX(),-100,0.001);
        assertEquals(o.getAllPoints().get(0).getY(),-100,0.001);
        assertEquals(o.getAllPoints().get(0).getZ(),500,0.001);
        assertEquals(o.getAllPoints().get(1).getX(),-100,0.001);
        assertEquals(o.getAllPoints().get(1).getY(),-100,0.001);
        assertEquals(o.getAllPoints().get(1).getZ(),700,0.001);
        o.addRotationOffset(new Rotator_3D(0,Math.PI,0));
        assertEquals(o.getAllPoints().get(0).getX(),100,0.001);
        assertEquals(o.getAllPoints().get(0).getY(),-100,0.001);
        assertEquals(o.getAllPoints().get(0).getZ(),700,0.001);
        assertEquals(o.getAllPoints().get(1).getX(),100,0.001);
        assertEquals(o.getAllPoints().get(1).getY(),-100,0.001);
        assertEquals(o.getAllPoints().get(1).getZ(),500,0.001);
        o.addRotationOffset(new Rotator_3D(0,0,Math.PI));
        assertEquals(o.getAllPoints().get(0).getX(),100,0.001);
        assertEquals(o.getAllPoints().get(0).getY(),100,0.001);
        assertEquals(o.getAllPoints().get(0).getZ(),500,0.001);
        assertEquals(o.getAllPoints().get(1).getX(),100,0.001);
        assertEquals(o.getAllPoints().get(1).getY(),100,0.001);
        assertEquals(o.getAllPoints().get(1).getZ(),700,0.001);
    }

    @Test
    void getName() {
        assertEquals(o.getName(),"a1");
        o =new DisplayObjectWithTick(new Vector_3D(0,0,500),new Vector_3D[]{},new int[][]{},"c1",new Rotator_3D(0,0,0),null);
        assertEquals(o.getName(),"c1");
        try {
            o = new DisplayObjectWithTick(null, new Vector_3D[]{}, new int[][]{}, "c1", new Rotator_3D(0, 0, 0), null);
            fail("");
        }catch (InvalidVectorException e){

        }
    }

    @Test
    void setName() {
        o.setName("Hello");
        assertEquals(o.getName(),"Hello");
    }
    @Test
    void getLocation(){
        o.setLocation(new Vector_3D(100,200,100));
        assertEquals(o.getLocation().getX(),100);
        assertEquals(o.getLocation().getY(),200);
        assertEquals(o.getLocation().getZ(),100);
    }
    @Test
    void getRotation(){
        assertEquals(o.getRotation().getXy(),0);
        assertEquals(o.getRotation().getXz(),0);
        assertEquals(o.getRotation().getYz(),0);
    }
    @Test
    void related(){
        assertEquals(o.getRelatedAllPoints().size(),2);
    }

    @Test
    void copyObj(){
        Level l = new Level();
        o=new DisplayObjectWithTick(new Vector_3D(0,0,600), new Vector_3D[]{new Vector_3D(100, 100, -100), new Vector_3D(100, 100, 100)},
                new int[][]{{0,1}},"a1",new Rotator_3D(),l);
        DisplayObjectWithTick d = new DisplayObjectWithTick(o);
        assertEquals(o.getAllPoints().size(),d.getAllPoints().size());
        assertEquals(o.getName(),d.getName());
        assertEquals(o.getAllLines().size(),d.getAllLines().size());
        assertEquals(o.getAllSurfaces().size(),d.getAllSurfaces().size());
        assertEquals(o.getSurfaces().size(),d.getSurfaces().size());


    }
}