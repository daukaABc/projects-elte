/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tdgame;

import java.util.ArrayList;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author ilya
 */
class Main extends StateBasedGame{
    
    private ArrayList<String> IDs;
    
    public static void main(String[] args) throws SlickException{
        new AppGameContainer(new Main(), 1152, 736, false).start();
    }

    public Main() {
        super("Battle for Varois-et-Chaignot");
        Input.disableControllers();
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        IDs=new ArrayList<>();
        addState(new MenuScreen(0));
        addState(new SelectMapScreen(1));
        addState(new GameScreen());
        IDs.add("GameMenu");
        IDs.add("GameScreen");
    }
    
    public int getStateID(String name){
        return IDs.indexOf(name);
    }
}
