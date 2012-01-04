/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class LifeBarView {

    int x;
    int y;
    int value;
    int max;

    public LifeBarView(int x, int y, int value, int max)
    {
        this.x = x;
        this.y = y;
        this.value = value;
        this.max = max;
    }

    public void draw(Graphics graphics)
    {
        Polygon bar = new Polygon();
        bar.addPoint((int)(x-20), (int)(y+30));
        bar.addPoint((int)(x-20+(float)value/(float)max*40), (int)(y+30));
        bar.addPoint((int)(x-20), (int)(y+32));
        bar.addPoint((int)(x-20+(float)value/(float)max*40), (int)(y+32));
        if((float)value/(float)max>0.50)
            graphics.setColor(Color.green);
        else if((float)value/(float)max>0.25)
            graphics.setColor(Color.yellow);
        else
            graphics.setColor(Color.RED);
        graphics.fillPolygon(bar);
    }
}
