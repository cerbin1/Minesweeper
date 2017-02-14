package run;

import debug.game.DebugApplication;
import debug.game.DebugGame;
import game.Application;
import game.Game;
import game.Size;

import static javax.swing.SwingUtilities.invokeLater;

public class Debug {
    public static void main(String[] args) {
        Size size = new Size(10, 10);
        Game game = new DebugGame(size, 10);
        Application app = new DebugApplication(size, 10, game);

        invokeLater(app::createAndShowBoard);
    }
}
