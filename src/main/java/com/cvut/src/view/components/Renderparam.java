package com.cvut.src.view.components;

import java.io.Serializable;
/**
 * Сlass that stores the positions of an object and its characteristics
 * @author ulcheyev
 **/
public class Renderparam implements Serializable {
    private double x = 0;
    private double y = 0;
    private double width = 0;
    private double height = 0;

    /**
     * Initialize Renderparam.
     * @param x x coordinate
     * @param y y coordinate
     * @param width object width
     * @param height object height
     **/
    public Renderparam(double x, double y, double width, double height){
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    /**
     * Initialize Renderparam.
     * @param x x coordinate
     * @param y y coordinate
     **/
    public Renderparam(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * Initialize Renderparam.
     **/
    public Renderparam(){
    }


    /**
     * Returns objects x coordinate
     * @return   objects x coordinate
     **/
    public double getX() {
        return this.x;
    }

    /**
     * Sets objects  x coordinate
     * @param x x coordinate
     **/
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Returns objects y coordinate
     * @return  objects y coordinate
     **/
    public double getY() {
        return this.y;
    }

    /**
     * Sets objects y coordinate
     * @param y y coordinate
     **/
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Returns objects width
     * @return   objects x width
     **/
    public double getWidth() {
        return width;
    }

    /**
     * Returns objects height
     * @return   objects x height
     **/
    public double getHeight() {
        return height;
    }


}
