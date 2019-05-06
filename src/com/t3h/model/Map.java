package com.t3h.model;

import images.ImageLoader;

import java.awt.*;

public class Map {
    private int x;
    private int y;
    private int bit;
    private int w = 34;
    private int h = 40;
    public boolean pushed = false;
    private Image[] img = {
            ImageLoader.getImage("floor3.png"),
            ImageLoader.getImage("question_box.png"),
            ImageLoader.getImage("brick.png"),
            ImageLoader.getImage("pipe.png"),
            ImageLoader.getImage("cloud.png"),
            ImageLoader.getImage("cloud1.png"),
            ImageLoader.getImage("cloud2.png"),
            ImageLoader.getImage("moutain1.png"),
            ImageLoader.getImage("moutain2.png"),
            ImageLoader.getImage("Pipe2.png"),
            ImageLoader.getImage("stair1.png"),
    };

    public Map(int x, int y, int bit) {
        this.x = x;
        this.y = y;
        this.bit = bit;
    }

    public void draw(Graphics2D g2d) {
        if (bit == 8 || bit == 9) {
            g2d.drawImage(img[bit - 1], x, y, 130, 130, null);
        } else {
            g2d.drawImage(img[bit - 1], x, y, 34, 40, null);
        }
    }

    public Rectangle getRect() {
//        if (bit == 1) {
//            w = img[bit - 1].getWidth(null);
//            h = img[bit - 1].getHeight(null);
//        }
        Rectangle rect = new Rectangle(
                x, y, w, h
        );
        return rect;
    }

    public Rectangle getRectBot() {
        Rectangle bot = new Rectangle(
                x, y + h,
                w, 2
        );
        return bot;
    }

    int push = 20;

    public void push(Mario mario) {
        if (impactBrick == true) {
            if (push == 0) {
                pushed = true;
                return;
            }
            if (push > 0) {
                y -= 2;
                push -= 2;
            }
        }
    }

    public void fall() {
        if (pushed) {
            if (push == 20) {
                pushed = false;
                impactBrick = false;
                return;
            }
            y += 2;
            push += 2;
        }
    }
    private boolean impactBrick = false;
    public boolean checkPush(Mario mario) {
        if (bit == 3) {
            Rectangle rect = mario.getRectTop().intersection(getRectBot());
            if (!rect.isEmpty()) {
                impactBrick = true;
                return true;
            }
        }
        return false;
    }

    public int getBit() {
        return bit;
    }

    public void move(int orient) {
        int xR = x;
        int yR = y;
        switch (orient) {
            case Mario.MOVE_LEFT:
                break;
            case Mario.MOVE_RIGHT:
                x -= 1;
                break;
        }
    }

    public int getX() {
        return x;
    }
}
