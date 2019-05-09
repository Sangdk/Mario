package com.t3h.model;

import com.t3h.model.items.Coin;
import images.ImageLoader;
import sounds.SoundManage;

import java.awt.*;
import java.util.ArrayList;

public class Map {
    private int x;
    private int y;
    private int bit;
    private int w = 34;
    private int h = 40;
    public boolean pushed = false;
    int push = 20;
    private boolean impactBrick = false;
    private Coin coin;
    private int index = 0;

    private ArrayList<Image[]> img = new ArrayList<>();

    public Map(int x, int y, int bit) {
        this.x = x;
        this.y = y;
        this.bit = bit;
        img.add(new Image[]{
                ImageLoader.getImage("floor3.png")
        });
        img.add(new Image[]{
                ImageLoader.getImage("question_box.png"),
                ImageLoader.getImage("null_box.png")
        });
        img.add(new Image[]{
                ImageLoader.getImage("brick.png")
        });
        img.add(new Image[]{
                ImageLoader.getImage("pipe.png")
        });
        img.add(new Image[]{
                ImageLoader.getImage("cloud.png")
        });
        img.add(new Image[]{
                ImageLoader.getImage("cloud1.png")
        });
        img.add(new Image[]{
                ImageLoader.getImage("cloud2.png")
        });
        img.add(new Image[]{
                ImageLoader.getImage("mountain1.png")
        });
        img.add(new Image[]{
                ImageLoader.getImage("mountain2.png")
        });
        img.add(new Image[]{
                ImageLoader.getImage("Pipe2.png")
        });
        img.add(new Image[]{
                ImageLoader.getImage("stair1.png")
        });
        img.add(new Image[]{
                ImageLoader.getImage("mario_castle.png")
        });

    }

    public void draw(Graphics2D g2d) {
        if (bit == 8 || bit == 9) {
            g2d.drawImage(img.get(bit - 1)[index], x, y, 130, 130, null);
        } else if (bit == 12) {
            g2d.drawImage(img.get(bit - 1)[index], x, y - 90, 130, 130, null);
        } else if (bit == 4) {
            g2d.drawImage(img.get(bit - 1)[index], x, y - 50, 60, 100, null);
        } else if (bit == 10) {
            g2d.drawImage(img.get(bit - 1)[index], x, y - 100, 60, 150, null);
        } else {
            g2d.drawImage(img.get(bit - 1)[index], x, y, 34, 40, null);
        }
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

    public void push(ArrayList<Coin> arrCoin) {
        if (impactBrick == true && !colected) {
            if (push == 0) {
                if (bit == 2) {
                    coinUp(x + 7, y, arrCoin);
                    colected = true;
                    SoundManage.play("smw_coin.wav");
                } else {
                    SoundManage.play("smb_bump.wav");
                }
                pushed = true;
                return;
            }
            if (push > 0) {
                y -= 2;
                push -= 2;
            }
        }
    }

    boolean colected = false;

    public void coinUp(int x, int y, ArrayList<Coin> arrCoin) {
        coin = new Coin(x, y);
        arrCoin.add(coin);
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

    public Rectangle getRect() {
        if (bit == 4) {
            Rectangle rect = new Rectangle(
                    x, y - 50, 60, 90
            );
            return rect;
        }
        if (bit == 10) {
            Rectangle rect = new Rectangle(
                    x, y - 100, 60, 140
            );
            return rect;
        }
        if (bit == 12) {
            Rectangle rect = new Rectangle(
                    x, y - 90, 130, 130
            );
            return rect;
        }
        Rectangle rect = new Rectangle(
                x, y, w, h
        );
        return rect;
    }

    public Rectangle getRectBot() {
        Rectangle bot = new Rectangle(
                x + 7, y + h,
                w - 14, 2
        );
        return bot;
    }

    public boolean checkPush(Mario mario) {
        if (bit == 3 || bit == 2) {
            Rectangle rect = mario.getRectTop().intersection(getRectBot());
            if (!rect.isEmpty() && !mario.die) {
                impactBrick = true;
                return true;
            }
        }
        return false;
    }

    public int getBit() {
        return bit;
    }

    public int getX() {
        return x;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setX(int x) {
        this.x = x;
    }
}
