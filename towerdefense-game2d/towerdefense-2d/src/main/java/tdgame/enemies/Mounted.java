package tdgame.enemies;

import tdgame.enemies.Enemy.type;

public class Mounted extends Enemy{
        private static String name = "Mounted";
        public static final int heigth=56;
        public static final int width=32;

        
	public Mounted(int[][] Locations, double health, double speed, double armor, int reward, int damage){
	super(Locations, health, speed, armor, reward, damage, name, type.MOUNTED, 0);
	}
}