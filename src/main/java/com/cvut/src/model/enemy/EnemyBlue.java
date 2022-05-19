package com.cvut.src.model.enemy;
import com.cvut.src.controller.GameController;
import com.cvut.src.view.components.Renderparam;
import com.cvut.src.view.GameView;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class EnemyBlue extends Enemy {
	private final String IMG_PATH = "/enemy_blue.png";
    private double dirY = 0;

    public EnemyBlue(GameController controller, int x, int y) {
		super(controller);
		this.controller = controller;
    	this.img = new Image(getClass().getResourceAsStream(IMG_PATH));
        imgParam = new Renderparam(x, y, img.getHeight(), img.getHeight());
        range = random.nextInt(150) + 50;
        maxLeftX = imgParam.getX() - range;
        if(maxLeftX < 0){
            maxLeftX = 0;
        }
        maxRightX = imgParam.getX() + range;
        if(maxRightX > GameView.GAME_WIDTH - imgParam.getWidth()){
            maxRightX = GameView.GAME_WIDTH - imgParam.getWidth();
        }
		this.moveSpeed = 2;
		this.health = 1;
		this.damage = 0.1;
    }


	@Override
    public void update() {
		//ENEMY START BLOCK
		if  (startAccessToMove == false)
		{
			super.update();
		}else {
			enemyShoot();
			enemyMoveLogic();
		}
    }

	private void enemyMoveLogic() {
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
			if(dirY == 0){
				imgParam.setY(imgParam.getY() - moveSpeed);
				if (imgParam.getY() < 0){
					imgParam.setY(imgParam.getY() + moveSpeed);
					dirY = 1;
				}
			}
			if(dirY == 1 ){
				imgParam.setY(imgParam.getY() + moveSpeed);
				if (imgParam.getY() > 450){
					imgParam.setY(imgParam.getY() -  moveSpeed);
					dirY = 0;
				}
			}
		}else {
			if((System.currentTimeMillis() - time) > stopTime){
				acccessToMoveDependingOnTheHour = true;
			}
		}
	}

	@Override
    public Rectangle getRectangle() {
        Rectangle rectangle = new Rectangle(imgParam.getX() + 15, imgParam.getY() + 12, imgParam.getWidth() - 35, imgParam.getHeight() - 24);
        return rectangle;
    }

}
