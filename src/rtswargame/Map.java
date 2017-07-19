package rtswargame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import static java.awt.Frame.CROSSHAIR_CURSOR;
import static java.awt.Frame.HAND_CURSOR;
import java.awt.Graphics;
import java.awt.List;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import javax.lang.model.element.Element;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import static rtswargame.RTSWarGame.gameFrame;


public class Map extends JPanel implements MouseListener, MouseMotionListener, KeyListener{
   static private Timer timer;
   static  Soldier[] platoon;
   static private int mouseX = 0;
   static private int mouseY = 0;
   static private int mX = 0;
   static private int mY = 0;
   static private int closestID = 0;
   static private boolean mouseclicked = false;
   static private boolean mouseDragged = false;
   private static final Color clear = new Color(0, 0, 0, 0);
   static private Color selectorColor = clear;
   static private int selectorX;
   static private int selectorY;
   static private int selectX;
   static private int selectY;
   static private int selectXLength;
   static private int selectYLength;
   private int[] xPosArray;
   private int[] yPosArray;
   private int[] xPosStatusArray;
   private int[] yPosStatusArray;
   private Soldier[] selectedPlatoon = new Soldier[1];
   private int numSelected = 0;
   private int mouseLX;
   private int mouseYX;
   private boolean mouseClickedinPlatoon = false;
   int minX;
   int maxX;
   int minY;
   int maxY;
   Keys myKeys = new Keys();
   boolean attack = false;
   Cursor myCursor = new Cursor(Cursor.DEFAULT_CURSOR);
   Cursor customCursor2;
   private int healMarker = 0;
   private boolean mouseClickX = false;
   private int mouseClickTimer = 0;
   Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
   private BufferedImage[] rocks = new BufferedImage[20];
   private int[] rockXPositions = new int[rocks.length];
   private int[] rockYPositions = new int[rocks.length];
   private int[] grassXPositions = new int[rocks.length];
   private int[] grassYPositions = new int[rocks.length];
   private boolean gameOver = false;
   private boolean Victory = false;
   private boolean Defeat = false;
   private boolean mouseClickedOnEenemy = false;
   private int enemyClickedTimer = 0;
   private boolean GodMode = false;
   private boolean NukeMode = false;
   private boolean nukeDropped = false;
   private int nukeTimer = 0;
   private boolean nukeFlashed = false;
   private int nukeFlashTimer = 0;
   private boolean hasteCheat = false;
   private boolean rainingArtillery = false;
   private int artilleryTimer = 0;
   private boolean artilleryBlast = false;
   private int blastX = 0;
   private int blastY = 0;
   private boolean wait = false;
   private int waitTimer = 20;
   int waitTime = 30;
   private boolean graveMode = false;
   private boolean loading = false;
   private boolean survivalMode = false;
   
   
   

    public Map(int goodCount, int badCount){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Point cursorHotSpot = new Point(0,0);
        setMap(goodCount,badCount);
        TimeEvent T = new TimeEvent();
        timer = new Timer(15,T);
        timer.start();
        
        addMouseMotionListener(this);
        setVisible(true);
        customCursor2 = toolkit.createCustomCursor(platoon[0].getRTSCursor(), cursorHotSpot, "Cursor");
        myCursor = customCursor2;
        setRockPositions();
    }
    
    public Cursor getCursor(){
        return myCursor;
    }
    
    boolean isGameOver(){
        return gameOver;
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
        
        if(numSelected!=0){ //Draw status Bar
            fillXPosStatusArray();
            fillYPosStatusArray();
            for(int i = 0; i < selectedPlatoon.length; i++){
                g.drawImage(selectedPlatoon[i].getSoldierHealth(selectedPlatoon[i].getHealthBarImg()),
                xPosStatusArray[i],yPosStatusArray[i]-9,null);
                if(selectedPlatoon[i].isAlive()){
                    g.drawImage(selectedPlatoon[i].getIcon(),xPosStatusArray[i],yPosStatusArray[i],null);
                }
                else{
                    g.drawImage(selectedPlatoon[i].getDeadIcon(),xPosStatusArray[i],yPosStatusArray[i],null);
                }
            }
        }
        for(int i = 0; i < platoon.length; i++){
            if(!platoon[i].isAlive()){
                platoon[i].draw(g,Integer.toString(i));
            }
        }
        for(int i = 0; i < platoon.length; i++){
                if(platoon[i].isAlive()){
                    platoon[i].draw(g,Integer.toString(i));
                } 
                if(isMouseOverSoldier(platoon[i])){
                    platoon[i].setIsMouseOverMe(true);
                }
                else{
                    platoon[i].setIsMouseOverMe(false);
                }
                //g.drawOval(platoon[i].getX() + 10, platoon[i].getY() - 10, 5, 5);
            }   
        g.setColor(Color.blue);
        if(mouseDragged){
            if(mX > selectorX){
                selectX = selectorX;
                selectY = selectorY;
                selectXLength = mX - selectorX;
                selectYLength = mY - selectorY;
                g.drawRect(selectX, selectY, selectXLength, selectYLength);
            }
            if(mY < selectorY){
                selectX = selectorX;
                selectY = mY;
                selectXLength = mX-selectorX;
                selectYLength = selectorY-mY;
                g.drawRect(selectX, selectY, selectXLength, selectYLength);
            }            
            if(mX < selectorX){
                selectX = selectorX - (selectorX - mX);
                selectY = selectorY;
                selectXLength = selectorX-mX;
                selectYLength = mY-selectorY;
                g.drawRect(selectX, selectY, selectXLength, selectYLength);
            }           
            if(mY < selectorY && mX < selectorX){
                selectX = selectorX - (selectorX - mX);
                selectY = mY;
                selectXLength = selectorX-mX;
                selectYLength = selectorY-mY;
                g.drawRect(selectX, selectY, selectXLength, selectYLength);
            }
        }
        if(mouseClickX){
            
            g.drawImage(platoon[0].getTarget(),mouseX-15,mouseY-15,null);
            //mouseClickX = false;
        }
        if(GodMode && mouseClickedOnEenemy){
            
            g.drawImage(platoon[0].getLightning(),mouseX-15,mouseY-50,null);
            //mouseClickX = false;
        }
        if(nukeFlashed&& NukeMode){
            g.drawImage(platoon[0].getFlash(),mouseX-100,mouseY-100,null);
        }
        if(nukeDropped && NukeMode){
            g.drawImage(platoon[0].getNuke(),mouseX-100,mouseY-100,null);
        }
        if(rainingArtillery && artilleryBlast){
            if(artilleryTimer < 20)
                g.drawImage(platoon[0].getArtillery(),blastX-25,blastY-25,null);
        }
        
        
        if(victory()){

            g.setFont(new Font("TimesRoman", Font.PLAIN, 100)); 
            g.drawString("Victory!", 520, 200);
            
        }
        
        if(defeat()){
            g.setColor(Color.red);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 100)); 
            g.drawString("Defeat!", 520, 200);
            
        }
        if(loading){
            
        }
        
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public class Keys extends KeyAdapter{
         @Override
         public void keyPressed(KeyEvent e){
            int code = e.getKeyCode();
            if(code == KeyEvent.VK_A){
                //System.out.println("a pressed");

                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Point cursorHotSpot = new Point(0,0);
                
                for (Soldier platoon1 : platoon) {
                    if(platoon1.isSelected() == 1){
                        Cursor customCursor = toolkit.createCustomCursor(platoon1.getAttackSymbol(), cursorHotSpot, "Cursor");
                        
                        myCursor = customCursor;
                        platoon1.setSet(true);
                    }
                }
            }
            if(code == KeyEvent.VK_ENTER){
                 String cheatCode = (String)JOptionPane.showInputDialog(
                                gameFrame,
                                "Enter Cheat Code",
                                "Cheats",
                                JOptionPane.PLAIN_MESSAGE,
                                null,
                                null,
                                null);
                 if("GodMode".equals(cheatCode)){
                     GodMode = true;
                 }
                 if("NukeMode".equals(cheatCode)){
                     NukeMode = true;
                 }
                 if("needforspeed".equals(cheatCode)){
                     hasteCheat = true;
                     hastePlatoon();
                 }
                 if("badBreath".equals(cheatCode)){
                     feedPlatoonOnions();
                 }
                 if("callInTheBigGuns".equals(cheatCode)){
                     rainingArtillery = true;
                     Random rand = new Random();
                     if(rand.nextInt(10) > 4)
                        platoon[0].playWhistle();
                    
                     platoon[0].playBomb();
                 }
                 if("straightToTheGround".equals(cheatCode)){
                     givePlatoonGraves();
                 }
                 if("theEnemyHaveEyes".equals(cheatCode)){
                     giveEnemyEyes();
                 }
                 if("survival".equals(cheatCode)){
                     
                     setMap(1,200);
                     giveEnemyEyes();
                     GodMode = true;
                     hasteCheat = true;
                     hastePlatoon();
                     giveEnemyDamage();
 
                     //survivalMode = true;
                     
                 }
                 if("march".equals(cheatCode)){
                     platoonMarch();
                 }
                 /*if("superSurvival".equals(cheatCode)){
                     survivalMode = true;
                     setMap(1,200);
                     giveEnemyEyes();
                     NukeMode = true;
                     hasteCheat = true;
                     hastePlatoon();
                     giveEnemyDamage();
                     rainingArtillery = true;
                     Random rand = new Random();
                     if(rand.nextInt(10) > 4)
                        platoon[0].playWhistle();
                    
                     platoon[0].playBomb();
                     //survivalMode = true;
                     
                 }*/
            }
         }
     }

    public class TimeEvent implements ActionListener{
        @Override 
        public void actionPerformed(ActionEvent ae){
            Object[] possibilities = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,
                                        17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32};
            if(victory() || defeat()){
                for(int i = 0; i < platoon.length; i++){
                    platoon[i].haltPlatoon();
                }
                
                if(defeat()){
                    //timer.stop();
                    
                    int play = JOptionPane.showConfirmDialog(
                            null,
                            "You lost! Would you like to play again?",
                            "Defeat!",
                            JOptionPane.YES_NO_OPTION);
                    if(play == JOptionPane.YES_OPTION){
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
                         setMap(goodStartCount,badStartCount);
                         //timer.start();
                         //Defeat = false;
                    }
                    else{
                        System.exit(0);
                    }
                }
                //Defeat = true;
                if(victory()){
                    //timer.stop();

                    int play = JOptionPane.showConfirmDialog(
                            null,
                            "You Won! Would you like to play again?",
                            "Victory!",
                            JOptionPane.YES_NO_OPTION);
                    if(play == JOptionPane.YES_OPTION){
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
                         setMap(goodStartCount,badStartCount);
                         //timer.start();
                         //Victory = false;
                    }
                    else{
                        System.exit(0);
                    }

                }
            }
            else{
                if(mouseClickX){
                    mouseClickTimer++;
                }
                if(mouseClickTimer == 15){
                    mouseClickTimer = 0;
                    mouseClickX = false;
                }
                if(mouseClickedOnEenemy){
                    enemyClickedTimer++;
                }
                if(enemyClickedTimer == 10){
                    enemyClickedTimer = 0;
                    mouseClickedOnEenemy = false;
                }
                if(nukeFlashed){
                    nukeFlashTimer++;
                }
                if(nukeFlashTimer == 7){
                    nukeFlashTimer = 0;
                    nukeFlashed = false;
                    nukeDropped = true;
                }
                if(nukeDropped){
                    nukeTimer++;
                }
                if(nukeTimer == 15){
                    nukeTimer = 0;
                    nukeDropped = false;
                }
                if(rainingArtillery){
                    artilleryBlast = true;
                    artilleryTimer++;
                }
                
                Random rand = new Random();
                if(artilleryTimer == waitTimer){
                    waitTimer = rand.nextInt(50) + 20;
                    artilleryBlast = false;
                    artilleryTimer = 0;
                    wait = true;
                    waitTime = 1;
                    setBlastPosition();
                    if(rand.nextInt(10) > 4)
                        platoon[0].playWhistle();
                    else
                        platoon[0].playBomb();
                }              
                if(rainingArtillery){
                    for(int i = 0; i < platoon.length; i++){
                        if(platoon[i].getX() + 30 > blastX - 25
                            && platoon[i].getX() < blastX + 25
                            && platoon[i].getY() + 30 > blastY - 25
                            && platoon[i].getY() < blastY + 25
                            && platoon[i].isAlive()){
                                platoon[i].die();
                        }
                    }
                }
                
                //System.out.println("platoon[0].direction: " + platoon[0].getXDir());
                //System.out.println("platoon[0].vDirection: " + platoon[0].getYDir());
                for(Soldier recruit: platoon){
                    if(healMarker == 10){
                        recruit.heal();
                        healMarker = 0;
                    }
                    healMarker++;
                }
                for(int i = 0; i < platoon.length; i++){
                    if(!isMouseInPlatoon(mouseX,mouseY)){
                        platoon[i].testCollisions();
                    }

                    if(platoon[i].good() == true){ 

                        //System.out.println("recruit attack: " + platoon[i].getAttack());
                        if(platoon[i].getAttack()){
                            int closestEnemy = platoon[i].markClosestEnemy(); 
                            int closestRecruit = platoon[i].markClosestRecruit(mouseX, mouseY);
                            if(closestEnemy == -2){ //In contact with enemy
                                platoon[i].setXTarget(platoon[i].getX());
                                platoon[i].setYTarget(platoon[i].getY());
                            }
                            if(closestEnemy == -1){ //no enemy in sight
                                //if(platoon[i].isSelected() == 1){
                                    platoon[i].setXTarget(platoon[i].getXTarget());
                                    platoon[i].setYTarget(platoon[i].getYTarget());
                                    platoon[i].setIsAttacking(false);
                                    for(int j = 0; j < selectedPlatoon.length; j++){

                                        selectedPlatoon[j].setXTarget(xPosArray[j]);
                                        selectedPlatoon[j].setYTarget(yPosArray[j]);
                                        //selectedPlatoon[j].moveTo(xPosArray[j], yPosArray[j]);
                                    }
                                //}
                            }
                            if(closestEnemy != -1 && closestEnemy != -2){ //nearest enemy found

                                if(platoon[closestEnemy].isAlive()){
                                    if(platoon[i].isSelected() == 1){
                                        fillXPosArray(platoon[closestEnemy].getX());
                                        fillYPosArray(platoon[closestEnemy].getY());
                                    }
                                    //platoon[i].testCollisions();
                                    platoon[i].setXTarget(platoon[closestEnemy].getX());
                                    platoon[i].setYTarget(platoon[closestEnemy].getY());
                                }
                            }  
                        }
                        if(hasteCheat){
                            platoon[i].moveTo(platoon[i].getXTarget() ,platoon[i].getYTarget());
                            platoon[i].moveTo(platoon[i].getXTarget() ,platoon[i].getYTarget());
                            platoon[i].moveTo(platoon[i].getXTarget() ,platoon[i].getYTarget());
                            platoon[i].moveTo(platoon[i].getXTarget() ,platoon[i].getYTarget());
                        }
                        platoon[i].moveTo(platoon[i].getXTarget() ,platoon[i].getYTarget());
                        platoon[i].moveTo(platoon[i].getXTarget() ,platoon[i].getYTarget());
                    }
                    if(platoon[i].good() == false){ 
                        int closestEnemy = platoon[i].markClosestEnemy();

                        if(closestEnemy == -2){ //In contact with enemy
                            //platoon[i].testCollisions();
                            platoon[i].setXTarget(platoon[i].getX());
                            platoon[i].setYTarget(platoon[i].getY());

                            platoon[i].moveTo(platoon[i].getXTarget() ,platoon[i].getYTarget());
                            platoon[i].moveTo(platoon[i].getXTarget() ,platoon[i].getYTarget());
                        }
                        if(closestEnemy == -1){ //no enemy in sight
                            platoon[i].setXTarget(platoon[i].getX());
                            platoon[i].setYTarget(platoon[i].getY());
                            platoon[i].moveTo(platoon[i].getXTarget() ,platoon[i].getYTarget());
                            platoon[i].moveTo(platoon[i].getXTarget() ,platoon[i].getYTarget());
                        }
                        if(closestEnemy != -1 && closestEnemy != -2){ //nearest enemy found
                            //System.out.println("nearestEnemy: " + closestEnemy);

                            //platoon[i].setXTarget(platoon[closestEnemy].getX());
                            //platoon[i].setYTarget(platoon[closestEnemy].getY());
                            //platoon[i].testCollisions();
                            platoon[i].moveTo(platoon[closestEnemy].getX() ,platoon[closestEnemy].getY());
                            platoon[i].moveTo(platoon[closestEnemy].getX() ,platoon[closestEnemy].getY());

                        }  

                        //platoon[i].testCollisions();
                    }
                    if(isMouseInPlatoon(mouseX,mouseY)){
                        for(int j = 0; j < selectedPlatoon.length; j++){
                            selectedPlatoon[j].setXTarget(xPosArray[j]);
                            selectedPlatoon[j].setYTarget(yPosArray[j]);
                            //selectedPlatoon[j].moveTo(xPosArray[j], yPosArray[j]);
                        }
                    }

                }

                if(!isMouseInPlatoon(mouseX,mouseY)){
                    for(int i = 0; i < platoon.length; i++){
                        //platoon[i].testCollisions();  

                    }
                }
                else{// mouse is inside platoon
                    for(int i = 0; i < platoon.length; i++){
                        if(platoon[i].isSelected() == 0){
                           // platoon[i].testCollisions();  
                        }
                    }

                }   

                //Victory = true;
            }
            
            
                
            
            
        }  
    }
    
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){//mouse left pressed
            //platoon[0].playWhistle();
            //platoon[1].playRandomClang();
            mouseDragged = true;
            selectorColor = Color.blue;
            selectorX = e.getX()-6;
            selectorY = e.getY()-31;
            for (Soldier recruit : platoon) {
                
                recruit.deselect();
                numSelected = 0;
            }
            mouseX = e.getX()-6;
            mouseY = e.getY()-31;
            myCursor = customCursor2;
            //mouseClickedOnEenemy = true;
            enemyClickedTimer = 0;
            if(NukeMode){
                nukeFlashed = true;
                //nukeDropped = true;

                platoon[0].playNuke();
                platoon[0].playNuke();
                platoon[0].playNuke();

                for(int i = 0; i < platoon.length; i++){
                    if(platoon[i].getX() + 30 > mouseX - 110
                        && platoon[i].getX() < mouseX + 110
                        && platoon[i].getY() + 30 > mouseY - 20
                        && platoon[i].getY() < mouseY + 100
                        && platoon[i].isAlive()){
                        platoon[i].die();
                    }
                }
            }
        }	    
        else if(e.getButton() == MouseEvent.BUTTON3)//mouse right pressed
        {
            
            myCursor = customCursor2;
            mouseLX = e.getX()-6;
            mouseLX = e.getY()-31;
            
            fillXPosArray(e.getX()-6);
            fillYPosArray(e.getY()-31);
            boolean anySelected = false;
            for (Soldier recruit : platoon){
                
                if(recruit.isSelected() == 1){
                    
                    anySelected = true;
                    closestID = recruit.markClosestRecruit(mouseX ,mouseY);
                    if(recruit.getSet()){
                        recruit.setAttack(true);
                        recruit.setSet(false);
                    }
                    else{
                        recruit.setAttack(false);
                        recruit.setSet(false);
                    }
                    //System.out.println("recruitSet: " + recruit.getSet());
                    //System.out.println("recruitAttack: " + recruit.getAttack());
                }
            }
            if(anySelected == true){
                platoon[0].playClick();
                mouseClickX = true;
                mouseClickTimer = 0;
                mouseclicked = true;
                mouseX = e.getX()-6;
                mouseY = e.getY()-31;
                int xTarget;
                int yTarget;
                if(isMouseInPlatoon(e.getX()-6,e.getY()-31)){
                    mouseClickedinPlatoon = true;
                    
                    for(int i = 0; i < selectedPlatoon.length; i++){
                        selectedPlatoon[i].setXTarget(xPosArray[i]);
                        selectedPlatoon[i].setYTarget(yPosArray[i]);
                        //selectedPlatoon[i].forward();
                        //selectedPlatoon[i].setArrived(true);
                    }
                }
                else{
                    mouseClickedinPlatoon = false;
                    for (Soldier recruit : platoon) {
                        if(recruit.isSelected() == 1){
                            closestID = recruit.markClosestRecruit(mouseX ,mouseY);
                            xTarget = mouseX + (recruit.getX() - platoon[closestID].getX());
                            yTarget = mouseY + (recruit.getY() - platoon[closestID].getY());
                            recruit.setXTarget(xTarget);
                            recruit.setYTarget(yTarget);
 
                            recruit.forward();
                            recruit.setArrived(true);
                        }
                    }
                }
            } 
        }
    }

    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            
            
            
            int[] selectedIDs = new int[platoon.length];
            for(int i = 0; i < platoon.length; i++){
                if(platoon[i].getX() + 30 > selectX
                    && platoon[i].getX() < selectX + selectXLength
                    && platoon[i].getY() + 30 > selectY
                    && platoon[i].getY() < selectY + selectYLength
                    && platoon[i].good()
                    && platoon[i].isAlive()){
                    platoon[i].selected();
                    selectedIDs[numSelected] = i;
                    numSelected++;
                }
                else
                    platoon[i].deselect();
            }
            //System.out.println("numSelected: " + numSelected);
            if(numSelected != 0){
                selectedPlatoon = new Soldier[numSelected];
                xPosArray = new int[numSelected];
                yPosArray = new int[numSelected];
                for(int i = 0; i < selectedPlatoon.length; i++){
                    selectedPlatoon[i] = platoon[selectedIDs[i]];
                    
                    
                    yPosArray[i] = selectedPlatoon[i].getYTarget();
                    xPosArray[i] = selectedPlatoon[i].getXTarget();
                    
                }
            }
            mouseDragged = false;
            selectX = 0;
            selectXLength = 0;
            selectY = 0;
            selectYLength = 0;
            if(GodMode){
                mouseClickedOnEenemy = true;
                platoon[0].playThunder();
                //platoon[0].playThunder();
            }
            
            for (Soldier recruit : platoon) {
                if(isMouseOverSoldier(recruit)){
                    
                    if(recruit.good() && recruit.isAlive() && numSelected==0){
                        
                        numSelected = 1;
                        platoon[recruit.getID()].selected();
                        selectedPlatoon = new Soldier[numSelected];
                        xPosArray = new int[numSelected];
                        yPosArray = new int[numSelected];
                        selectedPlatoon[0] = recruit;
                        yPosArray[0] = selectedPlatoon[0].getYTarget();
                        xPosArray[0] = selectedPlatoon[0].getXTarget();
                    }
                    if(!recruit.good() && GodMode){
                       // mouseClickedOnEenemy = true;
                        //recruit.playThunder();
                        recruit.die();
                        //recruit.playThunder();
                    }
                }   
            }
        }
        
        
        
        if(e.getButton() == MouseEvent.BUTTON3){
            
        }
        
    }

    public void mouseEntered(MouseEvent e) {
       
    }

    public void mouseExited(MouseEvent e) {
       
    }

    public void mouseClicked(MouseEvent e) {
       //System.out.println("x,y " + e.getX() + "," + e.getY());
    }
    
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    public void mouseMoved(MouseEvent e) {
        if(mouseDragged == true){
            repaint();
        }
        mX = (int) e.getPoint().getX()-6;
        mY = (int) e.getPoint().getY()-31;
        
    }
    
    public boolean isMouseInPlatoon(int clickX, int clickY){
        int selected;
        if(getSelected() != -1){
            selected = getSelected();
        }
        else
            return false;
        minX = platoon[selected].getX();
        maxX = platoon[selected].getX();
        minY = platoon[selected].getY();
        maxY = platoon[selected].getY();
        for(int i = 0; i < platoon.length; i++){
            if(platoon[i].isSelected() == 1){
                if(platoon[i].getX() < minX)
                    minX = platoon[i].getX();
                if(platoon[i].getX() > maxX)
                    maxX = platoon[i].getX();
                if(platoon[i].getY() < minY)
                    minY = platoon[i].getY();
                if(platoon[i].getY() > maxY)
                    maxY = platoon[i].getY();
            }
        }
        if(clickX > minX
            && clickX < maxX
            && clickY > minY
            && clickY < maxY)
            return true;
        return false;
    }
    
    public int getSelected(){
        for(int i = 0; i < platoon.length; i++){
            if(platoon[i].isSelected() == 1)
                return i;
        }
        return -1;
    }
    
    public void fillXPosArray(int xClick){
        
        xPosArray = new int[numSelected];
        int dimension = (int) Math.ceil(Math.sqrt(numSelected));
        int formInMiddle = (int) (40*(Math.ceil(dimension/2)));
        int newPos = xClick - formInMiddle;
        for(int i = 0; i < xPosArray.length; i++){
            
            newPos += 40;
            //if(i > 0){
               if(i%dimension==0){
                    newPos = xClick- formInMiddle;
                } 
            //}    
            xPosArray[i] = newPos;
        }
    }
    
    public void fillXPosStatusArray(){
        xPosStatusArray = new int[numSelected];
        int dimension = 8;
        //int formInMiddle = (int) (40*(Math.ceil(dimension/2)));
        int newPos = 600;
        for(int i = 0; i < selectedPlatoon.length; i++){
            
            newPos += 40;
            //if(i > 0){
               if(i%dimension==0){
                    newPos = 500;
                } 
            //}    
            xPosStatusArray[i] = newPos;
        }
    }
    
    public void fillYPosArray(int yClick){
        yPosArray = new int[numSelected];
        int dimension = (int) Math.ceil(Math.sqrt(numSelected));
        int formInMiddle = (int) (50*(Math.ceil(dimension/2)));
        int newPos = yClick - formInMiddle;
        for(int i = 0; i < xPosArray.length; i++){
            if(i > 0){
                if(i%dimension==0){
                    newPos += 50;
                }
            }
            yPosArray[i] = newPos;
        }
    }
    
    public void fillYPosStatusArray(){
        yPosStatusArray = new int[numSelected];
        int dimension = 8;
        //int formInMiddle = (int) (50*(Math.ceil(dimension/2)));
        int newPos = 530;
        for(int i = 0; i < selectedPlatoon.length; i++){
            if(i > 0){
                if(i%dimension==0){
                    newPos += 43;
                }
            }
            yPosStatusArray[i] = newPos;
        }
    }
    
    public boolean isMouseOverSoldier(Soldier recruit){
       return mX > recruit.getX()
               && mX < recruit.getX() + 30
               && mY > recruit.getY()
               && mY < recruit.getY() + 30;
    }
    
    public boolean victory(){
        boolean victory = true;
        for (Soldier recruit : platoon) {
            if (recruit.isAlive() && !recruit.good()) {
                victory = false;
            }
        }
        return victory;
    }
    
    public boolean defeat(){
        boolean defeat = true;
        for (Soldier recruit : platoon) {
            if (recruit.isAlive() && recruit.good()) {
                defeat = false;
            }
        }
        return defeat;
    }
    
    public void drawRocksAndGrass(Graphics g){
        for(int i = 0; i < rocks.length; i++){
            g.drawImage(rocks[i],rockXPositions[i],rockYPositions[i],null);
            g.drawImage(platoon[0].getGrass(),grassXPositions[i],grassYPositions[i],null);
        }
        
    }
    
    public void setRockPositions(){
        Random rand = new Random();
        for(int i = 0; i < rocks.length; i++){
            rocks[i] = platoon[0].getRock();
            rockXPositions[i] = rand.nextInt(screen.width - 35);
            rockYPositions[i] = rand.nextInt(screen.height - 330);
            grassXPositions[i] = rand.nextInt(screen.width - 35);
            grassYPositions[i] = rand.nextInt(screen.height - 330);
        }
    }
    
    public void setBlastPosition(){
        Random rand = new Random();
        blastX = rand.nextInt(screen.width - 35);
        blastY = rand.nextInt(screen.height - 330);
    }
    
    

    public void setMap(int goodCount, int badCount){
        NukeMode = false;
        GodMode = false;
        hasteCheat = false;
        rainingArtillery = false;
        mouseDragged = false;
        platoon = new Soldier[goodCount+badCount];
        int startX = 1100;
        int startY = 30;
        int percent = 100/(goodCount+badCount);
        
        for(int i = 0; i < platoon.length; i++){
            if(i%4==0){
                startY += 50;
                startX = 900;
            }
            try{
                platoon[i] = new Soldier(startX,startY,0);
            }
            catch (IOException ex) {
            // handle exception...
            }
            
            platoon[i].assignPlatoon(platoon,i);
            platoon[i].resurect();
            
            startX += 50;
        }
        startY = 70;
        startX = 100;
        for(int i = badCount; i < platoon.length; i++){
            if(i%4==0){
                startY += 50;
                startX = 100;
            }
            platoon[i].setGood(true);
            platoon[i].setX(startX);
            platoon[i].setXTarget(startX);
            platoon[i].setYTarget(startY);
            platoon[i].setY(startY);
            startX += 50;
            
        }
        if(survivalMode){
            for(int i = badCount; i < platoon.length; i++){
                platoon[i].setX(600);
                platoon[i].setY(275);
                platoon[i].setXTarget(500);
                platoon[i].setYTarget(275);
            }
            
            startY = -600;
            for(int i = goodCount; i < 50; i++){
                if(i%4==0){
                    startY += 50;
                    startX = 500;
                }
                platoon[i].setGood(false);
                platoon[i].setX(startX);
                platoon[i].setXTarget(startX);
                platoon[i].setYTarget(startY);
                platoon[i].setY(startY);
                startX += 50;
            }
            startY = 1500;
            for(int i = 50; i < 100; i++){
                if(i%4==0){
                    startY += 50;
                    startX = 100;
                }
                platoon[i].setGood(false);
                platoon[i].setX(startX);
                platoon[i].setXTarget(startX);
                platoon[i].setYTarget(startY);
                platoon[i].setY(startY);
                startX += 50;
            } 
            startY = 100;
            startX = 2000;
            for(int i = 100; i < 150; i++){
                if(i%4==0){
                    startY += 50;
                    startX = 2000;
                }
                platoon[i].setGood(false);
                platoon[i].setX(startX);
                platoon[i].setXTarget(startX);
                platoon[i].setYTarget(startY);
                platoon[i].setY(startY);
                startX += 50;
            } 
            startY = 100;
            startX = -1500;
            for(int i = 150; i < badCount; i++){
                if(i%4==0){
                    startY += 50;
                    startX = -1500;
                }
                platoon[i].setGood(false);
                platoon[i].setX(startX);
                platoon[i].setXTarget(startX);
                platoon[i].setYTarget(startY);
                platoon[i].setY(startY);
                startX += 100;
            } 
        }
    }
    
    public void hastePlatoon(){
       for (Soldier recruit : platoon) {
           if(recruit.good())
               recruit.haste();
       }
    }
    
    public void feedPlatoonOnions(){
       for (Soldier recruit : platoon) {
           if(recruit.good())
               recruit.eatOnion();
       }
    }
    
    public void givePlatoonGraves(){
       for (Soldier recruit : platoon) {
               recruit.setGrave();
        }
    }
    
     public void giveEnemyEyes(){
       for (Soldier recruit : platoon) {
           if(!recruit.good()){
               recruit.obtainSight();
           }     
        }
    }
     public void giveEnemyDamage(){
       for (Soldier recruit : platoon) {
           if(recruit.good()){
               recruit.damage();
           }     
        }
    }
     
     public void platoonMarch(){
       for (Soldier recruit : platoon) {
           if(recruit.good()){
               recruit.march();
               recruit.setRunCount(0);
           }     
        }
    }
}
    
    

 