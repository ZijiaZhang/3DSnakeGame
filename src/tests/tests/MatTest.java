package tests;

import DataTypes.Rotator_3D;
import DataTypes.Vector_3D;
import Objects.Food;
import Objects.Mat;
import World.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatTest {
    Mat mat;
    @BeforeEach
    void setUp() {
        mat = new Mat(200,2,new Vector_3D(0,0,500),"Mat",new Rotator_3D(),null);
    }
    @Test
    void mat(){
        assertEquals(mat.getAllPoints().get(0).getX(),100);
        assertEquals(mat.getAllPoints().get(0).getY(),100);
        assertEquals(mat.getAllPoints().get(0).getZ(),400);
        Food f =mat.getFood();
        mat.foodEaten();
        assertNotEquals(f,mat.getFood());
    }
}