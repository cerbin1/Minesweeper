package game;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CounterTest {
    @Test
    public void shouldGetDefaultValue() throws Exception {
        // given
        Counter counter = new Counter();

        // when
        int count = counter.getCount();

        // then
        assertEquals(0, count);
    }

    @Test
    public void shouldInc() throws Exception {
        // given
        Counter counter = new Counter();

        // when
        counter.inc();
        counter.inc();

        // then
        assertEquals(2, counter.getCount());
    }

    @Test
    public void shouldDec() throws Exception {
        // given
        Counter counter = new Counter();

        // when
        counter.dec();
        counter.dec();

        // then
        assertEquals(-2, counter.getCount());
    }
}
