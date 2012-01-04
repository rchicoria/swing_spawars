package views;

import java.awt.Graphics;
import pack.Pack;

public class PackView {
    
    Pack pack;

    public PackView(Pack p)
    {
        this.pack = p;
    }

    public void draw(Graphics graphics){
            graphics.setColor(pack.getColor());
            graphics.fillOval(pack.getCoords().x,
                                pack.getCoords().y,
                                pack.getRadius(),
                                pack.getRadius());
	}
}
