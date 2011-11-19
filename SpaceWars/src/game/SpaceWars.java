package game;
import java.awt.Color;
import java.awt.Graphics;
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

import spaceship.*;
 
@SuppressWarnings("serial")
public class SpaceWars extends JFrame implements KeyListener {
 
	/**
	 * @author Ricardo Lopes
	 * @author Rui Chicória
	 */
 
	public static int windowWidth = 800;
	public static int windowHeight = 600;
	
	public static int N_ROBOTS = 4;
	double robotPos[][] = new double[N_ROBOTS][3];
	
	private UserSpaceship spaceship;
	private ArrayList<RobotSpaceship> robotSpaceships = new ArrayList<RobotSpaceship>();
	
	private ArrayList<Integer> keys = new ArrayList<Integer>();
	private int counter = 0;
 
 
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
				counter%=6;
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
		spaceship = new UserSpaceship(windowWidth/2, windowHeight/2, 0, 0, 0, 0.2, 10, 0.2);
		
		robotPos[0][0] = 70; 
		robotPos[0][1] = 70;
		robotPos[0][2] = -0.75;
		robotPos[1][0] = windowWidth-70;
		robotPos[1][1] = 70;
		robotPos[1][2] = 0.75;
		robotPos[2][0] = 70;
		robotPos[2][1] = windowHeight-70;
		robotPos[2][2] = -2.25;
		robotPos[3][0] = windowWidth-70;
		robotPos[3][1] = windowHeight-70;
		robotPos[3][2] = 2.25;
		
		for(int i=0; i<N_ROBOTS; i++){
			robotSpaceships.add(new RobotSpaceship(robotPos[i][0], robotPos[i][1], 0,robotPos[i][2],0,0.2,5,0.2));
		}
	}
 
	private void gameLoop() {
		// your game logic goes here
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
		}
		
		if(spaceship.getVelocity() > 0)
			spaceship.move();
		
		if(counter==0)
			spaceship.savePos();
		
		for(int i=0; i<N_ROBOTS; i++){
			robotSpaceships.get(i).incrementVelocity();
			robotSpaceships.get(i).incrementAngVelocity();
			if(counter == 0)
				robotSpaceships.get(i).changeDirection();
			robotSpaceships.get(i).move();
			if(counter==0)
				robotSpaceships.get(i).savePos();
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
			for(int i=0; i<spaceship.getOldPos().size()-1; i++){
				int x = (int)spaceship.getOldPos().get(i)[0];
				int y = (int)spaceship.getOldPos().get(i)[1];
		        graphics.setColor(spaceship.getColor());
		        graphics.drawOval(x, y, 5, 5);
		        graphics.fillOval(x, y, 5, 5);
			}
			for(int i=0; i<N_ROBOTS; i++){
				RobotSpaceship robot = robotSpaceships.get(i);
				for(int j=0; j<robot.getOldPos().size()-1; j++){
					int x = (int)robot.getOldPos().get(j)[0];
					int y = (int)robot.getOldPos().get(j)[1];
			        graphics.setColor(robot.getColor());
			        graphics.drawOval(x, y, 5, 5);
			        graphics.fillOval(x, y, 5, 5);
				}
				robotSpaceships.get(i).draw(graphics);
			}
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