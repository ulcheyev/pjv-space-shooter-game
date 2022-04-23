import javax.swing.*;
import java.awt.*;

public class EnemyGreen extends Enemy{
    private String imgPathEnemyGreen = "res/enemy_green.png";
    int dirY;

    public EnemyGreen(int x, int y) {
        img = new ImageIcon(imgPathEnemyGreen).getImage();
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

        health = 20;
    }
    @Override
    public void update() {
        if(random.nextInt(16) == 0){
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
        Rectangle rectangle = new Rectangle(imgParam.getX() + 10, imgParam.getY() + 10, imgParam.getWidth() - 20, imgParam.getHeight() - 20);
        return rectangle;
    }

}
