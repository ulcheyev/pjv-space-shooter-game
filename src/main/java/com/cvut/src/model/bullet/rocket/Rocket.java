package com.cvut.src.model.bullet.rocket;

import com.cvut.src.model.bullet.Bullet;
import com.cvut.src.view.components.Renderparam;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.shape.Rectangle;

public abstract class Rocket implements Bullet {
    protected Image img;
    protected ImageView view;
    protected Renderparam bulletParam;
    protected  double speedMove;
    protected double damage;

    public Image getImg() {return img;}
    public double getDamage() { return damage; }
    public void setDamage(double damage) { this.damage = damage; }
    public Renderparam getImgParam() {return bulletParam;}
    public void setImgParam(Renderparam imgParam) {this.bulletParam = imgParam;}
    public ImageView getImageView() {return view;}
    public double getSpeedMove() {return speedMove;}
    public void setSpeedMove(double speedMove) {this.speedMove = speedMove;}


    public abstract void update();
    public abstract void paint(GraphicsContext graphicsContext);
    public abstract Rectangle getRectangle();

}


