/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tdgame.enemies;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import tdgame.FileParser;

/**
 *
 * @author ilya
 */
public class Wave {

    private final int[] enemies = new int[5];
    private final int delay;
    private final int[][] Locations;
    public Queue<Enemy> enemyList = new LinkedList<>();

    public Wave(String content, int[][] locations) {
        Locations = locations;
        Scanner s = new Scanner(content);
        this.delay = s.nextInt();
        for (int i = 0; i < enemies.length; i++) {
            enemies[i] = s.nextInt();
        }
        s.close();
    }

    public void buildWave() {
        FileParser fp;
        Enemy e;
        Random r;
        if (enemies[0] > 0) {
            fp = new FileParser("Minion");
            for (int i = 0; i < enemies[0]; i++) {
                r = new Random();
                e = new Minion(Locations, fp.get("health"), fp.get("speed"), fp.get("armor"), (int) fp.get("reward"), (int) fp.get("damage"), r.nextInt(2));
                enemyList.add(e);
            }
        }
        if (enemies[1] > 0) {
            fp = new FileParser("Skeleton");
            for (int i = 0; i < enemies[1]; i++) {
                r = new Random();
                e = new Skeleton(Locations, fp.get("health"), fp.get("speed"), fp.get("armor"), (int) fp.get("reward"), (int) fp.get("damage"), r.nextInt(2));
                enemyList.add(e);
            }
        }
        if (enemies[2] > 0) {
            fp = new FileParser("Armored");
            for (int i = 0; i < enemies[2]; i++) {
                e = new Armored(Locations, fp.get("health"), fp.get("speed"), fp.get("armor"), (int) fp.get("reward"), (int) fp.get("damage"));
                enemyList.add(e);
            }
        }
        if (enemies[3] > 0) {
            fp = new FileParser("Mounted");
            for (int i = 0; i < enemies[3]; i++) {
                e = new Mounted(Locations, fp.get("health"), fp.get("speed"), fp.get("armor"), (int) fp.get("reward"), (int) fp.get("damage"));
                enemyList.add(e);
            }
        }
        if (enemies[4] > 0) {
            fp = new FileParser("Boss");
            for (int i = 0; i < enemies[4]; i++) {
                e = new Boss(Locations, fp.get("health"), fp.get("speed"), fp.get("armor"), (int) fp.get("reward"), (int) fp.get("damage"));
                enemyList.add(e);
            }
        }
    }

    public int getDelay() {
        return delay;
    }

    public boolean isFirstWave() {
        return delay == 0;
    }

    public boolean hasBeenBuilt() {
        return enemyList.isEmpty();
    }

}
