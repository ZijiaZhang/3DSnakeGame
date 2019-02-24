package ui;

import DataTypes.Rotator_3D;
import Interfaces.Rotatable;
import Objects.DisplayObjectWithTick;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static java.lang.Math.toRadians;

public class TestWindow extends Frame implements ActionListener {

    private GeneralButton leftButton;
    private GeneralButton rightButton;
    private GeneralButton rotateButton;
    private GeneralButton saveButton;
    private GeneralButton loadButton;
    private AllObjectDropDown allObjectDropDown;
    private int width = 600;
    private int height = 600;
    private PaintWindow pw = new PaintWindow();
    private DisplayObjectWithTick controlledObject;
    //MODIFIES: this
    //EFFECTS:
    public TestWindow()
    {
        setSize(width,height);
        setTitle("Test Window");
        setLayout(null);
        leftButton = new GeneralButton("<-",Color.WHITE,100,50,"left",this);
        rightButton = new GeneralButton("->" , Color.WHITE , 100,50,"right",this);
        rotateButton = new GeneralButton("Rotate", Color.GREEN,100,50,"rotate",this);
        saveButton = new GeneralButton("Save Level", Color.yellow,100,50,"save",this);
        loadButton = new GeneralButton("Load Level", Color.green,100,50,"load",this);
        allObjectDropDown = new AllObjectDropDown();
        allObjectDropDown.addItems(pw.getLevel().getAllObj());
        addComponents(allObjectDropDown, 50,200);
        addComponents(leftButton, 50,10);
        addComponents(rightButton, 150,10);
        addComponents(rotateButton,150,100);
        addComponents(saveButton,150,200);
        addComponents(loadButton,150,300);
        setVisible(true);
        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                processKeyEvent(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };
        addKeyListener(keyListener);
        controlledObject = pw.getLevel().getAllObj().get(0);
    }

    public DisplayObjectWithTick getControlledObject() {
        return controlledObject;
    }


    //EFFECTS:ADD Component to Window
    private void addComponents(Component c , int x, int y){
        add(c);
        Insets insets = this.getInsets();
        Dimension size = c.getPreferredSize();
        c.setBounds(x + insets.left, y + insets.top,
                size.width, size.height);
    }

    //EFFECTS: Rotate Left when left is clicked, Rotate Right when Right is clicked
    //toggle Rotating when rotating is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == leftButton){
            controlledObject.addRotationOffset(new Rotator_3D(toRadians(-10.0),0,0));
            pw.repaint();
        }
        if(source == rightButton){
            controlledObject.addRotationOffset(new Rotator_3D(toRadians(10.0),0,0));
            pw.repaint();
        }
        if(source == rotateButton){
            ((Rotatable)controlledObject).setRotating(!((Rotatable)controlledObject).getRotating());
            if (!((Rotatable)controlledObject).getRotating()){
                rotateButton.setBackground(Color.green);
            }else{
                rotateButton.setBackground(Color.red);
            }

        }
        if(source == saveButton){
            String path = JOptionPane.showInputDialog("Enter a name for this save");
            if(path!=null) {
                save(path);
            }
        }
        if(source==loadButton){
            String path = JOptionPane.showInputDialog("Enter the name to load");
            if(path!=null) {
                load(path);
            }
        }
        repaint();
    }

    public void save(String path) {
        pw.getLevel().save(path + ".sav");
    }

    public void load(String path) {
        pw.getLevel().loadData(path + ".sav");
        pw.repaint();
        allObjectDropDown.removeAllItems();
        allObjectDropDown.addItems(pw.getLevel().getAllObj());
        changeControlledObject(0);
    }


    //EFFECT: get current PaintWindow.
    public PaintWindow getPw() {
        return pw;
    }

    //MODIFIES: this
    //EFFECTS: change controlled object to the index on the list,
    //if index is out of range, controlled object is null;
    public void changeControlledObject(int index) {
        try{
            this.controlledObject = pw.getLevel().getAllObj().get(index);
            allObjectDropDown.setSelectedIndex(index);
        }catch (Exception e){
            controlledObject = null;
        }
    }

    @Override
    protected void processKeyEvent(KeyEvent e) {
        switch (e.getKeyChar()){
            case 's': pw.getLevel().getSnake().setDirection(2);
                break;
            case 'a': pw.getLevel().getSnake().setDirection(1);
                break;
            case 'd': pw.getLevel().getSnake().setDirection(0);
                break;
            case 'w': pw.getLevel().getSnake().setDirection(3);
                break;

        }
        //System.out.println(e.getKeyChar());
//        super.processKeyEvent(e);
    }

    @Override
    public void dispose() {
        super.dispose();
        pw.dispose();
    }
}
