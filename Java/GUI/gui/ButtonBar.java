/**
 * @todo add delete button
 * @todo add user selector
 * @todo make frame resize based on contents, particularl when revealing
 * @todo make dragging snap to upper and lower edge of display
*/

package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.LayoutManager;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseMotionListener;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import gui.BackgroundPanel;
//import gui.DraggableFrame;

/**
 * A {@link javax.swing.JFrame} with a background pattern, a reveal button for 
 * controlling whether button descriptive text is displayed, and which resizes
 * based upon whether the descriptive text is displayed.
 *
 * Currently, this is entirely too mixed in with the actions and buttons
 * that would more properly should be part of the client.
 */
public class ButtonBar extends JFrame {
    static final private String PREVIOUS = "previous";
    static final private String UP = "up";
    static final private String NEXT = "next";
    static final private String SOMETHING_ELSE = "other";

    JPanel panel;
    
    /**
     * Construct a ButtonBar with a collection of default buttons and actions.
     * There should be another constructor that supplies the name of the
     * background pattern.
     */
    public ButtonBar() {
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

    /**
     * Make an {@code ImageIcon} available through the resources of 
     * {@code ButtonBar}.
     */
    // TODO: Make the error-handling throw an exception.
    protected static ImageIcon makeIcon(String imageName, String altText) {
        //Look for the image.
	// TODO: fix to use system specific file seperator, not "/"?
        String imgLocation = "images/" + imageName + ".png";
        URL imageURL = ButtonBar.class.getResource(imgLocation);
	if (imageURL == null) {
	    System.err.println("couldn't find: " + imgLocation);
	}
	return new ImageIcon(imageURL, altText);
    }

    /** Convenience super class for actions attached to ButtonBar */
    protected abstract static class ToolbarActions extends AbstractAction {
	public ToolbarActions(String altText, String name, String iconName) {
	    putValue(SHORT_DESCRIPTION, altText);
	    putValue(NAME, name);
	    putValue(LARGE_ICON_KEY, makeIcon(iconName, altText));
	}
	/** Default (stupid) action.  This should be overridden */
	public void actionPerformed(ActionEvent ae) {
	    System.out.println("actionPerformed: " + 
			       getValue(SHORT_DESCRIPTION));
	}
    }

    protected static class CreateNewAddressAction extends ToolbarActions {
	public CreateNewAddressAction() {
	    super("Create New Address", "New Address", "NewAddress");
	}
    }
    
    protected static class AddressListAction extends ToolbarActions {
	public AddressListAction() {
	    super("Show Address List", "Addresses", "Addresses");
	}
    }

    protected static class CreateNewToDoAction extends ToolbarActions {
	public CreateNewToDoAction() {
	    super("Create New To Do", "New To Do", "New To Do");
	}
    }
    
    protected static class ToDoListAction extends ToolbarActions {
	public ToDoListAction() {
	    super("Show To Do List", "To Dos", "To Dos");
	}
    }

    protected static class CreateNewMemoAction extends ToolbarActions {
	public CreateNewMemoAction() {
	    super("Create New Memo", "New Memo", "New Memo");
	}
    }

    protected static class MemosAction extends ToolbarActions {
        public MemosAction() {
            super("Show Memo List", "Memos", "Memos");
        }
    }
    
    protected static class CreateNewEventAction extends ToolbarActions {
	public CreateNewEventAction() {
	    super("Create New Event", "New Event", "New Event");
	}
    }

    protected static class GotoAction extends ToolbarActions {
	public GotoAction() {
	    super("Go To", "Go To", "Go To");
	}
    }

    protected static class GotoTodayAction extends ToolbarActions {
	public GotoTodayAction() {
	    super("Today", "Today", "Today");
	}
    }

    protected static class DatebookAction extends ToolbarActions {
	public DatebookAction() {
	    super("Date Book", "Date Book", "Date Book");
	}
    }

    protected static class PrintAction extends ToolbarActions {
	public PrintAction() {
	    super("Print", "Print", "Print");
	}
    }

    protected static class FindAction extends ToolbarActions {
	public FindAction() {
	    super("Find", "Find", "Find");
	}
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
	ImageIcon triRight = makeIcon("triRight", "hide labels");
	ImageIcon triDown = makeIcon("triDown", "show labels");

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
	JButton createAddrButton = makeButton(new CreateNewAddressAction());
	
	// Addresses
	JButton addresses = makeButton(new AddressListAction());
	
	// New To Do
	JButton createToDoButton = makeButton(new CreateNewToDoAction());
	
	// To Dos
	JButton toDos = makeButton(new ToDoListAction());
	
	// New Memo
	JButton createNewMemo = makeButton(new CreateNewMemoAction());
	
	// Memos
	JButton memos = makeButton(new MemosAction());
	
	// New Event
	JButton createEventButton = makeButton(new CreateNewEventAction());

	// Go To
	JButton gotoButton = makeButton(new GotoAction());
	
	// Go To Today
	JButton gotoTodayButton = makeButton(new GotoTodayAction());

	// DateBook
	JButton datebookButton = makeButton(new DatebookAction());

	// Print
	JButton printButton = makeButton(new PrintAction());

	// Find
	JButton findButton = makeButton(new FindAction());

	// TODO: Extract the code for dealing with reveal/unreveal into another
	// method.
	// Hide button labels.
	JButton buttons[] = {
	    createAddrButton, addresses, createToDoButton, toDos, createNewMemo,
	    memos, createEventButton, gotoButton, gotoTodayButton,
	    datebookButton, printButton, findButton
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
        //button.addActionListener(this);
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
     * A placeholder method to demonstrate actions.
     */
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
	System.out.println(cmd);
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
	ButtonBar frame = new ButtonBar();
	
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