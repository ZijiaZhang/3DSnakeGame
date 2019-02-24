package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GeneralButton extends JButton {
    //MODIFIES: this
    //REQUIRES: label, color are not null, size_X and size_Y is greater than 0, name is unique
    //EFFECTS: constructs a button.
    public GeneralButton(String label, Color color, int size_X, int size_Y, String name, ActionListener i){
        super(label);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        this.setBackground(color);
        this.setOpaque(true);
        this.setPreferredSize(new Dimension(size_X,size_Y));
        this.setName(name);
        this.addActionListener(i);
    }
}
