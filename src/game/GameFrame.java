package game;

import javax.swing.*;

class GameFrame extends JFrame {
    GameFrame() {
        super("Minesweeper");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(300, 300);
    }

    void displayGameBoard() {
        setVisible(true);
    }
}
