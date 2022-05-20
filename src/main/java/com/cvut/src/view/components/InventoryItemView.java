package com.cvut.src.view.components;

import com.cvut.src.controller.GameController;
import com.cvut.src.model.bonusItem.HPItem;
import com.cvut.src.model.bonusItem.Item;
import com.cvut.src.model.bonusItem.ShootItem;
import com.cvut.src.model.bonusItem.SpeedItem;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
/**
 * Class representing an item in the inventory.
 * @author ulcheyev
 **/
public class InventoryItemView {
    private GameController controller;
    private Item item;
    private Label label;

    /**
     * Initialize item in inventory
     * @param item item to put to inventory
     * @param controller game controller
     **/
    public InventoryItemView(Item item, GameController controller){
        this.label = new Label();
        this.item = item;
        this.controller  = controller;
    }

    /**
     The method creates an item in the inventory, adds a label to it, and adds other styles
     @return return group with item image and its label quantity
     **/
    public Group makeInventoryItem(){
        Group itemInInventory = new Group();
        label.setFont(Font.font("Courier", FontWeight.BOLD, 15)); // set to Label
        label.setTextFill(Color.WHITE);
        label.setBackground(new Background(new BackgroundFill(Color.GREEN, new CornerRadii(10), null)));
        itemInInventory.getChildren().add(new ImageView(new Image(getClass().getResourceAsStream(item.getIMG_PATH()))));
        itemInInventory.getChildren().add(label);
        label.setText(String.valueOf(item.getQuantity()));
        itemInInventory.setLayoutY(item.getRenderParam().getY());
        itemInInventory.setLayoutX(item.getRenderParam().getX());
        return itemInInventory;
    }
    /**
     The method updates label of this item
     **/
    public void update(){this.label.setText(String.valueOf(this.item.getQuantity()));}

}

