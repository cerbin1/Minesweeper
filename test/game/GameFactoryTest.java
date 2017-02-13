package game;

import org.junit.Test;

public class GameFactoryTest {
    @Test
    public void shouldCreateGame() {
        // when
        GameFactory.create(new Size(10, 8), 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowOnNegativeWidth() {
        // when
        GameFactory.create(new Size(-2, 8), 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowOnInvalidHeight() {
        // when
        GameFactory.create(new Size(5, 0), 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowOnBombsMoreThanSize() {
        // when
        GameFactory.create(new Size(5, 5), 26);
    }
}
