package debug.game;

import game.Game;
import game.Mediator;
import game.Size;
import game.sound.SoundRepository;

public class DebugMediator extends Mediator {
    public DebugMediator(Size size, Game game, SoundRepository soundRepository) {
        super(size, game, soundRepository);
        displayAllBombs();
    }

    @Override
    public void bombsCountChanged(int bombsCount) {
        super.bombsCountChanged(bombsCount);
        displayAllBombs();
    }
}
