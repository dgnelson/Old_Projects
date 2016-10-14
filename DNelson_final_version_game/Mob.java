import java.util.Random;
/**
 *   This class is an object that has a string name and an integer difficulty assigned to it. It is
 *   practically identical to the weapon class except for that the integers for this class are
 *   used to determine battle outcomes in a different way. The mobs name and difficulty are randomly
 *   generated when the object is initialized.
 */
public class Mob
{
    String theName;
    int difficulty;
    public Mob(String name)
    {
        //sets name and difficulty when initializing
        theName = name;
        difficulty = setDifficulty();
    }
    
    String getName()
    {
        return theName;
    }
    
    int getDifficulty()
    {
        return difficulty;
    }
    
    int setDifficulty()
    {
        //sets difficulty based on name string
        if (theName.equals("goblin"))
            return 8;
        if (theName.equals("lion"))
            return 8;
        if (theName.equals("cyclopes"))
            return 10;
        if (theName.equals("cerberus"))
            return 12;
        if (theName.equals("harpy"))
            return 10;
        if (theName.equals("sphinx"))
            return 12;
        if (theName.equals("gorgon"))
            return 10;
        if (theName.equals("fury"))
            return 10;
        if (theName.equals("hydra"))
            return 12;
        if (theName.equals("giant spider"))
            return 8;
        return 0;
    }
    
    static String randomMob()
    {
        //randomly picks mob name
        Random rand = new Random();
        int mob = rand.nextInt(10);
        if(mob==0)
            return "goblin";
        if(mob==1)
            return "lion";
        if(mob==2)
            return "cyclopes";
        if(mob==3)
            return "cerberus";
        if(mob==4)
            return "harpy";
        if(mob==5)
            return "sphinx";
        if(mob==6)
            return "gorgon";
        if(mob==7)
            return "fury";
        if(mob==8)
            return "hydra";
        if(mob==9)
            return "giant spider";
        return "";
    }
}
