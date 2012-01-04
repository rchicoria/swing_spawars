/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package levels;

import game.Spaceships;
import game.SpaceshipsMemory;
import iterator.ProjectileCollection;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import pack.Pack;
import pack.PacksFactory;
import projectile.Projectile;
import spaceship.RobotSpaceship;
import spaceship.UserSpaceship;

/**
 *
 * @author alpha
 */
public class LevelOne extends Level implements LevelOneCommons
{

    public LevelOne(BufferStrategy bf)
    {
        super(bf);
        GAME_EPSILON_TIME = EPSILON_TIME;

        GAME_KEY_LEFT = KEY_LEFT;
        GAME_KEY_RIGHT = KEY_RIGHT;
        GAME_KEY_DOWN = KEY_DOWN;
        GAME_KEY_UP = KEY_UP;
        GAME_KEY_AMMO = KEY_AMMO;
        GAME_KEY_ROCKET = KEY_ROCKET;
        GAME_KEY_HYPERSPACE = KEY_HYPERSPACE;

        GAME_ROBOT_SPACESHIP_FIRE_RATE = ROBOT_SPACESHIP_FIRE_RATE;

        GAME_INVENCIBLE_PACK_RATE = INVENCIBLE_PACK_RATE;
        GAME_ENERGY_PACK_RATE = ENERGY_PACK_RATE;
        GAME_AMMO_PACK_RATE = AMMO_PACK_RATE;
        GAME_INVENCIBLE_PACK_TIME = INVENCIBLE_PACK_TIME;
        GAME_ENERGY_PACK_TIME = ENERGY_PACK_TIME;
        GAME_AMMO_PACK_TIME = AMMO_PACK_TIME;

        spaceships = new Spaceships();
        memory = new SpaceshipsMemory();
        packsFactory = new PacksFactory();
        packs = new ArrayList<Pack>();
        user_projectiles = new ProjectileCollection();
        enemy_projectiles = new ArrayList<Projectile>();

        spaceships.setPlayer(new UserSpaceship(USER_SPACESHIP_INIT_X,
                                            USER_SPACESHIP_INIT_Y,
                                            USER_SPACESHIP_RADIUS,
                                            USER_SPACESHIP_INIT_SPEED,
                                            USER_SPACESHIP_DIRECTION,
                                            USER_SPACESHIP_COLOR,
                                            USER_SPACESHIP_ENERGY,
                                            USER_SPACESHIP_AMMO,
                                            USER_SPACESHIP_AMMO_DMG,
                                            USER_SPACESHIP_ROCKETS,
                                            USER_SPACESHIP_HYPERSPACE ));

        for(int i=0; i<N_ROBOTS; i++)
                spaceships.addEnemy(new RobotSpaceship(ROBOT_SPACESHIP_POS[i][0],
                                                        ROBOT_SPACESHIP_POS[i][1],
                                                        ROBOT_SPACESHIP_RADIUS,
                                                        ROBOT_SPACESHIP_INIT_SPEED,
                                                        ROBOT_SPACESHIP_DIRECTION,
                                                        ROBOT_SPACESHIP_COLOR,
                                                        ROBOT_SPACESHIP_ENERGY,
                                                        ROBOT_SPACESHIP_AMMO_DMG));
    }


}
