package com.example.siyo_pc.medme_trial;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DialerFilter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.adapters.AdminDiseaseAdapter;
import com.example.siyo_pc.medme_trial.adapters.AdminSicknessAdapter;
import com.example.siyo_pc.medme_trial.adapters.AdminSymptomAdapter;
import com.example.siyo_pc.medme_trial.classes.MM_Disease;
import com.example.siyo_pc.medme_trial.classes.MM_Person;
import com.example.siyo_pc.medme_trial.classes.MM_Sickness;
import com.example.siyo_pc.medme_trial.classes.MM_Symptom;
import com.example.siyo_pc.medme_trial.db.AsyncGetAllDiseases;
import com.example.siyo_pc.medme_trial.db.AsyncGetAllDiseasesForSickness;
import com.example.siyo_pc.medme_trial.db.AsyncGetAllSicknesses;
import com.example.siyo_pc.medme_trial.db.AsyncGetAllSymptoms;
import com.example.siyo_pc.medme_trial.db.AsyncGetAllSymptomsForSickness;
import com.example.siyo_pc.medme_trial.db.AsyncTaskResponse;
import com.example.siyo_pc.medme_trial.db.AsyncTaskResponse2;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdminSicknessView extends AppCompatActivity implements AsyncTaskResponse, AsyncTaskResponse2 {

    TextView sicknessTitle, sicknessDesc, sicknessGreekName;
    ListView listDiseases, listSymptoms;

    MM_Person userLoggedIn;

    private ArrayList<MM_Disease> diseaseList = null;
    private ArrayList<MM_Symptom> symptomList = null;
    private AsyncGetAllSicknesses asyncAllSicknessess = new AsyncGetAllSicknesses(this, this);
    private ArrayList<MM_Sickness> sicknessList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sickness_view);

        try {
            Intent intent = getIntent();
            userLoggedIn = (MM_Person) intent.getParcelableExtra("userCred");
        } catch (Exception e) {

        }

        if (userLoggedIn == null) {
            Toast.makeText(this, "Access restricted! No user is logged in", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Start.class);
            startActivity(intent);
        } else {
            sicknessTitle = (TextView)findViewById(R.id.tvAdminSicknessTitle);
            sicknessDesc = (TextView)findViewById(R.id.tvAdminSicknessDescription);
            sicknessGreekName = (TextView)findViewById(R.id.tvAdminSicknessGreekName);
            listDiseases = (ListView) findViewById(R.id.listAdminViewRelatedDiseases);
            listSymptoms = (ListView) findViewById(R.id.listAdminViewRelatedSymptoms);

            asyncAllSicknessess.execute();
        }
    }

    private void previousActivity(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AdminSicknessesViewAll.class);
        intent.putExtra("userCred", userLoggedIn);
        startActivity(intent);
    }

    @Override
    public void onTaskCompleted(List<JSONObject> objectList, int passTypeID) {
        switch (passTypeID){
            case 1 : {
                diseaseList = convertToDiseases(objectList);
                fillDiseaseList(diseaseList);
            } break;
            case 2 : {
                symptomList = convertToSymptoms(objectList);
                fillSymptomList(symptomList);
            } break;
            case 3 : {
                sicknessList = convertToSickness(objectList);
                getSicknessInformation(sicknessList);
            } break;
        }
    }

    @Override
    public void onTaskCompleted2(List<JSONObject> objectList, int passTypeID) {
        switch (passTypeID){
            case 1 : {
                diseaseList = convertToDiseases(objectList);
                fillDiseaseList(diseaseList);
            } break;
            case 2 : {
                symptomList = convertToSymptoms(objectList);
                fillSymptomList(symptomList);
            } break;
            case 3 : {
                sicknessList = convertToSickness(objectList);
                getSicknessInformation(sicknessList);
            } break;
        }
    }

    private ArrayList<MM_Disease> convertToDiseases(List<JSONObject> objectList) {
        if (objectList.size() > 0) {

            diseaseList = new ArrayList<>();

            try {
                for (int i = 0; i < objectList.size(); i++) {
                    JSONObject jObject = objectList.get(i);
                    int diseaseID = jObject.getInt("DiseaseID");
                    String greekName = jObject.getString("GreekName");
                    String diseaseName = jObject.getString("DiseaseName");
                    String diseaseDesc = jObject.getString("DiseaseDesc");

                    MM_Disease disease = new MM_Disease();
                    disease.SetDiseaseID(diseaseID);
                    disease.SetGreekName(greekName);
                    disease.SetDiseaseName(diseaseName);
                    disease.SetDiseaseDesc(diseaseDesc);

                    diseaseList.add(disease);
                }
            } catch ( JSONException e) {

            }
        }

        return diseaseList;
    }

    private void fillDiseaseList(ArrayList<MM_Disease> diseaseList){

        if (diseaseList != null) {
            AdminDiseaseAdapter adapter = new AdminDiseaseAdapter(this, diseaseList, userLoggedIn);
            View header = getLayoutInflater().inflate(R.layout.listview_header_row, null);
            listDiseases.addHeaderView(header);
            listDiseases.setAdapter(adapter);
        }
    }

    private ArrayList<MM_Symptom> convertToSymptoms(List<JSONObject> objectList) {
        if (objectList.size() > 0) {

            symptomList = new ArrayList<>();

            try {
                for (int i = 0; i < objectList.size(); i++) {
                    JSONObject jObject = objectList.get(i);
                    int symptomID = jObject.getInt("SymptomID");
                    String greekName = jObject.getString("GreekName");
                    String symptomName = jObject.getString("SymptomName");
                    String symptomDesc = jObject.getString("SymptomDesc");

                    MM_Symptom symptom = new MM_Symptom();
                    symptom.SetSymptomID(symptomID);
                    symptom.SetGreekName(greekName);
                    symptom.SetSymptomName(symptomName);
                    symptom.SetSymptomDesc(symptomDesc);

                    symptomList.add(symptom);
                }
            } catch ( JSONException e) {

            }
        }

        return symptomList;
    }

    private void fillSymptomList(ArrayList<MM_Symptom> symptomList){

        if (symptomList != null) {
            AdminSymptomAdapter adapter = new AdminSymptomAdapter(this, symptomList, userLoggedIn);
            View header = getLayoutInflater().inflate(R.layout.listview_header_row, null);
            listSymptoms.addHeaderView(header);
            listSymptoms.setAdapter(adapter);
        }
    }

    private ArrayList<MM_Sickness> convertToSickness(List<JSONObject> objectList) {
        if (objectList.size() > 0) {

            sicknessList = new ArrayList<>();

            try {
                for (int i = 0; i < objectList.size(); i++) {
                    JSONObject jObject = objectList.get(i);
                    int sicknessID = jObject.getInt("SicknessID");
                    String greekName = jObject.getString("GreekName");
                    String sicknessName = jObject.getString("SicknessName");
                    String sicknessDesc = jObject.getString("SicknessDesc");

                    MM_Sickness sickness = new MM_Sickness();
                    sickness.SetSicknessID(sicknessID);
                    sickness.SetGreekName(greekName);
                    sickness.SetSicknessName(sicknessName);
                    sickness.SetSicknessDesc(sicknessDesc);

                    sicknessList.add(sickness);
                }
            } catch ( JSONException e) {

            }
        }

        return sicknessList;
    }

    private void getSicknessInformation(ArrayList<MM_Sickness> sicknessList){

        Intent intent = getIntent();
        String diss = intent.getStringExtra("sickness");
        MM_Sickness sickness = null;

        for (int i = 0; i < sicknessList.size(); i++) {
            if (sicknessList.get(i).GetSicknessID() == Integer.parseInt(diss)) {
                sickness = sicknessList.get(i);
                break;
            }
        }

        sicknessTitle.setText(sickness.GetSicknessName());
        sicknessGreekName.setText("Greek Name: \n" + sickness.GetGreekName());
        sicknessDesc.setText("Sickness Description: \n" + sickness.GetSicknessDesc());

        //setting alert dialog to show more information
        final Context context = this;
        final MM_Sickness sicknessInfo = sickness;

        sicknessDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("Sickness Information");
                alertDialog.setMessage(sicknessInfo.GetSicknessDesc());
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sicknessID", Integer.toString(sickness.GetSicknessID())));

        AsyncGetAllDiseasesForSickness asyncAllDiseasesForSickness = new AsyncGetAllDiseasesForSickness(this, this, params);
        AsyncGetAllSymptomsForSickness asyncAllSymptomsForSickness = new AsyncGetAllSymptomsForSickness(this, this, params);
        asyncAllDiseasesForSickness.execute();
        asyncAllSymptomsForSickness.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_sickness_view, menu);
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
