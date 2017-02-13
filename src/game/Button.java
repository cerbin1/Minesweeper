package game;

import javax.swing.*;
import java.awt.*;

import static game.Application.getBombCounterColor;
import static java.awt.Color.*;

class Button {
    private final Field field;
    private final JButton jButton;

    Button(Field field, JButton jButton) {
        this.field = field;
        this.jButton = jButton;
        field.setFloodFillListener(createFloodFillListener());
    }

    private FloodFillListener createFloodFillListener() {
        return () -> {
            if (field.getNearBombsCount() != 0) {
                field.setDiscovered(true);
                jButton.setEnabled(false);
                jButton.setBackground(getBombCounterColor(field.getNearBombsCount()));
                jButton.setText(Integer.toString(field.getNearBombsCount()));
            } else {
                jButton.setBackground(Color.darkGray);
            }
        };
    }

    JButton getJButton() {
        return jButton;
    }

    void displayBomb() {
        if (field.isBomb()) {
            jButton.setBackground(field.isFlag() ? GREEN : RED);
           /* File file = new File("C:\\Users\\bartek\\Desktop\\Projekty\\Minesweeper\\src\\resources\\bomb.png");
            System.out.println(file.exists());
            try {
                Image image = ImageIO.read(getClass().getResource("/resources/1.png"));
//                Image image1 = image.getScaledInstance(10, 10, Image.SCALE_SMOOTH);
                jButton.setIcon(new ImageIcon(image));
            } catch (IOException ignored) {
                throw new RuntimeException("File not found");
            }*/
        }
    }

    boolean isDiscovered() {
        return field.isDiscovered();
    }

    void toggleFlag() {
        if (isFlag()) {
            setUnflagged();
        } else {
            setFlagged();
        }
    }

    boolean isFlag() {
        return field.isFlag();
    }

    private void setUnflagged() {
        field.setFlag(false);
        jButton.setText("");
    }

    private void setFlagged() {
        field.setFlag(true);
        jButton.setText("?");
        jButton.setForeground(new Color(239, 144, 35));
    }

    boolean isBomb() {
        return field.isBomb();
    }

    void setBomb(boolean bomb) {
        field.setBomb(bomb);
    }

    void discover() {
        field.setDiscovered(true);
        getJButton().setEnabled(false);
        jButton.setBackground(getBombCounterColor(field.getNearBombsCount()));
        jButton.setText(Integer.toString(field.getNearBombsCount()));
    }

    boolean hasNearBombs() {
        return field.getNearBombsCount() > 0;
    }
}
