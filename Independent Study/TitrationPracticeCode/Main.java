
public class Main
{

    public Main(){

    }

    public static void main(String[] args){
        boolean addTitrant=false;
        // Button titrantButton;
        String titrant, analyte;
        double analyteMolarity;
        PhGraph graph;
        int combo;
        boolean add=false;

        titrant = "Acetic Acid"; //intent.getStringExtra(MyActivity.TITRANT);
        analyte = "Sodium Hydroxide"; //intent.getStringExtra(MyActivity.ANALYTE);
        analyteMolarity = .53; //intent.getDoubleExtra(MyActivity.MOLARITY, -1);
        combo = 1; //intent.getIntExtra(MyActivity.COMBO, -1);
        graph = new PhGraph();//(PhGraph) findViewById(R.id.the_graph);
        graph.setExperimentParameters(analyte, titrant, analyteMolarity, combo);
        graph.createData();
        
        //System.out.print((float)1.234/(double)14);
    }

}
