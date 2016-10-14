import javax.swing.JOptionPane;
public class Poker
{
    private PokerHand player1=new PokerHand();
    private PokerHand player2=new PokerHand();
    private Deck deck = new Deck();
    private Deck usedCards = new Deck();
    
    final int CARDS_PER_HAND = 5;  //change number of cards per hand here
    
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

    public Poker(){
        usedCards.clearDeck();  //empties the used cards deck bc decks are filled 
    }                           //with cards when constructed and this deck should start empty

    public void play(){
        String input = JOptionPane.showInputDialog("How many rounds?");
        int rounds = Integer.parseInt(input);
        int p1hand;
        int p2hand;
        for(int i=0;i<rounds;i++){   //runs for however many rounds you input
            refillDeck();  //gives cards from used pile if not enough cards for two hands
            deck.shuffle();
            dealCards();
            System.out.println("Player 1:");
            player1.printHand();
            System.out.println("Player 2:");
            player2.printHand();
            p1hand=player1.evaluate();  //stores hand 1
            p2hand=player2.evaluate();  //stores hand 2
            System.out.println();
            compareHands(p1hand,p2hand);  //decides the hand (uses tie breakers)
            discardCards();  //hands give cards to used card pile
            System.out.println();
            System.out.println();
        }
        
    }

    public void oneMilRounds(){
        long startTime = System.nanoTime();  //starts timer
        float[] hands = new float[10];  //holds number of each type of hand dealt ie. pair, high card, flush
        float a=10000000;
        int p1hand;

        for(int i=0;i<10000000;i++){
            if(deck.numCards()<CARDS_PER_HAND){  //refills deck from used cards pile if not enough cards for full hand
                while(!usedCards.empty())
                    deck.addCard(usedCards.dealCard());
            }
            deck.shuffle();
            for(int x=0;x<CARDS_PER_HAND;x++) //deals cards to hand
                player1.addCard(deck.dealCard());
            p1hand=player1.evaluate();
            if(p1hand==HIGH_CARD)
                hands[0]++;
            else if(p1hand==PAIR)
                hands[1]++;
            else if(p1hand==TWO_PAIR)
                hands[2]++;
            else if(p1hand==THREE_OF_A_KIND)
                hands[3]++;
            else if(p1hand==STRAIGHT)
                hands[4]++;
            else if(p1hand==FLUSH)
                hands[5]++;
            else if(p1hand==FULL_HOUSE)
                hands[6]++;
            else if(p1hand==FOUR_OF_A_KIND)
                hands[7]++;
            else if(p1hand==STRAIGHT_FLUSH)
                hands[8]++;
            else if(p1hand==ROYAL_FLUSH)
                hands[9]++;
            while(player1.numCards()>0)  //hand gives its cards to used card pile
                usedCards.addCard(player1.discardCard());
        }

        long estimatedTime = System.nanoTime() - startTime;
        System.out.println("The test took " + estimatedTime/(double)1000000000 + " seconds to run.");
        System.out.println("");
        for(int k=0;k<hands.length;k++){  //prints out the ratios
            System.out.print(getHandName(k));
            System.out.println(hands[k]/a);
        }

    }

    private String getHandName(int x){
        if(x==HIGH_CARD)
            return "High Card: ";
        if(x==PAIR)
            return "Pair: ";
        if(x==TWO_PAIR)
            return "Two Pair: ";
        if(x==THREE_OF_A_KIND)
            return "Three of a kind: ";
        if(x==STRAIGHT)
            return "Straight: ";
        if(x==FLUSH)
            return "Flush: ";
        if(x==FULL_HOUSE)
            return "Full House: ";
        if(x==FOUR_OF_A_KIND)
            return "Four of a kind: ";
        if(x==STRAIGHT_FLUSH)
            return "Straight Flush ";
        if(x==ROYAL_FLUSH)
            return "Royal Flush ";
        return "";
    }

    private void dealCards(){  //for two player
        for(int i=0;i<CARDS_PER_HAND;i++){
            player1.addCard(deck.dealCard());
            player2.addCard(deck.dealCard());
        }
    }

    private void discardCards(){ //hands give their cards to the used cards pile after round is over
        while(player1.numCards()>0)
            usedCards.addCard(player1.discardCard());
        while(player2.numCards()>0)
            usedCards.addCard(player2.discardCard());
    }

    private void refillDeck(){
        if(deck.numCards()<CARDS_PER_HAND*2){  //checks if enough cards for two hands
            while(!usedCards.empty())            //and refills the deck if theres not enough
                deck.addCard(usedCards.dealCard());
        }
    }

    private void compareHands(int p1, int p2){   //incorporates tie breaker rules
        if(p1>p2)
            System.out.println("player 1 wins");
        else if(p2>p1)
            System.out.println("player 2 wins");
        else{   //tie breakers
            player1.insertionSort();
            player2.insertionSort();
            if(p1==HIGH_CARD){
                for(int k=0;k<CARDS_PER_HAND;k++){
                    if(player1.getCard(CARDS_PER_HAND-k-1).getRank()>player2.getCard(CARDS_PER_HAND-k-1).getRank()){
                        System.out.println("player 1 wins");
                        return;
                    }
                    if(player2.getCard(CARDS_PER_HAND-k-1).getRank()>player1.getCard(CARDS_PER_HAND-k-1).getRank()){
                        System.out.println("player 2 wins");
                        return;
                    }
                }
                System.out.println("tie");
            }
            else if(p1==PAIR){
                if(player1.getPair(0)>player2.getPair(0)){
                    System.out.println("player 1 wins");
                    return;
                }
                if(player2.getPair(0)>player1.getPair(0)){
                    System.out.println("player 2 wins");
                    return;
                }
                for(int k=0;k<CARDS_PER_HAND;k++){
                    if(player1.getCard(CARDS_PER_HAND-k-1).getRank()>player2.getCard(CARDS_PER_HAND-k-1).getRank()){
                        System.out.println("player 1 wins");
                        return;
                    }
                    if(player2.getCard(CARDS_PER_HAND-k-1).getRank()>player1.getCard(CARDS_PER_HAND-k-1).getRank()){
                        System.out.println("player 2 wins");
                        return;
                    }
                }
                System.out.println("tie");
            }
            else if(p1==TWO_PAIR){  //fix!!not 1 index, its last index
                if(player1.getPair(player1.numPairs()-1)>player2.getPair(player2.numPairs()-1)){
                    System.out.println("player 1 wins");
                    return;
                }
                if(player2.getPair(player2.numPairs()-1)>player1.getPair(player1.numPairs()-1)){
                    System.out.println("player 2 wins");
                    return;
                }
                if(player1.getPair(player1.numPairs()-2)>player2.getPair(player2.numPairs()-2)){
                    System.out.println("player 1 wins");
                    return;
                }
                if(player2.getPair(player2.numPairs()-2)>player1.getPair(player1.numPairs()-2)){
                    System.out.println("player 2 wins");
                    return;
                }
                for(int k=0;k<CARDS_PER_HAND;k++){
                    if(player1.getCard(CARDS_PER_HAND-k-1).getRank()>player2.getCard(CARDS_PER_HAND-k-1).getRank()){
                        System.out.println("player 1 wins");
                        return;
                    }
                    if(player2.getCard(CARDS_PER_HAND-k-1).getRank()>player1.getCard(CARDS_PER_HAND-k-1).getRank()){
                        System.out.println("player 2 wins");
                        return;
                    }
                }
                System.out.println("tie");
            }
            else if(p1==THREE_OF_A_KIND){
                if(player1.getThreeKind(player1.numThrees()-1)>player2.getThreeKind(player2.numThrees()-1))
                    System.out.println("player 1 wins");
                else
                    System.out.println("player 2 wins");
            }
            else if(p1==STRAIGHT){
                if(player1.getStraightHigh()>player2.getStraightHigh()){
                    System.out.println("player 1 wins");
                    return;
                }
                if(player2.getStraightHigh()>player1.getStraightHigh()){
                    System.out.println("player 2 wins");
                    return;
                }
                System.out.println("tie");
            }
            else if(p1==FLUSH){
                for(int k=0;k<CARDS_PER_HAND;k++){
                    if(player1.getCard(CARDS_PER_HAND-k-1).getRank()>player2.getCard(CARDS_PER_HAND-k-1).getRank()){
                        System.out.println("player 1 wins");
                        return;
                    }
                    if(player2.getCard(CARDS_PER_HAND-k-1).getRank()>player1.getCard(CARDS_PER_HAND-k-1).getRank()){
                        System.out.println("player 2 wins");
                        return;
                    }
                }
                System.out.println("tie");
            }
            else if(p1==FULL_HOUSE){
                if(player1.getThreeKind(player1.numThrees()-1)>player2.getThreeKind(player2.numThrees()-1))
                    System.out.println("player 1 wins");
                else
                    System.out.println("player 2 wins");
            }
            else if(p1==FOUR_OF_A_KIND){
                if(player1.getFourKind(player1.numFours()-1)>player2.getFourKind(player2.numFours()-1))
                    System.out.println("player 1 wins");
                else
                    System.out.println("player 2 wins");
            }
            else if(p1==STRAIGHT_FLUSH){
                if(player1.getStraightFlushHigh()>player2.getStraightFlushHigh()){
                    System.out.println("player 1 wins");
                    return;
                }
                if(player2.getStraightFlushHigh()>player1.getStraightFlushHigh()){
                    System.out.println("player 2 wins");
                    return;
                }
                System.out.println("tie");
            }
            else if(p1==ROYAL_FLUSH){  //whats the tie breaker rule?
                System.out.println("tie");
            }
        }
    }
}
