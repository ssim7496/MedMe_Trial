package com.example.siyo_pc.medme_trial;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.siyo_pc.medme_trial.adapters.DiseaseAdapter;
import com.example.siyo_pc.medme_trial.adapters.SymptomAdapter;
import com.example.siyo_pc.medme_trial.classes.MM_Disease;
import com.example.siyo_pc.medme_trial.classes.MM_Sickness;
import com.example.siyo_pc.medme_trial.classes.MM_Symptom;
import com.example.siyo_pc.medme_trial.db.MedMe_Helper;

import java.util.ArrayList;


public class GuestSicknessView extends ActionBarActivity {

    MedMe_Helper medMeDB = null;
    TextView sicknessTitle, sicknessDesc, sicknessGreekName;
    ListView listDiseases, lstSymptoms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_sickness_view);

        medMeDB = new MedMe_Helper(this);
        getSicknessInformation();
    }

    public void getSicknessInformation() {
        sicknessTitle = (TextView)findViewById(R.id.tvSicknessTitle);
        sicknessDesc = (TextView)findViewById(R.id.tvSicknessDescription);
        sicknessGreekName = (TextView)findViewById(R.id.tvSicknessGreekName);

        Intent intent = getIntent();
        String sick = intent.getStringExtra("sickness");

        MM_Sickness sickness = medMeDB.GetSicknessByID(Integer.parseInt(sick));

        sicknessTitle.setText(sickness.GetSicknessName());
        sicknessDesc.setText(sickness.GetSicknessDesc());
        sicknessGreekName.setText(sickness.GetGreekName());

        ArrayList<MM_Disease> diseaseList = medMeDB.GetAllDiseasesForSickness(sickness.GetSicknessID());

        if (diseaseList != null) {
            DiseaseAdapter adapter = new DiseaseAdapter(this, diseaseList);
            listDiseases = (ListView) findViewById(R.id.listViewRelatedDiseases);
            View header = getLayoutInflater().inflate(R.layout.listview_header_row, null);
            listDiseases.addHeaderView(header);
            listDiseases.setAdapter(adapter);
        }

        ArrayList<MM_Symptom> symptomList = medMeDB.GetAllSymptomsForSickness(sickness.GetSicknessID());

        if (symptomList != null) {
            SymptomAdapter adapter = new SymptomAdapter(this, symptomList);
            lstSymptoms = (ListView) findViewById(R.id.listViewRelatedSymptoms);
            View header = getLayoutInflater().inflate(R.layout.listview_header_row, null);
            lstSymptoms.addHeaderView(header);
            lstSymptoms.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guest_sickness_view, menu);
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