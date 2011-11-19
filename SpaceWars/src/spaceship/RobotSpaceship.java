package spaceship;

import java.awt.Color;
import java.util.Random;

public class RobotSpaceship extends Spaceship {
	
	private double currentProb;

	public RobotSpaceship(double x, double y, double velocity, double direction,
			double angVelocity, double acceleration, double maxVelocity,
			double maxAngVelocity) {
		super(x, y, velocity, direction, angVelocity, acceleration, maxVelocity,
				maxAngVelocity);
		this.color = Color.green;
		// TODO Auto-generated constructor stub
	}
	
	public void changeDirection(){
		Random random = new Random();
		setCurrentProb(random.nextDouble());
	}

	/*
	 * Move todos os pontos da nave espacial à velocidade da mesma
	 */
	
	public void move() {
		if(currentProb>=0.1 && currentProb<=0.2)
			rotate(angVelocity);
		else if(currentProb<0.1)
			rotate(angVelocity);
		double dx = velocity*Math.cos(direction+0.5 * Math.PI);
		double dy = velocity*Math.sin(direction+0.5 * Math.PI);
		for(int i = 0; i < spaceship.length; i++) {
			spaceship[i][0] += dx;
			spaceship[i][1] += dy;
		}
		x = (int)spaceship[7][0];
		y = (int)spaceship[7][1];
 
		if(x <= 0) {
			x = game.SpaceWars.windowWidth;
		} else if (x >= game.SpaceWars.windowWidth+20) {
			x = 0;
		}
		if(y <= 0) {
			y = game.SpaceWars.windowHeight;
		} else if(y >= game.SpaceWars.windowHeight) {
			y = 0;
		}
		set(x,y);
	}

	public double getCurrentProb() {
		return currentProb;
	}

	public void setCurrentProb(double currentProb) {
		this.currentProb = currentProb;
	}

}
