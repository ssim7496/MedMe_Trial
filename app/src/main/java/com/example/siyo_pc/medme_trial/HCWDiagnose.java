package com.example.siyo_pc.medme_trial;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.classes.MM_Person;
import com.example.siyo_pc.medme_trial.classes.MM_Symptom;
import com.example.siyo_pc.medme_trial.db.AsyncGetAllSymptoms;
import com.example.siyo_pc.medme_trial.db.AsyncTaskResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HCWDiagnose extends AppCompatActivity implements AsyncTaskResponse {

    MM_Person userLoggedIn;
    private Button btnDiagnose;
    private ListView listSymptoms;

    private AsyncGetAllSymptoms asyncAllSymptoms = null;
    private ArrayList<MM_Symptom> symptomList = null;
    final ArrayList<String> symptomsSelected = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hcwdiagnose);

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
            btnDiagnose = (Button)findViewById(R.id.btnHCWDiagnose);
            listSymptoms = (ListView)findViewById(R.id.listViewHCWDiagnoseResults);

            try {
                asyncAllSymptoms = new AsyncGetAllSymptoms(this, this);
                asyncAllSymptoms.execute();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Oops. Something went wrong and we will get to it very soon.", Toast.LENGTH_LONG).show();
            }

            addButtonEvents();
        }
    }

    public void addButtonEvents(){
        final Context context = this;
        final AsyncTaskResponse asyncTaskResponse = this;

        btnDiagnose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray checkedSymptoms = listSymptoms.getCheckedItemPositions();

                if (checkedSymptoms.size() > 0) {
                    //getting the checked check boxes in the listview and adding them to an string array
                    for (int i = 0; i < listSymptoms.getAdapter().getCount(); i++) {
                        if (checkedSymptoms.get(i)) {
                            symptomsSelected.add(symptomList.get(i).GetSymptomName());
                        }
                    }
                    //storing symptom id's and names in hashmap
                    HashMap<Integer, String> map = new HashMap<Integer, String>();

                    for (int k = 0; k < symptomList.size(); k++) {
                        map.put(symptomList.get(k).GetSymptomID(), symptomList.get(k).GetSymptomName());
                    }

                    //take to confirm symtpoms page
                    Intent intent = new Intent(getApplicationContext(), HCWDiagnosisSymptomsConfirm.class);
                    intent.putExtra("userCred", userLoggedIn);
                    intent.putExtra("symptomsSel", symptomsSelected);
                    intent.putExtra("symptomList", map);
                    startActivity(intent);

                } else {
                    Toast.makeText(context, "Please select a symptom", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HCWHome.class);
        intent.putExtra("userCred", userLoggedIn);
        startActivity(intent);
    }
    @Override
    public void onTaskCompleted(List<JSONObject> objectList, int passTypeID) {
        symptomList = convertToSymptoms(objectList);

        fillSymptomList(symptomList);
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

    private void fillSymptomList(ArrayList<MM_Symptom> symptomList){

        if (symptomList != null) {

            String[] symptomNames = new String[symptomList.size()];

            for (int i = 0; i < symptomList.size(); i++) {
                symptomNames[i] = symptomList.get(i).GetSymptomName();
            }

            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_multiple_choice, symptomNames);

            listSymptoms.setAdapter(adapter2);
            listSymptoms.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        }
    }
}
