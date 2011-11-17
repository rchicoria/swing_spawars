import java.awt.Graphics;
 
public class Bullet {
	private int x;
	private int y;
	private double direction;
 
	public Bullet(int x, int y, double direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
 
	public void draw(Graphics g) {
		g.drawOval(x, y, 5, 5);
	}
 
	public void move() {
		double dx = 5*Math.cos(direction+0.5 * Math.PI);
		double dy = 5*Math.sin(direction+0.5 * Math.PI);
 
		x += dx;
		y += dy;
	}
 
	// getters and setters
	public int getX() {
		return x;
	}
 
	public void setX(int x) {
		this.x = x;
	}
 
	public int getY() {
		return y;
	}
 
	public void setY(int y) {
		this.y = y;
	}
 
	public double getDirection() {
		return direction;
	}
 
	public void setDirection(double direction) {
		this.direction = direction;
	}
 
}