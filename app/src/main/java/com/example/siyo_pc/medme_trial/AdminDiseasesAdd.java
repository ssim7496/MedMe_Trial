package com.example.siyo_pc.medme_trial;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.classes.MM_Disease;
import com.example.siyo_pc.medme_trial.db.MedMe_Helper;


public class AdminDiseasesAdd extends ActionBarActivity {

    MedMe_Helper medMeDB = null;
    Button btnAdd, btnCancel;
    EditText edtDiseaseName, edtDiseaseGreekName, edtDiseaseDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_diseases_add);

        medMeDB = new MedMe_Helper(this);
        btnAdd = (Button)findViewById(R.id.btnAdminConfirmAdd);
        btnCancel = (Button)findViewById(R.id.btnAdminConfirmCancel);
        edtDiseaseName = (EditText)findViewById(R.id.edtAdminDiseaseName);
        edtDiseaseGreekName = (EditText)findViewById(R.id.edtAdminDiseaseGreekName);
        edtDiseaseDesc = (EditText)findViewById(R.id.edtAdminDiseaseDesc);

        addNextActivityOnClickListener(btnCancel, AdminDiseasesHome.class);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDisease(edtDiseaseName, edtDiseaseGreekName, edtDiseaseDesc);
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

    public void addDisease(EditText diseaseName, EditText greekName, EditText diseaseDesc) {

        if (diseaseName != null && greekName != null && diseaseDesc != null){
            if (diseaseName.length() > 1 && greekName.length() > 1 && diseaseDesc.length() > 1) {
                String gName = greekName.getText().toString();
                String dName = diseaseName.getText().toString();
                String dDesc = diseaseDesc.getText().toString();
                MM_Disease disease = new MM_Disease(gName, dName, dDesc);
                medMeDB.AddDisease(disease);

                Toast.makeText(getApplicationContext(), "Successfully added.", Toast.LENGTH_LONG).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_diseases_add, menu);
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
