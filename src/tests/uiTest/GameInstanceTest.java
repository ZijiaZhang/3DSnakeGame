package uiTest;

import DataTypes.Vector_3D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.GameInstance;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class GameInstanceTest {

    GameInstance ui;
    @BeforeEach
    void setUp() {
        ui =new GameInstance();
    }

//    @Test
//    void testLeftRight() {
//        ByteArrayInputStream in = new ByteArrayInputStream("left\nright\nquit".getBytes());
//        System.setIn(in);
//        ui.main(null);
//        Vector_3D v = ui.getTw().getControlledObject().getAllPoints().get(0);
//        assertEquals(v.getX(),100,0.001);
//        assertEquals(v.getY(),100,0.001);
//        assertEquals(v.getZ(),400,0.001);
//    }
//    @Test
//    void testError(){
//        ByteArrayInputStream in = new ByteArrayInputStream("aaa".getBytes());
//        System.setIn(in);
//        ByteArrayOutputStream output = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(output));
//        ui.main(null);
//        Vector_3D v = ui.getTw().getControlledObject().getAllPoints().get(0);
//        assertEquals("'left' to turn left\n" +
//                "'right' to turn right\n" +
//                "'quit' to quit\n" +
//                "'save' to save\n"+
//        "'load' to load\n"+
//                "Please select an option\nError\n",output.toString());
//    }

}