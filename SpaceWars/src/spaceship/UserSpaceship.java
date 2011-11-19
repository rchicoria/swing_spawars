package spaceship;

import java.awt.Color;

public class UserSpaceship extends Spaceship {
	public UserSpaceship(double x, double y, double velocity, double direction,
			double angVelocity, double acceleration, double maxVelocity,
			double maxAngVelocity, int energy) {
		super(x, y, velocity, direction, angVelocity, acceleration, maxVelocity,
				maxAngVelocity, energy);
		this.color = Color.RED;
		this.simpleProjectileColor = Color.WHITE;
		// TODO Auto-generated constructor stub
	}

}
