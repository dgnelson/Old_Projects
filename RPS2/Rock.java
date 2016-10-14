
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.Color;

public class Rock extends Hand{

    private double xLeft;
    private double yTop;

    public Rock(double x, double y){
        xLeft = x;
        yTop = y;
    }

    public void draw(Graphics2D g2){
        double ellipseHeight=100;
        double ellipseWidth=150;
        double endEllipseHeight=yTop+ellipseHeight;
        double endEllipseWidth=xLeft+ellipseWidth;

        int[] x = {(int)xLeft, (int)xLeft+20, (int)xLeft+30, (int)xLeft+50, (int)xLeft+55, 
                (int)xLeft+80, (int)xLeft+100, (int)xLeft+100, (int)xLeft+10, (int)xLeft};

        int[] y = {(int)yTop+50, (int)yTop+30, (int)yTop, (int)yTop, (int)yTop+10, 
                (int)yTop+20, (int)yTop+40, (int)yTop+60, (int)yTop+60, (int)yTop+55};

        g2.setColor(Color.GRAY);
        g2.fillPolygon(x,y,10);
        g2.setColor(Color.BLACK);
        g2.drawPolygon(x,y,10);
        
    }

}
