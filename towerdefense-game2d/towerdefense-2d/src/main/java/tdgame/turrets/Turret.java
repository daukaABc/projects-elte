/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tdgame.turrets;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import tdgame.FileParser;
import tdgame.enemies.Enemy;

public abstract class Turret {

    public enum turretType {
        ARCHER, BLAZE, WATER
    };
    public static final int NBTURRETSAVAILABLE = 3;

    //rendering related parameters
    public Rectangle boundingBox;
    public long lastUpdate;
    private final long updateDelay = 1000;
    private boolean currentlyShooting = false;

    //engine related parameters
    private double attack_damage;
    private double range;
    private Point coord;
    private double attack_velocity;
    private int level;
    private int price;
    private int upgrading_price;
    private Enemy target;
    private Image image;
    private double[] upgrades;
    private int[] boughtUpgrades;
    public turretType type;

    public abstract void upgradeTurret(int upgradeType);

    public void shoot(Player player) {
        if (isCurrentlyShooting()) {
            if (System.currentTimeMillis() - lastUpdate > updateDelay+0.0 / getAS()) {
                if (hasTarget()) {
                    
                    
                    if ((getTarget().getElemental() == 0 && type == turretType.BLAZE) //same elemental
                         || (getTarget().getElemental() == 1 && type==turretType.WATER)) {
                        getTarget().takeDamage(getAD()/2);
                    } else if ((getTarget().getElemental() == 1 && type == turretType.BLAZE) //different elemental
                         || (getTarget().getElemental() == 0 && type==turretType.WATER)) {
                        getTarget().takeDamage(getAD()*2);
                    } else {
                        getTarget().takeDamage(getAD());
                    }
           
                    
                    if (!getTarget().isAlive()) {
                          player.updateGold(getTarget().getReward());
                          setTarget(null);
                          setCurrentlyShooting(false);
                    }
                }
                lastUpdate = System.currentTimeMillis();
            }
        
        } else {
            if (hasTarget()) {
                lastUpdate = System.currentTimeMillis();
                setCurrentlyShooting(true);
            }
        }
    }


    public int getAvailableUpgrades() {
        int res = 3; // 3 total upgrades available in the beginning
        if (getLevel() == 3) { // after 3 upgrades are used set available upgrades to 0
            res = 0;
        }
        return res;
    }

    public boolean isInRange(float x, float y) {
        return new Circle(getCoord().getX(), getCoord().getY(), (float) getRange()).contains(x, y);
    }

    public void setUpgrades(double[] upgrades) {
        this.upgrades = upgrades;
    }

    public double[] getUpgrades() {
        return upgrades;
    }

    public double getAD() {
        return this.attack_damage;
    }

    public void setAD(double attack_damage) {
        this.attack_damage = attack_damage;
    }

    public double getRange() {
        return this.range;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getUpgradingPrice() {
        return upgrading_price;
    }

    public void setUpgradingPrice(int upgrading_price) {
        this.upgrading_price = upgrading_price;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public double getAS() {
        return this.attack_velocity;
    }

    public void setAS(double attack_velocity) {
        this.attack_velocity = attack_velocity;
    }

    public Point getCoord() {
        return this.coord;
    }

    public void setCoord(Point coord) {
        this.coord = coord;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setTarget(Enemy target) {
        this.target = target;
    }

    public Enemy getTarget() {
        return this.target;
    }

    public boolean hasTarget() {
        return target != null;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void addLevel() {
        this.level++;
    }

    public boolean isCurrentlyShooting() {
        return currentlyShooting;
    }

    public void setIsCurrentlyShooting(boolean currentlyShooting) {
        this.currentlyShooting = currentlyShooting;
    }

    public void setCurrentlyShooting(boolean currentlyShooting) {
        this.currentlyShooting = currentlyShooting;
    }

    public int[] getBoughtUpgrades() {
        return boughtUpgrades;
    }

    public void setBoughtUpgrades(int[] boughtUpgrades) {
        this.boughtUpgrades = boughtUpgrades;
    }

}
