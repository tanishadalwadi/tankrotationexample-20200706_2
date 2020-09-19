package tankrotationexample.GameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Wall extends GameObject {

    private static BufferedImage background_img;

    public abstract void drawImage(Graphics g) ;

    }
