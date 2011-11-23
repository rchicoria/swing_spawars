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
 
@SuppressWarnings("serial")
public class SpaceWars extends JFrame implements KeyListener {
 
	/**
	 * @author João Claro
	 * @author Ricardo Lopes
	 * @author Rui Chicória
	 */
 
	public static int windowWidth = 800;
	public static int windowHeight = 600;
	
	public static int N_ROBOTS = 4;
	double robotPos[][] = new double[N_ROBOTS][3];
	
	private UserSpaceship spaceship;
	private ArrayList<RobotSpaceship> robotSpaceships;
	private ArrayList<Pack> packs;
	
	private ArrayList<Integer> keys = new ArrayList<Integer>();
	private int counter = 0;
	private int fireLimit = 0;
 
 
	public static void main(String[] args) {
		new SpaceWars();
	}
 
	public SpaceWars() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(windowWidth, windowHeight);
		this.setResizable(false);
		this.setLocation(100, 100);
		this.setVisible(true);
 
		this.createBufferStrategy(2);
		
		this.addKeyListener(this);
 
		initGame();
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				counter++;
				counter%=12;
				fireLimit++;
				if(fireLimit>12)
					fireLimit=12;
				gameLoop();
			}
	    };
	    Timer timer = new Timer(1000/30, actionListener);
	    timer.start();
 
	}
	
	/*
	 * Inicialização das variáveis relacionadas com o jogo
	 */
	private void initGame() {
		spaceship = new UserSpaceship(windowWidth/2, windowHeight/2, 0, 0, 0, 0.2, 10, 0.2, 100, 100, 3);
		robotSpaceships = new ArrayList<RobotSpaceship>();
		packs = new ArrayList<Pack>();
		robotPos[0][0] = 70; 
		robotPos[0][1] = 70;
		robotPos[0][2] = 0;
		robotPos[1][0] = windowWidth-70;
		robotPos[1][1] = 70;
		robotPos[1][2] = 0;
		robotPos[2][0] = 70;
		robotPos[2][1] = windowHeight-70;
		robotPos[2][2] = 0;
		robotPos[3][0] = windowWidth-70;
		robotPos[3][1] = windowHeight-70;
		robotPos[3][2] = 0;
		
		for(int i=0; i<N_ROBOTS; i++){
			robotSpaceships.add(new RobotSpaceship(robotPos[i][0], robotPos[i][1], 
					0,robotPos[i][2],0,0.2,5,0.2, 40));
		}
	}
 
	private void gameLoop() {
		// your game logic goes here
		
		for(int j=0; j<robotSpaceships.size(); j++){
			boolean collision = false;
			RobotSpaceship robot = robotSpaceships.get(j);
			for(int i=0; i<800; i+=1){
				double dx = i*Math.cos(robot.getDirection()+0.5 * Math.PI);
				double dy = i*Math.sin(robot.getDirection()+0.5 * Math.PI);
				int posX = (int)(robot.getX()+dx);
				int posY = (int)(robot.getY()+dy);
	        	Point sp = new Point((int)spaceship.getX(), (int)spaceship.getY());
				Point rp = new Point((int)posX, (int)posY);
				if(sp.distance(rp) <= 60 && i>200) {
					collision=true;
				} else if(sp.distance(rp) <= 60 && i<=200)
					robot.rotate(-robot.getAngVelocity());
				for(int k=0; k<robotSpaceships.size(); k++){
					RobotSpaceship otherRobot = robotSpaceships.get(k); 
					if(otherRobot != robot){
						Point op = new Point((int)otherRobot.getX(), (int)otherRobot.getY());
						if(op.distance(rp) <= 60 && i<=200)
							robot.rotate(-robot.getAngVelocity());
					}
				}		
			}
			
	        if(collision)
	        	robot.setFireActive(true);
	        else
	        	robot.setFireActive(false);
		}
		
		if(keys.contains(new Integer(37))) {
			spaceship.rotate(-spaceship.getAngVelocity());
		} if(keys.contains(new Integer(39))) {
			spaceship.rotate(spaceship.getAngVelocity());
		} if (keys.contains(new Integer(38))) {
			spaceship.incrementVelocity();
			spaceship.incrementAngVelocity();
		} if (keys.contains(new Integer(40))) {
			spaceship.decrementVelocity();
			spaceship.decrementAngVelocity();
		} if (keys.contains(new Integer(32)) && spaceship.getAmmo()>0) {
			spaceship.fireSimpleProjectile();
			spaceship.setAmmo(spaceship.getAmmo()-1);
		} if (keys.contains(KeyEvent.VK_ALT) && spaceship.getRocketsAmmo()>0 && fireLimit==12) {
				RobotSpaceship currentTarget = null;
				double min = 800;
				for(int i=0; i<800; i++)
					for(int j=0; j<robotSpaceships.size(); j++){
						RobotSpaceship robot = robotSpaceships.get(j);
						double dx = i*Math.cos(spaceship.getDirection()+0.5 * Math.PI);
						double dy = i*Math.sin(spaceship.getDirection()+0.5 * Math.PI);
						int posX = (int)(spaceship.getX()+dx);
						int posY = (int)(spaceship.getY()+dy);
			        	Point sp = new Point((int)posX, (int)posY);
						Point rp = new Point((int)robot.getX(), (int)robot.getY());
						if(sp.distance(rp) <= min) {
							min=sp.distance(rp);
							currentTarget = robot;		
						}
					}
				System.out.println(currentTarget);
				spaceship.fireGuidedProjectile(currentTarget);
				spaceship.setRocketsAmmo(spaceship.getRocketsAmmo()-1);
				fireLimit=0;
		}
		
		
		if(spaceship.getVelocity() > 0)
			spaceship.move();
		
		// guarda as posições anteriores
		if(counter==0 || counter==6)
			spaceship.savePos();
		
		// movimento das naves robot
		for(int i=0; i<robotSpaceships.size(); i++){
			robotSpaceships.get(i).incrementVelocity();
			robotSpaceships.get(i).incrementAngVelocity();
			if(counter == 0)
				robotSpaceships.get(i).changeDirection();
			robotSpaceships.get(i).move();
			if(counter==0 || counter == 6)
				robotSpaceships.get(i).savePos();
			if(robotSpaceships.get(i).isFireActive()){
				Random random = new Random();
				if(random.nextDouble() < 0.6)
					robotSpaceships.get(i).fireSimpleProjectile();
			}
		}
		
		// decrement pack's remaining time
		for(int n = 0; n < packs.size(); n++) {
			Pack p = packs.get(n);
			p.setTimeRemaining(p.getTimeRemaining()-1);
			if(p.getTimeRemaining()<=0)
				packs.remove(p);
		}
		
		// decrement rockets' remaining time
		for(int n = 0; n < spaceship.getGuidedProjectiles().size(); n++) {
			GuidedProjectile p = spaceship.getGuidedProjectiles().get(n);
			p.setRemainingTime(p.getRemainingTime()-1);
			if(p.getRemainingTime()<=0)
				spaceship.getGuidedProjectiles().remove(p);
		}
		
		// create packs
		Random random = new Random();
		double prob = random.nextDouble();
		if(prob < 0.0003)
			packs.add(new EnergyPack(30));
		else if( prob < 0.003)
			packs.add(new ProjectilePack(100));
		
		
		// check for collision between robots and projectiles
		try{
			for(int i=spaceship.getProjectiles().size()-1; i>=0; i--) {
				Projectile s = spaceship.getProjectiles().get(i);
				for(int n = 0; n < robotSpaceships.size(); n++) {
					RobotSpaceship r = robotSpaceships.get(n);
					Point sp = new Point((int)s.getX(), (int)s.getY());
					Point rp = new Point((int)r.getX(), (int)r.getY());
					if(sp.distance(rp) <= 20) {
						r.setEnergy(r.getEnergy()-s.getDamage());
						spaceship.getProjectiles().remove(s);
						if(r.getEnergy()<=0){
							robotSpaceships.remove(n);
							r.setEnergy(0);
							if(robotSpaceships.size()==0){
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
			for(int i=spaceship.getGuidedProjectiles().size()-1; i>=0; i--) {
				Projectile s = spaceship.getGuidedProjectiles().get(i);
				for(int n = 0; n < robotSpaceships.size(); n++) {
					RobotSpaceship r = robotSpaceships.get(n);
					Point sp = new Point((int)s.getX(), (int)s.getY());
					Point rp = new Point((int)r.getX(), (int)r.getY());
					if(sp.distance(rp) <= 20) {
						r.setEnergy(r.getEnergy()-s.getDamage());
						spaceship.getGuidedProjectiles().remove(s);
						if(r.getEnergy()<=0){
							robotSpaceships.remove(n);
							r.setEnergy(0);
							if(robotSpaceships.size()==0){
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
			for(int n = 0; n < robotSpaceships.size(); n++) {
				RobotSpaceship r = robotSpaceships.get(n);
				for(int i=r.getProjectiles().size()-1; i>=0; i--) {
					Projectile p = r.getProjectiles().get(i);
					Point sp = new Point((int)spaceship.getX(), (int)spaceship.getY());
					Point pp = new Point((int)p.getX(), (int)p.getY());
					if(sp.distance(pp) <= 20) {
						spaceship.setEnergy(spaceship.getEnergy()-p.getDamage());
						r.getProjectiles().remove(p);
						if(spaceship.getEnergy()<=0){
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
				Point sp = new Point((int)spaceship.getX(), (int)spaceship.getY());
				Point pp = new Point((int)p.getX(), (int)p.getY());
				if(sp.distance(pp) <= 30){
					spaceship.setEnergy(spaceship.getEnergy()+p.getAmount());
					if(spaceship.getEnergy()>spaceship.getMaxEnergy())
						spaceship.setEnergy(spaceship.getMaxEnergy());
					packs.remove(p);
				}
			} catch(ClassCastException ex) {
				ProjectilePack p = (ProjectilePack) packs.get(n);
				Point sp = new Point((int)spaceship.getX(), (int)spaceship.getY());
				Point pp = new Point((int)p.getX(), (int)p.getY());
				if(sp.distance(pp) <= 30){
					spaceship.setAmmo(spaceship.getAmmo()+p.getAmount());
					packs.remove(p);
				}
			}
		}
		
		// so much for game logic now let's draw already!
		drawFrame();
	}
 
	private void drawFrame() {
		// code for the drawing goes here
		BufferStrategy bf = this.getBufferStrategy();
		Graphics graphics = null;
 
		try {
			graphics = bf.getDrawGraphics();
 
			// Clear
			graphics.setColor(Color.BLACK);
			graphics.fillRect(0, 0, windowWidth, windowHeight);
			
			for(int i=0; i<packs.size(); i++)
				packs.get(i).draw(graphics);
			
			spaceship.drawOldPos(graphics);
			
			for(int i=0; i<robotSpaceships.size(); i++){
				RobotSpaceship robot = robotSpaceships.get(i);
				robot.draw(graphics);
				robot.drawOldPos(graphics);
				robot.drawBar(graphics);
			}
			
			spaceship.draw(graphics);
			
			Polygon bar = new Polygon();
			bar.addPoint(600, 560);
			bar.addPoint(600, 580);
			bar.addPoint(700, 580);
			bar.addPoint(700, 560);

			Polygon bar2 = new Polygon();
			bar2.addPoint(600, 560);
			bar2.addPoint(600, 580);
			bar2.addPoint(600+(spaceship.getEnergy()), 580);
			bar2.addPoint(600+(spaceship.getEnergy()), 560);
			
			graphics.setColor(Color.WHITE);
			graphics.fillPolygon(bar);
			if(spaceship.getEnergy()>50)
				graphics.setColor(Color.GREEN);
			else if(spaceship.getEnergy()>25)
				graphics.setColor(Color.YELLOW);
			else
				graphics.setColor(Color.RED);
			graphics.fillPolygon(bar2);
			
			// show ammo
			graphics.setColor(Color.WHITE);
			graphics.setFont(new Font("Arial", 20, 30));
			graphics.drawString("Ammo: " + spaceship.getAmmo(), 10, 575);
			graphics.setColor(Color.WHITE);
			graphics.setFont(new Font("Arial", 20, 30));
			graphics.drawString("Rockets: " + spaceship.getRocketsAmmo(), 10, 540);
			
		} finally {
			graphics.dispose();
		}
 
		// Shows the contents
		bf.show();

        Toolkit.getDefaultToolkit().sync();
	}

	@Override
	public void keyPressed(KeyEvent e) { 
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