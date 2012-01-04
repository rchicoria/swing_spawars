package spaceship;

import java.util.Formatter;
import collisionphysics.*;
import game.ContainerBox;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.text.DecimalFormat;
import java.util.ArrayList;
import projectile.Projectile;
import projectile.ProjectileFactory;

public class Spaceship
{
    float x, y;                     // Spaceship's center x and y
    float angleDegrees;             // Spaceship's direction
    float angle_inc;                // Spaceship's direction increment
    float speed;                    // Spaceship's speed
    float speedX, speedY;           // Spaceship's speed per step in x and y
    float ospeedX, ospeedY;
    float max_speed;                // Spaceship's speed limit
    double acceleration;            // Spaceship's acceleration
    double[] acceleration_vector;
    float radius;                   // Spaceship's radius
    Color color;                    // Spaceship's color
    
    float[][] shape = new float[8][2];

    protected ProjectileFactory projectileFactory;
    protected Color simpleProjectileColor;

    protected int max_energy;
    protected int energy;
    
    protected int ammoDamage;

    ArrayList<double[]> oldPos = new ArrayList<double[]>();

   // For collision detection and response
   // Maintain the response of the earliest collision detected 
   // by this ball instance. Only the first collision matters! (package access)
   public CollisionResponse earliestCollisionResponse = new CollisionResponse();

   public Spaceship(float x, float y, float radius, float speed, float angleInDegree, Color color, int energy, int ammoDamage)
   {
      this.x = x;
      this.y = y;
      this.angleDegrees = angleInDegree;
      this.speed = speed;
      this.speedX = (float)(speed * Math.cos(Math.toRadians(angleInDegree)));
      this.speedY = (float)(-speed * Math.sin(Math.toRadians(angleInDegree)));
      this.radius = radius;
      this.color = color;
      this.max_speed = 10;
      this.acceleration = 0.2;
      this.acceleration_vector = new double[2];
      this.angle_inc = 10;
      this.energy = energy;
      this.max_energy = energy;
      this.ammoDamage = ammoDamage;
      this.projectileFactory = new ProjectileFactory();
      set(this.x,this.y);
   }
   
   // Working copy for computing response in intersect(Spaceship, timeLimit),
   // to avoid repeatedly allocating objects.
   private CollisionResponse thisResponse = new CollisionResponse(); 
   private CollisionResponse anotherResponse = new CollisionResponse(); 

   /**
    * Check if this ball collides with the given another ball in the interval 
    * (0, timeLimit].
    * 
    * @param another: another moving ball to be checked for collision.
    * @param timeLimit: upperbound of the time interval.
    */
   public void intersect(Spaceship another, float timeLimit)
   {
      // Call movingPointIntersectsMovingPoint() with timeLimit.
      // Use thisResponse and anotherResponse, as the working copies, to store the
      // responses of this ball and another ball, respectively.
      // Check if this collision is the earliest collision, and update the ball's
      // earliestCollisionResponse accordingly.
      CollisionPhysics.pointIntersectsMovingPoint(
            this.x, this.y, this.speedX, this.speedY, this.radius,
            another.x, another.y, another.speedX, another.speedY, another.radius,
            timeLimit, thisResponse, anotherResponse);
      
      if (anotherResponse.t < another.earliestCollisionResponse.t) {
         another.earliestCollisionResponse.copy(anotherResponse);
      }
      if (thisResponse.t < earliestCollisionResponse.t) {
         earliestCollisionResponse.copy(thisResponse);
      }
   }
   /** 
    * Update the states of this ball for the given time, where time <= 1.
    * 
    * @param time: the earliest collision time detected in the system.
    *    If this ball's earliestCollisionResponse.time equals to time, this
    *    ball is the one that collided; otherwise, there is a collision elsewhere.
    */
   public void update(float time)
   {
       // Box (un)limits for spaceships
        if(x <= ContainerBox.getInstance().get_minX())
            x = ContainerBox.getInstance().get_maxX();
        else if (x >= ContainerBox.getInstance().get_maxX())
                x = ContainerBox.getInstance().get_minX();

        if(y <= ContainerBox.getInstance().get_minY())
            y = ContainerBox.getInstance().get_maxY();
        else if (y >= ContainerBox.getInstance().get_maxY())
            y = ContainerBox.getInstance().get_minY();

        //System.out.println("Direccao nave: " + this.angle_degrees);
        // Box limits for projectiles
        /*
        try {
            for(int i = 0; i<projectiles.size(); i++)
            {
                Projectile p = projectiles.get(i);
                p.move();
                //System.out.println("Direccao tiro: " + p.getDirection());
                // check if this bullet is out of the screen, if so remove it from the list
                if(p.get_x()<ContainerBox.getInstance().get_minX()
                    || p.get_x() > ContainerBox.getInstance().get_maxX()
                    || p.get_y()<ContainerBox.getInstance().get_minY()
                    || p.get_y() > ContainerBox.getInstance().get_maxY())
                    projectiles.remove(p);
            }
        }
        catch ( ConcurrentModificationException e) { }
*/
        // Set spaceship shape in position
        set(x,y);

        // Check if this ball is responsible for the first collision?
        
        if (earliestCollisionResponse.t <= time)
        { // FIXME: threshold?
         // This ball collided, get the new position and speed
         //this.x = earliestCollisionResponse.getNewX(this.x, this.speedX);
         //this.y = earliestCollisionResponse.getNewY(this.y, this.speedY);
            //System.out.println("*\n*\n* CHOQUE !\n*\n* ");

            this.ospeedX = this.speedX;
            this.ospeedY = this.speedY;
            this.speedX = earliestCollisionResponse.newSpeedX;
            this.speedY = earliestCollisionResponse.newSpeedY;
            //System.out.println("ospeed( " + ospeedX+ "  ,  " + ospeedY+")\tspeed( "+ speedX + "  ,  "+ speedY + ")");
            while(this.speed>this.max_speed)
            {
                this.speed = (float)Math.sqrt(Math.abs(Math.pow(speedX, 2)) + Math.abs(Math.pow(speedY, 2)));
                this.speedX *= (float)0.8;
                this.speedY *= (float)0.8;
            }
        }
        else
        {
             // This ball does not involve in a collision. Move straight.
             this.x += this.speedX * 1.2;
             this.y += this.speedY * 1.2;
        }

      // Clear for the next collision detection
      earliestCollisionResponse.reset();
   }

   /*
    * Spaceship polygn
    */
   
   public void set(float x, float y)
   {
        float y_offset = 5;
        shape[0][0] = x-20;
        shape[0][1] = y-20+y_offset;
        shape[1][0] = x;
        shape[1][1] = y+20+y_offset;
        shape[2][0] = x+20;
        shape[2][1] = y-20+y_offset;
        shape[3][0] = x;
        shape[3][1] = y-15+y_offset;
        shape[4][0] = x-12;
        shape[4][1] = y-10+y_offset;
        shape[5][0] = x+12;
        shape[5][1] = y-10+y_offset;
        shape[6][0] = x;
        shape[6][1] = y+15+y_offset;
        shape[7][0] = x;
        shape[7][1] = y+y_offset;

        for(int i = 0; i < 7; i++)
            shape[i] = movePoint(shape[i], shape[7], this.angleDegrees);
    }

    private float[] movePoint(float[] point, float[] origin, float angle)
    {
        angle = -angle+90;
        float X = (float) (origin[0] + ((point[0] - origin[0]) * Math.cos(Math.toRadians(angle)) +
                (point[1] - origin[1]) * Math.sin(Math.toRadians(angle))));
        float Y = (float) (origin[1] + ((point[0] - origin[0]) * Math.sin(Math.toRadians(angle)) -
                (point[1] - origin[1]) * Math.cos(Math.toRadians(angle))));
        float[] res = {X, Y};
        return res;
    }

   /*
    * Rotation
    */

   public void rotate(int direction)
   {
        this.angleDegrees += this.angle_inc*direction;
        if(this.angleDegrees<0)
            this.angleDegrees = 360+this.angleDegrees;
        this.angleDegrees = this.angleDegrees % 360;
        this.speedX = (float)(speed * Math.cos(Math.toRadians(this.angleDegrees)));
        this.speedY = (float)(-speed * Math.sin(Math.toRadians(this.angleDegrees)));
   }

    /**
    * Speed
    */

    public void speedUp()
    {
        //System.out.println("========= UP ============== [" + this.speed +"]");
        if(this.speed<this.max_speed)
        {
            this.acceleration_vector[0] = (double)(this.acceleration*Math.cos(Math.toRadians(this.angleDegrees)));
            this.acceleration_vector[1] = (double)(this.acceleration*Math.sin(Math.toRadians(this.angleDegrees)));
            this.speedX += (float)(this.acceleration_vector[0]);
            this.speedY -= (float)(this.acceleration_vector[1]);
            this.speed = (float)Math.sqrt(Math.abs(Math.pow(speedX, 2)) + Math.abs(Math.pow(speedY, 2)));

            if(is_backwards())
                this.speed *= -1;
        }
        else
            this.speed = this.max_speed;
    }

    public void speedDown()
    {
        //System.out.println("======== DOWN ============= [" + this.speed +"]");
        if(this.speed>-this.max_speed/2)
        {
            this.acceleration_vector[0] = (double)(this.acceleration*Math.cos(Math.toRadians(this.angleDegrees+180)));
            this.acceleration_vector[1] = (double)(this.acceleration*Math.sin(Math.toRadians(this.angleDegrees+180)));
            this.speedX += 2*(float)(this.acceleration_vector[0]);
            this.speedY -= 2*(float)(this.acceleration_vector[1]);

            this.speed = (float)Math.sqrt(Math.abs(Math.pow(speedX, 2)) + Math.abs(Math.pow(speedY, 2)));

            if(is_backwards())
                this.speed *= -1;
        }
        else
            this.speed = -this.max_speed/2;
    }

    private boolean is_backwards()
    {
        double theta = Math.atan(-speedY/speedX)*360/2/Math.PI;
        if (speedX < 0 && -speedY >= 0) {
                theta = 180 + theta;
        } else if (speedX < 0 && -speedY < 0) {
                theta = 180 + theta;
        } else if (speedX > 0 && -speedY < 0) {
                theta = 360 + theta;
        }

        if(Math.abs(this.angleDegrees-theta)>100)
            return true;
        return false;
    }


    /**
     *  Getters
     *  Setters
    */

    public ArrayList<double[]> getOldPos()
    {
        return oldPos;
    }

    public void setOldPos(ArrayList<double[]> oldPos)
    {
        this.oldPos = oldPos;
    }

    public void savePos()
    {
        oldPos.add(new double[2]);
        oldPos.get(oldPos.size()-1)[0] = x;
        oldPos.get(oldPos.size()-1)[1] = y;
        if(oldPos.size()>5)
                oldPos.remove(0);
    }

    public int getAmmoDamage() {
        return ammoDamage;
    }
    public int getMaxEnergy() {
            return max_energy;
    }

    public void setMaxEnergy(int maxenergy) {
            this.max_energy = maxenergy;
    }

    public int getEnergy() {
            return energy;
    }

    public void setEnergy(int energy) {
            this.energy = energy;
    }
    
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getAngleDegrees() {
        return angleDegrees;
    }

    public void setAngleDegrees(float angleDegrees) {
        this.angleDegrees = angleDegrees;
    }

    public float[][] getShape() {
        return shape;
    }

    public void setShape(float[][] shape) {
        this.shape = shape;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    /*
     * SimpleProjectile
     */

    public Projectile fireProjectile(RobotSpaceship target)
    {
        Projectile p = projectileFactory.createPack("ammo",this,target);
        p.set_x(x);
        p.set_y(y);
        return p;
    }

    public void takeDamage(int damage)
    {
        if(energy-damage<0)
            this.energy = 0;
        else
            this.energy -= damage;
    }

   
   /** Return the magnitude of speed. */
   public float getSpeed() {
      return (float)Math.sqrt(speedX * speedX + speedY * speedY);
   }
   
   /** Return the direction of movement in degrees (counter-clockwise). */
   public float getMoveAngle() {
      return (float)Math.toDegrees(Math.atan2(-speedY, speedX));
   }
   
   /** Return mass */
   public float getMass() {
      return radius * radius * radius / 1000f;
   }
   
   /** Return the kinetic energy (0.5mv^2) */
   public float getKineticEnergy() {
      return 0.5f * getMass() * (speedX * speedX + speedY * speedY);
   }

   /** Describe itself. */
    @Override
   public String toString() {
      sb.delete(0, sb.length());
      formatter.format("@(%3.0f,%3.0f) r=%3.0f V=(%3.0f,%3.0f) " +
            "S=%4.1f \u0398=%4.0f KE=%3.0f", 
            x, y, radius, speedX, speedY, getSpeed(), getMoveAngle(),
            getKineticEnergy());  // \u0398 is theta
      return sb.toString();
   }
   private StringBuilder sb = new StringBuilder();
   private Formatter formatter = new Formatter(sb);

    public float getX() { return this.x; }
    public float getY() { return this.y; }
    public float getDirection() { return this.angleDegrees; }
    public float getDirectorRadians() { return (float)Math.toRadians(this.angleDegrees); }
    
    
    public Color getSimpleProjectileColor() {
            return simpleProjectileColor;
    }

    public void setSimpleProjectileColor(Color simpleProjectileColor) {
        this.simpleProjectileColor = simpleProjectileColor;
    }

    public int getSimpleProjectilesDamage() {
        return ammoDamage;
    }

    public void setSimpleProjectilesDamage(int simpleProjectilesDamage) {
	this.ammoDamage = simpleProjectilesDamage;
    }

    
}
