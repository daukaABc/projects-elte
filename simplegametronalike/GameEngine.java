package simplegametronalike;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ExecutorService;

public class GameEngine extends JPanel implements ActionListener, KeyListener {
	 
        protected Player p1;
        protected Player p2;
        
        boolean test=false;
        ExecutorService executorService;
    
        Timer t = new Timer(10,this);
        int [][]table;
        
	 public GameEngine() throws Exception {
             super();
                
            t.start();
            addKeyListener(this);
            setFocusable(true);
            setFocusTraversalKeysEnabled(false);
            table = new int [700][900];
                
            for(int i=0;i<700; i++){
                for(int j=0; j<900; j++){
                    table[i][j] = 0;
                }
            }   
            p1= new Player("Robert",50,200,Color.RED );
            p2= new Player("Paul",650,200,Color.WHITE);      
        }

    public void paint(Graphics g)  {
            super.paintComponents(g);
            //(10, 10, 905, 700);
           Graphics2D g2 =  (Graphics2D)g;
           p1.paint(g2);
           p2.paint(g2);
    }
    
        @Override
    public void actionPerformed(ActionEvent e) throws ArrayIndexOutOfBoundsException {
            repaint();
            
            //905, 700
            /* bounds */
            //R.B
            if ( p1.getX() + p1.getVelx()<0 || p1.getX()+ p1.getVelx()>900){ 
                gameOver(p2);
            }
            //L.B
            if ( p1.getX() + p1.getVelx()<0 || p1.getX()+ p1.getVelx()>865){ 
                gameOver(p2);
            }
            //D.B
            if (p1.getY()+ p1.getVely()<0 || p1.getY()+ p1.getVely()>620 ){
                gameOver(p2);
            }
            //U.B
            if (p1.getY()+ p1.getVely()<0 || p1.getY()+ p1.getVely()>650 ){
                gameOver(p2);
            }
            //U.B
            if ( p2.getX()+ p2.getVelx()<0 || p2.getX()+ p2.getVelx()>900){ 
                gameOver(p1);
            } 
            //R.B
            if ( p2.getX()+ p2.getVelx()<0 || p2.getX()+ p2.getVelx()>865){ 
                gameOver(p1);
            } 
            //D.B
            if (p2.getY()+ p2.getVely() <0 || p2.getY()+ p2.getVely()>620){
                gameOver(p1);
            }
            //L.B
            if (p2.getY()+ p2.getVely() <0 || p2.getY()+ p2.getVely()>850){
                gameOver(p1);
            }
            
            p1.setX(p1.getX()+p1.getVelx());
            p1.setY(p1.getY()+p1.getVely());
           
            if (table[p1.getY()][p1.getX()] == 2){
                gameOver(p2);
            } else {
                table[p1.getY()][p1.getX()] = 1;
            }
            
            p2.setX(p2.getX() +p2.getVelx());
            p2.setY(p2.getY() +p2.getVely()) ;
            
            if (table[p2.getY()][p2.getX()] == 1 ){
                gameOver(p1);
            } else {
                table[p2.getY()][p2.getX()] = 2;
            }
	}
        //moves
        public void up(Player p){
            if(p.getVelx() == 0 && p.getVely() ==1){
                p.setVely(0);
                p.setVelx(0);
                gameOver(p);
            } else {
                p.setVely(-1);
                p.setVelx(0);
            }
	 }
	public void down(Player p){
            if(p.getVelx() == 0 && p.getVely() ==-1){
                p.setVely(0);
                p.setVelx(0);
                gameOver(p);
            } else {
                p.setVely(1);
                p.setVelx(0);
            }
        }   
	public void left(Player p){
            if(p.getVelx() == 1 && p.getVely() ==0){
	        p.setVely(0);
                p.setVelx(0);
                gameOver(p);
            } else {
               p.setVely(0);
               p.setVelx(-1); 
            }
	}
	public void right(Player p){
            if(p.getVelx() == -1 && p.getVely() ==0){
                p.setVely(0);
                p.setVelx(0);
                gameOver(p);
            } else {
                p.setVely(0);
                p.setVelx(1); 
            }
	}
      
	public void keyPressed(KeyEvent e){
	    int code=e.getKeyCode();
            /* arrow type */
                if (code == KeyEvent.VK_UP){ up(p1);  }
                if (code == KeyEvent.VK_DOWN){ down(p1); }
                if (code == KeyEvent.VK_RIGHT){  right(p1); }
                if (code == KeyEvent.VK_LEFT){ left(p1); }
            /* wasd type */
                if (code == KeyEvent.VK_W){ up(p2); }
                if (code == KeyEvent.VK_A){ left(p2); }
                if (code == KeyEvent.VK_D) { right(p2); }
                if (code == KeyEvent.VK_S) { down(p2); }
        }
        @Override
        public void keyTyped(KeyEvent e) { }
    
        @Override
        public void keyReleased(KeyEvent e) { }
    
        private void reset(){
            p1= new Player("Robert",50,200,Color.RED );
            p2= new Player("Paul",650,200,Color.WHITE);   
        }
    
        public void gameOver(Player p){
            System.out.println(p.getName());

            JOptionPane.showMessageDialog(this,
            "Game is over. Winner is " +p.getName() );
            System.exit(1);
        }
}
