package game;

import org.junit.Test;

public class GameFactoryTest {
    @Test
    public void shouldCreateGame() {
        // when
        GameFactory.create(10, 8, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowOnNegativeWidth() {
        // when
        GameFactory.create(-2, 8, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowOnInvalidHeight() {
        // when
        GameFactory.create(5, 0, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowOnBombsMoreThanSize() {
        // when
        GameFactory.create(5, 5, 26);
    }
}
