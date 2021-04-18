package simplegametronalike;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Player {
    //Timer t = new Timer(10,this);
    String name;
    int x,y,vely=0,velx=0;
    Color color;
    String tmp;
    
    public Player() { }
    
    public Player(String name,int x,int y,Color color ){
        this.name=name;
        this.x=x;
        this.y=y;
        this.color=color;     
    }
    
    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }
    public void setX(int x){
        this.x=x;
    }
    public int getX(){
        return x;
    }
    public void setY(int y){
        this.y=y;
    }
    public int getY(){
        return y;
    }
    
   public void setVelx(int x){
        this.velx=x;
    }
    public int getVelx(){
        return velx;
    }
    public void setVely(int y){
        this.vely=y;
    }
    public int getVely(){
        return vely;
    }
    
    public void setColor(Color color){
        this.color=color;     
    }
    public Color getColor(){
        return color;
    }
    
  public void paint(Graphics2D g){
         Graphics2D g2 =  (Graphics2D)g;
         g2.setColor(getColor());
         g2.fill(new Ellipse2D.Double(x,y,20,20));
    }  
}
