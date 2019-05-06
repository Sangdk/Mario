package com.t3h.model.brick;

import java.awt.*;

public class MyBrick {
    private int w =34;
    private int h =40;
    private int x,y;
    private Image[] images = new Image[1];

    public MyBrick(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D g2d){
        g2d.drawImage(images[1],x,y,w,h,null);
    }

    public void push(){
    }
    Rectangle getRect(){
        Rectangle rect = new Rectangle(
                x,y,
                w,h
        );
        return rect;
    }
    Rectangle getRectBot(){
        Rectangle rect = new Rectangle(
                x,y+h,
                w,2
        );
        return rect;
    }

}
