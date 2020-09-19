/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankrotationexample.game;

import tankrotationexample.GameConstants;
import tankrotationexample.GameObjects.*;
import tankrotationexample.Launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

/**
 *
 * @author anthony-pc
 */
public class TREbkp extends JPanel implements Runnable {

    private BufferedImage world;
    public static BufferedImage bulletImage;
    private Graphics2D buffer;
    //private JFrame jFrame;
    private Tank t1;
    private Tank t2;
    private Launcher lf;
    private long tick = 0;
    private ArrayList<GameObject> objects;
    public static long tickCount = 0;
    private int t1life = 3;
    private int t2life = 3;
    private CollisionHandler collisionhandler;


    public TREbkp(Launcher lf) {
        this.lf = lf;
    }

    @Override
    public void run() {
        try {
            this.resetGame();
            while (true) {
                this.tick++;
                this.tickCount++;
                this.t1.update(); // update tank
                this.repaint();   // redraw game
                this.t2.update(); // update tank
                this.repaint();
               /* ArrayList<GameObject> activegameobjects = new ArrayList<>();
                ArrayList<GameObject> bullets = t1.getBulletList();
                System.out.println("bullets "+bullets);
                if(bullets.size()!=0){
                    for(int i=0;i<bullets.size();i++)
                        activegameobjects.add(bullets.get(i));
                }
                for(int i=0;i<activegameobjects.size();i++){
                    for(int j=i;j<activegameobjects.size();j++){
                        GameObject gamebbject1=activegameobjects.get(i);
                        GameObject gamebbject2=activegameobjects.get(i);
                        collisionhandler = new CollisionHandler(gamebbject1,gamebbject2);
                        collisionhandler.checkForCollision();
                }}*/
               /* if(this.t1.getHitbox().intersects(this.t2.getHitbox())){
                    System.out.println("Tanks have collided");
                }*/
                Thread.sleep(10); //sleep for a few milliseconds

                /*
                 * simulate an end game event
                 * we will do this with by ending the game when drawn 2000 frames have been drawn
                 */
               /*if(this.tick > 2000){
                   this.lf.setFrame("end");
                   return;
               }*/
            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame() {
        this.tick = 0;
    }


    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize() {
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH,
                GameConstants.WORLD_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        BufferedImage t1img = null;
        BufferedImage t2img = null;
        BufferedImage breakWall = null;
        BufferedImage unBreakWall = null;
        BufferedImage bulletimg = null;
        BufferedImage backgroundimage = null;
        objects = new ArrayList<>();

        try {
            /*
             * note class loaders read files from the out folder (build folder in Netbeans) and not the
             * current working directory.
             */
            backgroundimage = read(Objects.requireNonNull(TREbkp.class.getClassLoader().getResource("Background.bmp")));
            t1img = read(Objects.requireNonNull(TREbkp.class.getClassLoader().getResource("tank1.png")));
            t2img = read(Objects.requireNonNull(TREbkp.class.getClassLoader().getResource("tank2.png")));
            breakWall = read(Objects.requireNonNull(TREbkp.class.getClassLoader().getResource("break.png")));
            unBreakWall = read(Objects.requireNonNull(TREbkp.class.getClassLoader().getResource("unbreak.png")));
            TREbkp.bulletImage = read(Objects.requireNonNull(TREbkp.class.getClassLoader().getResource("bullet.png")));
            InputStreamReader isr = new InputStreamReader(TREbkp.class.getClassLoader().getResourceAsStream("map/map1"));
            BufferedReader mapReader = new BufferedReader(isr);
            String row = mapReader.readLine();
            if (row == null)
                throw new IOException(("No data found"));
            String[] mapInfo = row.split("\t");
            int numCols = Integer.parseInt(mapInfo[0]);
            int numRows = Integer.parseInt(mapInfo[1]);

            System.out.println("Cols " + numCols + " Rows " + numRows);
            for (int curRow = 0; curRow < numRows; curRow++) {
                row = mapReader.readLine();
                mapInfo = row.split("\t");
                for (int curCol = 0; curCol < numCols; curCol++) {
                    switch (mapInfo[curCol]) {
                        case "0":
                            this.objects.add(new Background(curCol * 32, curRow * 32));
                            break;

                        case "2":
                            BreakWall br = new BreakWall(curCol * 32, curRow * 32, breakWall);
                            this.objects.add(br);
                            break;
                        case "3":
                        case "9":
                            UnBreakWall ubr = new UnBreakWall(curCol * 32, curRow * 32, unBreakWall);
                            this.objects.add(ubr);
                            break;
                    }
                }
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        t1 = new Tank(200, 150, 0, 0, (short) 0, t1img,true);
        t2 = new Tank(1200, 1700, 0, 0, (short) 180, t2img,true);
        t1.setName("Tank 1");
        t2.setName("Tank 2");
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.lf.getJf().addKeyListener(tc1);
        this.lf.getJf().addKeyListener(tc2);
        this.setBackground(Color.WHITE);
        objects.add(t1);
        objects.add(t2);

    }
//activegameobjects
    public ArrayList<GameObject> getGameObjects(ArrayList<GameObject>objects){
        ArrayList<GameObject> activegameobjects = new ArrayList<>();
        for(int i=0;i<objects.size();i++){
            if(!(objects.get(i) instanceof Background) ){
                activegameobjects.add(objects.get(i));
            }
        }
        return activegameobjects;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        buffer.setColor(Color.BLACK);
        g2.setColor(Color.cyan);
        this.objects.forEach(obj -> {
            if (obj instanceof Background) {
                obj.drawImage(buffer);
            }
            if (obj instanceof UnBreakWall) {
                final UnBreakWall ubr = (UnBreakWall) obj;
                ubr.drawImage(buffer);
            } else if (obj instanceof BreakWall) {
                final BreakWall ubr = (BreakWall) obj;
                ubr.drawImage(buffer);
            }
        });
        int t1Xvalue = t1.getX();
        int t1Yvalue = t1.getY();
        int t2Xvalue = t2.getX();
        int t2Yvalue = t2.getY();
        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);

      if (t1Xvalue < GameConstants.GAME_SCREEN_WIDTH/4) {
            t1Xvalue = GameConstants.GAME_SCREEN_WIDTH/4;
      }
        if (t2Xvalue < GameConstants.GAME_SCREEN_WIDTH/4) {
            t2Xvalue = GameConstants.GAME_SCREEN_WIDTH/4;
        }
        if (t1Xvalue > GameConstants.WORLD_WIDTH-GameConstants.GAME_SCREEN_WIDTH/4) { //240>1296
            t1Xvalue = GameConstants.WORLD_WIDTH-GameConstants.GAME_SCREEN_WIDTH/4;
        }
        if (t2Xvalue > GameConstants.WORLD_WIDTH-GameConstants.GAME_SCREEN_WIDTH/4) {
            t2Xvalue = GameConstants.WORLD_WIDTH-GameConstants.GAME_SCREEN_WIDTH/4;
        }
        if (t1Yvalue < GameConstants.GAME_SCREEN_HEIGHT/2) {
            t1Yvalue = GameConstants.GAME_SCREEN_HEIGHT/2;
        }
        if (t2Yvalue < GameConstants.GAME_SCREEN_HEIGHT/2) {
            t2Yvalue = GameConstants.GAME_SCREEN_HEIGHT/2;
        }
        if (t1Yvalue > GameConstants.WORLD_HEIGHT-GameConstants.GAME_SCREEN_HEIGHT/2) {
            t1Yvalue = GameConstants.WORLD_HEIGHT-GameConstants.GAME_SCREEN_HEIGHT/2;
        }
        if (t2Yvalue > GameConstants.WORLD_HEIGHT-GameConstants.GAME_SCREEN_HEIGHT/2) {
            t2Yvalue = GameConstants.WORLD_HEIGHT-GameConstants.GAME_SCREEN_HEIGHT/2;
        }

            System.out.println("Boundary values:" + GameConstants.GAME_SCREEN_HEIGHT + " " + GameConstants.GAME_SCREEN_WIDTH);
            System.out.println("T1 details " + t1Xvalue + " " + t1Yvalue);
            System.out.println("T2 details " + t2Xvalue + " " + t2Yvalue);
            BufferedImage leftHalf = world.getSubimage(t1Xvalue - GameConstants.GAME_SCREEN_WIDTH / 4, t1Yvalue - GameConstants.GAME_SCREEN_HEIGHT / 2, GameConstants.GAME_SCREEN_WIDTH / 2, GameConstants.GAME_SCREEN_HEIGHT);
            BufferedImage rightHalf = world.getSubimage(t2Xvalue - GameConstants.GAME_SCREEN_WIDTH / 4, t2Yvalue - GameConstants.GAME_SCREEN_HEIGHT / 2, GameConstants.GAME_SCREEN_WIDTH / 2, GameConstants.GAME_SCREEN_HEIGHT);


            g2.drawImage(leftHalf, 0, 0, null);
            g2.drawImage(rightHalf, GameConstants.GAME_SCREEN_WIDTH / 2 + 4, 0, null);
            g2.drawImage(world, GameConstants.GAME_SCREEN_WIDTH / 2 - GameConstants.WORLD_WIDTH / 6 / 2, GameConstants.GAME_SCREEN_HEIGHT - GameConstants.WORLD_HEIGHT / 6, GameConstants.WORLD_WIDTH / 6, GameConstants.WORLD_HEIGHT / 6, null);
            g2.setFont(new Font("Courier",  Font.BOLD, 33));
            g2.drawString("Player1 lives: " + t1life, 10, 28);
            g2.drawString("Player2 lives: " + t2life, GameConstants.GAME_SCREEN_WIDTH / 2 + 10, 28);

            g2.drawString("[", 23, 60);
            g2.drawString("[", GameConstants.GAME_SCREEN_WIDTH / 2 + 10, 58);
            g2.drawString("]", 238, 60);
            g2.drawString("]", GameConstants.GAME_SCREEN_WIDTH / 2 + 220, 58);
            g2.setColor(Color.green);
            g2.fillRect(40, 40, 2 * t1.getHealth1(), 20);
            g2.fillRect(GameConstants.GAME_SCREEN_WIDTH  / 2 + 25, 40, 2 * t2.getHealth1(), 20);
        }
    }
