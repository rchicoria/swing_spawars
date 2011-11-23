package spaceship;

import game.SpaceWars;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ConcurrentModificationException;
import java.util.Random;

import projectile.SimpleProjectile;

public class RobotSpaceship extends Spaceship {
	
	private double currentProb;
	private boolean fireActive = false;

	public RobotSpaceship(double x, double y, double velocity, double direction,
			double angVelocity, double acceleration, double maxVelocity,
			double maxAngVelocity, int energy) {
		super(x, y, velocity, direction, angVelocity, acceleration, maxVelocity,
				maxAngVelocity, energy);
		this.color = Color.green;
		this.simpleProjectileColor = Color.YELLOW;
		// TODO Auto-generated constructor stub
	}
	
	public void changeDirection(){
		Random random = new Random();
		setCurrentProb(random.nextDouble());
	}
	
	public void drawBar(Graphics graphics){
		Polygon bar = new Polygon();
		bar.addPoint((int)(x-20), (int)(y+30));
		bar.addPoint((int)(x-20+(float)energy/(float)maxEnergy*40), (int)(y+30));
		bar.addPoint((int)(x-20), (int)(y+32));
		bar.addPoint((int)(x-20+(float)energy/(float)maxEnergy*40), (int)(y+32));
		if((float)energy/(float)maxEnergy>0.50)
			graphics.setColor(Color.green);
		else if((float)energy/(float)maxEnergy>0.25)
			graphics.setColor(Color.yellow);
		else
			graphics.setColor(Color.RED);
		graphics.fillPolygon(bar);
	}

	/*
	 * Move todos os pontos da nave espacial à velocidade da mesma
	 */
	
	public void move() {
		if(currentProb>=0.1 && currentProb<=0.2)
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
		set(x,y);
	}
	
	protected void drawProjectiles(Graphics graphics) {
		try {
			for(int n = 0; n < projectiles.size(); n++) {
				SimpleProjectile p = (SimpleProjectile)projectiles.get(n);
				p.move();
				p.draw(graphics, simpleProjectileColor);
				// check if this bullet is out of the screen, if so remove it from the list
				if(p.getX()<0 || p.getX() > SpaceWars.windowWidth || p.getY()<0 || p.getY() > SpaceWars.windowHeight) {
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
