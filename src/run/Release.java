package run;

import game.Application;
import game.Game;
import game.Size;

import static javax.swing.SwingUtilities.invokeLater;

public class Release {
    public static void main(String[] args) {
        Size size = new Size(10, 10);
        Game game = new Game(size, 10);
        Application app = new Application(size, 10, game);

        invokeLater(app::createAndShowBoard);
    }
}
