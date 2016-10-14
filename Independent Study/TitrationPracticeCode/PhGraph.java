
import java.util.ArrayList;

import java.util.jar.Attributes;

/**
 * Created by davidnelson on 10/4/14.
 */
public class PhGraph {//extends View{

    //     private Path sketchPath;
    //     private Paint sketchPaint, linePaint, canvasPaint, axisPaint;
    //     private int sketchingColour, lineColour, axisColour;
    //     private Canvas sketchCanvas;
    //     private Bitmap canvasBitmap;
    //     private float pointSize, lineSize, axisLineSize;
    //     private int viewWidth;
    //     private int viewHeight;
    //     private float minXPosition, maxXPosition, minYPosition, maxYPosition;
    //     private float sketchingWidth, sketchingHeight;

    private static double analyteVolume=20.0;        //constant regardless of parameters //fix, .020 L
    private static double titrantMolarity=1.0;
    private float analyteMolarity;      //received from user inputs from first activity
    private String analyte;
    private String titrant;
    private int combo;
    private float ka=-1;
    private float kb=-1;
    private static double kw=Math.pow(10,-14);  //1x10^-14, standard kw value
    private float molsOfAnalyte;
    private float addedTitrantVolume;
    private float maxVolume;//max amount of titrant that can be added
    private float maxPh=14;// max values are used to facilitate graphing
    private ArrayList<Point> dataPoints;

    boolean add=false;
    int endPoint=0;
    boolean parametersSet=false;
    final double graphPrecision=.01;
    float titrantVolume;

    public PhGraph(){
        onStart();
    }

    private void onStart(){

        //potentially change data structure(linkedList?)
        dataPoints=new ArrayList<Point>();

        //         this.viewHeight = 200;
        //         this.viewWidth = 200;
        //         canvasBitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);
        //         sketchCanvas = new Canvas(canvasBitmap);
        //         sketchPath = new Path();
        //         sketchPaint = new Paint();
        //         pointSize = 2; lineSize=2; axisLineSize=2;
        //         sketchingColour = Color.BLACK;
        //         sketchPaint.setColor(sketchingColour);
        //         sketchPaint.setAntiAlias(true);
        //         sketchPaint.setStrokeWidth(pointSize);
        //         sketchPaint.setStyle(Paint.Style.STROKE);
        //         sketchPaint.setStrokeJoin(Paint.Join.ROUND);
        //         sketchPaint.setStrokeCap(Paint.Cap.ROUND);
        //         canvasPaint = new Paint(Paint.DITHER_FLAG);
        //         lineColour = -7829368;
        //         linePaint = new Paint();
        //         linePaint.setColor(lineColour);
        //         lineSize = 2f;
        //         linePaint.setStrokeWidth(lineSize);
        //         linePaint.setAlpha(100);
        //         axisColour = Color.BLACK;
        //         axisPaint = new Paint();
        //         axisPaint.setColor(axisColour);
        //         axisPaint.setAntiAlias(true);
        //         axisPaint.setStrokeWidth(axisLineSize);
        //         axisPaint.setStyle(Paint.Style.STROKE);
        //         axisPaint.setStrokeJoin(Paint.Join.ROUND);
        //         axisPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    //must be called immediately after class is instantiated
    public void setExperimentParameters(String theAnalyte, String theTitrant, double theAnalyteMolarity, int theCombo){
        parametersSet=true;
        analyte=theAnalyte;
        titrant=theTitrant;
        analyteMolarity=(float)theAnalyteMolarity;
        combo=theCombo;//which radio button combination was chosen
        molsOfAnalyte=(float)analyteVolume*analyteMolarity;
        maxVolume=(float)(2*(analyteVolume*analyteMolarity)/titrantMolarity);
        if(combo==0||combo==1){
            if(analyte.equals("Acetic Acid")){
                ka=(float)(1.8*Math.pow(10,-5));  kb=(float)(kw/ka);   //ka value: 1.8x10^-5
            }
            if(analyte.equals("Hydroflouric Acid")){
                ka=(float)(7.2*Math.pow(10,-4));  kb=(float)(kw/ka);  //ka value: 7.2x10^-4
            }
            if(analyte.equals("Hydrocyanic Acid")){
                ka=(float)(6.2*Math.pow(10,-10));  kb=(float)(kw/ka); //ka value: 6.2x10^-10
            }
            if(analyte.equals("Ammonia")){
                kb=(float)(1.8*Math.pow(10,-5));  ka=(float)(kw/kb);  //kb value: 1.8x10^-5
            }
            if(analyte.equals("Pyridine")){
                kb=(float)(1.7*Math.pow(10,-9)); ka=(float)(kw/kb);   //kb value: 1.7x10^-9
            }
            if(analyte.equals("Methylamine")){
                kb=(float)(4.4*Math.pow(10,-4)); ka=(float)(kw/kb);   //kb value: 4.4x10^-4
            }
        }
    }

    public void createData(){
        if(!parametersSet)
            return;
        for(titrantVolume=0;titrantVolume*titrantMolarity<maxVolume;titrantVolume+=graphPrecision)
            dataPoints.add(new Point(titrantVolume, getPh(titrantVolume)));
        for(int n = 0; n<dataPoints.size();n++)
            System.out.println(dataPoints.get(n).getX()+" "+dataPoints.get(n).getY()+" ");
    }

    public float getPh(double titrantVolume){ //to be called in activity 2 from button listener
        addedTitrantVolume=(float)titrantVolume;
        double molsOfTitrant=addedTitrantVolume*titrantMolarity;
        double xValue=addedTitrantVolume;
        double yValue=-1;
        double totalVolume=analyteVolume+addedTitrantVolume;
        if(combo==2){//strong acid analyte
            if(molsOfAnalyte>molsOfTitrant)
                yValue=-Math.log10((molsOfAnalyte-molsOfTitrant)/totalVolume);
            else if(molsOfAnalyte<molsOfTitrant)
                yValue=14+Math.log10((molsOfTitrant-molsOfAnalyte)/totalVolume);
        }
        else if(combo==3){//strong base analyte
            if(molsOfAnalyte>molsOfTitrant)
                yValue=14+Math.log10((molsOfAnalyte-molsOfTitrant)/totalVolume);
            else if(molsOfAnalyte<molsOfTitrant)
                yValue=-Math.log10((molsOfTitrant-molsOfAnalyte)/totalVolume);
        }
        else if(combo==1){//weak acid analyte
            if(molsOfAnalyte>molsOfTitrant)
                yValue=-Math.log10(ka)+Math.log10(molsOfTitrant/(molsOfAnalyte-molsOfTitrant));//Henderson-Hasselbach Equation
            else if(molsOfAnalyte<molsOfTitrant)
                yValue=14+Math.log10((molsOfTitrant-molsOfAnalyte)/totalVolume);//effects of A- are negligible
        }
        else if(combo==0){//ask Mr. P
            if(molsOfAnalyte>molsOfTitrant)
                yValue=14-(-Math.log10(kb)+Math.log10(molsOfTitrant/(molsOfAnalyte-molsOfTitrant)));
            else if(molsOfAnalyte<molsOfTitrant)
                yValue=-Math.log10((molsOfTitrant-molsOfAnalyte)/totalVolume);
        }
        return (float)yValue;
    }

}
