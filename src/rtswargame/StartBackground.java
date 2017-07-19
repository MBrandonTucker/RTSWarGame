package rtswargame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.swing.JPanel;
import static rtswargame.Map.platoon;

public class StartBackground extends JPanel{
    
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    private BufferedImage[] rocks = new BufferedImage[20];
    private int[] rockXPositions = new int[rocks.length];
    private int[] rockYPositions = new int[rocks.length];
    private int[] grassXPositions = new int[rocks.length];
    private int[] grassYPositions = new int[rocks.length];
    Soldier mySoldier;
    
    public StartBackground() throws IOException{
        mySoldier = new Soldier(0,0,0);
        setRockPositions();
    }
    
    protected void draw(Graphics g) throws InterruptedException {
        super.paintComponent(g);
        
        g.setColor(Color.green);
        g.fillRect(6, 0, 1354, 510);
        drawRocksAndGrass(g);
        g.setColor(Color.gray);
        g.fillRect(6, 511, 1354, 256);
        g.setColor(Color.white);
        g.fillRect(430,518,510,175);
        g.setColor(Color.red);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 100)); 
        g.drawString("RTS War Game", 320, 150);
       
        
    }
    
    public void drawRocksAndGrass(Graphics g){
        for(int i = 0; i < rocks.length; i++){
            g.drawImage(rocks[i],rockXPositions[i],rockYPositions[i],null);
            g.drawImage(mySoldier.getGrass(),grassXPositions[i],grassYPositions[i],null);
        }
    }
    
     public void setRockPositions(){
        Random rand = new Random();
        for(int i = 0; i < rocks.length; i++){
            rocks[i] = mySoldier.getRock();
            rockXPositions[i] = rand.nextInt(screen.width - 35);
            rockYPositions[i] = rand.nextInt(screen.height - 330);
            grassXPositions[i] = rand.nextInt(screen.width - 35);
            grassYPositions[i] = rand.nextInt(screen.height - 330);
        }
    }
}
