package com.cvut.src.view.components;

import com.cvut.src.controller.GameController;
import com.cvut.src.managers.SpaceConfigurationData;
import com.cvut.src.model.enemy.BossType;
import com.cvut.src.model.player.ship.PlayerShip;

import com.cvut.src.model.player.ship.ShipType;
import com.cvut.src.view.GameView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Component of VIEW which representing a my ship menu.
 * @author ulcheyev
 **/
public class MyShipMenu {
    private final static Logger logger = Logger.getLogger(MyShipMenu.class.getName());

    private GameController controller;
    private AnchorPane myShipPane;

    private VBox firstBox;
    private VBox secondBox;
    private boolean choosen;

    private Scene myShipScene;
    private Image backGroundImage;


    /**
     * Initialize my ship menu. Draws my ship menu components.
     * @param controller game controller
     * @param stage main stage of the game
     **/
    public MyShipMenu(GameController controller, Stage stage){
        logger.log(Level.INFO, "My ship menu initialize");
        this.controller = controller;
        myShipPane = new AnchorPane();
        myShipScene = new Scene(myShipPane, GameView.GAME_WIDTH, GameView.GAME_HEIGHT);
        this.backGroundImage = new Image(getClass().getResourceAsStream("/menu_res/my_ship/background.jpg"));

        Image firstImage = new Image(getClass().getResourceAsStream("/menu_res/my_ship/first.png"));
        Image secondImage = new Image(getClass().getResourceAsStream("/menu_res/my_ship/second.png"));
        Image logoImage = new Image(getClass().getResourceAsStream("/menu_res/my_ship/choose_logo.png"));

        drawBackgroundOnPane(myShipPane, backGroundImage);
        createBackButton();
        createPlayButton();
        setLogo(logoImage, 32, 50);

        firstBox = createChoosenPaneShipN1(firstImage, 50, 350);
        secondBox = createChoosenPaneShipN2(secondImage, 620, 350);
    }

    /**
     * Draws a background with a image on a certain pane
     * @param pane pane, where background will be drawn
     * @param back background to draw
     **/
    public void drawBackgroundOnPane(AnchorPane pane, Image back){
        BackgroundImage backGroundImage = new BackgroundImage(back,  BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backGroundImage);
        pane.setBackground(background);
    }

    private void createBackButton(){
        MyButton backButton = new MyButton("Back", 5, 890);
        backButton.setLayoutX(backButton.getPosition().getX());
        backButton.setLayoutY(backButton.getPosition().getY());
        backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                controller.setMainMenu();
            }
        });
        myShipPane.getChildren().add(backButton);
    }


    private void createPlayButton(){
        MyButton nextButton = new MyButton("Play!", 645, 890);
        nextButton.setLayoutX(nextButton.getPosition().getX());
        nextButton.setLayoutY(nextButton.getPosition().getY());
        nextButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(choosen) {
                    removeBorder(firstBox);
                    removeBorder(secondBox);
                    controller.clearScreen();
                    controller.resetPlayerShipInventory();
                    controller.createNewGameParametrizedByJson(1);
                    controller.setGameScene();
                    controller.startGame();

                }else{
                    controller.showErrorMessage("Choose a ship!");
                }
            }
        });
        myShipPane.getChildren().add(nextButton);
    }



    private VBox createChoosenPaneShipN1(Image image, double x, double y){
        VBox box = new VBox();
        ImageView view = new ImageView(image);
        box.getChildren().add(view);
        myShipPane.getChildren().add(box);
        box.setLayoutX(x);
        box.setLayoutY(y);
        box.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setBorder(box);
                choosen = true;
                removeBorder(secondBox);
                controller.setChoosenShip(new PlayerShip(controller, ShipType.ALIEN));
            }
        });
        return box;
    }

    private VBox createChoosenPaneShipN2(Image image, double x, double y){
        VBox box = new VBox();
        ImageView view = new ImageView(image);
        box.getChildren().add(view);
        myShipPane.getChildren().add(box);
        box.setLayoutX(x);
        box.setLayoutY(y);
        box.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setBorder(box);
                choosen = true;
                removeBorder(firstBox);
                controller.setChoosenShip(new PlayerShip(controller, ShipType.SHARK));
            }
        });
        return box;

    }

    private void setBorder(VBox box){
        box.setStyle("-fx-border-style: solid inside;" +
                "-fx-border-color: orange;" +
                "-fx-border-width: 3;");
    }

    private void removeBorder(VBox box){
        box.setStyle(null);
    }


    private void setLogo(Image image, double posX, double posY){
        ImageView view = new ImageView(image);
        myShipPane.getChildren().add(view);
        view.setLayoutX(posX);
        view.setLayoutY(posY);
    }

    /**
     * Returns my ship menu scene
     * @return my ship menu scene
     **/
    public Scene getMyShipScene() {
        return myShipScene;
    }

}
