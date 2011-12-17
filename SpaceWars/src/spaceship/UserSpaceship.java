package spaceship;

import game.Commons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import projectile.RocketProjectile;

public class UserSpaceship extends Spaceship {
	
	protected int ammo;
	protected int rockets;
	protected int rocketsDamage;

	protected ArrayList<RocketProjectile> guidedProjectiles;

    public UserSpaceship(float x, float y, float radius, float speed, float angleInDegree, Color color, int energy, int ammo, int rockets) {
		super(x, y,  radius, speed,angleInDegree, color,energy);
		this.ammo = ammo;
		this.rockets = rockets;
		this.color = Color.RED;
		this.simpleProjectileColor = Color.WHITE;
		this.guidedProjectiles = new ArrayList<RocketProjectile>();
		this.simpleProjectilesDamage = Commons.USER_SPACESHIP_SIMPLE_PROJECTILES_DAMAGE;
		this.rocketsDamage = Commons.USER_SPACESHIP_ROCKETS_DAMAGE;
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<RocketProjectile> getGuidedProjectiles() {
		return guidedProjectiles;
	}
	
	public void draw(Graphics graphics){
		super.draw(graphics);
		//drawGuidedProjectiles(graphics);
	}

	@Override
    public void fireProjectile(RobotSpaceship target)
    {
        if(ammo>0)
            super.fireProjectile(target);
    }
	public void setGuidedProjectiles(ArrayList<RocketProjectile> guidedProjectiles) {
		this.guidedProjectiles = guidedProjectiles;
	}

	public void fireGuidedProjectile(RobotSpaceship target) {
		try {
			System.out.println(target);
			guidedProjectiles.add(new RocketProjectile(shape[1][0], shape[1][1], angleDegrees, 50, target));
		} catch ( ConcurrentModificationException e) { }
	}
	
//	protected void drawGuidedProjectiles(Graphics graphics) {
//		try {
//			for(int n = 0; n < guidedProjectiles.size(); n++) {
//				RocketProjectile p = (RocketProjectile)guidedProjectiles.get(n);
//				p.move();
//				p.draw(graphics);
//				// check if this bullet is out of the screen, if so remove it from the list
////				if(p.getX()<0 || p.getX() > SpaceWars.windowWidth || p.getY()<0 || p.getY() > SpaceWars.windowHeight) {
////					projectiles.remove(p);
////				}
//			}
//		} catch ( ConcurrentModificationException e) { }
//	}

	public int getAmmo() {
		return ammo;
	}

	public void setAmmo(int ammo) {
		if(ammo>=0)
                    this.ammo = ammo;
	}
	
	public int getrockets() {
		return rockets;
	}

	public void setrockets(int rockets) {
		this.rockets = rockets;
	}




    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            rotate(1);
        }

        if (key == KeyEvent.VK_RIGHT) {
            rotate(-1);
        }

        if (key == KeyEvent.VK_UP) {
            speed_up();
        }

        if (key == KeyEvent.VK_DOWN) {
            speed_down();
        }
    }

	public int getRocketsDamage() {
		return rocketsDamage;
	}

	public void setRocketsDamage(int rocketsDamage) {
		this.rocketsDamage = rocketsDamage;
	}
    
    


}
