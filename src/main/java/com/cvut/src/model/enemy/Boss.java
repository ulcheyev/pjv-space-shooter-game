package com.cvut.src.model.enemy;

import com.cvut.src.controller.GameController;
import com.cvut.src.model.bullet.rocket.EnemyRocket;
import com.cvut.src.view.components.MyProgressBar;
import com.cvut.src.view.components.Renderparam;
import com.cvut.src.model.bullet.ammo.BossBullet;
import com.cvut.src.model.bullet.Bullet;
import com.cvut.src.model.bullet.ammo.EnemyBullet;
import com.cvut.src.view.GameView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

/**
 * The class represents the boss in the game
 * @author ulcheyev
 **/
public class Boss extends Enemy {
    private final String IMG_PATH = "/boss1.png";
    private BossType type;
    private MyProgressBar hpBar;

    /**
     * Initialize Boss. Determination of maximum values for movement. Creation boss hp bar.
     * @param controller game controller
     * @param type type of the boss
     **/
    public Boss(GameController controller, BossType type) {
        super(controller);
        this.type = type;
        this.controller = controller;
        this.img = new Image(getClass().getResourceAsStream(type.getImgPath()));
        this.view = new ImageView(img);
        imgParam = new Renderparam( GameView.GAME_WIDTH/2 - 122,-250, img.getWidth(), img.getHeight());

        range = random.nextInt(150) + 50;

        maxLeftX = imgParam.getX() - range;
        if (maxLeftX < 0) {
            maxLeftX = 0;
        }
        maxRightX = imgParam.getX() + range;
        if (maxRightX > GameView.GAME_WIDTH - imgParam.getWidth()) {
            maxRightX = GameView.GAME_HEIGHT - imgParam.getWidth();
        }
        this.health = type.getHealth();
        this.moveSpeed = type.getMoveSpeed();
        this.damage = type.getDamage();

        hpBar = new MyProgressBar(controller, imgParam.getX(), imgParam.getY());
        hpBar.setProgressBarBackGroundColor("black");
        hpBar.setProgressBarHueColor(2);
        hpBar.setProgressToBar(1);
        controller.setBarOnPane(hpBar);
    }


    @Override
    public void update() {
        hpBar.setX(imgParam.getX() - 27);
        hpBar.setY(imgParam.getY() + 25);
        hpBar.setProgressToBar(health);
//         ENEMY START BLOCK
        if (startAccessToMove == false) {
            super.update();
        } else {
            enemyShoot();
            enemyMoveLogic();
        }
    }

    @Override
    public Rectangle getRectangle() {
        Rectangle rectangle = new Rectangle(imgParam.getX() + 10, imgParam.getY() + 5, imgParam.getWidth() - 20, imgParam.getHeight() - 20);
        return rectangle;
    }

    /**
     * The method creates the logic of the boss's movement.
     **/
    public void enemyMoveLogic() {
        if (acccessToMoveDependingOnTheHour == true) {
            if (route == true) {
                imgParam.setX(imgParam.getX() + moveSpeed);
            } else {
                imgParam.setX(imgParam.getX() - moveSpeed);
            }

            if (imgParam.getX() < maxLeftX || imgParam.getX() > maxRightX) {
                route = !route;
                acccessToMoveDependingOnTheHour = false;
                time = System.currentTimeMillis();
            }
        } else {
            if ((System.currentTimeMillis() - time) > stopTime) {
                acccessToMoveDependingOnTheHour = true;
            }
        }
    }

    /**
     * The method allows to the boss to fire with different ammo.
     **/
    public void enemyShoot() {
        if (stopShoot == false) {
            shootDelay = 1000;
            if ((System.currentTimeMillis() - lastShoot) > shootDelay) {
                shootAcces = true;
            }
            if (shootAcces == true) {
                Bullet bullet;
                int rand = random.nextInt(6);
                if (rand == 0) {
                    bullet = new EnemyRocket(damage, imgParam.getX() + imgParam.getWidth() / 2, imgParam.getY() + 90);
                } else if (rand == 2 || rand == 3) {
                    bullet = new BossBullet(damage, imgParam.getX() + imgParam.getWidth() / 2, imgParam.getY() + 90);
                } else {
                    bullet = new EnemyBullet(damage, imgParam.getX() + imgParam.getWidth() / 2, imgParam.getY() + 90);
                }
                controller.getGameSpace().enemyBulletsAddBullet(bullet);
                shootAcces = false;
                lastShoot = System.currentTimeMillis();
            }
        }
    }

    /** Returns boss type
     * @return boss type
     **/
    public BossType getType() {
        return type;
    }

    /** Sets boss type
     * @param  type boss type
     **/
    public void setType(BossType type) {
        this.type = type;
    }

    /** Returns boss hp bar
     * @return   boss hp bar
     **/
    public MyProgressBar getHpBar() {
        return hpBar;
    }
}
