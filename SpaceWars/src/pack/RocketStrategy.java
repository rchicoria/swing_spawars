package pack;

import spaceship.UserSpaceship;

/**
 *
 * @author alpha
 */
public class RocketStrategy implements UserPackStrategy
{
    public void activate(UserSpaceship spaceship)
    {
        spaceship.setrockets(spaceship.getrockets()+3);
    }
}
