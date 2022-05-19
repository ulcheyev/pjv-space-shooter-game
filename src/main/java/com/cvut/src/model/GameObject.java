package com.cvut.src.model;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

public interface  GameObject {
    void paint(GraphicsContext graphicsContext);
    void update();
    Rectangle getRectangle();
}
