// java -Dcom.apple.mrj.application.apple.menu.about.name=COinJ gui.MenuBar
package gui;

import gui.ButtonBar;

// TODO: move this to a seperate file
import com.apple.eawt.AboutHandler;
import com.apple.eawt.AppEvent;
import com.apple.eawt.Application;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import java.util.HashMap;

/*
<<Apple icon>>
Palm Desktop
    About Palm Desktop
    ------
    Preferences... cmd-,      # Not automatically generated
    ---------
    Services>
    ----------
    Hide Palm Desktop cmd-H
    Hide Others opt-cmd-H
    Show All
    ......
    Quic Palm Desktop
File
    New
    Open cmd-O
    -------
    Close Window cmd-W
    Close File
    Save A Copy
    ---------
    Merge
    Export
    Import
    ----------
    Print cmd-P
Edit
View
    Address List shift-cmd-A
    To Do List shift-cmd-T
    Memo List shift-cmd-N
    Date Book
        Day shift-cmd-D
        Week shift-cmd-W
        Month shift-cmd-M
        --------
        Next Date Book shift-cmd-M
        --------
        <check> Show Events
        <check> Show To Dos
    --------
    Sort ...
    Columns ...
    --------
    Hide Toolbar cmd-B
    --------
    Desktop Memos
    This Week-Uncompleted    # are these custom
    Today Uncompleted        # from memorize view?
Create
    Address... opt-cmd-A
    To Do... opt-cmd-T
    Memo... opt-cmd-N
    Event... opt-cmd-E
    Untimed Event... opt-cmd-U
    --------
    UA Computer Science     # these are custom
    UIUC Computer Science   # from templates
    --------
    Attach To               # disabled if there is not a current element
        Existing Item... cmd-L
    --------------
    New Address...
    New To Do...
    New Memo...
    New Event...
    New Untimed Event...
    -------------
    UA Computer Science # these are custom
    UIUC CS Department  # from templates
    --------------
    File...
    ------------
    Instant Attach cmd-I
    --------
    Create Template...      # disabled if there is not a current element
    Delete Templates...
Locate
    Today cmd-T
    Go To Date... cmd-R
    --------
    Find... cmd-F
    Find Again cmd-G        # disabled if no current search
    ---------
    Recent>                 # items that were recently visited (20, settable?)
    ---------
    Add To Menu
    Remove...
HotSync
    User
        None                # user list, current checked
    ------------
    Edit Users...
    --------
    Install Handheld Files...
    ---------------
    Setup...
    Conduit Settings...
    ---------
    View Log...
Window
    Minimize cmd-M
    Zoom                     # toggles between quasi-full-screen & small
    Cycle Through Windows cmd-`
    ----------
    Bring All to Front
<<AppleScript icon>>
    Open Scripts Folder
    ------------
    Create Project Memo     # scripts that have been
    ExportToXML             # added to ? directory
Help
    *** Search ______ ****
    Palm Desktop Help
*/

public class MenuBar extends JMenuBar {
    public static class MacAboutHandler implements AboutHandler {
        public void handleAbout(AppEvent.AboutEvent e) {
            System.out.println("About COinJ");
        }
    }

    private static JMenuItem makeMenuItem(Action action) {
        JMenuItem jmi = new JMenuItem(action);
        // TODO: Is there something in the Google ? for doing null check and
        // cast?
        Object menuNameObj = action.getValue("menuName");
        if (menuNameObj != null && menuNameObj instanceof String) {
            String menuName = (String) menuNameObj;
            if (menuName != null) {
                jmi.setText(menuName);
            }
        }
        return jmi;
    }

    public MenuBar(HashMap<String, Action> actionMap) {
        // File Menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(new JMenuItem("New"));
        fileMenu.add(new JMenuItem("Open..."));
        fileMenu.addSeparator();
        fileMenu.add(new JMenuItem("Close Window"));
        fileMenu.add(new JMenuItem("Close File"));
        fileMenu.add(new JMenuItem("Save A Copy..."));
        fileMenu.addSeparator();
        fileMenu.add(new JMenuItem("Merge..."));
        fileMenu.add(new JMenuItem("Export..."));
        fileMenu.add(new JMenuItem("Import..."));
        fileMenu.addSeparator();
        fileMenu.add(makeMenuItem(actionMap.get("print")));
        add(fileMenu);

        // View Menu
        // TODO: use actions from ButtonBar
        JMenu viewMenu = new JMenu("View");
        viewMenu.add(makeMenuItem(actionMap.get("addressList")));
        viewMenu.add(makeMenuItem(actionMap.get("toDoList")));
        viewMenu.add(makeMenuItem(actionMap.get("memoList")));
        JMenu dateBookMenu = new JMenu("Date Book");
        dateBookMenu.add(new JMenuItem("Day"));
        dateBookMenu.add(new JMenuItem("Week"));
        dateBookMenu.add(new JMenuItem("Month"));
        viewMenu.add(dateBookMenu);
        viewMenu.addSeparator();
        // TODO: add "Sort ..." and "Columns ..."
        viewMenu.addSeparator();
        viewMenu.add(new JMenuItem("Hide Toolbar"));
        viewMenu.addSeparator();
        viewMenu.add(new JMenuItem("Desktop Memos")); // subset of "Memo List"
        add(viewMenu);

        // Create Menu
        JMenu createMenu = new JMenu("Create");
        createMenu.add(makeMenuItem(actionMap.get("newAddress")));
        createMenu.add(makeMenuItem(actionMap.get("newToDo")));
        createMenu.add(makeMenuItem(actionMap.get("newMemo")));
        createMenu.add(makeMenuItem(actionMap.get("newEvent")));
        createMenu.add(makeMenuItem(actionMap.get("newUntimedEvent")));
        createMenu.addSeparator();
        // Add stuff from templates
        createMenu.addSeparator();
        createMenu.add(new JMenuItem("Attach To"));
        createMenu.addSeparator();
        createMenu.add(new JMenuItem("Create Template..."));
        createMenu.add(new JMenuItem("Delete Template..."));
        add(createMenu);

        // Locate Menu
        JMenu locateMenu = new JMenu("Locate");
        locateMenu.add(makeMenuItem(actionMap.get("goToToday")));
        locateMenu.add(makeMenuItem(actionMap.get("goTo")));
        locateMenu.addSeparator();
        locateMenu.add(makeMenuItem(actionMap.get("find")));
        locateMenu.add(new JMenuItem("Find Again"));
        locateMenu.addSeparator();
        locateMenu.add(new JMenuItem("Recent"));
        // TODO: add stuff from history
        locateMenu.addSeparator();
        locateMenu.add(new JMenuItem("Add To Menu"));
        locateMenu.add(new JMenuItem("Remove..."));
        add(locateMenu);

        // Window Menu
        JMenu windowMenu = new JMenu("Window");
        windowMenu.add(new JMenuItem("Minimize"));
        windowMenu.add(new JMenuItem("Zoom"));
        windowMenu.add(new JMenuItem("Cycle Through Windows"));
        windowMenu.addSeparator();
        windowMenu.add(new JMenuItem("Bring All to Front"));
    }

    static HashMap<String, Action> createActions() {
        HashMap<String, Action> map = new HashMap<String, Action>();

        // New Address
        map.put("newAddress", new CreateNewAddressAction());

        // Addresses
        map.put("addressList", new AddressListAction());

        // New To Do
        map.put("newToDo", new CreateNewToDoAction());

        // To Dos
        map.put("toDoList", new ToDoListAction());

        // New Memo
        map.put("newMemo", new CreateNewMemoAction());

        // Memos
        map.put("memoList", new MemosAction());

        // New Event
        map.put("newEvent", new CreateNewEventAction());

        // Go To
        map.put("goTo", new GotoAction());

        // Go To Today
        map.put("goToToday", new GotoTodayAction());

        // DateBook
        map.put("dateBook", new DatebookAction());

        // Print
        map.put("print", new PrintAction());

        // Find
        map.put("find", new FindAction());

        // New Untimed Event (NB not in button bar, just in menu)
        map.put("newUntimedEvent", new CreateNewUntimedEventAction());

        // Attach item (memo edit window)
        map.put("attachItem", new AttachItemAction());

        // Next item (memo edit window)
        map.put("nextItem", new NextItemAction());

        // Previous item (memo edit window)
        map.put("prevItem", new PrevItemAction());

    return map;
    }

    private static void createAndShowGUI() {
        HashMap<String, Action> actionMap = createActions();

        ButtonBar buttonBar = new ButtonBar(actionMap);
            MenuBar menuBar = new MenuBar(actionMap);
        buttonBar.setJMenuBar(menuBar);

        buttonBar.setLocation(0,25);
        buttonBar.setDefaultLookAndFeelDecorated(false);
        buttonBar.setUndecorated(true);
        buttonBar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Display the window.
        buttonBar.pack();
        buttonBar.setVisible(true);
    }

    public static void main(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name",
               "COinJ");
        Application app = Application.getApplication();
        app.setAboutHandler(new MacAboutHandler());

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
            UIManager.put("swing.boldMetal", Boolean.FALSE);
               createAndShowGUI();
            }
        });
    }
}
