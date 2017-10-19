/**
 * @todo add delete button
 * @todo add user selector
 * @todo make dragging snap to upper and lower edge of display
*/

package gui;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.util.HashMap;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import gui.COinJActions;
import gui.BackgroundPanel;

/**
 * A {@link javax.swing.JFrame} with a background pattern, a reveal button for
 * controlling whether button descriptive text is displayed, and which resizes
 * based upon whether the descriptive text is displayed.
 *
 * Currently, this is entirely too mixed in with the actions and buttons
 * that would more properly should be part of the client.
 */
public class ButtonBar extends JFrame {
    static final private String SOMETHING_ELSE = "other";

    JPanel panel;
    HashMap<String, Action> actionMap;

    /**
     * Construct a ButtonBar with a collection of default buttons and actions.
     * There should be another constructor that supplies the name of the
     * background pattern.
     */
    public ButtonBar(HashMap<String, Action> actionMap) {
        this.actionMap = actionMap;
        panel = new BackgroundPanel(new BorderLayout(), "SunstoneFullBack");
        //Create the buttonbar.
        JToolBar buttonbar = new JToolBar();

        addButtons(buttonbar);
        buttonbar.setFloatable(false);
        buttonbar.setRollover(true);
        buttonbar.setOpaque(false);


        // TODO add code to hide buttonbar when app is not in foreground
        // com.apple.eawt Interface AppForegroundListener

        //Lay out the main panel.
        Dimension buttonBarSize = buttonbar.getPreferredSize();
        setPreferredSize(buttonBarSize);
        panel.add(buttonbar, BorderLayout.PAGE_START);
        add(panel);
    }

    /** ButtonLabelItemListener hides/reveals button label text */
    protected static class ButtonLabelItemListener implements ItemListener {
        private JButton buttons[];
        private JFrame frame;
        private JToolBar toolbar;

        ButtonLabelItemListener(JButton buttons[], JFrame frame,
                JToolBar toolbar) {
            this.buttons = buttons;
            this.frame = frame;
            this.toolbar = toolbar;
        }
        public void itemStateChanged(ItemEvent e) {
            boolean shouldHide = e.getStateChange() == ItemEvent.SELECTED;
            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setHideActionText(shouldHide);
            }

            // NB: this seems like something of a hack, but frame.validate();
            // frame.pack(); doesn't seem to do anything.
            toolbar.setPreferredSize(null);
            Dimension toolbarSize = toolbar.getPreferredSize();
            frame.setPreferredSize(toolbarSize);
            frame.pack();
        }
    }

    /**
     * Create a {@code JCheckBox} with triangle icons for the check box
     * that casuse the buttons to have their text revealed or hidden.
     */
    protected JCheckBox makeRevealCheckBox(JButton buttons[],
                       JToolBar toolbar) {
        // TODO: Use larger icons for reveal triangle. Are there
        // standard Swing icons for reveal/hide triangles?
        ImageIcon triRight = COinJActions.makeIcon("triRight", "hide labels");
        ImageIcon triDown = COinJActions.makeIcon("triDown", "show labels");

        JCheckBox revealBox = new JCheckBox();
        revealBox.setIcon(triDown);
        revealBox.setSelectedIcon(triRight);

        // ButtonLabelItemListener hides/reveals button label text
        ButtonLabelItemListener hider =
            new ButtonLabelItemListener(buttons, this, toolbar);
        revealBox.addItemListener(hider);

        return revealBox;
    }

    /**
     * A placeholder method to demonstrate how to add buttons. This should
     * be in the client.
     */
    protected void addButtons(JToolBar toolBar) {
        JButton button = null;

        // New Address
        JButton createAddrButton = makeButton(actionMap.get("newAddress"));

    // Addresses
    JButton addresses = makeButton(actionMap.get("addressList"));

        // New To Do
        JButton createToDoButton = makeButton(actionMap.get("newToDo"));

        // To Dos
        JButton toDos = makeButton(actionMap.get("toDoList"));

        // New Memo
        JButton createNewMemo = makeButton(actionMap.get("newMemo"));

        // Memos
        JButton memos = makeButton(actionMap.get("memoList"));

        // New Event
        JButton createEventButton = makeButton(actionMap.get("newEvent"));

        // Go To
        JButton gotoButton = makeButton(actionMap.get("goTo"));

        // Go To Today
        JButton gotoTodayButton = makeButton(actionMap.get("goToToday"));

        // DateBook
        JButton datebookButton = makeButton(actionMap.get("dateBook"));

        // Print
        JButton printButton = makeButton(actionMap.get("print"));

        // Find
        JButton findButton = makeButton(actionMap.get("find"));

        // TODO: Extract the code for dealing with reveal/unreveal into another
        // method.
        // Hide button labels.
        JButton buttons[] = {
            createAddrButton, addresses, createToDoButton, toDos,
            createNewMemo, memos, createEventButton, gotoButton,
            gotoTodayButton, datebookButton, printButton, findButton
        };
        JCheckBox revealBox = makeRevealCheckBox(buttons, toolBar);
        // TODO: Iterate over buttons array.
        toolBar.add(revealBox);
        toolBar.add(createAddrButton);
        toolBar.add(addresses);
        toolBar.add(createToDoButton);
        toolBar.add(toDos);
        toolBar.add(createNewMemo);
        toolBar.add(memos);
        toolBar.add(createEventButton);
        toolBar.add(gotoButton);
        toolBar.add(gotoTodayButton);
        toolBar.add(printButton);
        toolBar.add(findButton);

        // TODO: Make artwork for a Delete button and add here.
        // Delete

        // TODO: Change this into a drop-down user selector.
        button = new JButton("Another button");
        button.setActionCommand(SOMETHING_ELSE);
        button.setToolTipText("Something else");
        toolBar.add(button);

    }

    /**
     * Make a {@code JButton} from an {@code Action} that has text
     * centered below, has the underlying background showing through,
     * and has no borders.
     */
    public JButton makeButton(Action action) {
        JButton button = new JButton(action);

        // place text below icon
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);

        // make button transparent, allowing theme to show through
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        return button;
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    static private void createAndShowGUI() {
        //Create and set up the window.
        ButtonBar frame = new ButtonBar(MenuBar.createActions());

        // TODO: this should probably be moved to ButtonBar()
        frame.setLocation(0,25);
        frame.setDefaultLookAndFeelDecorated(false);
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // make frame transparent, allowing theme to show through
        frame.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * A method for running the {@code ButtonBar} as a standalone component,
     * for testing.
     */
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
