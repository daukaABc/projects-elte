/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tdgame;

import java.io.File;
import java.io.FileNotFoundException;
import tdgame.turrets.Turret;
import tdgame.turrets.Player;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import tdgame.enemies.Enemy;
import tdgame.enemies.waveGenerator;
import java.util.ArrayList;
import tdgame.enemies.Wave;


/**
 *
 * @author ilya
 */
public class GameEngine {
    private waveGenerator waves;
    private Player player;
    private long timerLastWave;
    private long timerLastEnemy;
    private int enemyDelay=1000;
    private int level=1;

    public boolean noMoreWavesToAdd=false;
    public boolean waveIsInProgress=false;
    private boolean currentlyAddingEnemiesFromCurrentWave=false;
    
    //enemy queues
    private Queue<Wave> wavesQueue = new LinkedList<>(); //Stores the list of waves storing the enemies for the level
    private ArrayList<Enemy> activeEnemyQueue = new ArrayList<>(); //stores the list of enemies actually being displayed on screen
    
    GameEngine(){
        try {
            player=new Player(100, 100);
            int[][] locations;
            locations = initLocations();
            waves=new waveGenerator(locations, 1);
            setWavesQueue(waves.generateWaves());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void updateEnemyLists(boolean pressed){
        if(wavesQueue.peek()!=null){
            if(currentlyAddingEnemiesFromCurrentWave){//if a waves is in progress but there is still enemies from that waves that need to be added to the activeList
                if(timerLastEnemy+enemyDelay <= System.currentTimeMillis()){
                    if(wavesQueue.peek().enemyList.peek()!=null){//if there is still enemies to add
                        activeEnemyQueue.add(wavesQueue.peek().enemyList.poll());
                        timerLastEnemy=System.currentTimeMillis();
                    }
                }
                if(wavesQueue.peek().enemyList.peek()==null){//if the waves is empty
                    wavesQueue.poll();//removes the empty waves
                    currentlyAddingEnemiesFromCurrentWave=false;
                }
            }
            else{
                if(wavesQueue.peek().isFirstWave()){//special handling in case of first waves. It doesn't start until the ENTER key is pressed
                    if(pressed){
                        wavesQueue.peek().buildWave();
                        timerLastWave=System.currentTimeMillis();
                        timerLastEnemy=System.currentTimeMillis();
                        currentlyAddingEnemiesFromCurrentWave=true;
                        waveIsInProgress=true;
                    }
                }
                else{
                    if(System.currentTimeMillis() >= timerLastWave+wavesQueue.peek().getDelay()*1000 || pressed){//the OR-statement allow the player to call the waves earlier
                        wavesQueue.peek().buildWave();
                        timerLastWave=System.currentTimeMillis();
                        timerLastEnemy=System.currentTimeMillis();
                        currentlyAddingEnemiesFromCurrentWave=true;
                        waveIsInProgress=true;
                    }
                }
            }
        }
        else{
            noMoreWavesToAdd=true;
        }
    }

    public void updateEnemies() {
        ArrayList<Enemy> toRemove=moveEnemies();
        for(Enemy e: toRemove){
            activeEnemyQueue.remove(e);
        }
        if(activeEnemyQueue.isEmpty() && !currentlyAddingEnemiesFromCurrentWave){
            waveIsInProgress=false;
        }
    }
    public void acquireTargets(){
        Enemy target;
        double maxDist;
        for(Turret t: getPlayer().getTurrets()){
            target=null;
            maxDist=0;
            if(!t.hasTarget()){
                for(Enemy e :getActiveEnemyQueue()){
                    if(t.isInRange(e.getXLoc(), e.getYLoc()) && e.getDistanceTravelled()>maxDist){
                        target=e;
                    }
                }
                t.setTarget(target);
            }
            else{
                if(!t.isInRange(t.getTarget().getXLoc(), t.getTarget().getYLoc())){
                    t.setTarget(null);
                    t.setCurrentlyShooting(false);
                }
            }
        }
    }
    public void turretShooting(){
        for(Turret turret : player.getTurrets()){
                turret.shoot(player);
            }
        }
    public ArrayList<Enemy> moveEnemies(){
        ArrayList<Enemy> toRemove=new ArrayList<>();
        for(Enemy e : activeEnemyQueue){
            if(e.isAlive()){
                e.move();
            }
            if(e.isAtEndPoint()){
                player.updateHp(-e.getDamage());
                e.setVisible(false);
            }
            if(!e.isVisible() || !e.isAlive()){
                toRemove.add(e);
            }
        }
        return toRemove;
    }
    private int[][] initLocations() throws FileNotFoundException{
        File file = new File("levels/level"+level+"_path.txt");
        Scanner sc = new Scanner(file);
        int[][] loc=new int[sc.nextInt()][2];
        for(int i=0; i<loc.length; i++){
            loc[i][0]=sc.nextInt();
            loc[i][1]=sc.nextInt();
        }
        return loc;
    }
    public waveGenerator getWaves() {
        return waves;
    }
    public Player getPlayer() {
        return player;
    }
    public Queue<Wave> getWavesQueue() {
        return wavesQueue;
    }
    public ArrayList<Enemy> getActiveEnemyQueue(){
        return activeEnemyQueue;
    }
    public void setWavesQueue(Queue<Wave> wavesQueue) {
        this.wavesQueue = wavesQueue;
    }
    public void setActiveEnemyQueue(ArrayList<Enemy> activeEnemyQueue) {
        this.activeEnemyQueue = activeEnemyQueue;
    }
    public void setLevel(int level){
        this.level=level;
    }
}
