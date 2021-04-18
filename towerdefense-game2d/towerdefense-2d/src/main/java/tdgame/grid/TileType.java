package tdgame.grid;

public enum TileType {
	
	pavement("pavement"), pavementL("pavementL"), pavementU("pavementU"), pavementB("pavementB"), pavementR("pavementR"),
        pavementIBL("pavementIBL"), pavementIBR("pavementIBR"), pavementIUL("pavementIUL"), pavementIUR("pavementIUR"), pavementOBR("pavementOBR"),
        pavementOBL("pavementOBL"), pavementOUL("pavementOUL"), pavementOUR("pavementOUR"),
        grass("grass"), grassWaterB("grassWaterB"), grassWaterU("grassWaterU"), grassWaterL("grassWaterL"), grassWaterR("grassWaterR"),
        grassWaterBL("grassWaterBL"), grassWaterBR("grassWaterBR"), grassWaterUL("grassWaterUL"), grassWaterUR("grassWaterUR"),
        water("water"), waterWind("waterWind"), waterRocks("waterRocks");
	
	String textureName;
	
	TileType(String textureName) {
		this.textureName = textureName;
	}

}