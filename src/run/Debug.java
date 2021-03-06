package run;

import debug.game.DebugGame;
import debug.game.DebugMediator;
import debug.game.DebugSoundRepository;
import game.Game;
import game.Size;

public class Debug {
    public static void main(String[] args) {
        Size size = new Size(10, 10);
        Game game = new DebugGame(size, 10);
        new DebugMediator(game, new DebugSoundRepository()).showFrame();
    }
}
