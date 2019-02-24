package DataTypesTest;

import DataTypes.Rotator_3D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Rotator_3DTest {
    Rotator_3D r;
    @BeforeEach
    void setUp() {
        r= new Rotator_3D(10,10,10);
    }

    @Test
    void add() {
        Rotator_3D result = r.add(new Rotator_3D(1,2,3));
        assertEquals(result.getXy(),11);
        assertEquals(result.getXz(),12);
        assertEquals(result.getYz(),13);
    }
    @Test
    void testSet(){
        assertEquals(r.getXy(),10);
        assertEquals(r.getYz(),10);
        assertEquals(r.getXz(),10);
        r.setXy(0);
        assertEquals(r.getXy(),0);
        r.setXz(30);
        assertEquals(r.getXz(),30);
        r.setYz(120);
        assertEquals(r.getYz(),120);
    }
}