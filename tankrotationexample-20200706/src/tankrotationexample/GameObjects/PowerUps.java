package tankrotationexample.GameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class PowerUps extends GameObject{
    public abstract void DrawingOfImage(Graphics g) ;
    private boolean active;
    private BufferedImage powerUpImg;

    public PowerUps(int x,int y){
        this.x = x;
        this.y = y;
        this.rectangle = new Rectangle(x, y, 40, 40);

   }


}


