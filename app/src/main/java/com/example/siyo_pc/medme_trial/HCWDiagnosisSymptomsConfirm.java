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

import java.util.ArrayList;
import java.util.HashMap;

public class HCWDiagnosisSymptomsConfirm extends AppCompatActivity {

    private Button btnBack, btnConfirm;
    private ListView listSymptoms;

    MM_Person userLoggedIn;

    ArrayList<String> symptomsSelected = new ArrayList<String>();
    private HashMap<Integer, String> symptomList = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hcwdiagnosis_symptoms_confirm);

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
            btnBack = (Button)findViewById(R.id.btnHCWDiagnoseBackSymptoms);
            btnConfirm = (Button)findViewById(R.id.btnHCWDiagnoseConfirmSymptoms);
            listSymptoms = (ListView)findViewById(R.id.listViewHCWDiagnoseResults);

            AdminDiagnoseSymptomsAdapter adapter = new AdminDiagnoseSymptomsAdapter(this, symptomsSelected, userLoggedIn);
            View header = getLayoutInflater().inflate(R.layout.listview_header_confirm_symptoms, null);
            listSymptoms.addHeaderView(header);
            listSymptoms.setAdapter(adapter);

            addButtonEvents();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HCWDiagnose.class);
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
                Intent intent = new Intent(getApplicationContext(), HCWDiagnosedSicknesses.class);
                intent.putExtra("userCred", userLoggedIn);
                intent.putExtra("symptomsSel", symptomsSelected);
                intent.putExtra("symptomList", symptomList);
                startActivity(intent);
                //diagnose(symptomsSelected);
            }
        });
    }
}
