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
    private static final String IMG_PATH = "/explosion.png";

    //Column quantity * Row quantity in sprite image
    private static final int TOTAL_NUMBER_OF_FRAMES = 48;

    private static final int TOTAL_COLUMN_NUMBER_IN_SPRITE_IMAGE = 8;
    private static final float WIDTH_OF_THE_CUT_OUT_FRAGMENT = 150;
    private static final float HEIGHT_OF_THE_CUT_OUT_FRAGMENT = 149.1f;
    private static final float WIDTH_OF_THE_IMAGE_FROM_ORIGINAL_IMAGE = 150;
    private static final float HEIGHT_OF_THE_IMAGE_FROM_ORIGINAL_IMAGE = 149.1f;


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
                if(frame >= TOTAL_NUMBER_OF_FRAMES){ //pocet columnu
                    run = false;
                }
            }
        }
    }

    @Override
    public void paint(GraphicsContext graphics){
            drawSpriteFrame(graphics, spriteImage, renderParam.getX(), renderParam.getY(), TOTAL_COLUMN_NUMBER_IN_SPRITE_IMAGE, frame, WIDTH_OF_THE_CUT_OUT_FRAGMENT, HEIGHT_OF_THE_CUT_OUT_FRAGMENT);
    }

    @Override
    protected void drawSpriteFrame(GraphicsContext graphics, Image spriteImage, double x, double y, int columnNumber, int frameNumber, double width, double height){
        double frameX = (frameNumber % columnNumber) * width;
        double frameY = (frameNumber / columnNumber) * height;
        graphics.drawImage(spriteImage, frameX, frameY, WIDTH_OF_THE_IMAGE_FROM_ORIGINAL_IMAGE, HEIGHT_OF_THE_IMAGE_FROM_ORIGINAL_IMAGE, renderParam.getX(), renderParam.getY(), width, height);
    }





}
