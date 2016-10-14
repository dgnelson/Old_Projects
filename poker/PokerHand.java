
import java.util.ArrayList;
public class PokerHand
{
    private ArrayList<Card> hand = new ArrayList<Card>();
    private ArrayList<Integer> straights = new ArrayList<Integer>(); //holds rank of high card in straight
    private boolean flush; //not an array bc tie breaker doesnt use suit
    private ArrayList<Integer> twoKind = new ArrayList<Integer>();  //holds rank of pairs
    private ArrayList<Integer> threeKind = new ArrayList<Integer>(); //holds rank of three kinds
    private ArrayList<Integer> fourKind = new ArrayList<Integer>();  //holds rank of four kinds
    private int[] ranks = new int[13]; //holds number of cards of each rank
    private int[] suits = new int[4];  //holds number of cards of each suit
    private int highCard;  //stores the value of the high card
    private int straightFlushHigh;  //store the value of the high card in a straight flush
    
    final int HIGH_CARD=0;
    final int PAIR=1;
    final int TWO_PAIR=2;
    final int THREE_OF_A_KIND=3;
    final int STRAIGHT=4;
    final int FLUSH=5;
    final int FULL_HOUSE=6;
    final int FOUR_OF_A_KIND=7;
    final int STRAIGHT_FLUSH=8;
    final int ROYAL_FLUSH=9;

    public PokerHand()
    {

    }

    private void countRanks(){
        for(int i=0;i<hand.size();i++)    //counts the number of cards of each rank ny storing in an array
            ranks[(hand.get(i).getRank())-2]++;
        for(int x=0;x<ranks.length;x++){   //checks for pairs, three kind and four kind. then stores the rank of the grouping in its respective array list
            if(ranks[x]==2)
                twoKind.add(x+2);
            else if(ranks[x]==3)
                threeKind.add(x+2);
            else if(ranks[x]>=4)
                fourKind.add(x+2);
        }
        for(int n=ranks.length-1;n>=0;n--){   //stores the value of the high card
            if(ranks[n]!=0){
                highCard=n+2;
                break;
            }
        }
    }

    private void checkStraights(){
        if(ranks[12]!=0&&ranks[0]!=0&&ranks[1]!=0&&ranks[2]!=0&&ranks[3]!=0)//checks for low straight
            straights.add(5);
        for(int i=0;i<ranks.length-4;i++){ //checks for 5 cards of increasing rank and stores the high card is the straight exists
            boolean success=true;
            for(int x=0;x<5;x++){
                if(ranks[i+x]==0)
                    success=false;
            }
            if(success)  //storing the high card
                straights.add(i+6);
        }
    }

    private void checkFlushes(){
        for(int i=0;i<hand.size();i++)   //sorts the cards based on suit
            suits[hand.get(i).getSuit()]++;
        for(int x=0;x<suits.length;x++){  //sets variable true if 5 or more cards of a suit
            if(suits[x]>=5)
                flush=true;
        }
    }

    public int evaluate(){  //evaluates the hand by checking specific conditions. the better the hand, the higher the int it returns
        resetClassVars();  //clears all stored data from previous hand
        countRanks();  //checks for pairs, three kind and four kind
        checkStraights();  //checks hand for straights
        checkFlushes();  //checks for occurance of flushes
        if(!flush&&straights.size()==0&&fourKind.size()==0&&threeKind.size()==0&&twoKind.size()==0) //checks if bust (high card)
            return HIGH_CARD;
        else if(!flush&&straights.size()==0&&fourKind.size()==0&&threeKind.size()==0&&twoKind.size()==1) //checks if pair
            return PAIR;
        else if(!flush&&straights.size()==0&&fourKind.size()==0&&threeKind.size()==0&&twoKind.size()>=2) //check if two pair
            return TWO_PAIR;
        else if(!flush&&straights.size()==0&&fourKind.size()==0&&threeKind.size()>=1&&twoKind.size()==0) //checks if three of a kind
            return THREE_OF_A_KIND;
        else if(!flush&&straights.size()>=1&&fourKind.size()==0&&!fullHouse())  //checks if straight
            return STRAIGHT;
        else if(flush&&fourKind.size()==0&&!fullHouse()&&!straightFlush()&&!royalFlush()) //checks if flush
            return FLUSH;
        else if(fullHouse()&&fourKind.size()==0&&!straightFlush()&&!royalFlush())  //check if full house
            return FULL_HOUSE;
        else if(fourKind.size()>=1&&!straightFlush()&&!royalFlush())  //checks if four of a kind
            return FOUR_OF_A_KIND;
        else if(straightFlush()&&!royalFlush())  //checks if straight flush
            return STRAIGHT_FLUSH;
        else if(royalFlush())  //checks if royal flush
            return ROYAL_FLUSH;
        return -1;  //somethings wrong if it gets here...
    }

    private boolean fullHouse(){  //checks if hand has a full house
        if(threeKind.size()>=1&&twoKind.size()>=1)  //checks for at least one pair and at leats one three of a kind
            return true;
        return false;
    }

    private boolean straightFlush(){  // returns true if hand has straight flush
        if(straights.size()>=1&&flush){
            for(int i=0;i<suits.length;i++){  //goes through all suits that have flushes
                if(suits[i]>=5){
                    for(int x=straights.size()-1;x>=0;x--){  //checks the flushed suits for straights
                        if(straights.get(x)!=14){  //condition accounts for royal flush
                            boolean success=true;
                            for(int j = 0;j<5;j++){   //5 represents number of cards in straight
                                if(!isCard(straights.get(x)-j,i)){
                                    success=false;
                                    break;
                                }
                            }
                            if(success){  
                                straightFlushHigh=straights.get(x); //stores the high card of the straight 
                                return true;
                            }
                        }
                    }
                }
            }
        } 
        if(straights.size()>=1&&flush){  //checks for low straight flush
            for(int z=0;z<suits.length;z++){
                if(suits[z]>=5){
                    if(isCard(14,z)){
                        boolean successful=true;
                        for(int k=0;k<4;k++){  //four represents # of remaining cards in a
                            if(!isCard(5-k,z)){ //low straight besides the ace
                                successful=false;  //five represents high card of low straight
                                break;
                            }
                        }
                        if(successful){
                            straightFlushHigh=5;  //stores the high card of the straight
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean royalFlush(){  //checks for occurance of royal flush in hand
        if(straights.size()>=1&&flush){   //first checks if theres a straight and a flush
            for(int z=0;z<suits.length;z++){  //searches the suits that have flushes
                if(suits[z]>=5){
                    boolean success=true;  //checks isf flushed suit has an ace high straight
                    for(int x=0;x<5;x++){  //5 represents number of cards in straight
                        if(!isCard(14-x,z)){  //14 checks for ace high striaght
                            success=false;
                            break;
                        }
                    }
                    if(success){
                        straightFlushHigh=14;  //stores the the high card of the straight (ACE)
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isCard(int rank, int suit){  //returns true if a specific card is in the hand
        for(int i=0;i<hand.size();i++){
            if(hand.get(i).getRank()==rank&&hand.get(i).getSuit()==suit)
                return true;
        }
        return false;
    }

    private void resetClassVars(){  //clears all of the recorded data in preparation of another hand
        while(straights.size()>0)
            straights.remove(straights.size()-1);
        while(twoKind.size()>0)
            twoKind.remove(twoKind.size()-1);
        while(threeKind.size()>0)
            threeKind.remove(threeKind.size()-1);
        while(fourKind.size()>0)
            fourKind.remove(fourKind.size()-1);
        for(int i=0;i<ranks.length;i++)
            ranks[i]=0;
        for(int x=0;x<suits.length;x++)
            suits[x]=0;
        flush=false;
        highCard=0;
        straightFlushHigh=0;
    }
    
    public void insertionSort(){  //sorts the cards in the hand and is only called when using tie breakers
        int index;
        for(int i=1;i<hand.size();i++){
            index=i-1;
            while(index>=0&&hand.get(i).getRank()<hand.get(index).getRank()){
                index--;
            }
            hand.add(index+1,hand.get(i));
            hand.remove(i+1);
        }
    }
    
    public void addCard(Card card){
        hand.add(card);
    }
    
    public Card getCard(int x){
        return hand.get(x);
    }

    public int numCards()
    {
        return hand.size();
    }

    public Card discardCard()  //used to give cards back after the hand is finished
    {
        Card card=null;
        if(hand.size()>0){
            card=hand.get(hand.size()-1);
            hand.remove(hand.size()-1);
        }
        return card;
    }

    public void printHand(){
        for(int i=0;i<hand.size();i++){
            System.out.print(hand.get(i)+", ");
        }
        System.out.println();
    }

    public int getHighCard(){
        return highCard;
    }
    
    public int getPair(int x){
        return twoKind.get(x);
    }
    
    public int numPairs(){
        return twoKind.size();
    }
    
    public int getThreeKind(int x){
        return threeKind.get(x);
    }
    
     public int numThrees(){
        return threeKind.size();
    }
    
    public int getFourKind(int x){
        return fourKind.get(x);
    }
    
     public int numFours(){
        return fourKind.size();
    }

    public int getStraightHigh(){
        return straights.get(straights.size()-1);
    }

    public int getStraightFlushHigh(){
        return straightFlushHigh;
    }

}
