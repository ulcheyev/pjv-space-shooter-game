package com.cvut.src.model.bonusItem;

import com.cvut.src.controller.GameController;
import com.cvut.src.model.bullet.Bullet;
import com.cvut.src.model.bullet.rocket.PlayerRocket;
import com.cvut.src.model.bullet.rocket.Rocket;
import com.cvut.src.view.components.Renderparam;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;

public class ShootItem extends Item implements Serializable {
    private  final String IMG_PATH = "/items/Rocket_Bonus.png";
    private  int damage;

    public ShootItem(GameController controller, double x, double y, int damage){
        this.controller = controller;
        this.img = new Image(getClass().getResourceAsStream(IMG_PATH));
        this.renderParam = new Renderparam(x, y);
        this.view = new ImageView(this.img);
    }


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

    @Override
    public String getIMG_PATH() {return IMG_PATH;}
}
