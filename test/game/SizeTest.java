package game;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SizeTest {
    @Test
    public void shouldGetValues() throws Exception {
        // given
        Size size = new Size(10, 20);

        // when
        int width = size.getWidth();
        int height = size.getHeight();

        // then
        assertEquals(10, width);
        assertEquals(20, height);
    }
}
