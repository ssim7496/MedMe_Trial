package com.example.siyo_pc.medme_trial;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.adapters.DiseaseSpinnerAdapter;
import com.example.siyo_pc.medme_trial.adapters.NothingSelectedSpinnerAdapter;
import com.example.siyo_pc.medme_trial.classes.MM_Disease;
import com.example.siyo_pc.medme_trial.classes.MM_Person;
import com.example.siyo_pc.medme_trial.classes.MM_Symptom;
import com.example.siyo_pc.medme_trial.db.BusinessLogic;
import com.example.siyo_pc.medme_trial.db.MedMe_Helper;

import java.util.ArrayList;

public class AdminSymptomsAdd extends ActionBarActivity {

    Button btnAdd, btnCancel;
    EditText edtSymptomName, edtSymptomGreekName, edtSymptomDesc;

    MM_Person userLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_symptoms_add);

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
            btnAdd = (Button)findViewById(R.id.btnAdminConfirmAddSymptom);
            btnCancel = (Button)findViewById(R.id.btnAdminConfirmCancelSymptom);
            edtSymptomName = (EditText)findViewById(R.id.edtAdminSymptomNameAdd);
            edtSymptomGreekName = (EditText)findViewById(R.id.edtAdminSymptomGreekNameAdd);
            edtSymptomDesc = (EditText)findViewById(R.id.edtAdminSymptomDescAdd);

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
        Intent intent = new Intent(this, AdminSymptomsHome.class);
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
                bll.addSymptomAdmin(symptom);
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
        getMenuInflater().inflate(R.menu.menu_admin_symptoms_add, menu);
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
