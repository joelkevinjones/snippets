package gui;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class DraggableFrame extends JFrame {

    private DraggableFrameMouseInputAdapter mouseAdapter;

    private static class DraggableFrameMouseInputAdapter
                         extends MouseInputAdapter {
        /** starting location of a drag */
        private int startX = -1;
        private int startY = -1;
        JFrame frame;

        DraggableFrameMouseInputAdapter(JFrame frame) {
            this.frame = frame;
        }

    public void mousePressed(MouseEvent ev) {
            startX = ev.getX();
            startY = ev.getY();
        }

        public void mouseDragged(MouseEvent ev) {
            Point p = frame.getLocation();
           frame.setLocation(p.x + ev.getX() - startX,
                              p.y + ev.getY() - startY);
        }
    };

    public DraggableFrame() {
        mouseAdapter = new DraggableFrameMouseInputAdapter(this);
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    public MouseInputAdapter getMouseAdapter() {
        return mouseAdapter;
    }

    /*******************************************************
     * stand alone demo; not part of normal use
     *******************************************************/

    private static void createAndShowGUI() {
        DraggableFrame frame = new DraggableFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension preferredSize = new Dimension(200, 100);
        frame.setPreferredSize(preferredSize);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String argv[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }
}
