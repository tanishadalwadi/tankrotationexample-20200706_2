package tankrotationexample.GameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HealthPowerUp extends PowerUps{
    private boolean isHealthBooster = false;
    private BufferedImage healthboosterimg;
    private Rectangle hbox;
    public HealthPowerUp(int x, int y, boolean isHealthBooster, BufferedImage healthboosterimg) {
        super(x, y);
        this.isHealthBooster = isHealthBooster;
        this.healthboosterimg = healthboosterimg;
        this.hbox =new Rectangle(x,y,this.healthboosterimg.getWidth(),this.healthboosterimg.getHeight());

    }


    @Override
    public void update() {

    }
    @Override
    public Rectangle getHBox() {
        return this.hbox;
    }

    @Override
    public void DrawingOfImage(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.healthboosterimg, x, y, null);

    }
    @Override
    public String getNameOfObject() {
        return "HealthPowerUp";
    }


    public void collide() {

    }

}
