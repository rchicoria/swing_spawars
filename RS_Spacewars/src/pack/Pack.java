/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pack;

import java.awt.Color;
import spaceship.UserSpaceship;

/**
 *
 * @author alpha
 */
public class Pack{

    private Strategy strategy;
    protected double x;
    protected double y;
    protected Color color;
    protected int timeRemaining;

    Pack(Color color, int time)
    {
        this.color = color;
        this.timeRemaining = time*1000;
    }

    public void setStrategy(Strategy s)
    {
        this.strategy = s;
    }

    public void show()
    {
        //this.x = ..
        // this.y = ..
    }

    public void activate(UserSpaceship spaceship)
    {
        this.strategy.activate(spaceship);
    }

}
