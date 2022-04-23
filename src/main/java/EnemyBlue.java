import javax.swing.*;
import java.awt.*;

public class EnemyBlue extends Enemy {
    private String imgPathEnemyBlue = "res/enemy_blue.png";
    private int dirY = 0;

    public EnemyBlue(int x, int y) {
        img = new ImageIcon(imgPathEnemyBlue).getImage();
        imgParam = new Renderparam(x, y, img.getHeight(null), img.getHeight(null));
        range = random.nextInt(150) + 50;
        maxLeftX = imgParam.getX() - range;
        if(maxLeftX < 0){
            maxLeftX = 0;
        }
        maxRightX = imgParam.getX() + range;
        if(maxRightX > Game.WIDTH - imgParam.getWidth()){
            maxRightX = Game.WIDTH - imgParam.getWidth();
        }
        health = 60;
    }
    @Override
    public void update() {
        if(random.nextInt(1000) == 0){
            dirY = random.nextInt(2);   // 0 1
        }
        super.update();
        if(moveAccess == true){
            if(route == true){
                imgParam.setX(imgParam.getX() + moveSpeed);
            }else{
                imgParam.setX(imgParam.getX() - moveSpeed);
            }

            if(imgParam.getX() < maxLeftX || imgParam.getX() > maxRightX){
                route = !route;
                moveAccess = false;
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
                moveAccess = true;
            }
        }
    }

    @Override
    public Rectangle getRectangle() {
        Rectangle rectangle = new Rectangle(imgParam.getX() + 15, imgParam.getY() + 12, imgParam.getWidth() - 35, imgParam.getHeight() - 24);
        return rectangle;
    }
}
