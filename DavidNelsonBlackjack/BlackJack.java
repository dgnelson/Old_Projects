
import javax.swing.JOptionPane;
public class BlackJack     //NOT FINISHED, SPLIT IN PROGRESS
{
    //all final ints at bottom of class
    int play=JOptionPane.YES_OPTION;
    Deck deck=new Deck();
    BlackJackHand theUser=new BlackJackHand();
    BlackJackHand theDealer=new BlackJackHand();
    int userSum=0;
    int dealerSum=0;
    String theBet="";
    int bet=0;
    String[] buttons = {"Hit","Stay","Double Down","Split","Surrender"};  //choices after initial hand is dealt
    int choice=0;
    int money=500;
    boolean showDealerSecondCard=false;
    int count=0;
    boolean isSplit=false;
    BlackJackHand secondHand=null;
    int secondHandSum=0;
    int secondBet=0;
    int z=0; //used to check if user split and the chooses to stay or exits for both hands, thus ending the round

    public BlackJack()
    {

    }

    public void play()
    {
        while (play == JOptionPane.YES_OPTION&&money>0){  
            resetTable();
            takeBets();
            if(theBet==null||theBet.equals("0"))
                break;
            dealCards();
            printHands();
            userSum=theUser.blackJackValue();
            dealerSum=theDealer.blackJackValue();
            if(userSum!=BLACK_JACK&&dealerSum!=BLACK_JACK){   //round begins if no inital blackjacks
                while(true){   //stays in loop only if hits and not busts or if splits
                    if(isSplit){
                        splitPrint();
                        if(!theUser.bust()){
                            choice = JOptionPane.showOptionDialog(null, "Choose an option for first hand", "Blackjack", 
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Hit", "Stay"}, "default");
                            if(choice==EXIT||choice==STAY)
                                z++;
                            if(choice==HIT){
                                System.out.print("\f");
                                theUser.getCard(deck.dealCard());
                                userSum=theUser.blackJackValue();
                                splitPrint();
                            }
                        } 
                        if(!secondHand.bust()){
                            choice = JOptionPane.showOptionDialog(null, "Choose an option for second hand", "Blackjack", 
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Hit", "Stay"}, "default");
                            if(choice==EXIT||choice==STAY)
                                z++;
                            if(choice==HIT){
                                System.out.print("\f");
                                secondHand.getCard(deck.dealCard());
                                secondHandSum=secondHand.blackJackValue();
                                splitPrint();
                            }
                        }
                        if((theUser.bust()&&secondHand.bust())||(z==2)||(theUser.bust()&&z==1)||(secondHand.bust()&&z==1)){ //condition to break;
                            //z=0;
                            //outcomes must still be addressed after dealer finishes
                            //break;
                        }
                        z=0;
                    }
                    else{
                        choice = JOptionPane.showOptionDialog(null, "Choose an option", "Blackjack",
                            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,null, buttons , buttons [0]);
                        if(choice==EXIT||choice==STAY)    //if user exits button panel or chooses stay 
                            break;
                        else if(choice==HIT){   //if user chooses hit
                            hit();
                            if(theUser.bust())
                                break;
                            count++;
                        }
                        else if(choice==DOUBLE){  //double
                            money=money-bet;
                            if(money<0){
                                System.out.println("You do not have enough money to double down!");
                                money=money+bet;
                            }
                            else{
                                bet=bet*2;
                                hit();
                                break;
                            }
                        }
                        else if(choice==SPLIT){  //split
                            split();
                            count++;
                        }
                        else if(choice==SURRENDER){  //surrender
                            if(count==0){
                                money=money+(bet/2);
                                break;
                            }
                            System.out.println("You can only surrender on the first decision of a hand!");
                        }
                    }
                }
                showDealerSecondCard=true;   //will show all of dealers cards
                while(dealerSum<DEALER_MIN&&!theUser.bust()){   //dealer's turn to hit till reach 17 or bust
                    printHands();                   //add slow print or slow change effect?
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
        String nums="9876543210";
        boolean isNumber=true;
        int i=0;
        while(true){
            theBet = JOptionPane.showInputDialog("You have $"+money+". What is your bet?");
            if(theBet==null||theBet.equals("0")||theBet.equals(""))
                break;
            for(int x=0;x<theBet.length()-1;x++){
                if(nums.indexOf(theBet.substring(x,x+1))==-1)
                    i++;
            }
            if(i==0){
                bet=Integer.parseInt(theBet);
                money=money-bet;
                if(money<0){
                    System.out.print("\f");
                    System.out.println("You don't have enough money to bet that amount!");
                    money=money+bet;
                }
                else
                    break;
            }
            i=0;
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

    private void splitPrint(){
        System.out.print("\f");
        System.out.print("User's first hand: ");
        theUser.printHand(); 
        System.out.print("User's second hand: ");
        secondHand.printHand();
        System.out.print("Dealer's hand: ");
        if(showDealerSecondCard)
            theDealer.printHand();
        else
            theDealer.printOneCard();
    }

    private void hit(){
        System.out.print("\f");
        theUser.getCard(deck.dealCard());
        printHands();
        userSum=theUser.blackJackValue();
    }

    private void split(){
        if(count==0&&theUser.canSplit()&&money>=bet){
            isSplit=true;        
            secondHand=new BlackJackHand();
            secondHand.getCard(theUser.giveCard());                         
            theUser.getCard(deck.dealCard());
            secondHand.getCard(deck.dealCard());
            secondBet=bet;
            money=money-secondBet;
        }
        else
            System.out.println("You cannot split now.");
    }

    private void outcome()
    {
        if(choice==SURRENDER)
            System.out.println("You surrendered. Half of your initial bet has been returned.");
        else if(theUser.bust()&&theDealer.bust()){
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

    private void resetTable()
    {
        System.out.print("\f");
        deck.createDeck();
        deck.shuffle();
        showDealerSecondCard=false;
        theUser.clearHand();
        theDealer.clearHand();
        count=0;
        isSplit=false;
        secondHand=null;
        secondBet=0;
        secondHandSum=0;
    }

    final int BLACK_JACK = 21;
    final int BLACK_JACK_HAND = 2;
    final int ACE = 1;
    final int JACK = 11;
    final int QUEEN = 12;
    final int KING = 13;
    final int DEALER_MIN = 17;
    final int EXIT=-1;
    final int HIT=0;
    final int STAY=1;
    final int DOUBLE=2;
    final int SPLIT=3;
    final int SURRENDER=4;
}
