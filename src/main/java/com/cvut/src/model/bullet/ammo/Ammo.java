package com.cvut.src.model.bullet.ammo;
import com.cvut.src.model.bullet.Bullet;
import com.cvut.src.view.components.Renderparam;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
/**
 * Class representing a single ship cartridges
 * @author ulcheyev
 **/
public abstract class Ammo implements Bullet {
    protected Image img;
    protected ImageView view;
    protected Renderparam bulletParam;

    protected  int speedMove;
    protected double damage;

    /** Returns image of this ammo
     * @return image item
     **/
    public Image getImg() {return img;}


    /** Returns damage of this ammo
     * @return damage ammo
     **/
    public double getDamage() { return damage; }

    /** Sets damage to this ammo
     * @param  damage damage to set
     **/
    public void setDamage(double damage) { this.damage = damage; }

    /** Returns Renderparam object
     * @return Renderparam object
     **/
    public Renderparam getImgParam() {return bulletParam;}

    /** Sets Renderparam object to this ammo
     * @param  imgParam Renderparam object
     **/
    public void setImgParam(Renderparam imgParam) {this.bulletParam = imgParam;}

    /** Returns image view of this ammo
     * @return  image view of this ammo
     **/
    public ImageView getImageView() {return view;}

    /** Returns speed move of this ammo
     * @return  image view of this ammo
     **/
    public double getSpeedMove() {return speedMove;}

    /**
     * The method update object parameters:
     * The coordinates of the object relative to the speed of movement
     **/
    public abstract void update();

    /**
     * The method paint this object on game scene graphic context
     * @param graphicsContext graphic context
     **/
    public abstract void paint(GraphicsContext graphicsContext);

    /** Returns Rectangle object
     * @return rectangle of this object
     **/
    public abstract Rectangle getRectangle();

}
