package ui;

import Players.PlayerManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainMenu extends JFrame implements ActionListener {
    private static MainMenu m;
    private GeneralButton logout;
    private GeneralButton start;
    private GeneralButton score;
    private GeneralButton deleteAccount;
    private JLabel nameLbl;
    private int width = 450;
    private int height = 600;
    private ScoreWindow sc;
    private PlayerManager playerManager;
    private PaintWindow pw;
    private LoginWindow lw;
    private int Y_loc = 50;

    public MainMenu(){
        m = this;
        playerManager = GameInstance.getInstance().getPlayerManager();
        setSize(width,height);
        setTitle("Main Menu");
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        logout = new GeneralButton("logout",Color.WHITE,350,50,"logout",this);
        start = new GeneralButton("Start Game",Color.GREEN,350,50,"start",this);
        score = new GeneralButton("Show ScoreBoard",Color.GREEN,350,50,"score",this);
        deleteAccount = new GeneralButton("Delete Account",Color.RED,350,50,"del",this);
        nameLbl= new JLabel("Not logged in");
        nameLbl.setPreferredSize(new Dimension(350,50));
        createComponents();
        lw = new LoginWindow();
        setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source==logout){
            playerManager.logout();
            this.setVisible(false);
            lw = new LoginWindow();
        }
        if(source==start){
            pw = new PaintWindow();
            pw.resetScore();
            this.setVisible(false);
        }

        if(source==score){
            sc = new ScoreWindow(playerManager.getScoreBoard());
        }
        if(source == deleteAccount){
            DeleteAccount();
        }
        repaint();
    }

    private void DeleteAccount() {
        if(JOptionPane.showConfirmDialog(this,"Delete Account?","Confirm",JOptionPane.YES_NO_OPTION)==0) {
            try {
                playerManager.deleteAccount();
                this.setVisible(false);
                lw = new LoginWindow();
            }catch (IOException e){
                JOptionPane.showMessageDialog(this,"Cannot connect to Server");
            }
        }
    }

    //EFFECTS:ADD Component to Window
    private void addComponents(Component c , int x, int y){
        add(c);
        Insets insets = this.getInsets();
        Dimension size = c.getPreferredSize();
        c.setBounds(x + insets.left, y + insets.top,
                size.width, size.height);
    }

    private void addVerticalComponents(Component c){
        addComponents(c, 50 ,Y_loc);
        Y_loc+=70;
    }

    public void gameEnd() {
        JOptionPane.showMessageDialog(this,"You are died");
        setVisible(true);
        int curScore = pw.getScore();
        pw.dispose();
        if(playerManager.getCurrentLoginPlayer()!=null) {
            //int curScore = GameInstance.getInstance().getScore();
            if (playerManager.getScoreBoard().containsKey(playerManager.getCurrentLoginPlayer().getName())) {
                int preScore = playerManager.getScoreBoard().get(playerManager.getCurrentLoginPlayer().getName());
                if (curScore > preScore) {
                    try {
                        playerManager.updateScore(playerManager.getCurrentLoginPlayer(), curScore);
                    }catch (IOException e){
                        JOptionPane.showMessageDialog(this,"Cannot connect to Server, Score Unsaved");
                    }
                }
            }else{
                try {
                    playerManager.updateScore(playerManager.getCurrentLoginPlayer(), curScore);
                }catch (IOException e){
                    JOptionPane.showMessageDialog(this,"Cannot connect to Server, Score Unsaved");
                }
            }
            playerManager.save();
        }
    }

    public static MainMenu getInstance(){
        if(m==null)
            m = new MainMenu();
        return m;
    }

    public void loggedIn() {
        this.setVisible(true);
        Y_loc = 50;
        createComponents();
        this.nameLbl.setText("Hello," + playerManager.getCurrentLoginPlayer().getName());
    }

    public void loggedInOffline(){
        this.setVisible(true);
        this.nameLbl.setText("Playing Offline");
        Y_loc = 50;
        playerManager.resetPlayer();
        playerManager.resetScoreBoard();
        createComponents();
    }

    public void createComponents(){
        remove();
        addVerticalComponents(nameLbl);
        addVerticalComponents(logout);
        addVerticalComponents(start);
        addVerticalComponents(score);
        if(playerManager.getCurrentLoginPlayer()!=null)
            addVerticalComponents(deleteAccount);
        update(this.getGraphics());
    }
    public void remove(){
        remove(nameLbl);
        remove(logout);
        remove(start);
        remove(score);
        remove(deleteAccount);
    }
}
