package com.t3h.model.enemy;

import images.ImageLoader;

import java.awt.*;

public class Goomba extends Enemy {
    public Goomba(int x, int y) {
        super(x, y);
        orient = LEFT;
        images.add(new Image[]{
                ImageLoader.getImage("ghost_left.png"),
                ImageLoader.getImage("ghost_right.png")
        });
        images.add(new Image[]{
                ImageLoader.getImage("ghost_left.png"),
                ImageLoader.getImage("ghost_right.png")
        });
        images.add(new Image[]{
           ImageLoader.getImage("ghost_die2.png")
        });
    }
}
