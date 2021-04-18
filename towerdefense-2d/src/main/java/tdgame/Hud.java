/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tdgame;

import java.util.ArrayList;
import tdgame.turrets.Player;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import tdgame.turrets.Archer;
import tdgame.turrets.Blaze;
import tdgame.turrets.Turret;
import tdgame.turrets.Water;

/**
 *
 * @author ilya
 */
public class Hud implements ComponentListener{
    
    private GameScreen game;
    private Image goldCoin;
    private Image heart;
    private Image playerInfos;
    private final Player player;
    private int turretSelected=-1;
    private Rectangle upgradeScreen;
    public static final int[] UPGRADESCREENSIZE={75, 75};
    private Turret upgradeTarget;
    private ArrayList<MouseOverArea> upgrades;
    private boolean displayUpgradeScreen=false;
    private Rectangle buyingScreen;
    public static final int[] BUYINGSCREENSIZE={75, 110};
    private Point targetLocation;
    private ArrayList<MouseOverArea> turrets;
    private boolean displayBuyingScreen=false;
    private Rectangle gameOverScreen;
    public static final int[] GAMEOVERSCREENSIZE={300, 150};
    private boolean displayGameOverScreen=false;
    private ArrayList<MouseOverArea> buttonsGameOver;
    private Image upAD;
    private Image upRange;
    private Image upAS;
    private Image sellButton;
    private Image archerTower;
    private Image blazeTower;
    private Image waterTower;
    private Image quitButton;
    private Image replayButton;
    
    public Hud(Player p){
        player=p;
    }
    public int getTurretSelected() {
        return turretSelected;
    }

    public void setTurretSelected(int turretSelected) {
        this.turretSelected = turretSelected;
    }
    
    public void init(GameContainer container, GameScreen game) throws SlickException {
        this.game=game;
        this.playerInfos=new Image("ressources/player_infos.png");
        this.goldCoin=new Image("ressources/GoldCoin.png");
        this.heart=new Image("ressources/heart.png");
        this.upAD=new Image("ressources/upAD.png");
        this.upRange=new Image("ressources/upRange.png");
        this.upAS=new Image("ressources/upAS.png");
        this.sellButton=new Image("ressources/sellButton.png");
        this.blazeTower=new Image("ressources/blaze.png");
        this.waterTower=new Image("ressources/water.png");
        this.archerTower=new Image("ressources/archer.png");
        this.replayButton=new Image("ressources/blank_space.png");
        this.quitButton=new Image("ressources/blank_space.png");
    }
    
    public void render(GameContainer container, Graphics g) {
        renderPlayerInfo(g);
        String string;
        if(displayBuyingScreen){
            g.setColor(Color.lightGray);
            g.fill(buyingScreen);
            String[] t={"Archer", "Blaze", "Water"};
            for(int i=0; i<turrets.size(); i++){
                turrets.get(i).render(container, g);
                g.setColor(Color.blue.darker());
                g.drawString(""+new FileParser(t[i]).get("price"), turrets.get(i).getX()+10, turrets.get(i).getY()+turrets.get(i).getHeight());
            }
        }
        if(displayUpgradeScreen){
            g.setColor(Color.lightGray);
            g.fill(upgradeScreen);
            for(int i=0; i<upgrades.size()-1; i++){
                upgrades.get(i).render(container, g);
            }
            upgrades.get(upgrades.size()-1).render(container, g);
        }
        if(displayGameOverScreen){
            g.setColor(Color.lightGray);
            g.fill(gameOverScreen);
            if(game.playerWins){
                string="You win. Perfect !";
            }
            else{
                string="You lose";
            }
            g.setColor(Color.black);
            g.drawString(string, gameOverScreen.getX()+100, gameOverScreen.getY()+25);
            //for(int i=0; i<buttonsGameOver.size(); i++){
            //    buttonsGameOver.get(i).render(container, g);
            //}
        }
            
    }
    private void renderPlayerInfo(Graphics g){
        int heigth=-20;
        int startingX=150;
        g.drawImage(playerInfos, -10, heigth);
        g.setColor(Color.white);
        g.drawString("Mem :" + ((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/1000000) +"/"+ (Runtime.getRuntime().totalMemory()/1000000)+" MB", 10, 25);
        g.drawImage(heart, startingX, heigth+playerInfos.getHeight()/3);
        g.drawString(""+player.getHP(), startingX+17, heigth+playerInfos.getHeight()/3);
        g.drawImage(goldCoin, startingX, heigth+playerInfos.getHeight()/2);
        g.drawString(""+player.getGold(), startingX+17, heigth+playerInfos.getHeight()/2);
    }
    
    @Override
    public void componentActivated(AbstractComponent source) {
        int selected;
        if(displayBuyingScreen){
            selected=turrets.indexOf(source);
            switch(selected){
                case 0:{
                    game.buyTurret(0, targetLocation);
                    break;
                }
                case 1:{
                    game.buyTurret(1, targetLocation);
                    break;
                }
                case 2:{
                    game.buyTurret(2, targetLocation);
                    break;
                }
            }
            displayBuyingScreen=false;
            turretSelected=-1;
        }
        else if(displayUpgradeScreen){
            selected=upgrades.indexOf(source);
            if(selected!=-1){
                if(source==upgrades.get(upgrades.size()-1)){
                    player.sellTurret(upgradeTarget);
                    displayUpgradeScreen=false;
                    turretSelected=-1;
                }
                else{
                    player.upgradeTurret(upgradeTarget, selected);
                    displayUpgradeScreen=false;
                    turretSelected=-1;
                    }
                }
            }
        else if(displayGameOverScreen){
            selected=buttonsGameOver.indexOf(source);
            System.out.println(selected);
            if(selected == 1){
                game.container.exit();
                }
            else if(selected == 2){
                game.container.exit();
            }
            }
        }
    public void displayUpdateScreen(GameContainer container, Point p, Turret target) throws SlickException{
        upgradeScreen=new Rectangle(p.getX(), p.getY(), UPGRADESCREENSIZE[0]*(target.getAvailableUpgrades()+1), UPGRADESCREENSIZE[1]);
        upgradeTarget=target;
        upgrades=new ArrayList<>();
        if(target.getAvailableUpgrades()==3){
            upgrades.add(new MouseOverArea(container, upAD, (int)upgradeScreen.getX()+UPGRADESCREENSIZE[0]*0+(UPGRADESCREENSIZE[0]-64)/2, (int)upgradeScreen.getY()+(UPGRADESCREENSIZE[1]-64)/2, this));
            upgrades.add(new MouseOverArea(container, upRange, (int)upgradeScreen.getX()+UPGRADESCREENSIZE[0]*1+(UPGRADESCREENSIZE[0]-64)/2, (int)upgradeScreen.getY()+(UPGRADESCREENSIZE[1]-64)/2, this));
            upgrades.add(new MouseOverArea(container, upAS, (int)upgradeScreen.getX()+UPGRADESCREENSIZE[0]*2+(UPGRADESCREENSIZE[0]-64)/2, (int)upgradeScreen.getY()+(UPGRADESCREENSIZE[1]-64)/2, this));
        }
        upgrades.add(new MouseOverArea(container, sellButton, (int)upgradeScreen.getX()+UPGRADESCREENSIZE[0]*target.getAvailableUpgrades()+(UPGRADESCREENSIZE[0]-64)/2, (int)upgradeScreen.getY()+(UPGRADESCREENSIZE[1]-64)/2, this));
        displayUpgradeScreen=true;
    }
    public void displayBuyingScreen(GameContainer container, Point screen, Point target) throws SlickException{
        int nbTurrets=Turret.NBTURRETSAVAILABLE;
        buyingScreen=new Rectangle(screen.getX(), screen.getY(), BUYINGSCREENSIZE[0]*nbTurrets, BUYINGSCREENSIZE[1]);
        turrets=new ArrayList<>();
        turrets.add(new MouseOverArea(container, archerTower, (int)buyingScreen.getX()+BUYINGSCREENSIZE[0]*0+(BUYINGSCREENSIZE[0]-Archer.width)/2, (int)buyingScreen.getY()+(BUYINGSCREENSIZE[1]-Archer.height)/4, this));
        turrets.add(new MouseOverArea(container, blazeTower, (int)buyingScreen.getX()+BUYINGSCREENSIZE[0]*1+(BUYINGSCREENSIZE[0]-Blaze.width)/2, (int)buyingScreen.getY()+(BUYINGSCREENSIZE[1]-Blaze.height)/4, this));
        turrets.add(new MouseOverArea(container, waterTower, (int)buyingScreen.getX()+BUYINGSCREENSIZE[0]*2+(BUYINGSCREENSIZE[0]-Water.width)/2, (int)buyingScreen.getY()+(BUYINGSCREENSIZE[1]-Water.height)/4, this));
        targetLocation=target;
        displayBuyingScreen=true;
    }
    public void displayGameOverScreen(GameContainer container){
        gameOverScreen=new Rectangle(500, 200, GAMEOVERSCREENSIZE[0], GAMEOVERSCREENSIZE[1]);
        displayGameOverScreen=true;
        buttonsGameOver=new ArrayList<>();
        if(game.playerWins){
            buttonsGameOver.add(new MouseOverArea(container, quitButton, (int)(gameOverScreen.getX()+100), (int)(gameOverScreen.getY()+75)));
        }
        else{
            buttonsGameOver.add(new MouseOverArea(container, quitButton, (int)(gameOverScreen.getX()+50), (int)(gameOverScreen.getY()+75)));
            buttonsGameOver.add(new MouseOverArea(container, replayButton, (int)(gameOverScreen.getX()+150), (int)(gameOverScreen.getY()+75)));
        }
    }

    public boolean UpgradeScreenIsDisplayed() {
        return displayUpgradeScreen;
    }

    public Rectangle getUpgradeScreen() {
        return upgradeScreen;
    }

    public void setDisplayUpgradeScreen(boolean displayUpgradeScreen) {
        this.displayUpgradeScreen = displayUpgradeScreen;
    }

    public Rectangle getBuyingScreen() {
        return buyingScreen;
    }

    public void setDisplayBuyingScreen(boolean displayBuyingScreen) {
        this.displayBuyingScreen = displayBuyingScreen;
    }
    
    public boolean BuyingScreenIsDisplayed() {
        return displayBuyingScreen;
    }

    
}
