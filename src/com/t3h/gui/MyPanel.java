package com.t3h.gui;

import com.t3h.manage.GameManager;
import com.t3h.model.Mario;
import images.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.BitSet;

public class MyPanel extends JPanel implements KeyListener, Runnable {
    private GameManager manager = new GameManager();
    private BitSet b = new BitSet(256);
    private Image Background;
    private Image GameOver;
    private boolean marioDie;

    public MyPanel() {
        Background = ImageLoader.getImage("Background1.jpg");
        GameOver = ImageLoader.getImage("screen_model_newr.png");
        manager.initGame();
        setFocusable(true);
        addKeyListener(this);

        Thread t = new Thread(this);
        t.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.drawImage(Background, 0, 0, 750, 550, null);

        g2d.setFont(new Font("Tahoma",Font.BOLD,20));
        g2d.drawString("SCORE",20,20);
        g2d.drawString(manager.score(),30,40);
        g2d.drawString("COINS",120,20);
        g2d.drawString(manager.coins(),130,40);
        g2d.drawString("WORLD",220,20);
        g2d.drawString("1-1",230,40);
        manager.draw(g2d);
        if (marioDie){
            g2d.drawImage(GameOver,0,0,750,550,null);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        b.set(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        b.clear(e.getKeyCode());
    }

    @Override
    public void run() {
        while (true) {
            if (b.get(KeyEvent.VK_LEFT) == true) {
                manager.marioMove(Mario.MOVE_LEFT);
            } else if (b.get(KeyEvent.VK_RIGHT) == true) {
                manager.marioMove(Mario.MOVE_RIGHT);
            } else {
                manager.setCheckMapMove(false);
                manager.setMarioMove(false);
            }
            if (b.get(KeyEvent.VK_UP) == true) {
                manager.marioJump();
            }
            boolean check = manager.ai();
            if (!check){
                marioDie = true;
            }
            repaint();
            try {
                Thread.sleep(6);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
