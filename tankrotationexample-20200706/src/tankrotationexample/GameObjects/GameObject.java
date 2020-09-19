package tankrotationexample.GameObjects;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    int x;
    int y;
    int vx;
    int vy;
    int angle;
    boolean active;
    BufferedImage image;
    Rectangle rectangle;

    public abstract Rectangle getHBox();
    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return this.x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return this.y;
    }


    public boolean isActive() {
        return this.active;
    }

    public abstract void update();

    public abstract void DrawingOfImage(Graphics g);

    public abstract String getNameOfObject();

    public abstract void collide();
}



