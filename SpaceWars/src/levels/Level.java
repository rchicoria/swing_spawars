/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package levels;

import game.ContainerBox;
import game.Spaceships;
import game.SpaceshipsMemory;
import iterator.IIterator;
import iterator.ProjectileCollection;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;
import pack.Pack;
import pack.PacksFactory;
import projectile.Projectile;
import spaceship.RobotSpaceship;
import views.PackView;
import views.SpaceshipView;

/**
 * 
 * @author alpha
 */
public class Level {
	protected Spaceships spaceships;
	protected SpaceshipsMemory memory;
	protected PacksFactory packsFactory;
	protected ArrayList<Pack> packs;
	protected ProjectileCollection user_projectiles;
	protected ArrayList<Projectile> enemy_projectiles;

	protected int counter = 0;
	protected int fireLimit = 0;
	BufferStrategy bf;
	Graphics graphics;

	protected float GAME_EPSILON_TIME;

	protected int GAME_KEY_LEFT;
	protected int GAME_KEY_RIGHT;
	protected int GAME_KEY_DOWN;
	protected int GAME_KEY_UP;
	protected int GAME_KEY_AMMO;
	protected int GAME_KEY_ROCKET;
	protected int GAME_KEY_HYPERSPACE;

	protected double GAME_ROBOT_SPACESHIP_FIRE_RATE;

	protected double GAME_INVENCIBLE_PACK_RATE;
	protected double GAME_ENERGY_PACK_RATE;
	protected double GAME_AMMO_PACK_RATE;
	protected int GAME_INVENCIBLE_PACK_TIME;
	protected int GAME_ENERGY_PACK_TIME;
	protected int GAME_AMMO_PACK_TIME;

	public Level(BufferStrategy bf) {
		this.bf = bf;
	}

	public String loop(ArrayList<Integer> keys) {
		float timeLeft = 1.0f; // One time-step to begin with
		// Find the earliest collision up to timeLeft among all objects
		
		// Restart if all enemies killed
		if (spaceships.getEnemies().isEmpty())
			return "up";
		else if(spaceships.getPlayer().getEnergy() <= 0)
			return "lost";

		counter++;
		counter %= 12;
		fireLimit++;
		if (fireLimit > 12)
			fireLimit = 12;
		
		// check if user is target of any robot 
		for(int j=0; j<spaceships.getEnemies().size(); j++){
			boolean collision = false;
			RobotSpaceship robot = spaceships.getEnemies().get(j);
			for(int i=0; i<800; i+=1){
				double dx = i*Math.cos(robot.getAngleDegrees()*Math.PI/180);
				double dy = -i*Math.sin(robot.getAngleDegrees()*Math.PI/180);
				int posX = (int)(robot.getX()+dx);
				int posY = (int)(robot.getY()+dy);
	        	Point sp = new Point((int)spaceships.getPlayer().getX(), (int)spaceships.getPlayer().getY());
				Point rp = new Point((int)posX, (int)posY);
				if(sp.distance(rp) <= 200 && i>100) {
					collision=true;
				} else if(sp.distance(rp) <= 60 && i<=200)
					robot.rotate();
				for(int k=0; k<spaceships.getEnemies().size(); k++){
					RobotSpaceship otherRobot = spaceships.getEnemies().get(k); 
					if(otherRobot != robot){
						Point op = new Point((int)otherRobot.getX(), (int)otherRobot.getY());
						if(op.distance(rp) <= 60 && i<=200)
							robot.rotate();
					}
				}		
			}

	        if(collision)
	        	robot.setFireActive(true);
	        else
	        	robot.setFireActive(false);
		}
		
		// Repeat until the one time-step is up
		do {
			// Find the earliest collision up to timeLeft among all objects
			float tMin = timeLeft;

			// Check collision between two spaceships
			for (int i = 0; i < spaceships.getEnemies().size(); i++) {
				Point r1 = new Point((int)spaceships.getEnemies().get(i).getX(),(int)spaceships.getEnemies().get(i).getY());
				Point u = new Point((int)spaceships.getPlayer().getX(),(int)spaceships.getPlayer().getY());
				if(r1.distance(u)<40)
					if (spaceships.getEnemies().get(i).getEnergy() < spaceships.getPlayer().getEnergy()) {
						spaceships.getPlayer().getState().takeDamage(spaceships.getPlayer(), spaceships.getEnemies().get(i).getEnergy());
						spaceships.getEnemies().get(i).setEnergy(-1);
					}
					else{
						spaceships.getPlayer().getState().takeDamage(spaceships.getPlayer(), spaceships.getPlayer().getEnergy());
						spaceships.getEnemies().get(i).setEnergy(spaceships.getEnemies().get(i).getEnergy()-spaceships.getPlayer().getEnergy());
					}
				for (int j = i+1; j < spaceships.getEnemies().size(); j++) {
					Point r2 = new Point((int)spaceships.getEnemies().get(j).getX(),(int)spaceships.getEnemies().get(j).getY());
					if(r1.distance(r2)<40)
						if (spaceships.getEnemies().get(i).getEnergy() < spaceships.getEnemies().get(j).getEnergy()) {
							spaceships.getEnemies().get(j).setEnergy(spaceships.getEnemies().get(j).getEnergy()-spaceships.getEnemies().get(i).getEnergy());
							spaceships.getEnemies().get(i).setEnergy(0);
						}
						else{
							spaceships.getEnemies().get(i).setEnergy(spaceships.getEnemies().get(i).getEnergy()-spaceships.getEnemies().get(j).getEnergy());
							spaceships.getEnemies().get(j).setEnergy(0);
						}
						
				}
			}

			// Update all the spaceships up to the detected earliest collision
			// time tMin,
			// or timeLeft if there is no collision.
			for (int i = 0; i < spaceships.getEnemies().size(); i++) {
				if (counter == 0 || counter == 6) {
					spaceships.getEnemies().get(i).savePos();
				}
				spaceships.getEnemies().get(i).update(tMin);
			}
			spaceships.getPlayer().update(tMin);
			timeLeft -= tMin; // Subtract the time consumed and repeat
		} while (timeLeft > GAME_EPSILON_TIME); // Ignore remaining time less
												// than threshold

		// Rotate Left/Right
		if (keys.contains(GAME_KEY_LEFT))
			spaceships.getPlayer().rotate(1);
		if (keys.contains(GAME_KEY_RIGHT))
			spaceships.getPlayer().rotate(-1);
		// Speed Up/Down
		if (keys.contains(GAME_KEY_UP))
			spaceships.getPlayer().speedUp();
		if (keys.contains(GAME_KEY_DOWN))
			spaceships.getPlayer().speedDown();
		// Fire Simple Projectiles
		if (keys.contains(GAME_KEY_AMMO)) {
			Projectile p = spaceships.getPlayer().fireProjectile(null);
			if (p != null)
				user_projectiles.add(p);
		}
		// Fire Rockets
		if (keys.contains(KeyEvent.VK_C) && fireLimit == 12) {
			RobotSpaceship currentTarget = null;
			double min = 800;
			for (int j = 0; j < spaceships.getEnemies().size(); j++) {
				RobotSpaceship robot = spaceships.getEnemies().get(j);
				int posX = (int) spaceships.getPlayer().getX();
				int posY = (int) spaceships.getPlayer().getY();
				Point sp = new Point((int) posX, (int) posY);
				Point rp = new Point((int) robot.getX(), (int) robot.getY());
				if (sp.distance(rp) <= min) {
					min = sp.distance(rp);
					currentTarget = robot;
				}
			}
			System.out.println(currentTarget);
			Projectile p = spaceships.getPlayer().fireProjectile(currentTarget);
			if (p != null)
				user_projectiles.add(p);
			fireLimit = 0;
		}
		if (keys.contains(KeyEvent.VK_S)) {
			if (spaceships.getPlayer().hyper()) {
				memory.setMemento(spaceships.saveMemento());
				System.out.println("Saved Memento!");
			}
		}

		if (keys.contains(KeyEvent.VK_R)) {
			spaceships.restoreMemento(memory.getMemento());
		}

		// guarda as posições anteriores
		if (counter == 0 || counter == 6)
			spaceships.getPlayer().savePos();

		// movimento das naves robot
		for (int i = 0; i < spaceships.getEnemies().size(); i++) {
			if (counter == 0) {
				spaceships.getEnemies().get(i).rotate();
			}
			spaceships.getEnemies().get(i).update(0);

			if (counter == 0 || counter == 6)
				spaceships.getEnemies().get(i).savePos();

			if (spaceships.getEnemies().get(i).isFireActive()) {
				Random random = new Random();
				if (random.nextDouble() < GAME_ROBOT_SPACESHIP_FIRE_RATE)
					enemy_projectiles.add(spaceships.getEnemies().get(i)
							.fireProjectile(null));
			}
		}

		// decrement pack's remaining time
		for (int n = 0; n < packs.size(); n++) {
			Pack p = packs.get(n);
			p.setTimeRemaining(p.getTimeRemaining() - 1);
			if (p.getTimeRemaining() <= 0)
				packs.remove(p);
		}

		// create packs
		Random random = new Random();
		double prob = random.nextDouble();
		if (prob < GAME_INVENCIBLE_PACK_RATE)
			packs.add(packsFactory.createPack("invencible",
					GAME_INVENCIBLE_PACK_TIME));
		else if (prob < GAME_ENERGY_PACK_RATE)
			packs.add(packsFactory.createPack("health", GAME_ENERGY_PACK_TIME));
		else if (prob < GAME_AMMO_PACK_RATE)
			packs.add(packsFactory.createPack("ammo", GAME_AMMO_PACK_TIME));

		// move user projectiles and check for colisions
		IIterator it = user_projectiles.createIterator();
		while (it.hasNext()) {
			Projectile s = (Projectile) it.next();
			s.move();
			for (int n = 0; n < spaceships.getEnemies().size(); n++) {
				RobotSpaceship r = spaceships.getEnemies().get(n);
				Point sp = new Point((int) s.get_x(), (int) s.get_y());
				Point rp = new Point((int) r.getX(), (int) r.getY());
				if (sp.distance(rp) <= 20) {
					r.takeDamage(s.getDamage());
					user_projectiles.remove(s);
					if (r.getEnergy() <= 0)
						spaceships.deleteEnemy(n);
				}
			}
		}

		// move enemy projectiles and check for colisions
		for (int i = 0; i < enemy_projectiles.size(); i++) {
			Projectile s = enemy_projectiles.get(i);
			s.move();

			Point sp = new Point((int) s.get_x(), (int) s.get_y());
			Point rp = new Point((int) spaceships.getPlayer().getX(),
					(int) spaceships.getPlayer().getY());
			if (sp.distance(rp) <= 20) {
				spaceships.getPlayer().takeDamage(s.getDamage());
				user_projectiles.remove(s);
			}
		}


		// check for collision between user and packs
		for (int n = 0; n < packs.size(); n++) {
			Pack p = packs.get(n);
			if (p.intersect((int) spaceships.getPlayer().getX(),
					(int) spaceships.getPlayer().getY())) {
				p.activate(spaceships.getPlayer());
				packs.remove(p);
			}
		}
		
		for(int i=spaceships.getEnemies().size()-1; i>=0;i--)
			if(spaceships.getEnemies().get(i).getEnergy()<=0)
				spaceships.getEnemies().remove(i);

		return "ok";
	}

	public void draw() {
		try {
			graphics = bf.getDrawGraphics();

			// Clear
			ContainerBox.getInstance().draw(graphics);

			SpaceshipView spaceshipView;
			PackView packView;

			// Draw packs
			for (int i = 0; i < packs.size(); i++) {
				packView = new PackView(packs.get(i));
				packView.draw(graphics);
			}

			// Draw robots
			for (int i = 0; i < spaceships.getEnemies().size(); i++) {
				spaceshipView = new SpaceshipView(spaceships.getEnemies()
						.get(i));
				spaceshipView.draw(graphics);
			}

			// Draw User Spaceships
			spaceshipView = new SpaceshipView(spaceships.getPlayer());
			spaceshipView.draw(graphics);

			// Draw User Projectiles
			IIterator it = user_projectiles.createIterator();
			while (it.hasNext()) {
				Projectile p = (Projectile) it.next();
				p.draw(graphics);
			}

			// Draw Enemy Projectiles
			for (int i = 0; i < enemy_projectiles.size(); i++) {
				enemy_projectiles.get(i).draw(graphics);
			}
			
			Polygon toolBar = new Polygon();
			toolBar.addPoint(0, 470);
			toolBar.addPoint(0, 600);
			toolBar.addPoint(800, 600);
			toolBar.addPoint(800, 470);
			graphics.setColor(Color.BLACK);
			graphics.fillPolygon(toolBar);
			
			Polygon line = new Polygon();
			line.addPoint(0, 470);
			line.addPoint(0, 475);
			line.addPoint(800, 475);
			line.addPoint(800, 470);
			graphics.setColor(Color.DARK_GRAY);
			graphics.fillPolygon(line);
			
			// Draw robots
			for (int i = 0; i < spaceships.getEnemies().size(); i++) {
				spaceshipView = new SpaceshipView(spaceships.getEnemies()
						.get(i));
				spaceshipView.drawSimpleShape(graphics);
			}

			// Draw User Spaceships
			spaceshipView = new SpaceshipView(spaceships.getPlayer());
			spaceshipView.drawSimpleShape(graphics);

			// Draw bar
			Polygon bar = new Polygon();
			bar.addPoint(600, 530);
			bar.addPoint(600, 550);
			bar.addPoint(700, 550);
			bar.addPoint(700, 530);

			Polygon bar2 = new Polygon();
			bar2.addPoint(600, 530);
			bar2.addPoint(600, 550);
			bar2.addPoint(600 + (spaceships.getPlayer().getEnergy()), 550);
			bar2.addPoint(600 + (spaceships.getPlayer().getEnergy()), 530);

			graphics.setColor(Color.DARK_GRAY);
			graphics.fillPolygon(bar);
			if (spaceships.getPlayer().getEnergy() > 50)
				graphics.setColor(Color.GREEN);
			else if (spaceships.getPlayer().getEnergy() > 25)
				graphics.setColor(Color.YELLOW);
			else
				graphics.setColor(Color.RED);
			graphics.fillPolygon(bar2);

			// show ammo
			graphics.setColor(Color.LIGHT_GRAY);
			graphics.setFont(new Font("Arial", 20, 30));
			graphics.drawString("Ammo: " + spaceships.getPlayer().getAmmo(),
					300, 560);
			graphics.setColor(Color.LIGHT_GRAY);
			graphics.setFont(new Font("Arial", 20, 30));
			graphics.drawString("Rockets: "
					+ spaceships.getPlayer().getrockets(), 300, 525);

		} finally {
			graphics.dispose();
		}

		// Shows the contents
		bf.show();

		Toolkit.getDefaultToolkit().sync();
	}
}
