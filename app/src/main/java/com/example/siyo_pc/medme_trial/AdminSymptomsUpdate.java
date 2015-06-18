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
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.adapters.NothingSelectedSpinnerAdapter;
import com.example.siyo_pc.medme_trial.adapters.SymptomSpinnerAdapter;
import com.example.siyo_pc.medme_trial.classes.MM_Disease;
import com.example.siyo_pc.medme_trial.classes.MM_Symptom;
import com.example.siyo_pc.medme_trial.db.MedMe_Helper;

import java.util.ArrayList;


public class AdminSymptomsUpdate extends ActionBarActivity {

    MedMe_Helper medMeDB = null;
    Button btnUpdate, btnCancel;
    EditText edtSymptomName, edtSymptomGreekName, edtSymptomDesc;
    Spinner spnSymptomList;
    Integer symptomChosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_symptoms_update);

        medMeDB = new MedMe_Helper(this);
        spnSymptomList = (Spinner)findViewById(R.id.spinnerUpdateSymptom);
        btnUpdate = (Button)findViewById(R.id.btnAdminConfirmUpdateSymptom);
        btnCancel = (Button)findViewById(R.id.btnAdminConfirmCancelSymptom);
        edtSymptomName = (EditText)findViewById(R.id.edtAdminSymptomNameUpdate);
        edtSymptomGreekName = (EditText)findViewById(R.id.edtAdminSymptomGreekNameUpdate);
        edtSymptomDesc = (EditText)findViewById(R.id.edtAdminSymptomDescUpdate);

        addNextActivityOnClickListener(btnCancel, AdminSymptomsHome.class);

        IntentFilter intentFilter = new IntentFilter("com.example.siyo_pc.medme_trial.adapters");
        BroadcastReceiver mReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                String msg1 = intent.getStringExtra("symptomItem2");
                MM_Symptom symptom = medMeDB.GetSymptomByID(Integer.parseInt(msg1));
                symptomChosen = Integer.parseInt(msg1);
                edtSymptomName.setText(symptom.GetSymptomName());
                edtSymptomGreekName.setText(symptom.GetGreekName());
                edtSymptomDesc.setText(symptom.GetSymptomDesc());
            }
        };

        this.registerReceiver(mReceiver, intentFilter);

        ArrayList<MM_Symptom> symptomList = medMeDB.GetAllSymptoms();
        final ArrayAdapter<MM_Symptom> adapter = new SymptomSpinnerAdapter(this, android.R.layout.simple_spinner_item,symptomList);

        spnSymptomList.setPrompt("Please select a Symptom");
        spnSymptomList.setAdapter(new NothingSelectedSpinnerAdapter(
                adapter, R.layout.spinner_row_default_symptom, this
        ));
        spnSymptomList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSymptom(edtSymptomName, edtSymptomGreekName, edtSymptomDesc);
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

    public void updateSymptom(EditText symptomName, EditText greekName, EditText symptomDesc) {

        if (symptomChosen != null && symptomName != null && greekName != null && symptomDesc != null){
            if (symptomChosen > 0 && symptomName.length() > 1 && greekName.length() > 1 && symptomDesc.length() > 1) {
                String gName = greekName.getText().toString();
                String dName = symptomName.getText().toString();
                String dDesc = symptomDesc.getText().toString();
                MM_Symptom symptom = new MM_Symptom(symptomChosen, gName, dName, dDesc);
                medMeDB.UpdateSymptom(symptom);

                Toast.makeText(getApplicationContext(), "Successfully updated.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), AdminSymptomsHome.class);
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
        getMenuInflater().inflate(R.menu.menu_admin_symptoms_update, menu);
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
