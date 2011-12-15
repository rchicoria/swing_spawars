package pack;

import game.ContainerBox;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Pack {

	protected double x;
	protected double y;
	protected Color color;
	protected int amount;
	protected int timeRemaining;
	
	int[][] pos = new int[4][2];
	
	public Pack(int amount)
	{
		Random r = new Random();
		this.x = r.nextInt(ContainerBox.getInstance().get_maxX()-100)+50;
		this.y = r.nextInt(ContainerBox.getInstance().get_maxY()-100)+50;
		this.amount = amount;
		this.timeRemaining = 150;
	}
	
	public int getTimeRemaining() {
		return timeRemaining;
	}

	public void setTimeRemaining(int timeRemaining) {
		this.timeRemaining = timeRemaining;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double get_x() {
		return x;
	}

	public void set_x(double x) {
		this.x = x;
	}

	public double get_y() {
		return y;
	}

	public void set_y(double y) {
		this.y = y;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int[][] getPos() {
		return pos;
	}

	public void setPos(int[][] pos) {
		this.pos = pos;
	}

	public void draw(Graphics graphics){
		graphics.setColor(color);
		graphics.fillOval((int)x, (int)y, 30, 30);
		graphics.drawOval((int)x, (int)y, 30, 30);
	}
}
