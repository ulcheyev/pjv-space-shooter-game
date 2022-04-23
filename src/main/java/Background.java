import javax.swing.*;
import java.awt.*;

public class Background {

    private Image img;
    private String imagePathBackground = "res/space1.png";
    private Renderparam imgN1Param;
    private Renderparam imgN2Param;
    private float moveSpeed = 0.3f;
    private float totalSpeed = 0f;

    public void setImagePathBackground(String imagePathBackground) {
        this.imagePathBackground = imagePathBackground;
    }
    public Background(){
        this.img = new ImageIcon(imagePathBackground).getImage();
        this.imgN1Param  = new Renderparam(0,0, img.getWidth(null), img.getHeight(null));
        this.imgN2Param  = new Renderparam(0,img.getHeight(null), img.getWidth(null), img.getHeight(null));
    }

    public void paint(Graphics graphics){
        graphics.drawImage(img, imgN1Param.getX(), imgN1Param.getY(), imgN1Param.getWidth(), imgN1Param.getHeight(), null);
        graphics.drawImage(img, imgN2Param.getX(), imgN2Param.getY(), imgN2Param.getWidth(), imgN2Param.getHeight(), null);
    }

    public void update(){
        totalSpeed += moveSpeed;
        if(totalSpeed >= 1){
            totalSpeed -= 1;
            imgN1Param.setY(imgN1Param.getY() + 1);
            imgN2Param.setY(imgN2Param.getY() + 1);
            if(imgN2Param.getY() >= 0){
                imgN1Param.setY(0);
                imgN2Param.setY(img.getHeight(null)* (-1)); //chtoby byla sverhu
            }
        }

    }
}
