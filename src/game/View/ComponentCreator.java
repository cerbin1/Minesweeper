package game.View;

import game.Application;
import game.FieldMouseAdapter;
import game.Game;

import javax.swing.*;
import java.awt.*;

import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class ComponentCreator {
    private Application application;

    public ComponentCreator(Application application) {
        this.application = application;
    }

    public JButton createSingleJButton(Application application, int x, int y) {
        JButton jButton = new JButton();
        jButton.setPreferredSize(new Dimension(50, 50));
        jButton.addMouseListener(new FieldMouseAdapter(application, x, y));
        jButton.setFont(new Font("Arial", 0, 30));
        jButton.setMargin(new Insets(0, 0, 0, 0));
        return jButton;
    }

    public JFrame createMainJFrame() {
        JFrame frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
        return frame;
    }

    public JLabel createDefaultTextLabel(String name, String defaultText) {
        JLabel textLabel = new JLabel(name, SwingConstants.CENTER);
        textLabel.setText(defaultText);
        textLabel.setBorder(createEmptyBorder(15, 15, 15, 15));
        textLabel.setFont(new Font("Arial", Font.BOLD, 20));
        return textLabel;
    }

    public JPanel createJPanelWithJButtons() {
        JPanel panel = new JPanel();
        Game game = application.getGame();
        panel.setLayout(new GridLayout(game.getWidth(), game.getHeight()));
        for (int i = 0; i < game.getWidth(); i++) {
            for (int j = 0; j < game.getHeight(); j++) {
                panel.add(application.getButton(i, j).getJButton());
            }
        }
        return panel;
    }

    public JPanel createOuterPanel(JPanel panel) {
        JPanel outerPanel = new JPanel();
        outerPanel.setLayout(new BorderLayout());
        outerPanel.add(application.getBombsLeftLabel(), BorderLayout.PAGE_START);
        outerPanel.add(panel, BorderLayout.CENTER);
        outerPanel.add(application.getMessageBox(), BorderLayout.PAGE_END);
        return outerPanel;
    }
}
