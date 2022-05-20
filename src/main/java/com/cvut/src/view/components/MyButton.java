package com.cvut.src.view.components;

import com.cvut.src.view.components.Renderparam;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
/**
 * Component of VIEW which representing button.
 * @author ulcheyev
 **/
public class MyButton extends Button {

    private final String FONT_PATH = "/menu_res/Prototype.ttf";
    private final String IMG_PATH = "/menu_res/button.png";
    private Renderparam position;
    private Image img = new Image(getClass().getResourceAsStream(IMG_PATH));
    BackgroundImage backGroundImage = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT,
                                          BackgroundRepeat.NO_REPEAT,
                                          BackgroundPosition.DEFAULT,
                                          BackgroundSize.DEFAULT);
    private Background background = new Background(backGroundImage);

    /**
     * Button initialize
     * @param text text on button
     * @param x x coordinate
     * @param y y coordinate
     **/
    public MyButton(String text, int x, int y){
        position = new Renderparam(x, y);
        setText(text);
        setPrefWidth(350);
        setPrefHeight(70);
        setButtonFont();
        setBackground(background);
        setStyle("-fx-text-fill: white");
        setButtonFont();
        initiaLizeButtonListener();
        setAlignment(Pos.TOP_CENTER);
    }

    private void setButtonFont(){
        try{
            setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 30));
        }catch (Exception e){
            setFont(Font.font("Verdana", 30));
        }
    }

    private void initiaLizeButtonListener(){
        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setStyle("-fx-text-fill: #6B60FC");
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setStyle("-fx-text-fill: white");
            }
        });
    }

    /**
     * Returns button position
     * @return button position
     **/
    public Renderparam getPosition() {return position;}

}
