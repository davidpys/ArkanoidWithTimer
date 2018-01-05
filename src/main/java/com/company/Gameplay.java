package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Dawid on 2017-12-29.
 */
public class Gameplay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;

    private int totalBricks = 21;

    private Timer timer;
    // delay -speed timer
    private int delay = 5;

    private int playerX = 310;

    private int ballposX = 360;
    private int ballposy = 530;
    private int ballXdir = -1;
    private int ballYdir = -2;

    private int varMoveeBall = 50;

    private MapGenerator mapGenerator;


    public Gameplay() {
        mapGenerator = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer (delay, this);
        timer.start();
    }

    public void paint (Graphics g){
        //backgound
        g.setColor(Color.BLACK);
        g.fillRect(1,1,692,592);

        // drawing mapGenerator
        mapGenerator.draw((Graphics2D)g);

        // borders -ramka
        g.setColor(Color.YELLOW);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);

        // scores
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString(""+score, 590, 30);

        // psddle
        g.setColor(Color.GREEN);
        g.fillRect(playerX, 550, 150, 10);

        // ball
        g.setColor(Color.WHITE);
        g.fillOval(ballposX, ballposy, 20, 20);

        if (totalBricks <=0 ){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.CYAN);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You are won, Scores: "+ score*10 ,200, 300);

            g.setFont(new Font("serif", Font.ITALIC, 20));
            g.drawString("Pres Enter to Restart", 230, 350);
        }

        if (ballposy > 570){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.CYAN);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over, Scores: "+ score ,200, 300);

            g.setFont(new Font("serif", Font.ITALIC, 20));
            g.drawString("Pres Enter to Restart", 230, 350);
        }


        g.dispose();
    }

//    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play){
            if (new Rectangle(ballposX, ballposy, 20, 20).intersects(new Rectangle(playerX, 550, 150, 10))){
                ballYdir = -ballYdir;
            }
            A : for (int i =0; i < mapGenerator.map.length; i++){
                for (int j = 0; j< mapGenerator.map[0].length; j++){
                    if (mapGenerator.map[i][j] > 0){
                        int brickX = j * mapGenerator.brickWidth + 80;
                        int brickY = i * mapGenerator.brickHeight + 50;
                        int brickWidth = mapGenerator.brickWidth;
                        int brickHeight = mapGenerator.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposy, 20,20);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect)){
                            mapGenerator.setBrickValue(0, i, j);
                            totalBricks --;
                            score +=10;

                            if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width){
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }



            ballposX += ballXdir;
            ballposy += ballYdir;

            if (ballposy < 0){
                ballYdir = -ballYdir;
            }
            if (ballposX < 0){
                ballXdir = -ballXdir;
            }
            if (ballposX > 670){
                ballXdir = -ballXdir;
            }
        }

        repaint();

    }

//    @Override
    public void keyTyped(KeyEvent e) {}

//    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if (playerX >= 550){
                playerX = 550;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT){
            if (playerX <= 5){
                playerX = 5;
            } else {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            if (!play){
                play = true;
                ballposX = 360;
                ballposy = 530;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21 ;
                mapGenerator = new MapGenerator(3,7);

                repaint();
            }
        }
    }

    private void moveLeft() {
        play = true;
        playerX -= varMoveeBall;
    }

    private void moveRight() {
        play = true;
        playerX += varMoveeBall;
    }

//    @Override
    public void keyReleased(KeyEvent e) {}
}
