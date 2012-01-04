package spaceship;

import game.Commons;

import java.awt.Color;
import java.awt.Graphics;
import projectile.Projectile;


public class UserSpaceship extends Spaceship
{
    protected int ammo;
    protected int rockets;
    protected int rocketsDamage;
    protected int hyperspace;
    protected State state;
    

    public UserSpaceship(float x, float y, float radius, float speed, float angleInDegree, Color color, int energy, int ammo, int ammoDamage, int rockets, int hyperspace)
    {
        super(x, y,  radius, speed,angleInDegree, color,energy, ammoDamage);
        this.ammo = ammo;
        this.rockets = rockets;
        this.color = Color.RED;
        this.simpleProjectileColor = Color.WHITE;
        this.state = new StateDefault();
        this.hyperspace = hyperspace;
    }

     public int getHyperspace() {
        return hyperspace;
    }

    public void setHyperspace(int hyperspace) {
        this.hyperspace = hyperspace;
    }

    public boolean hyper()
    {
        if(hyperspace>0)
        {
            hyperspace--;
            return true;
        }
        return false;
    }

    @Override
    public void takeDamage(int damage) {
        this.state.takeDamage(this, damage);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }


    @Override
    public Projectile fireProjectile(RobotSpaceship target)
    {
        if(target != null && rockets > 0)
        {
            rockets--;
            Projectile p = projectileFactory.createPack("rocket", this, target);
            p.set_x(x);
            p.set_y(y);
            return p;
        }
        else if(target==null && ammo>0)
        {
            ammo--;
            return super.fireProjectile(target);
        }
        return null;
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

	public void setAmmo(int ammo)
        {
            this.ammo = ammo;
	}
	
	public int getrockets() {
		return rockets;
	}

	public void setrockets(int rockets) {
		this.rockets = rockets;
	}
	
	@Override
	public Color getColor() {
		return this.getState().getColor();
	}

}
