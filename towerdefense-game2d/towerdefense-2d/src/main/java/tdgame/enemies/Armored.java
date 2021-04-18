package tdgame.enemies;

import tdgame.enemies.Enemy.type;

public class Armored extends Enemy {
        private static String name = "Armored";
        public static final int heigth=40;
        public static final int width=30;

	public Armored(int[][] Locations, double health, double speed, double armor, int reward, int damage){
            super(Locations, health, speed, armor, reward, damage, name, type.ARMORED, 0);
	}
	
	
}