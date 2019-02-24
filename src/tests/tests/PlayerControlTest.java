package tests;

import DataTypes.Rotator_3D;
import DataTypes.Vector_3D;
import Objects.DisplayObjectWithTick;
import Objects.PlayerControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerControlTest {
    PlayerControl p;
    @BeforeEach
    void setUp() {
        p=new PlayerControl();
    }
    @Test
    void testPlayerControl(){
        p.setControlledObject(new DisplayObjectWithTick(new Vector_3D(0,0,500),new Vector_3D[]{},new int[][]{},"c1",new Rotator_3D(0,0,0),null));
        DisplayObjectWithTick o  = p.getControlledObject();
        assertEquals(o.getName(),"c1");
        assertTrue(o.getAllPoints().isEmpty());
        assertTrue(o.getAllLines().isEmpty());
    }
}