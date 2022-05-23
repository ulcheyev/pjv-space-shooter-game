package com.cvut.src.model.bonusItem;

import com.cvut.src.controller.GameController;
import com.cvut.src.model.player.ship.PlayerShip;
import com.cvut.src.view.components.Renderparam;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class representing the Speed bonus item
 * @author ulcheyev
 **/
public class SpeedItem extends Item implements Serializable {
    private final static Logger logger = Logger.getLogger(GameController.class.getName());


    private transient Timer timer;
    private  double originalShipSpeed;

    /**
     * Initialize Speed bonus item
     * @param controller game controller
     * @param x x coordinate to spawn
     * @param y y coordinate to spawn
     **/
    public SpeedItem(GameController controller, double x, double y){
        super("/items/Speed_Bonus.png");
        this.controller = controller;
        this.img = new Image(getClass().getResourceAsStream(IMG_PATH));
        this.renderParam = new Renderparam(x, y);
        this.view = new ImageView(this.img);
        this.attribute = 5;
        this.timer = new Timer();
    }

    /**
     * The method activate item bonus and gives bonus to the player ship.
     **/
    @Override
    public void activateBonus(){
        if (controller.getGameSpace().getPlayerInventory().getSpeedItemQuantity() >= 1) {
            controller.getGameSpace().getPlayerInventory().decreaseItemQuantity(this);
            controller.updateInventoryItems();
            this.originalShipSpeed = controller.getGameSpace().getPlayerShip().getSpeedMove();
            controller.getGameSpace().getPlayerShip().setSpeedMove(attribute);
            timer.schedule(setPlayerShipOriginalSpeed(), 10000);
        }
    }




    /** Returns player speed to original before speed item bonus passed
     * @return TimerTask. Object set player ship speed move to original after 10 seconds.
     **/
    public TimerTask setPlayerShipOriginalSpeed(){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    PlayerShip ship  = controller.getGameSpace().getPlayerShip();
                    ship.setSpeedMove(originalShipSpeed);
                }catch (NullPointerException e){
                    e.printStackTrace();
                    logger.log(Level.SEVERE, "Player ship is null");
                }

            }
        };
        return task;
    }
}
