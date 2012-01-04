package game;

import java.awt.Color;
import java.awt.event.KeyEvent;

/**
 *
 * @author alpha
 */
public interface Commons {

    public static final int BOARD_WIDTH = 800;
    public static final int BOARD_HEIGTH = 490;
    public static final int UPDATE_RATE = 30;
    public static final float EPSILON_TIME = 1e-2f;

    public static final int KEY_UP = KeyEvent.VK_UP;
    public static final int KEY_DOWN = KeyEvent.VK_DOWN;
    public static final int KEY_LEFT = KeyEvent.VK_LEFT;
    public static final int KEY_RIGHT = KeyEvent.VK_RIGHT;
    public static final int KEY_AMMO = KeyEvent.VK_SPACE;
    public static final int KEY_ROCKET = KeyEvent.VK_C;
    public static final int KEY_HYPERSPACE = KeyEvent.VK_SHIFT;
    
    public static final double PI = 3.14159;
}