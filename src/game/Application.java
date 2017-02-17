package game;

import game.View.ComponentCreator;

import javax.swing.*;
import java.awt.Color;

import static java.awt.Color.*;

public class Application {
    private final static Color[] BOMBS_COUNTER_COLORS = {
            BLUE, GREEN, RED, MAGENTA, ORANGE, LIGHT_GRAY, YELLOW, PINK
    };

    private final Size size;

    private final Game game;

    private final Button[][] buttons;

    private JLabel messageBox, bombsLeftLabel;

    private final int bombsCount;

    private final ComponentCreator componentCreator;

    public Application(Size size, int bombsCount, Game game) {
        this.size = size;
        this.game = game;
        this.bombsCount = bombsCount;

        componentCreator = new ComponentCreator(this);

        buttons = createButtons();
    }

    private Button[][] createButtons() {
        Button[][] buttons = new Button[size.getWidth()][size.getHeight()];
        for (int i = 0; i < size.getWidth(); i++) {
            for (int j = 0; j < size.getHeight(); j++) {
                buttons[i][j] = new Button(game.getField(i, j), componentCreator.createSingleJButton(this, i, j));
            }
        }
        return buttons;
    }

    public JLabel getMessageBox() {
        return messageBox;
    }

    public JLabel getBombsLeftLabel() {
        return bombsLeftLabel;
    }


    protected void displayAllBombs() {
        for (int i = 0; i < size.getWidth(); i++) {
            for (int j = 0; j < size.getHeight(); j++) {
                buttons[i][j].displayBomb();
            }
        }
    }

    static Color getBombCounterColor(int bombsCount) {
        return BOMBS_COUNTER_COLORS[bombsCount - 1];
    }

    public void createAndShowBoard() {
        JFrame frame = componentCreator.createMainJFrame();

        messageBox = componentCreator.createDefaultTextLabel("TextLabel", "Start clicking");
        bombsLeftLabel = componentCreator.createDefaultTextLabel("BombsCounter", "Bombs left: " + bombsCount);

        JPanel panel = componentCreator.createJPanelWithJButtons();
        JPanel outerPanel = componentCreator.createOuterPanel(panel);
        frame.add(outerPanel);
        frame.pack();
    }

    public Button getButton(int x, int y) {
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

    public Game getGame() {
        return game;
    }

    void setGameAsWon() {
        displayAllBombs();
        setMessageBoxText("You win!");
        game.finish();
    }
}
