package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;
 
import javax.swing.JFrame;
import javax.swing.Timer;

import pack.EnergyPack;
import pack.Pack;
import pack.ProjectilePack;
import projectile.GuidedProjectile;
import projectile.Projectile;
import spaceship.*;

/**
 * @author João Claro
 * @author Ricardo Lopes
 * @author Rui Chicória
 */

@SuppressWarnings("serial")
public class SpaceWars extends JFrame implements KeyListener, Commons {
        
        public ContainerBox box;
        
	float robot_spaceships_pos[][] = new float[N_ROBOTS][3];
	
	private UserSpaceship user_spaceship;
	private ArrayList<RobotSpaceship> robot_spaceships;
	private ArrayList<Pack> packs;
	
	private ArrayList<Integer> keys = new ArrayList<Integer>();
	private int counter = 0;
	private int fireLimit = 0;
 
 
	public static void main(String[] args) {
		new SpaceWars();
	}
 
	public SpaceWars()
        {
            // Init the Container Box to fill the screen
            box = ContainerBox.getInstance();
            box.set(0, 0, BOARD_WIDTH, BOARD_HEIGTH);

            
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    // Exit on Close
            this.setSize(BOARD_WIDTH, BOARD_HEIGTH);                // Window Size
            this.setResizable(false);                               // Non Resizable
            this.setLocationRelativeTo(null);                       // Centers
            this.setVisible(true);                                  // Is visible
            this.createBufferStrategy(2);
            this.addKeyListener(this);                              // Accept key control

            initGame();
            ActionListener actionListener = new ActionListener()
            {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent)
                    {
                        counter++;
                        counter%=12;
                        fireLimit++;
                        if(fireLimit>12)
                                fireLimit=12;
                        gameLoop();
                    }
            };
            
            Timer timer = new Timer(1000/UPDATE_RATE, actionListener);
            timer.start();
 
        }
	
	/*
	 * Inicialização das variáveis relacionadas com o jogo
	 */
	private void initGame()
        {

            user_spaceship = new UserSpaceship(USER_SPACESHIP_INIT_X,
                                                USER_SPACESHIP_INIT_Y,
                                                USER_SPACESHIP_RADIUS,
                                                USER_SPACESHIP_INIT_SPEED,
                                                USER_SPACESHIP_DIRECTION,
                                                USER_SPACESHIP_COLOR,
                                                USER_SPACESHIP_ENERGY,
                                                USER_SPACESHIP_AMMO,
                                                USER_SPACESHIP_ROCKETS);
            
            robot_spaceships = new ArrayList<RobotSpaceship>();

            robot_spaceships_pos[0][0] = 70;
            robot_spaceships_pos[0][1] = 70;
            robot_spaceships_pos[0][2] = 0;
            robot_spaceships_pos[1][0] = ContainerBox.getInstance().maxX-70;
            robot_spaceships_pos[1][1] = 70;
            robot_spaceships_pos[1][2] = 0;
            robot_spaceships_pos[2][0] = 70;
            robot_spaceships_pos[2][1] = ContainerBox.getInstance().maxY-70;
            robot_spaceships_pos[2][2] = 0;
            robot_spaceships_pos[3][0] = ContainerBox.getInstance().maxX-70;
            robot_spaceships_pos[3][1] = ContainerBox.getInstance().maxY-70;
            robot_spaceships_pos[3][2] = 0;
            packs = new ArrayList<Pack>();

            for(int i=0; i<N_ROBOTS; i++)
                    robot_spaceships.add(new RobotSpaceship(robot_spaceships_pos[i][0],
                                                            robot_spaceships_pos[i][1],
                                                            ROBOT_SPACESHIP_RADIUS,
                                                            ROBOT_SPACESHIP_INIT_SPEED,
                                                            ROBOT_SPACESHIP_DIRECTION,
                                                            ROBOT_SPACESHIP_COLOR,
                                                            ROBOT_SPACESHIP_ENERGY));

	}
 
	private void gameLoop()
        {
            float timeLeft = 1.0f;  // One time-step to begin with
            // Find the earliest collision up to timeLeft among all objects

           // Repeat until the one time-step is up
            do {
                // Find the earliest collision up to timeLeft among all objects
                float tMin = timeLeft;

                // Check collision between two spaceships
                for (int i = 0; i < robot_spaceships.size(); i++) {
                    for (int j = 0; j < robot_spaceships.size(); j++) {
                        if (i < j) {
                           robot_spaceships.get(i).intersect(robot_spaceships.get(j), tMin);
                            if (robot_spaceships.get(i).earliestCollisionResponse.t < tMin) {
                                tMin = robot_spaceships.get(i).earliestCollisionResponse.t;
                            }
                        }
                    }
                }

                // Update all the spaceships up to the detected earliest collision time tMin,
                // or timeLeft if there is no collision.
                for (int i = 0; i < robot_spaceships.size(); i++)
                {
                    if (counter == 0 || counter == 6)
                    {
                        robot_spaceships.get(i).savePos();
                    }
                    robot_spaceships.get(i).update(tMin);
                }
                timeLeft -= tMin;                // Subtract the time consumed and repeat
        } while (timeLeft > EPSILON_TIME);  // Ignore remaining time less than threshold

        
		// your game logic goes here
		/*
		for(int j=0; j<robot_spaceships.size(); j++){
			boolean collision = false;
			RobotSpaceship robot = robot_spaceships.get(j);
			for(int i=0; i<800; i+=1){
				double dx = i*Math.cos(robot.getDirection()+0.5 * Math.PI);
				double dy = i*Math.sin(robot.getDirection()+0.5 * Math.PI);
				int posX = (int)(robot.get_x()+dx);
				int posY = (int)(robot.get_y()+dy);
	        	Point sp = new Point((int)user_spaceship.get_x(), (int)user_spaceship.get_y());
				Point rp = new Point((int)posX, (int)posY);
				if(sp.distance(rp) <= 60 && i>200) {
					collision=true;
				} else if(sp.distance(rp) <= 60 && i<=200)
					robot.rotate(-robot.getAngVelocity());
				for(int k=0; k<robot_spaceships.size(); k++){
					RobotSpaceship otherRobot = robot_spaceships.get(k);
					if(otherRobot != robot){
						Point op = new Point((int)otherRobot.get_x(), (int)otherRobot.get_y());
						if(op.distance(rp) <= 60 && i<=200)
							robot.rotate(-robot.getAngVelocity());
					}
				}		
			}
			
	        if(collision)
	        	robot.setFireActive(true);
	        else
	        	robot.setFireActive(false);
		}*/

                // Rotate Left/Right
		if(keys.contains(KEY_LEFT))
			user_spaceship.rotate(1);
                if(keys.contains(KEY_RIGHT))
			user_spaceship.rotate(-1);
		// Speed Up/Down
                if (keys.contains(KEY_UP))
			user_spaceship.speed_up();
		if (keys.contains(KEY_DOWN))
			user_spaceship.speed_down();
                // Fire Simple Projectiles
                if (keys.contains(KEY_AMMO))
                {
                    user_spaceship.fire_projectiles();
                    user_spaceship.set_ammo(user_spaceship.get_ammo()-1);
		}

                // Fire Rockets
                if (keys.contains(KeyEvent.VK_C) && user_spaceship.getRocketsAmmo()>0 && fireLimit==12)
                {
                        RobotSpaceship currentTarget = null;
                        double min = 800;
                        for(int i=0; i<800; i++)
                                for(int j=0; j<robot_spaceships.size(); j++)
                                {
                                        RobotSpaceship robot = robot_spaceships.get(j);
                                        double dx = i*Math.cos(user_spaceship.get_direction_radians()+0.5 * Math.PI);
                                        double dy = i*Math.sin(user_spaceship.get_direction_radians()+0.5 * Math.PI);
                                        int posX = (int)(user_spaceship.get_x()+dx);
                                        int posY = (int)(user_spaceship.get_y()+dy);
                                        Point sp = new Point((int)posX, (int)posY);
                                        Point rp = new Point((int)robot.get_x(), (int)robot.get_y());
                                        if(sp.distance(rp) <= min)
                                        {
                                            min = sp.distance(rp);
                                            currentTarget = robot;
                                        }
                                }
                        System.out.println(currentTarget);
                        user_spaceship.fireGuidedProjectile(currentTarget);
                        user_spaceship.setRocketsAmmo(user_spaceship.getRocketsAmmo()-1);
                        fireLimit=0;
		}
		
		
		
		// guarda as posições anteriores
		if(counter==0 || counter==6)
			user_spaceship.savePos();
		
		// movimento das naves robot
		for(int i=0; i<robot_spaceships.size(); i++)
                {
			if(counter == 0)
                        {
                            robot_spaceships.get(i).rotate();
                        }
			robot_spaceships.get(i).update(0);

                        if(counter==0 || counter == 6)
                            robot_spaceships.get(i).savePos();

                        if(robot_spaceships.get(i).isFireActive())
                        {
                            Random random = new Random();
                            if(random.nextDouble() < ROBOT_SPACESHIP_FIRE_RATE)
                                robot_spaceships.get(i).fire_projectiles();
			}
		}
		
		// decrement pack's remaining time
		for(int n = 0; n < packs.size(); n++)
                {
                    Pack p = packs.get(n);
                    p.setTimeRemaining(p.getTimeRemaining()-1);
                    if(p.getTimeRemaining()<=0)
                        packs.remove(p);
		}
		
		// decrement rockets' remaining time
		for(int n = 0; n < user_spaceship.getGuidedProjectiles().size(); n++)
                {
			GuidedProjectile p = user_spaceship.getGuidedProjectiles().get(n);
			p.setRemainingTime(p.getRemainingTime()-1);
			if(p.getRemainingTime()<=0)
				user_spaceship.getGuidedProjectiles().remove(p);
		}
		
		// create packs
		Random random = new Random();
		double prob = random.nextDouble();
		if(prob < ENERGY_PACK_RATE)
                    packs.add(new EnergyPack(ENERGY_PACK));
		else if( prob < AMMO_PACK_RATE)
                    packs.add(new ProjectilePack(AMMO_PACK));
		
		
		// check for collision between robots and projectiles
		try{
			for(int i=user_spaceship.getProjectiles().size()-1; i>=0; i--) {
				Projectile s = user_spaceship.getProjectiles().get(i);
				for(int n = 0; n < robot_spaceships.size(); n++) {
					RobotSpaceship r = robot_spaceships.get(n);
					Point sp = new Point((int)s.get_x(), (int)s.get_y());
					Point rp = new Point((int)r.get_x(), (int)r.get_y());
					if(sp.distance(rp) <= 20) {
						r.setEnergy(r.getEnergy()-s.getDamage());
						user_spaceship.getProjectiles().remove(s);
						if(r.getEnergy()<=0){
							robot_spaceships.remove(n);
							r.setEnergy(0);
							if(robot_spaceships.size()==0){
								initGame();
								break;
							}
						}
					}
				}
			}
		} catch(IndexOutOfBoundsException ex)
		{
			
		}
		
		// check for collision between robots and guided projectiles
		try{
			for(int i=user_spaceship.getGuidedProjectiles().size()-1; i>=0; i--) {
				Projectile s = user_spaceship.getGuidedProjectiles().get(i);
				for(int n = 0; n < robot_spaceships.size(); n++) {
					RobotSpaceship r = robot_spaceships.get(n);
					Point sp = new Point((int)s.get_x(), (int)s.get_y());
					Point rp = new Point((int)r.get_x(), (int)r.get_y());
					if(sp.distance(rp) <= 20) {
						r.setEnergy(r.getEnergy()-s.getDamage());
						user_spaceship.getGuidedProjectiles().remove(s);
						if(r.getEnergy()<=0){
							robot_spaceships.remove(n);
							r.setEnergy(0);
							if(robot_spaceships.size()==0){
								initGame();
								break;
							}
						}
					}
				}
			}
		} catch(IndexOutOfBoundsException ex)
		{
			
		}
		
		// check for collision between user and projectiles
		try{
			for(int n = 0; n < robot_spaceships.size(); n++) {
				RobotSpaceship r = robot_spaceships.get(n);
				for(int i=r.getProjectiles().size()-1; i>=0; i--) {
					Projectile p = r.getProjectiles().get(i);
					Point sp = new Point((int)user_spaceship.get_x(), (int)user_spaceship.get_y());
					Point pp = new Point((int)p.get_x(), (int)p.get_y());
					if(sp.distance(pp) <= 20) {
						user_spaceship.setEnergy(user_spaceship.getEnergy()-p.getDamage());
						r.getProjectiles().remove(p);
						if(user_spaceship.getEnergy()<=0){
							initGame();
							break;
						}
					}
				}
			}
		} 
			catch(IndexOutOfBoundsException ex)
		{
			
		}
		
		// check for collision between user and packs
		for(int n = 0; n < packs.size(); n++) {
			try{
				EnergyPack p = (EnergyPack) packs.get(n);
				Point sp = new Point((int)user_spaceship.get_x(), (int)user_spaceship.get_y());
				Point pp = new Point((int)p.get_x(), (int)p.get_y());
				if(sp.distance(pp) <= 30){
					user_spaceship.setEnergy(user_spaceship.getEnergy()+p.getAmount());
					if(user_spaceship.getEnergy()>user_spaceship.getMaxEnergy())
						user_spaceship.setEnergy(user_spaceship.getMaxEnergy());
					packs.remove(p);
				}
			} catch(ClassCastException ex) {
				ProjectilePack p = (ProjectilePack) packs.get(n);
				Point sp = new Point((int)user_spaceship.get_x(), (int)user_spaceship.get_y());
				Point pp = new Point((int)p.get_x(), (int)p.get_y());
				if(sp.distance(pp) <= 30){
					user_spaceship.set_ammo(user_spaceship.get_ammo()+p.getAmount());
					packs.remove(p);
				}
			}
		}
		user_spaceship.update(TOP_ALIGNMENT);
		// so much for game logic now let's draw already!
		draw_frame();
	}
 
	private void draw_frame()
        {
            // code for the drawing goes here
            BufferStrategy bf = this.getBufferStrategy();
            Graphics graphics = null;

            try {
                    graphics = bf.getDrawGraphics();

                    // Clear
                    box.draw(graphics);

                    // Draw packs
                    for(int i=0; i<packs.size(); i++)
                            packs.get(i).draw(graphics);

                    for(int i=0; i<robot_spaceships.size(); i++)
                            robot_spaceships.get(i).draw(graphics);

                    user_spaceship.draw(graphics);

                    Polygon bar = new Polygon();
                    bar.addPoint(600, 560);
                    bar.addPoint(600, 580);
                    bar.addPoint(700, 580);
                    bar.addPoint(700, 560);

                    Polygon bar2 = new Polygon();
                    bar2.addPoint(600, 560);
                    bar2.addPoint(600, 580);
                    bar2.addPoint(600+(user_spaceship.getEnergy()), 580);
                    bar2.addPoint(600+(user_spaceship.getEnergy()), 560);

                    graphics.setColor(Color.WHITE);
                    graphics.fillPolygon(bar);
                    if(user_spaceship.getEnergy()>50)
                        graphics.setColor(Color.GREEN);
                    else if(user_spaceship.getEnergy()>25)
                        graphics.setColor(Color.YELLOW);
                    else
                        graphics.setColor(Color.RED);
                    graphics.fillPolygon(bar2);

                    // show ammo
                    graphics.setColor(Color.WHITE);
                    graphics.setFont(new Font("Arial", 20, 30));
                    graphics.drawString("Ammo: " + user_spaceship.get_ammo(), 10, 575);
                    graphics.setColor(Color.WHITE);
                    graphics.setFont(new Font("Arial", 20, 30));
                    graphics.drawString("Rockets: " + user_spaceship.getRocketsAmmo(), 10, 540);

            }
            finally
            {
                    graphics.dispose();
            }

            // Shows the contents
            bf.show();

            Toolkit.getDefaultToolkit().sync();
	}

	@Override
	public void keyPressed(KeyEvent e) {
            //System.out.println(e.getKeyChar());
		if(!keys.contains(new Integer(e.getKeyCode())))
			keys.add(new Integer(e.getKeyCode()));
	}
	 
	@Override
	public void keyReleased(KeyEvent e) { 
		keys.remove(new Integer(e.getKeyCode()));
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}