// Create a category pop-up.  The interaction between themeing and modeling
// is interesting. The data model for a category consists of a name, an id,
// and a color index.  For example:
// <category>
//   <name>Friends</name>
//   <id>9</id>
//   <colorIndex>12</colorIndex>
//   </category>
// The color index can only range from 1 to 12, but there can be more
// categories. The drop down // has a menu item structure of: {None,
// seperator, category items*, separator, Edit Categories...}
// Each category item has a block of color and the name to the right.

package gui;

import gui.Category;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class CategoryChooser extends JComboBox {
    private Category categories[];
    public CategoryChooser(Category categories[], String textureName) {
      super(categories);
      // FIXME: add actual appearence:
      // Menu contents: {None; seperator; categories; seperator; Edit Categories...}.
      // Disclose down trianble button to the right of categories menu.
      // wWen not selected, background is themed, and the selected category name has appropriate
      // color.
      setRenderer(new CategoryComboBoxRenderer());
      // FIXME: disable for now.  Substituing the ComponentUI results in no combo box being
      // displayed.
      //addPopupMenuListener(new CategoryPopupMenuListener(textureName));
    }

    class CategoryComboBoxRenderer extends JLabel implements ListCellRenderer {
        public CategoryComboBoxRenderer() {
            setOpaque(true);
            setHorizontalAlignment(CENTER);
            setVerticalAlignment(CENTER);
        }

        public Component getListCellRendererComponent(
            JList list, Object value, int index, boolean isSelected,
            boolean cellHasFocus) {
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            // Set the icon and text.  If icon was null, say so.
            // FIXME: Implement icon
            // ImageIcon icon = images[selectedIndex];
            Category category = (Category) value;
            String categoryName = category.getName();
            System.out.println("index: " + index);
            System.out.println("cellHasFocus: " + cellHasFocus);
            Color color = Category.getColorForIndex(category.getColorIndex());
            setText("<html><font size=\"2\" color=rgb(" +
                    color.getRed() + "," + color.getGreen() + "," +
                    color.getBlue() + ") face=\"Casual\">" + categoryName +  "</font></html>");
            return this;
        }
    }

    class CategoryPopupMenuListener implements PopupMenuListener {
        private String textureName;
        public CategoryPopupMenuListener(String textureName) {
            this.textureName = textureName;
        }
        public void popupMenuCanceled(PopupMenuEvent e) {}
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            // render the label with the "themed" background and the selected
            // categories color
            // e.source is a CategoryComboBox
            System.out.println("in popupMenuWillBecomeInvisible, e: " + e);
            CategoryChooser chooser = (CategoryChooser) e.getSource();
            System.out.println("chooser isinstanceof JComponent? " +
                              (chooser instanceof JComponent));
            chooser.setUI(new BackgroundComponentUI(textureName));
            //Category category = (Category) comboBox.getSelectedItem();
        }
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}
    }

    private static JPanel contentPane;

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("CategoryChooserDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        contentPane = new JPanel(new BorderLayout());
        contentPane.setOpaque(true);
        Category categories[] = {
            new Category("Babysitters", "16", 3),
            new Category("Birthday", "13", 1)
        };
        contentPane.add(new JLabel("Above"), BorderLayout.NORTH);
        contentPane.add(new CategoryChooser(categories, "SunStoneHilite2"), BorderLayout.CENTER);
        contentPane.add(new JLabel("Below"), BorderLayout.SOUTH);
        frame.setContentPane(contentPane);

        //Display the window.
        frame.setSize(450, 260);
        frame.setVisible(true);
    }

    public static void main(String args[]) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name",
                           "COinJ");
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }

}
