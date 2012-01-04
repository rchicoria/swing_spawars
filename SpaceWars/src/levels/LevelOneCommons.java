/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package levels;

import game.Commons;
import game.ContainerBox;
import java.awt.Color;

/**
 *
 * @author alpha
 */
public interface LevelOneCommons extends Commons{

    public static final int USER_SPACESHIP_INIT_X = 400;
    public static final int USER_SPACESHIP_INIT_Y = 300;
    public static final int USER_SPACESHIP_RADIUS = 15;
    public static final int USER_SPACESHIP_INIT_SPEED = 0;
    public static final int USER_SPACESHIP_DIRECTION = 90;
    public static final Color USER_SPACESHIP_COLOR = Color.RED;
    public static final int USER_SPACESHIP_ENERGY = 100;
    public static final int USER_SPACESHIP_AMMO = 200;
    public static final int USER_SPACESHIP_ROCKETS = 3;
    public static final int USER_SPACESHIP_AMMO_DMG = 30;
    public static final int USER_SPACESHIP_HYPERSPACE = 1;

    public static final int N_ROBOTS = 4;
    public static final int ROBOT_SPACESHIP_RADIUS = 20;
    public static final int ROBOT_SPACESHIP_INIT_SPEED = 2;
    public static final int ROBOT_SPACESHIP_DIRECTION = 90;
    public static final Color ROBOT_SPACESHIP_COLOR = Color.GREEN;
    public static final int ROBOT_SPACESHIP_ENERGY = 100;
    public static final double ROBOT_SPACESHIP_FIRE_RATE = 0.15;
    public static final int ROBOT_SPACESHIP_AMMO_DMG = 5;

    public static final int ROBOT_SPACESHIP_POS[][] = {{ ContainerBox.getInstance().get_minX()+70,ContainerBox.getInstance().get_maxY()-70},
                                                        {ContainerBox.getInstance().get_minX()+70, ContainerBox.getInstance().get_minY()+70},
                                                        {ContainerBox.getInstance().get_maxX()-70,ContainerBox.getInstance().get_maxY()-70},
                                                        {ContainerBox.getInstance().get_maxX()-70,ContainerBox.getInstance().get_minY()+70}};

    public static final int ENERGY_PACK_TIME = 3*UPDATE_RATE;
    public static final int ENERGY_PACK = 30;
    public static final double ENERGY_PACK_RATE = 0.0003;
    public static final int AMMO_PACK_TIME = 3*UPDATE_RATE;
    public static final int AMMO_PACK = 100;
    public static final double AMMO_PACK_RATE = 0.003;
    public static final int ROCKET_PACK_TIME = 3*UPDATE_RATE;
    public static final int ROCKET_PACK = 100;
    public static final double ROCKET_PACK_RATE = 0.003;
    public static final int INVENCIBLE_PACK_TIME = 2*UPDATE_RATE;
    public static final int INVENCIBLE_PACK = 100;
    public static final double INVENCIBLE_PACK_RATE = 0.00015;

}
