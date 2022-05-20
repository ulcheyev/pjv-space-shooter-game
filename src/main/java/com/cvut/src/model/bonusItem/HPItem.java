package com.cvut.src.model.bonusItem;
import com.cvut.src.controller.GameController;
import com.cvut.src.managers.SaveManager;
import com.cvut.src.view.components.Renderparam;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class representing the HP bonus item
 * @author ulcheyev
 **/
public class HPItem extends Item{
    private final String IMG_PATH = "/items/HP_Bonus.png";

    /**
     * Initialize HP bonus item
     * @param controller game controller
     * @param x x coordinate to spawn
     * @param y y coordinate to spawn
     **/
    public HPItem(GameController controller, double x, double y){
        this.controller = controller;
        this.img = new Image(getClass().getResourceAsStream(IMG_PATH));
        this.renderParam = new Renderparam(x, y);
        this.view = new ImageView(this.img);
        this.attribute = 0.2;
    }


    /**
     * The method activate item bonus and gives bonus to the player ship
     **/
    @Override
    public void activateBonus(){
        if(controller.getGameSpace().getPlayerShip().getHealth() > 1 ||
                controller.getGameSpace().getPlayerInventory().getHpItemQuantity() < 1){
            return;
        }else{
            controller.getGameSpace().getPlayerInventory().decreaseItemQuantity(this);
            controller.updateInventoryItems();
            controller.getGameSpace().getPlayerShip().setHealth(controller.getGameSpace().getPlayerShip().getHealth() + attribute);
            controller.setHPBarProgress(controller.getHPBar().getProgress() + attribute);
        }
    }

    /** Returns image path
     * @return image path
     **/
    public String getIMG_PATH() {return IMG_PATH;}


}
