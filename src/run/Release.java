package run;

import game.Game;
import game.Mediator;
import game.Size;

public class Release {
    public static void main(String[] args) {
        Size size = new Size(10, 10);
        Game game = new Game(size, 10);
        new Mediator(size, game).showFrame();
    }
}
