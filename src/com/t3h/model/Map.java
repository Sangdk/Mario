package com.t3h.model;

import images.ImageLoader;

import java.awt.*;

public class Map {
    private int x;
    private int y;
    private int bit;
    private int w = 34;
    private int h = 40;
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

    private boolean pushed = false;

    public void push(Mario mario) {
        if (checkPush(mario)) {
                y -= 4;
                pushed = true;
        }
    }

    public void fall() {
        if (pushed) {
            y += 4;
            pushed = false;
        }
    }

    public boolean checkPush(Mario mario) {
        if (bit == 3) {
            Rectangle rect = mario.getRectTop().intersection(getRectBot());
            if (!rect.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public int getBit() {
        return bit;
    }

    public void move(int orient) {
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
