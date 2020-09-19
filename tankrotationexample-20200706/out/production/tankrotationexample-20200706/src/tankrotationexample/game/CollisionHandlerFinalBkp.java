package tankrotationexample.game;
import tankrotationexample.GameObjects.*;

import java.awt.*;
import java.util.ArrayList;

public class CollisionHandlerFinalBkp {
    private ArrayList<GameObject> objects;
    //private Tank t1;
    //private Tank t2;
    private ArrayList<GameObject> healthobjects;
    private ArrayList<GameObject> breakwallobjects;

    public CollisionHandlerFinalBkp(ArrayList<GameObject> objects) {
        this.objects = objects;
        this.healthobjects = new ArrayList<>();
        this.breakwallobjects = new ArrayList<>();
    }

    /*public void findTanks2() {
        for (GameObject i : objects) {
            if (i instanceof Tank) {
                Tank tmp = (Tank) i;
                if (tmp.getName().equals("Tank 1"))
                    t1 = tmp;
                else t2 = tmp;
            }
        }
    }*/

    public Tank findTanks(String name) {
        Tank tank = null;
        for (GameObject i : objects) {
            if (i instanceof Tank) {
                Tank tmp = (Tank) i;
                if (tmp.getName().equals(name))
                    tank = tmp;
            }
        }
        return tank;
    }

    public void getWalls() {
        for (GameObject i : objects) {
            if (i instanceof BreakWall)
                this.breakwallobjects.add(i);
        }
    }

    public void findhealth() {
        for (GameObject i : objects) {
            if (i instanceof HealthPowerUp)
                this.healthobjects.add(i);
        }
    }

    public void checkForCollision() {
        // Tank t1 = null,t2=null;
        Tank t1 = findTanks("Tank 1");
        Tank t2 = findTanks("Tank 2");
        findhealth();
        Rectangle t1Box = t1.getHitBox();
        Rectangle t2Box = t2.getHitBox();
        for (GameObject obji : objects) {
            if(obji instanceof Bullet) {
                Bullet b = (Bullet) obji;
                System.out.println("T1 value "+t1Box);
                //System.out.println("T2 value "+t2);
                System.out.println("BULLET VALUE "+b.getHitBox());

                // T1 shoots bullet -->> Reduce T2 life
                if ((b.getHitBox().intersects(t2Box)) && (b.tankNameForBullet().equals("Tank 1"))) {
                    System.out.println("BULLET VALUE "+b.getHitBox());
                    t2.setHealth2(t2.getHealth2() - 1);
                    System.out.println("Health reduced for (t2) after " + t2.getHealth2());
                    if (t2.getHealth2() == 0) {
                        TRE.t2life--;
                        t2.setHealth2(100);
                    }
                    break;
                }
                // T2 shoots bullet -->> Reduce T1 life
               else if ((b.getHitBox().intersects(t1Box)) && (b.tankNameForBullet().equals("Tank 2"))) {
                    System.out.println("In else");
                    System.out.println("BULLET VALUE "+b.getHitBox());
                    t1.setHealth1(t1.getHealth1() - 1);
                    System.out.println("Health reduced for (t1) after " + t1.getHealth1());
                    if (t1.getHealth1() == 0) {
                        TRE.t1life--;
                        t1.setHealth1(100);
                    }
                    break;
                }
            }
            //Checking for Health Powerups and increasing the life and health if its less than the original health & lives
            for (GameObject hobj : healthobjects) {
                if (t1.getX()  == hobj.getX() || t1.getX()  == hobj.getX() || t1.getY()  == hobj.getY() || t1.getY()  == hobj.getY()) {
                    if (t1.getHealth1() < 100) {
                        System.out.println("Health (t1)" + t1.getHealth1());
                        TRE.t1life++;
                        t1.setHealth1(100);
                    }
                } else if (t2.getX()  == hobj.getX() || t2.getX()  == hobj.getX() || t2.getY()  == hobj.getY() || t2.getY()  == hobj.getY()) {
                    if (t2.getHealth2() < 100) {
                        System.out.println("Health (t2)" + t2.getHealth2());
                        TRE.t2life++;
                        t2.setHealth2(100);
                    }
                }
            }
        } //for
    } // Method


}
