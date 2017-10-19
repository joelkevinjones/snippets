package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class CategoryComboBox extends JComboBox implements ActionListener {
    // TODO implement ComboBoxModel instead
    public CategoryComboBox(String names[]) {
        super(names);
        // TODO: substitute a more complicated calculation based upon location
        // and screen size
        setMaximumRowCount(names.length);
        addActionListener(this);
    }

    // behavior when rendering is that the "anchor" is a fixed width, but
    // the pop-up width is set based upon the length of the longest category
    // name.
    // Also, the pop-up list has more than just the categories:
    //  None
    //  seperator bar
    //  icon category 1
    //  ...
    //  icon last category
    //  seperator bar
    // Edit Categories...
    public void actionPerformed(ActionEvent e) {
        CategoryComboBox cb = (CategoryComboBox) e.getSource();
        String categoryName = (String) cb.getSelectedItem();
        System.out.println("setCategory(\"" + categoryName + "\")");
    }

    // TODO use the categories model
    static String[] categories = {
        "Babysitters",
        "Birthday",
        "Business",
        "Calls",
        "Christian Hol.",
        "...",
        "Travel",
        "URL",
        "US Holidays",
        "Vacation/Holiday",
        "Work"
    };

    static public String[] getCategories() {
        return categories;
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame();
        Container container = frame.getContentPane();
        JPanel panel = new JPanel(new BorderLayout());

        CategoryComboBox categoryComboBox =
            new CategoryComboBox(getCategories());
        panel.add(categoryComboBox);
        container.add(panel);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }
}
