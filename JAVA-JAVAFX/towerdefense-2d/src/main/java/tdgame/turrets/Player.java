/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tdgame.turrets;

import java.util.ArrayList;

/**
 *
 * @author ilya
 */
public class Player {
    int hp;
    int gold;
    ArrayList<Turret> turrets;
    
    public Player(int hp, int gold){
        this.hp=hp;
        this.gold=gold;
        turrets=new ArrayList<>();
    }

    
    
    public void updateHp(int delta) {
        this.hp += delta;
    }
    public void updateGold(int gold) {
        this.gold += gold;
    }
    
    
    public void buyTurret(Turret turret){
        if(turret.getPrice() <= this.gold){
            turrets.add(turret);
            this.gold -= turret.getPrice();
        }
    }
    
    public void sellTurret(Turret turret){
        gold += turret.getPrice();
        turrets.remove(turret);
    }
    
    public void upgradeTurret(Turret turret, int upgradeType){
        if(turret.getUpgradingPrice() <= this.gold){
            this.gold -= turret.getUpgradingPrice();
            turret.upgradeTurret(upgradeType);
        }
    }
    
    public int getHP() {
        return hp;
    }
    public int getGold() {
        return gold;
    }
    
    public ArrayList<Turret> getTurrets() {
        return turrets;
    }
    
    public void setTurrets(ArrayList<Turret> turrets) {
        this.turrets = turrets;
    }
    
}
