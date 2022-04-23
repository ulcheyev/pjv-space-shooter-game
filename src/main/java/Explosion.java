import javax.swing.*;
import java.awt.*;

public class Explosion {
    private Image spriteImage = new ImageIcon("res/sprite_explosion.png").getImage();
    private Renderparam renderParam;
    private int frame = 0;

    public boolean getRun() { return this.run; }

    public void setRun(boolean run) { this.run = run; }

    private boolean run = true;
    private int deltaTime = 0;
    private int duration = 2;

    public Explosion(int x, int y) {
        this.renderParam = new Renderparam();
        this.renderParam.setX(x);
        this.renderParam.setY(y);
    }

    public void update(){
        if(run){
            deltaTime++;
            if(deltaTime >= duration){
                frame++;
                deltaTime = 0;
                if(frame >= 81){ //pocet columnu
                    run = false;
                }
            }
        }
    }

    public void paint(Graphics graphics){
        drawSpriteFrame(graphics, spriteImage, renderParam.getX(), renderParam.getY(), 9, frame, 100, 100);
    }

    public void drawSpriteFrame(Graphics graphics, Image spriteImage, int x, int y, int columnNumber, int frameNumber, int width, int height){
         int frameX = (frame % columnNumber) * width;
         int frameY = (frame / columnNumber) * height;
         graphics.drawImage(spriteImage, x, y, x + width, y + height, frameX, frameY, frameX + width, frameY + height, null);
    }
}
