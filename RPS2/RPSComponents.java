import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import javax.swing.*;

public class RPSComponents extends JComponent{

    private int player1;
    private int player2;

    public RPSComponents(int choice1, int choice2){
        player1=choice1;
        player2=choice2;
    }

    public void paintComponent(Graphics g){  
        Graphics2D g2 = (Graphics2D) g;
        
        Hand[] rps1 = {new Rock(getWidth()/8, getHeight()/2),
                new Paper(getWidth()/8, getHeight()/2), new Scissors(getWidth()/8, getHeight()/2)};
        Hand[] rps2 = {new Rock(getWidth()/2, getHeight()/2),
                new Paper(getWidth()/2, getHeight()/2), new Scissors(getWidth()/2, getHeight()/2)};

        rps1[player1].draw(g2);
        rps2[player2].draw(g2);

    }
    
}
