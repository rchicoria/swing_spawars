/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projectile;

import game.ContainerBox;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import spaceship.Spaceship;

/**
 *
 * @author alpha
 */
public class RocketProjectile extends Projectile {

    private double [][] pos = new double[6][2];
    private Spaceship target;
    int velocity;
    int remainingTime;
    
    public RocketProjectile(float x, float y, double direction, Spaceship target)
    {
        super(x, y, direction);
        this.target = target;
        this.damage = 100;
        this.velocity = 10;
        this.remainingTime = 150;
        set(x,y);
    }

    /*
	 * Desenha o projectil
	 */
    @Override
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
    @Override
	public void move() {
        if(target == null)
            return;
        
        double dx = 0;
		double dy = 0;
		
        Point pp = new Point((int) pos[4][0], (int) pos[4][1]);
		Point rp = new Point((int) target.getX(), (int) target.getY());
		double dist = pp.distance(rp);

		double nextPosX = pos[4][0]+dist*Math.cos(direction);
		double nextPosY = pos[4][1]+dist*Math.sin(direction);
		Point p = new Point((int) nextPosX, (int) nextPosY);
		
		double nextPosXL = pos[4][0]+dist*Math.cos(direction+Math.PI/4);
		double nextPosYL = pos[4][1]+dist*Math.sin(direction+Math.PI/4);
		Point pl = new Point((int) nextPosXL, (int) nextPosYL);
		
		double nextPosXR = pos[4][0]+dist*Math.cos(direction-Math.PI/4);
		double nextPosYR = pos[4][1]+dist*Math.sin(direction-Math.PI/4);
		Point pr = new Point((int) nextPosXR, (int) nextPosYR);
		
		double nextDist = p.distance(rp);
		double nextDistL = pl.distance(rp);
		double nextDistR = pr.distance(rp);
		
		if(nextDist < nextDistL && nextDist < nextDistR){
			dx = velocity*Math.cos(direction);
			dy = velocity*Math.sin(direction);
		}
		else if(nextDistL < nextDistR){
			dx = velocity*Math.cos(direction+Math.PI/4);
			dy = velocity*Math.sin(direction+Math.PI/4);
			direction += Math.PI/4;
		}
		else{
			dx = velocity*Math.cos(direction-Math.PI/4);
			dy = velocity*Math.sin(direction-Math.PI/4);
			direction -= Math.PI/4;
		}
		
		x = (float) (pos[4][0]+dx);
		y = (float) (pos[4][1]+dy);
		
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
		
		set(x, y);
		
		remainingTime--;	

    }

	/*
	 * Define a forma e posição da nave (os seus pontos)
	 */
	public void set(double x, double y) {
		pos[0][0] = (double)x-10;
		pos[0][1] = (double)y-5;
		pos[1][0] = (double)x+10;
		pos[1][1] = (double)y-5;
		pos[2][0] = (double)x+10;
		pos[2][1] = (double)y+5;
		pos[3][0] = (double)x-10;
		pos[3][1] = (double)y+5;
		pos[4][0] = x;
		pos[4][1] = y;
		pos[5][0] = x;
		pos[5][1] = (double)y+5;

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
