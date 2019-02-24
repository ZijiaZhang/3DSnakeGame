package WorldTest;

import DataTypes.Rotator_3D;
import DataTypes.Vector_3D;
import Objects.*;
import World.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LevelTest {
    Level l;
    @BeforeEach
    void setUp() {
        l=new Level();
    }

    @Test
    void getAllObj() {
        try{
        l.destroy();
        }catch (Exception e){

        }
        assertEquals(l.getAllObj().size(),0);
        l.addToDestroy(new Cube(new Vector_3D(100,10,30),"new Cube",new Rotator_3D(),10, Color.red,l));
    }

    @Test
    void save(){
        l.getAllObj().clear();
        l.addToAllObj(new RotatingCube(new Vector_3D(0,0,500),"Basic_Cube",new Rotator_3D(0,0,0),l));
        l.getAllObj().add(new DisplayObjectWithTick("l",l));
        l.save("p");
        try {
            ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get("p")));
            assertEquals(lines.get(0),"ObjectName:Basic_Cube");
            assertEquals(lines.get(1),"class:Objects.RotatingCube");
            assertEquals(lines.get(2),"name:Basic_Cube");
            assertEquals(lines.get(3),"related:100.0,100.0,-100.0");
            assertEquals(lines.get(2),"name:Basic_Cube");
            assertEquals(lines.get(11),"location:0.0,0.0,500.0");
            assertEquals(lines.get(20),"lines:0,1");
            assertTrue(lines.contains("name:l"));
        }catch (Exception e){

        }

    }

    @Test
    void load(){
        l.loadData("a");
        assertEquals(l.getAllObj().size(),1);
    }

    @Test
    void testAddAndRemoveSnake(){
            Snake s = new Snake(10, null, null);
            l.removeSnake();
            assertNull(l.getSnake());
            l.setSnake(s);
            assertEquals(l.getSnake(),s);
            l.removeSnake();
            assertNull(l.getSnake());
    }
}