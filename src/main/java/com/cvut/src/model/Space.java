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
import java.util.Random;
import java.util.logging.*;


public class Space {
    private final static Logger logger = Logger.getLogger(Space.class.getName());

    private GameController controller;
    private Random random;

    //Game Components
    private MyBackground background;
    private PlayerShip playerShip;
    private PlayerShield playerShield;
    private ArrayList<Bullet> playerBullets;
    private ArrayList<Bullet> enemyBullets;
    private ArrayList<Enemy> enemies;
    private ArrayList<Explosion> explosions;
    private ArrayList<Item> items;
    private Inventory playerInventory;
    private Boss boss;
    private double bossHealthCheck; // peremennaya dlya vzrblvov u bossa.
    private boolean gameState = false; //peremennaya pokazivaet, chto igra eshte idet. Pri viigreshe ona budet true

    //Data
    private int enemiesWave;
    private int enemiesQuantity;

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




    public void addExplosion(Explosion explosion) {explosions.add(explosion);}
    public ArrayList<Bullet> getPlayerBullets() {
        return this.playerBullets;
    }
    public MyBackground getBackground() {
        return this.background;
    }
    public PlayerShip getPlayerShip() {
        return this.playerShip;
    }
    public Inventory getPlayerInventory() {return playerInventory;}
    public void setPlayerInventory(Inventory playerInventory) {this.playerInventory = playerInventory;}
    public Boss getBoss() {
        return boss;
    }
    public void setBoss(Boss boss) {
        this.boss = boss;
    }
    public void playerBulletsAddBullet(Bullet bullet){playerBullets.add(bullet);}
    public void enemyBulletsAddBullet(Bullet bullet){enemyBullets.add(bullet);}
    public void setPlayerShip(PlayerShip playerShip) {this.playerShip = playerShip;}
    public ArrayList<Bullet> getEnemyBullets() {return enemyBullets;}
    public void setGameState(boolean gameState) {this.gameState = gameState;}
    public ArrayList<Item> getItems() {return items;}

    public void paint(GraphicsContext graphicsContext) {
        background.paint(graphicsContext);
        if(playerShip != null){
            playerShip.paint(graphicsContext);
            playerShield.paint(graphicsContext);
        }

        for (int i = 0; i < playerBullets.size(); i++) {
            playerBullets.get(i).paint(graphicsContext);
        }
        for (int i = 0; i < enemyBullets.size(); i++) {
            enemyBullets.get(i).paint(graphicsContext);
        }
        for (int i = 0; i < enemies.size(); i++) {
            if( enemies.get(i) != null) {
                enemies.get(i).paint(graphicsContext);
            }
        }
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).paint(graphicsContext);
        }
        for (int i = 0; i < items.size(); i++) {
            items.get(i).paint(graphicsContext);
        }
    }

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
    //risuem vragov i proveryam na kolize s puley
    private void checkingPlayerShip(PlayerShip playerShip, ArrayList<Bullet> enemyBullets){
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


    //risuem vragov i proveryam na kolize s puley
    private void updateAndCheckingEnemies(ArrayList<Enemy> enemies, ArrayList<Bullet> playerBullets) {
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


    //SET GAME WIN;
    private void removeDestroyedEnemies(ArrayList<Enemy> enemies){
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

    //risuem puli. udalyaem puli, kogda oni uhodyat za ramki
    private void updatePlayerBullets(ArrayList<Bullet> playerBullets){
        for (int i = 0; i < playerBullets.size(); i++) {
            playerBullets.get(i).update();
            if (playerBullets.get(i).getImgParam().getY() < -50) {
                playerBullets.remove(i);
                i--;
            }
        }
    }

    //risuem puli vragov. udalyaem puli, kogda oni uhodyat za ramki
    private void updateEnemiesBullets(ArrayList<Bullet> enemyBullets){
        for (int i = 0; i < enemyBullets.size(); i++) {
            enemyBullets.get(i).update();
            if (enemyBullets.get(i).getImgParam().getY() > 1000) {
                enemyBullets.remove(i);
                i--;
            }
        }
    }

    //vybuchy
    private void updateExplosions(ArrayList<Explosion> explosions) {
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).update();
            if (!explosions.get(i).getRun()) {
                explosions.remove(i);
            }
        }
    }


    private void enemiesCreate(ArrayList<Enemy> enemies, int enemiesQuantity, int enemiesWave){
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

    private void bossSpawn(ArrayList<Enemy> enemies){
        playerBullets.clear();
        try {
            logger.log(Level.INFO, "Game save");
            bossHealthCheck = boss.getHealth();
            controller.saveGame();
        }catch (NullPointerException e){logger.log(Level.SEVERE, "Null pointer exception");}
        enemies.add(boss);


    }



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

    private void getRandomBonus(double x, double y){
        Item bonus;
        int rand = random.nextInt(6);
        if(rand == 1){
            bonus = new ShootItem(controller, x, y, 500);
        }
        else  if(rand == 2 || rand == 3){
            bonus = new HPItem(controller, x, y);
        }
        else {
            bonus = new SpeedItem(controller, x, y);
        }
        items.add(bonus);
    }

    private void updateItems(ArrayList<Item> items){
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


    public boolean checkCollision(GameObject object1, GameObject object2){
        if(object1.getRectangle().getBoundsInParent().intersects(object2.getRectangle().getBoundsInParent())){
            return true;
        }else {
            return false;
        }
    }

    private void updateHpBar(){
        controller.setHPBarProgress(playerShip.getHealth() + 0.05);
    }

    private void updateMultiShotBar(){
        if(controller.getAllowToFillMultiShotBar() == true){
            controller.setMultiShotProgress(controller.getMultiShotProgress() + 0.2);
        }
    }

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




}