package Brick;

import javax.swing.JFrame;

public class MyJava {
    public static void main(String[] args) {
        JFrame obj = new JFrame();
        Gameplay gameplay = new Gameplay();
        obj.setBounds(1,1,600, 800);
        obj.setTitle("BRICK BREAKER ULTIMATE");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gameplay);
    }
}
