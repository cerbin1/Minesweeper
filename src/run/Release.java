package run;

import game.Game;
import game.Mediator;
import game.Size;
import game.sound.SoundRepository;

public class Release {
    public static void main(String[] args) {
        Size size = new Size(10, 10);
        Game game = new Game(size, 10);
        SoundRepository soundRepository = new SoundRepository();
        new Mediator(game, soundRepository).showFrame();
    }
}
