package tdgame.enemies;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class waveGenerator {

        private final int[][] Locations;
        File enemyList;
  
    public waveGenerator(int[][] Locations, int level){
        this.Locations = Locations;
        String path="levels/level"+level+"_waves.txt";
        enemyList=new File(path);
    }

    public waveGenerator(int[][] Locations, String givenPath){
        this.Locations = Locations;
        String path = givenPath;
        enemyList=new File(path);
    }
    
    public Queue<Wave> generateWaves() throws FileNotFoundException{
        Scanner sc=new Scanner(new FileInputStream(enemyList));
        Wave w;
        Queue<Wave> waves= new LinkedList<>();
        while(sc.hasNextLine()){
            w=new Wave(sc.nextLine(), Locations);
            waves.add(w);
        }
        return waves;
    }
    
}