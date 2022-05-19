package com.cvut.src.model.bonusItem;

import com.cvut.src.controller.GameController;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;

public class Inventory implements Serializable{
    private ArrayList<Item> items;


    private  HPItem hp;
    private  SpeedItem speed;
    private  ShootItem shoot;

    public Inventory(GameController controller){
        items = new ArrayList<>();
        hp = new HPItem(controller, 930, 810);
        speed = new SpeedItem(controller, 930, 870);
        shoot = new ShootItem(controller, 930, 930, 0);
        items.add(hp);
        items.add(speed);
        items.add(shoot);
    }

    public  void increaseItemQuantity(Item item){
        if (item instanceof HPItem) {
            hp.quantity = hp.quantity + 1;
        }if(item instanceof SpeedItem){
            speed.quantity = speed.quantity + 1;
        }if(item instanceof ShootItem){
            shoot.quantity = shoot.quantity + 1;
        }
    }

    public  void decreaseItemQuantity(Item item){
        if (item instanceof HPItem) {
            hp.quantity = hp.quantity - 1;
        }if(item instanceof SpeedItem){
            speed.quantity = speed.quantity - 1;
        }if(item instanceof ShootItem){
            shoot.quantity = shoot.quantity - 1;
        }
    }

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

    public  int getHpItemQuantity(){return hp.quantity;}

    public  int getSpeedItemQuantity(){return speed.quantity;}

    public  int getShootItemQuantity(){return shoot.quantity;}

    public ArrayList<Item> getItems() {return items;}


    public void setItems(ArrayList<Item> items) {
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

    public HPItem getHp() {
        return hp;
    }
    public void setHp(HPItem hp) {
        this.hp = hp;
    }
    public SpeedItem getSpeed() {
        return speed;
    }
    public void setSpeed(SpeedItem speed) {
        this.speed = speed;
    }
    public ShootItem getShoot() {
        return shoot;
    }
    public void setShoot(ShootItem shoot) {
        this.shoot = shoot;
    }
}
