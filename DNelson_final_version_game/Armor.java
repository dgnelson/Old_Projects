import java.util.Random;
/**
 * The class is obviously dedicated to armor usage in the game. In reality, all an armor object is, is
 * a string with an integer associated with it and an additional string that representing the armor type. 
 * Types are set according to whatever the name string is. Possible types are helmet, chest and boots.
 * Whenever an armor piece is contrsucted its name is randomly generated and it protection(integer value)
 * is set based off of that string. Additionally the type is set based of the string name.
 */
public class Armor
{
    int theProtection;
    String theName;
    String type;
    public Armor(String name)
    {
       //sets all armor variables as soon as it is initialized
       theName = name;
       theProtection = setProtection();
       type = setType();
    }

    int setProtection()
    {
        //assigns an integer to the armor
        if(theName.equals("chainmail boots"))
            return 2;
        if(theName.equals("chainmail helmet"))
            return 2;
        if(theName.equals("chainmail chest piece"))
            return 2;
        if(theName.equals("bronze breastplate"))
            return 2;
        if(theName.equals("bronze helmet"))
            return 2;
        if(theName.equals("bronze boots"))
            return 2;
        if(theName.equals("leather tunic"))
            return 1;
        if(theName.equals("leather sandals"))
            return 1;
        if(theName.equals("leather helmet"))
            return 1;
        if(theName.equals("Zues's gold laurel"))
            return 3;
        if(theName.equals("Ares's impenetrable chestplate"))
            return 3;
        if(theName.equals("Hermes's winged sandals"))
            return 3;
        return -1;
    }
    
    String setType()
    {
        //sets the type of armor
        if(theName.equals("chainmail boots")||theName.equals("bronze boots")||theName.equals("leather sandals")||theName.equals("Hermes's winged sandals"))
            return "boots";
        if(theName.equals("chainmail helmet")||theName.equals("bronze helmet")||theName.equals("leather helmet")||theName.equals("Zues's gold laurel"))
            return "helmet";
        if(theName.equals("chainmail chest piece")||theName.equals("bronze breastplate")||theName.equals("leather tunic")||theName.equals("Hermes's winged sandals"))
            return "chest";
        return null;
    }
    
    String getType()
    {
        return type;
    }
    
    String getName()
    {
        return theName;
    }
    
    int getProtection()
    {
        return theProtection;
    }
    
    static String randomArmor()
    {
        //randomly picks the armor name. It is static so it can be accessed before the armor is initialized
        Random rand = new Random();
        int armor = rand.nextInt(10);
        if(armor==0)
            return "chainmail boots";
        if (armor==1)
            return "chainmail helmet";
        if (armor==2)
            return "chainmail chest piece";
        if (armor==3)
            return "bronze breastplate";
        if (armor==4)
            return "bronze helmet";
        if (armor==5)
            return "bronze boots";
        if (armor==6)
            return "leather tunic";
        if (armor==7)
            return "leather sandals";
        if (armor==8)
            return "leather helmet";
        if (armor==9)
            return "Zues's gold laurel";
        if (armor==10)
            return "Ares's impenetrable chestplate";
        if (armor==11)
            return "Hermes's winged sandals";
        return "";
    }
}
