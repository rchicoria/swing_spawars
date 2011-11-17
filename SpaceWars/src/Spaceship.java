public class Spaceship {
	private int x;
	private int y;
	private double velocity;
	private double direction;
	private double acceleration;
	private double angVelocity;
	
	double[][] ship = new double[4][2];
 
	public Spaceship(int x, int y, int velocity, int direction, int angVelocity, int acceleration) {
		this.x = x;
		this.y = y;
		this.velocity = velocity;
		this.direction = direction;
		this.angVelocity = angVelocity;
		this.acceleration = acceleration;
	}
 
	// getters and setters
	public int getX() {
		return x;
	}
 
	public int getY() {
		return y;
	}
 
	public double getVelocity() {
		return velocity;
	}
 
	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}
 
	public double getDirection() {
		return direction;
	}
 
	public void setDirection(int direction) {
		this.direction = direction;
	}

	public double getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}

	public double getAngVelocity() {
		return angVelocity;
	}

	public void setAngVelocity(double angVelocity) {
		this.angVelocity = angVelocity;
	}
}