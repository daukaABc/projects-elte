package tdgame.enemies;

abstract public class Enemy {


    public enum type {MINION, SKELETON, ARMORED, MOUNTED, BOSS};
    public enum direction {LEFT, RIGHT, UP, DOWN};

    private final String name;
    private final int reward;
    private final int[][] locations;
    private int locationIncrementer = 0;
        
    private double health;
    private double speed;
    private final double armor;
    private int damage;
    private static final int DISTANCEUNIT=8;
    private double distanceTravelled;
        
    private float XLoc;
    private float YLoc;
        
    private final boolean canMove = true;
    private boolean alive;
    private boolean visible;
    private boolean frozen = false;
    private boolean atEndPoint = false;
    private long lastUpdate;
        
    private final long freezeDuration = 1000;
    private final long updateDelay=100;
        
    private final type enemyType;
        
    direction enemyDirection;
    
    private int elemental;

    public Enemy(int[][] locations, double health, double speed, double armor, int reward, int damage, String name, type enemyType, int elemental){
        this.health = health;
        this.speed = speed;
        this.armor = armor;
        this.reward = reward;
        this.damage=damage;
        this.name = name;
        this.locations = locations;
        XLoc = locations[0][0];
        YLoc = locations[0][1];
        this.enemyType = enemyType;
        distanceTravelled = 0;
        alive = true;
        visible=true;
        findDir(0);
        this.elemental = elemental;
    }

    public void move(){
        if(locationIncrementer == 0){
                visible = true;
                findDir(locationIncrementer);//findDir(0)
                lastUpdate=System.currentTimeMillis();
                locationIncrementer++;
        }
        if(locationIncrementer==locations.length-1){
            this.atEndPoint=true;
        }

        if(System.currentTimeMillis()-lastUpdate>updateDelay/speed && locationIncrementer<locations.length-1){
            switch(enemyDirection){
                case LEFT:{
                    XLoc-=DISTANCEUNIT;
                    distanceTravelled+=DISTANCEUNIT;
                    break;
                }
                case RIGHT:{
                    XLoc+=DISTANCEUNIT;
                    distanceTravelled+=DISTANCEUNIT;
                    break;
                }
                case DOWN:{
                    YLoc+=DISTANCEUNIT;
                    distanceTravelled+=DISTANCEUNIT;
                    break;
                }
                case UP:{
                    YLoc-=DISTANCEUNIT;
                    distanceTravelled+=DISTANCEUNIT;
                    break;
                }
                default:{
                    
                }
            }
            lastUpdate=System.currentTimeMillis();
            if(XLoc==locations[locationIncrementer][0] && YLoc==locations[locationIncrementer][1]){
                if(locationIncrementer<locations.length-1){
                    findDir(locationIncrementer);
                    locationIncrementer++;
                }
            }
        }
    }

    public void takeDamage(double damage){
            health = health - damage/armor;
            if(health <= 0){
                alive = false;
                visible = false;
            }
        }
      
    private void findDir(int currentPos){//finds the direction the enemy will go to knowing that it can only go in one direction at a time
        if(locations[currentPos][0]!=locations[currentPos+1][0]){
            if(locations[currentPos][0]<locations[currentPos+1][0]){
                enemyDirection=direction.RIGHT;
            }
            else{
                enemyDirection=direction.LEFT;
            }
        }
        else{
            if(locations[currentPos][1]<locations[currentPos+1][1]){
                enemyDirection=direction.DOWN;
            }
            else{
                enemyDirection=direction.UP;
            }
        }
    }
    //getters and setters
        
    public String getName() {
        return name;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getDamage() {
        return damage;
    }

    public double getHealth() {
        return health;
    }

    public double getSpeed() {
        return speed;
    } //if frozen speed x freezeMultiplier else return speed

    public double getDistanceTravelled() {
        return distanceTravelled;
    }

    public float getXLoc() {
        return XLoc;
    }

    public float getYLoc() {
        return YLoc;
    }

    public int getReward() {
        return reward;
    }

    public boolean CanMove() {
        return canMove;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean isAtEndPoint() {
        return atEndPoint;
    }
    
    public void setFreezeDuration(long duration) {
        duration = freezeDuration;
    }
    
    public type getType() {
        return enemyType;
    }
    
    
    public direction getEnemyDirection() {
        return enemyDirection;
    }
    
    public int getElemental() {
        return elemental;
    }
    
    public void setElemental(int elemental) {
        this.elemental = elemental;
    }

    // for testing

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public void setDamage(int damage){
        this.damage = damage;
    }

}