package com.cvut.src.model.bullet.ammo;

import com.cvut.src.view.components.Renderparam;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class BossBullet extends Ammo {
    private final String IMG_PATH = "/boss_second_shoot.png";

    public BossBullet(double damage, double x, double y){
        this.speedMove = 3;
        this.img = new Image(getClass().getResourceAsStream(IMG_PATH));
        this.view = new ImageView(img);
        this.bulletParam  = new Renderparam(x-img.getWidth()/2, y, img.getWidth(), img.getHeight());
        this.damage = damage;
    }

    @Override
    public  void update(){bulletParam.setY(bulletParam.getY() + speedMove);}

    @Override
    public  void paint(GraphicsContext graphicsContext) {graphicsContext.drawImage(img, bulletParam.getX(), bulletParam.getY());}

    //metoda vrati objekt typu rerctangle pro zjisteni kolize mezi objekty pri strileni
    public Rectangle getRectangle(){
        Rectangle rectangle = new Rectangle(bulletParam.getX(), bulletParam.getY(), bulletParam.getWidth() - 10, bulletParam.getHeight() - 10);
        return rectangle;

    }
}
