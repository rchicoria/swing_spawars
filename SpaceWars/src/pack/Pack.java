package pack;

import game.ContainerBox;
import java.awt.Color;
import java.awt.Point;
import java.util.Random;
import spaceship.UserSpaceship;

public class Pack
{
        protected Point coords;
        protected int radius;
	protected Color color;
	protected int timeRemaining;
        protected UserPackStrategy strategy;
	
	
	public Pack(Color color, int time)
	{
		Random r = new Random();
		int x = r.nextInt(ContainerBox.getInstance().get_maxX()-100)+50;
		int y = r.nextInt(ContainerBox.getInstance().get_maxY()-100)+50;
                this.coords = new Point(x,y);
                this.color = color;
		this.timeRemaining = time;
                this.radius = 30;
	}

        public void setStrategy(UserPackStrategy s)
        {
            this.strategy = s;
        }

        public void activate(UserSpaceship spaceship)
        {
            this.strategy.activate(spaceship);
        }

        public boolean intersect(int x, int y)
        {
            Point object = new Point(x, y);
            if(coords.distance(object)<=30)
                return true;
            return false;
        }
	
	public int getTimeRemaining()
        {
            return timeRemaining;
	}

	public void setTimeRemaining(int timeRemaining)
        {
            this.timeRemaining = timeRemaining;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

        public Point getCoords() {
            return coords;
        }

        public int getRadius() {
            return radius;
        }



}
