package debug.game;

import game.Game;
import game.Mediator;
import game.Size;

public class DebugMediator extends Mediator {
    public DebugMediator(Size size, Game game) {
        super(size, game);
        displayAllBombs();
    }

    @Override
    public void bombsCountChanged(int bombsCount) {
        super.bombsCountChanged(bombsCount);
        displayAllBombs();
    }
}
