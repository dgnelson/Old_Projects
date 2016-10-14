import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import javax.swing.*;

public class RPSComponent extends JComponent{

    private int player;

    public RPSComponent(int choice){
        player=choice;
    }

    public void paintComponent(Graphics g){  
        Graphics2D g2 = (Graphics2D) g;
        
        if(player==-1){
            Blank blank = new Blank(getWidth()/4, getHeight()/2);
            blank.draw(g2);
            return;
        }
        
        Hand[] rps = {new Rock(getWidth()/4, getHeight()/2),
                new Paper(getWidth()/4, getHeight()/2), new Scissors(getWidth()/4, getHeight()/2)};
                
        rps[player].draw(g2);
        

    }
    
    public void changeRPS(int x){
        player=x;
    }
    
}
