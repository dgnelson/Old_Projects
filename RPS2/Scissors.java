
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.Color;

public class Scissors extends Hand{
    private double xLeft;
    private double yTop;

    public Scissors(double x, double y){
        xLeft = x;
        yTop = y;
    }

    public void draw(Graphics2D g2){
        double outEllDia=50;
        double inEllDia=40;
        double inEllShift= (outEllDia-inEllDia)/2;
        double upBladeStartX=xLeft+outEllDia;
        double upBladeStartY=yTop+outEllDia/4;
        double downBladeStartX=xLeft+outEllDia;
        double downBladeStartY=yTop+outEllDia*(3.0/4.0);

        double bladeStopX=xLeft+outEllDia*4;
        double bladeStopY=yTop+outEllDia/2;
        
        double translate = outEllDia;

        Ellipse2D.Double outEllipse = new Ellipse2D.Double(xLeft, yTop, 
                outEllDia,outEllDia);
        Ellipse2D.Double inEllipse = new Ellipse2D.Double(inEllShift+xLeft, inEllShift+yTop, 
                inEllDia,inEllDia);
        Line2D.Double upBladeSide = new Line2D.Double(upBladeStartX, upBladeStartY,
                bladeStopX, bladeStopY+translate);
        Line2D.Double downBladeSide = new Line2D.Double(downBladeStartX, downBladeStartY,
                bladeStopX, bladeStopY+translate);

        g2.setColor(Color.RED);
        g2.fill(outEllipse);
        g2.setColor(Color.WHITE);
        g2.fill(inEllipse);

        g2.setColor(Color.GRAY);
        int[] x = {(int)upBladeStartX,(int)downBladeStartX,(int)bladeStopX};
        int[] y = {(int)upBladeStartY,(int)downBladeStartY,(int)bladeStopY+(int)translate};
        g2.fillPolygon(x,y,3);

        g2.setColor(Color.BLACK);
        g2.draw(outEllipse);  
        g2.draw(inEllipse);  
        g2.draw(upBladeSide);
        g2.draw(downBladeSide);

        
        Ellipse2D.Double outEllipse2 = new Ellipse2D.Double(xLeft, yTop+translate, 
                outEllDia,outEllDia);
        Ellipse2D.Double inEllipse2 = new Ellipse2D.Double(inEllShift+xLeft, inEllShift+yTop+translate, 
                inEllDia,inEllDia);
        Line2D.Double upBladeSide2 = new Line2D.Double(upBladeStartX, upBladeStartY+translate,
                bladeStopX, bladeStopY);
        Line2D.Double downBladeSide2 = new Line2D.Double(downBladeStartX, downBladeStartY+translate,
                bladeStopX, bladeStopY);

        g2.setColor(Color.RED);
        g2.fill(outEllipse2);
        g2.setColor(Color.WHITE);
        g2.fill(inEllipse2);

        g2.setColor(Color.GRAY);
        int[] x2 = {(int)upBladeStartX,(int)downBladeStartX,(int)bladeStopX};
        int[] y2 = {(int)upBladeStartY+(int)translate,(int)downBladeStartY+(int)translate,(int)bladeStopY};
        g2.fillPolygon(x2,y2,3);

        g2.setColor(Color.BLACK);
        g2.draw(outEllipse2);  
        g2.draw(inEllipse2);  
        g2.draw(upBladeSide2);
        g2.draw(downBladeSide2);
        g2.setColor(Color.BLACK);

    }
}
