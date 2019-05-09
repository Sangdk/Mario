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

    public MyPanel() {
//        setBackground(Color.blue);
        Background = ImageLoader.getImage("Background1.jpg");
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
        manager.draw(g2d);
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
//            if (!check){
//                int result = JOptionPane.showConfirmDialog(
//                        null,
//                        "Do you want to replay?",
//                        "Game over",
//                        JOptionPane.YES_NO_OPTION,
//                        JOptionPane.QUESTION_MESSAGE
//                );
//                if (result == JOptionPane.YES_OPTION){
//                    manager.initGame();
//                    b = new BitSet();
//                }else{
//                    System.exit(0);
//                    return;
//                }
//            }
            repaint();
            try {
                Thread.sleep(6);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
