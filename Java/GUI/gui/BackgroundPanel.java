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

import javax.swing.JPanel;

/**
 * A {@link javax.swing.JPanel} that has a named background taken from
 * a resource.
 */
public class BackgroundPanel extends JPanel {

    /**
     * Construct a {@code JPanel} with a background, named in the resources
     * for this class.
     */
    public BackgroundPanel(LayoutManager lom, String textureName) {
        super(lom);
        setUI(new BackgroundComponentUI(textureName));
    }

    /**
     * Construct a {@code JPanel} with a background, named in the resources
     * for this {@code BackgroundComponentUI}.
     */
    public BackgroundPanel(String textureName) {
        setUI(new BackgroundComponentUI(textureName));
    }
}

