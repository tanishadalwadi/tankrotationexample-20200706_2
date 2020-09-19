package tankrotationexample.GameObjects;

import tankrotationexample.GameConstants;
import tankrotationexample.game.TRE;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
/**
 *
 * @author anthony-pc
 */
public class Tank extends GameObject {
    private int x;
    private int y;
    private int vx;
    private int vy;
    private float angle;
    private final int R = 2;
    private final float ROTATIONSPEED = 3.0f;

    private BufferedImage tankImg;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;
    private Bullet blt;
    private ArrayList<GameObject> BulletListing;
    private Rectangle bound;
    private int lives;
    private int tickCount;
    private Rectangle hBox; //ADded
    private String NameTank;
    public static int HealthOft1 = 100;
    public static int HealthOft2 = 100;
    private String tankname;
    private boolean canMoveUp;
    private boolean canMoveDown;
    private boolean canMoveLeft;
    private boolean canMoveRight;
    private boolean move;
    private boolean collision = false;

    public Tank(int x, int y, int vx, int vy, int angle, BufferedImage tankImg,boolean move) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.tankImg = tankImg;
        this.angle = angle;
        this.move = move;
        this.bound = new Rectangle(this.tankImg.getWidth(),this.tankImg.getHeight());
        this.BulletListing =new ArrayList<>();
        this.hBox = new Rectangle(x,y,this.tankImg.getWidth(),this.tankImg.getHeight());
    }

    public Rectangle getHbox(){ //ADDED
        return this.hBox.getBounds();
    }

    @Override
    public Rectangle getHBox() {
        return this.hBox;
    }

    public void setX(int x){ this.x = x; }
    public int getX(){ return this.x; }
    public int getLives(){return this.lives;}

    public void setY(int y) { this. y = y;}
    public int getY(){ return this.y; }


    public void toggleUpPressed() {
        this.UpPressed = true;
    }

    public void toggleDownPressed() {
        this.DownPressed = true;
    }

    public void toggleShootPressed() {
        this.ShootPressed = true;
    }

    public void toggleRightPressed() {
        this.RightPressed = true;
    }

    public void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    public void unToggleUpPressed() {
        this.UpPressed = false;
    }

    public void unToggleShootPressed() {
        this.ShootPressed = false;
    }


    public void unToggleDownPressed() {
        this.DownPressed = false;
    }

    public void unToggleRightPressed() {
        this.RightPressed = false;
    }

    public void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    public void update() {
       // if (this.canMove) {
            if (this.UpPressed) {
                this.movingForwards();
            }
            if (this.DownPressed) {
                this.movingBackwards();
            }

            if (this.LeftPressed) {
                this.rotateToLeft();
            }
            if (this.RightPressed) {
                this.rotateToRight();
            }
       // }
        if (this.ShootPressed && TRE.tickCount % 20 == 0) {
            System.out.println("I have shot the bullet" + this.x + " " + this.y + " Angle is" + this.angle);
            blt = new Bullet(this.x, this.y, angle, TRE.bulletImage);
            blt.setNameOfTank(NameTank);
            this.BulletListing.add(blt);
            TRE.activeobjects.add(blt);
        }
        this.BulletListing.forEach(bullet -> bullet.update());
    }
    public ArrayList<GameObject> getBulletList(){ return this.BulletListing;}
    private void rotateToLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateToRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void movingBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        if(this.move){
            x -= vx;
            y -= vy;}
        BorderChecker();
        movingBounds(x,y);
        this.hBox.setLocation(x,y);
    }


    private void movingForwards() {

        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
       // System.out.println(this.move);
        if(this.move){
            x += vx;
            y += vy;}
        BorderChecker();
        movingBounds(x,y);
    }
    private void movingBounds(int a, int b){
        this.bound.setLocation(a,b);

    }


    private void BorderChecker() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.WORLD_WIDTH - 88) {
            x = GameConstants.WORLD_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.WORLD_HEIGHT - 80) {
            y = GameConstants.WORLD_HEIGHT - 80;
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

    public void setHealthOft2(int health2){ this.HealthOft2 =health2; }
    public int getHealthOft2(){  return this.HealthOft2; }

    public void setHealthOft1(int health1){ this.HealthOft1 =health1; }
    public int getHealthOft1(){ return this.HealthOft1; }

    public void DrawingOfImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.tankImg.getWidth() / 2.0, this.tankImg.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.tankImg, rotation, null);
        g2d.setColor(Color.GREEN);
        //if(blt!=null)blt.DrawingOfImage(g);
        this.BulletListing.forEach(bullet -> bullet.DrawingOfImage(g));
        g2d.drawRect(x,y,this.tankImg.getWidth(),this.tankImg.getHeight());
        g2d.setColor(Color.BLUE);
        g2d.drawRect(x,y,this.tankImg.getWidth(),this.tankImg.getHeight());
    }
    //************************
    public void setMove(boolean move){
        this.move = move;
    }
   /* @Override
    public boolean collide() {
        this.collide = true;
        return this.collide;
    }*/

    public void setName(String NameTank){
        this.NameTank = NameTank;
    }
    public String getName(){
        return this.NameTank;
    }
    @Override
    public String getNameOfObject() {
        return "Tank";
    }

    @Override
    public void collide() {
        this.removingHealth(10);


    }

    private void removingHealth(int i) {
        if (HealthOft1 - i < 0) {
            HealthOft1 = 0; //tank died
        } else {
            HealthOft1 -= i;
        }
    }


    public Rectangle getOffsetBound() {
        System.out.println("In getOffsetBound "+x+" "+y+" "+vx+" "+vy);
        return new Rectangle(x + vx, y + vy, this.tankImg.getWidth(),  this.tankImg.getWidth());
    }
}






