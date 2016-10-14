import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
public class Deck
{
    ArrayList<Card> deck = new ArrayList<Card>();
    Random rand = new Random();

    public Deck()
    {
        createDeck();
    }

    public void createDeck()
    {
        int x=0;
        for(int y=0;y<4;y++){
            for(int z=2;z<=14;z++){    
                deck.add(new Card(z,y));
                x++;
            }
        }
    }

    public void addCard(Card card){
        deck.add(card);
    }

    public void clearDeck(){
        while(!empty())
            deck.remove(deck.size()-1);
    }

    public void printDeck()
    {
        for(int i=0;i<deck.size();i++){
            System.out.println(deck.get(i));
        }
    }

    public String toString()
    {
        String allDeck="";   
        for(int i=0;i<deck.size();i++){
            allDeck+=deck.get(i).toString()+", ";
        }
        return allDeck;
    }

    public boolean empty()
    {
        if(deck.size()==0){
            return true;
        }
        return false;
    }

    public int numCards()
    {
        return deck.size();
    }

    public Card dealCard()
    {
        Card card=null;
        if(deck.size()>0){
            card=deck.get(deck.size()-1);
            deck.remove(deck.size()-1);
        }
        return card;
    }

    public void shuffle() 
    {
        int i=0;
        Card card;
        for(int x=0;x<deck.size();x++){
            card=deck.get(x);
            i = rand.nextInt(deck.size()-1);
            deck.set(x,deck.get(i));
            deck.set(i,card);
        }
    }

    public void selectionSort(){
        Card card;
        boolean change=false;
        int index;
        for(int i=0;i<deck.size()-1;i++){
            index=i;
            for(int x=i+1; x<deck.size();x++){
                if(deck.get(x).getRank()<deck.get(index).getRank()){
                    index=x;
                    change=true;
                }
            }
            if(change){
                card=deck.get(i);
                deck.set(i,deck.get(index));
                deck.set(index,card);
            }
            change=false;
        }
    }

    public void insertionSort(){ 
        int index;
        for(int i=1;i<deck.size();i++){
            index=i-1;
            while(index>=0&&deck.get(i).getRank()<deck.get(index).getRank()){
                index--;
            }
            deck.add(index+1,deck.get(i));
            deck.remove(i+1);
        }
    }
  
    public int findCard(int rank){  //binary search
        insertionSort();
        int high=deck.size()-1;
        int low=0;
        int mid=deck.size()/2;
        while(true){
            if(rank>deck.get(mid).getRank()){
                low=mid;
                mid=mid+((high-low)/2);
            }
            else if(rank<deck.get(mid).getRank()){
                high=mid;
                mid=mid-((mid-low)/2);
            }
            else if(rank==deck.get(mid).getRank())
                return mid;
            if (mid==low||mid==high)
                break;
        }
        return -1;
    }
   
    public boolean equals(Card card1, Card card2)
    {
        if(card1.getRank()==card2.getRank())
            return true;
        return false;
    }

}
