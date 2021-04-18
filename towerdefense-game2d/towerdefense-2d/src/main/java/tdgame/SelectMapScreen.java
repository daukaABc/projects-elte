package tdgame;

/**
 *
 * @author hejoel
 */

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import java.awt.Font;

/**
 *
 * @author hejoel
 */
public class SelectMapScreen extends BasicGameState {

    Image grass;
    Image map1;
    Image map2;
    Image map3;
    Image button;
    
    //new maps
    Image map4;
    Image map5;
    Image map6;

    Rectangle map1Button;
    Rectangle map2Button;
    Rectangle map3Button;
    Rectangle backButton;
    
    Rectangle map4Button;
    Rectangle map5Button;
    Rectangle map6Button;

    private final String title = "Choose the map";
    private final String backButtonText = "Back";

    Font font;
    Font font2;
    TrueTypeFont ttf;
    TrueTypeFont ttf2;

    private final int mouseClickDelay = 200;
    private long lastClick = (-1 * mouseClickDelay);
    

    public SelectMapScreen(int state) {

    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        loadImages();
        loadFonts();
        createRectangleButtons(container);        
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if (Mouse.isButtonDown(0)) {
            mouseClicked(Mouse.getX(), container.getHeight() - Mouse.getY(), game, container);
        }
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        drawGrass(container);
        drawMapButtons(container);
        button.draw(25,550);
        drawText(container);
        
    }

    private void drawGrass(GameContainer container) {
        for (int x = 0; x < container.getWidth(); x += grass.getWidth()) {
            for (int y = 0; y < container.getHeight(); y += grass.getHeight()) {
                grass.draw(x, y);
            }
        }
    }

    private void drawMapButtons(GameContainer container) {
        map1.draw(50, 125);
        map2.draw(450, 125);
        map3.draw(850, 125);
        
        map4.draw(50, 400);
        map5.draw(450, 400);
        map6.draw(850, 400);
    }
   
   private void createRectangleButtons(GameContainer container) {
        map1Button = new Rectangle(50,125, 
                map1.getWidth(), map1.getHeight());
        
        map2Button = new Rectangle(450,125, 
                map2.getWidth(), map2.getHeight());
        
        map3Button = new Rectangle(850,125, 
                map3.getWidth(), map3.getHeight());
       
        map4Button = new Rectangle(50,350, 
                map3.getWidth(), map3.getHeight());
        
        map5Button = new Rectangle(450,350, 
                map3.getWidth(), map3.getHeight());
        
        map6Button = new Rectangle(850,350, 
                map3.getWidth(), map3.getHeight());
        
        backButton = new Rectangle(25,600,button.getWidth(), 80);
    }

    private void drawText(GameContainer container) {
        ttf.drawString(container.getWidth() / 2 - ttf.getWidth(title) / 2, 50, title, Color.magenta);
        ttf2.drawString(50+map1.getWidth()/2-ttf2.getWidth(backButtonText)/2, 625, backButtonText, Color.orange);
    }

    private void loadImages() throws SlickException {
        grass = new Image("ressources/grass.png");

        map1 = new Image("ressources/map1.png");
        map2 = new Image("ressources/map2.png");
        map3 = new Image("ressources/map3.png");
        
        map4 = new Image("ressources/map4.png");
        map5 = new Image("ressources/map5.png");
        map6 = new Image("ressources/map6.png");
        
        button = new Image("ressources/button1.png");

    }

    private void loadFonts() {
        font = new Font("Times", Font.BOLD, 50);
        font2 = new Font("Times", Font.BOLD, 33);
        ttf = new TrueTypeFont(font, true);
        ttf2 = new TrueTypeFont(font2, true);
    }

    public void mouseClicked(int x, int y, StateBasedGame game, GameContainer container) throws SlickException {

        if (lastClick + mouseClickDelay > System.currentTimeMillis()) {
            return;
        }
        lastClick = System.currentTimeMillis();

        if (map1Button.contains(x, y)) {
            GameScreen gs = (GameScreen) game.getState(2);
            gs.initNewGame(container, game);
            gs.map.setCurrentMap("levels/level1.txt");
            game.enterState(2);
        } else if (map2Button.contains(x, y)) {
            GameScreen gs = (GameScreen) game.getState(2);
            gs.initNewGame(container, game);
            gs.map.setCurrentMap("levels/level2.txt");
            game.enterState(2);
        } else if (map3Button.contains(x, y)) {
            GameScreen gs = (GameScreen) game.getState(2);
            gs.initNewGame(container, game);
            gs.map.setCurrentMap("levels/level3.txt");
            game.enterState(2);
        } else if (map4Button.contains(x,y)) {
            GameScreen gs = (GameScreen) game.getState(2);
            gs.initNewGame(container, game);
            gs.map.setCurrentMap("levels/level1.txt");
            game.enterState(2);
        } else if (map5Button.contains(x,y)) {
            GameScreen gs = (GameScreen) game.getState(2);
            gs.initNewGame(container, game);
            gs.map.setCurrentMap("levels/level1.txt");
            game.enterState(2);
        } else if (map6Button.contains(x,y)) {
            GameScreen gs = (GameScreen) game.getState(2);
            gs.initNewGame(container, game);
            gs.map.setCurrentMap("levels/level1.txt");
            game.enterState(2);
        } else if (backButton.contains(x,y)) {
            game.enterState(0);
        }
    }

    @Override
    public int getID() {
        return 1;
    }

}