package game;

import javax.swing.*;
import java.awt.*;

import static java.awt.Color.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Application {
    private final static Color[] BOMBS_COUNTER_COLORS = {
            BLUE, GREEN, RED, MAGENTA, ORANGE, LIGHT_GRAY, YELLOW, PINK
    };

    private final Size size;

    private final Game game;
    private boolean firstClick = true;

    private final Button[][] buttons;

    private JLabel messageBox, bombsLeftLabel;

    private final int bombsCount;

    private final JComponentsGenerator jComponentsGenerator;

    private Application(Size size, int bombsCount) {
        this.size = size;
        game = GameFactory.create(size, bombsCount);
        this.bombsCount = bombsCount;

        jComponentsGenerator = new JComponentsGenerator(this);

        buttons = createButtons();
    }

    private Button[][] createButtons() {
        Button[][] buttons = new Button[size.getWidth()][size.getHeight()];
        for (int i = 0; i < size.getWidth(); i++) {
            for (int j = 0; j < size.getHeight(); j++) {
                buttons[i][j] = new Button(game.getField(i, j), jComponentsGenerator.createSingleJButton(this, i, j));
            }
        }
        return buttons;
    }

    JLabel getMessageBox() {
        return messageBox;
    }

    JLabel getBombsLeftLabel() {
        return bombsLeftLabel;
    }


    void displayAllBombs() {
        for (int i = 0; i < size.getWidth(); i++) {
            for (int j = 0; j < size.getHeight(); j++) {
                buttons[i][j].displayIfBomb();
            }
        }
    }

    static Color getBombCounterColor(int bombsCount) {
        return BOMBS_COUNTER_COLORS[bombsCount - 1];
    }

    private void createAndShowBoard() {
        JFrame frame = jComponentsGenerator.createJFrame("Minesweeper");

        messageBox = jComponentsGenerator.createDefaultTextLabel("TextLabel", "Start clicking");
        bombsLeftLabel = jComponentsGenerator.createDefaultTextLabel("BombsCounter", "Bombs left: " + bombsCount);

        JPanel panel = jComponentsGenerator.createJPanelWithJButtons();
        JPanel outerPanel = jComponentsGenerator.createOuterPanel(panel);
        frame.add(outerPanel);
        frame.pack();
    }


    Button getButton(int x, int y) {
        return buttons[x][y];
    }

    void setMessageBoxText(String text) {
        messageBox.setText(text);
    }

    void clearMessageBox() {
        messageBox.setText("");
    }

    void updateFlaggedBombsCount() {
        int unflaggedBombs = game.countUnflaggedBombs();
        bombsLeftLabel.setText("Bombs left: " + unflaggedBombs);
        if (unflaggedBombs < 0) {
            messageBox.setText("Some flags are wrong!");
        }
    }

    Game getGame() {
        return game;
    }

    boolean isFirstClick() {
        return firstClick;
    }

    void setFirstClick(boolean firstClick) {
        this.firstClick = firstClick;
    }

    void repositionFirstClickedBomb(Button button) {
        if (isFirstClick()) {
            if (button.isBomb()) {
                System.out.println("losuje bombe nowa");
                game.plantSingleBomb();
                button.setBomb(false);
            }
            game.fillBombsCounters();
            setFirstClick(false);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Application(new Size(10, 10), 10).createAndShowBoard());
    }
}
