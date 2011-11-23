package spaceship;

import game.SpaceWars;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import projectile.GuidedProjectile;
import projectile.SimpleProjectile;

public class UserSpaceship extends Spaceship {
	
	protected int ammo;
	protected int rocketsAmmo;

	protected ArrayList<GuidedProjectile> guidedProjectiles;
	
	public UserSpaceship(double x, double y, double velocity, double direction,
			double angVelocity, double acceleration, double maxVelocity,
			double maxAngVelocity, int energy, int ammo, int rocketsAmmo) {
		super(x, y, velocity, direction, angVelocity, acceleration, maxVelocity,
				maxAngVelocity, energy);
		this.ammo = ammo;
		this.rocketsAmmo = rocketsAmmo;
		this.color = Color.RED;
		this.simpleProjectileColor = Color.WHITE;
		this.guidedProjectiles = new ArrayList<GuidedProjectile>();
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<GuidedProjectile> getGuidedProjectiles() {
		return guidedProjectiles;
	}
	
	public void draw(Graphics graphics){
		super.draw(graphics);
		drawGuidedProjectiles(graphics);
	}

	public void setGuidedProjectiles(ArrayList<GuidedProjectile> guidedProjectiles) {
		this.guidedProjectiles = guidedProjectiles;
	}

	public void fireGuidedProjectile(RobotSpaceship target) {
		try {
			System.out.println(target);
			guidedProjectiles.add(new GuidedProjectile(spaceship[1][0], spaceship[1][1], direction, 50, target));
		} catch ( ConcurrentModificationException e) { }
	}
	
	protected void drawGuidedProjectiles(Graphics graphics) {
		try {
			for(int n = 0; n < guidedProjectiles.size(); n++) {
				GuidedProjectile p = (GuidedProjectile)guidedProjectiles.get(n);
				p.move();
				p.draw(graphics);
				// check if this bullet is out of the screen, if so remove it from the list
//				if(p.getX()<0 || p.getX() > SpaceWars.windowWidth || p.getY()<0 || p.getY() > SpaceWars.windowHeight) {
//					projectiles.remove(p);
//				}
			}
		} catch ( ConcurrentModificationException e) { }
	}

	public int getAmmo() {
		return ammo;
	}

	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}
	
	public int getRocketsAmmo() {
		return rocketsAmmo;
	}

	public void setRocketsAmmo(int rocketsAmmo) {
		this.rocketsAmmo = rocketsAmmo;
	}

}
