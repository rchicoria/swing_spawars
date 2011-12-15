/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spaceship;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author alpha
 */
public interface Spaceship_I {

    double getAcceleration();

    float getAngle_degrees();

    float getAngle_inc();

    Color getColor();

    int getEnergy();

    int getMax_energy();

    float getMax_speed();

    ArrayList<double[]> getOldPos();

    float getRadius();

    float[][] getShape();

    float getSpeed();

    float getSpeedX();

    float getSpeedY();

    float getX();

    float getY();

    void setAcceleration(double acceleration);

    void setAngle_degrees(float angle_degrees);

    void setAngle_inc(float angle_inc);

    void setColor(Color color);

    void setEnergy(int energy);

    void setMax_energy(int max_energy);

    void setMax_speed(float max_speed);

    void setOldPos(ArrayList<double[]> oldPos);

    void setRadius(float radius);

    void setShape(float[][] shape);

    void setSpeed(float speed);

    void setSpeedX(float speedX);

    void setSpeedY(float speedY);

    void setX(float x);

    void setY(float y);

}
