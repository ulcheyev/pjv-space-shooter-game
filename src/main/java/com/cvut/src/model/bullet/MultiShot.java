package com.cvut.src.model.bullet;

import com.cvut.src.controller.GameController;
import com.cvut.src.model.bullet.ammo.MyBullet;
import com.cvut.src.model.player.ship.PlayerShip;

public class MultiShot implements Runnable {
    private GameController controller;
    private PlayerShip ship;

    public MultiShot(GameController controller){
        this.controller = controller;
        this.ship = controller.getGameSpace().getPlayerShip();
    }

    private void multiShot(){
        controller.playSound("multi_shot");
        for(int i= 0; i < 60; i++){

            controller.getGameSpace().playerBulletsAddBullet(new MyBullet(controller,0.03,
                                                        ship.getImgParam().getX() + ship.getImgParam().getWidth()/2,
                                                        ship.getImgParam().getY() - 30));
            try {
                Thread.sleep(100);
            }catch (InterruptedException e){
                System.out.println(e);
            }
        }
        controller.setAllowToFillMultiShotBar(true);
    }


    @Override
    public void run() {
        multiShot();
    }

}
