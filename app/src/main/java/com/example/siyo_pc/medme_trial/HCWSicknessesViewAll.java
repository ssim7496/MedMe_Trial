package com.example.siyo_pc.medme_trial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.adapters.AdminSicknessAdapter;
import com.example.siyo_pc.medme_trial.adapters.HCWSicknessAdapter;
import com.example.siyo_pc.medme_trial.classes.MM_Person;
import com.example.siyo_pc.medme_trial.classes.MM_Sickness;
import com.example.siyo_pc.medme_trial.db.AsyncGetAllSicknesses;
import com.example.siyo_pc.medme_trial.db.AsyncTaskResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HCWSicknessesViewAll extends AppCompatActivity implements AsyncTaskResponse {

    private ListView listSickness;

    MM_Person userLoggedIn;

    private AsyncGetAllSicknesses asyncAllSickness = new AsyncGetAllSicknesses(this, this);
    private ArrayList<MM_Sickness> sicknessList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hcwsicknesses_view_all);

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
            listSickness = (ListView) findViewById(R.id.listViewHCWSicknessList);

            try {
                asyncAllSickness.execute();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Oops. Something went wrong and we will get to it very soon.", Toast.LENGTH_LONG).show();
            }
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
        Intent intent = new Intent(this, HCWSicknessesHome.class);
        intent.putExtra("userCred", userLoggedIn);
        startActivity(intent);
    }

    @Override
    public void onTaskCompleted(List<JSONObject> objectList, int passTypeID) {
        sicknessList = convertToSickness(objectList);
        fillSicknessList(sicknessList);
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

    private void fillSicknessList(ArrayList<MM_Sickness> sicknessList){

        if (sicknessList != null) {
            HCWSicknessAdapter adapter = new HCWSicknessAdapter(this, sicknessList, userLoggedIn);
            View header = getLayoutInflater().inflate(R.layout.listview_header_row, null);
            listSickness.addHeaderView(header);
            listSickness.setAdapter(adapter);
        }
    }
}
