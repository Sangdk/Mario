package com.t3h.model.enemy;


import images.ImageLoader;

import java.awt.*;

public class Koopa extends Enemy {
    public Koopa(int x, int y) {
        super(x, y);
        orient = LEFT;
        images.add(new Image[]{
                ImageLoader.getImage("tortoise1.png"),
                ImageLoader.getImage("tortoise2.png")
        });
        images.add(new Image[]{
                ImageLoader.getImage("tortoise1.png"),
                ImageLoader.getImage("tortoise2.png")
        });
        images.add(new Image[]{
                ImageLoader.getImage("tortoise_die.png")
        });
    }
}
