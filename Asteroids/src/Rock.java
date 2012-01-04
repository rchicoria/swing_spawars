import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.Random;
 
 
public class Rock {
	private double x;
	private double y;
	private double dx;
	private double dy;
	private int size;
	private double[][] rock = new double[8][2];
 
	private Random rand = new Random();
 
	public Rock(int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
		while(this.dx==0)
			this.dx = rand.nextDouble()*2-1;
		while(this.dy==0)
			this.dy = rand.nextDouble()*2-1;
 
		rock[0][0] = -size;
		rock[0][1] = 0;
		rock[1][0] = -rand.nextDouble()*size;
		rock[1][1] = rand.nextDouble()*size;
		rock[2][0] = 0;
		rock[2][1] = size;
		rock[3][0] = rand.nextDouble()*size;
		rock[3][1] = rand.nextDouble()*size;
		rock[4][0] = size;
		rock[4][1] = 0;
		rock[5][0] = rand.nextDouble()*size;
		rock[5][1] = -rand.nextDouble()*size;
		rock[6][0] = 0;
		rock[6][1] = -size;
		rock[7][0] = -rand.nextDouble()*size;
		rock[7][1] = -rand.nextDouble()*size;
 
	}
 
	public void move() {
		x += dx;
		y += dy;
 
		if(x <= 0) {
			x = Asteroids.windowWidth;
		} else if (x >= Asteroids.windowWidth) {
			x = 0;
		}
		if(y <= 0) {
			y = Asteroids.windowHeight;
		} else if(y >= Asteroids.windowHeight) {
			y = 0;
		}
	}
 
	public void draw(Graphics g) {
		Polygon rockPoly = new Polygon();
 
		for(int n = 0; n < 4; n++) {
			rockPoly.addPoint((int)(rock[n][0] + x), (int)(rock[n][1] + y));
		}
		g.setColor(Color.GREEN);
		g.drawPolygon(rockPoly);
		
		
	}
 
	// getters and setters
	public double getX() {
		return x;
	}
 
	public void setX(int x) {
		this.x = x;
	}
 
	public double getY() {
		return y;
	}
 
	public void setY(int y) {
		this.y = y;
	}
 
	public int getSize() {
		return size;
	}
 
	public void setSize(int size) {
		this.size = size;
	}
 
	public double[][] getRock() {
		return rock;
	}
 
	public void setRock(double[][] rock) {
		this.rock = rock;
	}
 
	public Random getRand() {
		return rand;
	}
 
	public void setRand(Random rand) {
		this.rand = rand;
	}
}