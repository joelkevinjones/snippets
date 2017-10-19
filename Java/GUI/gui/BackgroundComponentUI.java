package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.TexturePaint;

import java.io.IOException;

import java.net.URL;

import javax.imageio.ImageIO;

import javax.swing.JComponent;

import javax.swing.plaf.ComponentUI;

/**
 * A {@link javax.swing.JPanel} that has a named background taken from
 * a resource.
 */
public class BackgroundComponentUI extends ComponentUI {
    private TexturePaint texture = null;
    private String textureName = null;

    /**
     * Construct a {@code JPanel} with a background, named in the resources
     * for this class.
     */
    public BackgroundComponentUI(String textureName) {
        this.textureName = textureName;
    }

    private static BufferedImage makeBufImage(String imageName) {
        //Look for the image.
        // TODO: Fix to use system specific file seperator, not "/"?
        String imgLocation = "images/" + imageName + ".gif";
        URL imageURL = BackgroundPanel.class.getResource(imgLocation);
        BufferedImage bufImage = null;
        try {
            bufImage = ImageIO.read(imageURL);
        }
        catch(IOException ioe) {
            System.err.println("couldn't laod image");
            return null;
        }
        return bufImage;
    }

    private void setTexture(JComponent c) {
        Graphics g = c.getGraphics();
        if (!(g instanceof Graphics2D)) {
            // TODO throw exception instead
            System.out.println("Graphics not Graphics2D! " +
                               g.getClass().getName());
            System.exit(1);
        }
        Graphics2D g2 = (Graphics2D) g;

        BufferedImage bufImage = makeBufImage(textureName);
        int h = bufImage.getHeight();
        int w = bufImage.getWidth();
        texture =
            new TexturePaint(bufImage, new Rectangle(new Dimension(h,w)));
        g2.setPaint(texture);
    }

    /** {@inheritDoc} */
    public void paint(Graphics g, JComponent c) {
        if (texture == null) setTexture(c);
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(texture);
        g2.fill(g2.getClip());
    }
}

