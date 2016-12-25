package game;

import java.awt.*;

public class Application {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new GameFrame().displayGameBoard());
    }
}