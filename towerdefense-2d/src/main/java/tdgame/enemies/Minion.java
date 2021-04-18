package tdgame.enemies;

import tdgame.enemies.Enemy.type;


public class Minion extends Enemy{
    private static String name = "Ground";   
    public static final int heigth=36;
    public static final int width=24;
    
    public Minion(int[][] Locations, double health, double speed, double armor, int reward, int damage, int elemental){
	super(Locations, health, speed, armor, reward, damage, name, type.MINION, elemental);
    }

    
}