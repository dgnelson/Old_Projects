package uno;

import java.util.*;
/**
 * Uno player AI
 */
public class dnellyfant_UnoPlayer implements UnoPlayer
{
    private static int RED=0;
    private static int YELLOW=1;
    private static int GREEN=2;
    private static int BLUE=3;
    private static int WILD=4;
    ArrayList<Card>[] cards;
    ArrayList<Card> reds;
    ArrayList<Card> yellows;
    ArrayList<Card> greens;
    ArrayList<Card> blues;
    ArrayList<Card> specials;
    public dnellyfant_UnoPlayer(){
        cards = new ArrayList[5];
        reds = new ArrayList<Card>();
        yellows = new ArrayList<Card>();
        greens = new ArrayList<Card>();
        blues = new ArrayList<Card>();
        specials = new ArrayList<Card>();
        cards[RED]=reds;
        cards[YELLOW]=yellows;
        cards[GREEN]=greens;
        cards[BLUE]=blues;
        cards[WILD]=specials;
    }

    public int play(List<Card> hand, Card upCard, Color calledColor,
    GameState state){
        int index=-1;
        Card theCard=null;
        organize(hand);//collects information on the hand
        if(canPlay(hand, upCard, calledColor)){
            int group=-1;
            if(upCard.getColor()==Color.RED||calledColor==Color.RED)
                group=RED;
            else if(upCard.getColor()==Color.YELLOW||calledColor==Color.YELLOW)
                group=YELLOW;
            else if(upCard.getColor()==Color.GREEN||calledColor==Color.GREEN)
                group=GREEN;
            else if(upCard.getColor()==Color.BLUE||calledColor==Color.BLUE)
                group=BLUE;

            if((!cards[WILD].isEmpty())){//use wildcard first to avoid +50
                theCard=cards[WILD].get(0);
                if(theCard.getRank()!=Rank.WILD_D4){
                    for(int n=1;n<cards[group].size();n++){
                        if(cards[WILD].get(n).getRank()==Rank.WILD_D4){
                            theCard=cards[WILD].get(n);
                            break;
                        }
                    }
                }
            }
            if(!cards[group].isEmpty()&&theCard==null){ //if you have card same color as upcard
                theCard=cards[group].get(0);
                for(int n=1;n<cards[group].size();n++){
                    if(cards[group].get(n).getNumber()==-1){//if card is special, use itd
                        theCard=cards[group].get(n);
                        break;
                    }
                    if(cards[group].get(n).getNumber()>theCard.getNumber())
                        theCard=cards[group].get(n);
                }
            }
            else {//if you have same rank/number
                ListIterator<Card> iter = hand.listIterator();
                while(iter.hasNext()){
                    Card c = iter.next();
                    if(c.getRank()==upCard.getRank()&&c.getRank()!=Rank.NUMBER){
                        theCard=c;
                        break;
                    }
                    if(c.getNumber()==upCard.getNumber()&&c.getNumber()!=-1){
                        theCard=c;
                        break;
                    }
                }
            }

            
            for(int n=0;n<hand.size();n++){  //Finds the actual index of the card in the hand
                if(theCard.getColor()==hand.get(n).getColor()&&theCard.getNumber()==hand.get(n).getNumber()&&theCard.getRank()==hand.get(n).getRank()){
                    index=n;
                    break;
                }
            }
        }
        clearData();
        return index;
    }

    public Color callColor(List<Card> hand){
        int group=0;
        for(int n=1;n<cards.length-1;n++){ //finds most common color of cards in hand
            if(cards[n].size()>cards[group].size())
                group=n;
        }
        if(group==RED)
            return Color.RED;
        else if(group==YELLOW)
            return Color.YELLOW;
        else if(group==GREEN)
            return Color.GREEN;
        else if(group==BLUE)
            return Color.BLUE;
        return Color.RED;  //in case something messes up, just pick red...
    }

    private boolean canPlay(List<Card> hand, Card upCard, Color calledColor){//returns true if have playable card
        int group=-1;
        if(!cards[WILD].isEmpty())
            return true;
        else if(upCard.getColor()==Color.RED||calledColor==Color.RED)
            group=RED;
        else if(upCard.getColor()==Color.YELLOW||calledColor==Color.YELLOW)
            group=YELLOW;
        else if(upCard.getColor()==Color.GREEN||calledColor==Color.GREEN)
            group=GREEN;
        else if(upCard.getColor()==Color.BLUE||calledColor==Color.BLUE)
            group=BLUE;          
        if(!cards[group].isEmpty())
            return true;
        ListIterator<Card> iter = hand.listIterator();
        while(iter.hasNext()){
            Card c = iter.next();
            if(c.getRank()==upCard.getRank()&&c.getRank()!=Rank.NUMBER)
                return true;
            if(c.getNumber()==upCard.getNumber()&&c.getNumber()!=-1)
                return true;
        }
        return false;
    }

    private void organize(List<Card> hand){//holds data collecion methods
        setup(hand);
    }

    private List<Card> sort(List<Card> hand){//to be implemented
        return null;
    }

    private void setup(List<Card> hand){//basically sorts the cards
        ListIterator<Card> iter = hand.listIterator();
        while(iter.hasNext()){
            Card c = iter.next();
            if(c.getColor()==Color.RED)
                cards[0].add(c);
            if(c.getColor()==Color.YELLOW)
                cards[1].add(c);
            if(c.getColor()==Color.GREEN)
                cards[2].add(c);
            if(c.getColor()==Color.BLUE)
                cards[3].add(c);
            if(c.getColor()==Color.NONE)
                cards[4].add(c);
        }
    }

    private void clearData(){//emties arrays of cards
        for(int n=0;n<cards.length;n++){
            ListIterator iter = cards[n].listIterator();
            while(iter.hasNext()){
                iter.next();
                iter.remove();
            }
        }

    }
}
