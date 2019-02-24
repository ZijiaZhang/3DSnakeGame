package DataTypesTest;

import DataTypes.Vector_2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector_2DTest {
    Vector_2D v;
    @BeforeEach
    void setUp() {
        v = new Vector_2D(1,2);
    }

    @Test
    void add() {
        Vector_2D result = v.add(new Vector_2D(1,1));
        assertEquals(result.getX(),2);
        assertEquals(result.getY(),3);

    }

    @Test
    void minus() {
        Vector_2D result = v.minus(new Vector_2D(1,1));
        assertEquals(result.getX(),0);
        assertEquals(result.getY(),1);

    }

    @Test
    void length() {
        assertEquals(v.length(),Math.sqrt(5));
        assertEquals(new Vector_2D().length(),0);
        assertEquals(new Vector_2D(-1,-1).length(),Math.sqrt(2));
    }

    @Test
    void multiply() {
        Vector_2D result = v.multiply(2);
        assertEquals(result.getX(),2);
        assertEquals(result.getY(),4);


    }

    @Test
    void divide() {
        Vector_2D result = v.divide(2);
        assertEquals(result.getX(),0.5);
        assertEquals(result.getY(),1);

    }
    @Test
    void testSet(){
        Vector_2D result = new Vector_2D(v);
        result .setX(100);
        assertEquals(result.getX(),100);
        result.setY(200);
        assertEquals(result.getY(),200);
    }
}