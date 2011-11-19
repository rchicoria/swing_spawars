package projectile;

import java.awt.Graphics;

public class Projectile {
	protected double x;
	protected double y;
	protected double direction;
	
	public Projectile(double x, double y, double direction){
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
	public void draw(Graphics graphics){
		
	}
	
	public void move(){
		
	}
	
	// getters and setters
	public double getX() {
		return x;
	}
 
	public void setX(double x) {
		this.x = x;
	}
 
	public double getY() {
		return y;
	}
 
	public void setY(double y) {
		this.y = y;
	}
 
	public double getDirection() {
		return direction;
	}
 
	public void setDirection(double direction) {
		this.direction = direction;
	}
}
