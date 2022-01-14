package ui;

import javax.swing.*;
import java.awt.*;

// Represents Title in the JFrame
public class TitleBar extends JPanel {
    public TitleBar(String title, int width) {
        setPreferredSize(new Dimension(width, 100));

        JLabel titleText = new JLabel(title);
        titleText.setFont(new Font("Sans-serif", Font.BOLD, 60));
        titleText.setHorizontalAlignment(JLabel.CENTER);

        add(titleText);
    }
}
