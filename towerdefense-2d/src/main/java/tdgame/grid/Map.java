package tdgame.grid;


import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import tdgame.turrets.*;
import tdgame.turrets.Turret.turretType;
import static tdgame.turrets.Turret.turretType.BLAZE;


public class Map {
    
    //map
    private Image pavement;
    private Image pavementL;
    private Image pavementR;
    private Image pavementB;
    private Image pavementU;
    private Image pavementIBL;
    private Image pavementIBR;
    private Image pavementIUL;
    private Image pavementIUR;
    private Image pavementOBL;
    private Image pavementOBR;
    private Image pavementOUL;
    private Image pavementOUR;
    private Image grass;
    private Image grassWaterB;
    private Image grassWaterL;
    private Image grassWaterR;
    private Image grassWaterU;
    private Image grassWaterBL;
    private Image grassWaterBR;
    private Image grassWaterUL;
    private Image grassWaterUR;
    private Image water;
    private Image waterRocks;
    private Image waterWind;
    private Image houseImage;
    private Image varoisImage;
    private Image flagPole;
    private Animation flagAnimation=new Animation();
    private static final int TILESIZE=32;
    private ArrayList<Image> tilesArray=new ArrayList<>();
    private TileGrid grid;
    private final int mapHeight = 23;
    private final int mapWidth = 36;
    private int[][] map = new int[mapHeight][mapWidth];
    
    
    
    
    public void setCurrentMap(String level) {
        try {
            loadMapFromFile(level);
            this.grid = new TileGrid(this.map);
        } catch (Exception ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void init(GameContainer container) throws SlickException {
        flagPole=new Image("ressources/flagPole.png");
    	SpriteSheet flagSprites=new SpriteSheet("ressources/flag_spritesheet.png", 32, 32);
        flagAnimation=new Animation(flagSprites, 100);
        houseImage=new Image("ressources/house.png");
        varoisImage=new Image("ressources/varois_downsized.png");
        loadMapTiles();
    }
    
    public void render(GameContainer container, Graphics g) {
        drawMap(container,g);
        g.drawAnimation(flagAnimation, 590, 660);
        g.drawImage(flagPole, 590, 692);
        g.drawAnimation(flagAnimation, 450, 660);
        g.drawImage(flagPole, 450, 692);
    }

    
    public void loadMapTiles() throws SlickException {
    	this.pavement = new Image("ressources/pavement.png");//coded 00
        this.pavement.setName("pavement");
        tilesArray.add(this.pavement);
        this.pavementU = new Image("ressources/pavement_u.png");//coded 01
        this.pavementU.setName("pavementU");
        tilesArray.add(this.pavementU);
        this.pavementB = new Image("ressources/pavement_b.png");//coded 02
        this.pavementB.setName("pavementB");
        tilesArray.add(this.pavementB);
        this.pavementL = new Image("ressources/pavement_l.png");//coded 03
        this.pavementL.setName("pavementL");
        tilesArray.add(this.pavementL);
        this.pavementR = new Image("ressources/pavement_r.png");//coded 04
        this.pavementR.setName("pavementR");
        tilesArray.add(this.pavementR);
        this.pavementIBL = new Image("ressources/pavement_ibl.png");//coded 05
        this.pavementIBL.setName("pavementIBL");
        tilesArray.add(this.pavementIBL);
        this.pavementIBR = new Image("ressources/pavement_ibr.png");//coded 06
        this.pavementIBR.setName("pavementIBR");
        tilesArray.add(this.pavementIBR);
        this.pavementIUL = new Image("ressources/pavement_iul.png");//coded 07
        this.pavementIUL.setName("pavementIUL");
        tilesArray.add(this.pavementIUL);
        this.pavementIUR = new Image("ressources/pavement_iur.png");//coded 08
        this.pavementIUR.setName("pavementIUR");
        tilesArray.add(this.pavementIUR);
        this.pavementOBL = new Image("ressources/pavement_obl.png");//coded -1
        this.pavementOBL.setName("pavementOBL");
        tilesArray.add(this.pavementOBL);
        this.pavementOBR = new Image("ressources/pavement_obr.png");//coded -2
        this.pavementOBR.setName("pavementOBR");
        tilesArray.add(this.pavementOBR);
        this.pavementOUL = new Image("ressources/pavement_oul.png");//coded -3
        this.pavementOUL.setName("pavementOUL");
        tilesArray.add(this.pavementOUL);
        this.pavementOUR = new Image("ressources/pavement_our.png");//coded -4
        this.pavementOUR.setName("pavementOUR");
        tilesArray.add(this.pavementOUR);
        this.grass = new Image("ressources/grass.png");//coded 10
        this.grass.setName("grass");
        tilesArray.add(this.grass);
        this.grassWaterB = new Image("ressources/grass_water_b.png");//coded 11
        this.grassWaterB.setName("grassWaterB");
        tilesArray.add(this.grassWaterB);
        this.grassWaterL = new Image("ressources/grass_water_l.png");//coded 12
        this.grassWaterL.setName("grassWaterL");
        tilesArray.add(this.grassWaterL);
        this.grassWaterR = new Image("ressources/grass_water_r.png");//coded 13
        this.grassWaterR.setName("grassWaterR");
        tilesArray.add(this.grassWaterR);
        this.grassWaterU = new Image("ressources/grass_water_u.png");//coded 14
        this.grassWaterU.setName("grassWaterU");
        tilesArray.add(this.grassWaterU);
        this.grassWaterBL = new Image("ressources/grass_water_bl.png");//coded 15
        this.grassWaterBL.setName("grassWaterBL");
        tilesArray.add(this.grassWaterBL);
        this.grassWaterBR = new Image("ressources/grass_water_br.png");//coded 16
        this.grassWaterBR.setName("grassWaterBR");
        tilesArray.add(this.grassWaterBR);
        this.grassWaterUL = new Image("ressources/grass_water_ul.png");//coded 17
        this.grassWaterUL.setName("grassWaterUL");
        tilesArray.add(this.grassWaterUL);
        this.grassWaterUR = new Image("ressources/grass_water_ur.png");//coded 18
        this.grassWaterUR.setName("grassWaterUR");
        tilesArray.add(this.grassWaterUR);
        this.water = new Image("ressources/water1.png");//coded 20
        this.water.setName("water");
        tilesArray.add(this.water);
        this.waterWind = new Image("ressources/water2.png");//coded 21
        this.waterWind.setName("waterWind");
        tilesArray.add(this.waterWind);
        this.waterRocks = new Image("ressources/water3.png");//coded 22
        this.waterRocks.setName("waterRocks");
        tilesArray.add(this.waterRocks);
    }
    
    public boolean onPath(int x, int y, turretType type){//returns true if the place is on the path or if the turret will overlap on the path
        boolean res=false;
        int posX=x/32, posY=y/32;
        Tile place=grid.map[posX][posY];
        int height=0, width=0;
        switch(type){
            case ARCHER:{
                height=Archer.height;
                width=Archer.width;
                break;
            }
            case WATER:{
                height=Water.height;
                width=Water.width;
                break;
            }
            case BLAZE:{
                height=Blaze.height;
                width=Blaze.width;
                break;
            }
        }
        if(place.getType().toString().equals("pavement")){//checks whether the tile is already on the path
            res=true;
        }
        else{
            res=checkOverlap(x, y, posX, posY, width, height);
        }
        return res;
    }
    private boolean checkOverlap(int x, int y, int posX, int posY, int width, int height){
        boolean res=false;
        Rectangle r=new Rectangle(x-(width)/2, y-(height)/2, width, height);//if not, check if the turret will overlap on the path
            outerloop :
            for(int i=-1; i<=1; i++){
                for(int j=-1; j<=1; j++){
                    if(i==0 && j==0) {//skip checking the same rectangle
                    }
                    else if(posX==0 && i==-1 || posX==grid.map.length-1 && i==1 || posY==0 && j==-1 || posY==grid.map[0].length && j==1){//avoid outOfBoundsException 
                    //in case the chosen tile is in the border of the map
                    }
                    else{
                        if(grid.map[posX+i][posY+j].getType().toString().equals("pavement")){//only if the tile is of type Pavement
                            Rectangle r0=new Rectangle(grid.map[posX+i][posY+j].getX(), grid.map[posX+i][posY+j].getY(), TILESIZE, TILESIZE);
                            if(r.intersects(r0)){
                                res=true;
                                break outerloop;//we exit the nested loops if we found a problem
                            }
                        }
                    }
                }
            }
        return res;
    }
    
    public void loadMapFromFile(String mapFile) throws Exception {
    	
        File file = new File(mapFile);
        Scanner sc = new Scanner(file); 
    		  
    	for (int i = 0; i < this.mapHeight; i++) {
    		for (int j = 0; j < this.mapWidth; j++) {
    			this.map[i][j] = sc.nextInt();
    		}
    	}		    
    }


    private void drawMap(GameContainer container, Graphics g){
    	for (int i = 0; i < this.grid.map.length; i++) {
			for (int j = 0; j < this.grid.map[i].length; j++) {
				Tile t = this.grid.map[i][j];
                                int ind=0;
                                while(ind<tilesArray.size() && !(tilesArray.get(ind).getName().equals(t.getType().textureName)))
                                    ind++;
                                if(ind>=tilesArray.size())
                                    g.drawRect(t.getX(), t.getY(), TILESIZE, TILESIZE);
                                else{
                                    g.drawImage(tilesArray.get(ind), t.getX(), t.getY());
                                }
			}
        }
        g.drawImage(houseImage, 760, -20);
        g.drawImage(houseImage, 840, -20);
        g.drawImage(houseImage, 920, -20);
        g.drawImage(houseImage, 1000, -20);
        g.drawImage(houseImage, 600, 0);
        g.drawImage(houseImage, 521, 0);
        g.drawImage(houseImage, 442, 0);
        g.drawImage(varoisImage, 610, 20);
    }

    public TileGrid getGrid() {
        return grid;
    }

    public int[][] getMap() {
        return map;
    }
    
}