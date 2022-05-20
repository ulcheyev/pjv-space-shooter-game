package com.cvut.src.model.bullet.ammo;
import com.cvut.src.controller.GameController;
import com.cvut.src.view.components.Renderparam;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

/**
 * Class representing player ship small bullet
 * @author ulcheyev
 **/
public class MyBullet extends Ammo {
    private final String IMG_PATH = "/bullet4.png";
    private GameController controller;

    /**
     * Player ship bullet initialize
     * @param controller game controller
     * @param damage damage of this bullet
     * @param x x coordinate
     * @param y y coordinate
     **/
    public MyBullet(GameController controller, double damage, double x, double y){
        this.controller = controller;
        this.speedMove = 6;
    	this.img = new Image(getClass().getResourceAsStream(IMG_PATH));
        this.view = new ImageView(img);
        this.bulletParam  = new Renderparam(x-img.getWidth()/2, y, img.getWidth(), img.getHeight());
        this.damage = damage;
    }

    @Override
    public  void update(){bulletParam.setY(bulletParam.getY() - speedMove);}



    @Override
    public  void paint(GraphicsContext graphicsContext) {graphicsContext.drawImage(img, bulletParam.getX(), bulletParam.getY());}


    @Override
    public Rectangle getRectangle(){
        Rectangle rectangle = new Rectangle(bulletParam.getX(), bulletParam.getY(), bulletParam.getWidth(), bulletParam.getHeight());
        return rectangle;
    }
}
