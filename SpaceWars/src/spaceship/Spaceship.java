package spaceship;

import java.util.Formatter;
import collisionphysics.*;
import game.ContainerBox;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import projectile.Projectile;
import projectile.SimpleProjectile;

public class Spaceship
{
    float x, y;                     // Spaceship's center x and y
    float angle_degrees;            // Spaceship's direction
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

    protected ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
    protected Color simpleProjectileColor;

    protected int max_energy;
    protected int energy;
    
    ArrayList<double[]> oldPos = new ArrayList<double[]>();

   // For collision detection and response
   // Maintain the response of the earliest collision detected 
   // by this ball instance. Only the first collision matters! (package access)
   public CollisionResponse earliestCollisionResponse = new CollisionResponse();

   public Spaceship(float x, float y, float radius, float speed, float angleInDegree, Color color, int energy)
   {
      this.x = x;
      this.y = y;
      this.angle_degrees = angleInDegree;
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
        try {
            for(Projectile p: projectiles)
            {
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

        // Set spaceship shape in position
        set(x,y);

        // Check if this ball is responsible for the first collision?
        
        if (earliestCollisionResponse.t <= time)
        { // FIXME: threshold?
         // This ball collided, get the new position and speed
         //this.x = earliestCollisionResponse.getNewX(this.x, this.speedX);
         //this.y = earliestCollisionResponse.getNewY(this.y, this.speedY);
            System.out.println("*\n*\n* CHOQUE !\n*\n* ");

            this.ospeedX = this.speedX;
            this.ospeedY = this.speedY;
            this.speedX = earliestCollisionResponse.newSpeedX;
            this.speedY = earliestCollisionResponse.newSpeedY;
            System.out.println("ospeed( " + ospeedX+ "  ,  " + ospeedY+")\tspeed( "+ speedX + "  ,  "+ speedY + ")");
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
        shape[0][0] = (float)x-20;
        shape[0][1] = (float)y-20+y_offset;
        shape[1][0] = (float)x;
        shape[1][1] = (float)y+20+y_offset;
        shape[2][0] = (float)x+20;
        shape[2][1] = (float)y-20+y_offset;
        shape[3][0] = (float)x;
        shape[3][1] = (float)y-15+y_offset;
        shape[4][0] = (float)x-12;
        shape[4][1] = (float)y-10+y_offset;
        shape[5][0] = (float)x+12;
        shape[5][1] = (float)y-10+y_offset;
        shape[6][0] = (float)x;
        shape[6][1] = (float)y+15+y_offset;
        shape[7][0] = x;
        shape[7][1] = y+y_offset;

        for(int i = 0; i < 7; i++)
            shape[i] = movePoint(shape[i], shape[7], this.angle_degrees);
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
        this.angle_degrees += this.angle_inc*direction;
        if(this.angle_degrees<0)
            this.angle_degrees = 360+this.angle_degrees;
        this.angle_degrees = this.angle_degrees % 360;
        this.speedX = (float)(speed * Math.cos(Math.toRadians(this.angle_degrees)));
        this.speedY = (float)(-speed * Math.sin(Math.toRadians(this.angle_degrees)));
   }

    /**
    * Speed
    */

    public void speed_up()
    {
        //System.out.println("========= UP ============== [" + this.speed +"]");
        if(this.speed<this.max_speed)
        {
            this.acceleration_vector[0] = (double)(this.acceleration*Math.cos(Math.toRadians(this.angle_degrees)));
            this.acceleration_vector[1] = (double)(this.acceleration*Math.sin(Math.toRadians(this.angle_degrees)));
            logspeed(true,this.speed,this.speedX,this.speedY);
            this.speedX += (float)(this.acceleration_vector[0]);
            this.speedY -= (float)(this.acceleration_vector[1]);
            this.speed = (float)Math.sqrt(Math.abs(Math.pow(speedX, 2)) + Math.abs(Math.pow(speedY, 2)));

            if(is_backwards())
                this.speed *= -1;
        }
        else
            this.speed = this.max_speed;
    }

    public void speed_down()
    {
        //System.out.println("======== DOWN ============= [" + this.speed +"]");
        if(this.speed>-this.max_speed/2)
        {
            this.acceleration_vector[0] = (double)(this.acceleration*Math.cos(Math.toRadians(this.angle_degrees+180)));
            this.acceleration_vector[1] = (double)(this.acceleration*Math.sin(Math.toRadians(this.angle_degrees+180)));
            logspeed(true,this.speed,this.speedX,this.speedY);
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

        if(Math.abs(this.angle_degrees-theta)>100)
            return true;
        return false;
    }

    private void logspeed(boolean up, float speed, float speedX, float speedY)
    {
         if(this.color==color.BLUE)
        {
            DecimalFormat dec = new DecimalFormat("###.##");
            
            System.out.println("Speed [ " + dec.format(speed) + " ]");
            System.out.println("Speed [ " + dec.format(speedX) + ", " + dec.format(speedY) + "]");

            if(up)
                System.out.println("Vou acelerar [ " + dec.format(acceleration_vector[0]) + ", " + dec.format(acceleration_vector[1])+ "]");
            else
                System.out.println("Vou travar [ " + dec.format(acceleration_vector[0]) + ", " + dec.format(acceleration_vector[1])+ "]");

        }
    }

    /**
    * Old positions
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

    /*
     * Energy
     */

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

    /*
     * SimpleProjectile
     */

    public void fire_projectiles()
    {
        try
        {
            if(projectiles.size()<20)
            projectiles.add(new SimpleProjectile(shape[1][0], shape[1][1], Math.toRadians(this.angle_degrees), 5));
        }
        catch ( ConcurrentModificationException e) { }
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public void setProjectiles(ArrayList<Projectile> projectiles) {
        this.projectiles = projectiles;
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

   /*
    * Drawing
    */

    /** Draw itself using the given graphics context. */
    public void draw(Graphics g)
    {
        // Shape
        draw_shape(g);
        // Old position
        draw_old_position(g);
        // Life Bar
        draw_life_bar(g);
        // Projectiles
        draw_projectiles(g);
        // Radius
        g.drawOval((int)(x - radius), (int)(y - radius), (int)(2 * radius),(int)(2 * radius));
        g.drawOval((int)x-1, (int)y-1, 2, 2);
        // Angles
        g.fillOval((int)(x+speedX*1.4), (int)(y+speedY*1.4), 5, 5);
        g.setColor(Color.cyan);
        g.fillOval((int)(x+ospeedX*1.4), (int)(y+ospeedY*1.4), 5, 5);
    }

    private void draw_old_position(Graphics graphics)
    {
        graphics.setColor(this.color);
        for(int i=0; i<getOldPos().size()-1; i++)
        {
            int x = (int)getOldPos().get(i)[0];
            int y = (int)getOldPos().get(i)[1];
            graphics.drawOval(x, y, 5, 5);
            graphics.fillOval(x, y, 5, 5);
        }
    }

    private void draw_life_bar(Graphics graphics)
    {
        Polygon bar = new Polygon();
        bar.addPoint((int)(x-20), (int)(y+30));
        bar.addPoint((int)(x-20+(float)energy/(float)max_energy*40), (int)(y+30));
        bar.addPoint((int)(x-20), (int)(y+32));
        bar.addPoint((int)(x-20+(float)energy/(float)max_energy*40), (int)(y+32));
        if((float)energy/(float)max_energy>0.50)
            graphics.setColor(Color.green);
        else if((float)energy/(float)max_energy>0.25)
            graphics.setColor(Color.yellow);
        else
            graphics.setColor(Color.RED);
        graphics.fillPolygon(bar);
    }

    private void draw_projectiles(Graphics graphics)
    {
        graphics.setColor(this.color);
        try
        {
            for(Projectile p: projectiles)
                p.draw(graphics, simpleProjectileColor);
        }
        catch ( ConcurrentModificationException e) { System.out.println(e.getMessage());}
    }

    private void draw_shape(Graphics graphics)
    {
        graphics.setColor(this.color);
        Polygon shape1  = new Polygon();
        Polygon shape2 = new Polygon();
        for(int i=0; i<4; i++){ shape1.addPoint((int)shape[i][0], (int)shape[i][1]);}
        for(int i=4; i<7; i++){ shape2.addPoint((int)shape[i][0], (int)shape[i][1]);}
        graphics.fillPolygon(shape1);
        graphics.setColor(Color.BLACK);
        graphics.fillPolygon(shape2);
        
    }

    public float get_x() { return this.x; }
    public float get_y() { return this.y; }
    public float get_direction() { return this.angle_degrees; }
    public float get_direction_radians() { return (float)Math.toRadians(this.angle_degrees); }
}
