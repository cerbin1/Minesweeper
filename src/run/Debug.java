package run;

import debug.game.DebugGame;
import debug.game.DebugMediator;
import game.Game;
import game.Mediator;
import game.Size;
import game.View.GameFrame;

import static javax.swing.SwingUtilities.invokeLater;

public class Debug {
    public static void main(String[] args) {
        Size size = new Size(10, 10);
        Game game = new DebugGame(size, 10);
        Mediator mediator = new DebugMediator(size, game);
    }
}
