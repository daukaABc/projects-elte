package tdgame.enemies;

import tdgame.enemies.Enemy.type;

public class Skeleton extends Enemy{
        private static String name = "Flying";
        public static final int heigth=27;
        public static final int width=16;
    
	public Skeleton(int[][] Locations, double health, double speed, double armor, int reward, int damage, int elemental){
            super(Locations, health, speed, armor, reward, damage, name, type.SKELETON, elemental);
//            elemental = 1;
        }
}