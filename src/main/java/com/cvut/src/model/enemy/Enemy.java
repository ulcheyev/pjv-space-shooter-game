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

public abstract class Enemy implements GameObject {
    protected Random random = new Random();
    protected GameController controller;

    //IMAGE
    protected Image img;
    protected ImageView view;
    protected Renderparam imgParam;
    protected double moveSpeed = 5;
    protected boolean route = true; //napravlenie dvizheniya  true - vpravo, false - vlevo
    protected double maxLeftX;
    protected double maxRightX;
    protected double range; //diapazon dvizheniya
    protected boolean acccessToMoveDependingOnTheHour = false; //razreshenie dvizheniya pri peredvizhenii v storonu
    protected long time; // soucasny.
    protected int stopTime = 1000; // vremya ostanovky vraga
    protected double health;
    protected double damage;
    protected int shootDelay;
    protected long lastShoot = 0; // vremya posledniho vystrela
    protected boolean shootAcces = true;
    protected boolean stopShoot = false;
    protected  boolean startAccessToMove  = false; //peremennaya dlya togo, chtiby pri starte vragi ehali toolko vniz


    public Enemy(GameController controller){
        this.controller = controller;
    }


    public void setStopShoot(boolean stopShoot) {this.stopShoot = stopShoot;}
    public  void setStartAccessToMove(boolean access) {startAccessToMove = access;}
    public double getDamage() { return this.damage; }
    public double getHealth() { return health;}
    public Renderparam getImgParam() {return imgParam;}

    public void takinDamage(double damage){
        health -= damage;
    }

    public void paint(GraphicsContext graphicsContext){ graphicsContext.drawImage(img, imgParam.getX(), imgParam.getY());}

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

    public Rectangle getRectangle(){
        Rectangle rectangle = new Rectangle(imgParam.getX(), imgParam.getY(), view.getFitWidth(), view.getFitHeight());
        return rectangle;
    }


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
}
