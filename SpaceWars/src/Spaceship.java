import java.awt.*;

public class Spaceship {
	private int x;
	private int y;
	private double velocity;
	private double direction;
	private double acceleration;
	private double angVelocity;
	
	double[][] spaceship = new double[7][2];
 
	public Spaceship(int x, int y, int velocity, int direction, int angVelocity, int acceleration) {
		this.x = x;
		this.y = y;
		this.velocity = velocity;
		this.direction = direction;
		this.angVelocity = angVelocity;
		this.acceleration = acceleration;
	}
	
	// ====== public ======
	
	/*
	 * Desenha a nave espacial
	 */
	public void draw(Graphics graphics){
		Polygon shape  = new Polygon();
		Polygon shape2 = new Polygon();
		for(int i=0; i<4; i++){
			shape.addPoint((int)spaceship[i][0], (int)spaceship[i][1]);
		}
		for(int i=4; i<7; i++){
			shape2.addPoint((int)spaceship[i][0], (int)spaceship[i][1]);
		}
		graphics.setColor(Color.RED);
		graphics.fillPolygon(shape);
		graphics.setColor(Color.BLACK);
		graphics.fillPolygon(shape2);
		set(x, y);
	}
	
	/*
	 * Roda a nave segundo um dado ângulo
	 */
	public void rotate(double angle) {
		this.direction += angle;
		for(int i=0; i<7; i++){
			spaceship[i] = movePoint(spaceship[i], spaceship[3], angle);
		}
	}
	
	/*
	 * Move todos os pontos da nave espacial à velocidade da mesma
	 * FIXME adicionar velocidade ângular
	 */
	
	public void move() {
		double dx = velocity*Math.cos(direction+0.5 * Math.PI);
		double dy = velocity*Math.sin(direction+0.5 * Math.PI);
		for(int i = 0; i < spaceship.length; i++) {
			spaceship[i][0] += dx;
			spaceship[i][1] += dy;
		}
		x = (int)spaceship[3][0];
		y = (int)spaceship[3][1];
	}
	
	/*
	 * Define a forma e posição da nave (os seus pontos)
	 */
	public void set(int x, int y) {
		spaceship[0][0] = (double)x-20;
		spaceship[0][1] = (double)y-20;
		spaceship[1][0] = (double)x;
		spaceship[1][1] = (double)y+20;
		spaceship[2][0] = (double)x+20;
		spaceship[2][1] = (double)y-20;
		spaceship[3][0] = (double)x;	
		spaceship[3][1] = (double)y-15;
		spaceship[4][0] = (double)x-12;	
		spaceship[4][1] = (double)y-10;
		spaceship[5][0] = (double)x+12;	
		spaceship[5][1] = (double)y-10;
		spaceship[6][0] = (double)x;	
		spaceship[6][1] = (double)y+15;
	 
		for(int i = 0; i < 7; i++) {
			spaceship[i] = movePoint(spaceship[i], spaceship[3], direction);
		}
	}
	
	// ====== private ======
	
	/*
	 * Move um ponto segundo a origem a partir de um dado ângulo
	 */
	private double[] movePoint(double[] point, double[] origin, double angle){
		double X = (double) (origin[0] + ((point[0] - origin[0]) * Math.cos(angle) -
			(point[1] - origin[1]) * Math.sin(angle)));
		double Y = (double) (origin[1] + ((point[0] - origin[0]) * Math.sin(angle) +
			(point[1] - origin[1]) * Math.cos(angle)));
		double[] res = {X, Y};
		return res;
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