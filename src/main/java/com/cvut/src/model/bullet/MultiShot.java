package com.cvut.src.model.bullet;

import com.cvut.src.controller.GameController;
import com.cvut.src.model.bullet.ammo.MyBullet;
import com.cvut.src.model.player.ship.PlayerShip;

/**
 * Class representing a multi shot
 * @author ulcheyev
 **/
public class MultiShot implements Runnable {
    private final int FIRE_RATE_MULTISHOT = 60;
    private GameController controller;
    private PlayerShip ship;

    /**
     * Initialize Multi Shot
     * @param controller game controller
     **/
    public MultiShot(GameController controller){
        this.controller = controller;
        this.ship = controller.getGameSpace().getPlayerShip();
    }
    private void multiShot(){
        controller.playSound("multi_shot");
        for(int i= 0; i < FIRE_RATE_MULTISHOT; i++){

            controller.getGameSpace().playerBulletsAddBullet(new MyBullet(controller,0.03,
                                                        ship.getImgParam().getX() + ship.getImgParam().getWidth()/2,
                                                        ship.getImgParam().getY() - 30));
            try {
                Thread.sleep(100);
            }catch (InterruptedException e){
                controller.showErrorMessage("Problem with game thread while multi shooting");
                e.printStackTrace();
            }
        }
        controller.setAllowToFillMultiShotBar(true);
    }

    /**
     The method launches a multi shot
     **/
    @Override
    public void run() {
        multiShot();
    }

}
