package Interfaces;

import java.util.ArrayList;
import java.util.List;

public interface Saveable {
    void save(String filename);
    void load(List<String> data);
}
