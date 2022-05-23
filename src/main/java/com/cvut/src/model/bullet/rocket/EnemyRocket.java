package com.cvut.src.model.bullet.rocket;

import com.cvut.src.view.components.Renderparam;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class EnemyRocket extends Rocket{
    private static final String IMG_PATH = "/boss_rocket.png";

    /**
     * Initialize Enemy Rocket
     * @param x x coordinate to spawn
     * @param y y coordinate to spawn
     * @param damage damage of this rocket
     **/
    public EnemyRocket(double damage, double x, double y) {
        this.speedMove = 6;
        img = new Image(getClass().getResourceAsStream(IMG_PATH));
        view = new ImageView(img);
        this.damage = damage;
        this.bulletParam = new Renderparam(x, y, img.getWidth(), img.getHeight());
    }

    @Override
    public  void update(){bulletParam.setY(bulletParam.getY() + speedMove);}

    @Override
    public  void paint(GraphicsContext graphicsContext) {graphicsContext.drawImage(img, bulletParam.getX(), bulletParam.getY(), img.getWidth(), img.getHeight());}

    @Override
    public Rectangle getRectangle(){
        Rectangle rectangle = new Rectangle(bulletParam.getX(), bulletParam.getY(), bulletParam.getWidth(), bulletParam.getHeight());
        return rectangle;
    }
}
