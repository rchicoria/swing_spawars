package spaceship;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;


public class UserSpaceship extends Spaceship {
    private State state;
    protected int ammo;
    protected int rockets;

	protected ArrayList<GuidedProjectile> guidedProjectiles;

        public UserSpaceship(float x, float y, float radius, float speed, float angleInDegree, Color color, int energy, int ammo, int rockets) {
		super(x, y,  radius, speed,angleInDegree, color,energy);
		this.ammo = ammo;
		this.rockets = rockets;
		this.color = Color.RED;
                this.state = new State_Default();
		this.guidedProjectiles = new ArrayList<GuidedProjectile>();
	}
        
        public void takeDamage(int damage)
        {
            this.state.takeDamage(this, damage);
        }

	
	public ArrayList<GuidedProjectile> getGuidedProjectiles() {
		return guidedProjectiles;
	}
	
	public void draw(Graphics graphics){
		super.draw(graphics);
		drawGuidedProjectiles(graphics);
	}


        public void fire_projectiles()
        {
            if(ammo>0)
                super.fire_projectiles();
        }
	public void setGuidedProjectiles(ArrayList<GuidedProjectile> guidedProjectiles) {
		this.guidedProjectiles = guidedProjectiles;
	}

	public void fireGuidedProjectile(RobotSpaceship target) {
		try {
			System.out.println(target);
			guidedProjectiles.add(new GuidedProjectile(shape[1][0], shape[1][1], angle_degrees, 50, target));
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


    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int get_ammo() {
            return ammo;
    }

    public void set_ammo(int ammo) {
            if(ammo>=0)
                this.ammo = ammo;
    }

    public int getrockets() {
            return rockets;
    }

    public void setrockets(int rockets) {
            this.rockets = rockets;
    }



}
