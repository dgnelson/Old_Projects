import javax.swing.JOptionPane;
public class BoringBlackjack  //a completely working version but simpler version
{
    //final ints are at bottom of page
    int play=JOptionPane.YES_OPTION;  //used to give user the option to play another hand
    Deck deck=new Deck();             //constructs the deck
    BlackJackHand theUser=new BlackJackHand();  //constructs the users empty hand
    BlackJackHand theDealer=new BlackJackHand(); //contructs the dealers empty hand
    int userSum=0;   //blackjack value of users had
    int dealerSum=0;  //blackjack value of dealers hand
    String theBet="";
    int bet=0;
    int money=500;
    int choice=0;
    boolean showDealerSecondCard=false;

    public BoringBlackjack()
    {

    }

    public void play(){
        while (play == JOptionPane.YES_OPTION&&money>0){
            resetTable();
            takeBets();
            if(theBet==null||theBet.equals("0")||theBet.equals(""))
                break;
            dealCards();
            printHands();
            userSum=theUser.blackJackValue();
            dealerSum=theDealer.blackJackValue();
            if(userSum!=BLACK_JACK&&dealerSum!=BLACK_JACK){
                while(true){
                    choice = JOptionPane.showOptionDialog(null, "Hit or Stay", "Blackjack", 
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Hit", "Stay"}, "default");
                    if(choice==EXIT||choice==STAY)
                        break;
                    hit();
                    if(theUser.bust())
                        break;
                }
                showDealerSecondCard=true; //will show all of dealers cards
                while(dealerSum<DEALER_MIN&&!theUser.bust()){   //dealer's turn to hit till reaches at least 17
                    printHands();                   
                    theDealer.getCard(deck.dealCard());
                    dealerSum=theDealer.blackJackValue();
                }
            }
            showDealerSecondCard=true;
            printHands();
            outcome();       
            if(money>0)
                play = JOptionPane.showConfirmDialog(null, "Play another hand?", "Blackjack", JOptionPane.YES_NO_OPTION);
            else
                break;
        }
        System.out.println("");
        System.out.println("");
        System.out.println("Final chip count: $"+money);
        System.out.print("Thanks for playing!");
    }

    private void takeBets()
    {
        String nums="9876543210";  //chars in theBet are checked with thi string to see if the input is a number
        boolean isNumber=true;     //changes if not all chars are numbers
       
        while(true){
            theBet = JOptionPane.showInputDialog("You have $"+money+". What is your bet?");  //input option
            if(theBet==null||theBet.equals("0")||theBet.equals(""))  //exits game if no bet is entered
                break;
            for(int x=0;x<theBet.length()-1;x++){   //loop to check for chars present that arnt numbers
                if(nums.indexOf(theBet.substring(x,x+1))==-1)
                    isNumber=false;
            }
            if(isNumber){  //if theBet passes the for loop test it enters the if statement
                bet=Integer.parseInt(theBet);  //theBet is parsed from a string into an int called bet
                money=money-bet;  //basically represents 'moving' chips from your stack and onto the table
                if(money<0){  //returns substracted amount if you do not have enough to bet that much
                    System.out.print("\f");
                    System.out.println("You don't have enough money to bet that amount!");
                    money=money+bet;
                }
                else  //if you have enough the while loop is exited
                    break;
            }
            isNumber=true;
        }
    }

    private void dealCards()
    {
        for(int i=0;i<BLACK_JACK_HAND;i++){  //deals two cards to user and dealer
            theUser.getCard(deck.dealCard());  //deals to user
            theDealer.getCard(deck.dealCard());  //deals to dealer
        }
    }

    private void printHands()
    {
        System.out.print("\f");
        System.out.print("User's hand: ");
        theUser.printHand();           //displays both cards
        System.out.print("Dealer's hand: ");
        if(showDealerSecondCard)   
            theDealer.printHand();         //displays both cards
        else
            theDealer.printOneCard();   //only displays one of dealers cards
    }
    
    private void hit(){
        System.out.print("\f");
        theUser.getCard(deck.dealCard());  //user is dealt a card 
        printHands();                         //hands are printed
        userSum=theUser.blackJackValue();    //blackjack value is recalculated
    }
    
    private void outcome()  //goes through all possible outcomes and prints the one that matches what happened
    {
        if(theUser.bust()&&theDealer.bust()){
            System.out.println("Both bust");
            money=money+bet;
        }
        else if(theUser.bust())
            System.out.println("User busts");
        else if(theDealer.bust()){
            System.out.println("Dealer busts");
            if(userSum==BLACK_JACK)
                money=money+((bet*3)/2);
            else
                money=money+(bet*2);
        }
        else if(userSum>dealerSum){
            System.out.println("User wins");
            money=money+(bet*2);
        }
        else if(userSum<dealerSum)
            System.out.println("Dealer wins");
        else if(userSum==BLACK_JACK&&dealerSum==BLACK_JACK){
            if(theDealer.getHandLength()==2&&theUser.getHandLength()!=2)
                System.out.println("Dealer wins");
            else if(theUser.getHandLength()==2&&theDealer.getHandLength()!=2){
                System.out.println("User wins");
                money=money+((bet*3)/2);
            }
            else{
                System.out.println("Tie");
                money=money+bet;
            }
        }
        else if(userSum==dealerSum){
            System.out.println("Tie");
            money=money+bet;
        }
        System.out.println("Chip count: $"+money);
    }

    private void resetTable() //prepares table to deal another hand
    {
        System.out.print("\f");
        deck.createDeck();        //resets the deck to ace spades, 2 spades 3 spades....  with all 52 cards
        deck.shuffle();       //shuffles the deck
        showDealerSecondCard=false;  // resets the second dealer card boolean
        theUser.clearHand();    //resets users hand
        theDealer.clearHand();  //resets dealers hand
    }

    final int BLACK_JACK = 21;
    final int BLACK_JACK_HAND = 2;
    final int DEALER_MIN = 17;
    final int EXIT=-1;
    final int HIT=0;
    final int STAY=1;
}
