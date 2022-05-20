package com.cvut.src.model.bullet.rocket;

import com.cvut.src.model.bullet.Bullet;
import com.cvut.src.view.components.Renderparam;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.shape.Rectangle;

/**
 * Class representing a rocket
 * @author ulcheyev
 **/
public abstract class Rocket implements Bullet {
    protected Image img;
    protected ImageView view;
    protected Renderparam bulletParam;
    protected  double speedMove;
    protected double damage;

    /** Returns image of this rocket
     * @return rocket image
     **/
    public Image getImg() {return img;}

    /** Sets damage to this rocket
     * @params damage
     **/
    public double getDamage() { return damage; }

    /** Returns damage of this rocket
     * @return rocket damage
     **/
    public void setDamage(double damage) { this.damage = damage; }

    /** Returns Renderparam object
     * @return Renderparam object
     **/
    public Renderparam getImgParam() {return bulletParam;}

    /** Sets Renderparam object
     * @param imgParam Renderparam object
     **/
    public void setImgParam(Renderparam imgParam) {this.bulletParam = imgParam;}

    /** Returns image view of this rocket
     * @return  image view of this rocket
     **/
    public ImageView getImageView() {return view;}

    /** Returns speed move of this rocket
     * @return speed move of this rocket
     **/
    public double getSpeedMove() {return speedMove;}

    /** Sets speed move to this rocket
     * @param speedMove speed move to set to this rocket
     **/
    public void setSpeedMove(double speedMove) {this.speedMove = speedMove;}

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


