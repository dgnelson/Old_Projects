
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.Color;
public class Blank extends Hand{
   
    private double xLeft;
    private double yTop;
    
    public Blank(double x, double y){
        xLeft = x;
        yTop = y;
    }

    public void draw(Graphics2D g2){
        Rectangle2D.Double rect = new Rectangle2D.Double(xLeft, yTop, xLeft, yTop);
        g2.draw(rect);
    }
    
}
