package com.example.siyo_pc.medme_trial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.adapters.AdminDiagnoseSymptomsAdapter;
import com.example.siyo_pc.medme_trial.classes.MM_Person;
import com.example.siyo_pc.medme_trial.classes.MM_Sickness;
import com.example.siyo_pc.medme_trial.classes.MM_Symptom;
import com.example.siyo_pc.medme_trial.db.AsyncAdminDiagnose;
import com.example.siyo_pc.medme_trial.db.AsyncGetAllSymptoms;
import com.example.siyo_pc.medme_trial.db.AsyncTaskResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminDiagnosisSymptomsConfirm extends AppCompatActivity implements AsyncTaskResponse {

    private Button btnBack, btnConfirm;
    private ListView listSymptoms;

    MM_Person userLoggedIn;

    ArrayList<String> symptomsSelected = new ArrayList<String>();
    private HashMap<Integer, String> symptomList = new HashMap<>();
    private ArrayList<MM_Sickness> convertedSicknessList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_diagnosis_symptoms_confirm);

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
            btnBack = (Button)findViewById(R.id.btnAdminDiagnoseBackSymptoms);
            btnConfirm = (Button)findViewById(R.id.btnAdminDiagnoseConfirmSymptoms);
            listSymptoms = (ListView)findViewById(R.id.listViewAdminDiagnoseResults);

            AdminDiagnoseSymptomsAdapter adapter = new AdminDiagnoseSymptomsAdapter(this, symptomsSelected, userLoggedIn);
            View header = getLayoutInflater().inflate(R.layout.listview_header_confirm_symptoms, null);
            listSymptoms.addHeaderView(header);
            listSymptoms.setAdapter(adapter);

            addButtonEvents();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AdminDiagnose.class);
        intent.putExtra("userCred", userLoggedIn);
        startActivity(intent);
    }

    private void addButtonEvents() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diagnose(symptomsSelected);
            }
        });
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

        new AsyncAdminDiagnose(this, this, selectedSymptomList).execute();


    }

    @Override
    public void onTaskCompleted(List<JSONObject> objectList, int passTypeID) {
        convertedSicknessList = convertToSickness(objectList);

        fillSymptomList(convertedSicknessList);
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

    private void fillSymptomList(ArrayList<MM_Sickness> sicknessList){

        if (sicknessList != null) {
            String output = "";

            for (int i = 0; i < sicknessList.size(); i++) {
                output += "\n" + sicknessList.get(i).GetSicknessName();
            }

            Toast.makeText(this, output, Toast.LENGTH_LONG).show();

            /*AdminSymptomAdapter adapter = new AdminSymptomAdapter(this, symptomList, userLoggedIn);
            View header = getLayoutInflater().inflate(R.layout.listview_header_row, null);
            listSymptoms.addHeaderView(header);
            listSymptoms.setAdapter(adapter);*/
        }
    }
}
