package com.t3h.model.items;

import images.ImageLoader;

import java.awt.*;

public class Coin {
    private int x;
    private int y;
    private int h = 10;
    private Image image = ImageLoader.getImage("coin.png");

    public Coin(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(image, x, y, 10, 10, null);
    }

    public boolean move() {
        y --;
        h--;
        if (h==0){
            return false;
        }
        return true;
    }
}
