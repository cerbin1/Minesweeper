package debug.game;

import game.Game;
import game.Mediator;
import game.sound.SoundRepository;

public class DebugMediator extends Mediator {
    public DebugMediator(Game game, SoundRepository soundRepository) {
        super(game, soundRepository);
        displayAllBombs();
    }

    @Override
    public void bombsCountChanged(int bombsCount) {
        super.bombsCountChanged(bombsCount);
        displayAllBombs();
    }
}
