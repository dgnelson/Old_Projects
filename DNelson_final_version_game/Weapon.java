import java.util.Random;
/**
 * This class is similar to the armor class. The objects name string string and integer and
 * immediately assigned when the object is contructed. The main difference is that the armor
 * class integer is used as protection and the weapon integer is used as damage. Additionally
 * there are not type strings associated to weapons unlike armors.
 */
public class Weapon
{
    int theDamage;
    String theName;
    public Weapon(String name)
    {
        //sets the objects string name and damage as part of initializing it
        theName = name;
        theDamage = setDamage();
    }

    int setDamage()
    {
        //setting the damage value based off its name
        if(theName.equals("long sword"))
            return 3;
        if(theName.equals("battle axe"))
            return 4;
        if(theName.equals("spear"))
            return 2;
        if(theName.equals("dagger"))
            return 2;
        if(theName.equals("cross bow"))
            return 2;
        if(theName.equals("mace"))
            return 3;
        if(theName.equals("war hammer"))
            return 4;
        return -1;
    }

    String getName()
    {
        return theName;
    }
    
    int getDamage()
    {
        return theDamage;
    }

    static String randomWeapon()
    {
        //randomly picks a weapon name
        Random rand = new Random();
        int weapon = rand.nextInt(10);
        if (weapon==0)
            return "long sword";
        if (weapon==1)
            return "battle axe";
        if (weapon==2)
            return "war hammer";
        if (weapon==3)
            return "mace";
        if (weapon<5)
            return "dagger";
        if (weapon<7)
            return "cross bow";
        if (weapon<10)
            return "spear";
        return "";
    }
}
