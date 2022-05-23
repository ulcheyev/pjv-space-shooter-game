package com.cvut.src.controller;
import com.cvut.src.managers.*;
import com.cvut.src.model.*;
import com.cvut.src.model.bonusItem.Inventory;
import com.cvut.src.model.enemy.BossType;
import com.cvut.src.model.player.ship.PlayerShip;
import com.cvut.src.view.GameView;
import com.cvut.src.view.components.MyBackground;
import com.cvut.src.view.components.MyProgressBar;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The controller class that provides communication between the model and the view
 * @author ulcheyev
 **/
public class GameController {

    private final static Logger logger = Logger.getLogger(GameController.class.getName());

    //Application Components
    private GameView gameView;
    private Scene gameScene;

    //Listeners
    private EventHandler<KeyEvent> pressed;
    private EventHandler<KeyEvent> released;
    private EventHandler<KeyEvent> winPressed;
    private EventHandler<KeyEvent> winReleased;

    //Game Components
    private Space gameSpace;
    private AudioManager theme;
    private PlayerShip choosenShip;
    private AnimationTimer gameTimer;

    //A variable for monitoring the state of the game. True - not finished yet, False - finished
    private boolean gameState = true;

    //Managers
    private SaveManager saveManager;

    /**
     * Initializing the GameController class
     * @param stage - accepts the main stage
     **/
    public GameController(Stage stage){
        logger.log(Level.INFO, "Game controller initialize");
        gameView = new GameView(this, stage);
        this.gameScene = gameView.getGameScene();
        saveManager = new SaveManager(this);

        theme = new AudioManager("theme");
        theme.setInfiniteLoop();

        logger.log(Level.INFO, "Game theme play");
        new Thread(theme).start();

        createKeyListeners();
        createGameLoop();
    }

    /**
     * The method initializes a new game
     * @param type boss type
     * @param enemiesWave  number of waves of enemies
     * @param enemiesQuantity the number of enemies in each wave
     **/

    public void createNewGame (BossType type, int enemiesWave, int enemiesQuantity){
        logger.log(Level.INFO, "New game is created");
        gameSpace = new Space(this, type, enemiesWave, enemiesQuantity);
        gameView.clearGameScreen();
        gameView.drawInventory(gameSpace.getPlayerInventory().getItems());
        gameView.setGameBackgroundImage(getSpaceBackground());
        gameView.updateBars();
        gameView.getGameScene().setOnKeyPressed(pressed);
        gameView.getGameScene().setOnKeyReleased(released);
    }

    /**
     * The method initializes a new game by parameters in JSON file
     * @param level - level of the game. Must match the data from JSON file.
     **/
    public void createNewGameParametrizedByJson(int level){
        logger.log(Level.INFO, "New game is created");
        SpaceConfigurationData data = readFromJson();
        if(level == 1){
            createNewGame(data.getBossTypeFirstLevel(), data.getEnemiesWaveFirstLevel(), data.getEnemiesQuantityFirstLevel());
        }if(level == 2){
            createNewGame(data.getBossTypeSecondLevel(), data.getEnemiesWaveSecondLevel(), data.getEnemiesQuantitySecondLevel());
        }
    }

    /**
     * The method initializes the game loop
     **/
    public void createGameLoop () {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    if(gameSpace != null){
                        gameSpace.paint(gameView.getGameGraphicsContext());
                        gameSpace.update();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * The method starts the game timer and sets the game scene and key listeners to this scene
     **/
    public void startGame(){
        logger.log(Level.INFO, "Game started");
        this.gameTimer.start();
        gameScene.setOnKeyReleased(released);
        gameScene.setOnKeyPressed(pressed);
    }
    /**
     * The method stops the game timer and remove key listeners
     **/
    public void stopGame(){
        logger.log(Level.INFO, "Game stopped");
        this.gameTimer.stop();
        gameScene.setOnKeyReleased(null);
        gameScene.setOnKeyPressed(null);
    }

    /**
     * The method sets loose game screen and clear the game world
     **/
    public void gameOver (){
        logger.log(Level.INFO, "Game over set");
        playSound("loose");
        gameView.setGameOverScreen();
        gameSpace.setPlayerInventory(null);
        gameSpace.getPlayerBullets().clear();
        gameSpace.getEnemyBullets().clear();
        gameSpace.setPlayerShip(null);
        gameScene.setOnKeyReleased(null);
        gameScene.setOnKeyPressed(null);
        gameSpace.getItems().clear();
        gameState=false;
        try {removeItemFromGamePane(gameSpace.getBoss().getHpBar().getProgressBar().getParent());
        }catch (Exception e){}
    }

    /**
     * The method sets win game screen and clear the game world
     **/
    public void gameWin(){
        logger.log(Level.INFO, "Game win set");
        playSound("win");
        gameSpace.getPlayerBullets().clear();
        gameSpace.getEnemyBullets().clear();
        removeItemFromGamePane(gameSpace.getBoss().getHpBar().getProgressBar().getParent());
        gameSpace.setBoss(null);
        gameView.setWinGameScreen();
        gameSpace.getItems().clear();
        gameSpace.setGameState(true);
        gameScene.setOnKeyReleased(winReleased);
        gameScene.setOnKeyPressed(winPressed);
    }

    /**
     * The method save game parameters : Player Ship Type, Boss Type, Item Quantity
     **/
    public void saveGame() {
        SaveData data = new SaveData();
        data.setShipType(choosenShip.getType());
        data.setBossType(gameSpace.getBoss().getType());
        data.setItems(gameSpace.getPlayerInventory().getItems());
        saveManager.save(data);
    }
    /**
     * The method load game parameters and set them
     **/
    public void loadGame(){
        SaveData data = (SaveData) saveManager.load();
        clearScreen();
        setChoosenShip(new PlayerShip(this, data.getShipType()));
        choosenShip.getInventory().setItems(data.getItems());
        createNewGame(data.getBossType(), 0 , 0);

        setGameScene();
        startGame();
        logger.log(Level.INFO, "Game loaded");
    }


    //The method create key listeners and set them
    private void createKeyListeners () {
        pressed = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.A) {
                    gameSpace.getPlayerShip().keyPressed(keyEvent);
                } else if (keyEvent.getCode() == KeyCode.D) {
                    gameSpace.getPlayerShip().keyPressed(keyEvent);
                } else if (keyEvent.getCode() == KeyCode.W) {
                    gameSpace.getPlayerShip().keyPressed(keyEvent);
                } else if (keyEvent.getCode() == KeyCode.S) {
                    gameSpace.getPlayerShip().keyPressed(keyEvent);
                } else if (keyEvent.getCode() == KeyCode.SPACE) {
                    gameSpace.getPlayerShip().keyPressed(keyEvent);
                } else if (keyEvent.getCode() == KeyCode.DIGIT1) {
                    gameSpace.getPlayerInventory().keyPressed(keyEvent);
                } else if (keyEvent.getCode() == KeyCode.DIGIT2) {
                    gameSpace.getPlayerInventory().keyPressed(keyEvent);
                } else if (keyEvent.getCode() == KeyCode.DIGIT3) {
                    gameSpace.getPlayerInventory().keyPressed(keyEvent);
                } else if (keyEvent.getCode() == KeyCode.ESCAPE){
                    gameView.setPauseScreen();
                    stopGame();
                } else if (keyEvent.getCode() == KeyCode.TAB){
                    if(getMultiShotProgress() >= 1) {
                        setAllowToFillMultiShotBar(false);
                        getMultiShotBar().resetProgressBar();
                        gameSpace.getPlayerShip().keyPressed(keyEvent);
                    }
                }
            }
        };
        gameScene.setOnKeyPressed(pressed);
        released = new EventHandler<>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.A) {
                    gameSpace.getPlayerShip().keyReleased(keyEvent);
                } else if (keyEvent.getCode() == KeyCode.D) {
                    gameSpace.getPlayerShip().keyReleased(keyEvent);
                } else if (keyEvent.getCode() == KeyCode.W) {
                    gameSpace.getPlayerShip().keyReleased(keyEvent);
                } else if (keyEvent.getCode() == KeyCode.S) {
                    gameSpace.getPlayerShip().keyReleased(keyEvent);
                }
            }

        };
        gameScene.setOnKeyReleased(released);
        winPressed = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.A) {
                    gameSpace.getPlayerShip().keyPressed(keyEvent);
                } else if (keyEvent.getCode() == KeyCode.D) {
                    gameSpace.getPlayerShip().keyPressed(keyEvent);
                } else if (keyEvent.getCode() == KeyCode.W) {
                    gameSpace.getPlayerShip().keyPressed(keyEvent);
                } else if (keyEvent.getCode() == KeyCode.S) {
                    gameSpace.getPlayerShip().keyPressed(keyEvent);
                }
            }
        };

        winReleased = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.A) {
                    gameSpace.getPlayerShip().keyReleased(keyEvent);
                } else if (keyEvent.getCode() == KeyCode.D) {
                    gameSpace.getPlayerShip().keyReleased(keyEvent);
                } else if (keyEvent.getCode() == KeyCode.W) {
                    gameSpace.getPlayerShip().keyReleased(keyEvent);
                } else if (keyEvent.getCode() == KeyCode.S) {
                    gameSpace.getPlayerShip().keyReleased(keyEvent);
                }
            }
        };
    }

    /**Resets player's ship states
     **/
    public void resetPlayerShipState(){
        choosenShip.setHealth(1);
        choosenShip.resetShield();
    }

    /**Reads from JSON file and returns readed data
     * @return readed data from JSON file
     **/
    public SpaceConfigurationData readFromJson(){
        JsonManager jsonManager = new JsonManager(this);
        jsonManager.parseJSON();
        return jsonManager.getData();
    }

    /**Resets player's ship inventory
     **/
    public void resetPlayerShipInventory(){
        choosenShip.getInventory().resetItemsQuantity();
    }


    //PROGRESS BAR GETTERS/SETTERS

    /**Sets progress to the game pane
     * @param bar Bar to set
     **/
    public void setBarOnPane(MyProgressBar bar){gameView.setBarOnPane(bar);}

    //HPBAR

    /**Returns the player ship hp progress bar
     * @return Player ship hp progress bar
     **/
    public MyProgressBar getHPBar(){return gameView.getHpBar();}

    /**Sets progress to the player ship hp progress bar
     * @param progress Number of progress
     **/
    public void setHPBarProgress(double progress){gameView.getHpBar().setProgressToBar(progress);}

    //MULTI SHOT BAR

    /**Returns the player ship multi shot progress bar
     * @return Player ship multi shot progress bar
     **/
    public MyProgressBar getMultiShotBar() {return gameView.getMultiShotBar();}


    /**Sets progress to the player ship multi shot progress bar
     *@param progress Number of progress
     **/
    public void setMultiShotProgress(double progress){gameView.getMultiShotBar().setProgressToBar(progress);}

    /**Returns progress from multi shot progress bar
     *@return  Number of progress
     **/
    public double getMultiShotProgress(){ return gameView.getMultiShotBar().getProgress();}

    /**Returns allow to fill multi shot progress bar
     *@return  true - allow filling, false - not allow filling
     **/
    public boolean getAllowToFillMultiShotBar(){return gameView.isAllowToFillMultiShotBar();}

    /**Sets allow to fill multi shot progress bar
     *@param allow  true - allow filling, false - not allow filling
    **/
    public void setAllowToFillMultiShotBar(boolean allow){gameView.setAllowToFillMultiShotBar(allow);}


    //MENU GETTERS AND SETTERS

    /**Sets main menu on the main stage**/
    public void setMainMenu(){gameView.setMainMenu();}

    /**Sets my ship menu on the main stage**/
    public void setMyShipMenu(){gameView.setMyShipMenu();}

    /**Returns main stage
     * @return Main stage
    **/
    public Stage getMainStage() {return gameView.getMainStage();}

    /**Sets game scene on the main stage**/
    public void setGameScene(){gameView.getMainStage().setScene(gameView.getGameScene());}

    /**Clears game scene**/
    public void clearScreen(){gameView.clearGameScreen();}

    /**Shows error message with set text
     * @param text - text to show
     * **/
    public void showErrorMessage(String text){gameView.showErrorMessage(text);}

    public void showMessageOnPane(String text){gameView.showMessageOnPane(text);}

    /**Removes item from game pane
     * @param node - item to remove
     * **/
    public void removeItemFromGamePane(Node node){gameView.removeItemFromGamePane(node);}


    /**Plays the sound
     * @param name  name of sound
     * ***/
    public void playSound(String name){new Thread(new AudioManager(name)).start();}

    /**Update player inventory**/
    public void updateInventoryItems(){gameView.updateInventoryItemsLabels();}

    /**Returns Space background
     * @return - space background
     * **/
    public MyBackground getSpaceBackground(){return gameSpace.getBackground();}

    /** Sets ship
     * @param ship - ship to set
     * **/
    public void setChoosenShip(PlayerShip ship) {this.choosenShip = ship;}

    /** Returns player ship inventory
     * @return  player ship inventory
     * **/
    public Inventory getPlayerInventory(){return gameSpace.getPlayerShip().getInventory();}

    /** Returns game space
     * @return  game space
     * **/
    public Space getGameSpace() {return gameSpace;}

    /** Sets game space
     * @param space - game space to set
     * **/
    public void setGameSpace(Space space) {this.gameSpace = space;}

    /** Returns selected ship game space
     * @return selected ship
     * **/
    public PlayerShip getChoosenShip() {return choosenShip;}

    /** Returns game level
     * @return game level
     * **/
    public int getSpaceLevel(){
        return gameSpace.getLevel();
    }

    /** Returns a boolean value meaning the file is empty
     * @return a boolean value: true - file is empty, false - not empty
     * **/
    public boolean isSavesFileEmpty(){
        return saveManager.isEmpty();
    }

    /**Increases the level of the game
     **/
    public void gameLevelUp(){
        gameSpace.levelUp();
    }

}
