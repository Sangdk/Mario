package com.t3h.manage;

import com.t3h.gui.MyFrame;
import com.t3h.model.Actor;
import com.t3h.model.enemy.Enemy;
import com.t3h.model.Map;
import com.t3h.model.Mario;
import com.t3h.model.enemy.Goomba;
import com.t3h.model.items.Coin;
import sounds.MusicManage;

import java.awt.*;
import java.util.ArrayList;

public class GameManager {
    private Mario mario;
    private ArrayList<Map> arrMap;
    private ArrayList<Enemy> arrGoomba;
    private MapManager mapManager = new MapManager();
    private ArrayList<Coin> arrCoin;

    public void initGame() {
        mario = new Mario(300, 380);

        arrGoomba = new ArrayList<>();

        arrCoin = new ArrayList<>();

        initEnemy();
        arrMap = mapManager.readMap("map.txt");
        MusicManage.play("world_1-1.mid");
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
        if (arrCoin != null) {
            for (Coin c : arrCoin
            ) {
                c.draw(g2d);
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

    boolean marioLeft = false;
    boolean marioRight = false;

    public void marioMove(int orient) {
        mario.setOrient(orient);
        mario.changImage();
        checkMarioMove = true;
        if (orient == Actor.MOVE_LEFT) {
            mario.move(arrMap);

            marioLeft = true;
            marioRight = false;

            checkMapMove = false;
            return;
        }
        if (orient == Actor.MOVE_RIGHT && arrMap.get(arrMap.size() - 1).getX() <= MyFrame.W_Frame) {
            mario.move(arrMap);

            marioRight = true;
            marioLeft = false;

            checkMapMove = false;
            return;
        }
        if (mario.getX() != MyFrame.W_Frame / 2.5) {
            mario.move(arrMap);
            if (orient == Actor.MOVE_LEFT) {
                marioLeft = true;
                marioRight = false;
            }
            if (orient == Actor.MOVE_RIGHT) {
                marioRight = true;
                marioLeft = false;
            }
            checkMapMove = false;
            return;
        }
        //
        if (!mario.checkMap(arrMap)
        ) {
            checkMapMove = false;
            return;
        }
        for (Map m : arrMap
        ) {
            m.move(orient);

            marioRight = true;
            marioLeft = false;
        }
        checkMapMove = true;
    }

    public void marioJump() {
        setMarioMove(false);
        if (marioLeft) {
            mario.setOrient(Actor.JUMP_LEFT);
        } else if (marioRight) {
            mario.setOrient(Actor.JUMP_RIGHT);
        }
        mario.jump();
    }

    public boolean ai() {
        long T =0;
        if (checkMarioMove == false) {
            mario.setIndex(0);
        }

        if (mario.checkDie(arrGoomba) == true) {
            mario.setOrient(Actor.DIE);
        }
        //Push box
        for (int i = 0; i < arrMap.size(); i++) {
            arrMap.get(i).checkPush(mario);

            if (arrMap.get(i).pushed == false) {
                arrMap.get(i).push(arrCoin);
            } else {
                arrMap.get(i).fall();
            }
        }
        //Enemy handle
        for (int i = arrGoomba.size() - 1; i >= 0; i--) {
            arrGoomba.get(i).changImage();
            arrGoomba.get(i).fall(arrMap);
            boolean check = arrGoomba.get(i).checkMove;
            if (checkMapMove && !check) {
                int x = arrGoomba.get(i).getX();
                arrGoomba.get(i).setX(x - 1);
            }
            if (arrGoomba.get(i).getX() - mario.getX() <= MyFrame.W_Frame / 1.5 || arrGoomba.get(i).checkMove == true) {
                arrGoomba.get(i).move(arrMap, checkMapMove);
                arrGoomba.get(i).checkMove = true;
            }

            boolean checkDie = arrGoomba.get(i).checkDie(mario);
            if (checkDie) {
                arrGoomba.get(i).die();
                if (arrGoomba.get(i).die) {
//                    while (T - arrGoomba.get(i).getT() < 1000) {
//                        T = System.currentTimeMillis();
//                    }
                    arrGoomba.remove(i);
                    System.out.println("Goomba have been destroy");
                }
            }

        }
        mario.fall(arrMap);
        if (mario.checkDie(arrGoomba)) {
            mario.die();
            return false;
        }

        for (int i = arrCoin.size() - 1; i >= 0; i--) {
            boolean check = arrCoin.get(i).move();
            if (check == false) {
                arrCoin.remove(i);
            }
        }
        return true;
    }
}
