/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package iterator;

import java.util.ArrayList;
import projectile.Projectile;

/**
 *
 * @author alpha
 */
public class ProjectileCollection
{
    private ArrayList<Projectile> projectile;

    public ProjectileCollection()
    {
        projectile = new ArrayList<Projectile>();
    }

    public void add(Projectile p)
    {
        projectile.add(p);
    }
    
    public void remove(Projectile p)
    {
        projectile.remove(p);
    }

    public boolean isEmpty()
    {
        return projectile.isEmpty();
    }

    public IIterator createIterator()
    {
        ProjectileIterator result = new ProjectileIterator();
        return result;
    }

    private class ProjectileIterator implements IIterator
    {
        private int position;
        public boolean hasNext()
        {
            if (position < projectile.size())
                return true;
            else
                return false;
        }
        public Object next()
        {
            if (this.hasNext())
                return projectile.get(position++);
            else
                return null;
        }
    }
}
