import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;
 
import javax.swing.JFrame;
 
public class Asteroids extends JFrame implements KeyListener {
 
	/**
	 * @author Georgi Khomeriki
	 */
 
	public static int windowWidth = 800;
	public static int windowHeight = 600;
 
	private Ship ship;
	private int fireLimit = 0;
	private ArrayList<Rock> rocks;
	private int level = 1;
 
	private Random rand = new Random();
 
	private ArrayList<Integer> keys = new ArrayList<Integer>();
 
	public static void main(String[] args) {
		new Asteroids();
	}
 
	public Asteroids() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(windowWidth, windowHeight);
		this.setResizable(false);
		this.setLocation(100, 100);
		this.setVisible(true);
 
		this.addKeyListener(this);
 
		this.createBufferStrategy(2);
 
		initGame();
 
		while(true) {
			long start = System.currentTimeMillis();
			gameLoop();
			while(System.currentTimeMillis()-start < 5) {
				 //do nothing
			}
		}
	}
 
	private void initGame() {
		// all you're game variables should be initialized here
		ship = new Ship(windowWidth/2, windowHeight/2, 0, 0);
 
		rocks = new ArrayList<Rock>();
 
		for(int n = 0; n < level * 7; n++) {
			rocks.add(new Rock(rand.nextInt(windowWidth), rand.nextInt(windowHeight), 10 + rand.nextInt(40)));
		}
	}
 
	private void gameLoop() {
		// your game logic goes here
		if(keys.contains(new Integer(37))) {
			ship.rotate(-0.05);
		} if(keys.contains(new Integer(39))) {
			ship.rotate(0.05);
		} if (keys.contains(new Integer(38))) {
			if(ship.getSpeed()<=2.0)
				ship.setSpeed(ship.getSpeed()+0.02);
		} if (keys.contains(new Integer(40))) {
			if(ship.getSpeed() > 0) {
				ship.setSpeed(ship.getSpeed()-0.05);
			}
		} if (keys.contains(new Integer(32))) {
			if(fireLimit==0) {
				ship.fire();
				fireLimit = 5;
			} else {
				fireLimit--;
			}
		}
 
		if(ship.getSpeed() > 0)
			ship.move();
 
		// check if the ship goes through any walls, if so respawn it on the other side 
		if(ship.getX() > windowWidth + 20) {
			ship.set(20,ship.getY());
		} else if(ship.getX() < -20) {
			ship.set(windowWidth-20, ship.getY());
		}
		if(ship.getY() > windowHeight + 20) {
			ship.set(ship.getX(), 20);
		} else if(ship.getY() < 0) {
			ship.set(ship.getX(), windowHeight);
		}
 
		// check for collision between rocks and bullets
		for(Bullet b:ship.getBullets()) {
			for(int n = 0; n < rocks.size(); n++) {
				Rock r = rocks.get(n);
				Point bp = new Point(b.getX(), b.getY());
				Point rp = new Point((int)r.getX(), (int)r.getY());
				if(bp.distance(rp) <= r.getSize()) {
					rocks.remove(n);
					if(r.getSize() > 20) {
						rocks.add(new Rock((int)r.getX(), (int)r.getY(), r.getSize()-10));
						rocks.add(new Rock((int)r.getX(), (int)r.getY(), r.getSize()-10));
						rocks.add(new Rock((int)r.getX(), (int)r.getY(), r.getSize()-10));
					}
				}
			}
		}
 
		// check for collision between ship and rocks
		for(int n = 0; n < rocks.size(); n++) {
			Rock r = rocks.get(n);
			Point sp = new Point(ship.getX(), ship.getY());
			Point rp = new Point((int)r.getX(), (int)r.getY());
			if(sp.distance(rp) <= r.getSize() + 20) {
				initGame();
			}
		}
 
		// check if all rocks have been demolished
		if(rocks.size() <= 0) {
			level++;
			initGame();
		}
 
		// so much for game logic now let's draw already!
		drawFrame();
	}
 
	private void drawLevel(Graphics g) {
		g.setColor(Color.GREEN);
		g.setFont(new Font("Arial", 20, 30));
		g.drawString("level: " + level, 10, 50);
	}
 
	private void drawFrame() {
		// code for the drawing goes here
		BufferStrategy bf = this.getBufferStrategy();
		Graphics g = null;
 
		try {
			g = bf.getDrawGraphics();
 
			// clear the back buffer (just draw a big black rectangle over it)
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, windowWidth, windowHeight);
 
			// move and draw all the rocks
			for(int n = 0; n < rocks.size(); n++) {
				Rock r = rocks.get(n);
				r.move();
				r.draw(g);
			}
 
			// draw the ship
			ship.draw(g);
			// draw the current level
			drawLevel(g);
 
		} finally {
			// It is best to dispose() a Graphics object when done with it.
			g.dispose();
		}
 
		// Shows the contents of the backbuffer on the screen.
		bf.show();
 
        //Tell the System to do the Drawing now, otherwise it can take a few extra ms until 
        //Drawing is done which looks very jerky
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
	public void keyTyped(KeyEvent e) {
		// not used
	}
}