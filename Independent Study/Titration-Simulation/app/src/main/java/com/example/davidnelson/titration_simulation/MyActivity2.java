package com.example.davidnelson.titration_simulation;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.content.Intent;


public class MyActivity2 extends Activity {

    View theGraph;
    LinearLayout graphHolder;
    boolean addTitrant=false;
    Button titrantButton;
    String titrant, analyte;
    double analyteMolarity;
    PhGraph graph;
    int combo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_activity2);
        Intent intent=getIntent();
        titrant=intent.getStringExtra(MyActivity.TITRANT);
        analyte=intent.getStringExtra(MyActivity.ANALYTE);
        analyteMolarity=intent.getDoubleExtra(MyActivity.MOLARITY,-1);
        combo=intent.getIntExtra(MyActivity.COMBO,-1);
        setContentView(R.layout.activity_my_activity2);

        graph=(PhGraph) findViewById(R.id.graph);
        graph.setExperimentParameters(analyte,titrant,analyteMolarity,combo);
        //for(int x=0;x<200;x++){
        //    graph.addPoint((double)x*.2);
        //}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void toggleTirant(){
        addTitrant=!addTitrant;
        if(addTitrant){
            titrantButton.setText("stop titrant");
        }
        else{
            titrantButton.setText("add titrant");
        }
    }
}