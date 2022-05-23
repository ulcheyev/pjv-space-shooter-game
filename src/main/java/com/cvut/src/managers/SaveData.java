package com.cvut.src.managers;
import com.cvut.src.model.bonusItem.Item;
import com.cvut.src.model.bullet.ammo.Ammo;
import com.cvut.src.model.enemy.BossType;
import com.cvut.src.model.player.ship.ShipType;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that has data in it that is saved and loaded
 * @author ulcheyev
 **/

public class SaveData implements Serializable {

    private static final long serialVersionUID = 6529685098267757690L;

    /** Ship type to save **/
    private ShipType shipType;

    /** Boss type to save**/
    private BossType bossType;

    /** Inventory items to save**/
    private List<Item> items;

    /**Returns saved ship type
     * @return saved ship type
     **/
    public ShipType getShipType() {
        return shipType;
    }

    /**Sets ship type to save
     * @param  shipType ship type to save
     **/
    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    /**Returns saved boss type
     * @return saved boss type
     **/
    public BossType getBossType() {
        return bossType;
    }

    /**Sets boss type to save
     * @param  bossType boss type to save
     **/
    public void setBossType(BossType bossType) {
        this.bossType = bossType;
    }

    /**Returns saved items
     * @return  saved items
     **/
    public List<Item> getItems() {
        return items;
    }

    /**Sets items
     * @param items items to save
     **/
    public void setItems(List<Item> items) {
        this.items = items;
    }

}
