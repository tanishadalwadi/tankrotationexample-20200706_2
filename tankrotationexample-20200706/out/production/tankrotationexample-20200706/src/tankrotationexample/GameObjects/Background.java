package tankrotationexample.GameObjects;

import tankrotationexample.game.TRE;

import java.awt.*;
import java.util.Objects;
import static javax.imageio.ImageIO.read;

public class Background extends GameObject{

    private Rectangle hitBox;
    public Background(int x,int y){
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
        try{
            this.image = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Background.bmp")));

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        this.active = true;
        //rectangle = new Rectangle(x, y, this.image.getWidth(), this.image.getHeight());

    }
    @Override
    public void update() {

    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.image, x, y, null);
    }

    @Override
    public String getObjectName() {
        return "Background";
    }

    @Override
    public void collision() {

    }

    @Override
    public Rectangle getHitBox() {
        return this.hitBox;
    }

}
