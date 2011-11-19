package spaceship;

import java.awt.Color;

public class UserSpaceship extends Spaceship {

	public UserSpaceship(double x, double y, double velocity, double direction,
			double angVelocity, double acceleration, double maxVelocity,
			double maxAngVelocity) {
		super(x, y, velocity, direction, angVelocity, acceleration, maxVelocity,
				maxAngVelocity);
		this.color = Color.RED;
		// TODO Auto-generated constructor stub
	}

}
