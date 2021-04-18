package tdgame;

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
public class MenuScreen extends BasicGameState {

    Image grass;
    Image button;

    Rectangle startGameButton;

    private final String title = "Battle for Varois-et-Chaignot";
    private final String button1text = "Start game";
    private final String button2text = "Highscores";
    private final String button3text = "Options";

    Font font;
    Font font2;
    TrueTypeFont ttf;
    TrueTypeFont ttf2;

    private final int mouseClickDelay = 200;
    private long lastClick = (-1 * mouseClickDelay);

    public MenuScreen(int state) {

    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        loadImages();
        createRectangleButtons(container);
        loadFonts();
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
        drawButtons(container);
        drawText(container);
    }

    private void drawGrass(GameContainer container) {
        for (int x = 0; x < container.getWidth(); x += grass.getWidth()) {
            for (int y = 0; y < container.getHeight(); y += grass.getHeight()) {
                grass.draw(x, y);
            }
        }
    }

    private void drawButtons(GameContainer container) {
    
        button.draw(container.getWidth() / 2 - button.getWidth() / 2, 150);
        button.draw(container.getWidth() / 2 - button.getWidth() / 2, 300);
        //button.draw(container.getWidth() / 2 - button.getWidth() / 2, 450);

    }

    private void drawText(GameContainer container) {
        ttf.drawString(container.getWidth() / 2 - ttf.getWidth(title) / 2, 50, title, Color.black);
        ttf2.drawString(container.getWidth() / 2 - ttf2.getWidth(button2text) / 2, 225, button1text, Color.orange);
        ttf2.drawString(container.getWidth() / 2 - ttf2.getWidth(button2text) / 2, 375, button2text, Color.orange);
        //ttf2.drawString(container.getWidth() / 2 - ttf2.getWidth(button3text) / 2, 525, button3text, Color.orange);
    }

    private void createRectangleButtons(GameContainer container) {
        startGameButton = new Rectangle(container.getWidth()/2 - button.getWidth() / 2, 
                200, button.getWidth(),
                80);
    }

    private void loadImages() throws SlickException {
        grass = new Image("ressources/grass.png");
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

        if (startGameButton.contains(x, y)) {
            game.enterState(1);
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public int getID() {
        return 0;
    }

}