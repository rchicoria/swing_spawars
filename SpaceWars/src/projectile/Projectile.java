package projectile;

import java.awt.Color;
import java.awt.Graphics;

public class Projectile {
	protected float x;
	protected float y;
	protected double direction;
	protected int damage;
	
	public Projectile(float x, float y, double direction, int damage){
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.damage = damage;
	}
	
	public void draw(Graphics graphics, Color color){
		System.out.println("AQUI!!!!!!!!!!!!!!!!!");
	}
	
	public void move(){
		
	}
	
	// getters and setters
	public float get_x() {
		return x;
	}
 
	public void set_x(float x) {
		this.x = x;
	}
 
	public float get_y() {
		return y;
	}
 
	public void set_y(float y) {
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
