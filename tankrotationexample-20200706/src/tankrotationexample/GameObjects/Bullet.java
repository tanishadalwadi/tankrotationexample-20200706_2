package tankrotationexample.GameObjects;

import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject{
    private int x,y,vx,vy;
    private float direction;
    private int R = 7;
    private BufferedImage bulletImage;
    private Rectangle hBox;
    private String NameOfTank;
    private boolean collide = false;


    public Bullet(int x, int y, float direction, BufferedImage bulletImage ){
        this.x=x;
        this.y=y;
        this.direction = direction;
        this.bulletImage=bulletImage;
        System.out.println("IN CONS "+this.x+" "+this.y);
        this.hBox =new Rectangle(x,y,this.bulletImage.getWidth(),this.bulletImage.getHeight());
    }
    @Override
    public Rectangle getHBox() {
        return this.hBox;
    }


    public boolean getCollided(){
        return  this.collide;
    }

    public void BorderChecker(){
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
    public void movingForwards(){
        vx = (int)Math.round(R*Math.cos(Math.toRadians(direction)));
        vy = (int)Math.round(R*Math.sin(Math.toRadians(direction)));
        x+=vx;
        y+=vy;
        BorderChecker();
        this.hBox.setLocation(x,y);
    }
    public void update(){
        movingForwards();
    }

    public void DrawingOfImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x,y);
        rotation.rotate(Math.toRadians(direction),this.bulletImage.getWidth());
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.bulletImage,rotation,null);
    }
    @Override
    public String getNameOfObject() {
        return "Bullet";
    }

    public void collide(){
        collide = true;

    }

    public String NameOfTankForBullet(){
        return this.NameOfTank;
    }

    public void setNameOfTank(String name){
        this.NameOfTank = name;
    }

}
