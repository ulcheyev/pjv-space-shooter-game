package com.cvut.src.model.bonusItem;
import com.cvut.src.controller.GameController;
import com.cvut.src.model.GameObject;
import com.cvut.src.view.components.Renderparam;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import java.io.Serializable;

/**
 * Class representing a bonus item
 * @author ulcheyev
 **/
public abstract class Item implements Serializable, GameObject {
    protected transient Image img;
    protected transient ImageView view;
    protected Renderparam renderParam;
    protected transient GameController controller;
    protected double attribute;
    protected int quantity;

    /**
     * The method update object parameters:
     * 1. The movement of the object relative to the speed of movement background
     * 2. Checks, if item and player ship have collision
     **/
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

    /**
     * The method paint object on game scene graphic context
     * @param graphicsContext graphic context
     **/
    public void paint(GraphicsContext graphicsContext){graphicsContext.drawImage(img, renderParam.getX(), renderParam.getY());}

    /**
     * The method activate item bonus and gives bonus to the player ship
     **/
    public abstract void activateBonus();

    /** Returns Rectangle object
     * @return rectangle of this object
     **/
    public Rectangle getRectangle() {
        Rectangle rectangle = new Rectangle(renderParam.getX(), renderParam.getY(), renderParam.getWidth() + 50, renderParam.getHeight() + 50);
        return rectangle;
    }

    /** Returns image path
     * @return image path
     **/
    public abstract String getIMG_PATH();

    /** Returns bonus item attribute
     * @return bonus item attribute
     **/
    public double getAttribute() {return attribute;}

    /** Sets bonus item attribute
     * @param  attribute to set
     **/
    public void setAttribute(double attribute) {this.attribute = attribute;}

    /** Returns image of this bonus item
     * @return image item
     **/
    public Image getImg() {return img;}

    /** Set image to  bonus item
     * @param  img item image to set
     **/
    public void setImg(Image img) {this.img = img;}

    /** Returns image view of this item
     * @return  image view of this item
     **/
    public ImageView getView() {return view;}

    /** Sets image view of this item
     * @param view image view of this item
     **/
    public void setView(ImageView view) {this.view = view;}

    /** Returns quantity of this item in inventory
     * @return quantity of this item in inventory
     **/
    public int getQuantity() {return quantity;}

    /** Returns Renderparam object
     * @return Renderparam object
     **/
    public Renderparam getRenderParam() {return renderParam;}

}
