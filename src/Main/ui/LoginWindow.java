package ui;

import Exceptions.NameLengthExcption;
import Exceptions.PLayerExistException;
import Exceptions.PasswordException;
import Players.PlayerManager;
import jdk.nashorn.internal.scripts.JO;
import sun.applet.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;

public class LoginWindow extends JFrame implements ActionListener {
    private GeneralButton createPlayer;
    private GeneralButton login;
    private GeneralButton loginoffline;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel nameLbl;
    private int width = 450;
    private int height = 600;
    private PlayerManager playerManager;
    private GameInstance g;


    public LoginWindow(){
        this.g = GameInstance.getInstance();
        playerManager = g.getPlayerManager();
        setSize(width,height);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createPlayer = new GeneralButton("Create Account",Color.WHITE,350,50,"create",this);
        login = new GeneralButton("login",Color.WHITE,350,50,"login",this);
        loginoffline = new GeneralButton("Play offline",Color.WHITE,350,50,"offline",this);
        usernameField = new JTextField(27);
        usernameField.setPreferredSize(new Dimension(350,50));
        passwordField = new JPasswordField(27);
        passwordField.setPreferredSize(new Dimension(350,50));
        nameLbl= new JLabel("Not logged in");
        nameLbl.setPreferredSize(new Dimension(350,50));
        addComponents(nameLbl,50,50);
        addComponents(usernameField,50,120);
        addComponents(passwordField,50,190);
        addComponents(createPlayer,50,260);
        addComponents(login,50,330);
        addComponents(loginoffline,50,400);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == createPlayer){
            CreatePlayer();
        }
        if (source == login){
            Login();
        }
        if (source== loginoffline){
            MainMenu.getInstance().loggedInOffline();
            this.dispose();
        }
        repaint();
    }

    private void Login() {
        try {
            playerManager.login(usernameField.getText(), new String(passwordField.getPassword()));
        }catch (PasswordException exception){
            JOptionPane.showMessageDialog(this,"Wrong username Or password");
        }catch (IOException e){
            JOptionPane.showMessageDialog(this,"Cannot Connect To Server");
        }
        if(playerManager.getCurrentLoginPlayer()!=null) {
            nameLbl.setText(playerManager.getCurrentLoginPlayer().getName());
            MainMenu.getInstance().loggedIn();
            this.dispose();
        }
        else
            nameLbl.setText("Not logged in");
    }

    private void CreatePlayer() {
        try {
            playerManager.createPlayer(usernameField.getText(), new String(passwordField.getPassword()));
            JOptionPane.showMessageDialog(this,"Account Created");
        }catch (NameLengthExcption exception){
            JOptionPane.showMessageDialog(this,"Invalid username length\n Username should be longer than 6 characters and don't contains '{' or '}' ");
        }catch (PasswordException exception){
            JOptionPane.showMessageDialog(this,"Invalid Password length\n Password should be longer than 6 characters");
        }catch (PLayerExistException exception){
            JOptionPane.showMessageDialog(this,"Player Already Exist");
        }catch (IOException exception){
            JOptionPane.showMessageDialog(this,"Cannot connect to Server");
        }
        if(playerManager.getCurrentLoginPlayer()!=null)
            nameLbl.setText(playerManager.getCurrentLoginPlayer().getName());
        else
            nameLbl.setText("Not logged in");
    }

    //EFFECTS:ADD Component to Window
    private void addComponents(Component c , int x, int y){
        add(c);
        Insets insets = this.getInsets();
        Dimension size = c.getPreferredSize();
        c.setBounds(x + insets.left, y + insets.top,
                size.width, size.height);
    }

}
