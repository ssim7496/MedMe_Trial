package com.example.siyo_pc.medme_trial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.adapters.AdminSymptomAdapter;
import com.example.siyo_pc.medme_trial.adapters.SymptomAdapter;
import com.example.siyo_pc.medme_trial.classes.MM_Disease;
import com.example.siyo_pc.medme_trial.classes.MM_Person;
import com.example.siyo_pc.medme_trial.classes.MM_Symptom;
import com.example.siyo_pc.medme_trial.db.AsyncGetAllDiseases;
import com.example.siyo_pc.medme_trial.db.AsyncGetAllSymptoms;
import com.example.siyo_pc.medme_trial.db.AsyncTaskResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdminDiseaseView extends AppCompatActivity implements AsyncTaskResponse {

    TextView diseaseTitle, diseaseGreekName, diseaseDesc;
    ListView lstSymptoms;

    MM_Person userLoggedIn;

    private AsyncGetAllDiseases asyncAllDiseases = new AsyncGetAllDiseases(this, this);
    private AsyncGetAllSymptoms asyncAllSymptoms = new AsyncGetAllSymptoms(this, this);
    private ArrayList<MM_Disease> diseaseList = null;
    private ArrayList<MM_Symptom> symptomList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_disease_view);

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
            diseaseTitle = (TextView)findViewById(R.id.tvAdminDiseaseTitle);
            diseaseGreekName = (TextView)findViewById(R.id.tvAdminDiseaseGreekName);
            diseaseDesc = (TextView)findViewById(R.id.tvAdminDiseaseDescription);

            asyncAllDiseases.execute();
            asyncAllSymptoms.execute();

        }
    }

    @Override
    public void onTaskCompleted(List<JSONObject> objectList, int passTypeID) {
        switch (passTypeID) {
            case 1 : {
                diseaseList = convertToDiseases(objectList);
                getDiseaseInformation(diseaseList);
            } break;
            case 2 : {
                symptomList = convertToSymptoms(objectList);
                getSymptomInformation(symptomList);
            } break;
            case 3 : {
                //sickness list
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

    private void getDiseaseInformation(ArrayList<MM_Disease> diseaseList) {
        Intent intent = getIntent();
        String diss = intent.getStringExtra("disease");
        MM_Disease disease = null;

        for (int i = 0; i < diseaseList.size(); i++) {
            if (diseaseList.get(i).GetDiseaseID() == Integer.parseInt(diss)) {
                disease = diseaseList.get(i);
                break;
            }
        }

        diseaseTitle.setText(disease.GetDiseaseName());
        diseaseGreekName.setText("Greek Name: \n" + disease.GetGreekName());
        diseaseDesc.setText("Disease Description: \n" + disease.GetDiseaseDesc());
    }

    private void getSymptomInformation(ArrayList<MM_Symptom> symptomList) {
        if (symptomList != null) {
            AdminSymptomAdapter adapter = new AdminSymptomAdapter(this, symptomList, userLoggedIn);
            lstSymptoms = (ListView) findViewById(R.id.listViewAdminSymptomDiseaseLink);
            View header = getLayoutInflater().inflate(R.layout.listview_header_row, null);
            lstSymptoms.addHeaderView(header);
            lstSymptoms.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_disease_view, menu);
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
