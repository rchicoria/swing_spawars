package spaceship;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.Random;

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
