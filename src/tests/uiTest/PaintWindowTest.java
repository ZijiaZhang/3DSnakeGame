package uiTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.PaintWindow;

import static org.junit.jupiter.api.Assertions.*;

class PaintWindowTest {
    PaintWindow paintWindow;
    @BeforeEach
    void setUp() {
        paintWindow=new PaintWindow();
    }

    @Test
    void getLevel() {
        assertEquals(paintWindow.getLevel().getAllObj().get(0).getName(),"Basic_Cube");
    }
}