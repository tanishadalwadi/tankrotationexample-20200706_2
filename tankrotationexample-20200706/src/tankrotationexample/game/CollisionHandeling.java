package tankrotationexample.game;
import tankrotationexample.GameObjects.*;

import java.util.ArrayList;

public class CollisionHandeling {
    private ArrayList<GameObject> obj;
    private Tank t1;
    private Tank t2;
    private ArrayList<GameObject> hobj;
    private ArrayList<GameObject> brlWallObj;

    public CollisionHandeling(ArrayList<GameObject> obj) {
        this.obj = obj;
        this.hobj = new ArrayList<>();
        this.brlWallObj = new ArrayList<>();
    }


    public void getWalls() {
        for (GameObject GoBj : obj) {
            if (GoBj instanceof BreakableWall)
                this.brlWallObj.add(GoBj);
        }
    }
    public void TankFinder() {
        for (GameObject GoBj : obj) {
            if (GoBj instanceof Tank) {
                Tank temporary = (Tank) GoBj;
                if (temporary.getName().equals("Tank 1"))
                    t1 = temporary;
                else t2 = temporary;
            }
        }
    }

    public void HealthFinder() {
        for (GameObject GoBj : obj) {
            if (GoBj instanceof HealthPowerUp)
                this.hobj.add(GoBj);
        }
    }

    public void CollisionChecker() {
        for (int k = 0; k < obj.size(); k++) {
            for (int l = 0; l < obj.size(); l++) {
                GameObject ObJk = obj.get(k);
                GameObject obJl = obj.get(l);
                    if (ObJk instanceof Bullet && obJl instanceof Tank && (((Bullet) ObJk).NameOfTankForBullet()!=(((Tank) obJl).getName())) && !((Bullet) ObJk). getCollided()) {
                     System.out.println( "Tank details "+ ((Tank) obJl).getName() );
                        if (ObJk.getHBox().intersects(obJl.getHBox())) {
                            System.out.println("I "+((Tank) obJl).getName());
                            if(((Tank) obJl).getName()=="Tank 2"){
                                ((Tank) obJl).setHealthOft2(((Tank) obJl).getHealthOft2() - 1);
                                System.out.println("Health reduced for (t2) after " + ((Tank) obJl).getHealthOft2());
                                if (((Tank) obJl).getHealthOft2() == 0) {
                                    TRE.LifeOft2--;
                                    ((Tank) obJl).setHealthOft2(100);
                                }
                                break;
                            } else  if(((Tank) obJl).getName()=="Tank 1"){
                                ((Tank) obJl).setHealthOft1(((Tank) obJl).getHealthOft1() - 1);
                                System.out.println("Health reduced for (t1) after " + ((Tank) obJl).getHealthOft1());
                                if (((Tank) obJl).getHealthOft1() == 0) {
                                    TRE.LifeOft1--;
                                    ((Tank) obJl).setHealthOft1(100);
                                }
                                break;
                            }
                        }
                        break;  //2
                }
            }

        }
    }

}







