package secondassignment;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board {
    private boolean picked = false;
    private boolean turn = true;
    private boolean GAME_STATUS = true;
    private int Prow;
    private int Pcol;
    private int winner = 0;
    private static final int WINNING_NUMBER = 4;
    private String Knight;
    private JButton[][] buttons;
    private static JPanel Panel;
    
    public Board(int BoardSize){
        Panel = new JPanel();
        Panel.setLayout(new GridLayout(BoardSize, BoardSize));
        JButton[][] b = new JButton[BoardSize][BoardSize];
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[i].length; j++) {
                if (i == 0 && j == 0 || i == 0 && j == BoardSize - 1) {
                    Knight = "W";
                } else if (i == BoardSize - 1 && j == 0 || i == BoardSize - 1 && j == BoardSize - 1) {
                    Knight = "B";
                } else {
                    Knight = "";
                }
                JButton button = new JButton(Knight);
                button.setBackground(Color.RED);
                b[i][j] = button;
                b[i][j].addActionListener(new MoveActionListener(i, j));
                Panel.add(b[i][j]);
            }
        }
        buttons = b;
    }
    public JPanel getBoardPanel() {
        Panel.setBackground(Color.LIGHT_GRAY);
        return Panel;
    }
    public void Refresh() {
        if (!GAME_STATUS) {
            if (winner == 1) {
                JOptionPane.showMessageDialog(Panel, "Player White won!", "OK", JOptionPane.PLAIN_MESSAGE);
            }
            if (winner == 2) {
                JOptionPane.showMessageDialog(Panel, "Player Black won!", "OK", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }
    class MoveActionListener implements ActionListener {
        private int row;
        private int col;
        public MoveActionListener(int x, int y) {
            col = y;
            row = x;
        }
        @Override
        public void actionPerformed(ActionEvent a) {
            JButton click = (JButton) a.getSource();
            if (GAME_STATUS) {
                if (picked == false) {
                    if (turn) {
                        if ("W".equals(click.getText())) {
                            Knight = "W";
                            Prow = row;
                            Pcol = col;
                            click.setText("");
                            picked = true;
                        } else {
                            JOptionPane.showMessageDialog(Panel, "Pick the WHITE knight!", "Ok", JOptionPane.PLAIN_MESSAGE);
                        }
                    } else if (turn == false) {
                        if ("B".equals(click.getText())) {
                            Knight = "B";
                            Prow = row;
                            Pcol = col;
                            click.setText("");
                            picked = true;
                        } else {
                            JOptionPane.showMessageDialog(Panel, "Pick the BLACK knight!", "Ok", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                } else if (picked == true) {
                    if (turn == true) {
                        if ("".equals(click.getText())) {
                            if (Prow + 1 == row || Prow - 1 == row) {
                                if (Pcol + 2 == col || Pcol - 2 == col) {
                                    click.setText(Knight);
                                    turn = !turn;
                                    picked = false;
                                    if (Knight.charAt(0) == 'W') {
                                        click.setBackground(Color.WHITE);
                                        click.setForeground(Color.BLACK);
                                    } else {
                                        click.setBackground(Color.BLACK);
                                        click.setForeground(Color.WHITE);
                                    }
                                    Knight = "";
                                }
                            } else if (Prow + 2 == row || Prow - 2 == row) {
                                if (Pcol + 1 == col || Pcol - 1 == col) {
                                    click.setText(Knight);
                                    turn = !turn;
                                    picked = false;
                                    if (Knight.charAt(0) == 'W') {
                                        click.setBackground(Color.WHITE);
                                        click.setForeground(Color.BLACK);
                                    } else {
                                        click.setBackground(Color.BLACK);
                                        click.setForeground(Color.WHITE);
                                    }
                                    Knight = "";
                                }
                            } else {
                                JOptionPane.showMessageDialog(Panel, "Invalid Move", "Ok", JOptionPane.PLAIN_MESSAGE);
                            }
                        } else if ("W".equals(click.getText())) {
                            buttons[Prow][Pcol].setText(Knight);
                            Knight = click.getText();
                            click.setText("");
                            Prow = row;
                            Pcol = col;
                        } else {
                            JOptionPane.showMessageDialog(Panel, "Invalid Move", "Ok", JOptionPane.PLAIN_MESSAGE);
                        }
                    } else if(turn == false) {
                        if("".equals(click.getText())) {
                            if(Prow + 1 == row || Prow - 1 == row) {
                                if(Pcol + 2 == col || Pcol - 2 == col) {
                                    click.setText(Knight);
                                    turn = !turn;
                                    picked = false;
                                    if(Knight.charAt(0) == 'W') {
                                        click.setBackground(Color.WHITE);
                                        click.setForeground(Color.BLACK);
                                    } else {
                                        click.setBackground(Color.BLACK);
                                        click.setForeground(Color.WHITE);
                                    }
                                    Knight = "";
                                }
                            } else if(Prow + 2 == row || Prow - 2 == row) {
                                if (Pcol + 1 == col || Pcol - 1 == col) {
                                    click.setText(Knight);
                                    turn = !turn;
                                    picked = false;
                                    if(Knight.charAt(0) == 'W') {
                                        click.setBackground(Color.WHITE);
                                        click.setForeground(Color.BLACK);
                                    } else {
                                        click.setBackground(Color.BLACK);
                                        click.setForeground(Color.WHITE);
                                    }
                                    Knight = "";
                                }
                            } else {
                                JOptionPane.showMessageDialog(Panel, "Invalid Move", "Ok", JOptionPane.PLAIN_MESSAGE);
                            }
                        } else if("B".equals(click.getText())) {
                            buttons[Prow][Pcol].setText(Knight);
                            Knight = click.getText();
                            click.setText("");
                            Prow = row;
                            Pcol = col;
                        } else {
                            JOptionPane.showMessageDialog(Panel, "Invalid Move", "Ok", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                }
                int horizCheckW = 0, horizCheckB = 0, vertCheckW = 0, vertCheckB = 0, 
                        leftDiagW = 0, leftDiagB = 0, rightDiagW = 0, rightDiagB = 0; // checkers
                for(int i = 0; i < buttons.length; i++) {
                    for(int j = 0; j < buttons[i].length; j++) {
                        {
                            if(buttons[i][j].getBackground() == Color.WHITE) {
                                horizCheckW++;
                                horizCheckB = 0;
                            } else if(buttons[i][j].getBackground() == Color.BLACK) {
                                horizCheckB++;
                                horizCheckW = 0;
                            } else {
                                horizCheckW = 0;
                                horizCheckB = 0;
                            }
                        }
                        //vertical check
                        {
                        if(buttons[j][i].getBackground() == Color.WHITE){
                            vertCheckW++;
                            vertCheckB=0;
                        } else if(buttons[j][i].getBackground() == Color.BLACK){
                            vertCheckB++;
                            vertCheckW=0;
                        } else {
                            vertCheckW=0;
                            vertCheckB=0;
                        }
                    }
                    if(i==j){
                        if(buttons[i][j].getBackground() == Color.WHITE){
                            rightDiagW++;
                            rightDiagB=0;
                        } else if(buttons[i][j].getBackground() == Color.BLACK){
                            rightDiagB++;
                            rightDiagW=0;
                        } else {
                            rightDiagW=0;
                            rightDiagB=0;
                        }
                    }
                        if(i + j == buttons.length) {
                            if(buttons[i][j].getBackground() == Color.WHITE) {
                                leftDiagW++;
                                leftDiagB = 0;
                            } else if (buttons[i][j].getBackground() == Color.BLACK) {
                                leftDiagB++;
                                leftDiagW = 0;
                            } else {
                                leftDiagW = 0;
                                leftDiagB = 0;
                            }
                        }
                        if(horizCheckW >= WINNING_NUMBER || vertCheckW >= WINNING_NUMBER || leftDiagW >= WINNING_NUMBER || rightDiagW >= WINNING_NUMBER) {
                            winner = 1;
                            GAME_STATUS = false;
                        } else if (horizCheckB >= WINNING_NUMBER || vertCheckB >= WINNING_NUMBER || leftDiagB >= WINNING_NUMBER || rightDiagB >= WINNING_NUMBER) {
                            winner = 2;
                            GAME_STATUS = false;
                        }
                    }
                }
                Refresh();
            }
        }
    }
}
