package com.cvut.src.view.components;
import com.cvut.src.controller.GameController;
import javafx.scene.Group;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * Component of VIEW which representing a progress bar.
 * @author ulcheyev
 **/
public class MyProgressBar{
    private GameController controller;
    private ProgressBar progressBar;
    private double progress = 0;
    private boolean isEmpty = false;

    /**
     * MyProgressBar initialize.
     * @param x x coordinate
     * @param y y coordinate
     **/
    public MyProgressBar(GameController controller, double x, double y){
        this.controller = controller;
        progressBar = new ProgressBar();
        progressBar.setPrefSize(300, 20);
        progressBar.setLayoutX(x);
        progressBar.setLayoutY(y);
    }

    /**
     * The method changes progress bar position to vertical
     **/
    public void setProgressBarVertical(){
        progressBar.getTransforms().setAll(
                new Translate(0, 100),
                new Rotate(-90, 0, 0)
        );
    }

    /**
     * The method changes progress bar background color
     * @param color color to set
     **/
    public void setProgressBarBackGroundColor(String color){
        progressBar.setStyle("-fx-control-inner-background:" + color);
    }

    /**
     * The method changes progress bar color
     * @param hueNumber HUE number of color to set
     **/
    public void setProgressBarHueColor(double hueNumber){
        ColorAdjust adjust = new ColorAdjust();
        adjust.setHue(hueNumber);
        progressBar.setEffect(adjust);
        progressBar.setVisible(true);
    }

    /**Sets y position of progress bar
     * @param y y coordinate
     **/
    public void setY(double y){
        progressBar.setLayoutY(y);
    }

    /**Sets x position of progress bar
     * @param x x coordinate
     **/
    public void setX(double x){
        progressBar.setLayoutX(x);
    }

    /**Returns progress bar
     * @return  progress bar
     **/
    public ProgressBar getProgressBar() {
        return progressBar;
    }

    /**Returns progress of the progress bar
     * @return  progress of the progress bar bar
     **/
    public double getProgress() {return progressBar.getProgress();}

    /**Sets progress of the progress bar
     * @param progress  progress to set to the progress bar
     **/
    public void setProgressToBar(double progress) {
        progressBar.setProgress(progress);
    }

    /**Sets progress of the progress bar to 0
     **/
    public void resetProgressBar(){
        progressBar.setProgress(0);
    }

}
