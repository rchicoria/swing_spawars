package projectile;

import java.awt.Color;
import java.awt.Graphics;

public class Projectile {
	protected double x;
	protected double y;
	protected double direction;
	protected int damage;
	
	public Projectile(double x, double y, double direction, int damage){
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.damage = damage;
	}
	
	public void draw(Graphics graphics, Color color){
		
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

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
}
