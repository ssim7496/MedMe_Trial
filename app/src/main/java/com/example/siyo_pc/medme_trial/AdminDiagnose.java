package com.example.siyo_pc.medme_trial;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.adapters.AdminSymptomAdapter;
import com.example.siyo_pc.medme_trial.adapters.AdminSymptomCheckBoxAdapter;
import com.example.siyo_pc.medme_trial.classes.MM_Person;
import com.example.siyo_pc.medme_trial.classes.MM_Symptom;
import com.example.siyo_pc.medme_trial.db.AsyncGetAllSymptoms;
import com.example.siyo_pc.medme_trial.db.AsyncSearchResponse;
import com.example.siyo_pc.medme_trial.db.AsyncTaskResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdminDiagnose extends AppCompatActivity implements AsyncTaskResponse {

    MM_Person userLoggedIn;
    private Button btnDiagnose;
    private ListView listSymptoms;

    private AsyncGetAllSymptoms asyncAllSymptoms = null;
    private ArrayList<MM_Symptom> symptomList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_diagnose);

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
            btnDiagnose = (Button)findViewById(R.id.btnAdminDiagnose);
            listSymptoms = (ListView)findViewById(R.id.listViewAdminDiagnoseResults);

            addButtonEvents();
        }
    }

    public void addButtonEvents(){
        final Context context = this;
        final AsyncTaskResponse asyncTaskResponse = this;

        btnDiagnose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asyncAllSymptoms = new AsyncGetAllSymptoms(asyncTaskResponse, context);
                asyncAllSymptoms.execute();
            }
        });
        //underConstruction(btnRegister);
        //underConstruction(btnLogIn);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AdminHome.class);
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
            /*AdminSymptomCheckBoxAdapter adapter = new AdminSymptomCheckBoxAdapter(this, symptomList, userLoggedIn);
            View header = getLayoutInflater().inflate(R.layout.listview_diagnose_header, null);
            listSymptoms.addHeaderView(header);
            listSymptoms.setAdapter(adapter);*/

            String[] symptomNames = new String[symptomList.size()];

            for (int i = 0; i < symptomList.size(); i++) {
                symptomNames[i] = symptomList.get(i).GetSymptomName();
            }

            /*listSymptoms.setAdapter(new ArrayAdapter<String>(this, R.layout.listview_diagnose_item, symptomNames));
            listSymptoms.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);*/

            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_multiple_choice, symptomNames);

            /*GridView gridView = (GridView) findViewById(R.id.gvSymptoms);
            gridView.setAdapter(adapter2);*/
            listSymptoms.setAdapter(adapter2);

        }
    }
}
