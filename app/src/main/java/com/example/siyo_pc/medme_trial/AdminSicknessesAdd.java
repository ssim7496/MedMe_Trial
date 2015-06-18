package com.example.siyo_pc.medme_trial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.adapters.DiseaseSpinnerAdapter;
import com.example.siyo_pc.medme_trial.adapters.NothingSelectedSpinnerAdapter;
import com.example.siyo_pc.medme_trial.adapters.SicknessDiseaseSpinnerAdapter;
import com.example.siyo_pc.medme_trial.adapters.SicknessSymptomSpinnerAdapter;
import com.example.siyo_pc.medme_trial.adapters.SymptomSpinnerAdapter;
import com.example.siyo_pc.medme_trial.classes.MM_Disease;
import com.example.siyo_pc.medme_trial.classes.MM_Sickness;
import com.example.siyo_pc.medme_trial.classes.MM_Symptom;
import com.example.siyo_pc.medme_trial.db.MedMe_Helper;

import java.util.ArrayList;


public class AdminSicknessesAdd extends ActionBarActivity {

    MedMe_Helper medMeDB = null;
    Button btnAdd, btnCancel;
    EditText edtSicknessName, edtSicknessGreekName, edtSicknessDesc;
    TextView tvSlaveDiseaseName, tvSlaveSymptomName;
    Spinner spnDiseaseList, spnSymptomList;
    Integer symptomChosen, diseaseChosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sicknesses_add);

        medMeDB = new MedMe_Helper(this);
        spnDiseaseList = (Spinner)findViewById(R.id.spinnerAdminAddDisease);
        spnSymptomList = (Spinner)findViewById(R.id.spinnerAdminAddSymptom);
        btnAdd = (Button)findViewById(R.id.btnAdminConfirmAddSickness);
        btnCancel = (Button)findViewById(R.id.btnAdminConfirmCancelSickness);
        edtSicknessName = (EditText)findViewById(R.id.edtAdminSicknessNameAdd);
        edtSicknessGreekName = (EditText)findViewById(R.id.edtAdminSicknessGreekNameAdd);
        edtSicknessDesc = (EditText)findViewById(R.id.edtAdminSicknessDescAdd);
        tvSlaveDiseaseName = (TextView)findViewById(R.id.tvAdminSlaveSelectedDisease);
        tvSlaveSymptomName = (TextView)findViewById(R.id.tvAdminSlaveSelectedSymptom);

        IntentFilter intentFilter = new IntentFilter("com.example.siyo_pc.medme_trial.adapters");
        BroadcastReceiver mReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                String msg1 = intent.getStringExtra("symptomItem");
                String msg2 = intent.getStringExtra("diseaseItem");

                if (msg1 != null) {
                    MM_Symptom symptom = medMeDB.GetSymptomByID(Integer.parseInt(msg1));
                    tvSlaveSymptomName.setText(symptom.GetSymptomName());
                    symptomChosen = Integer.parseInt(msg1);
                }
                if (msg2 != null) {
                    MM_Disease disease = medMeDB.GetDiseaseByID(Integer.parseInt(msg2));
                    tvSlaveDiseaseName.setText(disease.GetDiseaseName());
                    diseaseChosen = Integer.parseInt(msg2);
                }
            }
        };
        this.registerReceiver(mReceiver, intentFilter);

        //Diseases spinner
        ArrayList<MM_Disease> diseaseList = medMeDB.GetAllDiseases();
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
        });

        //Symptoms spinner
        ArrayList<MM_Symptom> symptomList = medMeDB.GetAllSymptoms();
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
        });

        addNextActivityOnClickListener(btnCancel, AdminSicknessesHome.class);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSickness(edtSicknessName, edtSicknessGreekName, edtSicknessDesc, tvSlaveDiseaseName, tvSlaveSymptomName);
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

    public void addSickness(EditText sicknessName, EditText greekName, EditText sicknessDesc, TextView diseaseName, TextView symptomName) {

        if (sicknessName != null && greekName != null && sicknessDesc != null && diseaseName != null && symptomName != null){
            if (sicknessName.length() > 1 && greekName.length() > 1 && sicknessDesc.length() > 1 && diseaseName.length() > 1 && symptomName.length() > 1) {
                String gName = greekName.getText().toString();
                String siName = sicknessName.getText().toString();
                String siDesc = sicknessDesc.getText().toString();
                MM_Sickness sickness = new MM_Sickness(diseaseChosen, symptomChosen, gName, siName, siDesc);
                medMeDB.AddSickness(sickness);

                Toast.makeText(getApplicationContext(), "Successfully added.", Toast.LENGTH_LONG).show();
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
        getMenuInflater().inflate(R.menu.menu_admin_sicknesses_add, menu);
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
