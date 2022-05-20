package com.cvut.src.view;
import com.cvut.src.controller.GameController;
import com.cvut.src.model.bonusItem.Item;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class responsible for creating a graphical user interface.
 * @author ulcheyev
 **/
public class GameView{
    private final static Logger logger = Logger.getLogger(GameView.class.getName());
    public static final int GAME_HEIGHT = 1000;
    public static final int GAME_WIDTH = 1000;
    protected GameController controller;

    //Graphic components
    private Stage mainStage;
    protected Scene gameScene;
    protected BorderPane gamePane;
    protected Canvas gameCanvas;
    protected GraphicsContext gameGraphicsContext;
    private MyProgressBar hpBar;
    protected MyProgressBar multiShotBar;
    protected ImageView backgroundView1;
    protected ImageView backgroundView2;

    private ArrayList<InventoryItemView> inventoryItems;
    protected boolean allowToFillLaserBar = true;

    //Screens
    protected Group gameWinScreen;
    protected Group gameLoseScreen;
    protected Group pauseGameScreen;

    //Menu
    protected MainMenu mainMenu;
    private MyShipMenu myShipMenu;


    /**
     * Initializing the GameView class. Creates buttons, main game pane, scene, canvas.
     * @param controller Game Controller
     * @param stage Main stage of the game
     **/
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



    //Setting up the main window
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


    /**
     * Sets the background for the game map (Space).
     * @param background background with image to set
     **/
    public void setGameBackgroundImage(MyBackground background){
        // cherniy po bokam
        gamePane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        backgroundView1 = background.getViewImgN1();
        backgroundView2 = background.getViewImgN2();
        backgroundView1.setLayoutY(0);
        backgroundView2.setLayoutY(backgroundView1.getFitHeight());
    }

    //Create Game Over Screen
    private void createGameOverScreen(){
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

    /**
     * Sets the node bar to the main pane.
     * @param bar progress bar to set
     **/
    public void setBarOnPane(MyProgressBar bar){
        Group group = new Group();
        group.getChildren().add(bar.getProgressBar());
        gamePane.getChildren().add(group);
    }

    //Creates HP progress bar and set it on game pane
    private void createHPProgressBar(){
        Group group = new Group();
        hpBar = new MyProgressBar(controller, 10, 870);
        hpBar.setProgressBarBackGroundColor("black");
        hpBar.setProgressBarHueColor(2);
        hpBar.setProgressBarVertical();
        hpBar.setProgressToBar(1);
        group.getChildren().add(hpBar.getProgressBar());
        gamePane.getChildren().add(group);
    }

    //Creates Multi shot progress bar and set it on game pane
    private void createMultiShotProgressBar(){
        Group group = new Group();
        multiShotBar = new MyProgressBar(controller, 40, 870);
        multiShotBar.setProgressBarBackGroundColor("black");
        multiShotBar.setProgressBarHueColor(0.310);
        multiShotBar.setProgressBarVertical();
        multiShotBar.setProgressToBar(1);
        group.getChildren().add(multiShotBar.getProgressBar());
        gamePane.getChildren().add(group);
    }

    //Creates Win Game Screen
    private void createWinGameScreen() {
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

    //Creates Pause Game Screen
    private void createPauseGameScreen() {
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

    /**
     * Clear game screen from other screens (pause, win...) and bars
     **/
    public void clearGameScreen(){
        try {
            gamePane.getChildren().remove(gameLoseScreen);
            gamePane.getChildren().remove(gameWinScreen);
            gamePane.getChildren().remove(pauseGameScreen);
            gamePane.getChildren().remove(hpBar);
            gamePane.getChildren().remove(multiShotBar);
        }catch (Exception e){logger.log(Level.INFO, "Clear game screen exception caught");}

    }

    //Creates Main menu exit button
    private MyButton createExitButton() {
        MyButton exitButton = new MyButton("Exit game", 500, 150);
        exitButton.setLayoutY(70);
        exitButton.setOnMouseClicked(new EventHandler<MouseEvent>()  {
            @Override
            public void handle(MouseEvent event) {
                logger.log(Level.INFO, "Game close");
                controller.getMainStage().close();
                Platform.exit();
                System.exit(0);
            }
        });
        return exitButton;
    }

    //Creates loose screen play again button
    private MyButton createPlayAgainButton() {
        MyButton playAgainButton = new MyButton("Play again", 500, 250);
        playAgainButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                removeLoseScreen();
                controller.resetPlayerShipState();
                controller.createNewGame(1 , 1, 1);
//                controller.setGameSpace(null);
                controller.startGame();
            }
        });
        return playAgainButton;
    }

    //Creates win screen continue button
    private MyButton createContinueButton() {
        MyButton continueButton = new MyButton("Continue", 0, 0);
        continueButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                removeLoseScreen();
                controller.startGame();
                removePauseScreen();
            }
        });
        return continueButton;
    }

    /**
     * Shows error message with text
     * @param text to show in error message
     **/
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

    //Creates next button
    private MyButton createNextButton(){
        MyButton nextButton = new MyButton("Next", 500, 150);
        nextButton.setLayoutY(70);
        nextButton.setLayoutX(27);
        nextButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                controller.clearScreen();
                controller.resetPlayerShipState();
                controller.createNewGame(BossType.TITAN, 1 ,10);
            }
        });
        return nextButton;
    }


    //Creates button which opens main menu
    private MyButton createMainMenuButton(){
        MyButton mainMenuButton = new MyButton("Main Menu", 0, 0);
        mainMenuButton.setLayoutY(150);
        mainMenuButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mainStage.setScene(mainMenu.getMenuScene());
                clearGameScreen();
            }
        });
        return mainMenuButton;
    }


    /**
     * Updating states of bars (Hp, Multi shot)
     **/
    public void updateBars(){
        hpBar.setProgressToBar(1);
        multiShotBar.setProgressToBar(0);
    }

    /**
     * Draw inventory on game pane with given list of items
     * @param items items to draw
     **/
    public void drawInventory(ArrayList<Item> items){
        Group inventory = new Group();
        for(int i = 0; i < items.size(); i++){
            InventoryItemView itemView = new InventoryItemView(items.get(i), controller);
            inventory.getChildren().add(itemView.makeInventoryItem());
            inventoryItems.add(itemView);
        }
        gamePane.getChildren().add(inventory);
    }

    /**
     * Updating quantity labels of items in inventory
     **/
    public void updateInventoryItemsLabels(){
        for(int i = 0; i < inventoryItems.size(); i++){
            inventoryItems.get(i).update();
        }
    }

    //SCREENS SET/REMOVES

    /**
     *Sets game over screen on game pane
     **/
    public void setGameOverScreen(){gamePane.setCenter(gameLoseScreen);}

    /**
     *Sets win game screen on game pane
     **/
    public void setWinGameScreen(){
        gamePane.setCenter(gameWinScreen);
    }

    /**
     *Sets pause game screen on game pane
     **/
    public void setPauseScreen(){
        gamePane.setCenter(pauseGameScreen);
    }

    /**
     *Removes pause screen from game pane
     **/
    public void removePauseScreen(){gamePane.getChildren().remove(pauseGameScreen);}

    /**
     *Removes win game screen from game pane
     **/
    public void removeWinScreen(){
        gamePane.getChildren().remove(gameWinScreen);
    }

    /**
     *Removes game over screen from game pane
     **/
    public void removeLoseScreen(){
        gamePane.getChildren().remove(gameLoseScreen);
    }

    /**
     *Sets my ship menu to game scene
     **/
    public void setMyShipMenu() {mainStage.setScene(myShipMenu.getMyShipScene());}

    //BARS GETTERS/SETTERS

    /**Returns the player ship hp progress bar
     * @return Player ship hp progress bar
     **/
    public MyProgressBar getHpBar() {return hpBar;}

    /**Returns the player ship multi shot progress bar
     * @return Player ship multi shot progress bar
     **/
    public MyProgressBar getMultiShotBar() {return multiShotBar;}

    /**Returns allow to fill multi shot progress bar
     *@return  true - allow filling, false - not allow filling
     **/
    public boolean isAllowToFillMultiShotBar() {return allowToFillLaserBar;}


    /**Sets allow to fill multi shot progress bar
     *@param allowToFillLaserBar  true - allow filling, false - not allow filling
     **/
    public void setAllowToFillMultiShotBar(boolean allowToFillLaserBar) {this.allowToFillLaserBar = allowToFillLaserBar;}

    /**Returns game pane
     *@return game pane
     **/
    public  Pane getGamePane() {return gamePane;}

    /**Returns game scene
     *@return game scene
     **/
    public Scene getGameScene() {return gameScene;}

    /**Returns game scene graphics context
     *@return game scene graphics context
     **/
    public GraphicsContext getGameGraphicsContext() {return gameGraphicsContext;}

    /**Returns game controller
     *@return Game Controller
     **/
    public GameController getController() {return controller;}

    /**Sets game controller to game view
     *@param controller game controllet to set
     **/
    public void setController(GameController controller) {this.controller = controller;}

    /**Returns main stage of the game
     *@return main stage of the game
     **/
    public Stage getMainStage() {
        return mainStage;
    }

    /**Sets main menu on game scene**/
    public void setMainMenu(){mainStage.setScene(mainMenu.getMenuScene());}

    /**Removes item from game pane
     *@param node node item to remove from game pane
     **/
    public void removeItemFromGamePane(Node node){
        gamePane.getChildren().remove(node);
    }








}
