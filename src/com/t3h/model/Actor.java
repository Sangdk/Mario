package com.t3h.model;


import com.t3h.gui.MyFrame;
import com.t3h.model.enemy.Enemy;
import sounds.MusicManage;
import sounds.SoundManage;

import java.awt.*;
import java.util.ArrayList;

public class Actor {
    public static final int STAND_LEFT = 0;
    public static final int STAND_RIGHT = 1;
    public static final int JUMP_LEFT = 2;
    public static final int JUMP_RIGHT = 3;
    public static final int MOVE_LEFT = 4;
    public static final int MOVE_RIGHT = 5;
    public static final int DIE = 6;
    private int index = 0;
    private int count = 0;
    private int jump = 0;
    private int w = 40;
    private int h = 44;
    private int jumpCount = 0;
    public boolean die = false;
    public int score = 0;
    public int coins = 0;

    protected int x, y;
    protected int orient;
    protected int speed = 1;
    protected ArrayList<Image[]> images = new ArrayList<>();

    public Actor(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(images.get(orient)[index], x, y, w, h, null);
    }

    public void changImage() {
        count++;
        if (count < 8) {
            return;
        }
        count = 0;
        index++;
        if (index >= images.get(orient).length) {
            index = 0;
        }
    }

    public void move(ArrayList<Map> arrMap) {
        if (!die) {
            int xR = x;
            int yR = y;
            switch (orient) {
                case MOVE_LEFT:
                case JUMP_LEFT:
                    x -= speed;
                    break;
                case MOVE_RIGHT:
                case JUMP_RIGHT:
                    x += speed;
                    break;
            }
            if (checkMap(arrMap) == false && !die) {
                x = xR;
                y = yR;
                return;
            }

            if (x <= 0 || x >= MyFrame.W_Frame - images.get(orient)[index].getWidth(null) + 10) {
                x = xR;
            }
            if (y <= 0 || y >= MyFrame.W_Frame - images.get(orient)[index].getHeight(null) - 40) {
                y = yR;
            }
        }
    }

    public void jump() {
        jumpCount++;
        if (jump > 0) return;
        if (jumpCount >= 2) {
            jumpCount = 0;
            return;
        }
        if (jump == 0) {
            if (!die) {
                onFloor = false;
                SoundManage.play("smw_jump.wav");
            }
            jump = 120;
        }
    }

    public boolean onFloor = false;

    public void fall(ArrayList<Map> arrMap) {
        int yR = y;
        int xR = x;
        //change y when jump
        if (jump > 0) {
            y -= 3;
            jump -= 2;
        } else {
            //change y when fall
            jump -= 2;
            y += 3;
        }
        boolean check = checkMap(arrMap);
        if (check == false && !die) {
            if (jump <= 0) {
                onFloor = true;
            }
            if (y > 480) return;
            y = yR;
            x = xR;
            jump = 0;
        }
    }

    public void die() {
        die = true;
        jump();
    }

    public boolean checkMap(ArrayList<Map> arrMap) {
        for (Map m : arrMap
        ) {
            if (m.getBit() == 8 || m.getBit() == 9 || m.getBit() == 5 || m.getBit() == 6
                    || m.getBit() == 7 || m.getBit() == 12 || m.getBit() == 13) continue;
            Rectangle rect = m.getRect().intersection(getRect());
            if (rect.isEmpty() == false) {
                return false;
            }
        }
        return true;
    }

    public boolean checkDie(ArrayList<Enemy> arrE) {
        for (int i = 0; i < arrE.size(); i++) {
            Rectangle right = arrE.get(i).getRectLeft().intersection(getRectRight());
            Rectangle left = arrE.get(i).getRectRight().intersection(getRectLeft());
            if (!right.isEmpty() || !left.isEmpty() && !die && !arrE.get(i).die) {
                MusicManage.stop();
                SoundManage.play("smb_mariodie.wav");
                return true;
            }
        }
        if (this.y > 490 && !die) {
            SoundManage.play("smb_mariodie.wav");
            return true;
        }
        return false;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setOrient(int orient) {
        if (orient == MOVE_LEFT && jump != 0) {
            orient = JUMP_LEFT;
        } else if (orient == MOVE_RIGHT && jump != 0) {
            orient = JUMP_RIGHT;
        }
        if (orient != this.orient) {
            index = 0;
        }
        this.orient = orient;
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
                x - 1, y,
                1, h
        );
        return rect;
    }

    public Rectangle getRectRight() {
        Rectangle rect = new Rectangle(
                x + w, y,
                1, h
        );
        return rect;
    }

    public Rectangle getRectTop() {
        Rectangle rect = new Rectangle(
                x + 15, y - 2,
                w - 30, 2
        );
        return rect;
    }

    public Rectangle getRectBot() {
        Rectangle rect = new Rectangle(
                x, y + h,
                w, 1
        );
        return rect;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getOrient() {
        return orient;
    }

    public int getScore() {
        return score;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins += coins;
    }

    public void setScore(int score) {
        this.score += score;
    }
}
