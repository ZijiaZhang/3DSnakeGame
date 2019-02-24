package uiTest;

import DataTypes.Rotator_3D;
import DataTypes.Vector_3D;
import Objects.DisplayObjectWithTick;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.AllObjectDropDown;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AllObjectDropDownTest {
    AllObjectDropDown dropDown;
    @BeforeEach
    void setUp() {
        dropDown = new AllObjectDropDown();
    }

    @Test
        void addItems() {
        ArrayList<DisplayObjectWithTick> itemToAdd = new ArrayList<DisplayObjectWithTick>();
        itemToAdd.add(new DisplayObjectWithTick(new Vector_3D(0,0,500),new Vector_3D[]{},new int[][]{},"c1",new Rotator_3D(0,0,0),null));
        itemToAdd.add(new DisplayObjectWithTick(new Vector_3D(0,0,500),new Vector_3D[]{},new int[][]{},"c2",new Rotator_3D(0,0,0),null));
        dropDown.addItems(itemToAdd);
        assertEquals(dropDown.getItemCount(),2);
        assertEquals(dropDown.getItemAt(0),"c1");
        assertEquals(dropDown.getItemAt(1),"c2");

    }
}