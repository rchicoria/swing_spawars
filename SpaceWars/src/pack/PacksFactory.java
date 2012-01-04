package pack;
import game.Commons;
import java.awt.Color;
/**
 *
 * @author alpha
 */
public class PacksFactory implements Commons
{
    public Pack createPack(String type, int time)
    {
        Pack pack = null;

        if(type.equals("health"))
        {
            pack = new Pack(Color.GREEN, time);
            pack.setStrategy(new HealthStrategy());
        }
        else if(type.equals("ammo"))
        {
            pack = new Pack(Color.WHITE, time);
            pack.setStrategy(new AmmoStrategy());
        }
        else if(type.equals("rockets"))
        {
            pack = new Pack(Color.ORANGE,3000);
            pack.setStrategy(new RocketStrategy());
        }
        else if(type.equals("invencible"))
        {
            pack = new Pack(Color.YELLOW,2000);
            pack.setStrategy(new InvencibleStrategy());
        }
        
        return pack;
    }
}
