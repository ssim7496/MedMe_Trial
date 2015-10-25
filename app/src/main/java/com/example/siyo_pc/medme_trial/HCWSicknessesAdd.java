package com.example.siyo_pc.medme_trial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.adapters.AdminSicknessDiseaseSpinnerAdapter;
import com.example.siyo_pc.medme_trial.adapters.HCWSicknessesDiseaseSpinnerAdapter;
import com.example.siyo_pc.medme_trial.adapters.NothingSelectedSpinnerAdapter;
import com.example.siyo_pc.medme_trial.classes.MM_Disease;
import com.example.siyo_pc.medme_trial.classes.MM_Person;
import com.example.siyo_pc.medme_trial.classes.MM_Sickness;
import com.example.siyo_pc.medme_trial.db.AsyncGetAllDiseases;
import com.example.siyo_pc.medme_trial.db.AsyncTaskResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HCWSicknessesAdd extends AppCompatActivity implements AsyncTaskResponse {

    Button btnNext, btnCancel;
    EditText edtSicknessName, edtSicknessGreekName, edtSicknessDesc;
    TextView tvSlaveDiseaseName;
    Spinner spnDiseaseList;
    Integer diseaseChosen;

    MM_Person userLoggedIn;
    MM_Disease disease;

    private AsyncGetAllDiseases asyncAllDiseases = new AsyncGetAllDiseases(this, this);
    private ArrayList<MM_Disease> diseaseList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hcwsicknesses_add);

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
            spnDiseaseList = (Spinner)findViewById(R.id.spinnerHCWAddDisease);
            btnNext = (Button)findViewById(R.id.btnHCWAddSicknessNext1);
            btnCancel = (Button)findViewById(R.id.btnHCWConfirmCancelSickness);
            edtSicknessName = (EditText)findViewById(R.id.edtHCWSicknessNameAdd);
            edtSicknessGreekName = (EditText)findViewById(R.id.edtHCWSicknessGreekNameAdd);
            edtSicknessDesc = (EditText)findViewById(R.id.edtHCWSicknessDescAdd);
            tvSlaveDiseaseName = (TextView)findViewById(R.id.tvHCWSlaveSelectedDisease);

            try {
                asyncAllDiseases.execute();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Oops. Something went wrong and we will get to it very soon.", Toast.LENGTH_LONG).show();
            }

            addButtonEvents();
            addIntentFiltersAndBroadcastReceivers();
        }
    }

    private void addButtonEvents() {
        previousActivity(btnCancel);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSicknessAndDisease(edtSicknessName, edtSicknessGreekName, edtSicknessDesc, tvSlaveDiseaseName);
            }
        });
    }

    private void addIntentFiltersAndBroadcastReceivers() {
        IntentFilter intentFilter = new IntentFilter("com.example.siyo_pc.medme_trial.adapters");
        BroadcastReceiver mReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                //String msg1 = intent.getStringExtra("symptomItem");
                String msg2 = intent.getStringExtra("diseaseItem");

                /*if (msg1 != null) {
                    //MM_Symptom symptom = medMeDB.GetSymptomByID(Integer.parseInt(msg1));
                    tvSlaveSymptomName.setText(symptom.GetSymptomName());
                    symptomChosen = Integer.parseInt(msg1);
                }*/
                if (msg2 != null) {
                    for (int i = 0; i < diseaseList.size(); i++) {
                        if (diseaseList.get(i).GetDiseaseID() == Integer.parseInt(msg2)){
                            disease = diseaseList.get(i);
                            tvSlaveDiseaseName.setText(disease.GetDiseaseName());
                            diseaseChosen = Integer.parseInt(msg2);
                        }
                    }
                }
            }
        };
        this.registerReceiver(mReceiver, intentFilter);
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
        Intent intent = new Intent(this, HCWSicknessesHome.class);
        intent.putExtra("userCred", userLoggedIn);
        startActivity(intent);
    }

    public void addNextActivityOnClickListener(View view, final Class nextClass) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), nextClass);
                intent.putExtra("userCred", userLoggedIn);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onTaskCompleted(List<JSONObject> objectList, int passTypeID) {
        diseaseList = convertToDiseases(objectList);

        fillDiseaseSpinner(diseaseList);
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

    private void fillDiseaseSpinner(ArrayList<MM_Disease> diseaseList){

        final ArrayAdapter<MM_Disease> adapter = new HCWSicknessesDiseaseSpinnerAdapter(this, android.R.layout.simple_spinner_item, diseaseList);

        spnDiseaseList.setPrompt("Please select a disease");
        spnDiseaseList.setAdapter(new NothingSelectedSpinnerAdapter(
                adapter, R.layout.spinner_row_default_disease, this
        ));
        spnDiseaseList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void saveSicknessAndDisease(EditText sicknessName, EditText greekName, EditText sicknessDesc, TextView diseaseName) {

        if (sicknessName != null && greekName != null && sicknessDesc != null && diseaseName != null){
            if (sicknessName.length() > 1 && greekName.length() > 1 && sicknessDesc.length() > 1 && diseaseName.length() > 1) {
                String gName = greekName.getText().toString();
                String siName = sicknessName.getText().toString();
                String siDesc = sicknessDesc.getText().toString();
                MM_Sickness sickness = new MM_Sickness(gName, siName, siDesc);

                Intent intent = new Intent(getApplicationContext(), HCWSicknessesAddSymptoms.class);
                intent.putExtra("userCred", userLoggedIn);
                intent.putExtra("sickToAdd", sickness);
                intent.putExtra("dissToAdd", disease);
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(), "All fields must be at least 2 characters in length.", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Please fill in all fields.", Toast.LENGTH_LONG).show();
        }
    }
}
