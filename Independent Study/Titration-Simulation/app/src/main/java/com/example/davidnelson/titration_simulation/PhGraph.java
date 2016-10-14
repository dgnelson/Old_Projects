package com.example.davidnelson.titration_simulation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;

import java.util.jar.Attributes;

/**
 * Created by davidnelson on 10/4/14.
 */
public class PhGraph extends View{

    private Path sketchPath;
    private Paint sketchPaint, linePaint, canvasPaint, axisPaint;
    private int sketchingColour, lineColour, axisColour;
    private Canvas sketchCanvas;
    private Bitmap canvasBitmap;
    private float pointSize, lineSize, axisLineSize;
    private int viewWidth;
    private int viewHeight;
    private float minXPosition, maxXPosition, minYPosition, maxYPosition;
    private float sketchingWidth, sketchingHeight;

    private static double analyteVolume=20.0;        //constant regardless of parameters
    private static double titrantMolarity=1.0;

    private double analyteMolarity;      //received from user inputs from first activity
    private String analyte;
    private String titrant;
    private int combo;
    private double ka=-1;
    private double kb=-1;
    private static double kw=Math.pow(10,-14);  //1x10^-14, standard kw value
    private double molsOfAnalyte;
    private double addedTitrantVolume;
    private double maxVolume;//max amount of titrant that can be added
    private double maxPh=14;// max values are used to facilitate graphing
    private ArrayList<Point> dataPoints;

    public PhGraph(Context context, AttributeSet attrs){
        super(context, attrs);
        onStart();
    }

    private void onStart(){

        //potentially change data structure(linkedList?)
        dataPoints=new ArrayList<Point>();

        this.viewHeight = this.getHeight();
        this.viewWidth = this.getLayoutParams().width;
        canvasBitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);
        sketchCanvas = new Canvas(canvasBitmap);
        sketchPath = new Path();
        sketchPaint = new Paint();
        pointSize = 5; lineSize=2; axisLineSize=2;
        sketchingColour = Color.BLACK;
        sketchPaint.setColor(sketchingColour);
        sketchPaint.setAntiAlias(true);
        sketchPaint.setStrokeWidth(pointSize);
        sketchPaint.setStyle(Paint.Style.STROKE);
        sketchPaint.setStrokeJoin(Paint.Join.ROUND);
        sketchPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
        lineColour = -7829368;
        linePaint = new Paint();
        linePaint.setColor(lineColour);
        lineSize = 2f;
        linePaint.setStrokeWidth(lineSize);
        linePaint.setAlpha(100);
        axisColour = Color.BLACK;
        axisPaint = new Paint();
        axisPaint.setColor(axisColour);
        axisPaint.setAntiAlias(true);
        axisPaint.setStrokeWidth(axisLineSize);
        axisPaint.setStyle(Paint.Style.STROKE);
        axisPaint.setStrokeJoin(Paint.Join.ROUND);
        axisPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    //must be called immediately after class is instantiated
    public void setExperimentParameters(String theAnalyte, String theTitrant, double theAnalyteMolarity, int theCombo){
        analyte=theAnalyte;
        titrant=theTitrant;
        analyteMolarity=theAnalyteMolarity;
        combo=theCombo;//which radio button combination was chosen
        molsOfAnalyte=analyteVolume*analyteMolarity;
        maxVolume=2*(analyteVolume*analyteMolarity)/titrantMolarity;
        double firstValue=-1;
        if(combo==0||combo==1){
            if(analyte.equals("Acetic Acid"))
                ka=1.8*Math.pow(10,-5);  kb=kw/ka;  //ka value: 1.8x10^-5
            if(analyte.equals("Hydroflouric Acid"))
                ka=7.2*Math.pow(10,-4);  kb=kw/ka;  //ka value: 7.2x10^-4
            if(analyte.equals("Hydrocyanic Acid"))
                ka=6.2*Math.pow(10,-10);  kb=kw/ka; //ka value: 6.2x10^-10
            if(analyte.equals("Ammonia"))
                kb=1.8*Math.pow(10,-5);  ka=kw/kb;  //kb value: 1.8x10^-5
            if(analyte.equals("Pyridine"))
                kb=1.7*Math.pow(10,-9); ka=kw/kb;   //kb value: 1.7x10^-9
            if(analyte.equals("Methylamine"))
                kb=4.4*Math.pow(10,-4); ka=kw/kb;   //kb value: 4.4x10^-4
            if(combo==1)
                firstValue=-Math.log10(Math.sqrt(ka*analyteMolarity));
            if(combo==0)
                firstValue=14+Math.log10(Math.sqrt(kb*analyteMolarity));
        }
        else if(combo==3){
            firstValue=-Math.log10(analyteMolarity);//first data point, no titrant added
        }
        else if(combo==2){
            firstValue=14+Math.log10(analyteMolarity);//first data point, no titrant added
        }
        dataPoints.add(new Point(0,firstValue));
    }

    public void addPoint(double titrantVolume){ //to be called in activity 2 from button listener
        addedTitrantVolume=titrantVolume;
        double molsOfTitrant=addedTitrantVolume*titrantMolarity;
        double xValue=addedTitrantVolume;
        double yValue=-1;
        double totalVolume=analyteVolume+addedTitrantVolume;
        if(combo==2){//strong acid analyte
            if(molsOfAnalyte>molsOfTitrant)
                yValue=-Math.log10((molsOfAnalyte-molsOfTitrant)/totalVolume);
            else if(molsOfAnalyte<molsOfTitrant)
                yValue=14+Math.log10((molsOfTitrant-molsOfAnalyte)/totalVolume);
            else
                yValue=7;   //Equivalence point, pH of water
        }
        else if(combo==3){//strong base analyte
            if(molsOfAnalyte>molsOfTitrant)
                yValue=14+Math.log10((molsOfAnalyte-molsOfTitrant)/totalVolume);
            else if(molsOfAnalyte<molsOfTitrant)
                yValue=-Math.log10((molsOfTitrant-molsOfAnalyte)/totalVolume);
            else
                yValue=7;   //Equivalence point, pH of water
        }
        else if(combo==1){//weak acid analyte
            if(molsOfAnalyte>molsOfTitrant)
                yValue=-Math.log10(ka)+Math.log10(molsOfTitrant/(molsOfAnalyte-molsOfTitrant));//Henderson-Hasselbach Equation
            else if(molsOfAnalyte<molsOfTitrant)
                yValue=14+Math.log10((molsOfTitrant-molsOfAnalyte)/totalVolume);//effects of A- are negligible
            else
                yValue=14+Math.log10(Math.sqrt(kb*(molsOfTitrant/totalVolume)));//Equivalence point. pH=14-pOH, pOH=-log((kb*M)^1/2)
        }
        else if(combo==0){//ask Mr. P
            if(molsOfAnalyte>molsOfTitrant)
                yValue=14+Math.log10(kb)+Math.log10(molsOfTitrant/(molsOfAnalyte-molsOfTitrant));
            else if(molsOfAnalyte<molsOfTitrant)
                yValue=-Math.log10((molsOfTitrant-molsOfAnalyte)/totalVolume);
            else
                yValue=-Math.log10(Math.sqrt(ka*(molsOfTitrant/totalVolume)));
        }
        dataPoints.add(new Point(xValue,yValue));
    }



    public void onDraw(Canvas canvas) {

        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(sketchPath, sketchPaint);

        canvas.drawRect(0f, 0f, viewWidth, viewHeight, axisPaint);

        float startYPosition = viewHeight*0.05f;
        float endYPosition = viewHeight*0.92f;
        float xValue = viewWidth*0.05f;
        canvas.drawLine(xValue, startYPosition, xValue, endYPosition, axisPaint);
        float startXPosition = viewWidth*0.05f;
        float endXPosition = viewWidth*0.95f;
        float yValue = viewHeight*0.92f;
        canvas.drawLine(startXPosition, yValue, endXPosition, yValue, axisPaint);
        this.minXPosition = startXPosition;
        this.maxXPosition = endXPosition;
        this.minYPosition = startYPosition;
        this.maxYPosition = endYPosition;
        this.sketchingHeight = maxYPosition - minYPosition;
        this.sketchingWidth= maxXPosition - minYPosition;

        for(int num=0;num<dataPoints.size();num++){
            Point pt=dataPoints.get(num);
            canvas.drawPoint((float)(pt.getX()/maxVolume)*this.sketchingWidth,(float)(pt.getY()/maxPh)*this.sketchingHeight,sketchPaint);
        }
        //for loop plots the grid lines onto the graph area
        //for (float i = startXPosition; i < sketchingWidth+startXPosition; i += (sketchingWidth / numberOfGridPoints)) { //change this to delta x at some point in the future
        //    canvas.drawLine(i, startYPosition, i, endYPosition, linePaint);
        //}//end of for loop for drawing lines


        //draw the data points to the screen
        //for (int i = 0; i < dataPointArray.length; i++) {
         //   canvas.drawPoint(dataPointArray[i].getMidX(), dataPointArray[i].getYPosition(), sketchPaint);
        //}//end of for loop for drawing points

    }


}
