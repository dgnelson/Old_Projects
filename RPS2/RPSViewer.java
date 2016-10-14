
import javax.swing.JFrame;
import java.awt.*; 
import javax.swing.*;

public class RPSViewer
{

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("RPS");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        String[] buttons = {"Rock", "Paper", "Scissors"};

        int choice1=-1;
        int choice2=-1;
        RPSComponents rps=null;
        int play = JOptionPane.YES_OPTION;
        
        Container pane = frame.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        JLabel label1 = new JLabel("Player 1:");
        JLabel label2 = new JLabel("Player 2:");
        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 200, 0));
        panel1.add(label1);
        panel1.add(label2);
        pane.add(panel1);
        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        while(play==JOptionPane.YES_OPTION){
            if(rps!=null){
                panel2.remove(rps);
                pane.remove(panel2);
            }
            choice1 = JOptionPane.showOptionDialog(null, "Player 1", "RPS",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,null, buttons , buttons [0]);

            choice2 = JOptionPane.showOptionDialog(null, "Player 2", "RPS",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,null, buttons , buttons [0]);

            if(choice1==-1||choice2==-1)//||choice1==null||choice2==null){
                break;
            
           
            rps = new RPSComponents(choice1, choice2);
            rps.setPreferredSize(new Dimension(500,200));
            panel2.add(rps);
            pane.add(panel2);
            //frame.add(rps);
            frame.setVisible(true);

            play = JOptionPane.showConfirmDialog(null, "Play Again?", "", JOptionPane.YES_NO_OPTION);
        }
        System.out.println("Thanks for playing");
    }
}
