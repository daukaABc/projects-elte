/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tdgame;
import tdgame.grid.Map;
import tdgame.turrets.Turret;
import tdgame.turrets.Archer;
import tdgame.turrets.Player;

import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import tdgame.enemies.Enemy;
import tdgame.turrets.Blaze;
import tdgame.turrets.Turret.turretType;
import static tdgame.turrets.Turret.turretType.*;
import tdgame.turrets.Water;
/**
 *
 * @author ilya
 */
public class GameScreen extends BasicGameState {
//global parameters
    private final int mouseClickDelay = 200;
    private double lastClick = (-1 * mouseClickDelay);
    public boolean gameOver=false;
    public boolean playerWins;
//actual parameters
    public StateBasedGame game;
    private Input input;
    public GameContainer container;
    private GameEngine gameEngine;
    private Hud hud;
    public Map map;
    

    //turrets graphics
    private Image water;
    private Image blaze;
    private Image archer;
    
    private Image quitButtonI;
    private Rectangle quitButtonR;
    private final Rectangle gameOverScreen=new Rectangle(450, 150, 250, 200);
    private final String quitButtonText = "Quit";
    private Font font;
    private TrueTypeFont ttf;
    
    //enemies graphics
    private SpriteSheet bossSpriteSheet;
    private SpriteSheet armoredSpriteSheet;
    private SpriteSheet mountedSpriteSheet;
    private SpriteSheet skeletonSpriteSheet[]=new SpriteSheet[2];
    private SpriteSheet minionSpriteSheet[]=new SpriteSheet[2];
    Animation[] minionAnimations=new Animation[8];//order right, up, left, down and again if there is two different elementals
    Animation[] armoredAnimations=new Animation[4];
    Animation[] mountedAnimations=new Animation[4];
    Animation[] skeletonAnimations=new Animation[8];
    Animation[] bossAnimations=new Animation[4];
    
    
    
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        gameEngine=new GameEngine();
        hud= new Hud(gameEngine.getPlayer());
        map=new Map();
        this.container=container;
        input=new Input(container.getHeight());
        this.hud.init(container, this);
        this.game=game;
        this.map.init(container);
        loadTurretsGraphics();
        loadAnimationsOfEnemies();
        loadFonts();
        quitButtonI = new Image("ressources/button1.png");
        quitButtonR = new Rectangle(25,600,quitButtonI.getWidth(), 80);
    }
    
    
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        this.map.render(container, g);
        renderTurrets(g);
        this.hud.render(container, g);
        if(gameOver==true){
            g.setColor(Color.lightGray);
            g.fill(gameOverScreen);
            String string;
            if(playerWins){
                string="You win. Perfect !";
            }
            else{
                string="You lose";
            }
            ttf.drawString(500, 200, string, Color.black);
        }
        if(gameEngine.waveIsInProgress && !gameOver){
            drawEnemies(g);
        }
        quitButtonI.draw(25,550);
        drawText(container);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if(input.isMouseButtonDown(0)){
            mousePressed(0, input.getMouseX(), input.getMouseY(), container);
        }
        gameEngine.updateEnemyLists(false);
        if(gameEngine.waveIsInProgress){
            if(!gameOver){
                gameEngine.acquireTargets();
                gameEngine.turretShooting();
                gameEngine.updateEnemies();
            }
        }
        checkGameOver();
        
    }
    @Override
    public void keyReleased(int key, char c) {
        if (Input.KEY_ENTER==key){
            gameEngine.updateEnemyLists(true);
        }
    }
    
    public void mousePressed(int button, int x, int y, GameContainer container){
        if(lastClick + mouseClickDelay > System.currentTimeMillis())//to prevent multi-clicking 
            return;
        lastClick = System.currentTimeMillis();
        switch(hud.getTurretSelected()){
            case -1:{
                Turret target=getTurretClicked(x, y);
                if(target!=null){
                    try {
                        Point modPoint=subScreenDecider(x, y, target);//decide where the upgrade screen should be displayed to prevent it from being displayed outside of the window
                        hud.displayUpdateScreen(container, modPoint, target);
                    } catch (SlickException ex) {
                        Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else{
                   try {
                        Point modPoint=subScreenDecider(x, y, null);//same with the buying screen
                        hud.displayBuyingScreen(container, modPoint, new Point(x, y));
                    } catch (SlickException ex) {
                        Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                hud.setTurretSelected(-2);
                break;
            }
            case -2:{
                if(hud.getBuyingScreen()!=null && !hud.getBuyingScreen().contains(x, y)){
                    hud.setDisplayBuyingScreen(false);
                    hud.setTurretSelected(-1);
                }
                if(hud.getUpgradeScreen()!=null && !hud.getUpgradeScreen().contains(x, y)){
                    hud.setDisplayUpgradeScreen(false);
                    hud.setTurretSelected(-1);
                }
                break;
            }
        }
        if (quitButtonR.contains(x,y)) {
            game.enterState(0);
        }
    }
    
    
    public void buyTurret(int type, Point p){
        switch(type){
            case 0:{
                if(placeIsCorrect((int)p.getX(), (int)p.getY(), ARCHER)){
                    Player player = gameEngine.getPlayer();
                    Archer a = new Archer(p, archer);
                    player.buyTurret(a);
                }
                break;
            }
            case 1:{
                if(placeIsCorrect((int)p.getX(), (int)p.getY(), BLAZE)){
                    Player player = gameEngine.getPlayer();
                    Blaze b = new Blaze(p, blaze);
                    player.buyTurret(b);
                }
                break;
            }
            case 2:{
                if(placeIsCorrect((int)p.getX(), (int)p.getY(), WATER)){
                    Player player = gameEngine.getPlayer();
                    Water w = new Water(p, water);
                    player.buyTurret(w);
                }
                break;
            }     
        }
    }
    @Override
    public int getID() {
        return 2;
    }
    private Turret getTurretClicked(int x, int y){
        Turret res=null;
        for(Turret t: gameEngine.getPlayer().getTurrets()){
            if(t.boundingBox.contains(x, y)){
                res=t;
            }
        }
        return res;
    }
    private boolean placeIsCorrect(int x, int y, turretType type){
        boolean res=true;
        if(map.onPath(x, y, type)){
            res=false;
        }
        else{
            int height=0, width=0;
            switch(type){
                case ARCHER:{
                    height=Archer.height;
                    width=Archer.width;
                    break;
                }
                case WATER:{
                    height=Water.height;
                    width=Water.width;
                    break;
                }
                case BLAZE:{
                    height=Blaze.height;
                    width=Blaze.width;
                    break;
                }
                }
            for(Turret t: gameEngine.getPlayer().getTurrets()){
                Rectangle candidate=new Rectangle(x-(width/2), y-(height/2), width, height);
                if(t.boundingBox.intersects(candidate)){
                    res=false;
                }
            }
        }
        return res;
    }

    private void renderTurrets(Graphics g) {
        for(Turret t: gameEngine.getPlayer().getTurrets()){
            Color c=null;
            int height=0, width=0;
            switch(t.type){
                case ARCHER:{
                    height=Archer.height;
                    width=Archer.width;
                    c=Color.lightGray;
                    break;
                }
                case WATER:{
                    height=Water.height;
                    width=Water.width;
                    c=Color.cyan;
                    break;
                }
                case BLAZE:{
                    height=Blaze.height;
                    width=Blaze.width;
                    c=Color.red.brighter();
                    break;
                }
            }
            g.setColor(Color.black);
            g.drawImage(t.getImage(), t.getCoord().getX()-(width)/2, t.getCoord().getY()-(height)/2);
            //g.drawOval((float)(t.getCoord().getX()-t.getRange()), (float)(t.getCoord().getY()-t.getRange()), (float)t.getRange()*2, (float)t.getRange()*2);
            g.drawString(t.getBoughtUpgrades()[0]+" "+t.getBoughtUpgrades()[1]+" "+t.getBoughtUpgrades()[2], t.getCoord().getX(), t.getCoord().getY()+5+(height)/2);
            if(t.isCurrentlyShooting()){
                g.setColor(c);
                g.drawLine(t.getCoord().getX(), t.getCoord().getY(), t.getTarget().getXLoc(), t.getTarget().getYLoc());
            }
        }
    }
    
    public void drawEnemies(Graphics g){
        for(Enemy e : gameEngine.getActiveEnemyQueue()){
            if(e.isVisible() && e.isAlive()){
                drawEnemy(e, g);
            }
        }
    }
    private void drawEnemy(Enemy e, Graphics g){
        Animation[] a=null;
        int direction=0;
        switch(e.getType()){
            case MINION:
                a = minionAnimations;
                break;
            case SKELETON:
                a = skeletonAnimations;
                break;
            case MOUNTED:
                a = mountedAnimations;
                break;
            case ARMORED:
                a = armoredAnimations;
                break;
            case BOSS:
                a = bossAnimations;
                break;
        }
        switch(e.getEnemyDirection()){
            case RIGHT:{
                direction=0;
                break;
            }
            case UP:{
                direction=1;
                break;
            }
            case LEFT:{
                direction=2;
                break;
            }
            case DOWN:{
                direction=3;
                break;
            }
        }
        if(e.getType()==Enemy.type.BOSS){
            direction=0;
        }
        if(e.getType()==Enemy.type.MINION || e.getType()==Enemy.type.SKELETON){
            if(e.getElemental()==1){
                direction+=4;
            }
        }
        int x=(int) (e.getXLoc() - a[direction].getCurrentFrame().getWidth());
        g.drawAnimation(a[direction], e.getXLoc() - (a[direction].getCurrentFrame().getWidth())/2, e.getYLoc() - a[direction].getCurrentFrame().getHeight());
    }

    public void loadAnimationsOfEnemies() throws SlickException  {
        bossSpriteSheet = new SpriteSheet("ressources/bossSpriteSheet.png", 30, 30, 0);
        bossAnimations[0] = new Animation(bossSpriteSheet, 100);
        minionSpriteSheet[0] = new SpriteSheet("ressources/minion_blue.png", 24, 34, 0);
        minionSpriteSheet[1] = new SpriteSheet("ressources/minion_red.png", 28, 32, 0);
        skeletonSpriteSheet[0] = new SpriteSheet("ressources/skeleton_blue.png", 16, 27, 0);
        skeletonSpriteSheet[1] = new SpriteSheet("ressources/skeleton_red.png", 16, 27, 0);
        armoredSpriteSheet=new SpriteSheet("ressources/armored_walking.png", 30, 40, 0);
        mountedSpriteSheet=new SpriteSheet("ressources/mounted_walking.png", 32, 56, 0);
        for(int i=0; i<4; i++){
            minionAnimations[i]=loadAnimation(minionSpriteSheet[0], i);
            minionAnimations[4+i]=loadAnimation(minionSpriteSheet[1], i);
            skeletonAnimations[i]=loadAnimation(skeletonSpriteSheet[0], i);
            skeletonAnimations[4+i]=loadAnimation(skeletonSpriteSheet[1], i);
            armoredAnimations[i]=loadAnimation(armoredSpriteSheet, i);
            mountedAnimations[i]=loadAnimation(mountedSpriteSheet, i);
        }
        
        
        
    }
    private Animation loadAnimation(SpriteSheet sprites, int y){
        Animation animation=new Animation();
        for(int x=0; x<sprites.getHorizontalCount(); x++){
            animation.addFrame(sprites.getSprite(x, y), 100);
        }
        return animation;
    }
    private Point subScreenDecider(int x, int y, Turret target) {
        int modX, modY;
        if(target!=null){
            modX=x-(Hud.UPGRADESCREENSIZE[0]*(target.getAvailableUpgrades()+1)/2)/2;
            modY=y-(Hud.UPGRADESCREENSIZE[1])/2;
            if(x+Hud.UPGRADESCREENSIZE[0]*(target.getAvailableUpgrades()+1)>container.getWidth()){
                modX=(int) (container.getWidth()-Hud.UPGRADESCREENSIZE[0]*(target.getAvailableUpgrades()+1)*1.5);
            }
            if(y+Hud.UPGRADESCREENSIZE[1]>container.getHeight()){
                modY=container.getHeight()-Hud.UPGRADESCREENSIZE[1]*2;
            }
        }
        else{
            modX=x-(Hud.BUYINGSCREENSIZE[0]*Turret.NBTURRETSAVAILABLE)/2;
            modY=y-(Hud.BUYINGSCREENSIZE[1])/2;
            if(x+Hud.BUYINGSCREENSIZE[0]*Turret.NBTURRETSAVAILABLE>container.getWidth()){
                modX=container.getWidth()-Hud.BUYINGSCREENSIZE[0]*Turret.NBTURRETSAVAILABLE;
            }
            if(y+Hud.BUYINGSCREENSIZE[1]>container.getHeight()){
                modY=container.getHeight()-Hud.BUYINGSCREENSIZE[1];
            } 
            }
            
        return new Point(modX, modY);
    }

    private void loadTurretsGraphics() throws SlickException {
        this.water=new Image("ressources/water.png");
        this.blaze=new Image("ressources/blaze.png");
        this.archer=new Image("ressources/archer.png");
    }
    
    private void loadFonts() {
        font = new Font("Times", Font.BOLD, 33);
        ttf = new TrueTypeFont(font, true);
    }
    
    private void drawText(GameContainer container) {
        ttf.drawString(50+256/2-ttf.getWidth(quitButtonText)/2, 625, quitButtonText, Color.orange);
    }

    private void checkGameOver() {
        if(gameEngine.getPlayer().getHP()<=0){
            gameOver=true;
            playerWins=false;
        }
        else{
            if(gameEngine.noMoreWavesToAdd && gameEngine.getActiveEnemyQueue().isEmpty()){
                gameOver=true;
                playerWins=true;
            }
        }
    }
    public void initNewGame(GameContainer container, StateBasedGame game){
        try {
            this.init(container, game);
        } catch (SlickException ex) {
            Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}