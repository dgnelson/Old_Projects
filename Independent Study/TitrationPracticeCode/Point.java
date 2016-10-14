 

/**
 * Created by davidnelson on 10/12/14.
 * A class to simulate points
 */
public class Point {

    double x;
    double y;

    public Point(double xValue, double yValue){
        x=xValue;
        y=yValue;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public void setX(double newX){
        x=newX;
    }

    public void setY(double newY){
        y=newY;
    }

    public void setCoordinates(double newX, double newY){
        x=newX;
        y=newY;
    }
}
