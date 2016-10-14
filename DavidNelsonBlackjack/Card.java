public class Card
{
    final int ACE = 1;
    final int JACK = 11;
    final int QUEEN = 12;
    final int KING = 13;
    private int rank;
    private int suit;
    private String[] suits={"spades","hearts","clubs","diamonds"};

    public Card(int rank, int suit)
    {
        this.rank=rank;
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
        if(rank==card.getRank()&&suit==card.getSuit())
            return true;
        return false;
    }
    
   
}
