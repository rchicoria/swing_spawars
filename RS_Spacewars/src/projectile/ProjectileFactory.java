/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projectile;

/**
 *
 * @author alpha
 */
public class ProjectileFactory
{
    public Projectile createPack(String type)
    {

        if(type.equals("ammo"))
        {
            return new SimpleProjectile();
        }
        else if(type.equals("rocket"))
        {
            return new RocketProjectile();
        }
        
        return null;
    }
}
