import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Ship {
	private int x;
	private int y;
	private double speed;
	private double direction;
	double[][] ship = new double[4][2];
	private ArrayList<Bullet> bullets;
 
	public Ship(int x, int y, int speed, int direction) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.direction = direction;
 
		bullets = new ArrayList<Bullet>();
 
		// generate coordinates for ship figure
		set(x, y);
	}
 
	public void fire() {
		try {
			bullets.add(new Bullet(x, y, direction));
		} catch ( ConcurrentModificationException e) { }
	}
 
	private void drawBullets(Graphics g) {
		try {
			for(int n = 0; n < bullets.size(); n++) {
				Bullet b = bullets.get(n);
				b.move();
				b.draw(g);
				// check if this bullet is out of the screen, if so remove it from the list
				if(b.getX()<0 || b.getX() > Asteroids.windowWidth || b.getY()<0 || b.getY() > Asteroids.windowHeight) {
					bullets.remove(b);
				}
 
			}
		} catch ( ConcurrentModificationException e) { }
	}
 
	public void draw(Graphics g) {
		Polygon shipPoly = new Polygon();
 
		for(int n = 0; n < 4; n++) {
			shipPoly.addPoint((int)ship[n][0], (int)ship[n][1]);
		}
		g.setColor(Color.GREEN);
		g.fillPolygon(shipPoly);
 
		drawBullets(g);
	}
 
	public void move() {
		double dx = speed*Math.cos(direction+0.5 * Math.PI);
		double dy = speed*Math.sin(direction+0.5 * Math.PI);
		for(int n = 0; n < ship.length; n++) {
			ship[n][0] += dx;
			ship[n][1] += dy;
		}
		x = (int)ship[3][0];
		y = (int)ship[3][1];
	}
 
	public void set(int x, int y) {
		ship[0][0] = (double)x-20;
		ship[0][1] = (double)y-20;
		ship[1][0] = (double)x;
		ship[1][1] = (double)y+20;
		ship[2][0] = (double)x+20;
		ship[2][1] = (double)y-20;
		ship[3][0] = (double)x;
		ship[3][1] = (double)y-10;
 
		for(int n = 0; n < 3; n++) {
			ship[n] = rotatePoint(ship[n], ship[3], direction);
		}
	}
 
	public void rotate(double direction) {
		this.direction += direction;
		for(int n = 0; n < 3; n++) {
			ship[n] = rotatePoint(ship[n], ship[3], direction);
		}
	}
 
	private double[] rotatePoint(double[] point, double[] origin, double angle) {
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
 
	public double getSpeed() {
		return speed;
	}
 
	public void setSpeed(double speed) {
		this.speed = speed;
	}
 
	public double getDirection() {
		return direction;
	}
 
	public void setDirection(int direction) {
		this.direction = direction;
	}
 
	public ArrayList<Bullet> getBullets() {
		return bullets;
	}
}