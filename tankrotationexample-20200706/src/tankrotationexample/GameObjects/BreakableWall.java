package tankrotationexample.GameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall extends Wall {
    int x,y;
    boolean state = false;
    BufferedImage ImageOfWall;
    int health = 100;
    private Rectangle hBox;

    public BreakableWall(int x, int y, BufferedImage ImageOfWall){
        this.x=x;
        this.y=y;
        this.ImageOfWall = ImageOfWall;
        this.hBox =new Rectangle(this.x,this.y,this.ImageOfWall.getWidth(),this.ImageOfWall.getHeight());
    }

    void setState(boolean state) {
        this.state = state;
    }
    public boolean getState() {
        return state;
    }

    public int getHealthVal(){
        return this.health;
    }
    public void removingHealth(int value){
        if (health - value < 0) {
            health = 0; //BreakableWall died
            state = true;
        } else {
            health -= value;
        }    }






    @Override
    public void DrawingOfImage(Graphics g) {
        if (!state) {
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(this.ImageOfWall, x, y, null);
        }
    }

    @Override
    public String getNameOfObject() {
        return "Breakwall";
    }



    @Override
    public Rectangle getHBox() {
        return this.hBox;
    }
    public void update() {
    }
    public void collide() {
        this.removingHealth(50);

    }

    public String getDetails(){
        return " "+ hBox.getX()+" "+ hBox.getY();
    }



}
