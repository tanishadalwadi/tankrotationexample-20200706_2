package tankrotationexample.GameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UnBreakableWall extends Wall {
    int x,y;
    int state = 2;
    BufferedImage ImageOfWall;
    private boolean collision = false;
    private Rectangle hbox;
    public UnBreakableWall(int x, int y, BufferedImage ImageOfWall){
        this.x=x;
        this.y=y;
        this.ImageOfWall = ImageOfWall;
        this.hbox =new Rectangle(x,y,this.ImageOfWall.getWidth(),this.ImageOfWall.getHeight());
    }
    public void DrawingOfImage(Graphics g) {
        if (state > 0) {
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(this.ImageOfWall, x, y, null);
        }
    }

    public void update() {

    }
    @Override
    public String getNameOfObject() {
        return "UnBreakWall";
    }

    @Override
    public void collide() {

    }

    @Override
    public Rectangle getHBox() {
        return this.hbox;
    }

    /*public boolean collide() {
        this.collide = true;
        return this.collide;
    }*/
}
