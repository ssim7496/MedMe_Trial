package com.example.siyo_pc.medme_trial;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.siyo_pc.medme_trial.adapters.DiseaseAdapter;
import com.example.siyo_pc.medme_trial.adapters.SicknessAdapter;
import com.example.siyo_pc.medme_trial.adapters.SymptomAdapter;
import com.example.siyo_pc.medme_trial.classes.MM_Disease;
import com.example.siyo_pc.medme_trial.classes.MM_Sickness;
import com.example.siyo_pc.medme_trial.classes.MM_Symptom;
import com.example.siyo_pc.medme_trial.db.MedMe_Helper;

import java.util.ArrayList;


public class AdminSicknessesViewAll extends ActionBarActivity {

    private ListView listSymptoms;
    private ListView listDiseases;
    private MedMe_Helper medMeDB = null;
    private ListView listSickness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sicknesses_view_all);

        medMeDB = new MedMe_Helper(this);
        //fillDiseaseList();
        //fillSymptomList();
        fillSicknessList();
    }

    public void fillSicknessList(){

        /*ArrayList<MM_Sickness> sicknessList2 = medMeDB.GetAllSicknesses();

        if (sicknessList2 != null) {
            SicknessAdapter adapter = new SicknessAdapter(this, sicknessList2);
            listSickness = (ListView) findViewById(R.id.listViewGuestSicknessListAdmin);
            View header = getLayoutInflater().inflate(R.layout.listview_header_row, null);
            listSickness.addHeaderView(header);
            listSickness.setAdapter(adapter);
        }*/
    }

    /*public void fillDiseaseList(){

        ArrayList<MM_Disease> diseaseList = medMeDB.GetAllDiseases();

        if (diseaseList != null) {
            DiseaseAdapter adapter = new DiseaseAdapter(this, diseaseList);
            listDiseases = (ListView) findViewById(R.id.listViewRelatedDiseasesAdmin);
            View header = getLayoutInflater().inflate(R.layout.listview_header_row, null);
            listDiseases.addHeaderView(header);
            listDiseases.setAdapter(adapter);
        }
    }

    public void fillSymptomList(){

        ArrayList<MM_Symptom> symptomList = medMeDB.GetAllSymptoms();

        if (symptomList != null) {
            SymptomAdapter adapter = new SymptomAdapter(this, symptomList);
            listSymptoms = (ListView) findViewById(R.id.listViewRelatedSymptomsAdmin);
            View header = getLayoutInflater().inflate(R.layout.listview_header_row, null);
            listSymptoms.addHeaderView(header);
            listSymptoms.setAdapter(adapter);
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_sicknesses_view_all, menu);
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
