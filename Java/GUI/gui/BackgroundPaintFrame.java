package gui;

import gui.BackgroundPanel;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.TexturePaint;

import java.io.IOException;

import java.net.URL;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/// An editing frame for a Memo
public class BackgroundPaintFrame extends JFrame {

    // public Graphics JFrame.getGraphics()
    // TexturePaint(BufferedImage, Rectangle2D)
    // Graphics2D.setPaint(java.awt.Paint)
    // Paint-<|-java.awt.TexturePaint
    public BackgroundPaintFrame() {
        JPanel contentPane =
            new BackgroundPanel(new BorderLayout(), "SunstoneFullBack");
        contentPane.setOpaque(true);
        JButton button = new JButton("Click me! I'm good");
        contentPane.add(button, BorderLayout.WEST);
        button = new JButton("small");
        contentPane.add(button, BorderLayout.EAST);
        setContentPane(contentPane);
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        BackgroundPaintFrame frame = new BackgroundPaintFrame();

        //Display the window.
        frame.pack();
        frame.setSize(300, 200);
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
