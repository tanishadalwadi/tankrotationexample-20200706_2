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
public class TRE extends JPanel implements Runnable {

    private BufferedImage world;
    public static BufferedImage bulletImage;
    private Graphics2D buffer;
    private Tank t1;
    private Tank t2;
    private Launcher lf;
    private long tick = 0;
    private ArrayList<GameObject> obj;
    public static ArrayList<GameObject> activeobjects;
    public static long tickCount = 0;
    public static int LifeOft1 = 3;
    public static int LifeOft2 = 3;
    private CollisionHandeling ColliHandler;
    public static String winner;

    public TRE() {
    }

    public TRE(Launcher lf) {
        this.lf = lf;
    }


    @Override
    public void run() {
        try {
            //this.resetGame();
            while (LifeOft1 >0 && LifeOft2 >0) {
                this.tick++;
                this.tickCount++;
                this.t1.update(); // update tank
                this.repaint();   // redraw game
                this.t2.update(); // update tank
                this.repaint();

                ColliHandler = new CollisionHandeling(activeobjects);

                ColliHandler.CollisionChecker();
                Thread.sleep(10); //sleep for a few milliseconds
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
        BufferedImage breakableWall = null;
        BufferedImage unBreakableWall = null;
        BufferedImage bulletimg = null;
        BufferedImage backgroundimage = null;
        BufferedImage healthPowerUp = null;
        obj = new ArrayList<>();
        activeobjects = new ArrayList<>();
        try {
            /*
             * note class loaders read files from the out folder (build folder in Netbeans) and not the
             * current working directory.
             */
            backgroundimage = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Background.bmp")));
            t1img = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tank1.png")));
            t2img = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tank2.png")));
            healthPowerUp = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("healthpowerup.png")));
            breakableWall = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("break.png")));
            unBreakableWall = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("unbreak.png")));
            TRE.bulletImage = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("bullet.png")));
            InputStreamReader inputstr = new InputStreamReader(TRE.class.getClassLoader().getResourceAsStream("map/map1"));
            BufferedReader mapReading = new BufferedReader(inputstr);
            String Row = mapReading.readLine();
            if (Row == null)
                throw new IOException(("Data not found"));
            String[] mapInfo = Row.split("\t");
            int NumberOfCols = Integer.parseInt(mapInfo[0]);
            int NumberOfRows = Integer.parseInt(mapInfo[1]);

            System.out.println("Cols " + NumberOfCols + " Rows " + NumberOfRows);
            for (int CurrRow = 0; CurrRow < NumberOfRows; CurrRow++) {
                Row = mapReading.readLine();
                mapInfo = Row.split("\t");
                for (int CurrCol = 0; CurrCol < NumberOfCols; CurrCol++) {
                    switch (mapInfo[CurrCol]) {
                        case "0":
                            this.obj.add(new Background(CurrCol * 32, CurrRow * 32));
                            break;
                        case "2":
                            BreakableWall br = new BreakableWall(CurrCol * 32, CurrRow * 32, breakableWall);
                            this.activeobjects.add(br);
                            this.obj.add(br);
                            break;
                        case "3":
                        case "9":
                            UnBreakableWall ubr = new UnBreakableWall(CurrCol * 32, CurrRow * 32, unBreakableWall);
                            this.obj.add(ubr);
                            break;
                        case "5":
                            HealthPowerUp hpu = new HealthPowerUp(CurrCol * 32, CurrRow * 32, true, healthPowerUp);
                            this.obj.add(hpu);

                            this.activeobjects.add(hpu);
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
        obj.add(t1);
        this.activeobjects.add(t1);
        obj.add(t2);
        this.activeobjects.add(t2);
    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        buffer.setColor(Color.BLACK);
        g2.setColor(Color.cyan);
        this.obj.forEach(obj -> {
            if (obj instanceof Background) {
                obj.DrawingOfImage(buffer);
            }
            if (obj instanceof UnBreakableWall) {
                final UnBreakableWall ubr = (UnBreakableWall) obj;
                ubr.DrawingOfImage(buffer);
            } else if (obj instanceof BreakableWall) {
                final BreakableWall ubr = (BreakableWall) obj;
                ubr.DrawingOfImage(buffer);
            } else if (obj instanceof HealthPowerUp) {
                obj.DrawingOfImage(buffer);

            }
        });
        int t1ValueOfX = t1.getX();
        int t1ValueOfY = t1.getY();
        int t2ValueOfX = t2.getX();
        int t2ValueOfY = t2.getY();
        this.t1.DrawingOfImage(buffer);
        this.t2.DrawingOfImage(buffer);

        if (t1ValueOfX < GameConstants.GAME_SCREEN_WIDTH / 4) {
            t1ValueOfX = GameConstants.GAME_SCREEN_WIDTH / 4;
        }
        if (t2ValueOfX < GameConstants.GAME_SCREEN_WIDTH / 4) {
            t2ValueOfX = GameConstants.GAME_SCREEN_WIDTH / 4;
        }
        if (t1ValueOfX > GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 4) { //240>1296
            t1ValueOfX = GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 4;
        }
        if (t2ValueOfX > GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 4) {
            t2ValueOfX = GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 4;
        }
        if (t1ValueOfY < GameConstants.GAME_SCREEN_HEIGHT / 2) {
            t1ValueOfY = GameConstants.GAME_SCREEN_HEIGHT / 2;
        }
        if (t2ValueOfY < GameConstants.GAME_SCREEN_HEIGHT / 2) {
            t2ValueOfY = GameConstants.GAME_SCREEN_HEIGHT / 2;
        }
        if (t1ValueOfY > GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT / 2) {
            t1ValueOfY = GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT / 2;
        }
        if (t2ValueOfY > GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT / 2) {
            t2ValueOfY = GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT / 2;
        }


        BufferedImage HalfOfLeft = world.getSubimage(t1ValueOfX - GameConstants.GAME_SCREEN_WIDTH / 4, t1ValueOfY - GameConstants.GAME_SCREEN_HEIGHT / 2, GameConstants.GAME_SCREEN_WIDTH / 2, GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage HalfOfRight = world.getSubimage(t2ValueOfX - GameConstants.GAME_SCREEN_WIDTH / 4, t2ValueOfY - GameConstants.GAME_SCREEN_HEIGHT / 2, GameConstants.GAME_SCREEN_WIDTH / 2, GameConstants.GAME_SCREEN_HEIGHT);


        g2.drawImage(HalfOfLeft, 0, 0, null);
        g2.drawImage(HalfOfRight, GameConstants.GAME_SCREEN_WIDTH / 2 + 4, 0, null);
        g2.drawImage(world, GameConstants.GAME_SCREEN_WIDTH / 2 - GameConstants.WORLD_WIDTH / 6 / 2, GameConstants.GAME_SCREEN_HEIGHT - GameConstants.WORLD_HEIGHT / 6, GameConstants.WORLD_WIDTH / 6, GameConstants.WORLD_HEIGHT / 6, null);
        g2.setFont(new Font("Courier", Font.BOLD, 33));
        g2.drawString("Player1 lives: " + LifeOft1, 10, 28);
        g2.drawString("Player2 lives: " + LifeOft2, GameConstants.GAME_SCREEN_WIDTH / 2 + 10, 28);

        g2.drawString("[", 23, 60);
        g2.drawString("[", GameConstants.GAME_SCREEN_WIDTH / 2 + 10, 58);
        g2.drawString("]", 238, 60);
        g2.drawString("]", GameConstants.GAME_SCREEN_WIDTH / 2 + 220, 58);
        g2.setColor(Color.green);
        g2.fillRect(40, 40, 2 * t1.getHealthOft1(), 20);
        g2.fillRect(GameConstants.GAME_SCREEN_WIDTH / 2 + 25, 40, 2 * t2.getHealthOft2(), 20);
        if (LifeOft1 == 0) {
            if (t1.getHealthOft1() <= 0) {
                System.out.println("T1 HEALTH --->> " + t1.getHealthOft2() + " " + LifeOft1);
                g2.drawString("Player1 lives: " + LifeOft1, GameConstants.GAME_SCREEN_WIDTH / 2 + 10, 28);
                g2.drawString("[", GameConstants.GAME_SCREEN_WIDTH / 2 + 10, 58);
                g2.drawString("]", GameConstants.GAME_SCREEN_WIDTH / 2 + 222, 58);
                g2.fillRect(GameConstants.GAME_SCREEN_WIDTH / 2 + 25, 40, 2 * t1.getHealthOft2(), 20);
                g2.setColor(Color.green);
            }
            this.winner = "Player2";
            lf.setFrame("end");
        }
        if (LifeOft2 == 0) {
            if (t2.getHealthOft1() <= 0) {
                System.out.println("T2 HEALTH --->> " + t2.getHealthOft2() + " " + LifeOft2);
                g2.drawString("Player2 lives: " + LifeOft2, GameConstants.GAME_SCREEN_WIDTH / 2 + 10, 28);
                g2.drawString("[", GameConstants.GAME_SCREEN_WIDTH / 2 + 10, 58);
                g2.drawString("]", GameConstants.GAME_SCREEN_WIDTH / 2 + 222, 58);
                g2.fillRect(GameConstants.GAME_SCREEN_WIDTH / 2 + 25, 40, 2 * t2.getHealthOft2(), 20);
                g2.setColor(Color.green);
                }
            this.winner = "Player1";
            lf.setFrame("end");
        }
    }
}
