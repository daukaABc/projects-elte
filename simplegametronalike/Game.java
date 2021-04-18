package simplegametronalike;

import java.awt.Color;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;


public class Game extends JFrame {
    
    private GameEngine gameArea;

    public Game() throws  Exception {
        setTitle("Tron Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        gameArea = new GameEngine();
        getContentPane().add(gameArea);
        
        setBounds(10, 10, 900, 700);
        setBackground(Color.DARK_GRAY);
        
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Menu");
        Menu register = new Menu("Register");
        MenuItem reg = new MenuItem("Register");
        
        MenuItem score = new MenuItem("Show best scores");
        MenuItem restart = new MenuItem("Restart game");
        menu.add(score);
        menu.add(restart);
        register.add(reg);
        
        //register.addActionListener(e -> register());
        restart.addActionListener(e -> {
            try {
                restartGame();
            } catch (Exception ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
        score.addActionListener(e -> bestScore());
        
        menuBar.add(menu);
        menuBar.add(register);
        
        setMenuBar(menuBar);
        setTitle("Tron Game");
        
        setResizable(false);
        setVisible(true);
    }      

     private void newGame() throws Exception {
       GameEngine g = new GameEngine();
        getContentPane().add(g);
    }

    private void bestScore() {} //db
    
    public void restartGame() throws Exception {
        System.out.println("aaaaaaa");
        remove(gameArea);
       
        gameArea = new GameEngine();
        getContentPane().add(gameArea);
        setTitle("TronAAAA Game");
        setResizable(false);
        setVisible(true);
    }
}
