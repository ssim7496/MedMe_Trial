package com.example.siyo_pc.medme_trial;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.classes.MM_Person;

public class HCWHome extends AppCompatActivity {

    Button btnSearch, btnDiagnose, btnDiseases, btnSymptoms, btnSicknesses, btnHelp;

    MM_Person userLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hcwhome);

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
            btnSearch = (Button)findViewById(R.id.btnHCWSearch);
            btnDiagnose = (Button)findViewById(R.id.btnHCWDiagnose);
            btnDiseases = (Button)findViewById(R.id.btnHCWDiseases);
            btnSymptoms = (Button)findViewById(R.id.btnHCWSymptoms);
            btnSicknesses = (Button)findViewById(R.id.btnHCWSicknesses);
            btnHelp = (Button)findViewById(R.id.btnHCWHelp);

            addNextActivityOnClickListener(btnDiseases, HCWDiseasesHome.class);
            addNextActivityOnClickListener(btnSearch, HCWSearch.class);
            addNextActivityOnClickListener(btnDiagnose, HCWDiagnose.class);
            addNextActivityOnClickListener(btnSymptoms, HCWSymptomsHome.class);
            addNextActivityOnClickListener(btnSicknesses, HCWSicknessesHome.class);
            addNextActivityOnClickListener(btnHelp, Help.class);

            //underConstruction(btnDiseases);
            //underConstruction(btnSearch);
            //underConstruction(btnDiagnose);
            //underConstruction(btnSymptoms);
            //underConstruction(btnSicknesses);
        }

    }

    private void addNextActivityOnClickListener(View view, final Class nextClass) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), nextClass);
                intent.putExtra("userCred", userLoggedIn);
                startActivity(intent);
            }
        });
    }

    private void underConstruction(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Currently under construction ... ", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
