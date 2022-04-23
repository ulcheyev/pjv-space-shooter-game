import javax.swing.*;
import java.awt.*;

public class EnemyYellow extends Enemy{
    private String imgPathEnemyYellow = "res/enemy_yellow.png";

    public EnemyYellow(int x, int y) {
        img = new ImageIcon(imgPathEnemyYellow).getImage();
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
        health = 10;
    }

    @Override
    public void update() {
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
        }else {
            if((System.currentTimeMillis() - time) > stopTime){
                moveAccess = true;
            }
        }
    }

    @Override
    public Rectangle getRectangle() {
        Rectangle rectangle = new Rectangle(imgParam.getX() + 10, imgParam.getY() + 5, imgParam.getWidth() - 20, imgParam.getHeight() - 20);
        return rectangle;
    }
}
