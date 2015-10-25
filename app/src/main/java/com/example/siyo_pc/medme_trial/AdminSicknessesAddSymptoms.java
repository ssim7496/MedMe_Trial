package com.example.siyo_pc.medme_trial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.adapters.AdminSymptomSpinnerAdapter;
import com.example.siyo_pc.medme_trial.adapters.NothingSelectedSpinnerAdapter;
import com.example.siyo_pc.medme_trial.classes.MM_Disease;
import com.example.siyo_pc.medme_trial.classes.MM_Person;
import com.example.siyo_pc.medme_trial.classes.MM_Sickness;
import com.example.siyo_pc.medme_trial.classes.MM_Symptom;
import com.example.siyo_pc.medme_trial.db.AsyncGetAllSymptoms;
import com.example.siyo_pc.medme_trial.db.AsyncTaskResponse;
import com.example.siyo_pc.medme_trial.db.BusinessLogic;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdminSicknessesAddSymptoms extends AppCompatActivity implements AsyncTaskResponse{

    MM_Person userLoggedIn;
    MM_Sickness sicknessSaved;
    MM_Disease diseaseChosen;

    private AsyncGetAllSymptoms asyncAllSymptoms = new AsyncGetAllSymptoms(this, this);
    private ArrayList<MM_Symptom> symptomList = null;
    private ArrayList<MM_Symptom> symptomToAddList = new ArrayList<>();

    LinearLayout linSymptoms = null;
    Button btnAdd, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sicknesses_add_symptoms);

        try {
            Intent intent = getIntent();
            userLoggedIn = (MM_Person) intent.getParcelableExtra("userCred");
            sicknessSaved = (MM_Sickness) intent.getParcelableExtra("sickToAdd");
            diseaseChosen = (MM_Disease) intent.getParcelableExtra("dissToAdd");
        } catch (Exception e) {

        }

        if (userLoggedIn == null) {
            Toast.makeText(this, "Access restricted! No user is logged in", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Start.class);
            startActivity(intent);
        } else {
            linSymptoms = (LinearLayout)findViewById(R.id.linAdminSicknessSymptomsAdd);
            btnAdd = (Button) findViewById(R.id.btnAdminSicknessSymptomAdd);
            btnBack = (Button) findViewById(R.id.btnAdminSicknessSymptomBack);

            try {
                asyncAllSymptoms.execute();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Oops. Something went wrong and we will get to it very soon.", Toast.LENGTH_LONG).show();
            }

            addButtonEvents();
        }
    }

    private  void addButtonEvents() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSymptomsToSickness();
            }
        });

        previousActivity(btnBack);
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
        Intent intent = new Intent(this, AdminSicknessesAdd.class);
        intent.putExtra("userCred", userLoggedIn);
        startActivity(intent);
    }

    @Override
    public void onTaskCompleted(List<JSONObject> objectList, int passTypeID) {
        symptomList = convertToSymptoms(objectList);

        fillSymptomsCheckBoxes(symptomList);
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

    private void fillSymptomsCheckBoxes(final ArrayList<MM_Symptom> symptomList){
        try {

            for (int i = 0; i < symptomList.size(); i++) {
                final int count = i;
                CheckBox cbSymptom = new CheckBox(getApplicationContext());
                cbSymptom.setText(symptomList.get(i).GetSymptomName());
                cbSymptom.setTextSize(30);
                linSymptoms.addView(cbSymptom);
                cbSymptom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked)
                            symptomToAddList.add(symptomList.get(count));
                        else
                            symptomToAddList.remove(symptomList.get(count));
                    }
                });
            }

        } catch (Exception e) {

        }
    }

    private void addSymptomsToSickness() {
        if (diseaseChosen != null) {
            if (sicknessSaved != null) {
                if (symptomToAddList != null && symptomToAddList.size() > 0) {
                    BusinessLogic bll = new BusinessLogic(this, userLoggedIn);
                    bll.addSicknessAdmin(sicknessSaved, diseaseChosen, symptomToAddList);
                } else {
                    Toast.makeText(getApplicationContext(), "Please choose at least one symptom.", Toast.LENGTH_LONG).show();
                }
            } else {
                onBackPressed();
                Toast.makeText(getApplicationContext(), "No sickness has been made for symptoms to be added.", Toast.LENGTH_LONG).show();
            }
        } else {
            onBackPressed();
            Toast.makeText(getApplicationContext(), "No disease has been chosen for a sickness to have symptoms.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_sicknesses_add_symptoms, menu);
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
