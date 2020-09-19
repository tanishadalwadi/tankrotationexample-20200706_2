package tankrotationexample.GameObjects;

import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject{
    private int x,y,vx,vy;
    private float angle;
    private int R = 7;
    private BufferedImage bulletImage;
    private Rectangle hitBox;
    private String tankName;
    private boolean collision = false;


    public Bullet(int x, int y, float angle, BufferedImage bulletImage ){
        this.x=x;
        this.y=y;
        this.angle=angle;
        this.bulletImage=bulletImage;
        System.out.println("IN CONS "+this.x+" "+this.y);
        this.hitBox=new Rectangle(x,y,this.bulletImage.getWidth(),this.bulletImage.getHeight());
    }
    @Override
    public Rectangle getHitBox() {
        return this.hitBox;
    }


    public boolean getCollided(){
        return  this.collision;
    }
    public void moveForwards(){
         vx = (int)Math.round(R*Math.cos(Math.toRadians(angle)));
         vy = (int)Math.round(R*Math.sin(Math.toRadians(angle)));
         x+=vx;
         y+=vy;
         checkBorder();
         this.hitBox.setLocation(x,y);
    }

    public void checkBorder(){
        if(x<30){
            x=30;
        } if(x>= GameConstants.GAME_SCREEN_WIDTH*88){
            x=GameConstants.GAME_SCREEN_WIDTH*88;
        } if(y<40){
            y=40;
        } if(y>=GameConstants.GAME_SCREEN_HEIGHT*80){
            y=GameConstants.GAME_SCREEN_HEIGHT*80;
        }

    }
    public void update(){
        moveForwards();
    }

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x,y);
        rotation.rotate(Math.toRadians(angle),this.bulletImage.getWidth());
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.bulletImage,rotation,null);
       // g2d.setColor(Color.BLUE);
        //g2d.drawRect(x,y,this.bulletImage.getWidth(),this.bulletImage.getHeight());
    }
    @Override
    public String getObjectName() {
        return "Bullet";
    }

    public void collision(){
        collision = true;

    }

    public String tankNameForBullet(){
        return this.tankName;
    }

    public void setTankName(String name){
        this.tankName = name;
    }

}
