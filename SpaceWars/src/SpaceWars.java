import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
 
import javax.swing.JFrame;
 
public class SpaceWars extends JFrame implements KeyListener {
 
	/**
	 * @author Ricardo Lopes
	 * @author Rui Chicória
	 */
 
	public static int windowWidth = 800;
	public static int windowHeight = 600;
	
	private Spaceship spaceship;
	
	private ArrayList<Integer> keys = new ArrayList<Integer>();
 
 
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
 
		while(true) {
			long start = System.currentTimeMillis();
			gameLoop();
			while(System.currentTimeMillis()-start < 5) {
				 //do nothing
			}
		}
	}
	
	/*
	 * Inicialização das variáveis relacionadas com o jogo
	 */
	private void initGame() {
		spaceship = new Spaceship(windowWidth/2, windowHeight/2, 0, 0, 0, 0);
	}
 
	private void gameLoop() {
		// your game logic goes here
		if(keys.contains(new Integer(37))) {
			spaceship.rotate(-0.05);
		} if(keys.contains(new Integer(39))) {
			spaceship.rotate(0.05);
		} if (keys.contains(new Integer(38))) {
			if(spaceship.getVelocity()<=2.0)
				spaceship.setVelocity(spaceship.getVelocity()+0.02);
		} if (keys.contains(new Integer(40))) {
			if(spaceship.getVelocity() > 0) {
				spaceship.setVelocity(spaceship.getVelocity()-0.05);
			}
		}
		 
		if(spaceship.getVelocity() > 0)
			spaceship.move();
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
			spaceship.draw(graphics);
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