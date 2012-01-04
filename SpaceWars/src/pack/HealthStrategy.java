package pack;

import spaceship.UserSpaceship;

public class HealthStrategy implements UserPackStrategy
{
    public void activate(UserSpaceship spaceship)
    {
        spaceship.setEnergy(spaceship.getEnergy()+20);
    }
}