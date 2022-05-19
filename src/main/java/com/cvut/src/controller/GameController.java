package com.cvut.src.controller;
import com.cvut.src.managers.AudioManager;
import com.cvut.src.managers.SaveData;
import com.cvut.src.managers.SaveManager;
import com.cvut.src.model.*;
import com.cvut.src.model.bonusItem.Inventory;
import com.cvut.src.model.bonusItem.Item;
import com.cvut.src.model.enemy.Boss;
import com.cvut.src.model.enemy.BossType;
import com.cvut.src.model.player.ship.PlayerShip;
import com.cvut.src.view.GameView;
import com.cvut.src.view.components.MyBackground;
import com.cvut.src.view.components.MyProgressBar;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameController {
    private final static Logger logger = Logger.getLogger(GameController.class.getName());

    //Application Components
    private GameView gameView;
    private Scene gameScene;


    //listeners
    private EventHandler<KeyEvent> pressed;
    private EventHandler<KeyEvent> released;

    private EventHandler<KeyEvent> winPressed;
    private EventHandler<KeyEvent> winReleased;

    //game
    private Space gameSpace;
    private AudioManager theme;
    private PlayerShip choosenShip;
    private AnimationTimer gameTimer;
    private boolean gameState = true; //hra jeshe idet



    public GameController(Stage stage){
        logger.log(Level.INFO, "Game controller initialize");
        gameView = new GameView(this, stage);
        this.gameScene = gameView.getGameScene();

        theme = new AudioManager("theme");
        theme.setInfiniteLoop();

        logger.log(Level.INFO, "Game theme play");
        new Thread(theme).start();


        createKeyListeners();
        createGameLoop();
    }


    public void createNewGame (BossType boss, int enemiesWave, int enemiesQuantity){
        logger.log(Level.INFO, "New game is created");
        gameSpace = new Space(this, boss, enemiesWave , enemiesQuantity);
        gameView.drawInventory(gameSpace.getPlayerInventory().getItems());
        gameView.setGameBackgroundImage(getSpaceBackground());
        gameView.updateBars();
    }


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


    public void playSound(String name){new Thread(new AudioManager(name)).start();}
    public void updateInventoryItems(){
        gameView.updateInventoryItemsLabels();
    }
    public boolean getAllowToFillMultiShotBar(){return gameView.isAllowToFillLaserBar();}
    public void setAllowToFillMultiShotBar(boolean allow){gameView.setAllowToFillLaserBar(allow);}


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

    public void stopGame(){
        logger.log(Level.INFO, "Game stopped");
        this.gameTimer.stop();
        gameScene.setOnKeyReleased(null);
        gameScene.setOnKeyPressed(null);
    }

    public void startGame(){
        logger.log(Level.INFO, "Game started");
        this.gameTimer.start();
        gameScene.setOnKeyReleased(released);
        gameScene.setOnKeyPressed(pressed);
    }

    public void setMainMenu(){gameView.setMainMenu();}
    public GraphicsContext getMainGraphicContext(){
        return gameView.getGameGraphicsContext();
    }

    public MyBackground getSpaceBackground(){return gameSpace.getBackground();}

    public void setGameScene(){gameView.getMainStage().setScene(gameView.getGameScene());}
    public Pane getGamePane(){return gameView.getGamePane();}

    public void setHPBarProgress(double progress){gameView.getHpBar().setProgressToBar(progress);}
    public void setMultiShotProgress(double progress){gameView.getMultiShotBar().setProgressToBar(progress);}

    public double getMultiShotProgress(){ return gameView.getMultiShotBar().getProgress();}
    public MyProgressBar getMultiShotBar() {return gameView.getMultiShotBar();}
    public MyProgressBar getHPBar(){return gameView.getHpBar();}


//    public void setPlayerShip(PlayerShip ship){
//        this.choosenShip = ship;
//    }

    public void setBarOnPane(MyProgressBar bar){
        gameView.setBarOnPane(bar);
    }

    public void removeItemFromGamePane(Node node){
        gameView.removeItemFromGamePane(node);
    }

    public void setMyShipMenu(){
        gameView.setMyShipMenu();
    }

    public void setChoosenShip(PlayerShip ship) {this.choosenShip = ship;}

    public Inventory getPlayerInventory(){return gameSpace.getPlayerShip().getInventory();}

    public void showErrorMessage(String text){
        gameView.showErrorMessage(text);
    }

    public void saveGame(){
        SaveData data = new SaveData();
        data.shipType = choosenShip.getType();
        data.bossType = gameSpace.getBoss().getType();
        data.items = gameSpace.getPlayerInventory().getItems();
        data.inventory = getPlayerInventory();
        SaveManager.save(data);
    }

    public void loadGame(){
        SaveData data = (SaveData) SaveManager.load();
        clearScreen();
        setChoosenShip(new PlayerShip(this, data.shipType));
        choosenShip.getInventory().setItems(data.items);
        createNewGame(data.bossType, 0 , 0);

        setGameScene();
        startGame();
        logger.log(Level.INFO, "Game loaded");
    }

    public void clearScreen(){
        gameView.clearGameScreen();
    }


    public void drawInventory(ArrayList<Item> items){
        gameView.drawInventory(items);
    }
    public Space getGameSpace() {return gameSpace;}
    public void setGameSpace(Space space) {this.gameSpace = space;}
    public Stage getMainStage() {return gameView.getMainStage();}
    public PlayerShip getChoosenShip() {
        return choosenShip;
    }
}
