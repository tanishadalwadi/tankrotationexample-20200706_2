package tankrotationexample.menus;

import tankrotationexample.GameConstants;
import tankrotationexample.Launcher;
import tankrotationexample.game.TRE;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class EndGamePanel extends JPanel {

    private BufferedImage menuBackground;
    private JButton end;
    private JButton exit;
    private Launcher lf;
    private JLabel l;
    public EndGamePanel(Launcher lf,String s) {
        //System.out.println("WINNER IN EGP "+s );
        this.lf = lf;
        this.l = l;
        try {
            menuBackground = ImageIO.read(this.getClass().getClassLoader().getResource("title.png"));
        } catch (IOException e) {
            System.out.println("Error cant read menu background");
            e.printStackTrace();
            System.exit(-3);
        }
        this.setBackground(Color.BLACK);
        this.setLayout(null);
        end = new JButton( s +" has won..!!!!");
        end.setFont(new Font("Courier New", Font.BOLD ,24));
        end.setBounds(95,300,300,50);
        end.addActionListener((actionEvent -> {
            this.lf.setFrame("end");
        }));

        exit = new JButton("Exit");
        exit.setFont(new Font("Courier New", Font.BOLD ,24));
        exit.setBounds(150,400,175,50);
        exit.addActionListener((actionEvent -> {
            this.lf.closeGame();
        }));
        this.add(end);
        this.add(exit);

    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.menuBackground,0,0,null);
    }
}
