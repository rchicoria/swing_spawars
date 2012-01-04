package views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import spaceship.Spaceship;

public class SpaceshipView
{
    Spaceship spaceship;

    public SpaceshipView(Spaceship s)
    {
        this.spaceship = s;
    }

    public void draw(Graphics g)
    {
        drawOldPosition(g);
        drawShape(g);
        drawLifeBar(g);
        // Radius
        //g.drawOval((int)(x - radius), (int)(y - radius), (int)(2 * radius),(int)(2 * radius));
        //g.drawOval((int)x-1, (int)y-1, 2, 2);
    }

    private void drawShape(Graphics graphics)
    {
        graphics.setColor(spaceship.getColor());
        Polygon shape1  = new Polygon();
        Polygon shape2 = new Polygon();
        float[][] shape = spaceship.getShape();
        for(int i=0; i<4; i++){ shape1.addPoint((int)shape[i][0], (int)shape[i][1]);}
        for(int i=4; i<7; i++){ shape2.addPoint((int)shape[i][0], (int)shape[i][1]);}
        graphics.fillPolygon(shape1);
        graphics.setColor(Color.BLACK);
        graphics.fillPolygon(shape2);
    }
    
    public void drawSimpleShape(Graphics graphics)
    {
        graphics.setColor(spaceship.getColor());
        Polygon shape2 = new Polygon();
        float[][] shape = spaceship.getShape();
        for(int i=4; i<7; i++) shape2.addPoint((int)(shape[i][0]/5), (int)(shape[i][1]/5 + 480 ));
        graphics.fillPolygon(shape2);
    }
    
    private void drawOldPosition(Graphics graphics)
    {
        graphics.setColor(spaceship.getColor());
        ArrayList<double[]> oldpos = spaceship.getOldPos();
        for(int i=0; i<oldpos.size()-1; i++)
        {
            int x = (int)oldpos.get(i)[0];
            int y = (int)oldpos.get(i)[1];
            graphics.drawOval(x, y, 5, 5);
            graphics.fillOval(x, y, 5, 5);
        }
    }

    private void drawLifeBar(Graphics graphics)
    {
        LifeBarView lifebarView = new LifeBarView((int)spaceship.getX(),
                                                    (int)spaceship.getY(),
                                                    spaceship.getEnergy(),
                                                    spaceship.getMaxEnergy());
        lifebarView.draw(graphics);
    }
}
