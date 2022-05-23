package com.cvut.src.model.bonusItem;

import com.cvut.src.controller.GameController;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the inventory where bonus items are stored
 * @author ulcheyev
 **/

public class Inventory implements Serializable{
    private List<Item> items;

    private  HPItem hp;
    private  SpeedItem speed;
    private  ShootItem shoot;

    /**
     * Initialize Inventory. Add items to inventory.
     * @param controller game controller
     **/
    public Inventory(GameController controller){
        items = new ArrayList<>();
        hp = new HPItem(controller, 930, 810);
        speed = new SpeedItem(controller, 930, 870);
        shoot = new ShootItem(controller, 930, 930, 0);
        items.add(hp);
        items.add(speed);
        items.add(shoot);
    }

    /**
     * Increase certain item quantity
     * @param item item which quantity will be increased
     **/
    public  void increaseItemQuantity(Item item){
        if (item instanceof HPItem) {
            hp.quantity = hp.quantity + 1;
        }if(item instanceof SpeedItem){
            speed.quantity = speed.quantity + 1;
        }if(item instanceof ShootItem){
            shoot.quantity = shoot.quantity + 1;
        }
    }

    /**
     * Decrease certain item quantity
     * @param item item which quantity will be decreased
     **/
    public  void decreaseItemQuantity(Item item){
        if (item instanceof HPItem) {
            hp.quantity = hp.quantity - 1;
        }if(item instanceof SpeedItem){
            speed.quantity = speed.quantity - 1;
        }if(item instanceof ShootItem){
            shoot.quantity = shoot.quantity - 1;
        }
    }

    /**
     * Resets items quantity
     **/
    public void resetItemsQuantity(){
      for(int i = 0; i < items.size(); i++){
          items.get(i).setQuantity(0);
      }
    }

    /**
     * Method activates  bonuses depending on the keys pressed
     * @param e key event
     **/
    public void keyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.DIGIT1) {
            hp.activateBonus();
        }
        if (e.getCode() == KeyCode.DIGIT2) {
            speed.activateBonus();
        }
        if (e.getCode() == KeyCode.DIGIT3) {
            shoot.activateBonus();
        }
    }

    /**
     * Method set items to inventory
     * @param items to set
     **/
    public void setItems(List<Item> items) {
       for (Item item: items){
           if (item instanceof HPItem) {
               hp.quantity = item.getQuantity();
               System.out.println(item.getQuantity());
           }if(item instanceof SpeedItem){
               speed.quantity = item.getQuantity();
           }if(item instanceof ShootItem){
               shoot.quantity = item.getQuantity();
           }
       }
    }

    /**
     * Return HP item from inventory
     * @return  hp item
     **/
    public HPItem getHp() {
        return hp;
    }

    /**
     * Return Speed item from inventory
     * @return  speed item
     **/
    public SpeedItem getSpeed() {
        return speed;
    }

    /**
     * Return Shoot item from inventory
     * @return  shoot item
     **/
    public ShootItem getShoot() {
        return shoot;
    }


    /**
     * Return HP item quantity from inventory
     * @return  hp item quantity
     **/
    public  int getHpItemQuantity(){return hp.quantity;}

    /**
     * Returns Speed item quantity from inventory
     * @return  speed item quantity
     **/
    public  int getSpeedItemQuantity(){return speed.quantity;}

    /**
     * Returns Shoot item quantity from inventory
     * @return  shoot item quantity
     **/
    public  int getShootItemQuantity(){return shoot.quantity;}

    /**
     * Returns all items  from inventory
     * @return  all items quantity
     **/
    public List<Item> getItems() {return items;}
}
