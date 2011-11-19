package projectile;

import java.awt.Color;
import java.awt.Graphics;
 
public class SimpleProjectile extends Projectile {

	public SimpleProjectile(double x, double y, double direction) {
		super(x, y, direction);
		// TODO Auto-generated constructor stub
	}

	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval((int)x, (int)y, 5, 5);
		g.drawOval((int)x, (int)y, 5, 5);
	}
 
	public void move() {
		double dx = 25*Math.cos(direction+0.5 * Math.PI);
		double dy = 25*Math.sin(direction+0.5 * Math.PI);
 
		x += dx;
		y += dy;
	}
 
}