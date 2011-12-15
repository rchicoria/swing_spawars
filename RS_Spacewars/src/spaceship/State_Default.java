/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spaceship;

/**
 *
 * @author alpha
 */
public class State_Default implements State
{
    public void takeDamage(UserSpaceship spaceship, int damage)
    {
        spaceship.setEnergy(spaceship.getEnergy()-damage);
    }
}
