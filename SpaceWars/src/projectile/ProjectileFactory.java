/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projectile;

import spaceship.Spaceship;

/**
 * @author alpha
 */
public class ProjectileFactory
{
    public Projectile createPack(String type, Spaceship spaceship, Spaceship target)
    {

        if(type.equals("ammo"))
        {
            return new SimpleProjectile(spaceship.getX(), spaceship.getY(), spaceship.getDirectorRadians(),spaceship.getSimpleProjectilesDamage());
        }
        else if(type.equals("rocket"))
        {
            return new RocketProjectile(spaceship.getX(), spaceship.getY(), spaceship.getDirectorRadians(), target);
        }
        
        return null;
    }
}
