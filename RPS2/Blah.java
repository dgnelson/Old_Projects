
import javax.swing.JFrame;
import java.awt.*; 
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Blah
{
    static int choice1=-1;
    static int choice2=-1;
    RPSComponent p1;
    RPSComponent p2;
    JFrame frame;
    public void main(String[] args)
    {

        frame = new JFrame("RPS");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container pane = frame.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        
        p1 = new RPSComponent(-1);
        p2 = new RPSComponent(-1);

        JLabel label = new JLabel("RPS");
        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel1.add(label);

        JLabel label2 = new JLabel("player 1 click once, player 2 click once");
        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel2.add(label2);

        JButton rockButton = new JButton("Rock");
        rockButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e){
                    if(choice1==-1){
                        p1.changeRPS(0);
                        frame.validate();
                    }
                    else{
                        p2.changeRPS(0);
                        frame.validate();
                        choice1=-1;
                    }
                }

            });      
        JButton paperButton = new JButton("Paper");
        paperButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e){
                    if(choice1==-1){
                        p1.changeRPS(1);
                        frame.validate();
                    }
                    else{
                        p2.changeRPS(1);
                        frame.validate();
                        choice1=-1;
                    }
                }

            });    
        JButton scissorsButton = new JButton("Scissors");
        scissorsButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e){
                    if(choice1==-1){
                        p1.changeRPS(2);
                        frame.validate();
                    }
                    else{
                        p2.changeRPS(2);
                        frame.validate();
                        choice1=-1;
                    }
                }

            });

        JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel3.add(rockButton);
        panel3.add(paperButton);
        panel3.add(scissorsButton);

        JLabel label3 = new JLabel("Player 1:");
        JLabel label4 = new JLabel("Player 2:");
        JPanel panel4 = new JPanel(new FlowLayout(FlowLayout.LEFT, 200, 0));
        panel4.add(label3);
        panel4.add(label4);

        
        p1.setPreferredSize(new Dimension(250,250));
        p2.setPreferredSize(new Dimension(250,250));
        JPanel panel5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel5.add(p1);
        panel5.add(p2);

        JButton playButton = new JButton("Play again?");
        JPanel panel6 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel6.add(playButton);

        pane.add(panel1);
        pane.add(panel2);
        pane.add(panel3);
        pane.add(panel4);
        pane.add(panel5);
        pane.add(panel6);

        frame.pack();
        frame.setVisible(true);
    }

}
