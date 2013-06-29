/**
 * Example code of how to implement Macintosh look and feel.
 * @author Joel Jones joelkevinjones@gmail.com
 */
import com.apple.eawt.AboutHandler;
import com.apple.eawt.AppEvent;
import com.apple.eawt.AppForegroundListener;
import com.apple.eawt.Application;
import com.apple.eawt.PreferencesHandler;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

class AppleLookAndFeelDemo {
    /** 
     * Handle the "about" box.  As all good Mac programmers know, you write
     * the about box first.  Also, a modal top-level matches the Apple Human
     * Interface guidelines, so a JFrame isn't what is needed.  Instead use 
     * {@code JDialog}, {@code JOptionPane}, or something similar.
     */
    public static class MacAboutHandler implements AboutHandler {
        public void handleAbout(AppEvent.AboutEvent e) {
            System.out.println("About Apple Look and Feel Demo");
            JOptionPane.showMessageDialog(null, "Apple Look and Feel Demo", 
                                          null, JOptionPane.PLAIN_MESSAGE);
        }
    }

    /** 
     * Handle the preferences dialog.  A modal top-level matches the Apple 
     * Human Interface guidelines, so a JFrame isn't what is needed.  
     * Instead use {@code JDialog}, {@code JOptionPane}, or something similar.
     * The contents of a real preferences dialog would be more complicated, of 
     * course.
     */
    public static class MacPreferencesHandler implements PreferencesHandler {
        public void handlePreferences(AppEvent.PreferencesEvent e) {
            System.out.println("Preferences for Apple Look and Feel Demo");
            JOptionPane.showMessageDialog(null, "Preferences", 
                                          null, JOptionPane.PLAIN_MESSAGE);
        }
    }

    /** 
     * Demonstrate the hiding/unhiding of a window on the application being
     *  placed in the forefound and removed from the foreground.
     */
    public static class MacForegroundListener implements AppForegroundListener {
        JFrame frame;
        MacForegroundListener(JFrame frame) {
            this.frame = frame;
        }
        public void appRaisedToForeground(AppEvent.AppForegroundEvent e) {
            frame.setVisible(true);
        }
        public void appMovedToBackground(AppEvent.AppForegroundEvent e) {
            frame.setVisible(false);
        }
    }
    static private void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Apple Look and Feel Demo");

        frame.setLocation(10,25);
        // TODO: This isn't the right behavior, applications should set up
        // a quit handler using Application.setQuitHandler
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Give it some content so the title isn't chopped off
        JLabel label = new JLabel("<html><font size=+1>" +
                                  "Demonstration of how to make Java look<br>"+
                                  "like a Mac Application</font></html>");
        frame.add(label);

        //Display the window.
        frame.pack();
        frame.setVisible(true);

        JPanel panel = new JPanel();

        // Create some buttons and use HTML to style them.
        JButton button1 = new JButton("<html><font size=+2>One</font></html>");
        panel.add(button1);
        JButton button2 = new JButton("<html><font size=+2>Two</font></html>");
        panel.add(button2);

        panel.setOpaque(false);

        //Lay out the main panel.
        JFrame toolBarFrame = new JFrame();
        toolBarFrame.add(panel);

        Application app = Application.getApplication();
        app.addAppEventListener(new MacForegroundListener(toolBarFrame));
        toolBarFrame.setLocation(10,125);
        toolBarFrame.pack();
        toolBarFrame.setVisible(true);
    }

    // Create a gui and add the appropriate handlers for about boxes, 
    // preferences, etc.
    public static void main(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", 
                           "Apple Look and Feel Demo");
        Application app = Application.getApplication();
        app.setAboutHandler(new MacAboutHandler());
        app.setPreferencesHandler(new MacPreferencesHandler());
        System.out.println("Hello, world");
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
               createAndShowGUI();
            }
          });
    }
}
