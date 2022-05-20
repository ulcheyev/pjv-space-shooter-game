package com.cvut.src.model.explosion;
import com.cvut.src.view.components.Renderparam;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The class represents a Red Destroy Explosion that appears after the destruction of the object
 * @author ulcheyev
 **/
public class RedDestroyExplosion extends Explosion {
    private final String IMG_PATH = "/explosion.png";

    /**
     * Initialize Red Destroy Explosion
     * @param x x coordinate
     * @param y y coordinate
     **/
    public RedDestroyExplosion(double x, double y) {
        this.renderParam = new Renderparam();
        this.renderParam.setX(x);
        this.renderParam.setY(y);
        spriteImage = new Image(IMG_PATH);
        this.duration = 1;
    }

    @Override
    public void update(){
        if(run){
            deltaTime++;
            if(deltaTime >= duration){
                frame++;
                deltaTime = 0;
                if(frame >= 65){ //pocet columnu
                    run = false;
                }
            }
        }
    }

    @Override
    public void paint(GraphicsContext graphics){
            drawSpriteFrame(graphics, spriteImage, renderParam.getX(), renderParam.getY(), 8, frame, 150, 149.1f);
    }

    @Override
    protected void drawSpriteFrame(GraphicsContext graphics, Image spriteImage, double x, double y, int columnNumber, int frameNumber, float width, float height){
        float frameX = (frame % columnNumber) * width;
        float frameY = (frame / columnNumber) * height;
        graphics.drawImage(spriteImage, frameX, frameY, 150, 149.1f, renderParam.getX(), renderParam.getY(), width, height);
    }





}
