
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import tdgame.FileParser;
import tdgame.enemies.*;
import tdgame.turrets.Archer;
import tdgame.turrets.Blaze;
import tdgame.turrets.Player;
import tdgame.turrets.Water;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import tdgame.grid.Map;
import tdgame.turrets.Turret;

public class TDUnitTests {

    public int[][] getLocation() throws FileNotFoundException {
        File file = new File("levels/level1_path.txt");
        Scanner sc = new Scanner(file);
        int[][] loc = new int[sc.nextInt()][2];
        for (int i = 0; i < loc.length; i++) {
            loc[i][0] = sc.nextInt();
            loc[i][1] = sc.nextInt();
        }
        return loc;
    }

    @Test
    public void testEnemyGettingDamage() throws FileNotFoundException {
        Minion minion = new Minion(getLocation(), 50, 1, 1, 0, 0, 0);
        minion.takeDamage(30);
        assertEquals(minion.getHealth(), 20, 1);
    }

    @Test
    public void testBuyingTurretWithMoney() {
        Player player = new Player(50, 100);
        // Arbitrary point
        Point p = new Point(5, 5);
        Archer archer = new Archer(p);

        player.buyTurret(archer);
        assertEquals(true, player.getTurrets().contains(archer));

    }

    @Test
    public void testBuyingTurretWithoutMoney() {
        Player player = new Player(50, 0);
        Point p = new Point(5, 5);
        Water water = new Water(p);
        boolean checkIfContains = player.getTurrets().contains(water);
        assertEquals(true, !checkIfContains);
    }

    @Test
    public void testMouseClickedOnPath() {
        Map map = new Map();
        map.setCurrentMap("levels/level1.txt");
        boolean checkOnPath = map.onPath(602, 184, Turret.turretType.BLAZE);
        assertEquals(true, checkOnPath);
    }

    @Test
    public void testMouseNotClickedOnPath() {
        Map map = new Map();
        map.setCurrentMap("levels/level1.txt");
        boolean checkOnPath = map.onPath(10, 10, Turret.turretType.BLAZE);
        assertEquals(false, checkOnPath);
    }

    @Test
    public void testWaterTurrentAgainstWaterMinion() throws FileNotFoundException {
        Point point = new Point(0, 0);
        Player player = new Player(50, 0);
        Water water = new Water(point);
        Minion minion = new Minion(getLocation(), water.getAD()/2+1, 1, 1, 0, 0, 1);

        water.setTarget(minion);
        water.setIsCurrentlyShooting(true);
        water.shoot(player);
        assertEquals(true, minion.isAlive());

    }

    @Test
    public void testWaterTurrentAgainstBlazeMinion() throws FileNotFoundException {
        Point point = new Point(0, 0);
        Player player = new Player(50, 0);
        Water water = new Water(point);
        Minion minion = new Minion(getLocation(), water.getAD()/2+1, 1, 1, 0, 0, 0);

        water.setTarget(minion);
        water.setIsCurrentlyShooting(true);
        water.shoot(player);
        double health = minion.getHealth();
        assertEquals(false, minion.isAlive());
    }

    @Test
    public void testBlazeTurrentAgainstBlazeMinion() throws FileNotFoundException {
        Point point = new Point(0, 0);
        Player player = new Player(50, 0);
        Blaze blaze = new Blaze(point);
        Minion minion = new Minion(getLocation(), blaze.getAD()/2+1, 1, 1, 0, 0, 0);

        blaze.setTarget(minion);
        blaze.setIsCurrentlyShooting(true);
        blaze.shoot(player);
        double health = minion.getHealth();
        assertEquals(true, minion.isAlive());

    }

    @Test
    public void testBlazeTurrentAgainstWaterMinion() throws FileNotFoundException {
        Point point = new Point(0, 0);
        Player player = new Player(50, 0);
        Blaze blaze = new Blaze(point);
        Minion minion = new Minion(getLocation(), blaze.getAD()/2+1, 1, 1, 0, 0, 1);

        blaze.setTarget(minion);
        blaze.setIsCurrentlyShooting(true);
        blaze.shoot(player);
        double health = minion.getHealth();
        assertEquals(false, minion.isAlive());
    }

    @Test
    public void testUpgradingArcherWithMoney(){
        Player player = new Player(50,1000);
        Point p = new Point(5,5);
        Archer archer = new Archer(p);

        player.upgradeTurret(archer,0);
        assertEquals(1,archer.getLevel());

        player.upgradeTurret(archer,1);
        assertEquals(2,archer.getLevel());

        player.upgradeTurret(archer,2);
        assertEquals(3,archer.getLevel());
    }

    @Test
    public void testUpgradingBlazeWithMoney(){
        Player player = new Player(50,1000);
        Point p = new Point(5,5);
        Blaze blaze = new Blaze(p);

        player.upgradeTurret(blaze,0);
        assertEquals(1,blaze.getLevel());

        player.upgradeTurret(blaze,1);
        assertEquals(2,blaze.getLevel());

        player.upgradeTurret(blaze,2);
        assertEquals(3,blaze.getLevel());
    }

    @Test
    public void testUpgradingBlazeWithoutMoney(){
        Player player = new Player(50,0);
        Point p = new Point(5,5);
        Blaze blaze = new Blaze(p);

        player.upgradeTurret(blaze,0);
        assertEquals(0,blaze.getLevel());

        player.upgradeTurret(blaze,1);
        assertEquals(0,blaze.getLevel());

        player.upgradeTurret(blaze,2);
        assertEquals(0,blaze.getLevel());
    }

    @Test
    public void testUpgradingWaterWithMoney(){
        Player player = new Player(50,1000);
        Point p = new Point(5,5);
        Water water = new Water(p);

        player.upgradeTurret(water,0);
        assertEquals(1,water.getLevel());

        player.upgradeTurret(water,1);
        assertEquals(2,water.getLevel());

        player.upgradeTurret(water,2);
        assertEquals(3,water.getLevel());
    }

    @Test
    public void testUpgradingWaterWithoutMoney(){
        Player player = new Player(50,0);
        Point p = new Point(5,5);
        Water water = new Water(p);

        player.upgradeTurret(water,0);
        assertEquals(0,water.getLevel());

        player.upgradeTurret(water,1);
        assertEquals(0,water.getLevel());

        player.upgradeTurret(water,2);
        assertEquals(0,water.getLevel());
    }

    @Test
    public void testPlayerLosing() throws FileNotFoundException {
        Player player = new Player(50,0);

        Queue<Enemy> enemyList = new LinkedList<>();
        ArrayList<Enemy> activeEnemyQueue = new ArrayList<>();
        waveGenerator waves=new waveGenerator(getLocation(), "levels/level1_waves_test.txt");
        Queue<Wave> wavesQueue = waves.generateWaves();

        wavesQueue.peek().buildWave();

        activeEnemyQueue.add(wavesQueue.peek().enemyList.poll());

        for(Enemy e : activeEnemyQueue){
            e.setSpeed(1000);
            e.setDamage(50);
            while (e.isAtEndPoint()!= true) {
                e.move();
            }
            if(e.isAtEndPoint()){
                player.updateHp(-e.getDamage());
            }
        }

        assertEquals(0,player.getHP());

    }


    @Test
    public void testPlayerWinning() throws FileNotFoundException {

        Player player = new Player(50, 0);

        Point point = new Point(0, 0);
        Blaze blaze = new Blaze(point);

        Queue<Enemy> enemyList = new LinkedList<>();
        ArrayList<Enemy> activeEnemyQueue = new ArrayList<>();
        waveGenerator waves=new waveGenerator(getLocation(), "levels/level1_waves_test.txt");
        Queue<Wave> wavesQueue = waves.generateWaves();

        wavesQueue.peek().buildWave();

        activeEnemyQueue.add(wavesQueue.peek().enemyList.poll());
        ArrayList<Enemy> toRemove=new ArrayList<>();
        for(Enemy e : activeEnemyQueue){
            blaze.setTarget(e);
            e.setElemental(1);
            blaze.setIsCurrentlyShooting(true);
            blaze.shoot(player);
            if(e.isAlive() == false){
                toRemove.add(e);
            }
        }

        for(Enemy e: toRemove){
            activeEnemyQueue.remove(e);
        }

        assertEquals(true,activeEnemyQueue.isEmpty());

    }


}
