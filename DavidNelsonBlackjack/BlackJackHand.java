
public class BlackJackHand
{
    final int BLACK_JACK = 21;
    final int BLACK_JACK_HAND = 2;
    final int MAX_NUM_OF_CARDS = 12;
    final int ACE = 1;
    final int JACK = 11;
    final int QUEEN = 12;
    final int KING = 13;
    Card[] hand=new Card[MAX_NUM_OF_CARDS];
    public BlackJackHand()
    {

    }

    public void printHand()
    {
        System.out.print(hand[0]);
        for(int x=1;x<hand.length;x++){
            if(hand[x]!=null)
                System.out.print(", "+hand[x]);
        }
        System.out.println();
    }

    public void printOneCard()
    {
        System.out.println(hand[0]+", FACE DOWN");
    }

    public void getCard(Card card)
    {
        for(int x=0;x<hand.length;x++){
            if(hand[x]==null){
                hand[x]=card;
                break;
            }
        }
    }

    public Card giveCard(){
        Card aCard=new Card(1,1);  //the 1,1 has no meaning. just placeholders to allow contructing
        for(int i=hand.length-1;i>=0;i--){
            if(hand[i]!=null){
                aCard=hand[i];
                hand[i]=null;
                return aCard;
            }
        }
        return aCard;
    }

    public int getHandLength()
    {
        int count=0;
        for(int x=1;x<hand.length;x++){
            if(hand[x]!=null)
                count++;
        }
        return count;
    }

    public int blackJackValue()
    {
        int sum=0;
        int aces=0;
        for(int i=0;i<hand.length&&hand[i]!=null;i++){
            if(hand[i].getRank()==ACE){
                sum=sum+11;
                aces++;
            }
            else if(hand[i].getRank()==JACK||hand[i].getRank()==QUEEN||hand[i].getRank()==KING)
                sum=sum+10;
            else
                sum=sum+hand[i].getRank();
        }
        while(sum>BLACK_JACK&&aces>0){
            sum=sum-10;
            aces--;
        }
        return sum;
    }

    public boolean bust()
    {
        if(this.blackJackValue()>BLACK_JACK)
            return true;
        return false;
    }

    public boolean canSplit(){
        int cardOne=0;
        int cardTwo=0;
        if(hand[0].getRank()>10)
            cardOne=10;
        else
            cardOne=hand[0].getRank();
        if(hand[1].getRank()>10)
            cardTwo=10;
        else
            cardTwo=hand[1].getRank();
        if(cardOne==cardTwo)
            return true;
        return false;
    }

    public void clearHand()
    {
        for(int i=0;i<hand.length;i++)
            hand[i]=null;
    }

}
