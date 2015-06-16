package com.example.siyo_pc.medme_trial;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.siyo_pc.medme_trial.adapters.DiseaseAdapter;
import com.example.siyo_pc.medme_trial.classes.MM_Disease;
import com.example.siyo_pc.medme_trial.db.MedMe_Helper;

import java.util.ArrayList;

public class GuestDisease extends ActionBarActivity {

    private ListView listDiseases;
    private Button btnViewDisease;
    private MedMe_Helper medMeDB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_disease);

        medMeDB = new MedMe_Helper(this);
        fillDiseaseList();

        btnViewDisease = (Button)findViewById(R.id.btnViewDisease);

        btnViewDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GuestDiseaseView.class);
                startActivity(intent);
            }
        });
    }

    public void fillDiseaseList(){

        ArrayList<MM_Disease> diseaseList = medMeDB.GetAllDiseases();

        if (diseaseList != null) {
            DiseaseAdapter adapter = new DiseaseAdapter(this, diseaseList);
            listDiseases = (ListView) findViewById(R.id.listView1);
            View header = getLayoutInflater().inflate(R.layout.listview_header_row, null);
            listDiseases.addHeaderView(header);
            listDiseases.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guest_disease, menu);
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
