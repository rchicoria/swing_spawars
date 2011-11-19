package spaceship;

import game.SpaceWars;

import java.awt.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import projectile.Projectile;
import projectile.SimpleProjectile;

public class Spaceship {
	protected double x;
	protected double y;
	protected double velocity;
	protected double direction;
	private double acceleration;
	protected double angVelocity;
	private double maxVelocity;
	private double maxAngVelocity;
	protected Color color;
	protected ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	
	double[][] spaceship = new double[8][2];
	
	ArrayList<double[]> oldPos = new ArrayList<double[]>();
 
	public Spaceship(double x, double y, double velocity, double direction, double angVelocity, double acceleration, double maxVelocity, double maxAngVelocity) {
		this.x = x;
		this.y = y;
		this.velocity = velocity;
		this.direction = direction;
		this.angVelocity = angVelocity;
		this.acceleration = acceleration;
		this.setMaxVelocity(maxVelocity);
		this.setMaxAngVelocity(maxAngVelocity);
		set(x, y);
	}
	
	// ====== public ======

	/*
	 * Desenha a nave espacial
	 */
	public void draw(Graphics graphics){
		Polygon shape  = new Polygon();
		Polygon shape2 = new Polygon();
		for(int i=0; i<4; i++){
			shape.addPoint((int)spaceship[i][0], (int)spaceship[i][1]);
		}
		for(int i=4; i<7; i++){
			shape2.addPoint((int)spaceship[i][0], (int)spaceship[i][1]);
		}
		graphics.setColor(this.color);
		graphics.fillPolygon(shape);
		graphics.setColor(Color.BLACK);
		graphics.fillPolygon(shape2);
		
		drawSimpleProjectiles(graphics);
	}
	
	/*
	 * Roda a nave segundo um dado ângulo
	 */
	public void rotate(double angle) {
		this.direction += angle;
		for(int i=0; i<7; i++){
			spaceship[i] = movePoint(spaceship[i], spaceship[3], angle);
		}
	}
	
	/*
	 * Move todos os pontos da nave espacial à velocidade da mesma
	 */
	public void move() {
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
	
	/*
	 * Define a forma e posição da nave (os seus pontos)
	 */
	public void set(double x, double y) {
		spaceship[0][0] = (double)x-20;
		spaceship[0][1] = (double)y-20;
		spaceship[1][0] = (double)x;
		spaceship[1][1] = (double)y+20;
		spaceship[2][0] = (double)x+20;
		spaceship[2][1] = (double)y-20;
		spaceship[3][0] = (double)x;	
		spaceship[3][1] = (double)y-15;
		spaceship[4][0] = (double)x-12;	
		spaceship[4][1] = (double)y-10;
		spaceship[5][0] = (double)x+12;	
		spaceship[5][1] = (double)y-10;
		spaceship[6][0] = (double)x;	
		spaceship[6][1] = (double)y+15;
		spaceship[7][0] = x;
		spaceship[7][1] = y;
	 
		for(int i = 0; i < 7; i++) {
			spaceship[i] = movePoint(spaceship[i], spaceship[3], direction);
		}
	}
	
	public void incrementVelocity(){
		if(this.velocity+this.acceleration<=this.maxVelocity)
			this.velocity += this.acceleration;
		else
			this.velocity = this.maxVelocity;
	}
	
	public void incrementAngVelocity(){
		if(this.angVelocity+this.acceleration/50<=this.maxAngVelocity)
			this.angVelocity += this.acceleration/50;
		else
			this.angVelocity = this.maxAngVelocity;
	}
	
	public void decrementVelocity(){
		if(this.velocity-this.acceleration>=0)
			this.velocity -= this.acceleration;
		else
			this.velocity = 0;
	}
	
	public void decrementAngVelocity(){
		if(this.angVelocity-this.acceleration/50>=0)
			this.angVelocity -= this.acceleration/50;
		else
			this.angVelocity = 0;
	}
	
	public void savePos(){
		oldPos.add(new double[2]);
		oldPos.get(oldPos.size()-1)[0] = x;
		oldPos.get(oldPos.size()-1)[1] = y;
		if(oldPos.size()>5)
			oldPos.remove(0);
	}
	
	public void fireSimpleProjectile() {
		try {
			projectiles.add(new SimpleProjectile(spaceship[1][0], spaceship[1][1], direction));
		} catch ( ConcurrentModificationException e) { }
	}
 
	private void drawSimpleProjectiles(Graphics graphics) {
		try {
			for(int n = 0; n < projectiles.size(); n++) {
				SimpleProjectile p = (SimpleProjectile)projectiles.get(n);
				p.move();
				p.draw(graphics);
				// check if this bullet is out of the screen, if so remove it from the list
				if(p.getX()<0 || p.getX() > SpaceWars.windowWidth || p.getY()<0 || p.getY() > SpaceWars.windowHeight) {
					projectiles.remove(p);
				}
 
			}
		} catch ( ConcurrentModificationException e) { }
	}
	
	// ====== private ======
	
	public ArrayList<double[]> getOldPos() {
		return oldPos;
	}

	public void setOldPos(ArrayList<double[]> oldPos) {
		this.oldPos = oldPos;
	}

	/*
	 * Move um ponto segundo a origem a partir de um dado ângulo
	 */
	private double[] movePoint(double[] point, double[] origin, double angle){
		double X = (double) (origin[0] + ((point[0] - origin[0]) * Math.cos(angle) -
			(point[1] - origin[1]) * Math.sin(angle)));
		double Y = (double) (origin[1] + ((point[0] - origin[0]) * Math.sin(angle) +
			(point[1] - origin[1]) * Math.cos(angle)));
		double[] res = {X, Y};
		return res;
	}
 
	// getters and setters
	public double getX() {
		return x;
	}
 
	public double getY() {
		return y;
	}
 
	public double getVelocity() {
		return velocity;
	}
 
	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}
 
	public double getDirection() {
		return direction;
	}
 
	public void setDirection(int direction) {
		this.direction = direction;
	}

	public double getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}

	public double getAngVelocity() {
		return angVelocity;
	}

	public void setAngVelocity(double angVelocity) {
		this.angVelocity = angVelocity;
	}

	public double getMaxVelocity() {
		return maxVelocity;
	}

	public void setMaxVelocity(double maxVelocity) {
		this.maxVelocity = maxVelocity;
	}

	public double getMaxAngVelocity() {
		return maxAngVelocity;
	}

	public void setMaxAngVelocity(double maxAngVelocity) {
		this.maxAngVelocity = maxAngVelocity;
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}