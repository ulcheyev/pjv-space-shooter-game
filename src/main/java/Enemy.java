import java.awt.*;
import java.util.Random;

public abstract class Enemy {
    protected Image img;
    protected Renderparam imgParam;
    protected int moveSpeed = 1;
    protected boolean route = true; //napravlenie dvizheniya
    protected int maxLeftX;
    protected int maxRightX;
    protected int range; //diapazon dvizheniya
    protected boolean moveAccess = true;
    protected long time;
    protected int stopTime = 1000;
    protected int health;
    protected Random random = new Random();

    public int getHealth() { return health;}
    public void setHealth(int health) { this.health = health; }



    public void paint(Graphics graphics){
        graphics.drawImage(img, imgParam.getX(), imgParam.getY(), imgParam.getWidth(), imgParam.getHeight(), null);
    }

    public void update(){
    }

    public Rectangle getRectangle(){
        return null;
    }

    public void takinDamage(int damage){
        health -= damage;
    }

}
