package com.example.siyo_pc.medme_trial;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.adapters.AdminSicknessAdapter;
import com.example.siyo_pc.medme_trial.adapters.HCWSicknessAdapter;
import com.example.siyo_pc.medme_trial.classes.MM_Person;
import com.example.siyo_pc.medme_trial.classes.MM_Sickness;
import com.example.siyo_pc.medme_trial.classes.MM_Symptom;
import com.example.siyo_pc.medme_trial.db.AsyncGetAllSicknessesForSymptom;
import com.example.siyo_pc.medme_trial.db.AsyncGetAllSymptoms;
import com.example.siyo_pc.medme_trial.db.AsyncTaskResponse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HCWSymptomView extends AppCompatActivity implements AsyncTaskResponse {

    ListView listSickness;
    TextView symptomTitle, symptomDesc, symptomGreekName;

    MM_Person userLoggedIn;

    private AsyncGetAllSymptoms asyncAllSymptoms = new AsyncGetAllSymptoms(this, this);
    private ArrayList<MM_Symptom> symptomList = null;
    private ArrayList<MM_Sickness> sicknessList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hcwsymptom_view);

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
            symptomTitle = (TextView) findViewById(R.id.tvHCWSymptomTitle);
            symptomDesc = (TextView) findViewById(R.id.tvHCWSymptomDescription);
            symptomGreekName = (TextView) findViewById(R.id.tvHCWSymptomGreekName);

            try {
                asyncAllSymptoms.execute();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Oops. Something went wrong and we will get to it very soon.", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public void onTaskCompleted(List<JSONObject> objectList, int passTypeID) {
        switch (passTypeID) {
            case 1 : {

            } break;
            case 2 : {
                symptomList = convertToSymptoms(objectList);
                getSymptomInformation(symptomList);
            } break;
            case 3 : {
                sicknessList = convertToSickness(objectList);
                getSicknessInformation(sicknessList);
            } break;
        }
    }

    private ArrayList<MM_Sickness> convertToSickness(List<JSONObject> objectList) {
        if (objectList.size() > 0) {

            sicknessList = new ArrayList<>();

            try {
                for (int i = 0; i < objectList.size(); i++) {
                    JSONObject jObject = objectList.get(i);
                    int sicknessID = jObject.getInt("SicknessID");
                    String greekName = jObject.getString("GreekName");
                    String sicknessName = jObject.getString("SicknessName");
                    String sicknessDesc = jObject.getString("SicknessDesc");

                    MM_Sickness sickness = new MM_Sickness();
                    sickness.SetSicknessID(sicknessID);
                    sickness.SetGreekName(greekName);
                    sickness.SetSicknessName(sicknessName);
                    sickness.SetSicknessDesc(sicknessDesc);

                    sicknessList.add(sickness);
                }
            } catch ( JSONException e) {

            }
        }

        return sicknessList;
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

    private void getSymptomInformation(ArrayList<MM_Symptom> symptomList) {
        Intent intent = getIntent();
        String diss = intent.getStringExtra("symptom");
        MM_Symptom symptom = null;

        for (int i = 0; i < symptomList.size(); i++) {
            if (symptomList.get(i).GetSymptomID() == Integer.parseInt(diss)) {
                symptom = symptomList.get(i);
                break;
            }
        }

        symptomTitle.setText(symptom.GetSymptomName());
        symptomGreekName.setText("Greek Name: \n" + symptom.GetGreekName());
        symptomDesc.setText("Symptom Description: \n" + symptom.GetSymptomDesc());

        //setting alert dialog to show more information
        final Context context = this;
        final MM_Symptom symptomInfo = symptom;

        symptomDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("Symptom Information");
                alertDialog.setMessage(symptomInfo.GetSymptomDesc());
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("symptomID", Integer.toString(symptom.GetSymptomID())));

        try {
            AsyncGetAllSicknessesForSymptom asyncGetAllSicknessesForSymptom = new AsyncGetAllSicknessesForSymptom(this, this, params);
            asyncGetAllSicknessesForSymptom.execute();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Oops. Something went wrong and we will get to it very soon.", Toast.LENGTH_LONG).show();
        }
    }

    private void getSicknessInformation(ArrayList<MM_Sickness> sicknessList) {
        if (sicknessList != null) {
            HCWSicknessAdapter adapter = new HCWSicknessAdapter(this, sicknessList, userLoggedIn);
            listSickness = (ListView) findViewById(R.id.listViewHCWSicknessSymptomLink);
            View header = getLayoutInflater().inflate(R.layout.listview_header_row, null);
            listSickness.addHeaderView(header);
            listSickness.setAdapter(adapter);
        }
    }
}
