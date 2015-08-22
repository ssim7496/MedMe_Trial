package com.example.siyo_pc.medme_trial;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.siyo_pc.medme_trial.adapters.SymptomAdapter;
import com.example.siyo_pc.medme_trial.classes.MM_Disease;
import com.example.siyo_pc.medme_trial.classes.MM_Symptom;
import com.example.siyo_pc.medme_trial.db.MedMe_Helper;

import java.util.ArrayList;


public class GuestDiseaseView extends ActionBarActivity {

    MedMe_Helper medMeDB = null;
    TextView diseaseTitle, diseaseGreekName, diseaseDesc;
    ListView lstSymptoms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_disease_view);

        medMeDB = new MedMe_Helper(this);
        getDiseaseInformation();
    }

    public void getDiseaseInformation() {
        diseaseTitle = (TextView)findViewById(R.id.tvDiseaseTitle);
        diseaseGreekName = (TextView)findViewById(R.id.tvDiseaseGreekName);
        diseaseDesc = (TextView)findViewById(R.id.tvDiseaseDescription);

        Intent intent = getIntent();
        String diss = intent.getStringExtra("disease");

        MM_Disease disease = medMeDB.GetDiseaseByID(Integer.parseInt(diss));

        diseaseTitle.setText(disease.GetDiseaseName());
        diseaseGreekName.setText("Greek Name: \n" + disease.GetGreekName());
        diseaseDesc.setText("Disease Description: \n" + disease.GetDiseaseDesc());

        ArrayList<MM_Symptom> symptomList = medMeDB.GetAllSymptomsForDisease(disease.GetDiseaseID());

        if (symptomList != null) {
            SymptomAdapter adapter = new SymptomAdapter(this, symptomList);
            lstSymptoms = (ListView) findViewById(R.id.listViewGuestSymptomDiseaseLink);
            View header = getLayoutInflater().inflate(R.layout.listview_header_row, null);
            lstSymptoms.addHeaderView(header);
            lstSymptoms.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guest_disease_view, menu);
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