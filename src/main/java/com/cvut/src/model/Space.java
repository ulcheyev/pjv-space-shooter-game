package com.cvut.src.model;
import com.cvut.src.controller.GameController;
import com.cvut.src.model.bonusItem.*;
import com.cvut.src.model.bullet.rocket.Rocket;
import com.cvut.src.model.bullet.Bullet;
import com.cvut.src.model.enemy.*;
import com.cvut.src.model.explosion.*;
import com.cvut.src.model.player.PlayerShield;
import com.cvut.src.model.player.ship.PlayerShip;
import com.cvut.src.view.components.MyBackground;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.*;

/**
 * Game map.The class in which the creation, updating, painting of objects takes place
 * @author ulcheyev
 **/
public class Space {
    private final static Logger logger = Logger.getLogger(Space.class.getName());

    private GameController controller;
    private Random random;

    //Game Components
    private MyBackground background;
    private PlayerShip playerShip;
    private PlayerShield playerShield;
    private List<Bullet> playerBullets;
    private List<Bullet> enemyBullets;
    private List<Enemy> enemies;
    private List<Explosion> explosions;
    private List<Item> items;
    private Inventory playerInventory;
    private Boss boss;

    private int enemiesWave;
    private int enemiesQuantity;
    private static int level = 1;

    //A variable for monitoring the boss health.
    private double bossHealthCheck;

    //A variable for monitoring the state of the game. True - not finished yet, False - finished
    private boolean gameState = false;

    /**
     * Space initialize.Creating game objects.
     * @param controller game controller
     * @param boss boos type
     * @param enemiesWave quantity of enemies wave
     * @param enemiesQuantity quantity of enemies in every wave
     **/
    public Space(GameController controller, BossType boss, int enemiesWave, int enemiesQuantity) {
        logger.log(Level.INFO, "Space initialize");
        this.controller = controller;
        this.enemiesWave = enemiesWave;
        this.enemiesQuantity = enemiesQuantity;
        this.boss = new Boss(controller, boss);
        playerShip = controller.getChoosenShip();
        playerShield = playerShip.getShield();
        playerInventory = playerShip.getInventory();
        background = new MyBackground();
        enemies = new ArrayList<>();
        playerBullets = new ArrayList<>();
        enemyBullets = new ArrayList<>();
        explosions = new ArrayList<>();
        random = new Random();
        items = new ArrayList<>();
    }

    /**The method calls the painting method for all game objects
     * @param graphicsContext - GraphicContext object from game scene
     * **/
    public void paint(GraphicsContext graphicsContext) {
        background.paint(graphicsContext);
        if(playerShip != null){
            playerShip.paint(graphicsContext);
            playerShield.paint(graphicsContext);
        }
        parametrizedPaintMethod(playerBullets, graphicsContext);
        parametrizedPaintMethod(enemyBullets, graphicsContext);
        parametrizedPaintMethod(enemies, graphicsContext);
        parametrizedPaintMethod(explosions, graphicsContext);
        parametrizedPaintMethod(items, graphicsContext);
    }

    private void parametrizedPaintMethod(List objects, GraphicsContext graphicsContext){
        for(int i = 0; i < objects.size(); i++){
            if(objects.get(i) != null){
                GameObject object = (GameObject) objects.get(i);
                object.paint(graphicsContext);
            }
        }
    }

    /**The method calls the processing method for all game objects
     * @throws InterruptedException
     * **/
    public void update() throws InterruptedException {
        background.update();
        if(gameState == false) {
            if (playerShip != null) {
                playerShip.update();
                playerShield.update();
                checkingPlayerShip(playerShip, enemyBullets);
                updatePlayerBullets(playerBullets);
                updateAndCheckingEnemies(enemies, playerBullets);
                updateEnemiesBullets(enemyBullets);
                removeDestroyedEnemies(enemies);
                updateExplosions(explosions);
                enemiesCreate(enemies, enemiesQuantity, enemiesWave);
                updateItems(items);
                if (enemiesWave == 0 && enemies.size() == 0 ) {
                    bossSpawn(enemies);
                }
                if (boss != null) {
                    bossBigExplosionAdd(boss);
                }
            }else {
                updateExplosions(explosions);
                for(int i =0; i < enemies.size(); i++){
                    enemies.get(i).update();
                    enemies.get(i).setStopShoot(true);
                }
            }
        }else{
            updateExplosions(explosions);
            playerShip.update();
        }

    }


    //SET GAME LOST
    //Update player ship
    //Checking the ship for collisions with enemy bullets.
    //Player ship health update
    private void checkingPlayerShip(PlayerShip playerShip, List<Bullet> enemyBullets){
        for (int i = 0; i < enemyBullets.size(); i++) {
            if (checkCollision(playerShip, enemyBullets.get(i))) {
                double explosionXcoord = enemyBullets.get(i).getImgParam().getX();
                double explosionYcoord = enemyBullets.get(i).getImgParam().getY();
                if(enemyBullets.get(i) instanceof Rocket){
                    explosionAdd(ExplosionType.RED_DESTROY, explosionXcoord - enemyBullets.get(i).getImgParam().getWidth()/2 - 20,
                            explosionYcoord + enemyBullets.get(i).getImgParam().getHeight()/2);
                }else{
                    explosionAdd(ExplosionType.HIT, explosionXcoord, explosionYcoord + enemyBullets.get(i).getImgParam().getHeight()/2);
                }
                playerShip.takinDamage(enemyBullets.get(i).getDamage());
                updateHpBar();
                enemyBullets.remove(i);
                if(playerShip.getHealth() <= 0){
                    explosionAdd(ExplosionType.RED_DESTROY, playerShip.getImgParam().getX() - 15, playerShip.getImgParam().getY());
                    controller.gameOver();
                }
            }
        }
    }

    //Update enemies
    //Checking enemies for collisions with the player's bullets
    private void updateAndCheckingEnemies(List<Enemy> enemies, List<Bullet> playerBullets) {
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update();
            for (int j = 0; j < playerBullets.size(); j++){
                if (checkCollision( enemies.get(i), playerBullets.get(j))) {
                    double explosionXcoord = playerBullets.get(j).getImgParam().getX();
                    double explosionYcoord = playerBullets.get(j).getImgParam().getY();
                    if(playerBullets.get(j) instanceof Rocket){
                        explosionAdd(ExplosionType.RED_DESTROY, explosionXcoord,explosionYcoord - enemies.get(i).getImgParam().getHeight()/4);
                    }else {
                        explosionAdd(ExplosionType.HIT, explosionXcoord, explosionYcoord - enemies.get(i).getImgParam().getHeight() / 4);
                    }
                    enemies.get(i).takinDamage(playerBullets.get(j).getDamage());
                    playerBullets.remove(j);
                    j--;
                }
            }
        }
    }


    //SET GAME WIN
    //Destroy enemies
    private void removeDestroyedEnemies(List<Enemy> enemies){
        for(int i = 0; i < enemies.size(); i++){
            if(enemies.get(i).getHealth() <= 0){
                if(enemies.get(i).getClass() == Boss.class){
                    explosionAdd(ExplosionType.BLUE_DESTROY, enemies.get(i).getImgParam().getX(), enemies.get(i).getImgParam().getY());
                    controller.gameWin();

                }else{
                    updateMultiShotBar();
                    getRandomBonus(enemies.get(i).getImgParam().getX(), enemies.get(i).getImgParam().getY());
                    explosionAdd(ExplosionType.RED_DESTROY, enemies.get(i).getImgParam().getX(), enemies.get(i).getImgParam().getY());
                }
                enemies.remove(i);
                i--;
            }
        }
    }

    //Player bullet updating. Removing out-of-bounds bullets.
    private void updatePlayerBullets(List<Bullet> playerBullets){
        for (int i = 0; i < playerBullets.size(); i++) {
            playerBullets.get(i).update();
            if (playerBullets.get(i).getImgParam().getY() < -50) {
                playerBullets.remove(i);
                i--;
            }
        }
    }

    //Enemies bullet updating. Removing out-of-bounds bullets.
    private void updateEnemiesBullets(List<Bullet> enemyBullets){
        for (int i = 0; i < enemyBullets.size(); i++) {
            enemyBullets.get(i).update();
            if (enemyBullets.get(i).getImgParam().getY() > 1000) {
                enemyBullets.remove(i);
                i--;
            }
        }
    }

    //Explosion updating
    private void updateExplosions(List<Explosion> explosions) {
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).update();
            if (!explosions.get(i).getRun()) {
                explosions.remove(i);
            }
        }
    }

    //Creating enemies
    private void enemiesCreate(List<Enemy> enemies, int enemiesQuantity, int enemiesWave){
        if(enemies.size() == 0) {
            if (enemiesWave != 0){
                this.enemiesWave--;
                Enemy enemy;
                playerBullets.clear();
                for (int i = 0; i < enemiesQuantity; i++) {
                    int rand = random.nextInt(4);
                    if(rand == 1){
                        enemy = new EnemyYellow(controller, random.nextInt(900), 10);
                    } else if(rand == 2){
                        enemy = new EnemyGreen(controller, random.nextInt(900), 10);
                    }else{
                        enemy = new EnemyBlue(controller, random.nextInt(900), 10);
                    }
                    enemy.setStartAccessToMove(false);
                    enemies.add(enemy);
                }
            }
        }
    }

    //Creating a boss
    private void bossSpawn(List<Enemy> enemies){
        playerBullets.clear();
        try {
            logger.log(Level.INFO, "Game save");
            bossHealthCheck = boss.getHealth();
            controller.saveGame();
        }catch (NullPointerException e){logger.log(Level.SEVERE, "Null pointer exception");}
        enemies.add(boss);


    }


    //Adding big explosions to the boss when he loses a certain amount of health
    private void bossBigExplosionAdd(Boss boss){
        if (boss.getHealth() <= bossHealthCheck - 1) {
            updateMultiShotBar();
            getRandomBonus(boss.getImgParam().getX() + boss.getImgParam().getWidth()/2, boss.getImgParam().getY() + boss.getImgParam().getHeight()-50);
            bossHealthCheck -= 1;
            double x = random.nextDouble(boss.getImgParam().getX() + boss.getImgParam().getWidth() - boss.getImgParam().getX() - 50) + boss.getImgParam().getX();
            double y = random.nextDouble(boss.getImgParam().getY() + boss.getImgParam().getHeight() - boss.getImgParam().getY() - 40) + boss.getImgParam().getY();
            explosionAdd(ExplosionType.RED_DESTROY, x, y);
        }
    }

    //Spawn random bonus
    private void getRandomBonus(double x, double y){
        int r = random.nextInt(4);
        if(r == 2) {
            Item bonus;
            int rand = random.nextInt(6);
            if (rand == 1) {
                bonus = new ShootItem(controller, x, y, 0.25);
            } else if (rand == 2 || rand == 3) {

                bonus = new HPItem(controller, x, y);
            } else {
                bonus = new SpeedItem(controller, x, y);
            }
            items.add(bonus);
        }
    }

    //Update items
    private void updateItems(List<Item> items){
        for(int i = 0; i < items.size(); i++){
            if(items.get(i).getAttribute() != 0) {
                items.get(i).update();
                if(items.get(i).getRenderParam().getY() >= 1100){
                    items.remove(i);
                }
            }else {
                items.remove(items.get(i));
            }
        }
    }


    /**
     * The method checks for a collision between two objects
     * @param object1 - object N1
     * @param object2 - object N2
     * **/
    public boolean checkCollision(GameObject object1, GameObject object2){
        if(object1.getRectangle().getBoundsInParent().intersects(object2.getRectangle().getBoundsInParent())){
            return true;
        }else {
            return false;
        }
    }

    //Updating player ship hp bar
    private void updateHpBar(){
        controller.setHPBarProgress(playerShip.getHealth() + 0.05);
    }

    //Updating player ship multi shot bar
    private void updateMultiShotBar(){
        if(controller.getAllowToFillMultiShotBar() == true){
            controller.setMultiShotProgress(controller.getMultiShotProgress() + 0.2);
        }
    }

    /**
     * The method adds an explosion depending on the input parameter (explosion type)
     * @param type - explosion type
     * @param x - x coordinate
     * @param y - y coordinate
     * **/
    public void explosionAdd(ExplosionType type, double x, double y){
        switch (type){
            case HIT -> {
                explosions.add(new HitExplosion(x, y));
            }
            case RED_DESTROY -> {
                controller.playSound("destroy");
                explosions.add(new RedDestroyExplosion(x, y));
            }
            case BLUE_DESTROY -> {
                controller.playSound("boss_destroy");
                explosions.add(new BlueDestroyExplosion(x, y));
            }
        }
    }



    //Player ship getters and setters
    /**Returns player ship
     * @return Player ship
     **/
    public PlayerShip getPlayerShip() {return this.playerShip;}

    /**Returns player inventory
     * @return Player inventory
     **/
    public Inventory getPlayerInventory() {return playerInventory;}

    /**Returns list of player bullets
     * @return list of player bullets
     **/
    public List<Bullet> getPlayerBullets() {return this.playerBullets;}

    /**Add bullet to player bullets list
     * @param  bullet bullet to add
     **/
    public void playerBulletsAddBullet(Bullet bullet){playerBullets.add(bullet);}

    /**Sets player inventory
     * @param  playerInventory inventory to set
     **/
    public void setPlayerInventory(Inventory playerInventory) {this.playerInventory = playerInventory;}

    /**Sets player ship
     * @param  playerShip Player ship to set
     **/
    public void setPlayerShip(PlayerShip playerShip) {this.playerShip = playerShip;}

    /**Returns list of inventory items
     * @return  list of items
     **/
    public List<Item> getItems() {return items;}


    /**Increases the level of the game
     **/
    public void levelUp(){
        level++;
    }

    /**Returns level of the game
     * @return level of the game
     **/
    public int getLevel() {return level;}

    //ENEMIES GETTERS AND SETTERS

    /**Returns quantity of enemies wave
     * @return quantity of enemies wave
     **/
    public int getEnemiesWave() {return enemiesWave;}

    /**Returns quantity of enemies in every wave
     * @return quantity of enemies in every wave
     **/
    public int getEnemiesQuantity() {return enemiesQuantity;}

    /**Returns boss
     * @return Boss
     **/
    public Boss getBoss() {return boss;}


    /**Sets boss
     * @param  boss Boss
     **/
    public void setBoss(Boss boss) {this.boss = boss;}

    /**Add bullet to enemies bullets list
     * @param  bullet bullet to add
     **/
    public void enemyBulletsAddBullet(Bullet bullet){enemyBullets.add(bullet);}

    /**Returns list of enemies bullets
     * @return  List of enemies bullets
     **/
    public List<Bullet> getEnemyBullets() {return enemyBullets;}

    /**Sets game state
     * @param  gameState game state to set. True-finished, False-not finished.
     **/
    public void setGameState(boolean gameState) {this.gameState = gameState;}




    //OTHER COMPONENTS
    /**Returns space background
     * @return Space background
     **/
    public MyBackground getBackground() {return this.background;}






}