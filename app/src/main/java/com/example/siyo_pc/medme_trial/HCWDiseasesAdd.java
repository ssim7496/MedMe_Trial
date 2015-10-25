package com.example.siyo_pc.medme_trial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.classes.MM_Disease;
import com.example.siyo_pc.medme_trial.classes.MM_Person;
import com.example.siyo_pc.medme_trial.db.BusinessLogic;

public class HCWDiseasesAdd extends AppCompatActivity {

    Button btnAdd, btnCancel;
    EditText edtDiseaseName, edtDiseaseGreekName, edtDiseaseDesc;

    MM_Person userLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hcwdiseases_add);

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
            btnAdd = (Button)findViewById(R.id.btnHCWConfirmAddDisease);
            btnCancel = (Button)findViewById(R.id.btnHCWConfirmCancelDisease);
            edtDiseaseName = (EditText)findViewById(R.id.edtHCWDiseaseNameAdd);
            edtDiseaseGreekName = (EditText)findViewById(R.id.edtHCWDiseaseGreekNameAdd);
            edtDiseaseDesc = (EditText)findViewById(R.id.edtHCWDiseaseDescAdd);

            addButtonEvents();
        }

    }

    private void addButtonEvents(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDisease();
            }
        });
        previousActivity(btnCancel);
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
        Intent intent = new Intent(this, HCWDiseasesHome.class);
        intent.putExtra("userCred", userLoggedIn);
        startActivity(intent);
    }

    private void addDisease() {
        if (edtDiseaseName != null && edtDiseaseGreekName != null && edtDiseaseDesc != null){
            if (edtDiseaseName.length() > 1 && edtDiseaseGreekName.length() > 1 && edtDiseaseDesc.length() > 1) {
                String gName = edtDiseaseGreekName.getText().toString();
                String dName = edtDiseaseName.getText().toString();
                String dDesc = edtDiseaseDesc.getText().toString();
                MM_Disease disease = new MM_Disease(gName, dName, dDesc);
                BusinessLogic bll = new BusinessLogic(this, userLoggedIn);
                bll.addDiseaseHCW(disease);
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
