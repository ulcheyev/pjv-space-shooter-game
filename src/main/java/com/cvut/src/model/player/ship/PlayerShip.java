package com.cvut.src.model.player.ship;
import com.cvut.src.controller.GameController;
import com.cvut.src.managers.AudioManager;
import com.cvut.src.model.GameObject;
import com.cvut.src.model.bonusItem.Inventory;
import com.cvut.src.model.bullet.Bullet;
import com.cvut.src.model.bullet.MultiShot;
import com.cvut.src.model.player.PlayerShield;
import com.cvut.src.view.components.Renderparam;
import com.cvut.src.model.bullet.ammo.MyBullet;
import com.cvut.src.view.GameView;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

/**
 * The class represents the player's ship
 * @author ulcheyev
 **/
public  class PlayerShip implements Serializable, GameObject {

    private transient GameController controller;

    protected  PlayerShield shield;
    protected Inventory inventory;

    protected double damage;
    protected double health;
    protected double speedMove;
    private ShipType type;
    protected transient Image img;
    protected transient ImageView view;
    protected Renderparam imgParam;

    //Current movement x coordinate
    protected double moveX = 0;

    //Current movement y coordinate
    protected double moveY = 0;
    protected int shootDelay = 200;
    protected long lastShoot = 0;

    //Allow shooting
    protected boolean shooting = false;

    /**
     * Initialize Player Ship and its components
     * @param controller game controller
     * @param type ship type
     **/
    public PlayerShip(GameController controller, ShipType type){
        this.inventory = new Inventory(controller);
        this.type = type;
        this.controller = controller;
        this.shield = new PlayerShield(controller);
        this.damage = type.getDamage();
        this.health = type.getHealth();
        this.speedMove = type.getMoveSpeed();
        this.img = new Image(getClass().getResourceAsStream(type.getImgPath()));
        this.view = new ImageView(img);
        this.imgParam = new Renderparam(600, 800, img.getWidth(), img.getHeight());
    }

    /**
     * The method paint this object on game scene graphic context.
     * @param graphicsContext graphic context
     **/
    public void paint(GraphicsContext graphicsContext){graphicsContext.drawImage(img, imgParam.getX(), imgParam.getY());};


    /**
     * The method update object parameters:
     * Changes coordinates depending on move speed.
     * Check move restrictions on stage
     **/
    public void update() {
        imgParam.setX(imgParam.getX() + moveX);
        imgParam.setY(imgParam.getY() + moveY);
        if (imgParam.getX() <= 0) {
            imgParam.setX(0);
        }
        if (imgParam.getX() >= GameView.GAME_WIDTH - (imgParam.getWidth())) {
            imgParam.setX( GameView.GAME_WIDTH  - (imgParam.getWidth()));
        }
        if (imgParam.getY() >= 860) {
            imgParam.setY(860);
        }
        if (imgParam.getY() <= 400) {
            imgParam.setY(400);
        }
        if ((System.currentTimeMillis() - lastShoot) > shootDelay) {
            shooting = true;
        }
    }

    /**
     * The method accepts an event from listener and moves the ship
     * @param e key event
     **/
    public void keyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.D) {
            moveX = speedMove;
        }
        if (e.getCode() == KeyCode.A) {
            moveX = -speedMove;
        }
        if (e.getCode() == KeyCode.W) {
            moveY = -speedMove;
        }
        if (e.getCode() == KeyCode.S) {
            moveY = speedMove;
        }
        if (e.getCode() == KeyCode.SPACE) {
            if(shooting == true) {
                playerBulletShooting();
            }
        }if (e.getCode() == KeyCode.TAB){
            playerMultiShooting();
        }
    }

    /**
     * The method accepts an event from listener and resets movement
     * @param e key event
     **/
    public void keyReleased(KeyEvent e) {
        if(e.getCode() == KeyCode.D){
            moveX = 0;
        }
        if(e.getCode() == KeyCode.A){
            moveX = 0;
        }
        if(e.getCode() == KeyCode.W) {
            moveY = 0;
        }
        if(e.getCode() == KeyCode.S) {
            moveY = 0;
        }
    }

    /** Returns Rectangle object
     * @return rectangle of this object
     **/
    public Rectangle getRectangle(){
        Rectangle rectangle = new Rectangle(imgParam.getX(), imgParam.getY() + 15, imgParam.getWidth() - 10, imgParam.getHeight() - 20);
        return rectangle;
    }

    /**
     The method realize a player ship shot.
     **/
    void playerBulletShooting(){
        controller.playSound("shot");
        MyBullet myBullet = new MyBullet(controller, damage, imgParam.getX() + imgParam.getWidth() / 2, imgParam.getY());
        controller.getGameSpace().playerBulletsAddBullet(myBullet);
        lastShoot = System.currentTimeMillis();
        setShooting(false);
    }

    /**
     The method realize a player ship multi shot.
     **/
    void playerMultiShooting(){
        setShooting(false);
        Thread thread = new Thread(new MultiShot(controller));
        thread.start();
    }


    /** Decrease player ship health
     * @param  damage the player's ship health will decrease by the damage taken
     **/
    public void takinDamage(double damage) {
        if(shield.getDurability() != 0){
            shield.setDurability(shield.getDurability() - 1);
            shield.setStart(true);
        }else {
            shield.setStart(false);
            health = health - damage;
        }
    }
    /** Resets player's ship shield
     **/
    public void resetShield() {this.shield = new PlayerShield(controller);}

    /** Returns image view of player ship
     * @return  image view of player ship
     **/
    public ImageView getView() {return view;}

    /** Sets allow to shooting
     * @param shooting allow to shooting; true - allow shooting; false - not allow
     **/
    public void setShooting(boolean shooting) {this.shooting = shooting;}

    /** Returns player ship damage
     * @return  player ship damage
     **/
    public  double getDamage() {
        return damage;
    }

    /** Returns player ship image
     * @return  player ship image
     **/
    public Image getImg() {
        return img;
    }

    /** Returns Renderparam object
     * @return  Renderparam object
     **/
    public Renderparam getImgParam() {
        return imgParam;
    }

    /** Returns player's ship health
     * @return  player's ship health
     **/
    public double getHealth() {return health;}

    /** Sets player's ship health
     * @param health  health to set
     **/
    public void setHealth(double health) {this.health = health;}

    /** Returns player's ship move speed
     * @return  health  health to set
     **/
    public double getSpeedMove() {return speedMove;}

    /** Sets player's ship move speed
     * @param  speedMove speed move to speed
     **/
    public void setSpeedMove(double speedMove) {this.speedMove = speedMove;}

    /** Sets player's ship object game controller
     * @param  controller controller
     **/
    public void setController(GameController controller) {this.controller = controller;}

    /** Returns player's ship shield
     * @return  player's ship shield
     **/
    public PlayerShield getShield() {
        return shield;
    }

    /**
     * Sets player's ship inventory to null
     **/
    public void clearInventory() {this.inventory = null;}

    /**
     * Sets player's ship inventory
     * @param inventory inventory to set
     **/
    public void setInventory(Inventory inventory) {this.inventory = inventory;}

    /**
     * Returns player's ship inventory
     * @return  player's ship inventory
     **/
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Returns player's ship type
     * @return  player's ship type
     **/
    public ShipType getType() {return type;}

}



