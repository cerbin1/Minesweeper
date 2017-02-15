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

    private final ComponentGenerator componentGenerator;

    public Application(Size size, int bombsCount, Game game) {
        this.size = size;
        this.game = game;
        this.bombsCount = bombsCount;

        componentGenerator = new ComponentGenerator(this);

        buttons = createButtons();
    }

    private Button[][] createButtons() {
        Button[][] buttons = new Button[size.getWidth()][size.getHeight()];
        for (int i = 0; i < size.getWidth(); i++) {
            for (int j = 0; j < size.getHeight(); j++) {
                buttons[i][j] = new Button(game.getField(i, j), componentGenerator.createSingleJButton(this, i, j));
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


    protected void displayAllBombs() {
        for (int i = 0; i < size.getWidth(); i++) {
            for (int j = 0; j < size.getHeight(); j++) {
                buttons[i][j].displayIfBomb();
            }
        }
    }

    static Color getBombCounterColor(int bombsCount) {
        return BOMBS_COUNTER_COLORS[bombsCount - 1];
    }

    public void createAndShowBoard() {
        JFrame frame = componentGenerator.createJFrame("Minesweeper");

        messageBox = componentGenerator.createDefaultTextLabel("TextLabel", "Start clicking");
        bombsLeftLabel = componentGenerator.createDefaultTextLabel("BombsCounter", "Bombs left: " + bombsCount);

        JPanel panel = componentGenerator.createJPanelWithJButtons();
        JPanel outerPanel = componentGenerator.createOuterPanel(panel);
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
}
