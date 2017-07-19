//Brandon Tucker Senior Project Spring 2017

package rtswargame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import static rtswargame.RTSWarGame.controlPanel;


public class RTSWarGame extends JPanel {
    
    static JFrame gameFrame = new JFrame();
    static Map battleField;
    static StartBackground background;
    static Cursor WarCursor;
    static JLabel startMenu = new JLabel("Starting Units");
    static JPanel controlPanel = new JPanel();
    static int play = JOptionPane.YES_NO_OPTION;
    static boolean mapAdded = false;
    static boolean mapLoading = false;
    static int loadingCounter = 0;
    static Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    


    
    
    public static void main(String[] args) throws IOException {
        
        background = new StartBackground();
        RTSWarGame WarGame = new RTSWarGame();
        JScrollPane scrPane = new JScrollPane(WarGame);
        gameFrame.add(scrPane);
        gameFrame.add(WarGame);
        gameFrame.setMinimumSize(new Dimension(400, 300));
        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        
        if(play == JOptionPane.YES_OPTION){
            
        
            Object[] possibilities = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,
                                        17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32};
            int goodStartCount = (int)JOptionPane.showInputDialog(
                        gameFrame,
                        "Enter starting number of  your troops",
                        "Set handicap",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        possibilities,
                        1);
            int badStartCount = (int)JOptionPane.showInputDialog(
                        gameFrame,
                        "Enter starting number of enemy troops",
                        "Set handicap",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        possibilities,
                        1);
            mapLoading = true;
            battleField = new Map(goodStartCount,badStartCount);
            mapLoading = false;
            loadingCounter = 0;
            WarCursor = battleField.getCursor();
            
            gameFrame.setCursor(WarCursor);
            mapAdded = true;
            gameFrame.add(WarGame);
            gameFrame.addKeyListener(WarGame.battleField.myKeys);
            gameFrame.addMouseListener(battleField);
            gameFrame.addMouseMotionListener(battleField);
            
            
            
        }
        gameFrame.setMinimumSize(new Dimension(400, 300));
        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        System.out.println(battleField.defeat());
       /* play = JOptionPane.showConfirmDialog(
                    null,
                    "Would you like to play again?",
                    "Play Again?",
                    JOptionPane.YES_NO_OPTION);*/
                
        
        
        
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        try {
            background.draw(g);
            if(mapAdded)
                battleField.draw(g);
        } catch (InterruptedException ex) {
            Logger.getLogger(RTSWarGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(mapAdded){
            WarCursor = battleField.getCursor();
            gameFrame.setCursor(WarCursor);
        }
        /*if(mapLoading){
            
            loadingCounter++;
            if(loadingCounter < 70){
                g.drawString("Loading", 480, 250);
            }
            if(loadingCounter > 70 && loadingCounter < 140){
                g.drawString("Loading.", 480, 250);
            }
            if(loadingCounter > 140 && loadingCounter < 210){
                g.drawString("Loading..", 480, 250);
            }
            if(loadingCounter > 210 && loadingCounter < 280){
                g.drawString("Loading...", 480, 250);
            }
            if(loadingCounter > 280){
                g.drawString("Loading", 480, 250);
                loadingCounter = 0;
            }
        }*/
        
        repaint();
    }
    
    
}
