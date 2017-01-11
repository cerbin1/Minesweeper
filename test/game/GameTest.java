package game;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {
    @Test
    public void shouldCountFlagsOnStart() {
        // given
        Game game = new Game(5, 5, 0);

        // when
        int result = game.countFlagPoints();

        // then
        assertEquals(result, 0);
    }

    @Test
    public void shouldCountUnflaggedBomb() {
        // given
        Game game = new Game(5, 5, 0);
        Field flaggedBomb = game.getField(0, 0);
        Field unflaggedBomb = game.getField(0, 1);
        flaggedBomb.setBomb(true);
        unflaggedBomb.setBomb(true);
        flaggedBomb.setFlag(true);

        // when
        int result = game.countFlagPoints();

        // then
        assertEquals(result, 1);
    }

    @Test
    public void shouldCountFlaggedBombs() {
        // given
        Game game = new Game(5, 5, 0);
        Field flaggedBomb = game.getField(0, 0);
        Field flaggedField = game.getField(0, 2);
        flaggedBomb.setBomb(true);
        flaggedBomb.setFlag(true);
        flaggedField.setFlag(true);

        // when
        int result = game.countFlagPoints();

        // then
        assertEquals(result, 0);
    }
}
