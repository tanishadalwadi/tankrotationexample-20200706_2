package tankrotationexample.GameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HealthPowerUp extends PowerUps{
    private boolean isHealthBoost = false;
    private BufferedImage healthboostimg ;
    private Rectangle hitbox;
    public HealthPowerUp(int x, int y,boolean isHealthBoost, BufferedImage healthboostimg) {
        super(x, y);
        this.isHealthBoost = isHealthBoost;
        this.healthboostimg = healthboostimg;
        this.hitbox=new Rectangle(x,y,this.healthboostimg.getWidth(),this.healthboostimg.getHeight());

    }


    @Override
    public void update() {

    }
    @Override
    public Rectangle getHitBox() {
        return this.hitbox;
    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.healthboostimg, x, y, null);

    }
    @Override
    public String getObjectName() {
        return "Healthpowerup";
    }


    /*public String getDetails(){
        return " "+ hitbox.getX()+" "+hitbox.getY();
    }*/

    public void collision() {

    }

}
