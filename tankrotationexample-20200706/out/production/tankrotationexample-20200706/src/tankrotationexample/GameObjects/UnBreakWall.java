package tankrotationexample.GameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UnBreakWall extends Wall {
    int x,y;
    int state = 2;
    BufferedImage wallImage;
    private boolean collision = false;
    private Rectangle hitbox;
    public UnBreakWall(int x, int y, BufferedImage wallImage ){
        this.x=x;
        this.y=y;
        this.wallImage=wallImage;
        this.hitbox=new Rectangle(x,y,this.wallImage.getWidth(),this.wallImage.getHeight());
    }
    public void drawImage(Graphics g) {
        if (state > 0) {
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(this.wallImage, x, y, null);
        }
    }

    public void update() {

    }
    @Override
    public String getObjectName() {
        return "UnBreakWall";
    }

    @Override
    public void collision() {

    }

    @Override
    public Rectangle getHitBox() {
        return this.hitbox;
    }

    /*public boolean collision() {
        this.collision = true;
        return this.collision;
    }*/
}
