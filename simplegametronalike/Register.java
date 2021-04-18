package simplegametronalike;

import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public final class Register extends JFrame {
    public Register(){
       
        setBounds(10, 10, 905, 700);
        setBackground(Color.DARK_GRAY);
        setTitle("Register");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setVisible(true);
        startGameWindow();
    }
    
    public void startGameWindow(){
        String[] colors = {/*list of colors in string kind*/};

        JComboBox player1Color = new JComboBox(colors);
        JComboBox player2Color = new JComboBox(colors);

        player1Color.setEditable(false);
        player2Color.setEditable(false);

        JTextField player1Name = new JTextField();
        JTextField player2Name = new JTextField();

        Object[] message = {"Name of Player 1:", player1Name, "Color of Player 1:", 
                            player1Color, "Name of Player 2:", player2Name, "Color of Player 2:", player2Color,};

        int option = JOptionPane.showConfirmDialog(null, message, "Enter the names and choose the colors!", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION){
            Color colorOfP1;
            Color colorOfP2;

            if (player1Color.getSelectedItem() == "Yellow Color") colorOfP1 = Color.yellow;
            else if (player1Color.getSelectedItem() == "Blue Color") colorOfP1 = Color.CYAN;
            else colorOfP1 = Color.red;

            if (player2Color.getSelectedItem() == "Yellow Color") colorOfP2 = Color.yellow;
            else if (player2Color.getSelectedItem() == "Blue Color") colorOfP2 = Color.CYAN;
            else colorOfP2 = Color.red;
        } else {
            System.exit(0);
        }
    }
}
