package game;

import java.awt.Color;
import java.awt.Graphics;

public class ContainerBox {

    private static ContainerBox instance = new ContainerBox();
    int minX, maxX, minY, maxY;  // Box's bounds (package access)
    private Color colorFilled;   // Box's filled color (background)
    private static final Color DEFAULT_COLOR_FILLED = Color.BLACK;

    /** Constructors */
    private ContainerBox(){}

    public static ContainerBox getInstance()
    {
        if(instance == null)
            instance = new ContainerBox();
        return instance;
    }

    /** Set or reset the boundaries of the box. */
    public void set(int x, int y, int width, int height) {
        minX = x;
        minY = y;
        maxX = x + width;
        maxY = y + height - 1;
    }

    /** Draw itself using the given graphic context. */
    public void draw(Graphics g) {
        g.setColor(DEFAULT_COLOR_FILLED);
        g.fillRect(minX, minY, maxX - minX - 1, maxY - minY - 1);
    }

    public int get_minX() {
        return this.minX;
    }

    public int get_maxX() {
        return this.maxX;
    }

    public int get_minY() {
        return this.minY;
    }

    public int get_maxY() {
        return this.maxY;
    }
}
