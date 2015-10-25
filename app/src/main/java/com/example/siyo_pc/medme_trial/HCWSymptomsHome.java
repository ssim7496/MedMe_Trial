package com.example.siyo_pc.medme_trial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.classes.MM_Person;

public class HCWSymptomsHome extends AppCompatActivity {

    Button btnAdd, btnViewAll;

    MM_Person userLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hcwsymptoms_home);

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
            btnAdd = (Button) findViewById(R.id.btnHCWAddSymptom);
            btnViewAll = (Button) findViewById(R.id.btnHCWViewAllSymptom);

            addButtonEvents();
        }
    }

    private void addButtonEvents(){
        addNextActivityOnClickListener(btnAdd, HCWSymptomsAdd.class);
        addNextActivityOnClickListener(btnViewAll, HCWSymptomsViewAll.class);
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
    public void onBackPressed() {
        Intent intent = new Intent(this, HCWHome.class);
        intent.putExtra("userCred", userLoggedIn);
        startActivity(intent);
    }
}
