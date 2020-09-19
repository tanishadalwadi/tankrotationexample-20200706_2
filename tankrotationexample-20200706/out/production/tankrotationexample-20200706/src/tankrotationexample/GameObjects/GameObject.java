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

    public abstract Rectangle getHitBox();
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

    public abstract void drawImage(Graphics g);

    public abstract String getObjectName();

    public abstract void collision();
}



