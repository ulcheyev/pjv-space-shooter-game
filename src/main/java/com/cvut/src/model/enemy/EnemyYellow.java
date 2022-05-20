package com.cvut.src.model.enemy;
import com.cvut.src.controller.GameController;
import com.cvut.src.view.components.Renderparam;
import com.cvut.src.view.GameView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

/**
 * The class represents first enemy type
 * @author ulcheyev
 **/
public class EnemyYellow extends Enemy {

    private final String IMG_PATH = "/enemy_yellow.png";

    /**
     * Initialize EnemyYellow. Determination of maximum values for movement. Set up parameters.
     * @param controller game controller
     * @param x x coordinate
     * @param y y coordinate
     **/
    public EnemyYellow(GameController controller, int x, int y) {
        super(controller);
        this.controller = controller;
    	this.img = new Image(getClass().getResourceAsStream(IMG_PATH));
        this.view = new ImageView(img);

        imgParam = new Renderparam(x, y, img.getWidth(), img.getHeight());
        range = random.nextInt(150) + 50;
        maxLeftX = imgParam.getX() - range;
        if(maxLeftX < 0){
            maxLeftX = 0;
        }
        maxRightX = imgParam.getX() + range;
        if(maxRightX > GameView.GAME_WIDTH - imgParam.getWidth()){
            maxRightX = GameView.GAME_HEIGHT - imgParam.getWidth();
        }
        this.health = 1;
        this.moveSpeed = 1;
        this.damage = 0.1;
    }


    @Override
    public void update() {
//         ENEMY START BLOCK
    	    if  (startAccessToMove == false)
            {
    		    super.update();
    	    }else {
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
     * The method creates the logic of the enemy's movement.
     **/
    public void enemyMoveLogic(){
        if(acccessToMoveDependingOnTheHour == true){
            if(route == true){
                imgParam.setX(imgParam.getX() + moveSpeed);
            }else{
                imgParam.setX(imgParam.getX() - moveSpeed);
            }

            if(imgParam.getX() < maxLeftX || imgParam.getX() > maxRightX){
                route = !route;
                acccessToMoveDependingOnTheHour = false;
                time = System.currentTimeMillis();
            }
        }else {
            if ((System.currentTimeMillis() - time) > stopTime) {
                acccessToMoveDependingOnTheHour = true;
            }
        }
    }



}
