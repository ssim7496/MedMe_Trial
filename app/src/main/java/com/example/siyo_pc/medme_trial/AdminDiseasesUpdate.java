package com.example.siyo_pc.medme_trial;

import android.content.Intent;
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
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.adapters.DiseaseSpinnerAdapter;
import com.example.siyo_pc.medme_trial.adapters.NothingSelectedSpinnerAdapter;
import com.example.siyo_pc.medme_trial.classes.MM_Disease;
import com.example.siyo_pc.medme_trial.db.MedMe_Helper;

import java.util.ArrayList;


public class AdminDiseasesUpdate extends ActionBarActivity {

    MedMe_Helper medMeDB = null;
    Button btnUpdate, btnCancel;
    EditText edtDiseaseName, edtDiseaseGreekName, edtDiseaseDesc;
    Spinner spnDiseaseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_diseases_update);

        medMeDB = new MedMe_Helper(this);
        spnDiseaseList = (Spinner)findViewById(R.id.spinnerUpdateDisease);
        btnUpdate = (Button)findViewById(R.id.btnAdminConfirmUpdate);
        btnCancel = (Button)findViewById(R.id.btnAdminConfirmCancel);
        edtDiseaseName = (EditText)findViewById(R.id.edtAdminDiseaseName);
        edtDiseaseGreekName = (EditText)findViewById(R.id.edtAdminDiseaseGreekName);
        edtDiseaseDesc = (EditText)findViewById(R.id.edtAdminDiseaseDesc);

        addNextActivityOnClickListener(btnCancel, AdminDiseasesHome.class);

        ArrayList<MM_Disease> diseaseList = medMeDB.GetAllDiseases();
        final ArrayAdapter<MM_Disease> adapter = new DiseaseSpinnerAdapter(this, android.R.layout.simple_spinner_item,diseaseList);

        spnDiseaseList.setPrompt("Please select a Disease");
        spnDiseaseList.setAdapter(new NothingSelectedSpinnerAdapter(
                adapter, R.layout.spinner_row_default_disease, this
        ));
        spnDiseaseList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //int count = 0;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*if (count >= 1) {
                    int item = spnDiseaseList.getSelectedItemPosition();
                    MM_Disease disease = adapter.getItem(item);
                    Toast.makeText(getApplicationContext(), Integer.toString(disease.GetDiseaseID()), Toast.LENGTH_LONG).show();
                }
                else {
                    count++;
                }*/
                //count++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Intent intent = getIntent();
        boolean tried = tryParseInt(intent.getStringExtra("disease"));
        if (tried == true) {
            String diss = intent.getStringExtra("disease");

            final MM_Disease disease = medMeDB.GetDiseaseByID(Integer.parseInt(diss));

            edtDiseaseName.setText(disease.GetDiseaseName());
            edtDiseaseGreekName.setText(disease.GetGreekName());
            edtDiseaseDesc.setText(disease.GetDiseaseDesc());

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateDisease(disease, edtDiseaseName, edtDiseaseGreekName, edtDiseaseDesc);
                }
            });
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtDiseaseName == null && edtDiseaseGreekName == null && edtDiseaseDesc == null){
                    Toast.makeText(getApplicationContext(), "Please fill in all fields.", Toast.LENGTH_LONG).show();
                }
                else if (edtDiseaseName.length() <= 1 && edtDiseaseGreekName.length() <= 1 && edtDiseaseDesc.length() <= 1) {
                    Toast.makeText(getApplicationContext(), "All fields must be at least 2 characters in length.", Toast.LENGTH_LONG).show();
                }
            }
        });
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

    public void updateDisease(MM_Disease currentDisease, EditText diseaseName, EditText greekName, EditText diseaseDesc) {

        if (diseaseName != null && greekName != null && diseaseDesc != null){
            if (diseaseName.length() > 1 && greekName.length() > 1 && diseaseDesc.length() > 1) {
                Integer dID = currentDisease.GetDiseaseID();
                String gName = greekName.getText().toString();
                String dName = diseaseName.getText().toString();
                String dDesc = diseaseDesc.getText().toString();
                MM_Disease disease = new MM_Disease(dID, gName, dName, dDesc);
                medMeDB.UpdateDisease(disease);

                Toast.makeText(getApplicationContext(), "Successfully updated.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), AdminDiseasesHome.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(), "All fields must be at least 2 characters in length.", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Please fill in all fields.", Toast.LENGTH_LONG).show();
        }
    }

    boolean tryParseInt(String value)
    {
        try
        {
            Integer.parseInt(value);
            return true;
        } catch(NumberFormatException nfe)
        {
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_diseases_update, menu);
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
