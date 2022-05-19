package com.cvut.src.model.bullet;
import com.cvut.src.model.GameObject;
import com.cvut.src.view.components.Renderparam;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public interface Bullet extends GameObject {

     Image getImg();
     double getDamage();
     void setDamage(double damage);
     Renderparam getImgParam();
     void setImgParam(Renderparam imgParam);
     ImageView getImageView();
     double getSpeedMove();
      void update();
      void paint(GraphicsContext graphicsContext);
     Rectangle getRectangle();
}
