package spaceship;

/**
 *
 * @author alpha
 */
public class State_Invencible implements State
{
    public void takeDamage(UserSpaceship spaceship, int damage)
    {
        spaceship.setState(new State_Default());
    }
}
