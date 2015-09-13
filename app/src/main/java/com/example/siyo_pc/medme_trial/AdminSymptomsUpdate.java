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

import com.example.siyo_pc.medme_trial.adapters.AdminDiseaseSpinnerAdapter;
import com.example.siyo_pc.medme_trial.adapters.AdminSymptomSpinnerAdapter;
import com.example.siyo_pc.medme_trial.adapters.NothingSelectedSpinnerAdapter;
import com.example.siyo_pc.medme_trial.adapters.SymptomSpinnerAdapter;
import com.example.siyo_pc.medme_trial.classes.MM_Disease;
import com.example.siyo_pc.medme_trial.classes.MM_Person;
import com.example.siyo_pc.medme_trial.classes.MM_Symptom;
import com.example.siyo_pc.medme_trial.db.AsyncGetAllDiseases;
import com.example.siyo_pc.medme_trial.db.AsyncGetAllSymptoms;
import com.example.siyo_pc.medme_trial.db.AsyncTaskResponse;
import com.example.siyo_pc.medme_trial.db.BusinessLogic;
import com.example.siyo_pc.medme_trial.db.MedMe_Helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AdminSymptomsUpdate extends ActionBarActivity implements AsyncTaskResponse {

    Button btnUpdate, btnCancel;
    EditText edtSymptomName, edtSymptomGreekName, edtSymptomDesc;
    Spinner spnSymptomList;
    Integer symptomChosen;

    MM_Person userLoggedIn;

    private AsyncGetAllSymptoms asyncAllSymptoms = new AsyncGetAllSymptoms(this, this);
    private ArrayList<MM_Symptom> symptomList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_symptoms_update);

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
            spnSymptomList = (Spinner)findViewById(R.id.spinnerAdminUpdateSymptom);
            btnUpdate = (Button)findViewById(R.id.btnAdminConfirmUpdateSymptom);
            btnCancel = (Button)findViewById(R.id.btnAdminConfirmCancelSymptom);
            edtSymptomName = (EditText)findViewById(R.id.edtAdminSymptomNameUpdate);
            edtSymptomGreekName = (EditText)findViewById(R.id.edtAdminSymptomGreekNameUpdate);
            edtSymptomDesc = (EditText)findViewById(R.id.edtAdminSymptomDescUpdate);

            asyncAllSymptoms.execute();

            addButtonEvents();
            addIntentFiltersAndBroadcastReceivers();
        }
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
        Intent intent = new Intent(this, AdminSymptomsHome.class);
        intent.putExtra("userCred", userLoggedIn);
        startActivity(intent);
    }

    @Override
    public void onTaskCompleted(List<JSONObject> objectList, int passTypeID) {
        symptomList = convertToSymptoms(objectList);

        fillSymptomsSpinner(symptomList);
    }

    private ArrayList<MM_Symptom> convertToSymptoms(List<JSONObject> objectList) {
        if (objectList.size() > 0) {

            symptomList = new ArrayList<>();

            try {
                for (int i = 0; i < objectList.size(); i++) {
                    JSONObject jObject = objectList.get(i);
                    int symptomID = jObject.getInt("SymptomID");
                    String greekName = jObject.getString("GreekName");
                    String symptomName = jObject.getString("SymptomName");
                    String symptomDesc = jObject.getString("SymptomDesc");

                    MM_Symptom symptom = new MM_Symptom();
                    symptom.SetSymptomID(symptomID);
                    symptom.SetGreekName(greekName);
                    symptom.SetSymptomName(symptomName);
                    symptom.SetSymptomDesc(symptomDesc);

                    symptomList.add(symptom);
                }
            } catch ( JSONException e) {

            }
        }

        return symptomList;
    }

    private void fillSymptomsSpinner(ArrayList<MM_Symptom> symptomList){

        final ArrayAdapter<MM_Symptom> adapter = new AdminSymptomSpinnerAdapter(this, android.R.layout.simple_spinner_item, symptomList);

        spnSymptomList.setPrompt("Please select a symptom");
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
    }

    private void addButtonEvents() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSymptom(edtSymptomName, edtSymptomGreekName, edtSymptomDesc);
            }
        });
        previousActivity(btnCancel);
    }

    private void addIntentFiltersAndBroadcastReceivers() {
        IntentFilter intentFilter = new IntentFilter("com.example.siyo_pc.medme_trial.adapters");
        BroadcastReceiver mReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                String msg1 = intent.getStringExtra("symptomItem2");
                MM_Symptom symptom = null;

                for (int i = 0; i < symptomList.size(); i++) {
                    if (symptomList.get(i).GetSymptomID() == Integer.parseInt(msg1)){
                        symptom = symptomList.get(i);
                        symptomChosen = Integer.parseInt(msg1);
                        edtSymptomName.setText(symptom.GetSymptomName());
                        edtSymptomGreekName.setText(symptom.GetGreekName());
                        edtSymptomDesc.setText(symptom.GetSymptomDesc());
                        break;
                    }
                }
            }
        };

        this.registerReceiver(mReceiver, intentFilter);
    }

    public void updateSymptom(EditText symptomName, EditText greekName, EditText symptomDesc) {

        if (symptomChosen != null && symptomName != null && greekName != null && symptomDesc != null){
            if (symptomChosen > 0 && symptomName.length() > 1 && greekName.length() > 1 && symptomDesc.length() > 1) {
                String gName = greekName.getText().toString();
                String dName = symptomName.getText().toString();
                String dDesc = symptomDesc.getText().toString();
                MM_Symptom symptom = new MM_Symptom(symptomChosen, gName, dName, dDesc);
                BusinessLogic bll = new BusinessLogic(this, userLoggedIn);
                bll.updateSymptomAdmin(symptom);
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
