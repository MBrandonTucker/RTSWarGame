package rtswargame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;
import static rtswargame.Map.platoon;

public class Soldier extends JPanel{
    
    private int xPos;
    private int yPos;
    private int xTarget;
    private int yTarget;
    private int direction = 1; //1 right 0 left
    private int vDirection = 1; //1 down 0 up
    private int prevDir = 2;
    private int prevVDir = 2;
    final int SOLDIER_SIZE = 30;
    private int soldierID;
    public Soldier[] platoon; 
    int fieldMinX;
    int fieldMaxX;
    int fieldMinY;
    int fieldMaxY;
    boolean metEnemy = false;
    private boolean attack = false;
    private boolean set = false;
    private boolean arrived = false;
    private int selected = 0;
    private boolean good = false;
    boolean mouseOverMe = false;
    private final BufferedImage[] healthBar = new BufferedImage[6];
    private BufferedImage StatusBar;
    private BufferedImage Icon;
    private BufferedImage DeadIcon;
    private BufferedImage Corps;
    private BufferedImage Corps2;
    private BufferedImage footman;
    private BufferedImage footmanRight;
    private BufferedImage footmanLeft;
    private BufferedImage footmanUp;
    private BufferedImage footmanUpRight;
    private BufferedImage footmanUpLeft;
    private BufferedImage footmanDownLeft;
    private BufferedImage footmanDownRight;
    private BufferedImage footmanBlue;
    private BufferedImage footmanRightBlue;
    private BufferedImage footmanLeftBlue;
    private BufferedImage footmanUpBlue;
    private BufferedImage footmanUpRightBlue;
    private BufferedImage footmanUpLeftBlue;
    private BufferedImage footmanDownLeftBlue;
    private BufferedImage footmanDownRightBlue;
    private BufferedImage attackSymbol;
    private BufferedImage RTSCursor;
    private BufferedImage Target;
    private BufferedImage[] FRAttack = new BufferedImage[4];
    private BufferedImage[] FLAttack = new BufferedImage[4];
    private BufferedImage[] FDAttack = new BufferedImage[4];
    private BufferedImage[] FUAttack = new BufferedImage[4];
    private BufferedImage[] FDRAttack = new BufferedImage[4];
    private BufferedImage[] FDLAttack = new BufferedImage[4];
    private BufferedImage[] FULAttack = new BufferedImage[4];
    private BufferedImage[] FURAttack = new BufferedImage[4];
    private BufferedImage[] FLRun = new BufferedImage[4];
    private BufferedImage[] FRRun = new BufferedImage[4];
    private BufferedImage[] FDRun = new BufferedImage[4];
    private BufferedImage[] FURun = new BufferedImage[4];
    private BufferedImage[] FULRun = new BufferedImage[4];
    private BufferedImage[] FURRun = new BufferedImage[4];
    private BufferedImage[] FDLRun = new BufferedImage[4];
    private BufferedImage[] FDRRun = new BufferedImage[4];
    private BufferedImage[] FRAttackBlue = new BufferedImage[4];
    private BufferedImage[] FLAttackBlue = new BufferedImage[4];
    private BufferedImage[] FDAttackBlue = new BufferedImage[4];
    private BufferedImage[] FUAttackBlue = new BufferedImage[4];
    private BufferedImage[] FDRAttackBlue = new BufferedImage[4];
    private BufferedImage[] FDLAttackBlue = new BufferedImage[4];
    private BufferedImage[] FULAttackBlue = new BufferedImage[4];
    private BufferedImage[] FURAttackBlue = new BufferedImage[4];
    private BufferedImage[] FLRunBlue = new BufferedImage[4];
    private BufferedImage[] FRRunBlue = new BufferedImage[4];
    private BufferedImage[] FDRunBlue = new BufferedImage[4];
    private BufferedImage[] FURunBlue = new BufferedImage[4];
    private BufferedImage[] FULRunBlue = new BufferedImage[4];
    private BufferedImage[] FURRunBlue = new BufferedImage[4];
    private BufferedImage[] FDLRunBlue = new BufferedImage[4];
    private BufferedImage[] FDRRunBlue = new BufferedImage[4];
    private BufferedImage rock1;
    private BufferedImage rock2;
    private BufferedImage rock3;
    private BufferedImage grass;
    private BufferedImage lightning;
    private BufferedImage nuke;
    private BufferedImage flash;
    private BufferedImage artillery;
    private BufferedImage grave;
    private boolean graves = false;
    private int health = 2000;
    private boolean alive = true;
    private boolean isAttacking = false;
    private int randBody;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    boolean attackTarget = false;
    int targetID;
    private int attackCount = 0;
    private int runCount = 0;
    private boolean isRunning = false;
    private boolean hasted = false;
    private boolean badBreath = false;
    private boolean iHaveEyes = false;
    File clang1 = new File("sounds/clang1.wav");
    File clang2 = new File("sounds/clang2.wav");
    File clang3 = new File("sounds/clang3.wav");
    File clang5 = new File("sounds/clang5.wav");
    File clang6 = new File("sounds/clang6.wav");
    File clickSound = new File("sounds/click_x.wav");
    File death = new File("sounds/dead.wav");
    File thunder = new File("sounds/thunder.wav");
    File nukeSound = new File("sounds/nuke.wav");
    File artillerySound = new File("sounds/whistle.wav");
    File bomb = new File("sounds/artillery.wav");
    private boolean damage = false;
    private boolean marching = false;
    
    public Soldier() throws IOException{
        xPos = 0;
        yPos = 0;
        File[] files = new File[6];
        File statBar;
        File SoldierIcon;
        File SoldierDeadIcon;
        File deadSoldier;
        files[0] = new File("images/HealthBarFull.png");
        files[1] = new File("images/HealthBar80.png");
        files[2] = new File("images/HealthBar60.png");
        files[3] = new File("images/HealthBar40.png");
        files[4] = new File("images/HealthBar20.png");
        files[5] = new File("images/HealthBarDead.png");
        statBar = new File("images/StatusBar.png");
        SoldierIcon = new File("images/SoldierIcon.png");
        SoldierDeadIcon = new File("images/SoldierDeadIcon.png");
        deadSoldier = new File("images/DeadSoldier.png");
        
        try
        {   
            for(int i = 0; i < healthBar.length; i++){
                healthBar[i] = ImageIO.read(new File(files[i].toURI()));
            }
            StatusBar = ImageIO.read(new File(statBar.toURI()));
            Icon = ImageIO.read(new File(SoldierIcon.toURI()));
            DeadIcon = ImageIO.read(new File(SoldierDeadIcon.toURI()));
            Corps = ImageIO.read(new File(deadSoldier.toURI()));
             
        }
        catch(IOException ex)
        {
        }
    }
    
    public void march(){
        marching = true;
    }
    
    public void haste(){
        hasted = true;
    }
    
    public void damage(){
        damage = true;
    }
    
    public void setGrave(){
        graves = true;
    }
    
    public void obtainSight(){
        iHaveEyes = true;
    }
    
    public void eatOnion(){
        badBreath = true;
    }
    
    public void playClick(){
        play(clickSound);
    }
    
    public void playWhistle(){
        play(artillerySound);
    }
    
    public void playBomb(){
        play(bomb);
    }
    
    public void playNuke(){
        play(nukeSound);
    }
    
    public void playThunder(){
        play(thunder);
    }
    
    public void setRunCount(int val){
        runCount = val;
    }
    
    public Soldier(int x, int y,int chosen) throws IOException{
        Random rand = new Random();
        runCount = rand.nextInt(120);
        
        randBody = rand.nextInt(2);
        xPos = x;
        yPos = y;
        xTarget = xPos;
        yTarget = yPos;
        File[] files = new File[6];
        File[] FRightAttack = new File[4];
        File[] FLeftAttack = new File[4];
        File[] FDownAttack = new File[4];
        File[] FUpAttack = new File[4];
        File[] FDownRightAttack = new File[4];
        File[] FDownLeftAttack = new File[4];
        File[] FUpLeftAttack = new File[4];
        File[] FUpRightAttack = new File[4];
        File[] FLMove = new File[4];
        File[] FRMove = new File[4];
        File[] FDMove = new File[4];
        File[] FUMove = new File[4];
        File[] FULMove = new File[4];
        File[] FURMove = new File[4];
        File[] FDLMove = new File[4];
        File[] FDRMove = new File[4];
        File[] FRightAttackBlue = new File[4];
        File[] FLeftAttackBlue = new File[4];
        File[] FDownAttackBlue = new File[4];
        File[] FUpAttackBlue = new File[4];
        File[] FDownRightAttackBlue = new File[4];
        File[] FDownLeftAttackBlue = new File[4];
        File[] FUpLeftAttackBlue = new File[4];
        File[] FUpRightAttackBlue = new File[4];
        File[] FLMoveBlue = new File[4];
        File[] FRMoveBlue = new File[4];
        File[] FDMoveBlue = new File[4];
        File[] FUMoveBlue = new File[4];
        File[] FULMoveBlue = new File[4];
        File[] FURMoveBlue = new File[4];
        File[] FDLMoveBlue = new File[4];
        File[] FDRMoveBlue = new File[4];
        File statBar;
        File SoldierIcon;
        File SoldierDeadIcon;
        File deadSoldier;
        File deadSoldier2;
        File knight;
        File knightRight;
        File knightleft;
        File knightUp;
        File knightUpRight;
        File knightUpleft;
        File knightDownleft;
        File knightDownRight;
        File knightBlue;
        File knightRightBlue;
        File knightleftBlue;
        File knightUpBlue;
        File knightUpRightBlue;
        File knightUpleftBlue;
        File knightDownleftBlue;
        File knightDownRightBlue;
        File attackAxe;
        File ArmoredHand;
        File ClickImage;
        File rock1Image;
        File rock2Image;
        File rock3Image;
        File grassImage;
        File lightningBolt;
        File nukeImage;
        File flashImage;
        File artilleryBlast;
        File graveStone;
        FDRMove[0] = new File("images/footmanDownRight.png");
        FDRMove[1] = new File("images/FDRRun1.png");
        FDRMove[2] = new File("images/footmanDownRight.png");
        FDRMove[3] = new File("images/FDRRun2.png");
        FDLMove[0] = new File("images/footmanDownLeft.png");
        FDLMove[1] = new File("images/FDLRun1.png");
        FDLMove[2] = new File("images/footmanDownLeft.png");
        FDLMove[3] = new File("images/FDLRun2.png");
        FURMove[0] = new File("images/footmanUpRight.png");
        FURMove[1] = new File("images/FURRun1.png");
        FURMove[2] = new File("images/footmanUpRight.png");
        FURMove[3] = new File("images/FURRun2.png");
        FULMove[0] = new File("images/footmanUpLeft.png");
        FULMove[1] = new File("images/FULRun1.png");
        FULMove[2] = new File("images/footmanUpLeft.png");
        FULMove[3] = new File("images/FULRun2.png");
        FUMove[0] = new File("images/footmanUp.png");
        FUMove[1] = new File("images/FURun1.png");
        FUMove[2] = new File("images/footmanUp.png");
        FUMove[3] = new File("images/FURun2.png");
        FDMove[0] = new File("images/footman.png");
        FDMove[1] = new File("images/FDRun1.png");
        FDMove[2] = new File("images/footman.png");
        FDMove[3] = new File("images/FDRun2.png");
        FRMove[0] = new File("images/footmanRight.png");
        FRMove[1] = new File("images/FRRun1.png");
        FRMove[2] = new File("images/footmanRight.png");
        FRMove[3] = new File("images/FRRun2.png");
        FLMove[0] = new File("images/footmanLeft.png");
        FLMove[1] = new File("images/FLRun1.png");
        FLMove[2] = new File("images/footmanLeft.png");
        FLMove[3] = new File("images/FLRun2.png");
        FUpRightAttack[0] = new File("images/footmanUpRight.png");
        FUpRightAttack[1] = new File("images/FURAttack1.png");
        FUpRightAttack[2] = new File("images/FURAttack2.png");
        FUpRightAttack[3] = new File("images/FURAttack3.png");
        FUpLeftAttack[0] = new File("images/footmanUpLeft.png");
        FUpLeftAttack[1] = new File("images/FULAttack1.png");
        FUpLeftAttack[2] = new File("images/FULAttack2.png");
        FUpLeftAttack[3] = new File("images/FULAttack3.png");
        FDownLeftAttack[0] = new File("images/footmanDownLeft.png");
        FDownLeftAttack[1] = new File("images/FDLAttack1.png");
        FDownLeftAttack[2] = new File("images/FDLAttack2.png");
        FDownLeftAttack[3] = new File("images/FDLAttack3.png");
        FDownRightAttack[0] = new File("images/footmanDownRight.png");
        FDownRightAttack[1] = new File("images/FDRAttack1.png");
        FDownRightAttack[2] = new File("images/FDRAttack2.png");
        FDownRightAttack[3] = new File("images/FDRAttack3.png");
        FUpAttack[0] = new File("images/footmanUp.png");
        FUpAttack[1] = new File("images/FUAttack1.png");
        FUpAttack[2] = new File("images/FUAttack2.png");
        FUpAttack[3] = new File("images/FUAttack3.png");
        FDownAttack[0] = new File("images/footman.png");
        FDownAttack[1] = new File("images/FDAttack1.png");
        FDownAttack[2] = new File("images/FDAttack2.png");
        FDownAttack[3] = new File("images/FDAttack3.png");
        FRightAttack[0] = new File("images/footmanRight.png");
        FRightAttack[1] = new File("images/FRAttack1.png");
        FRightAttack[2] = new File("images/FRAttack2.png");
        FRightAttack[3] = new File("images/FRAttack3.png");
        FLeftAttack[0] = new File("images/footmanLeft.png");
        FLeftAttack[1] = new File("images/FLAttack1.png");
        FLeftAttack[2] = new File("images/FLAttack2.png");
        FLeftAttack[3] = new File("images/FLAttack3.png");
        FDRMoveBlue[0] = new File("images/footmanDownRightBlue.png");
        FDRMoveBlue[1] = new File("images/FDRRun1Blue.png");
        FDRMoveBlue[2] = new File("images/footmanDownRightBlue.png");
        FDRMoveBlue[3] = new File("images/FDRRun2Blue.png");
        FDLMoveBlue[0] = new File("images/footmanDownLeftBlue.png");
        FDLMoveBlue[1] = new File("images/FDLRun1Blue.png");
        FDLMoveBlue[2] = new File("images/footmanDownLeftBlue.png");
        FDLMoveBlue[3] = new File("images/FDLRun2Blue.png");
        FURMoveBlue[0] = new File("images/footmanUpRightBlue.png");
        FURMoveBlue[1] = new File("images/FURRun1Blue.png");
        FURMoveBlue[2] = new File("images/footmanUpRightBlue.png");
        FURMoveBlue[3] = new File("images/FURRun2Blue.png");
        FULMoveBlue[0] = new File("images/footmanUpLeftBlue.png");
        FULMoveBlue[1] = new File("images/FULRun1Blue.png");
        FULMoveBlue[2] = new File("images/footmanUpLeftBlue.png");
        FULMoveBlue[3] = new File("images/FULRun2Blue.png");
        FUMoveBlue[0] = new File("images/footmanUpBlue.png");
        FUMoveBlue[1] = new File("images/FURun1Blue.png");
        FUMoveBlue[2] = new File("images/footmanUpBlue.png");
        FUMoveBlue[3] = new File("images/FURun2Blue.png");
        FDMoveBlue[0] = new File("images/footmanBlue.png");
        FDMoveBlue[1] = new File("images/FDRun1Blue.png");
        FDMoveBlue[2] = new File("images/footmanBlue.png");
        FDMoveBlue[3] = new File("images/FDRun2Blue.png");
        FRMoveBlue[0] = new File("images/footmanRightBlue.png");
        FRMoveBlue[1] = new File("images/FRRun1Blue.png");
        FRMoveBlue[2] = new File("images/footmanRightBlue.png");
        FRMoveBlue[3] = new File("images/FRRun2Blue.png");
        FLMoveBlue[0] = new File("images/footmanLeftBlue.png");
        FLMoveBlue[1] = new File("images/FLRun1Blue.png");
        FLMoveBlue[2] = new File("images/footmanLeftBlue.png");
        FLMoveBlue[3] = new File("images/FLRun2Blue.png");
        FUpRightAttackBlue[0] = new File("images/footmanUpRightBlue.png");
        FUpRightAttackBlue[1] = new File("images/FURAttack1Blue.png");
        FUpRightAttackBlue[2] = new File("images/FURAttack2Blue.png");
        FUpRightAttackBlue[3] = new File("images/FURAttack3Blue.png");
        FUpLeftAttackBlue[0] = new File("images/footmanUpLeftBlue.png");
        FUpLeftAttackBlue[1] = new File("images/FULAttack1Blue.png");
        FUpLeftAttackBlue[2] = new File("images/FULAttack2Blue.png");
        FUpLeftAttackBlue[3] = new File("images/FULAttack3Blue.png");
        FDownLeftAttackBlue[0] = new File("images/footmanDownLeftBlue.png");
        FDownLeftAttackBlue[1] = new File("images/FDLAttack1Blue.png");
        FDownLeftAttackBlue[2] = new File("images/FDLAttack2Blue.png");
        FDownLeftAttackBlue[3] = new File("images/FDLAttack3Blue.png");
        FDownRightAttackBlue[0] = new File("images/footmanDownRightBlue.png");
        FDownRightAttackBlue[1] = new File("images/FDRAttack1Blue.png");
        FDownRightAttackBlue[2] = new File("images/FDRAttack2Blue.png");
        FDownRightAttackBlue[3] = new File("images/FDRAttack3Blue.png");
        FUpAttackBlue[0] = new File("images/footmanUpBlue.png");
        FUpAttackBlue[1] = new File("images/FUAttack1Blue.png");
        FUpAttackBlue[2] = new File("images/FUAttack2Blue.png");
        FUpAttackBlue[3] = new File("images/FUAttack3Blue.png");
        FDownAttackBlue[0] = new File("images/footmanBlue.png");
        FDownAttackBlue[1] = new File("images/FDAttack1Blue.png");
        FDownAttackBlue[2] = new File("images/FDAttack2Blue.png");
        FDownAttackBlue[3] = new File("images/FDAttack3Blue.png");
        FRightAttackBlue[0] = new File("images/footmanRightBlue.png");
        FRightAttackBlue[1] = new File("images/FRAttack1Blue.png");
        FRightAttackBlue[2] = new File("images/FRAttack2Blue.png");
        FRightAttackBlue[3] = new File("images/FRAttack3Blue.png");
        FLeftAttackBlue[0] = new File("images/footmanLeftBlue.png");
        FLeftAttackBlue[1] = new File("images/FLAttack1Blue.png");
        FLeftAttackBlue[2] = new File("images/FLAttack2Blue.png");
        FLeftAttackBlue[3] = new File("images/FLAttack3Blue.png");
        files[0] = new File("images/HealthBarFull.png");
        files[1] = new File("images/HealthBar80.png");
        files[2] = new File("images/HealthBar60.png");
        files[3] = new File("images/HealthBar40.png");
        files[4] = new File("images/HealthBar20.png");
        files[5] = new File("images/HealthBarDead.png");
        statBar = new File("images/StatusBar.png");
        SoldierIcon = new File("images/SoldierIcon.png");
        SoldierDeadIcon = new File("images/SoldierDeadIcon.png");
        deadSoldier = new File("images/DeadSoldier.png");
        deadSoldier2 = new File("images/DeadSoldier2.png");
        knight = new File("images/footman.png");
        knightRight = new File("images/footmanRight.png");
        knightleft = new File("images/footmanLeft.png");
        knightUp = new File("images/footmanUp.png");
        knightUpRight = new File("images/footmanUpRight.png");
        knightUpleft = new File("images/footmanUpLeft.png");
        knightDownleft = new File("images/footmanDownLeft.png");
        knightDownRight = new File("images/footmanDownRight.png");
        knightBlue = new File("images/footmanBlue.png");
        knightRightBlue = new File("images/footmanRightBlue.png");
        knightleftBlue = new File("images/footmanLeftBlue.png");
        knightUpBlue = new File("images/footmanUpBlue.png");
        knightUpRightBlue = new File("images/footmanUpRightBlue.png");
        knightUpleftBlue = new File("images/footmanUpLeftBlue.png");
        knightDownleftBlue = new File("images/footmanDownLeftBlue.png");
        knightDownRightBlue = new File("images/footmanDownRightBlue.png");
        attackAxe = new File("images/attackSymbol2.png");
        ArmoredHand = new File("images/RTSCursor.png");
        ClickImage = new File("images/Target.png");
        rock1Image = new File("images/Rock1.png");
        rock2Image = new File("images/Rock2.png");
        rock3Image = new File("images/Rock3.png");
        grassImage = new File("images/grass.png");
        lightningBolt = new File("images/Lightning.png");
        nukeImage = new File("images/Nuke.png");
        flashImage = new File("images/Flash.png");
        artilleryBlast = new File("images/artillery.png");
        graveStone = new File("images/Grave.png");
        
        
        try
        {   
            for(int i = 0; i < healthBar.length; i++){
                healthBar[i] = ImageIO.read(new File(files[i].toURI()));
            }
            for(int i = 0; i < FRAttack.length; i++){
                FRAttack[i] = ImageIO.read(new File(FRightAttack[i].toURI()));
                FLAttack[i] = ImageIO.read(new File(FLeftAttack[i].toURI()));
                FDAttack[i] = ImageIO.read(new File(FDownAttack[i].toURI()));
                FUAttack[i] = ImageIO.read(new File(FUpAttack[i].toURI()));
                FDRAttack[i] = ImageIO.read(new File(FDownRightAttack[i].toURI()));
                FDLAttack[i] = ImageIO.read(new File(FDownLeftAttack[i].toURI()));
                FULAttack[i] = ImageIO.read(new File(FUpLeftAttack[i].toURI()));
                FURAttack[i] = ImageIO.read(new File(FUpRightAttack[i].toURI()));
                FLRun[i] = ImageIO.read(new File(FLMove[i].toURI()));
                FRRun[i] = ImageIO.read(new File(FRMove[i].toURI()));
                FDRun[i] = ImageIO.read(new File(FDMove[i].toURI()));
                FURun[i] = ImageIO.read(new File(FUMove[i].toURI()));
                FULRun[i] = ImageIO.read(new File(FULMove[i].toURI()));
                FURRun[i] = ImageIO.read(new File(FURMove[i].toURI()));
                FDLRun[i] = ImageIO.read(new File(FDLMove[i].toURI()));
                FDRRun[i] = ImageIO.read(new File(FDRMove[i].toURI()));
                
                FRAttackBlue[i] = ImageIO.read(new File(FRightAttackBlue[i].toURI()));
                FLAttackBlue[i] = ImageIO.read(new File(FLeftAttackBlue[i].toURI()));
                FDAttackBlue[i] = ImageIO.read(new File(FDownAttackBlue[i].toURI()));
                FUAttackBlue[i] = ImageIO.read(new File(FUpAttackBlue[i].toURI()));
                FDRAttackBlue[i] = ImageIO.read(new File(FDownRightAttackBlue[i].toURI()));
                FDLAttackBlue[i] = ImageIO.read(new File(FDownLeftAttackBlue[i].toURI()));
                FULAttackBlue[i] = ImageIO.read(new File(FUpLeftAttackBlue[i].toURI()));
                FURAttackBlue[i] = ImageIO.read(new File(FUpRightAttackBlue[i].toURI()));
                FLRunBlue[i] = ImageIO.read(new File(FLMoveBlue[i].toURI()));
                FRRunBlue[i] = ImageIO.read(new File(FRMoveBlue[i].toURI()));
                FDRunBlue[i] = ImageIO.read(new File(FDMoveBlue[i].toURI()));
                FURunBlue[i] = ImageIO.read(new File(FUMoveBlue[i].toURI()));
                FULRunBlue[i] = ImageIO.read(new File(FULMoveBlue[i].toURI()));
                FURRunBlue[i] = ImageIO.read(new File(FURMoveBlue[i].toURI()));
                FDLRunBlue[i] = ImageIO.read(new File(FDLMoveBlue[i].toURI()));
                FDRRunBlue[i] = ImageIO.read(new File(FDRMoveBlue[i].toURI()));
                
            }
            StatusBar = ImageIO.read(new File(statBar.toURI()));
            Icon = ImageIO.read(new File(SoldierIcon.toURI()));
            DeadIcon = ImageIO.read(new File(SoldierDeadIcon.toURI()));
            Corps = ImageIO.read(new File(deadSoldier.toURI()));
            Corps2 = ImageIO.read(new File(deadSoldier2.toURI()));
            footman = ImageIO.read(new File(knight.toURI()));
            footmanRight = ImageIO.read(new File(knightRight.toURI()));
            footmanLeft = ImageIO.read(new File(knightleft.toURI()));
            footmanUp = ImageIO.read(new File(knightUp.toURI()));
            footmanUpRight = ImageIO.read(new File(knightUpRight.toURI()));
            footmanUpLeft = ImageIO.read(new File(knightUpleft.toURI()));
            footmanDownLeft = ImageIO.read(new File(knightDownleft.toURI()));
            footmanDownRight = ImageIO.read(new File(knightDownRight.toURI()));
            footmanBlue = ImageIO.read(new File(knightBlue.toURI()));
            footmanRightBlue = ImageIO.read(new File(knightRightBlue.toURI()));
            footmanLeftBlue = ImageIO.read(new File(knightleftBlue.toURI()));
            footmanUpBlue = ImageIO.read(new File(knightUpBlue.toURI()));
            footmanUpRightBlue = ImageIO.read(new File(knightUpRightBlue.toURI()));
            footmanUpLeftBlue = ImageIO.read(new File(knightUpleftBlue.toURI()));
            footmanDownLeftBlue = ImageIO.read(new File(knightDownleftBlue.toURI()));
            footmanDownRightBlue = ImageIO.read(new File(knightDownRightBlue.toURI()));
            attackSymbol = ImageIO.read(new File(attackAxe.toURI()));
            RTSCursor = ImageIO.read(new File(ArmoredHand.toURI()));
            Target = ImageIO.read(new File(ClickImage.toURI()));
            rock1 = ImageIO.read(new File(rock1Image.toURI()));
            rock2 = ImageIO.read(new File(rock2Image.toURI()));
            rock3 = ImageIO.read(new File(rock3Image.toURI()));
            grass = ImageIO.read(new File(grassImage.toURI()));
            lightning = ImageIO.read(new File(lightningBolt.toURI()));
            nuke = ImageIO.read(new File(nukeImage.toURI()));
            flash = ImageIO.read(new File(flashImage.toURI()));
            artillery = ImageIO.read(new File(artilleryBlast.toURI()));
            grave = ImageIO.read(new File(graveStone.toURI()));
        }
        catch(IOException ex)
        {
        }
        
        
       
    }
    public BufferedImage getTarget(){
        return Target;
    }
    
    public BufferedImage getGrave(){
        return grave;
    }
    
    public BufferedImage getArtillery(){
        return artillery;
    }
    
     public BufferedImage getFlash(){
        return flash;
    }
    
    public BufferedImage getNuke(){
        return nuke;
    }
    
    public BufferedImage getLightning(){
        return lightning;
    }
    
    public BufferedImage getGrass(){
        return grass;
    }
    
    public BufferedImage getRock(){
        Random rand = new Random();
        
        switch(rand.nextInt(3)){
            case 0:{
                return rock1;
            }
            case 1:{
                return rock2;
            }
            case 2:{
                return rock3;
            }
        }
        return rock1;
    }
    
    public BufferedImage getRTSCursor(){
        return RTSCursor;
    }
    
    public BufferedImage getAttackSymbol(){
        return attackSymbol;
    }
    
    public boolean getAttackTarget(){
        return attackTarget;
    }
    
    public int getTargetID(){
        return targetID;
    }
    
    public void setAttackTarget(boolean val,int ID){
        attackTarget = val;
        targetID = ID;
    }
    
    public BufferedImage getIcon(){
        return footmanBlue;
    }
    
     public BufferedImage getDeadIcon(){
        return DeadIcon;
    }
    
    public boolean isAttacking(){
        return isAttacking;
    }
    
    public void setIsAttacking(boolean val){
        isAttacking = val;
    }
    
    public boolean isAlive(){
        return alive;
    }
    
    public void die(){
        alive = false;
        deselect();
        play(death);
    }
    
    public void resurect(){
        alive = true;
    }
    
    public int getHealth(){
        return health;
    }
    
    public void setHealth(int val){
        health = val;
    }
    public void takeHit(){
        if(health == 0){
            die();
        }
        else{
            if(damage)
                health-=10;
            System.out.println(damage);
            health-=1;
        }
        
    }
    
    public void heal(){
        if(health < 2000)
            health++;
    }
    
    public void setIsMouseOverMe(boolean val){
        mouseOverMe = val;
    }
    
    public void setAttack(boolean command){
        attack = command;
    }
    
    public boolean getAttack(){
       return attack;
    }
    
    public void setSet(boolean command){
        set = command;
    }
    
    public boolean getSet(){
       return set;
    }
    
    public void selected(){
        selected = 1;
    }
    
    public boolean good(){
        return good;
    }
    
    public void setGood(boolean goodOrBad){
        good = goodOrBad;
    }
            
    
    public void deselect(){
        selected = 0;
    }
    
    public int isSelected(){
        return selected;
    }
    
    public int getID(){
        return soldierID;
    }
    
    public int getXTarget(){
        return xTarget;
    }
    
    public int getYTarget(){
        return yTarget;
    }
    
    public void setXTarget(int val){
        xTarget = val;
    }
    
    public void setYTarget(int val){
        yTarget = val;
    }
    
    public void draw(Graphics g,String name){
        super.paintComponent(g);
        g.setColor(Color.black);
        g.drawRect(5,510,1355,190);
        g.drawRect(430,518,510,175);
        if(isAlive()){
            //if(good()){
            if(marching){
                isRunning = true;
            }
            
                if(isSelected() == 1){
                    g.setColor(Color.blue);
                    g.drawOval(xPos-1,yPos+15,SOLDIER_SIZE+2,SOLDIER_SIZE-10); // selector circle
                }
                if(!good()){
                    if(isAttacking){
                        if(direction == 1 && vDirection == 2){ // Right
                            g.drawImage(AttackAnimation(FRAttack),xPos,yPos,null);
                        }
                        else if(direction == 0 && vDirection == 2){ // Left
                            g.drawImage(AttackAnimation(FLAttack),xPos,yPos,null);
                        }
                        else if(direction == 2 && vDirection == 1){ //Down
                            g.drawImage(AttackAnimation(FDAttack),xPos,yPos,null);
                        }
                        else if(direction == 2 && vDirection == 0){ //Up
                            g.drawImage(AttackAnimation(FUAttack),xPos,yPos,null);
                        }
                        else if(direction == 1 && vDirection == 1){// Down Right
                            g.drawImage(AttackAnimation(FDRAttack),xPos,yPos,null);
                        }
                        else if(direction == 0 && vDirection == 1){// Down Left
                            g.drawImage(AttackAnimation(FDLAttack),xPos,yPos,null);
                        }
                        else if(direction == 0 && vDirection == 0){// Up Left
                            g.drawImage(AttackAnimation(FULAttack),xPos,yPos,null);
                        }
                        else if(direction == 1 && vDirection == 0){// Up Right
                            g.drawImage(AttackAnimation(FURAttack),xPos,yPos,null);
                        }
                        else{
                            g.drawImage(AttackAnimation(FURAttack),xPos,yPos,null);
                        }
                        attackCount++;
                    }
                    else{  
                        if(isRunning){
                            if(direction == 0 && vDirection == 2){ // Left
                                g.drawImage(runAnimation(FLRun),xPos,yPos,null);
                            }
                            else if(direction == 1 && vDirection == 2){ //Right
                                g.drawImage(runAnimation(FRRun),xPos,yPos,null);
                            }
                            else if(direction == 2 && vDirection == 1){ //Down
                                g.drawImage(runAnimation(FDRun),xPos,yPos,null);
                            }
                            else if(direction == 2 && vDirection == 0){ //Up
                                g.drawImage(runAnimation(FURun),xPos,yPos,null);
                            }
                            else if(direction == 0 && vDirection == 0){// Up Left
                                g.drawImage(runAnimation(FULRun),xPos,yPos,null);
                            }
                            else if(direction == 1 && vDirection == 0){// Up Right
                                g.drawImage(runAnimation(FURRun),xPos,yPos,null);
                            }
                            else if(direction == 0 && vDirection == 1){// Down Left
                                g.drawImage(runAnimation(FDLRun),xPos,yPos,null);
                            }
                            else if(direction == 1 && vDirection == 1){// Down Right
                                g.drawImage(runAnimation(FDRRun),xPos,yPos,null);
                            }
                            else{
                                g.drawImage(runAnimation(FDRRun),xPos,yPos,null);
                            }
                            runCount++;
                        }
                        else{
                            if(direction == 0 && vDirection == 2){ // Left
                                g.drawImage(footmanLeft,xPos,yPos,null);
                            }
                            
                            else if(direction == 2 && vDirection == 1){ //Down
                                g.drawImage(footman,xPos,yPos,null);
                            }
                            else if(direction == 2 && vDirection == 0){ //Up
                                g.drawImage(footmanUp,xPos,yPos,null);
                            }
                            else if(direction == 1 && vDirection == 1){// Down Right
                                g.drawImage(footmanDownRight,xPos,yPos,null);
                            }
                            else if(direction == 0 && vDirection == 0){// Up Left
                                g.drawImage(footmanUpLeft,xPos,yPos,null);
                            }
                            else if(direction == 1 && vDirection == 0){// Up Right
                                g.drawImage(footmanUpRight,xPos,yPos,null);
                            }
                            else if(direction == 0 && vDirection == 1){// Down Left
                                g.drawImage(footmanDownLeft,xPos,yPos,null);
                            }
                            else{
                                g.drawImage(footmanDownRight,xPos,yPos,null);
                            }
                        }
                        
                    }
                }
                else{
                    if(isAttacking){
                        if(direction == 1 && vDirection == 2){ // Right
                            g.drawImage(AttackAnimation(FRAttackBlue),xPos,yPos,null);
                        }
                        else if(direction == 0 && vDirection == 2){ // Left
                            g.drawImage(AttackAnimation(FLAttackBlue),xPos,yPos,null);
                        }
                        else if(direction == 2 && vDirection == 1){ //Down
                            g.drawImage(AttackAnimation(FDAttackBlue),xPos,yPos,null);
                        }
                        else if(direction == 2 && vDirection == 0){ //Up
                            g.drawImage(AttackAnimation(FUAttackBlue),xPos,yPos,null);
                        }
                        else if(direction == 1 && vDirection == 1){// Down Right
                            g.drawImage(AttackAnimation(FDRAttackBlue),xPos,yPos,null);
                        }
                        else if(direction == 0 && vDirection == 1){// Down Left
                            g.drawImage(AttackAnimation(FDLAttackBlue),xPos,yPos,null);
                        }
                        else if(direction == 0 && vDirection == 0){// Up Left
                            g.drawImage(AttackAnimation(FULAttackBlue),xPos,yPos,null);
                        }
                        else if(direction == 1 && vDirection == 0){// Up Right
                            g.drawImage(AttackAnimation(FURAttackBlue),xPos,yPos,null);
                        }
                        else{
                            g.drawImage(AttackAnimation(FURAttack),xPos,yPos,null);
                        }
                        attackCount++;
                    }
                    else{  
                        if(isRunning){
                            if(direction == 0 && vDirection == 2){ // Left
                                g.drawImage(runAnimation(FLRunBlue),xPos,yPos,null);
                            }
                            else if(direction == 1 && vDirection == 2){ //Right
                                g.drawImage(runAnimation(FRRunBlue),xPos,yPos,null);
                            }
                            else if(direction == 2 && vDirection == 1){ //Down
                                g.drawImage(runAnimation(FDRunBlue),xPos,yPos,null);
                            }
                            else if(direction == 2 && vDirection == 0){ //Up
                                g.drawImage(runAnimation(FURunBlue),xPos,yPos,null);
                            }
                            else if(direction == 0 && vDirection == 0){// Up Left
                                g.drawImage(runAnimation(FULRunBlue),xPos,yPos,null);
                            }
                            else if(direction == 1 && vDirection == 0){// Up Right
                                g.drawImage(runAnimation(FURRunBlue),xPos,yPos,null);
                            }
                            else if(direction == 0 && vDirection == 1){// Down Left
                                g.drawImage(runAnimation(FDLRunBlue),xPos,yPos,null);
                            }
                            else if(direction == 1 && vDirection == 1){// Down Right
                                g.drawImage(runAnimation(FDRRunBlue),xPos,yPos,null);
                            }
                            else{
                                g.drawImage(runAnimation(FDRRunBlue),xPos,yPos,null);
                            }
                            runCount++;
                        }
                        else{
                            if(direction == 0 && vDirection == 2){ // Left
                                g.drawImage(footmanLeftBlue,xPos,yPos,null);
                            }
                            
                            else if(direction == 2 && vDirection == 1){ //Down
                                g.drawImage(footmanBlue,xPos,yPos,null);
                            }
                            else if(direction == 2 && vDirection == 0){ //Up
                                g.drawImage(footmanUpBlue,xPos,yPos,null);
                            }
                            else if(direction == 1 && vDirection == 1){// Down Right
                                g.drawImage(footmanDownRightBlue,xPos,yPos,null);
                            }
                            else if(direction == 0 && vDirection == 0){// Up Left
                                g.drawImage(footmanUpLeftBlue,xPos,yPos,null);
                            }
                            else if(direction == 1 && vDirection == 0){// Up Right
                                g.drawImage(footmanUpRightBlue,xPos,yPos,null);
                            }
                            else if(direction == 0 && vDirection == 1){// Down Left
                                g.drawImage(footmanDownLeftBlue,xPos,yPos,null);
                            }
                            else{
                                g.drawImage(footmanDownRightBlue,xPos,yPos,null);
                            }
                        }
                        
                    }
                }
                    
            //}
            /*else{
                if(markClosestEnemy() != -1){
                    //g.setColor(Color.blue);
                    //g.drawRect(fieldMinX, fieldMinY, fieldMaxX - fieldMinX, fieldMaxY - fieldMinY);
                }
                g.setColor(Color.red);
                g.fillOval(xPos,yPos,SOLDIER_SIZE,SOLDIER_SIZE);
            }
            g.setColor(Color.white);
            //g.drawString(name,xPos+10,yPos + 20);*/
            if(mouseOverMe){
                //g.setColor(Color.blue);
                //g.drawRect(getX(), getY() - 10, 30, 5);
                System.out.println(healthBar[0]);
                if(getHealthBarImg() != -1){
                    g.drawImage(healthBar[getHealthBarImg()], getX(), getY() - 10, null);
                }
                
            }
        }
        else{
            //draw dead guy
            //g.setColor(Color.gray);
            if(graves){
                g.drawImage(grave,xPos,yPos,null);
            }
            else{
                if(direction == 1){
                    g.drawImage(Corps,xPos,yPos,null);
                }
                else{
                    g.drawImage(Corps2,xPos,yPos,null);
                }
            }
            
            
        }
        
    }
    
    public void moveH()
    {
        if(isAlive()){
            if(direction == 1)
                xPos += 1;
            if(direction == 0)
                xPos -= 1;
            if(xPos + 31 > screenSize.getWidth())
                direction = 0;
            if(xPos-1 < 0)
                 direction = 1; 
        }
        
    }
    
    public void moveV()
    {
        if(isAlive()){
            if(vDirection == 1)
                yPos += 1;
            if(vDirection == 0)
                yPos -= 1;
            if(yPos + 95 > screenSize.getHeight())
                vDirection = 0;
            if(yPos-1 < 0)
                 vDirection = 1;
        }
        
    }
    
    public int getX(){
        return xPos;
    }
    
    public int getY(){
        return yPos;
    }
    
    public void setX(int val){
        xPos = val;
    }
    
    public void setY(int val){
        yPos = val;
    }
    
    public boolean getArrived(){
        return arrived;
    }
    
    public void setArrived(boolean val){
        arrived = val;
    }
    
    
    public void reverseXDirection(){
        if(direction == 1){
            //xPos -= 2;
            direction = 0;
        }
        if(direction == 0){
            //xPos += 2;
            direction = 1;
        }
        
    }
    
    public void reverseYDirection(){
        if(vDirection == 1){
            //yPos -= 2;
            vDirection = 0;
        }
            
        if(vDirection == 0){
            //yPos += 2;
            vDirection = 1;
        }
    }
    
    public void setXDir(int dir){
        direction = dir;
    }
    
    public void setYDir(int dir){
        vDirection = dir;
    }
    
    public int getXDir(){
        return direction;
    }
    
    public int getYDir(){
        return vDirection;
    }
    
    public void stop(){
        if(direction == 1){
            prevDir = 1;
        }
        if(direction == 0){
            prevDir = 0;
        }
        direction = 2;
        if(vDirection == 1){
            prevVDir = 1;
        }
        if(vDirection == 0){
            prevVDir = 0;
        }
        vDirection = 2;
        direction = 2;
    }
    public void resume(){
        Random rand = new Random();
        if(prevDir == 1){
            xPos -=  rand.nextInt(9);
            direction = 0;
        }
        if(prevDir == 0){
            xPos += rand.nextInt(9);
            direction = 1;
        }
        if(prevVDir == 1){
            yPos += rand.nextInt(9);
            vDirection = 0;
        }
        if(prevVDir == 0){
            yPos -= rand.nextInt(9);
            vDirection = 1;
        } 
    }
    
    public void push(int x, int y){
        if(isAlive()){
            Random rand = new Random();
            if(prevDir == 1){
                int randDistance = rand.nextInt(12);
                xPos -= randDistance;
                if(isSelected() == 0){
                    xTarget -= randDistance;
                }
                direction = 0;
            }
            if(prevDir == 0){
                int randDistance = rand.nextInt(12);
                xPos += randDistance;
                if(isSelected() == 0){
                    xTarget += randDistance;
                }
                direction = 1;
            }
            if(prevVDir == 1){
                int randDistance = rand.nextInt(12);
                yPos += randDistance;
                if(isSelected() == 0){
                    yTarget += randDistance;
                }
                vDirection = 0;
            }
            if(prevVDir == 0){
                int randDistance = rand.nextInt(12);
                if(isSelected() == 0){
                    yTarget -= randDistance;
                }
                yPos -= randDistance;
                vDirection = 1;
            } 
        }
        
    }
    
    public void forward(){
        if(isAlive()){
            Random rand = new Random();
            if(prevDir == 1){

                direction = 0;
            }
            if(prevDir == 0){

                direction = 1;
            }
            if(prevVDir == 1){

                vDirection = 0;
            }
            if(prevVDir == 0){

                vDirection = 1;
            } 
        }
        
    }
    
    public boolean testCollisions(){
        //if(isAlive()){
            for(int i = 0; i < platoon.length; i++){
                if(i != soldierID){
                    if(badBreath){
                        if(xPos + 100 >= platoon[i].getX()
                        && xPos <= platoon[i].getX() + 100
                        && yPos + 100 >= platoon[i].getY()
                        && yPos <= platoon[i].getY() + 100
                        && platoon[i].isAlive() 
                        && isAlive()
                        && good()
                        && !platoon[i].good()){
                            platoon[i].die();
                        }
                    }

                    if(xPos + 30 >= platoon[i].getX()
                    && xPos <= platoon[i].getX() + 30
                    && yPos + 30 >= platoon[i].getY()
                    && yPos <= platoon[i].getY() + 30
                    && platoon[i].isAlive() 
                    && isAlive()){  
                        if(good() && !platoon[i].good()){
                            //stop();
                            if(!getAttack()){
                                stop();
                                takeHit();
                                //platoon[i].push(platoon[i].getX(),platoon[i].getY());
                                setIsAttacking(false);
                                platoon[i].setIsAttacking(true);
                            }
                            else{
                                takeHit();
                                //if(!isAttacking()){
                                if(hasted){
                                    platoon[i].takeHit();
                                    platoon[i].takeHit();
                                    platoon[i].takeHit();
                                }
                                else{
                                    platoon[i].takeHit();
                                }
                                //platoon[i].takeHit();
                                //}
                                platoon[i].setIsAttacking(true);
                                setIsAttacking(true);
                            }
                        }
                        if(!good() && platoon[i].good()){
                            //stop();
                            if(!platoon[i].getAttack()){
                                stop();
                                platoon[i].takeHit();
                                //platoon[i].push(platoon[i].getX(),platoon[i].getY());
                                platoon[i].setIsAttacking(true);
                                setIsAttacking(false);
                            }
                            else{
                                if(platoon[i].hasted){
                                    takeHit();
                                    takeHit();
                                    takeHit();
                                }
                                else{
                                    takeHit();
                                }
                                
                                //if(!isAttacking()){
                                    platoon[i].takeHit();
                                //}
                                platoon[i].setIsAttacking(true);
                                setIsAttacking(true);
                            }
                        }
                        if(!good() && !platoon[i].good()){   
                            //if(getAttack()){
                                stop();
                                platoon[i].push(platoon[i].getX(),platoon[i].getY());
                            //}

                        }
                        if(good() && platoon[i].good()){
                           // if(!getAttack()){
                                stop();
                                platoon[i].push(platoon[i].getX(),platoon[i].getY());
                            //}
                           

                        }
                    return true;
                }
            }
                else{
                    setIsAttacking(false);
                }
       // }
        }
        
        return false;
    }
    
    public void assignPlatoon(Soldier[] assignment,int id){
        platoon = assignment;
        System.arraycopy(assignment, 0, platoon, 0, assignment.length);
        soldierID = id;
    }
    
    public void moveTo(int x, int y){
        if(isAlive()){
            if(xPos == x && yPos == y){
                isRunning = false;    
            }
            else{
                isRunning = true;
            }

                if(xPos < x && xPos < screenSize.width - 35){  
                    direction = 1;
                    moveH();
                }
                if(xPos > x  && xPos > 0){  
                    direction = 0;
                    moveH();
                }
                if(yPos < y && yPos < screenSize.height - 330){ 
                    //System.out.println("screen size height: " + screenSize.height);
                    vDirection = 1;
                    moveV();
                }
                if(yPos > y && yPos > 0){  
                    vDirection = 0;
                    moveV();
                } 
                if(yPos == y && xPos != x){
                    vDirection = 2;
                }
                if(yPos != y && xPos == x){
                    direction = 2;
                }
            
        }
        
    }
    
    public void setIsRunning(boolean val){
        isRunning = val;
    }
    
    public void haltPlatoon(){
        for(int j = 0; j < platoon.length; j++){
            //platoon[j].stop();
            platoon[j].setAttack(false);
            platoon[j].setIsRunning(false);
        }
        this.isRunning = false;
        this.isAttacking = false;
    }
    
    public int markClosestRecruit(int x, int y){
        int closestID = 0;
        if(isAlive()){
            
            int shortestDistance = 50000;
            for(int i = 0; i < platoon.length; i++){
                if(platoon[i].isSelected() == 1){
                    if(platoon[i].getHypotenuse(x, platoon[i].getX(), y, platoon[i].getY()) < shortestDistance){
                        shortestDistance = platoon[i].getHypotenuse(x, platoon[i].getX(), y, platoon[i].getY());
                        closestID = platoon[i].getID();    
                    }
                }

            }
        }
        
        return closestID;
    }
    
    public int getHypotenuse(int x1,int x2,int y1,int y2){
        double hypotenuse;
        int adjacentLength = Math.abs(x1 - x2);
        int oppositeLength = Math.abs(y1 - y2);
        hypotenuse = Math.sqrt(adjacentLength*adjacentLength + oppositeLength*oppositeLength);
        return (int) hypotenuse;
    }
    
    public int markClosestEnemy(){
        int closestID = -1;
            
            int soldierVision = 100;
            if(iHaveEyes && !good())
                soldierVision = 100000;
            fieldMinX = getX() - soldierVision;
            fieldMaxX = getX() + soldierVision + 30;
            fieldMinY = getY() - soldierVision;
            fieldMaxY = getY() + soldierVision + 30;
            int shortestDistance = 50000;
            
            for(int i = 0; i < platoon.length; i++){
                if(platoon[i].getX() + 30 > fieldMinX
                    && platoon[i].getX() < fieldMaxX
                    && platoon[i].getY() + 30 > fieldMinY
                    && platoon[i].getY() < fieldMaxY
                    && platoon[i].good() != good()
                    && platoon[i].isAlive()){
                    //found Enemy
                    if(getHypotenuse(platoon[i].getX(),getX(),platoon[i].getY(),getY()) < shortestDistance){
                        shortestDistance = getHypotenuse(platoon[i].getX(),getX(),platoon[i].getY(),getY());
                        closestID = i;
                        
                    }
                    if(getHypotenuse(platoon[i].getX(),getX(),platoon[i].getY(),getY()) < 30){//in conctact with enemy
                        closestID = -2;
                        testCollisions();
                    }
                }
            }
        
        
        return closestID;
    }
    
    public int getHealthBarImg(){
        if(isAlive()){
            if(getHealth() > 1600){
                return 0;
            }
            if(getHealth() > 1200){
                return 1;
            }
            if(getHealth() > 800){
                return 2;
            }
            if(getHealth() > 400){
                return 3;
            }
            if(getHealth() > 0){
                return 4;
            }
            if(getHealth() < 0){
                die();
            }
        }
        
        return 5;
    }
    
    public BufferedImage getSoldierHealth(int index){
        return healthBar[index];
    }
    
    public BufferedImage AttackAnimation(BufferedImage[] array){
        if(hasted){
            if(attackCount < 10){
                return array[0];
            }
            else if(10 < attackCount && attackCount < 20){
                return array[1];
            }
            else if(30 < attackCount && attackCount < 40){
                return array[2];
            }
            else if(50 < attackCount && attackCount < 60){
                return array[3];
            }
            else if(60 < attackCount){

                attackCount = 0;
                playRandomClang();
            }
            return array[0];
        }
        else{
            if(attackCount < 30){
                return array[0];
            }
            else if(30 < attackCount && attackCount < 60){
                return array[1];
            }
            else if(60 < attackCount && attackCount < 90){
                return array[2];
            }
            else if(90 < attackCount && attackCount < 120){
                return array[3];
            }
            else if(120 < attackCount){

                attackCount = 0;
                playRandomClang();
            }
            return array[0];
        }
        
        
    }
    
    public BufferedImage runAnimation(BufferedImage[] array){
        if(hasted){
            if(runCount < 10){
                return array[0];
            }
            else if(20 < runCount && runCount < 30){
                return array[1];
            }
            else if(30 < runCount && runCount < 40){
                return array[2];
            }
            else if(40 < runCount && runCount < 50){
                return array[3];
            }
            else if(50 < runCount){
                runCount = 0;
            }
            return array[0];
        }
        else{
            if(runCount < 30){
                return array[0];
            }
            else if(30 < runCount && runCount < 60){
                return array[1];
            }
            else if(60 < runCount && runCount < 90){
                return array[2];
            }
            else if(90 < runCount && runCount < 120){
                return array[3];
            }
            else if(120 < runCount){
                runCount = 0;
            }
            return array[0];
        }
        
        
    }
    
    public static void play(File Sound)
    {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(Sound));
            clip.start();
        } 
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
    public void playRandomClang(){
        Random rand = new Random();
        int randSound = rand.nextInt(5);
        //System.out.println("randSound: " + randSound);
        //if(good()){
           switch(randSound){
                case 0:{
                    //System.out.println("1");
                    play(clang1);
                    break;
                }
                case 1:{
                    //System.out.println("2");
                    play(clang2);
                    break;
                }
                case 2:{
                    //System.out.println("3");
                    play(clang3);
                    break;
                }
                case 3:{
                    //System.out.println("4");
                    play(clang5);
                    break;
                }
                case 4:{
                    //System.out.println("5");
                    play(clang6);
                    break;
                }
                default:{
                    break;
                }
                //System.out.println("how many times am I called?");
            }
        //}
        
    }
    
}
