package projectile;

import game.ContainerBox;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

import spaceship.RobotSpaceship;

public class GuidedProjectile extends Projectile {
	
	double [][] pos = new double[6][2];
	
	int velocity;
	RobotSpaceship target;
	int remainingTime;
	
	public GuidedProjectile(double x, double y, double direction, int damage, RobotSpaceship target) {
		super(x, y, direction, damage);
		this.target = target;
		this.set(x, y);
		this.velocity = 10;
		this.remainingTime = 150;
		// TODO Auto-generated constructor stub
	}
	
	public int getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(int remainingTime) {
		this.remainingTime = remainingTime;
	}

	public double[][] getPos() {
		return pos;
	}

	public void setPos(double[][] pos) {
		this.pos = pos;
	}

	public int getVelocity() {
		return velocity;
	}

	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}

	public RobotSpaceship getTarget() {
		return target;
	}

	public void setTarget(RobotSpaceship target) {
		this.target = target;
	}
	
	/*
	 * Desenha o projectil
	 */
	public void draw(Graphics graphics){
		Polygon shape  = new Polygon();
		for(int i=0; i<4; i++){
			shape.addPoint((int)pos[i][0], (int)pos[i][1]);
		}
		graphics.setColor(Color.WHITE);
		graphics.fillPolygon(shape);
	}
	
	/*
	 * Roda a nave segundo um dado ângulo
	 */
	public void rotate(double angle) {
		this.direction += angle;
		for(int i=0; i<4; i++){
			pos[i] = movePoint(pos[i], pos[4], angle);
		}
		pos[5] = movePoint(pos[5], pos[4], angle);
	}
	
	/*
	 * Move todos os pontos da nave espacial à velocidade da mesma
	 */
	public void move() {
                if(target == null)
                    return;
		// guarda a shape actual
		double [][] temp = new double[6][2];
		for(int i=0; i<6; i++){
			temp[i][0] = pos[i][0];
			temp[i][1] = pos[i][1];
		}
		double [][] front = new double[6][2];
		for(int i=0; i<6; i++){
			front[i][0] = pos[i][0];
			front[i][1] = pos[i][1];
		}
		double [][] left = new double[6][2];
		double [][] right = new double[6][2];

		double dxfront = velocity*Math.cos(direction+0.5 * Math.PI);
		double dyfront = velocity*Math.sin(direction+0.5 * Math.PI);
		for(int i = 0; i < pos.length; i++) {
			front[i][0] += dxfront;
			front[i][1] += dyfront;
		}
		Point pp1 = new Point((int)front[5][0], (int)front[5][1]);
		Point rp = new Point((int)target.get_x(), (int)target.get_y());
		double dist1 = pp1.distance(rp);
		
		// experimenta rodar para um lado e vê se está mais perto
		rotate(0.2);
		for(int i=0; i<6; i++){
			left[i][0] = pos[i][0];
			left[i][1] = pos[i][1];
		}
		double dxleft = velocity*Math.cos(direction+0.5 * Math.PI);
		double dyleft = velocity*Math.sin(direction+0.5 * Math.PI);
		for(int i = 0; i < pos.length; i++) {
			left[i][0] += dxleft;
			left[i][1] += dyleft;
		}
		Point pp2 = new Point((int)left[5][0], (int)left[5][1]);
		double dist2 = pp2.distance(rp);
		
		if(dist1 < dist2){
			//System.out.println("roda1");
			pos=temp;
			//experimenta rodar para o outro lado e vê se está mais perto
			rotate(-0.2);
			for(int i=0; i<6; i++){
				right[i][0] = pos[i][0];
				right[i][1] = pos[i][1];
			}
			double dxright = velocity*Math.cos(direction+0.5 * Math.PI);
			double dyright = velocity*Math.sin(direction+0.5 * Math.PI);
			for(int i = 0; i < pos.length; i++) {
				right[i][0] += dxright;
				right[i][1] += dyright;
			}
			pp2 = new Point((int)right[5][0], (int)right[5][1]);
			dist2 = pp2.distance(rp);

			if(dist1 < dist2)
				pos=front;
			else 
				pos = right;
		} else
			pos=left;
		
		x = (int)pos[4][0];
		y = (int)pos[4][1];
 
		if(x <= 0) {
			x = ContainerBox.getInstance().get_maxX();
		} else if (x >= ContainerBox.getInstance().get_maxX()+20) {
			x = 0;
		}
		if(y <= 0) {
			y = ContainerBox.getInstance().get_maxY();
		} else if(y >= ContainerBox.getInstance().get_maxY()) {
			y = 0;
		}
		set(x,y);
	}
	
	/*
	 * Define a forma e posição da nave (os seus pontos)
	 */
	public void set(double x, double y) {
		pos[0][0] = (double)x-5;
		pos[0][1] = (double)y-10;
		pos[1][0] = (double)x+5;
		pos[1][1] = (double)y-10;
		pos[2][0] = (double)x+5;
		pos[2][1] = (double)y+10;
		pos[3][0] = (double)x-5;	
		pos[3][1] = (double)y+10;
		pos[4][0] = x;
		pos[4][1] = y;
		pos[5][0] = x;
		pos[5][1] = (double)y+10;
	 
		for(int i = 0; i < 6; i++) {
			pos[i] = movePoint(pos[i], pos[4], direction);
		}
	}
	
	/*
	 * Move um ponto segundo a origem a partir de um dado ângulo
	 */
	private double[] movePoint(double[] point, double[] origin, double angle){
		double X = (double) (origin[0] + ((point[0] - origin[0]) * Math.cos(angle) -
			(point[1] - origin[1]) * Math.sin(angle)));
		double Y = (double) (origin[1] + ((point[0] - origin[0]) * Math.sin(angle) +
			(point[1] - origin[1]) * Math.cos(angle)));
		double[] res = {X, Y};
		return res;
	}

}
