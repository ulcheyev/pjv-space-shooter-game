package com.cvut.src.model.explosion;

import com.cvut.src.controller.GameController;
import com.cvut.src.model.GameObject;
import com.cvut.src.view.components.Renderparam;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
/**
 * The class represents an explosion that appears after the destruction of the object
 * @author ulcheyev
 **/
public abstract class Explosion implements GameObject {
    protected Image spriteImage;
    protected Renderparam renderParam;
    //Present frame
    protected int frame = 0;

    //Variable for rendering the image frame
    protected boolean run = true;

    //Variable for duration
    protected int deltaTime = 0;

    protected int duration;

    /**
     * Return frame rendering state. True - the frame is still being drawn, False - rendering is over
     * @return  frame rendering state. True - the frame is still being drawn, False - rendering is over
     **/
    public boolean getRun() { return this.run; }

    /**
     * The method update object parameters:
     * Changes frame depending on the delay
     **/
    public abstract void update();

    /**
     * The method paint this object on game scene graphic context
     * @param graphics graphic context
     **/
    public abstract void paint(GraphicsContext graphics);

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
    protected abstract void drawSpriteFrame(GraphicsContext graphics, Image spriteImage, double x, double y, int columnNumber, int frameNumber, double width, double height);

    /** Returns Rectangle object
     * @return rectangle of this object
     **/
    public Rectangle getRectangle(){return null;}




}

