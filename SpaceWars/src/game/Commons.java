package game;

import java.awt.Color;
import java.awt.event.KeyEvent;

/**
 *
 * @author alpha
 */
public interface Commons {

    public static final int BOARD_WIDTH = 800;
    public static final int BOARD_HEIGTH = 600;
    public static final int UPDATE_RATE = 30;
    public static final float EPSILON_TIME = 1e-2f;
    
    public static final int N_ROBOTS = 4;
    public static final int USER_SPACESHIP_INIT_X = 400;
    public static final int USER_SPACESHIP_INIT_Y = 300;
    public static final int USER_SPACESHIP_RADIUS = 15;
    public static final int USER_SPACESHIP_INIT_SPEED = 0;
    public static final int USER_SPACESHIP_DIRECTION = 90;
    public static final Color USER_SPACESHIP_COLOR = Color.RED;
    public static final int USER_SPACESHIP_ENERGY = 100;
    public static final int USER_SPACESHIP_AMMO = 200;
    public static final int USER_SPACESHIP_ROCKETS = 3;
    public static final int USER_SPACESHIP_SIMPLE_PROJECTILES_DAMAGE = 30;
    public static final int USER_SPACESHIP_ROCKETS_DAMAGE = 100;

    public static final int ROBOT_SPACESHIP_RADIUS = 20;
    public static final int ROBOT_SPACESHIP_INIT_SPEED = 2;
    public static final int ROBOT_SPACESHIP_DIRECTION = 90;
    public static final Color ROBOT_SPACESHIP_COLOR = Color.GREEN;
    public static final int ROBOT_SPACESHIP_ENERGY = 100;
    public static final double ROBOT_SPACESHIP_FIRE_RATE = 0.15;
    public static final int ROBOT_SPACESHIP_SIMPLE_PROJECTILES_DAMAGE = 5;

    public static final int ENERGY_PACK = 30;
    public static final double ENERGY_PACK_RATE = 0.0003;
    public static final int AMMO_PACK = 100;
    public static final double AMMO_PACK_RATE = 0.003;

    public static final int KEY_UP = KeyEvent.VK_UP;
    public static final int KEY_DOWN = KeyEvent.VK_DOWN;
    public static final int KEY_LEFT = KeyEvent.VK_LEFT;
    public static final int KEY_RIGHT = KeyEvent.VK_RIGHT;
    public static final int KEY_AMMO = KeyEvent.VK_SPACE;
    public static final int KEY_ROCKET = KeyEvent.VK_C;
    
    public static final double PI = 3.14159;
}