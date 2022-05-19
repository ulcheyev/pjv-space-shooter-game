package com.cvut.src.model.explosion;

import com.cvut.src.controller.GameController;
import com.cvut.src.model.GameObject;
import com.cvut.src.view.components.Renderparam;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public abstract class Explosion implements GameObject {
    protected Image spriteImage;
    protected Renderparam renderParam;
    protected int frame = 0;

    protected boolean run = true;
    protected int deltaTime = 0;
    protected int duration;

    public boolean getRun() { return this.run; }
    public abstract void update();
    public abstract void paint(GraphicsContext graphics);
    protected abstract void drawSpriteFrame(GraphicsContext graphics, Image spriteImage, double x, double y, int columnNumber, int frameNumber, float width, float height);
    public Rectangle getRectangle(){return null;}




}

