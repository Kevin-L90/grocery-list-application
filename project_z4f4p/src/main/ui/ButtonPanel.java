package ui;

import javax.swing.*;
import java.awt.*;

// Represents option panel
public class ButtonPanel extends JPanel {
    private final JButton add;
    private final JButton save;
    private final JButton load;

    public ButtonPanel(int width) {
        setBackground(Color.cyan);
        setPreferredSize(new Dimension(width, 100));

        Icon icon1 = new ImageIcon("./data/Add.png");
        add = new JButton(icon1);
        add.setBorder(BorderFactory.createEmptyBorder());
        add.setPreferredSize(new Dimension(50, 50));
        add(add);

        add(Box.createHorizontalStrut(20));

        Icon icon2 = new ImageIcon("./data/Save.png");
        save = new JButton(icon2);
        save.setBorder(BorderFactory.createEmptyBorder());
        save.setPreferredSize(new Dimension(50, 50));
        add(save);

        add(Box.createHorizontalStrut(20));

        Icon icon3 = new ImageIcon("./data/Load.png");
        load = new JButton(icon3);
        load.setBorder(BorderFactory.createEmptyBorder());
        load.setPreferredSize(new Dimension(50, 50));
        add(load);
    }

    public JButton getAdd() {
        return add;
    }

    public JButton getSave() {
        return save;
    }

    public JButton getLoad() {
        return load;
    }
}
