package com.cvut.src.view.components;
import com.cvut.src.controller.GameController;
import com.cvut.src.managers.SaveData;
import com.cvut.src.managers.SaveManager;
import com.cvut.src.model.enemy.BossType;
import com.cvut.src.view.GameView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;


public class MainMenu{
    private final static Logger logger = Logger.getLogger(MainMenu.class.getName());

    private GameController controller;
    private Stage mainStage;
    private AnchorPane menuPane;
    private Scene menuScene;
    private Image backGroundImage;
    private Image logo;


    public MainMenu(GameController controller, Stage stage){
        logger.log(Level.INFO, "Main menu initialize");
        this.controller = controller;
        this.logo = new Image(getClass().getResourceAsStream("/menu_res/logo_menu.png"));
        this.backGroundImage = new Image(getClass().getResourceAsStream("/menu_res/menu.png"));
        this.menuPane = new AnchorPane();
        this.menuScene = new Scene(menuPane, GameView.GAME_WIDTH, GameView.GAME_HEIGHT);
        this.mainStage = stage;

        drawBackgroundOnPane(menuPane, this.backGroundImage);
        drawLogoOnPane(menuPane, this.logo, 50, 300);
        createStartButton();
        createLoadButton();
        createMyShipButton();
        createMenuExitButton();
    }


    public void drawBackgroundOnPane(AnchorPane pane, Image back){
        BackgroundImage backGroundImage = new BackgroundImage(back,  BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backGroundImage);
        pane.setBackground(background);
    }

    public void drawButtonOnPane(AnchorPane pane, Button button, int posX, int posY){
        pane.getChildren().add(button);
        button.setLayoutX(posX);
        button.setLayoutY(posY);
    }

    public void drawLogoOnPane(AnchorPane pane, Image logo, int posX, int posY){
        ImageView view = new ImageView(logo);
        pane.getChildren().add(view);
        view.setLayoutX(posX);
        view.setLayoutY(posY);
    }

    public void createLoadButton(){
        MyButton loadButton = new MyButton("Load game", 50, 760);
        loadButton.setLayoutX(loadButton.getPosition().getX());
        loadButton.setLayoutY(loadButton.getPosition().getY());

        loadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(SaveManager.isEmpty() == false) {
                    controller.loadGame();
                }else {
                    controller.showErrorMessage("No saves found :(");
                }

            }
        });
        menuPane.getChildren().add(loadButton);
    }




   public void createStartButton(){
        MyButton startButton = new MyButton("Start game", 50, 690);
        startButton.setLayoutX(startButton.getPosition().getX());
        startButton.setLayoutY(startButton.getPosition().getY());
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(controller.getChoosenShip() == null){
                    controller.setMyShipMenu();
                }else{
                    controller.clearScreen();
                    controller.createNewGame(BossType.TITAN, 1 ,10);
                    controller.setGameScene();
//                    controller.setPlayerShip(controller.getChoosenShip());
                    controller.startGame();
                }
            }
        });
        menuPane.getChildren().add(startButton);
   }



    public void createMyShipButton(){
        MyButton myShipButton = new MyButton("My ship", 50, 830);
        myShipButton.setLayoutX(myShipButton.getPosition().getX());
        myShipButton.setLayoutY(myShipButton.getPosition().getY());

        myShipButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.setMyShipMenu();
            }
        });
        menuPane.getChildren().add(myShipButton);
    }

    public void createMenuExitButton(){
        MyButton exitButton = new MyButton("Exit game", 50, 900);
        exitButton.setLayoutX(exitButton.getPosition().getX());

        exitButton.setLayoutY(exitButton.getPosition().getY());
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                logger.log(Level.INFO, "Game close");
                mainStage.close();
                Platform.exit();
                System.exit(0);
            }
        });
        menuPane.getChildren().add(exitButton);

    }

    public Scene getMenuScene() {return menuScene;}
}
