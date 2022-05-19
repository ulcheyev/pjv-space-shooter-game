package com.cvut.src.view;
import com.cvut.src.controller.GameController;
import com.cvut.src.model.bonusItem.Item;
import com.cvut.src.model.enemy.Boss;
import com.cvut.src.model.enemy.BossType;
import com.cvut.src.view.components.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameView{
    private final static Logger logger = Logger.getLogger(GameView.class.getName());

    public static final int GAME_HEIGHT = 1000;
    public static final int GAME_WIDTH = 1000;


    //Graphic components
    private Stage mainStage;
    protected Scene gameScene;
    protected BorderPane gamePane;
    protected Canvas gameCanvas;

    public Canvas getGameCanvas() {
        return gameCanvas;
    }

    protected GraphicsContext gameGraphicsContext;


    protected GameController controller;

    private MyProgressBar hpBar;
    private ArrayList<InventoryItemView> inventoryItems;

    public GameView() {
    }

    public Stage getMainStage() {
        return mainStage;
    }



    protected MyProgressBar multiShotBar;
    protected boolean allowToFillLaserBar = true;

    protected ImageView backgroundView1;
    protected ImageView backgroundView2;


    //Screens
    protected Group gameWinScreen;
    protected Group gameLoseScreen;
    protected Group pauseGameScreen;


    //Menu Elements
    protected MainMenu mainMenu;
    private MyShipMenu myShipMenu;


    public  Pane getGamePane() {return gamePane;}
    public Scene getGameScene() {return gameScene;}
    public boolean isAllowToFillLaserBar() {return allowToFillLaserBar;}
    public void setAllowToFillLaserBar(boolean allowToFillLaserBar) {this.allowToFillLaserBar = allowToFillLaserBar;}
    public MyProgressBar getHpBar() {return hpBar;}
    public MyProgressBar getMultiShotBar() {return multiShotBar;}
    public GraphicsContext getGameGraphicsContext() {return gameGraphicsContext;}
    public GameController getController() {return controller;}
    public void setController(GameController controller) {this.controller = controller;}


    public GameView(GameController controller, Stage stage){
        logger.log(Level.INFO, "Game view initialize");
        this.controller = controller;
        this.mainStage = stage;
        gameCanvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
        gameGraphicsContext = gameCanvas.getGraphicsContext2D();
        gamePane = new BorderPane();
        gamePane.getChildren().add(gameCanvas);
        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        inventoryItems = new ArrayList<>();

        setStageProperties(mainStage);
        createGameOverScreen();
        createPauseGameScreen();
        createWinGameScreen();
        createHPProgressBar();
        createMultiShotProgressBar();

        mainMenu = new MainMenu(controller, stage);
        myShipMenu = new MyShipMenu(controller, stage);

        setMainMenu();
    }

    public void setMainMenu(){
        mainStage.setScene(mainMenu.getMenuScene());
    }


    private void setStageProperties(Stage stage){
        //STAGE PROPERTIES
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        stage.setTitle("Space Hunter v1");
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/logo.png")));
    }

    public void removeItemFromGamePane(Node node){
        gamePane.getChildren().remove(node);
    }

    public void setGameBackgroundImage(MyBackground background){
        // cherniy po bokam
        gamePane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        backgroundView1 = background.getViewImgN1();
        backgroundView2 = background.getViewImgN2();
        backgroundView1.setLayoutY(0);
        backgroundView2.setLayoutY(backgroundView1.getFitHeight());
    }


    public void createGameOverScreen(){
        Image gameOverImg = new Image(getClass().getResourceAsStream("/menu_res/lose.png"));
        ImageView view = new ImageView(gameOverImg);
        MyButton exitButton = createExitButton();
        MyButton playAgainButton = createPlayAgainButton();

        exitButton.setLayoutY(150);
        playAgainButton.setLayoutY(80);
        exitButton.setLayoutX(50);
        playAgainButton.setLayoutX(50);

        gameLoseScreen = new Group();
        gameLoseScreen.getChildren().add(playAgainButton);
        gameLoseScreen.getChildren().add(exitButton);
        gameLoseScreen.getChildren().add(view);
    }

    public void setBarOnPane(MyProgressBar bar){
        Group group = new Group();
        group.getChildren().add(bar.getProgressBar());
        gamePane.getChildren().add(group);
    }

    public void createHPProgressBar(){
        Group group = new Group();
        hpBar = new MyProgressBar(controller, 10, 870);
        hpBar.setProgressBarBackGroundColor("black");
        hpBar.setProgressBarHueColor(2);
        hpBar.setProgressBarVertical();
        hpBar.setProgressToBar(1);
        group.getChildren().add(hpBar.getProgressBar());
        gamePane.getChildren().add(group);
    }

    public void createMultiShotProgressBar(){
        Group group = new Group();
        multiShotBar = new MyProgressBar(controller, 40, 870);
        multiShotBar.setProgressBarBackGroundColor("black");
        multiShotBar.setProgressBarHueColor(0.310);
        multiShotBar.setProgressBarVertical();
        multiShotBar.setProgressToBar(1);
        group.getChildren().add(multiShotBar.getProgressBar());
        gamePane.getChildren().add(group);
    }

    public void createWinGameScreen() {
        Image gameWinImg = new Image(getClass().getResourceAsStream("/menu_res/win.png"));
        ImageView view = new ImageView(gameWinImg);
        MyButton nextButton = createNextButton();
        MyButton mainMenuButtonButton = createMainMenuButton();
        mainMenuButtonButton.setLayoutX(27);
        mainMenuButtonButton.setLayoutY(135);
        gameWinScreen = new Group();
        gameWinScreen.getChildren().add(view);
        gameWinScreen.getChildren().add(mainMenuButtonButton);
        gameWinScreen.getChildren().add(nextButton);
    }

    public void createPauseGameScreen() {
        Image pauseGame = new Image(getClass().getResourceAsStream("/menu_res/pause.png"));
        ImageView view = new ImageView(pauseGame);
        MyButton contiueButton = createContinueButton();
        MyButton mainMenuButtonButton = createMainMenuButton();
        view.setLayoutX(27);
        contiueButton.setLayoutY(80);
        pauseGameScreen = new Group();
        pauseGameScreen.getChildren().add(view);
        pauseGameScreen.getChildren().add(contiueButton);
        pauseGameScreen.getChildren().add(mainMenuButtonButton);
    }

    public void clearGameScreen(){
        try {
            gamePane.getChildren().remove(gameLoseScreen);
            gamePane.getChildren().remove(gameWinScreen);
            gamePane.getChildren().remove(pauseGameScreen);
            gamePane.getChildren().remove(hpBar);
            gamePane.getChildren().remove(multiShotBar);
        }catch (Exception e){logger.log(Level.INFO, "Clear game screen exception caught");}

    }
    public void setGameOverScreen(){gamePane.setCenter(gameLoseScreen);}

    public void setWinGameScreen(){
        gamePane.setCenter(gameWinScreen);
    }

    public void setPauseScreen(){
        gamePane.setCenter(pauseGameScreen);
    }

    public void removePauseScreen(){
        gamePane.getChildren().remove(pauseGameScreen);
    }

    public void removeWinScreen(){
        gamePane.getChildren().remove(gameWinScreen);
    }

    public void removeLoseScreen(){
        gamePane.getChildren().remove(gameLoseScreen);
    }

    public void setMyShipMenu() {mainStage.setScene(myShipMenu.getMyShipScene());}

    public MyButton createExitButton() {
        MyButton exitButton = new MyButton("Exit game", 500, 150);
        exitButton.setLayoutY(70);
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                logger.log(Level.INFO, "Game close");
                controller.getMainStage().close();
                Platform.exit();
                System.exit(0);
            }
        });
        return exitButton;
    }

    public MyButton createPlayAgainButton() {
        MyButton playAgainButton = new MyButton("Play again", 500, 250);
        playAgainButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                removeLoseScreen();
                controller.setGameSpace(null);
                controller.createNewGame(BossType.PREDATOR, 0 , 0);
                controller.startGame();
            }
        });
        return playAgainButton;
    }

    public MyButton createContinueButton() {
        MyButton continueButton = new MyButton("Continue", 0, 0);
        continueButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                removeLoseScreen();
                controller.startGame();
                removePauseScreen();
            }
        });
        return continueButton;
    }

    public void showErrorMessage(String text){
        logger.log(Level.INFO, "Error message showed");
        Alert.AlertType type = Alert.AlertType.WARNING;
        Alert alert = new Alert(type, "");

        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(controller.getMainStage());

        alert.getDialogPane().setContentText(text);
        alert.getDialogPane().setStyle("-fx-background-color: orange;");

        alert.showAndWait();
    }



    public MyButton createNextButton(){
        MyButton nextButton = new MyButton("Next", 500, 150);
        nextButton.setLayoutY(70);
        nextButton.setLayoutX(27);
        nextButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
               controller.clearScreen();
               controller.createNewGame(BossType.TITAN, 1 ,10);
            }
        });
        return nextButton;
    }

    public MyButton createMainMenuButton(){
        MyButton mainMenuButton = new MyButton("Main Menu", 0, 0);
        mainMenuButton.setLayoutY(150);
        mainMenuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
              mainStage.setScene(mainMenu.getMenuScene());
              removePauseScreen();
            }
        });
        return mainMenuButton;
    }

    public void updateBars(){
        hpBar.setProgressToBar(1);
        multiShotBar.setProgressToBar(0);
    }


    public void drawInventory(ArrayList<Item> items){
        Group inventory = new Group();
        for(int i = 0; i < items.size(); i++){
            InventoryItemView itemView = new InventoryItemView(items.get(i), controller);
            inventory.getChildren().add(itemView.makeInventoryItem());
            inventoryItems.add(itemView);
        }
        gamePane.getChildren().add(inventory);
    }


    public void updateInventoryItemsLabels(){
        for(int i = 0; i < inventoryItems.size(); i++){
            inventoryItems.get(i).update();
        }
    }









}
