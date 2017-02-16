package game.View;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

import static java.awt.Image.SCALE_SMOOTH;

public class ImageRepository {
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
            return ImageIO.read(getClass().getResource(name)).getScaledInstance(50, 50, SCALE_SMOOTH);
        } catch (IOException e) {
            throw new RuntimeException("No access to file " + name.substring(1));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("File " + name.substring(1) + " not found");
        }
    }
}
