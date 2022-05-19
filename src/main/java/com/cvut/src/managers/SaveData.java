package com.cvut.src.managers;

import com.cvut.src.model.bonusItem.Inventory;
import com.cvut.src.model.bonusItem.Item;
import com.cvut.src.model.enemy.BossType;
import com.cvut.src.model.player.ship.ShipType;

import java.io.Serializable;
import java.util.ArrayList;

public class SaveData implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    public ShipType shipType;
    public BossType bossType;
    public ArrayList<Item> items;
    public Inventory inventory;
}
