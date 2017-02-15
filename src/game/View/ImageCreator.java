package game.View;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class ImageCreator {

    public Image getBomb() {
        return getImageIfCorrect("/bomb.png");
    }

    public Image getFlaggedBomb() {
        return getImageIfCorrect("/flaggedBomb.png");
    }

    public Image getMissedFlag() {
        return getImageIfCorrect("/missedFlag.png");
    }

    public Image getEmptyFlag() {
        return getImageIfCorrect("/emptyFlag.png");
    }

    public Image getFlag() {
        return getImageIfCorrect("/flag.png");
    }

    private Image getImageIfCorrect(String name) {
        try {
            return ImageIO.read(getClass().getResource(name)).getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        } catch (IOException ignored) {
            throw new RuntimeException("File not found");
        }
    }
}
