package secondassignment;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class KnightGameGUI {
    private JFrame frame;
    private Board board;
    private final int INITIAL_BOARD_SIZE = 8;
    public KnightGameGUI() {
        frame = new JFrame("Knight Tournament");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board = new Board(INITIAL_BOARD_SIZE);
        frame.getContentPane().add(board.getBoardPanel(), BorderLayout.CENTER);
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);
        JMenu newMenu = new JMenu("New");
        gameMenu.add(newMenu);
        int[] boardSizes = new int[]{4, 6, 8};
        for (int boardSize : boardSizes) {
            JMenuItem sizeMenuItem = new JMenuItem(boardSize + "x" + boardSize);
            newMenu.add(sizeMenuItem);
            sizeMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.getContentPane().remove(board.getBoardPanel());
                    board = new Board(boardSize);
                    frame.getContentPane().add(board.getBoardPanel(),
                            BorderLayout.CENTER);
                    frame.pack();
                }
            });
        }
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        gameMenu.add(exitMenuItem);
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        frame.pack();
        frame.setVisible(true);
    }
}
