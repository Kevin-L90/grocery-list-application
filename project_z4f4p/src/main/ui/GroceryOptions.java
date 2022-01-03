package ui;

import model.Grocery;
import model.GroceryList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Represents the options the user has to modify Grocery within GroceryList
public class GroceryOptions extends JFrame {
    private final JButton increase;
    private final JButton decrease;
    private final JButton remove;
    private final JButton cancel;
    private final JPanel input;
    private final JButton confirm;
    private final JTextField amount;
    private Boolean plus;

    public GroceryOptions(Grocery g, GroceryList groceryList) {
        input = new JPanel();
        amount = new JTextField();
        confirm = new JButton("Confirm");
        increase = new JButton("Increase Quantity");
        decrease = new JButton("Decrease Quantity");
        remove = new JButton("Remove Item");
        cancel = new JButton("Cancel");

        addTitle(g);
        addButtons();
        addTextField(g, groceryList);
    }

    private void addTitle(Grocery g) {
        JLabel grocery = new JLabel(g.toString());
        grocery.setFont(new Font("Sans-serif", Font.PLAIN, 50));
        grocery.setHorizontalAlignment(SwingConstants.CENTER);
        add(grocery, BorderLayout.NORTH);
    }

    private void addButtons() {
        JPanel panel = new JPanel();
        JPanel options1 = new JPanel();
        JPanel options2 = new JPanel();

        options1.setPreferredSize(new Dimension(800, 60));
        options2.setPreferredSize(new Dimension(800, 60));

        increase.setFont(new Font("Sans-serif", Font.PLAIN, 20));
        decrease.setFont(new Font("Sans-serif", Font.PLAIN, 20));
        remove.setFont(new Font("Sans-serif", Font.PLAIN, 20));
        cancel.setFont(new Font("Sans-serif", Font.PLAIN, 20));

        increase.setPreferredSize(new Dimension(200, 50));
        decrease.setPreferredSize(new Dimension(200, 50));
        remove.setPreferredSize(new Dimension(200, 50));
        cancel.setPreferredSize(new Dimension(200, 50));

        options1.add(increase);
        options1.add(Box.createHorizontalStrut(20));
        options1.add(decrease);

        options2.add(remove);
        options2.add(Box.createHorizontalStrut(20));
        options2.add(cancel);

        panel.add(Box.createVerticalStrut(150));
        panel.add(options1, BorderLayout.NORTH);
        panel.add(Box.createVerticalStrut(50));
        panel.add(options2, BorderLayout.SOUTH);
        add(panel, BorderLayout.CENTER);
    }

    private void addTextField(Grocery g, GroceryList groceryList) {
        amount.setFont(new Font("Sans-serif", Font.PLAIN, 30));
        amount.setPreferredSize(new Dimension(350,50));
        confirm.setFont(new Font("Sans-serif", Font.PLAIN, 30));
        confirm.setPreferredSize(new Dimension(150, 50));

        input.add(amount, BorderLayout.CENTER);
        input.add(confirm, BorderLayout.CENTER);
        add(input, BorderLayout.SOUTH);
        input.setVisible(false);

        addListeners1(g, groceryList);
        addListeners2(g, groceryList);
    }

    // MODIFIES: this
    // EFFECTS: adds mouse listeners to input text field, cancel, and confirm
    private void addListeners2(Grocery g, GroceryList groceryList) {
        amount.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                amount.setText("");
            }
        });
        cancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
        confirm.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String value = amount.getText();
                int quantity = Integer.parseInt(value);
                if (plus) {
                    for (int i = 0; i < groceryList.numGroceries(); i++) {
                        if (groceryList.getGrocery(i).toString().equals(g.toString())) {
                            groceryList.getGrocery(i).increaseQuantity(quantity);
                            break;
                        }
                    }
                    dispose();
                } else {
                    if (quantity >= g.getQuantity()) {
                        for (int i = 0; i < groceryList.numGroceries(); i++) {
                            if (groceryList.getGrocery(i).toString().equals(g.toString())) {
                                groceryList.getGrocery(i).decreaseQuantity(quantity);
                                break;
                            }
                        }
                        JFrame tooLow = new JFrame();
                        tooLow.setSize(800, 300);
                        JLabel low1 = new JLabel("You have decreased the quantity below 0");
                        JLabel low2 = new JLabel("Quantity set to 1");
                        JButton low3 = new JButton("OK");
                        low1.setFont(new Font("Sans-serif", Font.PLAIN, 30));
                        low2.setFont(new Font("Sans-serif", Font.PLAIN, 30));
                        low3.setFont(new Font("Sans-serif", Font.PLAIN, 30));
                        low3.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                tooLow.dispose();
                            }
                        });
                        tooLow.add(low1, BorderLayout.NORTH);
                        tooLow.add(low2);
                        tooLow.add(low3, BorderLayout.EAST);
                        tooLow.setVisible(true);
                    } else {
                        for (int i = 0; i < groceryList.numGroceries(); i++) {
                            if (groceryList.getGrocery(i).toString().equals(g.toString())) {
                                groceryList.getGrocery(i).decreaseQuantity(quantity);
                                break;
                            }
                        }
                    }
                }
                dispose();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: adds listeners to increase, decrease, and remove buttons
    private void addListeners1(Grocery g, GroceryList groceryList) {
        increase.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                plus = true;
                input.setVisible(true);
                amount.setText("Input Amount to Increase");
            }
        });
        decrease.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                plus = false;
                input.setVisible(true);
                amount.setText("Input Amount to Decrease");
            }
        });
        remove.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int i = 0; i < groceryList.numGroceries(); i++) {
                    if (groceryList.getGrocery(i).toString().equals(g.toString())) {
                        groceryList.remove(groceryList.getGrocery(i));
                        break;
                    }
                }
                dispose();
            }
        });
    }
}
