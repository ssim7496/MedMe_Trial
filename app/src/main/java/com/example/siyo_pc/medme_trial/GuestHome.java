package com.example.siyo_pc.medme_trial;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.classes.MM_Person;
import com.example.siyo_pc.medme_trial.classes.MM_Sickness;
import com.example.siyo_pc.medme_trial.db.MedMe_Helper;

import java.util.ArrayList;
import java.util.List;


public class GuestHome extends ActionBarActivity {

    MedMe_Helper medMeDB = null;
    Button btnSearch, btnDiagnose, btnDiseases, btnSymptoms, btnTerminology, btnSicknesses, btnHelp;
    MM_Person userLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_home);

        try {
            Intent intent = getIntent();
            userLoggedIn = (MM_Person) intent.getParcelableExtra("userCred");
        } catch (Exception e) {

        }

        /*if (userLoggedIn == null) {
            Toast.makeText(this, "Access restricted! No user is logged in", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Start.class);
            startActivity(intent);
        } else {*/
            medMeDB = new MedMe_Helper(this);

            btnSearch = (Button) findViewById(R.id.btnSearch);
            btnDiagnose = (Button) findViewById(R.id.btnDiagnose);
            btnDiseases = (Button) findViewById(R.id.btnDiseases);
            btnSymptoms = (Button) findViewById(R.id.btnSymptoms);
            btnTerminology = (Button) findViewById(R.id.btnTerminology);
            btnSicknesses = (Button) findViewById(R.id.btnSicknesses);
            btnHelp = (Button) findViewById(R.id.btnHelp);

            addNextActivityOnClickListener(btnDiseases, GuestDisease.class);
            //addNextActivityOnClickListener(btnSearch, GuestSearch.class);
            //addNextActivityOnClickListener(btnDiagnose, GuestDiagnose.class);
            addNextActivityOnClickListener(btnSymptoms, GuestSymptom.class);
            //addNextActivityOnClickListener(btnTerminology, GuestTerminology.class);
            addNextActivityOnClickListener(btnSicknesses, GuestSickness.class);
            //addNextActivityOnClickListener(btnHelp, GuestHelp.class);

            //underConstruction(btnDiseases);
            underConstruction(btnSearch);
            underConstruction(btnDiagnose);
            //underConstruction(btnSymptoms);
            underConstruction(btnTerminology);
            //underConstruction(btnSicknesses);
            underConstruction(btnHelp);
        }
    //}

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
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*finish();*/
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guest_home, menu);
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
