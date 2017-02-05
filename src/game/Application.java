package game;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

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

    private Application(Size size, int bombsCount) {
        this.size = size;
        game = GameFactory.create(size, bombsCount);
        this.bombsCount = bombsCount;

        buttons = createButtons();
    }

    private Button[][] createButtons() {
        Button[][] buttons = new Button[size.getWidth()][size.getHeight()];
        for (int i = 0; i < size.getWidth(); i++) {
            for (int j = 0; j < size.getHeight(); j++) {
                buttons[i][j] = new Button(game.getField(i, j), createSingleJButton(i, j));
            }
        }
        return buttons;
    }

    private JButton createSingleJButton(int i, int j) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(50, 50));
        button.addMouseListener(new FieldMouseAdapter(this, i, j));
        button.setFont(new Font("Arial", Font.BOLD, 20));
        return button;
    }

    void displayAllBombs() {
        for (int i = 0; i < size.getWidth(); i++) {
            for (int j = 0; j < size.getHeight(); j++) {
                buttons[i][j].displayBomb();
            }
        }
    }

    static Color getBombCounterColor(int bombsCount) {
        return BOMBS_COUNTER_COLORS[bombsCount - 1];
    }

    private void createAndShowBoard() {
        JFrame frame = createFrame("Minesweeper");

        messageBox = createDefaultTextLabel("TextLabel", "Start clicking");
        bombsLeftLabel = createDefaultTextLabel("BombsCounter", "Bombs left: " + bombsCount);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(game.getWidth(), game.getHeight()));
        for (int i = 0; i < game.getWidth(); i++) {
            for (int j = 0; j < game.getHeight(); j++) {
                panel.add(buttons[i][j].getJButton());
            }
        }

        JPanel outerPanel = new JPanel();
        outerPanel.setLayout(new BorderLayout());
        outerPanel.add(bombsLeftLabel, BorderLayout.PAGE_START);
        outerPanel.add(panel, BorderLayout.CENTER);
        outerPanel.add(messageBox, BorderLayout.PAGE_END);

        frame.add(outerPanel);
        frame.pack();
    }

    private JFrame createFrame(String frameName) {
        JFrame frame = new JFrame(frameName);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
        return frame;
    }

    private JLabel createDefaultTextLabel(String name, String defaultText) {
        JLabel textLabel = new JLabel(name, SwingConstants.CENTER);
        textLabel.setText(defaultText);
        textLabel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        textLabel.setFont(new Font("Arial", Font.BOLD, 20));
        return textLabel;
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Application(new Size(10, 10), 10).createAndShowBoard());
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
