package tankrotationexample.GameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakWall extends Wall {
    int x,y;
    boolean state = false;
    BufferedImage wallImage;
    int health = 100;
    private Rectangle hitBox;

    public BreakWall(int x, int y, BufferedImage wallImage ){
        this.x=x;
        this.y=y;
        this.wallImage=wallImage;
        this.hitBox=new Rectangle(this.x,this.y,this.wallImage.getWidth(),this.wallImage.getHeight());
    }

    void setState(boolean state) {
        this.state = state;
    }
    public boolean getState() {
        return state;
    }

    public int getHealth(){
        return this.health;
    }
    public void removeHealth(int value){
        if (health - value < 0) {
            health = 0; //BreakableWall died
            state = true;
        } else {
            health -= value;
        }    }






    @Override
    public void drawImage(Graphics g) {
        if (!state) {
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(this.wallImage, x, y, null);
        }
    }

    @Override
    public String getObjectName() {
        return "Breakwall";
    }



    @Override
    public Rectangle getHitBox() {
        return this.hitBox;
    }
    public void update() {
    }
    public void collision() {
        this.removeHealth(50);

    }

    public String getDetails(){
        return " "+ hitBox.getX()+" "+hitBox.getY();
    }



}
