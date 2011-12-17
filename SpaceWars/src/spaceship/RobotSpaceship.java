package spaceship;

import game.Commons;
import game.ContainerBox;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ConcurrentModificationException;
import java.util.Random;

import projectile.SimpleProjectile;

public class RobotSpaceship extends Spaceship {

	private double currentProb;
	private boolean fireActive = true;

	public RobotSpaceship(float x, float y, float radius, float speed, float angleInDegree, Color color,
            int energy) {
		super(x, y,  radius, speed,angleInDegree, color,energy);
		this.color = Color.green;
		this.simpleProjectileColor = Color.YELLOW;
		this.simpleProjectilesDamage = Commons.ROBOT_SPACESHIP_SIMPLE_PROJECTILES_DAMAGE;
		// TODO Auto-generated constructor stub
	}
	
	public void changeDirection(){
		Random random = new Random();
		setCurrentProb(random.nextDouble());
	}
        
	/*
	 * Move todos os pontos da nave espacial à velocidade da mesma
	 */
	
	public void move() {
		/*if(currentProb>=0.1 && currentProb<=0.2)
			rotate(angVelocity);
		else if(currentProb<0.1)
			rotate(-angVelocity);
		double dx = velocity*Math.cos(direction+0.5 * Math.PI);
		double dy = velocity*Math.sin(direction+0.5 * Math.PI);
		for(int i = 0; i < spaceship.length; i++) {
			spaceship[i][0] += dx;
			spaceship[i][1] += dy;
		}
		x = (int)spaceship[7][0];
		y = (int)spaceship[7][1];
 
		if(x <= 0) {
			x = game.SpaceWars.windowWidth;
		} else if (x >= game.SpaceWars.windowWidth+20) {
			x = 0;
		}
		if(y <= 0) {
			y = game.SpaceWars.windowHeight;
		} else if(y >= game.SpaceWars.windowHeight) {
			y = 0;
		}
		set(x,y);*/
	}

        public void rotate()
        {
            Random r = new Random();
            this.angleDegrees += 2*this.angle_inc*r.nextInt(3)- 1;
            if(this.angleDegrees<0)
                this.angleDegrees = 360+this.angleDegrees;
            this.angleDegrees = this.angleDegrees % 360;
            this.speedX = (float)(speed * Math.cos(Math.toRadians(this.angleDegrees)));
            this.speedY = (float)(-speed * Math.sin(Math.toRadians(this.angleDegrees)));
        }
	
	protected void drawProjectiles(Graphics graphics) {
		try {
			for(int n = 0; n < projectiles.size(); n++) {
				SimpleProjectile p = (SimpleProjectile)projectiles.get(n);
				p.move();
				p.draw(graphics, simpleProjectileColor);
				// check if this bullet is out of the screen, if so remove it from the list
				if(p.get_x()<ContainerBox.getInstance().get_minX()
                                        || p.get_x() > ContainerBox.getInstance().get_maxX()
                                        || p.get_y() < ContainerBox.getInstance().get_minY()
                                        || p.get_y() > ContainerBox.getInstance().get_maxY()) {
					projectiles.remove(p);
				}
			}
		} catch ( ConcurrentModificationException e) { }
	}

	public double getCurrentProb() {
		return currentProb;
	}

	public void setCurrentProb(double currentProb) {
		this.currentProb = currentProb;
	}

	public boolean isFireActive() {
		return fireActive;
	}

	public void setFireActive(boolean fireActive) {
		this.fireActive = fireActive;
	}

}
