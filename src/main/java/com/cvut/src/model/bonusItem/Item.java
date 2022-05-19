package com.cvut.src.model.bonusItem;
import com.cvut.src.controller.GameController;
import com.cvut.src.model.GameObject;
import com.cvut.src.view.components.Renderparam;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public abstract class Item implements Serializable, GameObject {
    protected transient Image img;
    protected transient ImageView view;
    protected Renderparam renderParam;
    protected transient GameController controller;
    protected double attribute;
    protected int quantity;


    public double getAttribute() {return attribute;}
    public void setAttribute(double attribute) {this.attribute = attribute;}
    public Image getImg() {return img;}
    public void setImg(Image img) {this.img = img;}
    public ImageView getView() {return view;}
    public void setView(ImageView view) {this.view = view;}
    public int getQuantity() {return quantity;}
    public Renderparam getRenderParam() {return renderParam;}

    public void update() {
        renderParam.setY(renderParam.getY() + controller.getGameSpace().getBackground().getMoveSpeed());
        if(controller.getGameSpace().getPlayerShip() != null){
            if(this.getRectangle().getBoundsInLocal().intersects(controller.getGameSpace().getPlayerShip().getRectangle().getBoundsInParent())) {
                controller.getGameSpace().getPlayerInventory().increaseItemQuantity(this);
                this.attribute = 0;
                controller.updateInventoryItems();
            }
        }
    }

    public void paint(GraphicsContext graphicsContext){graphicsContext.drawImage(img, renderParam.getX(), renderParam.getY());}
    public abstract void activateBonus();

    public Rectangle getRectangle() {
        Rectangle rectangle = new Rectangle(renderParam.getX(), renderParam.getY(), renderParam.getWidth() + 50, renderParam.getHeight() + 50);
        return rectangle;
    }
    public abstract String getIMG_PATH();



}
