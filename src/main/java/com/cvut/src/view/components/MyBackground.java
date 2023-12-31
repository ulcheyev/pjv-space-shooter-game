package com.cvut.src.view.components;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The VIEW that represents background image.
 * @author ulcheyev
 **/
public class MyBackground {
    private static final String IMG_PATH_BACKGROUND = "/space1.png";

    private Image img;
    private Renderparam imgN1Param;
    private Renderparam imgN2Param;
    private ImageView viewImgN1;
    private ImageView viewImgN2;
    private float moveSpeed = 1.5f;

    /**
     * Background initialize
     **/
    public MyBackground(){
        this.img = new Image(getClass().getResourceAsStream(IMG_PATH_BACKGROUND));
        viewImgN1 = new ImageView(img);
        viewImgN2 = new ImageView(img);
        this.imgN1Param  = new Renderparam(0,0, img.getWidth(), img.getHeight());
        this.imgN2Param  = new Renderparam(0,img.getHeight(), img.getWidth(), img.getHeight());
    }

    /**
     * The method paint background on game scene graphic context
     **/
    public void paint(GraphicsContext gc){
        gc.drawImage(img,0, imgN1Param.getY());
        gc.drawImage(img,0, imgN2Param.getY());
    }

    /**
     * The method update background position depending on its move speed
     **/
    public void update(){
        imgN1Param.setY(imgN1Param.getY() + moveSpeed);
        imgN2Param.setY(imgN2Param.getY() + moveSpeed);
        if(imgN1Param.getY() >= img.getHeight()){
            imgN1Param.setY(img.getHeight() * (-1));
        }
        if(imgN2Param.getY() >= img.getHeight()){
            imgN2Param.setY(img.getHeight() * (-1));
        }
    }

    /**
     * Returns background image
     * @return image of the background
     **/
    public Image getImg() {return img;}

    /**
     * Returns background image view
     * @return image view of the background
     **/
    public ImageView getViewImgN1() {return viewImgN1;}

    /**
     * Returns background image view
     * @return image view of the background
     **/
    public ImageView getViewImgN2() {return viewImgN2;}

    /**
     * Returns background move speed
     * @return background move speed
     **/
    public float getMoveSpeed() {return moveSpeed;}

}
