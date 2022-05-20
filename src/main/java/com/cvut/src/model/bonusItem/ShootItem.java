package com.cvut.src.model.bonusItem;
import com.cvut.src.controller.GameController;
import com.cvut.src.model.bullet.rocket.PlayerRocket;
import com.cvut.src.model.bullet.rocket.Rocket;
import com.cvut.src.view.components.Renderparam;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;

/**
 * Class representing the Shoot bonus item (Rocket)
 * @author ulcheyev
 **/
public class ShootItem extends Item {
    private  final String IMG_PATH = "/items/Rocket_Bonus.png";

    /**
     * Initialize Shoot bonus item
     * @param controller game controller
     * @param x x coordinate to spawn
     * @param y y coordinate to spawn
     * @param damage damage of this bonus item
     **/
    public ShootItem(GameController controller, double x, double y, double damage){
        this.controller = controller;
        this.img = new Image(getClass().getResourceAsStream(IMG_PATH));
        this.renderParam = new Renderparam(x, y);
        this.view = new ImageView(this.img);
        this.attribute = damage;
    }


    /**
     * The method activate item bonus and gives bonus to the player ship.
     **/
    @Override
    public void activateBonus() {
        if (controller.getGameSpace().getPlayerInventory().getShootItemQuantity() >= 1) {
            controller.getGameSpace().getPlayerInventory().decreaseItemQuantity(this);
            controller.updateInventoryItems();
            Renderparam player = controller.getGameSpace().getPlayerShip().getImgParam();
            Rocket bullet = new PlayerRocket(500, player.getX() + player.getWidth() / 2, player.getY());
            bullet.setSpeedMove(-5);
            controller.getGameSpace().getPlayerBullets().add(bullet);
        }

    }

    /** Returns image path
     * @return image path
     **/
    @Override
    public String getIMG_PATH() {return IMG_PATH;}
}
