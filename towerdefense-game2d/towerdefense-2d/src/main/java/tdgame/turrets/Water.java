/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tdgame.turrets;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import tdgame.FileParser;
import static tdgame.turrets.Turret.turretType.WATER;


/**
 *
 * @author ilya
 */
public class Water extends Turret{
    
    
    public static int height=91;
    public static int width=67;
    /**
     * construct
     * @param coord
     * @param image
     */
    
    public Water(Point coord, Image image){
        FileParser fp=new FileParser("Water");
        this.setAD(fp.get("AD"));
        this.setRange(fp.get("range"));
        this.setAS(fp.get("AS"));
        this.setLevel(0);
        this.setCoord(coord);
        this.setImage(image);
        setPrice((int)fp.get("price"));
        setUpgradingPrice((int)fp.get("upgradePrice"));
        double[] temp=new double[3];
        temp[0]=fp.get("upgradeDamage");
        temp[1]=fp.get("upgradeRange");
        temp[2]=fp.get("upgradeAS");
        setUpgrades(temp);   
        this.type=WATER;
        this.boundingBox=new Rectangle(coord.getX()-width/2, coord.getY()-height/2, width, height);
        setBoughtUpgrades(new int[3]);
    }

    public Water(Point coord){
        FileParser fp=new FileParser("Water");
        this.setAD(fp.get("AD"));
        this.setRange(fp.get("range"));
        this.setAS(fp.get("AS"));
        this.setLevel(0);
        this.setCoord(coord);
        setPrice((int)fp.get("price"));
        setUpgradingPrice((int)fp.get("upgradePrice"));
        double[] temp=new double[3];
        temp[0]=fp.get("upgradeDamage");
        temp[1]=fp.get("upgradeRange");
        temp[2]=fp.get("upgradeAS");
        setUpgrades(temp);
        this.type=WATER;
        this.boundingBox=new Rectangle(coord.getX()-width/2, coord.getY()-height/2, width, height);
        setBoughtUpgrades(new int[3]);
    }

    @Override
    public void upgradeTurret(int upgradeType) {
        addLevel(); // increase the level of the upgrades by one
        switch(upgradeType){
            case 0:{
                setAD(getAD()+getUpgrades()[0]); // increase attackdamage 
                getBoughtUpgrades()[0]++;
                break;
            }
            case 1:{
                setRange(getRange()+getUpgrades()[1]); // increase range
                getBoughtUpgrades()[1]++;
                break;
            }
            case 2:{
                setAS(getAS()+getUpgrades()[2]); // increase attackvelocity
                getBoughtUpgrades()[2]++;
                break;
            }
        }
    }
    
}
