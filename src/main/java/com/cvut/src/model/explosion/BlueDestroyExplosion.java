package com.cvut.src.model.explosion;

import com.cvut.src.view.components.Renderparam;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
/**
 * The class represents a Blue Destroy Explosion that appears after the destruction of the object
 * @author ulcheyev
 **/
public class BlueDestroyExplosion extends Explosion {
    private final String IMG_PATH = "/blue_explosion.png";


    /**
     * Initialize Blue Destroy Explosion
     * @param x x coordinate
     * @param y y coordinate
     **/
    public BlueDestroyExplosion(double x, double y) {
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
                if(frame >= 26){ //pocet columnu
                    run = false;
                }
            }
        }
    }

    @Override
    public void paint(GraphicsContext graphics){
        drawSpriteFrame(graphics, spriteImage, renderParam.getX(), renderParam.getY(), 5, frame, 201.3f, 201.3f);
    }

    @Override
    protected void drawSpriteFrame(GraphicsContext graphics, Image spriteImage, double x, double y, int columnNumber, int frameNumber, float width, float height){
        float frameX = (frame % columnNumber) * width;
        float frameY = (frame / columnNumber) * height;
        graphics.drawImage(spriteImage, frameX, frameY, width, 189, renderParam.getX(), renderParam.getY(), width, height);
    }


}
