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

public  class PlayerShip implements Serializable, GameObject {
    private transient GameController controller;
    protected  PlayerShield shield;
    protected Inventory inventory;


    //PARAMS
    protected double damage;
    protected double health;
    protected double speedMove;
    private ShipType type;
    protected transient Image img;
    protected transient ImageView view;
    protected Renderparam imgParam;



    protected double moveX = 0; //tekushee peremeshenie geroya
    protected double moveY = 0; //tekushee peremeshenie geroya
    protected int shootDelay = 200; // zaderzhka mezhdu vystrelami ms.
    protected long lastShoot = 0; // vremya posledniho vystrela
    protected boolean shooting = false; // razreshenie na vystre


    public ImageView getView() {return view;}
    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }
    public boolean isShooting() {
        return shooting;
    }
    public  double getDamage() {
        return damage;
    }
    public Image getImg() {
        return img;
    }
    public Renderparam getImgParam() {
        return imgParam;
    }
    public double getHealth() {return health;}
    public void setHealth(double health) {this.health = health;}
    public double getSpeedMove() {return speedMove;}
    public void setSpeedMove(double speedMove) {this.speedMove = speedMove;}
    public void setController(GameController controller) {this.controller = controller;}
    public PlayerShield getShield() {
        return shield;
    }
    public void clearInventory() {this.inventory = null;}
    public void setInventory(Inventory inventory) {this.inventory = inventory;}
    public Inventory getInventory() {
        return inventory;
    }
    public ShipType getType() {return type;}



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
    public void paint(GraphicsContext graphicsContext){graphicsContext.drawImage(img, imgParam.getX(), imgParam.getY());};

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

    //UPRAVLENIE
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

    public Rectangle getRectangle(){
        Rectangle rectangle = new Rectangle(imgParam.getX(), imgParam.getY() + 15, imgParam.getWidth() - 10, imgParam.getHeight() - 20);
        return rectangle;
    }

    void playerBulletShooting(){
        controller.playSound("shot");
        MyBullet myBullet = new MyBullet(controller, damage, imgParam.getX() + imgParam.getWidth() / 2, imgParam.getY());
        controller.getGameSpace().playerBulletsAddBullet(myBullet);
        lastShoot = System.currentTimeMillis();
        setShooting(false);
    }

    void playerMultiShooting(){
        setShooting(false);
        Thread thread = new Thread(new MultiShot(controller));
        thread.start();
    }

    public void takinDamage(double damage) {
        if(shield.getDurability() != 0){
            shield.setDurability(shield.getDurability() - 1);
            shield.setStart(true);
        }else {
            shield.setStart(false);
            health = health - damage;
        }
    }


}



