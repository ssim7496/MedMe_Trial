package com.example.siyo_pc.medme_trial;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.siyo_pc.medme_trial.classes.MM_Symptom;
import com.example.siyo_pc.medme_trial.db.MedMe_Helper;


public class GuestSymptomView extends ActionBarActivity {

    MedMe_Helper medMeDB = null;
    TextView symptomTitle, symptomDesc, symptomGreekName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_symptom_view);

        medMeDB = new MedMe_Helper(this);
        getSymptomInformation();
    }

    public void getSymptomInformation() {
        symptomTitle = (TextView)findViewById(R.id.tvSymptomTitle);
        symptomDesc = (TextView)findViewById(R.id.tvSymptomDescription);
        symptomGreekName = (TextView)findViewById(R.id.tvSymptomGreekName);

        Intent intent = getIntent();
        String diss = intent.getStringExtra("symptom");

        MM_Symptom symptom = medMeDB.GetSymptomByID(Integer.parseInt(diss));

        symptomTitle.setText(symptom.GetSymptomName());
        symptomDesc.setText(symptom.GetSymptomDesc());
        symptomGreekName.setText(symptom.GetGreekName());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guest_symptom_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
