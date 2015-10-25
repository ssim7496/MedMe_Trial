package com.example.siyo_pc.medme_trial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.classes.MM_Person;
import com.example.siyo_pc.medme_trial.classes.MM_Symptom;
import com.example.siyo_pc.medme_trial.db.BusinessLogic;

public class HCWSymptomsAdd extends AppCompatActivity {

    Button btnAdd, btnCancel;
    EditText edtSymptomName, edtSymptomGreekName, edtSymptomDesc;

    MM_Person userLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hcwsymptoms_add);

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
            btnAdd = (Button)findViewById(R.id.btnHCWConfirmAddSymptom);
            btnCancel = (Button)findViewById(R.id.btnHCWConfirmCancelSymptom);
            edtSymptomName = (EditText)findViewById(R.id.edtHCWSymptomNameAdd);
            edtSymptomGreekName = (EditText)findViewById(R.id.edtHCWSymptomGreekNameAdd);
            edtSymptomDesc = (EditText)findViewById(R.id.edtHCWSymptomDescAdd);

            addButtonEvents();
        }
    }

    public void addButtonEvents(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSymptom();
            }
        });
        previousActivity(btnCancel);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HCWSymptomsHome.class);
        intent.putExtra("userCred", userLoggedIn);
        startActivity(intent);
    }

    public void previousActivity(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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

    public void addSymptom() {
        if (edtSymptomName != null && edtSymptomGreekName != null && edtSymptomDesc != null){
            if (edtSymptomName.length() > 1 && edtSymptomGreekName.length() > 1 && edtSymptomDesc.length() > 1) {
                String gName = edtSymptomGreekName.getText().toString();
                String sName = edtSymptomName.getText().toString();
                String sDesc = edtSymptomDesc.getText().toString();
                MM_Symptom symptom = new MM_Symptom(gName, sName, sDesc);
                BusinessLogic bll = new BusinessLogic(this, userLoggedIn);
                bll.addSymptomHCW(symptom);
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
