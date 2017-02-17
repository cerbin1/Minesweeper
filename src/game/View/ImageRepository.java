package game.View;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

import static java.awt.Image.SCALE_SMOOTH;

public class ImageRepository {
    public Image getBomb() {
        return getImageIcon("/bomb.png");
    }

    public Image getFlaggedBomb() {
        return getImageIcon("/flaggedBomb.png");
    }

    public Image getMissedFlag() {
        return getImageIcon("/missedFlag.png");
    }

    public Image getEmptyFlag() {
        return getImageIcon("/emptyFlag.png");
    }

    public Image getFlag() {
        return getImageIcon("/flag.png");
    }

    private Image getImageIcon(String name) {
        try {
            return ImageIO.read(getClass().getResource(name)).getScaledInstance(50, 50, SCALE_SMOOTH);
        } catch (IOException e) {
            throw new RuntimeException("No access to file " + name.substring(1));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("File " + name.substring(1) + " not found");
        }
    }
}
