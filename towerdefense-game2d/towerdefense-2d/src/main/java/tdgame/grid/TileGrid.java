package tdgame.grid;

public class TileGrid {
	
	public Tile[][] map;
	
	public TileGrid(int[][] newMap) {
		map = new Tile[36][23];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				switch (newMap[j][i]) {
				case 0:
					map[i][j] = new Tile(i * 32, j *32, TileType.pavement);
					break;
                                case 1:
					map[i][j] = new Tile(i * 32, j *32, TileType.pavementU);
					break;
                                case 2:
					map[i][j] = new Tile(i * 32, j *32, TileType.pavementB);
					break;
                                case 3:
					map[i][j] = new Tile(i * 32, j *32, TileType.pavementL);
					break;
                                case 4:
					map[i][j] = new Tile(i * 32, j *32, TileType.pavementR);
					break;
                                case 5:
					map[i][j] = new Tile(i * 32, j *32, TileType.pavementIBL);
					break;
                                case 6:
					map[i][j] = new Tile(i * 32, j *32, TileType.pavementIBR);
					break;
                                case 7:
					map[i][j] = new Tile(i * 32, j *32, TileType.pavementIUL);
					break;
                                case 8:
					map[i][j] = new Tile(i * 32, j *32, TileType.pavementIUR);
					break;
                                case -1:
					map[i][j] = new Tile(i * 32, j *32, TileType.pavementOBL);
					break;
                                case -2:
					map[i][j] = new Tile(i * 32, j *32, TileType.pavementOBR);
					break;
                                case -3:
					map[i][j] = new Tile(i * 32, j *32, TileType.pavementOUL);
					break;
                                case -4:
					map[i][j] = new Tile(i * 32, j *32, TileType.pavementOUR);
					break;
				case 10:
					map[i][j] = new Tile(i*32, j*32, TileType.grass);
					break;
                                case 11:
					map[i][j] = new Tile(i*32, j*32, TileType.grassWaterB);
					break;
                                case 12:
					map[i][j] = new Tile(i*32, j*32, TileType.grassWaterL);
					break;
                                case 13:
					map[i][j] = new Tile(i*32, j*32, TileType.grassWaterR);
					break;
                                case 14:
					map[i][j] = new Tile(i*32, j*32, TileType.grassWaterU);
					break;
                                case 15:
					map[i][j] = new Tile(i*32, j*32, TileType.grassWaterBL);
					break;
                                case 16:
					map[i][j] = new Tile(i*32, j*32, TileType.grassWaterBR);
					break;
                                case 17:
					map[i][j] = new Tile(i*32, j*32, TileType.grassWaterUL);
					break;        
                                case 18:
					map[i][j] = new Tile(i*32, j*32, TileType.grassWaterUR);
					break;
                                case 20:
					map[i][j] = new Tile(i*32, j*32, TileType.water);
					break;
                                case 21:
					map[i][j] = new Tile(i*32, j*32, TileType.waterWind);
					break;
                                case 22:
					map[i][j] = new Tile(i*32, j*32, TileType.waterRocks);
					break;
			}
		}
	}
	
}
	
}
