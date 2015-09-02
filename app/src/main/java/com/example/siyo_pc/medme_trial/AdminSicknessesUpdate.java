package com.example.siyo_pc.medme_trial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.adapters.NothingSelectedSpinnerAdapter;
import com.example.siyo_pc.medme_trial.adapters.SicknessDiseaseSpinnerAdapter;
import com.example.siyo_pc.medme_trial.adapters.SicknessSpinnerAdapter;
import com.example.siyo_pc.medme_trial.adapters.SicknessSymptomSpinnerAdapter;
import com.example.siyo_pc.medme_trial.classes.MM_Disease;
import com.example.siyo_pc.medme_trial.classes.MM_Sickness;
import com.example.siyo_pc.medme_trial.classes.MM_Symptom;
import com.example.siyo_pc.medme_trial.db.MedMe_Helper;

import java.util.ArrayList;


public class AdminSicknessesUpdate extends ActionBarActivity {

    MedMe_Helper medMeDB = null;
    Button btnUpdate, btnCancel;
    EditText edtSicknessName, edtSicknessGreekName, edtSicknessDesc;
    TextView tvSlaveDiseaseName, tvSlaveSymptomName;
    Spinner spnSicknessList, spnDiseaseList, spnSymptomList;
    Integer symptomChosen, diseaseChosen, sicknessChosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sicknesses_update);

        medMeDB = new MedMe_Helper(this);
        spnSicknessList = (Spinner)findViewById(R.id.spinnerAdminUpdateSickness);
        /*spnDiseaseList = (Spinner)findViewById(R.id.spinnerAdminUpdateSicknessDisease);
        spnSymptomList = (Spinner)findViewById(R.id.spinnerAdminUpdateSicknessSymptom);*/
        btnUpdate = (Button)findViewById(R.id.btnAdminConfirmUpdateSicknessUpdate);
        btnCancel = (Button)findViewById(R.id.btnAdminConfirmCancelSicknessUpdate);
        edtSicknessName = (EditText)findViewById(R.id.edtAdminUpdateSicknessNameUpdate);
        edtSicknessGreekName = (EditText)findViewById(R.id.edtAdminUpdateSicknessGreekNameUpdate);
        edtSicknessDesc = (EditText)findViewById(R.id.edtAdminUpdateSicknessDescUpdate);
        tvSlaveDiseaseName = (TextView)findViewById(R.id.tvAdminSlaveSelectedDiseaseUpdate);
        tvSlaveSymptomName = (TextView)findViewById(R.id.tvAdminSlaveSelectedSymptomUpdate);

        IntentFilter intentFilter = new IntentFilter("com.example.siyo_pc.medme_trial.adapters");
        BroadcastReceiver mReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                String msg = intent.getStringExtra("sicknessItem");
                String msg1 = intent.getStringExtra("symptomItem");
                String msg2 = intent.getStringExtra("diseaseItem");

                if (msg != null) {
                    MM_Sickness sickness = medMeDB.GetSicknessByID(Integer.parseInt(msg));
                    edtSicknessName.setText(sickness.GetSicknessName());
                    edtSicknessGreekName.setText(sickness.GetGreekName());
                    edtSicknessDesc.setText(sickness.GetSicknessDesc());
                    //tvSlaveSymptomName.setText(medMeDB.GetSymptomByID(sickness.GetSymptomID()).GetSymptomName());
                    //tvSlaveDiseaseName.setText(medMeDB.GetDiseaseByID(sickness.GetDiseaseID()).GetDiseaseName());
                    sicknessChosen = Integer.parseInt(msg);
                    //symptomChosen = sickness.GetSymptomID();
                    //diseaseChosen = sickness.GetDiseaseID();
                }
                /*if (msg1 != null) {
                    MM_Symptom symptom = medMeDB.GetSymptomByID(Integer.parseInt(msg1));
                    tvSlaveSymptomName.setText(symptom.GetSymptomName());
                    symptomChosen = Integer.parseInt(msg1);
                }
                if (msg2 != null) {
                    MM_Disease disease = medMeDB.GetDiseaseByID(Integer.parseInt(msg2));
                    tvSlaveDiseaseName.setText(disease.GetDiseaseName());
                    diseaseChosen = Integer.parseInt(msg2);
                }*/
            }
        };
        this.registerReceiver(mReceiver, intentFilter);

        //Sickness spinner
        ArrayList<MM_Sickness> sicknessList = medMeDB.GetAllSicknesses();
        final ArrayAdapter<MM_Sickness> sicknessAdapter = new SicknessSpinnerAdapter(this, android.R.layout.simple_spinner_item,sicknessList);

        spnSicknessList.setPrompt("Please select a Sickness");
        spnSicknessList.setAdapter(new NothingSelectedSpinnerAdapter(
                sicknessAdapter, R.layout.spinner_row_default_sickness, this
        ));

        spnSicknessList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Diseases spinner
        /*ArrayList<MM_Disease> diseaseList = medMeDB.GetAllDiseases();
        final ArrayAdapter<MM_Disease> diseaseAdapter = new SicknessDiseaseSpinnerAdapter(this, android.R.layout.simple_spinner_item,diseaseList);

        spnDiseaseList.setPrompt("Please select a Disease");
        spnDiseaseList.setAdapter(new NothingSelectedSpinnerAdapter(
                diseaseAdapter, R.layout.spinner_row_default_disease, this
        ));

        spnDiseaseList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        //Symptoms spinner
        /*ArrayList<MM_Symptom> symptomList = medMeDB.GetAllSymptoms();
        final ArrayAdapter<MM_Symptom> symptomAdapter = new SicknessSymptomSpinnerAdapter(this, android.R.layout.simple_spinner_item,symptomList);

        spnSymptomList.setPrompt("Please select a Symptom");
        spnSymptomList.setAdapter(new NothingSelectedSpinnerAdapter(
                symptomAdapter, R.layout.spinner_row_default_symptom, this
        ));
        spnSymptomList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        addNextActivityOnClickListener(btnCancel, AdminSicknessesHome.class);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSickness(edtSicknessName, edtSicknessGreekName, edtSicknessDesc);
            }
        });
    }

    public void addNextActivityOnClickListener(View view, final Class nextClass) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), nextClass);
                startActivity(intent);
            }
        });
    }

    public void updateSickness(EditText sicknessName, EditText greekName, EditText sicknessDesc) {

        if (sicknessChosen != null && symptomChosen != null && diseaseChosen != null && sicknessName != null && greekName != null && sicknessDesc != null){
            if (sicknessChosen > 0 && symptomChosen > 0 && diseaseChosen > 0 && sicknessName.length() > 1 && greekName.length() > 1 && sicknessDesc.length() > 1) {
                String gName = greekName.getText().toString();
                String sName = sicknessName.getText().toString();
                String sDesc = sicknessDesc.getText().toString();
                //MM_Sickness sickness  = new MM_Sickness(sicknessChosen, diseaseChosen, symptomChosen, gName, sName, sDesc);
                MM_Sickness sickness  = new MM_Sickness(sicknessChosen, gName, sName, sDesc);
                medMeDB.UpdateSickness(sickness);

                Toast.makeText(getApplicationContext(), "Successfully updated.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), AdminSicknessesHome.class);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_sicknesses_update, menu);
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
