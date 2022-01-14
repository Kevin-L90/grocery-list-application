package ui;

import model.Event;
import model.EventLog;
import model.Grocery;
import model.GroceryList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represents Grocery List Application
public class App extends JFrame {
    private final int width = 800;
    private final int height = 1200;
    private final TitleBar title;
    private final DefaultListModel<String> model;
    private final JList<String> display;
    private final ButtonPanel btnPanel;
    private final JButton add;
    private final JButton save;
    private final JButton load;
    private GroceryList groceryList;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private String date;

    public App() {
        super("Grocery List");
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        groceryList = new GroceryList();
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        this.date = date.toString();

        title = new TitleBar(this.date, width);
        model = new DefaultListModel<>();
        display = new JList<>(model);
        btnPanel = new ButtonPanel(width);

        display.setFont(new Font("Sans-serif", Font.PLAIN, 30));

        add(title, BorderLayout.NORTH);
        add(display, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        add = btnPanel.getAdd();
        save = btnPanel.getSave();
        load = btnPanel.getLoad();

        addDisplayListener();
        addAddListeners();
        addSaveListener();
        addLoadListener();

        setVisible(true);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (Event event : EventLog.getInstance()) {
                    System.out.println(event.toString() + "\n\n");
                }
            }
        });
    }

    // EFFECTS: adds listener to display, allows selecting Grocery
    private void addDisplayListener() {
        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList list = (JList) e.getSource();
                int index = list.locationToIndex(e.getPoint());
                if (index >= 0) {
                    Object o = list.getModel().getElementAt(index);
                    convertToGrocery(o);
                }
            }
        };
        display.addMouseListener(mouseListener);
    }

    // EFFECTS: converts the object collected in addDisplayListener to Grocery
    private void convertToGrocery(Object g) {
        String s = g.toString();
        String[] n = s.split("");
        int quantity = 0;
        String name = "";
        StringBuilder f = new StringBuilder();

        for (int i = 0; i < n.length; i++) {
            if ((n[i].matches("[0-9]+"))) {
                f.append(n[i]); //appending
            } else {
                quantity = Integer.parseInt(f.toString());
                name = s.substring(i + 1);
                break;
            }
        }
        Grocery grocery = new Grocery(name, quantity);
        groceryFrame(grocery);
    }

    // MODIFIES: this
    // EFFECTS: opens new JFrame displaying selected Grocery and options for modifying it
    private void groceryFrame(Grocery g) {
        for (Grocery grocery : groceryList.getGroceries()) {
            if (grocery.equals(g)) {
                g = grocery;
            }
        }
        JFrame addFrame = new GroceryOptions(g, groceryList);
        addFrame.setSize(width, 500);
        addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addFrame.setVisible(true);
        addFrame.setResizable(false);
        addFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                refresh();
            }
        });
    }

    // EFFECTS: adds listeners to add, save, and load
    private void addAddListeners() {
        add.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                addPanel();
            }
        });
    }

    // EFFECTS: creates new JFrame to add Grocery
    private void addPanel() {
        JFrame add = new JFrame();
        JPanel panel = new JPanel(new GridLayout(3, 1));
        JPanel field1Panel = new JPanel();
        JPanel field2Panel = new JPanel();
        JPanel buttonPanel = new JPanel();
        add.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add.setSize(width, 500);

        JTextField field1 = createField("Input Grocery");
        JTextField field2 = createField("Input Quantity");

        JButton enter = createEnterButton(add, field1, field2);

        field1Panel.add(field1);
        field2Panel.add(field2);
        buttonPanel.add(enter);
        panel.add(field1Panel);
        panel.add(field2Panel);
        panel.add(enter);
        add.add(panel);

        add.setVisible(true);
    }

    // MODIFIES: add
    // EFFECTS: Creates enter button for add JFrame
    private JButton createEnterButton(JFrame frame, JTextField field1, JTextField field2) {
        JButton enter = new JButton("Enter");
        enter.setFont(new Font("Sans-serif", Font.PLAIN, 30));
        enter.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String name = field1.getText();
                int quantity = Integer.parseInt(field2.getText());
                groceryList.addGrocery(new Grocery(name, quantity));
                refresh();
                frame.dispose();
            }
        });
        return enter;
    }

    // MODIFIES: add
    // EFFECTS: creates text fields for add
    private JTextField createField(String string) {
        JTextField field = new JTextField(string);
        field.setFont(new Font("Sans-serif", Font.PLAIN, 30));
        field.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                field.setText("");
            }
        });
        return field;
    }

    // EFFECTS: saves current Grocery list
    // Will catch FileNotFoundException
    private void addSaveListener() {
        save.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jsonWriter = new JsonWriter("./data/SavedLists/" + date + ".json");
                try {
                    jsonWriter.open();
                    jsonWriter.write(groceryList);
                    jsonWriter.close();
                } catch (FileNotFoundException n) {
                    System.out.println("Unable to write file");
                }
            }
        });
    }

    // EFFECTS: opens JFrame with all saved lists
    private void addLoadListener() {
        load.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                loadFiles();
            }
        });
    }

    private void loadFiles() {
        JFrame savedLists = new JFrame("Load");
        savedLists.setSize(width, height / 2);

        File folder = new File("data/SavedLists");
        File[] listOfFiles = folder.listFiles();
        DefaultListModel<String> saved = new DefaultListModel<>();
        for (File file: listOfFiles) {
            saved.addElement(file.getName().replace(".json",""));
        }
        JList<String> load = new JList<>(saved);
        load.setFont(new Font("Sans-serif", Font.PLAIN, 30));

        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList list = (JList) e.getSource();
                int index = list.locationToIndex(e.getPoint());
                if (index >= 0) {
                    Object o = list.getModel().getElementAt(index);
                    load(o.toString());
                    savedLists.dispose();
                }
            }
        };
        load.addMouseListener(mouseListener);
        savedLists.add(load);
        savedLists.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        savedLists.setVisible(true);
        savedLists.setResizable(false);
    }

    private void load(String date) {
        jsonReader = new JsonReader("./data/SavedLists/" + date + ".json");
        try {
            groceryList = jsonReader.read();
        } catch (IOException n) {
            System.out.println("Unable to read or no file to read.");
        }
        refresh();
    }

    // EFFECTS: refreshes display to show contents of groceryList
    // MODIFIES: this
    private void refresh() {
        model.removeAllElements();
        for (Grocery grocery : groceryList.getGroceries()) {
            model.addElement(grocery.getQuantity() + " " + grocery.getName());
        }
        display.revalidate();
        display.repaint();
    }
}
