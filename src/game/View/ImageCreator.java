package game.View;

import game.Field;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class ImageCreator {
    public Image getImage(Field field) {
        if (field.isFlag() && field.isBomb()) {
            return getImageIfCorrect("/flaggedBomb.png");
        } else if (field.isBomb()) {
            return getImageIfCorrect("/bomb.png");
        } else if (field.isFlag() && !field.isBomb()) {
            return getImageIfCorrect("/missedFlag.png");
        } else if (field.isFlag()) {
            return getImageIfCorrect("/emptyFlag.png");
        } else {
            return getImageIfCorrect("/flag.png");
        }
    }

    private Image getImageIfCorrect(String name) {
        try {
            return ImageIO.read(getClass().getResource(name)).getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        } catch (IOException ignored) {
            throw new RuntimeException("File not found");
        }
    }
}
