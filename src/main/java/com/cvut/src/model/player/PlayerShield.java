package com.cvut.src.model.player;

import com.cvut.src.controller.GameController;
import com.cvut.src.model.GameObject;
import com.cvut.src.view.components.Renderparam;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public class PlayerShield implements Serializable, GameObject {
    private final String IMG_PATH = "/shield.png";
    private transient GameController controller;
    private transient Image spriteImage;

    private int durability = 10;

    private int frame = 0;
    private int numRows = 5 ;
    private int numColumns = 5;
    private boolean start;
    protected int deltaTime = 0;
    protected int duration = 2;
    private double frameWidth;
    private double frameHeight;


    public boolean isStart() {
        return start;
    }
    public void setStart(boolean start) {
        this.start = start;
    }
    public int getDurability() {
        return durability;
    }
    public void setDurability(int durability) {
        this.durability = durability;
    }
    public void setController(GameController controller) {this.controller = controller;}


    public PlayerShield(GameController controller) {
        this.controller = controller;
        spriteImage = new Image(getClass().getResourceAsStream(IMG_PATH));

        frameWidth = spriteImage.getWidth() / numColumns;
        frameHeight = (spriteImage.getHeight()/ numRows) ;

    }

    public void update(){
        if(start){
            deltaTime++;
            if (deltaTime >= duration) {
                frame++;
                deltaTime = 0;
                if (frame >= 31) { //pocet columnu
                    frame = 0;
                    start =false;
                }
            }
        }
    }

    public void paint(GraphicsContext graphics){
        if(start) {
            drawSpriteFrame(graphics, spriteImage, controller.getGameSpace().getPlayerShip().getImgParam().getX() -
                            controller.getGameSpace().getPlayerShip().getImgParam().getHeight() / 2 + 15,
                    controller.getGameSpace().getPlayerShip().getImgParam().getY() - 28, 5, frame, frameWidth, frameHeight);
        }
    }


    public void drawSpriteFrame(GraphicsContext graphics, Image spriteImage, double x, double y, int columnNumber, int frameNumber, double width, double height){
        double frameX = (frameNumber % columnNumber) * width;
        double frameY = (frameNumber / columnNumber) * height;
        graphics.drawImage(spriteImage, frameX, frameY, frameWidth, frameHeight, x, y, frameWidth + 30, frameHeight + 30);
    }

    @Override
    public Rectangle getRectangle() {return null;}

}
