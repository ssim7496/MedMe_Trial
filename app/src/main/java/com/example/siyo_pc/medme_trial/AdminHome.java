package com.example.siyo_pc.medme_trial;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.db.MedMe_Helper;


public class AdminHome extends ActionBarActivity {

    MedMe_Helper medMeDB = null;
    Button btnSearch, btnDiagnose, btnDiseases, btnSymptoms, btnTerminology, btnSicknesses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        medMeDB = new MedMe_Helper(this);

        btnSearch = (Button)findViewById(R.id.btnAdminSearch);
        btnDiagnose = (Button)findViewById(R.id.btnAdminDiagnose);
        btnDiseases = (Button)findViewById(R.id.btnAdminDiseases);
        btnSymptoms = (Button)findViewById(R.id.btnAdminSymptoms);
        btnTerminology = (Button)findViewById(R.id.btnAdminTerminology);
        btnSicknesses = (Button)findViewById(R.id.btnAdminSicknesses);

        addNextActivityOnClickListener(btnDiseases, AdminDiseasesHome.class);
        //addNextActivityOnClickListener(btnSearch, GuestSearch.class);
        //addNextActivityOnClickListener(btnDiagnose, GuestDiagnose.class);
        addNextActivityOnClickListener(btnSymptoms, AdminSymptomsHome.class);
        //addNextActivityOnClickListener(btnTerminology, GuestTerminology.class);
        //addNextActivityOnClickListener(btnSicknesses, GuestSickness.class);
        //addNextActivityOnClickListener(btnHelp, GuestHelp.class);

        //underConstruction(btnDiseases);
        underConstruction(btnSearch);
        underConstruction(btnDiagnose);
        //underConstruction(btnSymptoms);
        underConstruction(btnTerminology);
        underConstruction(btnSicknesses);
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

    public void underConstruction(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Currently under construction ... ", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_home, menu);
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
