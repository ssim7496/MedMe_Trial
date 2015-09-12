package com.example.siyo_pc.medme_trial;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.classes.MM_Disease;
import com.example.siyo_pc.medme_trial.classes.MM_Person;
import com.example.siyo_pc.medme_trial.classes.MM_Sickness;
import com.example.siyo_pc.medme_trial.classes.MM_Symptom;
import com.example.siyo_pc.medme_trial.db.AsyncTaskResponse;
import com.example.siyo_pc.medme_trial.db.BusinessLogic;
import com.example.siyo_pc.medme_trial.db.JSON_Handler;
import com.example.siyo_pc.medme_trial.db.MedMe_Helper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class AdminHome extends ActionBarActivity{

    MedMe_Helper medMeDB = null;
    Button btnSearch, btnDiagnose, btnDiseases, btnSymptoms, btnTerminology, btnSicknesses;

    MM_Person userLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

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
            addNextActivityOnClickListener(btnSicknesses, AdminSicknessesHome.class);
            //addNextActivityOnClickListener(btnHelp, GuestHelp.class);

            //underConstruction(btnDiseases);
            underConstruction(btnSearch);
            underConstruction(btnDiagnose);
            //underConstruction(btnSymptoms);
            underConstruction(btnTerminology);
            //underConstruction(btnSicknesses);
        }

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
