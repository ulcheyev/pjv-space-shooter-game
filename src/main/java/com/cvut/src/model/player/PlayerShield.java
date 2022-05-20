package com.cvut.src.model.player;

import com.cvut.src.controller.GameController;
import com.cvut.src.model.GameObject;
import com.cvut.src.view.components.Renderparam;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

/**
 * The class represents the player's ship shield
 * @author ulcheyev
 **/
public class PlayerShield implements Serializable, GameObject {
    private final String IMG_PATH = "/shield.png";
    private transient GameController controller;
    private transient Image spriteImage;

    //How many hits can it withstand
    private int durability = 10;

    private int frame = 0;
    private int numRows = 5 ;
    private int numColumns = 5;
    private boolean start;
    protected int deltaTime = 0;
    protected int duration = 2;
    private double frameWidth;
    private double frameHeight;

    /**
     * Initialize Player Shield
     * @param controller game controller
     **/
    public PlayerShield(GameController controller) {
        this.controller = controller;
        spriteImage = new Image(getClass().getResourceAsStream(IMG_PATH));

        frameWidth = spriteImage.getWidth() / numColumns;
        frameHeight = (spriteImage.getHeight()/ numRows) ;
    }


    /**
     * The method update object parameters:
     * Changes frame depending on the delay
     **/
    public void update(){
        if(start){
            deltaTime++;
            if (deltaTime >= duration) {
                frame++;
                deltaTime = 0;
                if (frame >= 31) {
                    frame = 0;
                    start =false;
                }
            }
        }
    }

    /**
     * The method paint this object on game scene graphic context
     * @param graphics graphic context
     **/
    public void paint(GraphicsContext graphics){
        if(start) {
            drawSpriteFrame(graphics, spriteImage, controller.getGameSpace().getPlayerShip().getImgParam().getX() -
                            controller.getGameSpace().getPlayerShip().getImgParam().getHeight() / 2 + 15,
                    controller.getGameSpace().getPlayerShip().getImgParam().getY() - 28, 5, frame, frameWidth, frameHeight);
        }
    }

    /**
     * The method gets frames out of the picture and switches them. Adds frame to game scene graphic context
     * @param graphics graphic context
     * @param spriteImage image to draw
     * @param x frame x coordinate
     * @param y frome y coordinate
     * @param columnNumber image column numbers
     * @param frameNumber present frame number
     * @param width frame width
     * @param height frame height
     **/
    public void drawSpriteFrame(GraphicsContext graphics, Image spriteImage, double x, double y, int columnNumber, int frameNumber, double width, double height){
        double frameX = (frameNumber % columnNumber) * width;
        double frameY = (frameNumber / columnNumber) * height;
        graphics.drawImage(spriteImage, frameX, frameY, frameWidth, frameHeight, x, y, frameWidth + 30, frameHeight + 30);
    }


    @Override
    public Rectangle getRectangle() {return null;}

    /** Sets allow update shield
     * @param start true-allow update, false-not allow
     **/
    public void setStart(boolean start) {
        this.start = start;
    }

    /**Returns durability of shield
     * @return  durability of shield
     **/
    public int getDurability() {
        return durability;
    }

    /**Sets durability of shield
     * @param  durability durability to set
     **/
    public void setDurability(int durability) {
        this.durability = durability;
    }

    /**Sets game controller
     * @param  controller game controller to set
     **/
    public void setController(GameController controller) {this.controller = controller;}

}
