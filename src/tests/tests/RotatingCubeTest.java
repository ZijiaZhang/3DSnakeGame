package tests;

import DataTypes.Rotator_3D;
import DataTypes.Vector_3D;
import Objects.RotatingCube;
import World.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RotatingCubeTest {
    RotatingCube r;
    @BeforeEach
    void setUp() {
        r= new RotatingCube(new Vector_3D(0,0,0),"Rc",new Rotator_3D(),null);
    }

    @Test
    void getRotating() {
        assertFalse(r.getRotating());
    }

    @Test
    void setRotating() {
        r.setRotating(true);
        assertTrue(r.getRotating());
    }

    @Test
    void tick() {
        r.setRotating(true);
        r.tick();
        assertEquals(r.getAllPoints().get(0).getX(),99.99,0.001);
        assertEquals(r.getAllPoints().get(0).getY(),100.995,0.001);
        assertEquals(r.getAllPoints().get(0).getZ(),-99.005,0.001);
    }
}