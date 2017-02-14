package debug.game;

import game.Game;
import game.Size;

public class DebugGame extends Game {
    public DebugGame(Size size, int bombsCount) {
        super(size, bombsCount);
    }

    @Override
    public boolean isGameDone() {
        return false;
    }
}
