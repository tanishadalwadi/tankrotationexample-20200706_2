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
    private Bullet b;
    private ArrayList<GameObject> bulletlist;
    private Rectangle bounds;
    private int lives;
    private int tickCount;
    private Rectangle hitBox; //ADded
    private String tankanme;
    public static int health1 = 100;
    public static int health2 = 100;
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
        this.bounds= new Rectangle(this.tankImg.getWidth(),this.tankImg.getHeight());
        this.bulletlist=new ArrayList<>();
        this.hitBox = new Rectangle(x,y,this.tankImg.getWidth(),this.tankImg.getHeight());
    }

    public Rectangle getHitbox(){ //ADDED
        return this.hitBox.getBounds();
    }

    @Override
    public Rectangle getHitBox() {
        return this.hitBox;
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
                this.moveForwards();
            }
            if (this.DownPressed) {
                this.moveBackwards();
            }

            if (this.LeftPressed) {
                this.rotateLeft();
            }
            if (this.RightPressed) {
                this.rotateRight();
            }
       // }
        if (this.ShootPressed && TRE.tickCount % 20 == 0) {
            System.out.println("I have shot the bullet" + this.x + " " + this.y + " Angle is" + this.angle);
            b = new Bullet(this.x, this.y, angle, TRE.bulletImage);
            b.setTankName(tankanme);
            this.bulletlist.add(b);
            TRE.activeobjects.add(b);
        }
        this.bulletlist.forEach(bullet -> bullet.update());
    }
    public ArrayList<GameObject> getBulletList(){ return this.bulletlist;}
    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        if(this.move){
            x -= vx;
            y -= vy;}
        checkBorder();
        moveBound(x,y);
        this.hitBox.setLocation(x,y);
    }


    private void moveForwards() {

        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
       // System.out.println(this.move);
        if(this.move){
            x += vx;
            y += vy;}
        checkBorder();
        moveBound(x,y);
    }
    private void moveBound(int a,int b){
        this.bounds.setLocation(a,b);

    }
   /* public void setCanMoveUp(boolean canMoveUp) {
        this.canMoveUp = canMoveUp;
    }

    public void setCanMoveDown(boolean canMoveDown) {
        this.canMoveDown = canMoveDown;
    }

    public void setCanMoveLeft(boolean canMoveLeft) {
        this.canMoveLeft = canMoveLeft;
    }

    public void setCanMoveRight(boolean canMoveRight) {
        this.canMoveRight = canMoveRight;
    }
*/

    private void checkBorder() {
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

    public void setHealth2(int health2){ this.health2 =health2; }
    public int getHealth2(){  return this.health2; }

    public void setHealth1(int health1){ this.health1 =health1; }
    public int getHealth1(){ return this.health1; }

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.tankImg.getWidth() / 2.0, this.tankImg.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.tankImg, rotation, null);
        g2d.setColor(Color.GREEN);
        //if(b!=null)b.drawImage(g);
        this.bulletlist.forEach(bullet -> bullet.drawImage(g));
        g2d.drawRect(x,y,this.tankImg.getWidth(),this.tankImg.getHeight());
        g2d.setColor(Color.BLUE);
        g2d.drawRect(x,y,this.tankImg.getWidth(),this.tankImg.getHeight());
    }
    //************************
    public void setMove(boolean move){
        this.move = move;
    }
   /* @Override
    public boolean collision() {
        this.collision = true;
        return this.collision;
    }*/

    public void setName(String tankname){
        this.tankanme = tankname;
    }
    public String getName(){
        return this.tankanme ;
    }
    @Override
    public String getObjectName() {
        return "Tank";
    }

    @Override
    public void collision() {
        this.removeHealth(10);


    }

    private void removeHealth(int i) {
        if (health1 - i < 0) {
            health1 = 0; //tank died
        } else {
            health1 -= i;
        }
    }


    public Rectangle getOffsetBounds() {
        System.out.println("In getOffsetBounds "+x+" "+y+" "+vx+" "+vy);
        return new Rectangle(x + vx, y + vy, this.tankImg.getWidth(),  this.tankImg.getWidth());
    }
}






