package com.example.siyo_pc.medme_trial;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.siyo_pc.medme_trial.adapters.SymptomAdapter;
import com.example.siyo_pc.medme_trial.classes.MM_Symptom;
import com.example.siyo_pc.medme_trial.db.MedMe_Helper;

import java.util.ArrayList;


public class GuestSymptom extends ActionBarActivity {

    private ListView listSymptoms;
    private Button btnViewSymptom;
    private MedMe_Helper medMeDB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_symptom);

        medMeDB = new MedMe_Helper(this);
        fillSymptomList();

        btnViewSymptom = (Button)findViewById(R.id.btnViewSymptom);

        btnViewSymptom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GuestSymptomView.class);
                startActivity(intent);
            }
        });
    }

    public void fillSymptomList(){

        ArrayList<MM_Symptom> symptomList = medMeDB.GetAllSymptoms();

        if (symptomList != null) {
            SymptomAdapter adapter = new SymptomAdapter(this, symptomList);
            listSymptoms = (ListView) findViewById(R.id.listView1);
            View header = getLayoutInflater().inflate(R.layout.listview_header_row, null);
            listSymptoms.addHeaderView(header);
            listSymptoms.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guest_symptom, menu);
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
