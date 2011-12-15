/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spaceship;

import java.awt.Color;
import java.util.ArrayList;

public class Spaceship implements Spaceship_I
{

    float x, y;                         // Spaceship's center x and y
    float angle_degrees;                // Spaceship's direction
    float angle_inc;                    // Spaceship's direction increment
    float speed;                        // Spaceship's speed
    float speedX, speedY;               // Spaceship's speed per step in x and y
    float max_speed;                    // Spaceship's speed limit
    double acceleration;                // Spaceship's acceleration
    float radius;                       // Spaceship's radius
    Color color;                        // Spaceship's color
    float[][] shape = new float[8][2];
    protected int max_energy;
    protected int energy;


    ArrayList<double[]> oldPos = new ArrayList<double[]>();


   public Spaceship(float x, float y, float radius, float speed, float angleInDegree, Color color, int energy)
   {
        this.x = x;
        this.y = y;
        this.angle_degrees = angleInDegree;
        this.speed = speed;
        this.speedX = (float)(speed * Math.cos(Math.toRadians(angleInDegree)));
        this.speedY = (float)(-speed * Math.sin(Math.toRadians(angleInDegree)));
        this.radius = radius;
        this.color = color;
        this.max_speed = 10;
        this.acceleration = 0.2;
        this.angle_inc = 10;
        this.energy = energy;
        this.max_energy = energy;
   }

   // Getters and Setters

    public double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    public float getAngle_degrees() {
        return angle_degrees;
    }

    public void setAngle_degrees(float angle_degrees) {
        this.angle_degrees = angle_degrees;
    }

    public float getAngle_inc() {
        return angle_inc;
    }

    public void setAngle_inc(float angle_inc) {
        this.angle_inc = angle_inc;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getMax_energy() {
        return max_energy;
    }

    public void setMax_energy(int max_energy) {
        this.max_energy = max_energy;
    }

    public float getMax_speed() {
        return max_speed;
    }

    public void setMax_speed(float max_speed) {
        this.max_speed = max_speed;
    }

    public ArrayList<double[]> getOldPos() {
        return oldPos;
    }

    public void setOldPos(ArrayList<double[]> oldPos) {
        this.oldPos = oldPos;
    }


    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float[][] getShape() {
        return shape;
    }

    public void setShape(float[][] shape) {
        this.shape = shape;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
