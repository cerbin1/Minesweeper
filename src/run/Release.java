package run;

import game.Game;
import game.Mediator;
import game.Size;

import static javax.swing.SwingUtilities.invokeLater;

public class Release {
    public static void main(String[] args) {
        Size size = new Size(10, 10);
        Game game = new Game(size, 10);
        Mediator mediator  = new Mediator(size, game);
    }
}
