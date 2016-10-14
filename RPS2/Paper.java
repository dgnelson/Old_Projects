import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.Color;


public class Paper extends Hand{
    private double xLeft;
    private double yTop;

    public Paper(double x, double y){
        xLeft = x;
        yTop = y;
    }

    public void draw(Graphics2D g2){
        
        double rectHeight=110;
        double rectWidth=80;
        double endSidePaper=xLeft+rectWidth;
        double endBotPaper=yTop+rectHeight;
        Rectangle2D.Double rect = new Rectangle2D.Double(xLeft, yTop, 80, 110);

        int numLines=26;
        double topSpace=20;
        double botSpace=10;
        //double sideSpace=10;
        double lineSpace=(rectHeight-topSpace)/numLines;//int lineSpace=5;
        Line2D.Double[] lines = new Line2D.Double[numLines];
        for(int num=numLines-1; num>=0;num--){
            lines[num] = new Line2D.Double(xLeft, yTop+topSpace+lineSpace*num,
                            endSidePaper, yTop+topSpace+lineSpace*num);
        }
        
        g2.draw(rect);
        g2.setColor(Color.BLUE);
        for(int i=0;i<numLines;i++){
            g2.draw(lines[i]);
        }
        
        g2.setColor(Color.RED);
        Line2D.Double vertLine = new Line2D.Double(xLeft+rectWidth/10, yTop,
                            xLeft+rectWidth/10, endBotPaper);
        
        g2.draw(vertLine);
        
        g2.setColor(Color.BLACK);
    }
}
