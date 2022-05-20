package com.cvut.src.managers;
import com.cvut.src.model.bonusItem.Item;
import com.cvut.src.model.enemy.BossType;
import com.cvut.src.model.player.ship.ShipType;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * A class that has data in it that is saved and loaded
 * @author ulcheyev
 **/

public class SaveData implements Serializable {

    private static final long serialVersionUID = 6529685098267757690L;

    /** Ship type to save **/
    public ShipType shipType;

    /** Boss type to save**/
    public BossType bossType;

    /** Inventory items to save**/
    public ArrayList<Item> items;

}
