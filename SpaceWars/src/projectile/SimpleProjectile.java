package projectile;

import game.ContainerBox;
import java.awt.Graphics;
 
public class SimpleProjectile extends Projectile{

    
    public SimpleProjectile(float x, float y, double direction, int damage)
    {
        super(x, y, direction);
        this.damage = damage;
    }

    @Override
    public void draw(Graphics g)
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

    public boolean isOutOfBorders()
    {
        ContainerBox box = ContainerBox.getInstance();
        return x>box.get_maxX() || x<box.get_minX() || y>box.get_maxY() || y<box.get_minY();
    }
 
}