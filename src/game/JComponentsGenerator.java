package game;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

class JComponentsGenerator {
    private Application application;

    JComponentsGenerator(Application application) {
        this.application = application;
    }

    JButton createSingleJButton(Application application, int x, int y) {
        JButton jButton = new JButton();
        jButton.setPreferredSize(new Dimension(50, 50));
        jButton.addMouseListener(new FieldMouseAdapter(application, x, y));
        jButton.setFont(new Font("Arial", 0, 30));
        jButton.setMargin(new Insets(0, 0, 0, 0));
        return jButton;
    }

    JFrame createJFrame(String frameName) {
        JFrame frame = new JFrame(frameName);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
        return frame;
    }

    JLabel createDefaultTextLabel(String name, String defaultText) {
        JLabel textLabel = new JLabel(name, SwingConstants.CENTER);
        textLabel.setText(defaultText);
        textLabel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        textLabel.setFont(new Font("Arial", Font.BOLD, 20));
        return textLabel;
    }

    JPanel createJPanelWithJButtons() {
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

    JPanel createOuterPanel(JPanel panel) {
        JPanel outerPanel = new JPanel();
        outerPanel.setLayout(new BorderLayout());
        outerPanel.add(application.getBombsLeftLabel(), BorderLayout.PAGE_START);
        outerPanel.add(panel, BorderLayout.CENTER);
        outerPanel.add(application.getMessageBox(), BorderLayout.PAGE_END);
        return outerPanel;
    }
}
