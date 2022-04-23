import javax.swing.*;
import java.awt.*;

public class Bullet {
    private Image img;
    private String imagePathBullet = "res/bullet.png";
    private Renderparam imgParam;
    private int speedMove = 10;
    private int damage = 150;

    public int getDamage() { return damage; }
    public void setDamage(int damage) { this.damage = damage; }
    public Renderparam getImgParam() {
        return imgParam;
    }
    public void setImgParam(Renderparam imgParam) {
        this.imgParam = imgParam;
    }

    public Bullet(int x, int y){
        this.img = new ImageIcon(imagePathBullet).getImage();
        this.imgParam  = new Renderparam(x-img.getWidth(null)/2, y, img.getWidth(null), img.getHeight(null));
    }

    public void update(){
        imgParam.setY(imgParam.getY() - speedMove);
    }

    public void paint(Graphics graphics){
        graphics.drawImage(img, imgParam.getX(), imgParam.getY(), imgParam.getWidth(), imgParam.getHeight(), null);
    }


    //metoda vrati objekt typu rerctangle pro zjisteni kolize mezi objekty pri strileni
    public Rectangle getRectangle(){
        Rectangle rectangle = new Rectangle(imgParam.getX(), imgParam.getY(), imgParam.getWidth(), imgParam.getHeight());
        return rectangle;
    }
}
