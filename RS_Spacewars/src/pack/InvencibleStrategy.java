/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pack;

import spaceship.State_Invencible;
import spaceship.UserSpaceship;

/**
 *
 * @author alpha
 */
public class InvencibleStrategy implements Strategy
{
    public void activate(UserSpaceship spaceship)
    {
        spaceship.setState(new State_Invencible());
    }
}
