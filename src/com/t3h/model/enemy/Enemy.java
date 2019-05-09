package com.t3h.model.enemy;

import com.t3h.model.Map;
import com.t3h.model.Mario;
import sounds.SoundManage;

import java.awt.*;
import java.util.ArrayList;

public class Enemy {
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int DIE = 2;

    public int x, y;
    public int orient;
    public int speed = 1;
    public int w = 40, h = 44;
    private int index = 0;
    private int count;
    public boolean checkMove = false;
    public boolean die = false;
    private long t;

    protected ArrayList<Image[]> images = new ArrayList<>();
    private SoundManage soundManage = new SoundManage();

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void changImage() {
        count++;
        if (count < 15) {
            return;
        }
        count = 0;
        index++;
        if (index >= images.get(orient).length) {
            index = 0;
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(images.get(orient)[index], x, y, w, h, null);
    }

    public void move(ArrayList<Map> arr, boolean checkMove) {
        int yR = y;
        int xR = x;
        int speed = this.speed;
        switch (orient) {
            case LEFT:
                if (checkMove == true) {
                    speed += 1;
                }
                x -= speed;
                break;
            case RIGHT:
                if (checkMove == true) {
                    speed = 0;
                }
                x += speed;
                break;
        }
        if (checkMap(arr, false) == false) {
            x = xR;
            y = yR;
            return;
        }

    }

    public void die() {
        orient = DIE;
        t = System.currentTimeMillis();
        die = true;
    }

    public long getT() {
        return t;
    }

    public boolean checkMap(ArrayList<Map> arrMap, boolean fromFall) {
        for (Map m : arrMap
        ) {
            if (m.getBit() == 8 || m.getBit() == 9 || m.getBit() == 5 || m.getBit() == 6 || m.getBit() == 7) continue;
            Rectangle rect = m.getRect().intersection(getRect());

            if (rect.isEmpty() == false) {
                if (!fromFall) {
                    if (this.orient == LEFT) {
                        this.orient = RIGHT;
                    } else if (this.orient == RIGHT) {
                        this.orient = LEFT;
                    }
                }
                return false;
            }
        }
        return true;
    }

    public Rectangle getRect() {
        Rectangle rect = new Rectangle(
                x, y,
                w, h
        );
        return rect;
    }

    public Rectangle getRectLeft() {
        Rectangle rect = new Rectangle(
                x - 2, y,
                2, h
        );
        return rect;
    }

    public Rectangle getRectRight() {
        Rectangle rect = new Rectangle(
                x + w, y,
                2, h
        );
        return rect;
    }

    public Rectangle getRectTop() {
        Rectangle rect = new Rectangle(
                x, y - 2,
                w, 2
        );
        return rect;
    }

    public boolean checkDie(Mario mario) {
        Rectangle top = mario.getRectBot().intersection(getRectTop());
        if (top.isEmpty() == false && !mario.die && !die) {
            mario.setScore(100);
            soundManage.play("smw_kick.wav");
            return true;
        }
        if (x < 0) return true;
        return false;
    }

    public void fall(ArrayList<Map> arr) {
        int xR = x;
        int yR = y;
        y += 5;
        boolean check = checkMap(arr, true);
        if (!check) {
            if (y > 481) return;
            x = xR;
            y = yR;
        }

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setOrient(int orient) {
        this.orient = orient;
    }
}
