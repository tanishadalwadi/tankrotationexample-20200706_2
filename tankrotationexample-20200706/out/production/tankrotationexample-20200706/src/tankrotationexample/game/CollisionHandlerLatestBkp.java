package tankrotationexample.game;
import tankrotationexample.GameObjects.*;

import java.awt.*;
import java.util.ArrayList;

public class CollisionHandlerLatestBkp{
    private ArrayList<GameObject> objects;
    private Tank t1;
    private Tank t2;
    private ArrayList<GameObject> healthobjects;
    private ArrayList<GameObject> breakwallobjects;

    public CollisionHandlerLatestBkp(ArrayList<GameObject> objects) {
        this.objects = objects;
        this.healthobjects = new ArrayList<>();
        this.breakwallobjects= new ArrayList<>();
    }

    public void findTanks() {
        for (GameObject i : objects) {
            if (i instanceof Tank) {
                Tank tmp = (Tank) i;
                if (tmp.getName().equals("Tank 1"))
                    t1 = tmp;
                else t2 = tmp;
            }
        }
    }

    public void getWalls(){
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
        //findTanks();
        findhealth();
        Rectangle t1Box = t1.getHitBox();
        Rectangle t2Box = t2.getHitBox();
        for (GameObject obji : objects) {


            //Checking for Bullets intersect tanks and redcuing the health and life of the oppenent's tank
            if (obji instanceof Bullet && obji.getHitBox().intersects(t2Box) || obji instanceof Bullet && obji.getHitBox().intersects(t1Box)) {
                if (obji.getY() >= t2.getX()) {
                    System.out.println(((Bullet) obji).tankNameForBullet());
                    if (((Bullet) obji).tankNameForBullet().equals(t1.getName())) {
                        System.out.println("Health reduced for (t2) bfr " + t2.getHealth2());
                        t2.setHealth2(t2.getHealth2() - 1);
                        System.out.println("Health reduced for (t2) after " + t2.getHealth2());
                        if (t2.getHealth2() == 0) {
                            TRE.t2life--;
                            t2.setHealth2(100);
                        }
                        break;
                    } else {
                        System.out.println("Health reduced bfr for (t1) " + t1.getHealth1());
                        t1.setHealth1(t1.getHealth1() - 1);
                        System.out.println("Health reduced after  for (t1)" + t1.getHealth1());
                        if (t1.getHealth1() == 0) {
                            TRE.t1life--;
                            t1.setHealth1(100);
                        }
                        break;
                    }
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
            System.out.println("T1 " + t1.getX()+" "+t1.getY());
            //Should check for walls
            for(GameObject o:breakwallobjects) {
                if (t1.getX()  == o.getX() || t1.getX()  == o.getX() || t1.getY()  == o.getY() || t1.getY()  == o.getY()) {
                    System.out.println("Intersected"+ t1.getX()+" "+t1.getY());
                }


            }
        }
    }


}







