/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pack;

import spaceship.UserSpaceship;

/**
 *
 * @author alpha
 */
public class HealthStrategy implements Strategy
{
    public void activate(UserSpaceship spaceship)
    {
        spaceship.setEnergy(spaceship.getEnergy()+20);
    }
}
