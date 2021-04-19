package tdgame.enemies;

import tdgame.enemies.Enemy.type;

public class Boss extends Enemy{
        private static String name = "Boss";
        public static final int heigth=42;
        public static final int width=42;
        
        
	public Boss(int[][] Locations, double health, double speed, double armor, int reward, int damage){
	super(Locations, health, speed, armor, reward, damage, name, type.BOSS, 0);
	}

	
}