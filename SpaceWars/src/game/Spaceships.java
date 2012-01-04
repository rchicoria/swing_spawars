
package game;

import java.util.ArrayList;
import spaceship.RobotSpaceship;
import spaceship.UserSpaceship;

public class Spaceships implements Commons
{
    private UserSpaceship player;
    private ArrayList<RobotSpaceship> enemies;

    public Spaceships() {
        enemies = new ArrayList<RobotSpaceship>();
    }

    public ArrayList<RobotSpaceship> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<RobotSpaceship> enemies) {
        this.enemies = enemies;
    }

    public void deleteEnemy(int i)
    {
        this.enemies.remove(i);
    }

    public void addEnemy(RobotSpaceship r)
    {
        this.enemies.add(r);
    }

    public UserSpaceship getPlayer()
    {
        return player;
    }

    public void setPlayer(UserSpaceship player) {
        this.player = player;
    }

    public Memento saveMemento()
    {
        return new Memento(this.player, this.enemies);
    }

    public void restoreMemento(Memento m)
    {
        if(m!=null)
        {
            this.player = m.getPlayer();
            this.enemies = m.getEnemies();
        }
    }
}
