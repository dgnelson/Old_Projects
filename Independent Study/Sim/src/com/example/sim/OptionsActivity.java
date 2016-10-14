package com.example.sim;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
//import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class OptionsActivity extends ActionBarActivity {

	private Spinner analyteSpinner, titrantSpinner;
	public final static String TITRANT = "com.example.sim.TITRANT";
	public final static String ANALYTE = "com.example.sim.ANALYTE";
	public final static String MOLARITY = "com.example.sim.MOLARITY";
	SeekBar anMolSeek;
	double analyteMolarity=0.01;
	TextView  anMolView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listenSeekBar();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
			if (checked)
				titrants=R.array.strong_acid;
			analytes=R.array.weak_bases;
			break;
		case R.id.sb_wa:
			if (checked)
				titrants=R.array.strong_base;
			analytes=R.array.weak_acids;
			break;
		case R.id.sa_sb:
			if (checked)
				titrants=R.array.strong_acid;
			analytes=R.array.strong_base;
			break;
		case R.id.sb_sa:
			if (checked)
				titrants=R.array.strong_base;
			analytes=R.array.strong_acid;
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
			anMolSeek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
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

	public void nextActivity(View view) {
		Object t=titrantSpinner.getSelectedItem();
		Object a=analyteSpinner.getSelectedItem();
		if(t==null||a==null)
			return;
		else{
			Intent intent = new Intent(this, Titration.class);
			intent.putExtra(TITRANT, String.valueOf(titrantSpinner.getSelectedItem()));
			intent.putExtra(ANALYTE, String.valueOf(analyteSpinner.getSelectedItem()));
			intent.putExtra(MOLARITY, anMolSeek.getProgress());
			startActivity(intent);
		}
	}
}
