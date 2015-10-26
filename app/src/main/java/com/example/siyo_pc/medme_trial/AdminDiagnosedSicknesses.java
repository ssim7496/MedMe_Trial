package com.example.siyo_pc.medme_trial;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.adapters.AdminDiagnoseSymptomsAdapter;
import com.example.siyo_pc.medme_trial.adapters.AdminDiagnosedSicknessAdapter;
import com.example.siyo_pc.medme_trial.classes.MM_DiagnosedSymptoms;
import com.example.siyo_pc.medme_trial.classes.MM_Person;
import com.example.siyo_pc.medme_trial.classes.MM_Sickness;
import com.example.siyo_pc.medme_trial.classes.MM_Symptom;
import com.example.siyo_pc.medme_trial.db.AsyncAdminDiagnose;
import com.example.siyo_pc.medme_trial.db.AsyncGetAllSymptomsForSickness;
import com.example.siyo_pc.medme_trial.db.AsyncGetSickSympMatchingDiagSymp;
import com.example.siyo_pc.medme_trial.db.AsyncTaskResponse;
import com.example.siyo_pc.medme_trial.db.AsyncTaskResponse2;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminDiagnosedSicknesses extends AppCompatActivity implements AsyncTaskResponse, AsyncTaskResponse2 {

    MM_Person userLoggedIn;

    private ListView listSicknesses;

    ArrayList<String> symptomsSelected = new ArrayList<String>();
    private HashMap<Integer, String> symptomList = new HashMap<>();
    private ArrayList<MM_Sickness> convertedSicknessList = null;
    private ArrayList<MM_Sickness> convertedSicknessListByID = null;
    private ArrayList<MM_Symptom> allSymptomsForSickness = null;
    private ArrayList<MM_DiagnosedSymptoms> diagnosisSymptomsList = new ArrayList<>();
    private int totalSicknessForListViewCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_diagnosed_sicknesses);

        try {
            Intent intent = getIntent();
            userLoggedIn = (MM_Person) intent.getParcelableExtra("userCred");
            symptomsSelected = intent.getStringArrayListExtra("symptomsSel");
            symptomList = (HashMap<Integer, String>)intent.getSerializableExtra("symptomList");
        } catch (Exception e) {

        }

        if (userLoggedIn == null) {
            Toast.makeText(this, "Access restricted! No user is logged in", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Start.class);
            startActivity(intent);
        } else {
            listSicknesses = (ListView)findViewById(R.id.listViewAdminDiagnosedSicknesses);

            diagnose(symptomsSelected);

            View header = getLayoutInflater().inflate(R.layout.listview_header_diagnosed_sicknesses, null);
            listSicknesses.addHeaderView(header);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AdminDiagnose.class);
        intent.putExtra("userCred", userLoggedIn);
        startActivity(intent);
    }

    private void diagnose(ArrayList<String> symptomsSelected) {
        //making an object list of the selected symptoms based on the previous activity's symptom list which contains the proper objects
        ArrayList<MM_Symptom> selectedSymptomList = new ArrayList<>();

        for (int i = 0; i < symptomsSelected.size(); i++) {
            for (Integer sympID : symptomList.keySet()) {
                //gets the row on each line of hashmap
                String sympName = symptomList.get(sympID);

                if (symptomsSelected.get(i).equals(sympName)) {
                    selectedSymptomList.add(new MM_Symptom(sympID, sympName));
                }
            }
        }

        try {
            new AsyncAdminDiagnose(this, this, selectedSymptomList).execute();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Oops. Something went wrong and we will get to it very soon.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onTaskCompleted(List<JSONObject> objectList, int passTypeID) {
        //diagnose
        if (passTypeID == 10) {
            convertedSicknessList = convertToSickness(objectList);
            fillSymptomList(convertedSicknessList);
        }
        //sicknesses by ID
        if (passTypeID == 11) {
            convertedSicknessListByID = convertToSickness(objectList);
        }
    }

    @Override
    public void onTaskCompleted2(List<JSONObject> objectList, int passTypeID) {
        if (passTypeID == 2) {
            allSymptomsForSickness = convertToSymptoms(objectList);

            if (allSymptomsForSickness != null) {
                //getting how many of the selected symptoms are a symptom of the sickness

                //making a list of symptom names to be used in finding out how many of the symptoms in the sickness match the selected symptoms
                Integer symptomsForSicknessCount = 0;

                for (int j = 0; j < symptomsSelected.size(); j++) {
                    for (int k = 0; k < allSymptomsForSickness.size(); k++) {
                        if (symptomsSelected.get(j).equals(allSymptomsForSickness.get(k).GetSymptomName())) {
                            symptomsForSicknessCount++;
                        }
                    }
                }

                Double percentage = ((double)symptomsForSicknessCount / allSymptomsForSickness.size()) * 100;

                diagnosisSymptomsList.add(new MM_DiagnosedSymptoms(symptomsForSicknessCount, symptomsSelected.size(), percentage, allSymptomsForSickness.size()));

                if (totalSicknessForListViewCount == (convertedSicknessListByID.size() - 1)) {
                    //adding sickness to listview
                    AdminDiagnosedSicknessAdapter adapter = new AdminDiagnosedSicknessAdapter(this, convertedSicknessListByID, userLoggedIn, diagnosisSymptomsList);
                    listSicknesses.setAdapter(adapter);
                } else {
                    totalSicknessForListViewCount++;
                }

            }
        }
    }

    private ArrayList<MM_Sickness> convertToSickness(List<JSONObject> objectList) {
        if (objectList.size() > 0) {

            convertedSicknessList = new ArrayList<>();

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

                    convertedSicknessList.add(sickness);
                }
            } catch ( JSONException e) {

            }
        }

        return convertedSicknessList;
    }

    private ArrayList<MM_Symptom> convertToSymptoms(List<JSONObject> objectList) {
        if (objectList.size() > 0) {

            allSymptomsForSickness = new ArrayList<>();

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

                    allSymptomsForSickness.add(symptom);
                }
            } catch ( JSONException e) {

            }
        }

        return allSymptomsForSickness;
    }

    private void fillSymptomList(ArrayList<MM_Sickness> sicknessList){
        convertedSicknessListByID = sicknessList;

        if (sicknessList != null) {
            //getting all symptoms for each sickness
            for (int i = 0; i < sicknessList.size(); i++) {
                ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("sicknessID", Integer.toString(sicknessList.get(i).GetSicknessID())));

                try {
                    new AsyncGetAllSymptomsForSickness(this, this, params).execute();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Oops. Something went wrong and we will get to it very soon.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
