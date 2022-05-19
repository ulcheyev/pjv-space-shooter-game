package com.cvut.src.view.components;

import java.io.Serializable;

public class Renderparam implements Serializable {
    private double x = 0;
    private double y = 0;
    private double width = 0;
    private double height = 0;


    public Renderparam(double x, double y, double width, double height){
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public Renderparam(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Renderparam(){
    }


    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

}
