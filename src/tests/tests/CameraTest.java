package tests;

import Objects.Camera;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CameraTest {
    Camera c;
    @BeforeEach
    void setUp() {
    c = new Camera();
    }

    @Test
    void getFov() {
        assertEquals(c.getFov(),90);
        c.setFov(10);
        assertEquals(c.getFov(),10);
    }

}