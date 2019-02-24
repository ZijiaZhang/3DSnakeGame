package uiTest;

import DataTypes.Vector_3D;
import Interfaces.Rotatable;
import Objects.RotatingCube;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.TestWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import static org.junit.jupiter.api.Assertions.*;

class TestWindowTest {
    TestWindow testWindow;

    @BeforeEach
    void setUp() {
        testWindow = new TestWindow();
    }

    @Test
    void getControlledObject() {
        assertEquals(testWindow.getControlledObject().getName(),"Basic_Cube");

    }

//    @Test
//    void actionPerformed() {
//        Component[] cl = testWindow.getComponents();
//        JButton left=null;
//        JButton right=null;
//        JButton rotate=null;
//        for(Component c : cl){
//            if(c.getName().equals("left")) left = (JButton)c;
//            if(c.getName().equals("right")) right = (JButton)c;
//            if(c.getName().equals("rotate")) rotate = (JButton)c;
//
//        }
//        assertNotEquals(left,null);
//        assertNotEquals(right,null);
//        assertNotEquals(rotate,null);
//        assertNotEquals(testWindow.getControlledObject(),null);
//        Vector_3D v = testWindow.getControlledObject().getAllPoints().get(0);
//        left.doClick();
//        assertNotEquals(v.getX(),100);
//        assertNotEquals(v.getY(),100);
//        assertNotEquals(v.getZ(),400);
//        right.doClick();
//        assertEquals(v.getX(),100,0.0001);
//        assertEquals(v.getY(),100,0.0001);
//        assertEquals(v.getZ(),400,0.0001);
//        rotate.doClick();
//        assertTrue(((Rotatable)testWindow.getControlledObject()).getRotating());
//        rotate.doClick();
//        assertFalse(((Rotatable)testWindow.getControlledObject()).getRotating());
//    }
//
//    @Test
//    void changeControlledObject() {
//        testWindow.changeControlledObject(3);
//        assertNull(testWindow.getControlledObject());
//        testWindow.changeControlledObject(0);
//        assertEquals(testWindow.getControlledObject().getName(),"Basic_Cube");
//    }
}