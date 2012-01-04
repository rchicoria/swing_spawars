/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pack;

import spaceship.StateInvencible;
import spaceship.UserSpaceship;

public class InvencibleStrategy implements UserPackStrategy
{
    public void activate(UserSpaceship spaceship)
    {
        spaceship.setState(new StateInvencible());
    }
}
