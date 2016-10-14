import java.util.Random;
public class Deck
{
    Card[] deck = new Card[52];
    public Deck()
    {
        createDeck();
    }

    public void createDeck()
    {
        int x=0;
        for(int y=0;y<4;y++){
            for(int z=1;z<14;z++){    
                deck[x]=new Card(z,y);
                x++;
            }
        }
    }
    
    public void printDeck()
    {
        for(int i=0;i<deck.length;i++){
            if(deck[i]!=null)
                System.out.println(deck[i]+", ");
        }
    }

    public String toString()
    {
        String allDeck="";   
        for(int i=0;i<deck.length;i++){
            if(deck[i]!=null)
                allDeck+=deck[i].toString()+", ";
        }
        return allDeck;
    }

    public boolean empty()
    {
        for(int x=0;x<deck.length;x++){
            if(deck[x]!=null)
                return false;
        }
        return true;
    }

    public int numCards()
    {
        int cards=0;
        for(int x=0;x<deck.length;x++){
            if(deck[x]!=null)
                cards++;
        }
        return cards;
    }

    public Card dealCard()
    {
        Card theCard=null;
        for(int i=0;i<deck.length;i++)
        {
            if(deck[i]!=null)
            {
                theCard=deck[i];
                deck[i]=null;
                return theCard;
            }
        }
        return theCard;
    }

    public void shuffle()
    {
        Random rand = new Random();
        int i=0;
        Card card;
        for(int x=0;x<deck.length;x++){
            card=deck[x];
            i = rand.nextInt(52);
            deck[x]=deck[i];
            deck[i]=card;
        }
    }

    public boolean equals(Card card1, Card card2)
    {
        if(card1.getRank()==card2.getRank()&&card1.getSuit()==card2.getSuit())
            return true;
        return false;
    }

}
