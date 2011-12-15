package pack;
import java.awt.Color;
/**
 *
 * @author alpha
 */
public class PacksFactory
{
    public Pack createPack(String type)
    {
        Pack pack = null;

        if(type.equals("health"))
        {
            pack = new Pack(Color.WHITE,3);
            pack.setStrategy(new HealthStrategy());
        }
        else if(type.equals("ammo"))
        {
            pack = new Pack(Color.YELLOW,3);
            pack.setStrategy(new AmmoStrategy());
        }
        else if(type.equals("rockets"))
        {
            pack = new Pack(Color.ORANGE,3);
            pack.setStrategy(new RocketStrategy());
        }
        else if(type.equals("invencible"))
        {
            pack = new Pack(Color.GREEN,2);
            pack.setStrategy(new InvencibleStrategy());
        }
        
        return pack;
    }
}
