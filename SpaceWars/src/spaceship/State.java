package spaceship;

import java.awt.Color;


public interface State
{
    public void takeDamage(UserSpaceship spaceship, int damage);
    public Color getColor();
}
