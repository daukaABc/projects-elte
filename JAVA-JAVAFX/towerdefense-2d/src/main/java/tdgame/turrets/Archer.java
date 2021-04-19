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
import static tdgame.turrets.Turret.turretType.ARCHER;


/**
 *
 * @author ilya
 */
public class Archer extends Turret{
    
    public static int height=85;
    public static int width=63;
    /**
     * construct
     * @param coord
     * @param image
     */
    
    public Archer(Point coord, Image image){
        FileParser fp=new FileParser("Archer");
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
        this.type=ARCHER;
        this.boundingBox=new Rectangle(coord.getX()-width/2, coord.getY()-height/2, width, height);
        setBoughtUpgrades(new int[3]);
    }

    public Archer(Point coord)
    {
        FileParser fp=new FileParser("Archer");
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
        this.type=ARCHER;
        this.boundingBox=new Rectangle(coord.getX()-width/2, coord.getY()-height/2, width, height);
        setBoughtUpgrades(new int[3]);
    }


    @Override
    public void upgradeTurret(int upgradeType) {
        addLevel(); // increase the level of the turret by one
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
