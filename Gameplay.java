package Brick;

import org.w3c.dom.css.Rect;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;
    private int level = 1;
    private int win = 0;
    private int totalBricks = 38;

    private Timer timer;
    private int delay = 8;

    private int playerX = 250;

    private int ballposX = 80;
    private int ballposY = 350;
    private int ballXdir = -2;
    private int ballYdir = -4;

    private MapGenerator map;

    public Gameplay() {
        map = new MapGenerator(4, 10);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g){
        //background
        g.setColor(Color.black);
        g.fillRect(1,1,600,800);

        //bricks
        map.draw((Graphics2D)g);

        //borders
        g.setColor(Color.yellow);
        g.fillRect(0,0,3,800);
        g.fillRect(0,0,600,3);
        g.fillRect(583,0,3,800);

        //scores
        g.setColor(Color.green);
        g.setFont(new Font("Serif", Font.BOLD, 30));
        g.drawString(""+score, 10, 30);

        //paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 700, 100, 15);

        if(ballposY > 750){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("Dialog", Font.ROMAN_BASELINE, 60 ));
            g.drawString("GAME OVER", 110, 400);
            g.setFont(new Font("Dialog", Font.ROMAN_BASELINE, 30 ));
            g.drawString("Press Enter to Restart", 140, 450);
        }

        if(totalBricks == 0){
            if(win==0) {
                play = false;
                ballXdir = 0;
                ballYdir = 0;
                g.setColor(Color.PINK);
                g.setFont(new Font("Dialog", Font.ROMAN_BASELINE, 60));
                g.drawString("CHICKEN DINNER!", 35, 400);
                g.setFont(new Font("Dialog", Font.ROMAN_BASELINE, 30 ));
                g.drawString("Press Enter to Continue", 130, 450);
                //win = 1;
            }
        }

        //ball
        g.setColor(Color.blue);
        g.fillOval(ballposX, ballposY, 40, 40);

        g.dispose();

    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        timer.start();
        if(play){
            if(new Rectangle(ballposX, ballposY, 40, 40).intersects(new Rectangle(playerX, 700, 100, 15))) {
                ballYdir = -ballYdir;
            }

            A: for(int i = 0; i < map.map.length; i++) {
                for (int j=0; j < map.map[0].length; j++){
                    if(map.map[i][j] > 0){
                        int brickX = j * map.BrickWidth + 100;
                        int brickY = i * map.BrickHeight + 75;
                        int BrickWidth = map.BrickWidth;
                        int BrickHeight = map.BrickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, BrickWidth, BrickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 40, 40);
                        Rectangle brickRect = rect;

                        if(ballRect.intersects(brickRect)) {
                            map.SetBrickValue(i,j);
                            totalBricks--;
                            score += 5;

                            if(ballposX + 38 <= brickRect.x || ballposX + 2 >= brickRect.x + brickRect.width){
                                ballXdir = -ballXdir;
                            }else
                                ballYdir = -ballYdir;

                            break A;
                        }
                    }
                }
            }



            ballposX += ballXdir;
            ballposY += ballYdir;
            if(ballposX < 0){
                ballXdir = -ballXdir;
            }
            if(ballposX > 550){
                ballXdir = -ballXdir;
            }
            if(ballposY < 0) {
                ballYdir = -ballYdir;
            }

        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) { }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 500) {
                playerX = 500;
            } else
                moveRight();
        }
        if(keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
            if(playerX < 10){
                playerX = 10;
            }else
                moveLeft();
        }
        if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER){
            if(win==0) {
                play = true;
                ballposX = 120;
                ballposY = 650;
                ballXdir = -2;
                ballYdir = -4;
                playerX = 250;
                score = 0;
                totalBricks = 38;
                map = new MapGenerator(4, 10);

                repaint();
            }else{
                play = true;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -3;
                ballYdir = -6;
                playerX = 500;
                totalBricks = 82;
                map = new MapGenerator(6, 14);
            }
        }
        if(keyEvent.getKeyCode() == keyEvent.VK_SPACE){
            play = true;
            ballposX = 120;
            ballposY = 350;
            ballXdir = -4;
            ballYdir = -8;
            playerX = 500;
            totalBricks = 20;
            map = new MapGenerator(6, 14);
        }
    }

    public void moveRight() {
        play = true;
        playerX+=40;
    }
    public void moveLeft() {
        play = true;
        playerX-=40;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) { }
}
