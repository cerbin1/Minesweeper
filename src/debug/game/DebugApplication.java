package debug.game;

import game.Application;
import game.Game;
import game.Size;

public class DebugApplication extends Application {
    public DebugApplication(Size size, int bombsCount, Game game) {
        super(size, bombsCount, game);
        displayAllBombs();
    }
}
