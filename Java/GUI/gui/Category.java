package gui;

import java.awt.Color;

public class Category {
    private String name;
    private String id;
    private int colorIndex;

    public Category(String name, String id, int colorIndex) {
        this.name = name;
        this.id = id;
        this.colorIndex = colorIndex;
    }
    public String getName() { return name; }
    public String getID() { return id; }
    public int getColorIndex() { return colorIndex; }

    // FIXME: refactor out color managment into another class
    static Color indexToColor[] = {
        new Color(0,0,0), // not a valid index
        new Color(0,0,0), // [1] (Gray) Text on White
        new Color(52428/256, 0, 0), // [2] (Red) Text on White
        //new Color(13107/256, 52428/256, 0/256) // [3] Text on Dark
        new Color(0/256, 26214/256, 0/256), // [3] Text on White
        new Color(0, 0, 52428/256), // [4] (Blue) Text on White
        new Color(65535/256, 39321/256, 52428/256), // [5] (Pink) Text on White
        new Color(52428/256, 39321/256, 65535/256), // [6] (Purple) Text on White
        new Color(65535/256, 26214/256, 0), // [7] (Orange) Text on White
        new Color(52428/256, 0, 39321/256), // [8] (Magenta) Text on White
        new Color(0, 39321/256, 39321/256), // [9] (Teal) Text on White
        new Color(26214/256, 13107/256, 0) // [10] (Brown) Text on White
    };

    public static Color getColorForIndex(int idx) {
        if (0 >= idx || idx >= indexToColor.length) return indexToColor[1];
        return indexToColor[idx];
    }
}

