package com.cvut.src.model.bonusItem;

import com.cvut.src.controller.GameController;
import com.cvut.src.view.components.Renderparam;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

public class SpeedItem extends Item implements Serializable {
    private  final String IMG_PATH = "/items/Speed_Bonus.png";
    private transient Timer timer;
    private  double originalShipSpeed;

    public SpeedItem(GameController controller, double x, double y){
        this.controller = controller;
        this.img = new Image(getClass().getResourceAsStream(IMG_PATH));
        this.renderParam = new Renderparam(x, y);
        this.view = new ImageView(this.img);
        this.attribute = 5;
        this.timer = new Timer();
    }


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

    @Override
    public String getIMG_PATH() {return IMG_PATH;}

    public TimerTask setPlayerShipOriginalSpeed(){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    controller.getGameSpace().getPlayerShip().setSpeedMove(originalShipSpeed);
                }catch (NullPointerException e){
                    e.printStackTrace();
                }

            }
        };
        return task;
    }
}
