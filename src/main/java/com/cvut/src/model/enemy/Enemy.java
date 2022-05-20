package com.cvut.src.model.enemy;
import com.cvut.src.controller.GameController;
import com.cvut.src.model.GameObject;
import com.cvut.src.view.components.Renderparam;
import com.cvut.src.model.bullet.ammo.EnemyBullet;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import java.util.Random;
/**
 * The class represents the enemy in the game
 * @author ulcheyev
 **/
public abstract class Enemy implements GameObject {
    protected Random random = new Random();
    protected GameController controller;
    protected Image img;
    protected ImageView view;
    protected Renderparam imgParam;

    protected double moveSpeed = 5;

    //Direction of movement. Truth - to the right, False - to the left
    protected boolean route = true;

    //Maximum positions in the right and left parts of stage
    protected double maxLeftX;
    protected double maxRightX;

    //Enemy movement range
    protected double range;

    //Permission to move when moving sideways
    protected boolean acccessToMoveDependingOnTheHour = false;

    //Present time
    protected long time;

    //Enemy stop/wait time
    protected int stopTime = 1000;
    protected double health;
    protected double damage;

    //Delay until the next shots
    protected int shootDelay;

    //Delay until the next shots
    protected long lastShoot = 0;
    protected boolean shootAcces = true;
    protected boolean stopShoot = false;

    //A variable that prohibits the enemy from moving
    protected  boolean startAccessToMove  = false;

    /**
     * Enemy initialize
     * @param controller game controller
     **/
    public Enemy(GameController controller){
        this.controller = controller;
    }

    /**
     * The method paint this object on game scene graphic context
     * @param graphicsContext graphic context
     **/
    public void paint(GraphicsContext graphicsContext){ graphicsContext.drawImage(img, imgParam.getX(), imgParam.getY());}

    /**
     * The method update object parameters:
     * The coordinates of the object relative to the speed of movement
     **/
    //Downward movement at the start of the game
    public void update() {
        if(imgParam.getY() < 261) {
            moveSpeed = 1;
    		controller.getGameSpace().getPlayerShip().setShooting(false);
    		imgParam.setY(imgParam.getY() + moveSpeed);
    		if(imgParam.getY() + imgParam.getHeight() == 260) {
    			this.startAccessToMove = true;
                controller.getGameSpace().getPlayerShip().setShooting(true);
    			shootAcces = true;
    		}
    	}
    }

    /** Returns Rectangle object
     * @return rectangle of this object
     **/
    public Rectangle getRectangle(){
        Rectangle rectangle = new Rectangle(imgParam.getX(), imgParam.getY(), view.getFitWidth(), view.getFitHeight());
        return rectangle;
    }

    /**
     The method allows the enemy to shoot by adding bullets objects to the space enemy bullets list
     **/
    public void enemyShoot() {
        if(stopShoot == false) {
            shootDelay = random.nextInt(500) + 2500; //chtoby vragi strlyali po raznomu. v raznoe vremya
            if ((System.currentTimeMillis() - lastShoot) > shootDelay) {
                shootAcces = true;
            }
            if (shootAcces == true) {
                EnemyBullet bullet = new EnemyBullet(damage, imgParam.getX() + imgParam.getWidth() / 2, imgParam.getY());
                controller.getGameSpace().enemyBulletsAddBullet(bullet);
                shootAcces = false;
                lastShoot = System.currentTimeMillis();
            }
        }
    }
    /** Stop enemy shoot
     * @param stopShoot true-the enemy stop shooting, false-the enemy does not stop shooting
     **/
    public void setStopShoot(boolean stopShoot) {this.stopShoot = stopShoot;}

    /** Allow enemy to move
     * @param access true-enemy can move, false-enemy can not move
     **/
    public  void setStartAccessToMove(boolean access) {startAccessToMove = access;}

    /** Returns enemy damage
     * @return  enemy damage
     **/
    public double getDamage() { return this.damage; }

    /** Returns enemy health
     * @return  enemy health
     **/
    public double getHealth() { return health;}

    /** Returns enemy Randerparam object
     * @return  Randerparam object
     **/
    public Renderparam getImgParam() {return imgParam;}

    /** Decrease enemy health
     * @param  damage the enemy's health will decrease by the damage taken
     **/
    public void takinDamage(double damage){
        health -= damage;
    }
}
