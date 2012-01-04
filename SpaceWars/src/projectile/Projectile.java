package projectile;

import game.Commons;
import java.awt.Color;
import java.awt.Graphics;

public class Projectile implements Commons
{
	protected float x;
	protected float y;
	protected double direction;
	protected int damage;
        protected Color color;

        public Projectile()
        {
            color = Color.WHITE;
        }
        
	public Projectile(float x, float y, double direction)
        {
		this.x = x;
		this.y = y;
		this.direction = direction;
                color = Color.WHITE;
	}
	
	public void draw(Graphics graphics){
		
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
