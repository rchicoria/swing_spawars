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
public class AmmoStrategy implements UserPackStrategy
{
    public void activate(UserSpaceship spaceship)
    {
        spaceship.setAmmo(spaceship.getAmmo()+100);
    }

}
