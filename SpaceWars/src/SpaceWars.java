import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
 
import javax.swing.JFrame;
 
public class SpaceWars extends JFrame {
 
	/**
	 * @author Ricardo Lopes
	 * @author Rui Chicória
	 */
 
	public static int windowWidth = 800;
	public static int windowHeight = 600;
 
 
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
	}
 
	private void gameLoop() {
		// your game logic goes here
 
		// so much for game logic now let's draw already!
		drawFrame();
	}
 
	private void drawFrame() {
		// code for the drawing goes here
		BufferStrategy bf = this.getBufferStrategy();
		Graphics g = null;
 
		try {
			g = bf.getDrawGraphics();
 
			// Clear
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, windowWidth, windowHeight);
		} finally {
			g.dispose();
		}
 
		// Shows the contents
		bf.show();

        Toolkit.getDefaultToolkit().sync();
	}
}