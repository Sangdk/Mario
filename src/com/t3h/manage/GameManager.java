package com.t3h.manage;

import com.t3h.gui.MyFrame;
import com.t3h.model.Actor;
import com.t3h.model.enemy.Enemy;
import com.t3h.model.Map;
import com.t3h.model.Mario;
import com.t3h.model.enemy.Goomba;
import sounds.MusicManage;
import sounds.SoundManage;

import java.awt.*;
import java.util.ArrayList;

public class GameManager {
    private Mario mario;
    private ArrayList<Map> arrMap;
    private ArrayList<Enemy> arrGoomba;
    private MapManager mapManager = new MapManager();
    private SoundManage soundManage = new SoundManage();
    private MusicManage musicManage = new MusicManage();

    public void initGame() {
        MusicManage.play("world_1-1.mid");
        mario = new Mario(300, 380);

        arrGoomba = new ArrayList<>();

        initEnemy();
        arrMap = mapManager.readMap("map.txt");

    }

    public void initEnemy() {
        checkCreate(700, 380);
        checkCreate(1000, 380);
        checkCreate(1200, 380);
    }

    protected void checkCreate(int x, int y) {
        Goomba g = new Goomba(x, y);
            arrGoomba.add(g);
            System.out.println("Goomba have been create");
    }


    public void draw(Graphics2D g2d) {
        for (Map m : arrMap
        ) {
            m.draw(g2d);
        }
        if (arrGoomba != null) {
            for (Enemy g : arrGoomba
            ) {
                g.draw(g2d);
            }
        }
        mario.draw(g2d);
    }

    private boolean checkMapMove = false;

    public void setCheckMapMove(boolean checkMapMove) {
        this.checkMapMove = checkMapMove;
    }

    private boolean checkMarioMove = false;

    public void setMarioMove(boolean checkMarioMove) {
        this.checkMarioMove = checkMarioMove;
    }

    public void marioMove(int orient) {
        mario.setOrient(orient);
        mario.changImage();
        checkMarioMove = true;
        if (orient == Actor.MOVE_LEFT) {
            mario.move(arrMap);
            checkMapMove = false;
            return;
        }
        if (orient == Actor.MOVE_RIGHT && arrMap.get(arrMap.size() - 1).getX() <= MyFrame.W_Frame) {
            mario.move(arrMap);
            checkMapMove = false;
            return;
        }
        if (mario.getX() != MyFrame.W_Frame / 2.5) {
            mario.move(arrMap);
            checkMapMove = false;
            return;
        }
        //
        if (!mario.checkMap(arrMap)) return;

        for (Map m : arrMap
        ) {
            m.move(orient);
        }
        checkMapMove = true;
    }

    public void marioJump() {
        mario.jump();
    }

    public boolean ai() {
        if (checkMarioMove == false) {
            mario.setIndex(0);
        }

        if (mario.checkDie(arrGoomba) == true) {
            mario.setOrient(Actor.DIE);
        }

        for (int i = 0; i < arrMap.size(); i++) {
                arrMap.get(i).push(mario);
                arrMap.get(i).fall();
        }
        for (int i = arrGoomba.size() - 1; i >= 0; i--) {
            arrGoomba.get(i).changImage();
            arrGoomba.get(i).fall(arrMap);
            boolean check = arrGoomba.get(i).checkMove;
            if (checkMapMove && !check){
                int x =arrGoomba.get(i).getX();
                arrGoomba.get(i).setX(x-1);
            }
            if (arrGoomba.get(i).getX() - mario.getX() <= MyFrame.W_Frame /1.5 || arrGoomba.get(i).checkMove == true) {
                arrGoomba.get(i).move(arrMap, checkMapMove);
                arrGoomba.get(i).checkMove = true;
            }

            boolean checkDie = arrGoomba.get(i).checkDie(mario);
            if (checkDie) {
                arrGoomba.remove(i);
                System.out.println("Goomba have been destroy");
            }

        }
        mario.fall(arrMap);
        if (mario.checkDie(arrGoomba)) return false;
        return true;
    }
}
