package com.example.davidnelson.titration_simulation;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Intent;


public class MyActivity extends Activity {

    private Spinner analyteSpinner, titrantSpinner;
    public final static String TITRANT = "com.example.davidnelson.titration_simulation.TITRANT";
    public final static String ANALYTE = "com.example.davidnelson.titration_simulation.ANALYTE";
    public final static String MOLARITY = "com.example.davidnelson.titration_simulation.MOLARITY";
    public final static String COMBO = "com.example.davidnelson.titration_simulation.COMBO";
    SeekBar anMolSeek;
    double analyteMolarity=0.01;
    TextView anMolView;
    int combo=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        listenSeekBar();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
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

    public void onModeClicked(View view) {
        int titrants=0;
        int analytes=0;
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.sa_wb:
                if (checked){
                    titrants=R.array.strong_acid;
                    analytes=R.array.weak_bases;
                    combo=0;
                }
                break;
            case R.id.sb_wa:
                if (checked){
                    titrants=R.array.strong_base;
                    analytes=R.array.weak_acids;
                    combo=1;
                }
                break;
            case R.id.sa_sb:
                if (checked){
                    titrants=R.array.strong_acid;
                    analytes=R.array.strong_base;
                    combo=2;
                }
                break;
            case R.id.sb_sa:
                if (checked){
                    titrants=R.array.strong_base;
                    analytes=R.array.strong_acid;
                    combo=3;
                }
                break;
        }

        titrantSpinner = (Spinner) findViewById(R.id.titrant_spinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                titrants, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        titrantSpinner.setAdapter(adapter1);

        analyteSpinner = (Spinner) findViewById(R.id.analyte_spinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                analytes, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        analyteSpinner.setAdapter(adapter2);
    }

    public void listenSeekBar(){
        anMolView = (TextView) findViewById(R.id.displayMolarityAnalyte);
        anMolSeek = (SeekBar) findViewById(R.id.analyteMolaritySeekBar);
        anMolSeek.setMax(100);
        //anMolSeek.setProgress(50);
        try{
            anMolSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onStopTrackingTouch(SeekBar arg0) {
                }
                public void onStartTrackingTouch(SeekBar arg0) {
                }
                public void onProgressChanged(SeekBar arg0,
                                              int progress, boolean arg2) {
                    analyteMolarity=progress;
                    analyteMolarity/=100;
                    if(progress==0)
                        analyteMolarity=.01;
                    anMolView.setText(""+analyteMolarity);
                }
            });
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void startTitration(View view){
        Object t=titrantSpinner.getSelectedItem();
        Object a=analyteSpinner.getSelectedItem();
        if(t==null||a==null)
            return;
        else{
            Intent intent = new Intent(this, MyActivity2.class);
            intent.putExtra(TITRANT, String.valueOf(titrantSpinner.getSelectedItem()));
            intent.putExtra(ANALYTE, String.valueOf(analyteSpinner.getSelectedItem()));
            intent.putExtra(MOLARITY, anMolSeek.getProgress());
            intent.putExtra(COMBO,combo);
            startActivity(intent);
        }
    }
}
