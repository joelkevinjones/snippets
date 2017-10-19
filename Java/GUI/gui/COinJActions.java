package gui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

import java.awt.Toolkit;

import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

/**
 * Convenience super class for actions attached to ButtonBar, MenuBar,.
 */
class COinJActions extends AbstractAction {
    static int menuShortCutKeyMask =
            Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

    public COinJActions(String altText, String name, String iconName,
                          String menuName, KeyStroke keyStroke) {
        putValue(SHORT_DESCRIPTION, altText);
        putValue(NAME, name);
        if (iconName != null)
            putValue(LARGE_ICON_KEY, makeIcon(iconName, altText));
        putValue("menuName", menuName);
        if (keyStroke != null)
             putValue(ACCELERATOR_KEY, keyStroke);
    }

    /**
     * Default (stupid) action.  This should be overridden.
     */
    public void actionPerformed(ActionEvent ae) {
        System.out.println("actionPerformed: " + getValue(SHORT_DESCRIPTION));
    }

    /**
     * Make an {@code ImageIcon} available through the resources of
     * {@code COinJActions}.
     */
    // TODO: Make the error-handling throw an exception.
    static ImageIcon makeIcon(String imageName, String altText) {
        //Look for the image.
        // TODO: fix to use system specific file seperator, not "/"?
        String imgLocation = "images/" + imageName + ".png";
        URL imageURL = COinJActions.class.getResource(imgLocation);
        if (imageURL == null) {
            System.err.println("couldn't find: " + imgLocation);
        }
        return new ImageIcon(imageURL, altText);
    }
}

class CreateNewAddressAction extends COinJActions {
    static int mask = menuShortCutKeyMask | InputEvent.ALT_DOWN_MASK;
    static KeyStroke keyStroke =
        KeyStroke.getKeyStroke(KeyEvent.VK_A, mask, true);

    public CreateNewAddressAction() {
        super("Create New Address", "New Address", "NewAddress", "Address...",
              keyStroke);
    }
}

class AddressListAction extends COinJActions {
    static int mask = menuShortCutKeyMask | InputEvent.SHIFT_DOWN_MASK;
    static KeyStroke keyStroke =
        KeyStroke.getKeyStroke(KeyEvent.VK_A, mask, true);

    public AddressListAction() {
        super("Show Address List", "Addresses", "Addresses", "Address List",
              keyStroke);
    }
}

class CreateNewToDoAction extends COinJActions {
    static int mask = menuShortCutKeyMask | InputEvent.ALT_DOWN_MASK;
    static KeyStroke keyStroke =
        KeyStroke.getKeyStroke(KeyEvent.VK_T, mask, true);

    public CreateNewToDoAction() {
        super("Create New To Do", "New To Do", "New To Do", "To Do...",
              keyStroke);
    }
}

class ToDoListAction extends COinJActions {
    static int mask = menuShortCutKeyMask | InputEvent.SHIFT_DOWN_MASK;
    static KeyStroke keyStroke =
        KeyStroke.getKeyStroke(KeyEvent.VK_T, mask, true);

    public ToDoListAction() {
        super("Show To Do List", "To Dos", "To Dos", "To Do List", keyStroke);
    }
}

class CreateNewMemoAction extends COinJActions {
    static int mask = menuShortCutKeyMask | InputEvent.ALT_DOWN_MASK;
    static KeyStroke keyStroke =
        KeyStroke.getKeyStroke(KeyEvent.VK_N, mask, true);

    public CreateNewMemoAction() {
        super("Create New Memo", "New Memo", "New Memo", "Memo...", keyStroke);
    }
}

class MemosAction extends COinJActions {
    static int mask = menuShortCutKeyMask | InputEvent.SHIFT_DOWN_MASK;
    static KeyStroke keyStroke =
        KeyStroke.getKeyStroke(KeyEvent.VK_N, mask, true);

    public MemosAction() {
        super("Show Memo List", "Memos", "Memos", "Memo List", keyStroke);
    }
}

class CreateNewEventAction extends COinJActions {
    static int mask = menuShortCutKeyMask | InputEvent.ALT_DOWN_MASK;
    static KeyStroke keyStroke =
        KeyStroke.getKeyStroke(KeyEvent.VK_E, mask, true);

    public CreateNewEventAction() {
        super("Create New Event", "New Event", "New Event", "Event...",
               keyStroke);
    }
}

class CreateNewUntimedEventAction extends COinJActions {
    static int mask = menuShortCutKeyMask | InputEvent.ALT_DOWN_MASK;
    static KeyStroke keyStroke =
        KeyStroke.getKeyStroke(KeyEvent.VK_U, mask, true);

    public CreateNewUntimedEventAction() {
        super("Create New Event", "New Event", null, "Untimed Event...",
              keyStroke);
    }
}

class GotoAction extends COinJActions {
    static int mask = menuShortCutKeyMask;
    static KeyStroke keyStroke =
        KeyStroke.getKeyStroke(KeyEvent.VK_R, mask, true);

    public GotoAction() {
        super("Go To", "Go To", "Go To", "Go To Date...", keyStroke);
    }
}

class GotoTodayAction extends COinJActions {
    static int mask = menuShortCutKeyMask;
    static KeyStroke keyStroke =
        KeyStroke.getKeyStroke(KeyEvent.VK_T, mask, true);

    public GotoTodayAction() {
        super("Today", "Today", "Today", "Today", keyStroke);
    }
}

class DatebookAction extends COinJActions {
    public DatebookAction() {
        super("Date Book", "Date Book", "Date Book", "Date Book", null);
    }
}

class PrintAction extends COinJActions {
    static int mask = menuShortCutKeyMask;
    static KeyStroke keyStroke =
        KeyStroke.getKeyStroke(KeyEvent.VK_P, mask, true);

    public PrintAction() {
        super("Print", "Print", "Print", "Print...", keyStroke);
    }
}

class FindAction extends COinJActions {
    static int mask = menuShortCutKeyMask;
    static KeyStroke keyStroke =
        KeyStroke.getKeyStroke(KeyEvent.VK_F, mask, true);

    public FindAction() {
        super("Find", "Find", "Find", "Find...", keyStroke);
    }
}

class AttachItemAction extends COinJActions {
    static int mask = menuShortCutKeyMask;
    static KeyStroke keyStroke =
        KeyStroke.getKeyStroke(KeyEvent.VK_KP_UP, mask, true);

    public AttachItemAction() {
        super("", "", "attach", "", keyStroke);
    }
}

class NextItemAction extends COinJActions {
    static int mask = menuShortCutKeyMask;
    static KeyStroke keyStroke =
        KeyStroke.getKeyStroke(KeyEvent.VK_KP_UP, mask, true);

    public NextItemAction() {
        super("", "", "SunStoneRightArrow", "", keyStroke);
    }
}

class PrevItemAction extends COinJActions {
    static int mask = menuShortCutKeyMask;
    static KeyStroke keyStroke =
        KeyStroke.getKeyStroke(KeyEvent.VK_KP_UP, mask, true);

    public PrevItemAction() {
        super("", "", "SunStoneLeftArrow", "", keyStroke);
    }
}

