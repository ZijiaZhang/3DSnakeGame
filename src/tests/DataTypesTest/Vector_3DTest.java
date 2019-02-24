package DataTypesTest;

import DataTypes.Vector_3D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector_3DTest {
    Vector_3D v;
    @BeforeEach
    void setUp() {
        v = new Vector_3D(1,2,1);
    }

    @Test
    void add() {
        Vector_3D result = v.add(new Vector_3D(1,1,1));
        assertEquals(result.getX(),2);
        assertEquals(result.getY(),3);
        assertEquals(result.getZ(),2);
    }

    @Test
    void minus() {
        Vector_3D result = v.minus(new Vector_3D(1,1,1));
        assertEquals(result.getX(),0);
        assertEquals(result.getY(),1);
        assertEquals(result.getZ(),0);
    }

    @Test
    void length() {
        assertEquals(v.length(),Math.sqrt(6));
        assertEquals(new Vector_3D().length(),0);
        assertEquals(new Vector_3D(-1,-1,-1).length(),Math.sqrt(3));
    }

    @Test
    void multiply() {
        Vector_3D result = v.multiply(2);
        assertEquals(result.getX(),2);
        assertEquals(result.getY(),4);
        assertEquals(result.getZ(),2);

    }

    @Test
    void divide() {
        Vector_3D result = v.divide(2);
        assertEquals(result.getX(),0.5);
        assertEquals(result.getY(),1);
        assertEquals(result.getZ(),0.5);
    }
}