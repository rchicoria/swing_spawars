
package game;

import java.util.ArrayList;
import spaceship.RobotSpaceship;
import spaceship.UserSpaceship;

public class Memento {

    private UserSpaceship player;
    private ArrayList<RobotSpaceship> enemies = new ArrayList<RobotSpaceship>();

    Memento(UserSpaceship player, ArrayList<RobotSpaceship> enemies)
    {
        this.player = new UserSpaceship(player.getX(),
                                        player.getY(),
                                        player.getRadius(),
                                        player.getSpeed(),
                                        player.getDirection(),
                                        player.getColor(),
                                        player.getEnergy(),
                                        player.getAmmo(),
                                        player.getAmmoDamage(),
                                        player.getrockets(),
                                        player.getHyperspace());

        for(int i=0; i<enemies.size(); i++)
            this.enemies.add(new RobotSpaceship(enemies.get(i).getX(),
                                                enemies.get(i).getY(),
                                                enemies.get(i).getRadius(),
                                                enemies.get(i).getSpeed(),
                                                enemies.get(i).getDirection(),
                                                enemies.get(i).getColor(),
                                                enemies.get(i).getEnergy(),
                                                enemies.get(i).getAmmoDamage()));
    }

    public ArrayList<RobotSpaceship> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<RobotSpaceship> enemies) {
        this.enemies = enemies;
    }

    public UserSpaceship getPlayer() {
        return player;
    }

    public void setPlayer(UserSpaceship player) {
        this.player = player;
    }
}
