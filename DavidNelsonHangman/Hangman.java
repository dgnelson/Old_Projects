import javax.swing.JOptionPane;
import java.util.Random;
public class Hangman
/**
 *@author David Nelson 
 */
{
    String word = "";   //word that is trying to be guessed
    String progress = ""; //part of word that has already been guessed
    String letter = ""; // guessed letter
    String guess = ""; //guess what the word is
    String guessedLetters = ""; //string of all the letters guessed
    String randomWords = "  string integer double boolean static void character pointer method class "; //possible random words that could be chosen
    int mode;  //pick word or random word
    int guessWord; //yes or no option to guess the word
    int play = JOptionPane.YES_OPTION; // yes or no option to play hangman
    int b; //increments through printing the guessed letters
    int d; //stores index of space character while incrementing
    int e; //increments through the spaces in the randomWords string
    int x; //increments through word while checking for instances of letter
    int y; //stores number of wrong letters guessed and represents an ascii picture
    int z; //increments through progress while printing spaces between characters
    public Hangman()
    {

    }

    public void playHangman()
    {
        while (play == JOptionPane.YES_OPTION)
        {
            System.out.println("\f");
            //picks mode
            mode = JOptionPane.showOptionDialog(null, "Pick word or random?", "", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Pick", "Random"}, "default");
            if (mode == JOptionPane.NO_OPTION)
                pickWord();
            else
            {
                //enter word for hangman
                while(word==null||word.length()==0||word.indexOf(" ")!=-1)
                {
                    word = JOptionPane.showInputDialog("Enter a word:");
                }
                word.toLowerCase();
            }
            //initally stores progress as an equal string length as word but with all underscores
            while (progress.length()!=word.length())
                progress = progress+"_";
            //the actual process of playing hangman
                while (!progress.equals(word))
            {
                //guessing a letter
                while (letter==null||letter.length()!=1)
                {
                    letter = JOptionPane.showInputDialog("Guess a letter:");
                    if (letter==null)
                    {
                        youLose();
                        break;
                    }
                    letter.toLowerCase();
                }
                if (letter==null)
                    break;
                //checking for instances of guessed letter in word
                while (word.length()>x)
                {
                    if (letter.equals(word.substring(x,x+1)))
                    {
                        addLetter();
                    }
                    x = x+1;
                }
                //stores if incorrect letter was guessed
                if (word.indexOf(letter)==-1)
                {
                    addBodyPart();
                }
                //shows result of guessed letter
                System.out.println("\f");
                printPicture();
                printWord();
                printGuessedLetters();
                //allows user to guess the word
                if (!progress.equals(word))
                    guessWord = JOptionPane.showConfirmDialog(null, "Guess Word?", "", JOptionPane.YES_NO_OPTION);
                if (guessWord == JOptionPane.YES_OPTION)
                {
                    guess = JOptionPane.showInputDialog("What is your guess?");
                    if (guess!=null)
                        guess.toLowerCase();
                    if (guess!=null&&guess.equals(word))
                    { 
                        youWin();
                        break;
                    }
                    else
                    {    
                        youLose();
                        break;
                    }
                }
                //checks if maximum amount of incorrect guesses was made
                if (y == 7)
                {
                    youLose();
                    break;
                }
                //checks if the you guessed all the correct letters
                if(progress.equals(word))
                {
                    youWin();
                    break;
                }
                //resets values to check for instance of another letter
                x = 0;
                letter = "";
            }
            //resets the game in case the user wants to play again
            word = "";
            progress = "";
            guess = "";
            x = 0;
            y = 0;
            letter = "";
            guessedLetters = "";
            play = JOptionPane.showConfirmDialog(null, "Play Again?", "", JOptionPane.YES_NO_OPTION);
        }
    }

    private void pickWord()
    //picks a random word from a string of words
    {
        Random rand = new Random();
        int n = rand.nextInt(10);
        d = 0;
        e = 0;
        while (n>=e)
        {
            d = randomWords.indexOf(" ", d+1);
            e = e+1;
        }
        word = randomWords.substring(d+1, randomWords.indexOf(" ", d+1));
    }

    private void addLetter()
    //adds correctly guessed letters to the progress string
    {
        if (x == 0)
            progress = letter+progress.substring(1);
        else
            progress = progress.substring(0,x)+letter+progress.substring(x+1);
    }

    private void addBodyPart()
    //changes y value to display the correct ascii picture
    {
        y = y+1;
    }

    private void printWord()
    //prints the progress string with spaces between each character
    {
        System.out.println();
        while (progress.length()>z)
        {
            System.out.print(progress.charAt(z)+" ");
            z = z+1;
        }
        z = 0;
    }

    private void printGuessedLetters()
    //prints all of the letters already guessed
    {
        if (guessedLetters.indexOf(letter)==-1)
            guessedLetters = guessedLetters+letter;
        System.out.println();
        System.out.println("Guessed Letters: ");
        while (guessedLetters.length()>b)
        {
            System.out.print(guessedLetters.charAt(b)+" ");
            b = b+1;
        }
        b = 0;
    }

    private void youWin()
    //end screen if user wins
    {
        System.out.println("\f");
        y = -1;
        printPicture();
        System.out.println("You Win");
        System.out.println("The word was "+word);
    }

    private void youLose()
    //end screen if user loses
    {
        System.out.println("\f");
        y = 7;
        printPicture();
        System.out.println("You Lose");
        System.out.println("The correct word was "+word);
    }

    private void printPicture()
    //prints respective ascii picture that correlates to the number of incorrect letters guessed
    {
        if (y == 0){
            System.out.println();
            System.out.println("(\\__.-. |");
            System.out.println("== ===_]+");
            System.out.println("        |");
            System.out.println();
            System.out.println();           
            System.out.println();     
            System.out.println();      
            System.out.println();     
            System.out.println();      
            System.out.println("    ~~");
            System.out.println("   ~");
            System.out.println(" _u__");
            System.out.println("/____\\");
            System.out.println("|[][]|");
            System.out.println("|[]..|");
            System.out.println("'--'''");
        }
        else if(y == 1){
            System.out.println();
            System.out.println("(\\__.-. |");
            System.out.println("== ===_]+");
            System.out.println("        |");
            System.out.println("   ,-*");
            System.out.println("  (_)    ");
            System.out.println();            
            System.out.println();      
            System.out.println();      
            System.out.println();      
            System.out.println();      
            System.out.println("    ~~");
            System.out.println("   ~");
            System.out.println(" _u__");
            System.out.println("/____\\");
            System.out.println("|[][]|");
            System.out.println("|[]..|");
            System.out.println("'--'''");
        }
        else if(y == 2){
            System.out.println();
            System.out.println("   ,-*");
            System.out.println("  (_)");                
            System.out.println();      
            System.out.println();      
            System.out.println();      
            System.out.println("    ~~");
            System.out.println("   ~");
            System.out.println(" _u__");
            System.out.println("/____\\");
            System.out.println("|[][]|");
            System.out.println("|[]..|");
            System.out.println("'--'''");
        }
        else if(y == 3){
            System.out.println();
            System.out.println("   ,-*");
            System.out.println("  (_)");                
            System.out.println();      
            System.out.println();      
            System.out.println("    ~~");
            System.out.println("   ~");
            System.out.println(" _u__");
            System.out.println("/____\\");
            System.out.println("|[][]|");
            System.out.println("|[]..|");
            System.out.println("'--'''");
        }
        else if(y == 4){
            System.out.println();
            System.out.println("   ,-*");
            System.out.println("  (_)");                
            System.out.println();      
            System.out.println("    ~~");
            System.out.println("   ~");
            System.out.println(" _u__");
            System.out.println("/____\\");
            System.out.println("|[][]|");
            System.out.println("|[]..|");
            System.out.println("'--'''");
        }
        else if(y == 5){
            System.out.println();   
            System.out.println("   ,-*");
            System.out.println("  (_)");                
            System.out.println("    ~~");
            System.out.println("   ~");
            System.out.println(" _u__");
            System.out.println("/____\\");
            System.out.println("|[][]|");
            System.out.println("|[]..|");
            System.out.println("'--'''");
        }
        else if(y == 6){
            System.out.println();
            System.out.println("   ,-*");
            System.out.println("  (_)");                
            System.out.println("   ~");
            System.out.println(" _u__");
            System.out.println("/____\\");
            System.out.println("|[][]|");
            System.out.println("|[]..|");
            System.out.println("'--'''");
        }
        else if(y == 7){
            System.out.println();
            System.out.println("   ..-^~~~^-..");
            System.out.println(" .~           ~.");
            System.out.println("(;:           :;)");
            System.out.println(" (:           :)");
            System.out.println("   ':._   _.:'");
            System.out.println("       | |");
            System.out.println("     (=====)");
            System.out.println("       | |");
            System.out.println("       | |");
            System.out.println("       | |");
            System.out.println("    ((/   \\))");
        }
        else if(y == -1)
        {
            System.out.println(" ...../ )");
            System.out.println(".....' /");
            System.out.println(" ---' (_____");
            System.out.println(".........((__)");
            System.out.println(" ..... _ ((___)");
            System.out.println(".......-'((__)");
            System.out.println("   --.___((_)");
        }
    }
}
