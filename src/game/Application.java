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

    public Application(Size size, int bombsCount, Game game) {
        this.size = size;
        this.game = game;
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

    private JButton createSingleJButton(int x, int y) {
        JButton jButton = new JButton();
        jButton.setPreferredSize(new Dimension(50, 50));
        jButton.addMouseListener(new FieldMouseAdapter(this, x, y));
        jButton.setFont(new Font("Arial", 0, 30));
        jButton.setMargin(new Insets(0, 0, 0, 0));
        return jButton;
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
        JFrame frame = createJFrame("Minesweeper");

        messageBox = createDefaultTextLabel("TextLabel", "Start clicking");
        bombsLeftLabel = createDefaultTextLabel("BombsCounter", "Bombs left: " + bombsCount);

        JPanel panel = createJPanelWithJButtons();
        JPanel outerPanel = createOuterPanel(panel);
        frame.add(outerPanel);
        frame.pack();
    }

    private JFrame createJFrame(String frameName) {
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

    private JPanel createJPanelWithJButtons() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(game.getWidth(), game.getHeight()));
        for (int i = 0; i < game.getWidth(); i++) {
            for (int j = 0; j < game.getHeight(); j++) {
                panel.add(buttons[i][j].getJButton());
            }
        }
        return panel;
    }

    private JPanel createOuterPanel(JPanel panel) {
        JPanel outerPanel = new JPanel();
        outerPanel.setLayout(new BorderLayout());
        outerPanel.add(bombsLeftLabel, BorderLayout.PAGE_START);
        outerPanel.add(panel, BorderLayout.CENTER);
        outerPanel.add(messageBox, BorderLayout.PAGE_END);
        return outerPanel;
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
