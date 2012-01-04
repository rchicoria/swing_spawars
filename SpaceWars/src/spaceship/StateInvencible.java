package spaceship;

import java.awt.Color;

/**
 *
 * @author alpha
 */
public class StateInvencible implements State
{
    private int duration = 10;
    public void takeDamage(UserSpaceship spaceship, int damage)
    {
        System.out.println("I'm invencible ! You cannot touch me !");
        duration--;
        if(duration==0)
            spaceship.setState(new StateDefault());
    }
    
    public Color getColor(){
    	return Color.YELLOW;
    }
}
