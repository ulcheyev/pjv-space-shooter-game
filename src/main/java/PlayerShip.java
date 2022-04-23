import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayerShip {
    private Image img;
    private String imagePathPlayerShip = "res/hero.png";
    private Renderparam imgParam;
    private int move = 0; //tekushee peremeshenie geroya
    private int speedMove = 3;//4 pixelu za minutu
    private int shootDelay = 200; // zaderzhka mezhdu vystrelami ms.
    private long lastShoot = 0; // vremya posledniho vystrela
    private boolean shooting = true; // razreshenie na vystrel

    public PlayerShip(){
        this.img = new ImageIcon(imagePathPlayerShip).getImage();
        this.imgParam  = new Renderparam(600,800, img.getWidth(null), img.getHeight(null));
    }

    public void paint(Graphics graphics){
        graphics.drawImage(img, imgParam.getX(), imgParam.getY(), imgParam.getWidth(), imgParam.getHeight(), null);
    }

    public void update(){
        imgParam.setX(imgParam.getX() + move);
        if(imgParam.getX() <= 0){
            imgParam.setX(0);
        }
        if(imgParam.getX() >= Game.WIDTH - (imgParam.getWidth() + 20)){
            imgParam.setX(Game.WIDTH - (imgParam.getWidth() + 20));
        }
        if((System.currentTimeMillis() - lastShoot) > shootDelay){
            shooting = true;
        }
    }


    //UPRAVLENIE
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode == 68){ //vpravo
            move = speedMove;
        }
        if(keyCode == 65){ //vlevo
            move = -speedMove;
        }
        if(keyCode == 32){ //probel
            if(shooting == true) {
                Space.getBullets().add(new Bullet((imgParam.getX() + imgParam.getWidth() / 2), imgParam.getY()));
                shooting = false;
                lastShoot = System.currentTimeMillis();
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode == 68){
            move = 0;
        }
        if(keyCode == 65){
            move = 0;
        }
    }
}
