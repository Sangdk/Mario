package com.t3h.model;


import images.ImageLoader;

import java.awt.*;

public class Mario extends Actor {
    public Mario(int x, int y) {
        super(x, y);
        orient = STAND_RIGHT;
        images.add(new Image[]{
                ImageLoader.getImage("smallmario_stand_left.png"),
        });
        images.add(new Image[]{
                ImageLoader.getImage("smallmario_stand_right.png"),
        });
        images.add(new Image[]{
                ImageLoader.getImage("smallmario_jump_left.png"),
        });
        images.add(new Image[]{
                ImageLoader.getImage("smallmario_jump_right.png"),
        });
        images.add(new Image[]{
                ImageLoader.getImage("smallmario_stand_left.png"),
                ImageLoader.getImage("smallmario_move_left_1.png"),
                ImageLoader.getImage("smallmario_move_left_2.png"),
                ImageLoader.getImage("smallmario_move_left_3.png"),
        });
        images.add(new Image[]{
                ImageLoader.getImage("smallmario_stand_right.png"),
                ImageLoader.getImage("smallmario_move_right_1.png"),
                ImageLoader.getImage("smallmario_move_right_2.png"),
                ImageLoader.getImage("smallmario_move_right_3.png"),
        });
        images.add(new Image[]{
                ImageLoader.getImage("smallmario_die.png")
        });
    }

    @Override
    public void draw(Graphics2D g2d) {
        super.draw(g2d);
    }

}
