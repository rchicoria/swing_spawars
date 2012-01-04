/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spaceship;

import java.awt.Color;

/**
 *
 * @author alpha
 */
public class StateDefault implements State
{
    public void takeDamage(UserSpaceship spaceship, int damage)
    {
        System.out.println("Took damage. Energy: " + spaceship.getEnergy());
        if(spaceship.getEnergy()-damage<0)
            spaceship.setEnergy(0);
        else
            spaceship.setEnergy(spaceship.getEnergy() - damage);
    }
    
    public Color getColor(){
    	return Color.RED;
    }
}
