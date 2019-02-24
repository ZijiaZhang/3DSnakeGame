package ui;

import Objects.DisplayObjectWithTick;

import javax.swing.*;
import java.util.List;

public class AllObjectDropDown extends JComboBox<String> {
    //EFFECT: construct the Object Drop Down
    public AllObjectDropDown(){
        this.setName("dropdown");
    }

    //REQUIRES: no entry is null
    //EFFECTS:Add Items to the Drop Down
    public void addItems(List<DisplayObjectWithTick> objects){
        for(DisplayObjectWithTick o :objects){
            addItem(o);
        }
    }
    //REQUIRES: object is not null
    //MODIFIES: this
    //EFFECTS:Add Item to the Drop Down
    public void addItem(DisplayObjectWithTick o){
        this.addItem(o.getName());
    }
}
