package com.cvut.src.model;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

/**
 * An interface that provides an implementation of three main methods: paint(), update(), getRectangle()
 * @author ulcheyev
 **/
public interface GameObject {
    void paint(GraphicsContext graphicsContext);
    void update();
    Rectangle getRectangle();
}
