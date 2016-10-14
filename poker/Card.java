public class Card
{
    private final int ACE = 14;
    private final int JACK = 11;
    private final int QUEEN = 12;
    private final int KING = 13;
    private int rank=-1;
    private int suit=-1;
    private final String[] suits={"spades","hearts","clubs","diamonds"};

    public Card(int rank, int suit)
    {
        if(2<=rank&&rank<=14)
            this.rank=rank;
        if(0<=suit&&suit<=3)
            this.suit=suit;
    }

    public int getRank() 
    {
        return rank;
    }

    public int getSuit()
    {
        return suit;
    }

    public String toString()
    {
        if(rank==ACE)
            return "ace of "+suits[suit];
        if(rank==JACK)
            return "jack of "+suits[suit];
        if(rank==QUEEN)
            return "queen of "+suits[suit];
        if(rank==KING)
            return "king of "+suits[suit];
        return rank+" of "+suits[suit];
    }

    public boolean equals(Card card)
    {
        if(rank==card.getRank())
            return true;
        return false;
    }
    
}
