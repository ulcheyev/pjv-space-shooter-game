package com.cvut.src.view.components;
import com.cvut.src.controller.GameController;
import javafx.scene.Group;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class MyProgressBar{
    private GameController controller;

    private ProgressBar progressBar;
    private double progress = 0;
    private boolean isEmpty = false;

    public MyProgressBar(GameController controller, double x, double y){
        this.controller = controller;
        progressBar = new ProgressBar();
        progressBar.setPrefSize(300, 20);
        progressBar.setLayoutX(x);
        progressBar.setLayoutY(y);
    }

    public void setProgressBarVertical(){
        progressBar.getTransforms().setAll(
                new Translate(0, 100),
                new Rotate(-90, 0, 0)
        );
    }

    public void setProgressBarBackGroundColor(String color){
        progressBar.setStyle("-fx-control-inner-background:" + color);
    }

    public void setProgressBarHueColor(double hueNumber){
        ColorAdjust adjust = new ColorAdjust();
        adjust.setHue(hueNumber);
        progressBar.setEffect(adjust);
        progressBar.setVisible(true);
    }

    public void setY(double y){
        progressBar.setLayoutY(y);
    }

    public void setX(double x){
        progressBar.setLayoutX(x);
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }
    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }
    public double getProgress() {return progressBar.getProgress();}

    public void setProgressToBar(double progress) {
        progressBar.setProgress(progress);
    }

    public void resetProgressBar(){
        progressBar.setProgress(0);
    }

}
