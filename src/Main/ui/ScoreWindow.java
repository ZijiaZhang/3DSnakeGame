package ui;

import Players.Player;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ScoreWindow extends JFrame {
    private ArrayList<JLabel> names =  new ArrayList<>();
    private ArrayList<JLabel> scores= new ArrayList<>();
    private int width = 450;
    private int height = 800;

    public ScoreWindow(Map<String,Integer> scoreBoard){
        int h=0;
        setSize(width,height);
        setTitle("Scores Window");
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Map<String,Integer> p = sortByValue(scoreBoard);

        JLabel col1 = new JLabel("Player Name");
        JLabel col2 = new JLabel("Score");
        col1.setPreferredSize(new Dimension(200,50));
        col2.setPreferredSize(new Dimension(200,50));
        addComponents(col1,50,h);
        addComponents(col2,250,h);
        h+=50;

        for(Map.Entry<String,Integer> entry : p.entrySet()){
            JLabel name = new JLabel(entry.getKey());
            JLabel score = new JLabel(entry.getValue().toString());
            name.setPreferredSize(new Dimension(200,50));
            score.setPreferredSize(new Dimension(200,50));
            addComponents(name,50,h);
            addComponents(score,250,h);
            h+=50;
            names.add(name);
            scores.add(score);
        }
        setVisible(true);
    }



    private void addComponents(Component c , int x, int y){
        add(c);
        Insets insets = this.getInsets();
        Dimension size = c.getPreferredSize();
        c.setBounds(x + insets.left, y + insets.top,
                size.width, size.height);
    }


    private static Map<String, Integer> sortByValue(Map<String, Integer> unsortMap) {
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

}
