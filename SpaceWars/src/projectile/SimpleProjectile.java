package projectile;

import java.awt.Color;
import java.awt.Graphics;
 
public class SimpleProjectile extends Projectile {

	public SimpleProjectile(float x, float y, double direction, int damage) {
		super(x, y, direction, damage);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics g, Color color)
        {
            g.setColor(color);
            g.fillOval((int)x, (int)y, 5, 5);
            g.drawOval((int)x, (int)y, 5, 5);
	}
 
    @Override
    public void move()
    {
        double dx = 20*Math.cos(direction);
        double dy = 20*Math.sin(direction);

        x += dx;
        y -= dy;
    }
 
}