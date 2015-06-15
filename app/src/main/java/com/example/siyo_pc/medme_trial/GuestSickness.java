package com.example.siyo_pc.medme_trial;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.siyo_pc.medme_trial.adapters.SicknessAdapter;
import com.example.siyo_pc.medme_trial.classes.MM_Sickness;
import com.example.siyo_pc.medme_trial.db.MedMe_Helper;

import java.util.ArrayList;
import java.util.List;


public class GuestSickness extends ActionBarActivity {

    private ListView listSickness;
    private MedMe_Helper medMeDB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_sickness);

        medMeDB = new MedMe_Helper(this);
        fillSicknessList();
    }

    public void fillSicknessList(){

        ArrayList<MM_Sickness> sicknessList2 = medMeDB.GetAllSicknesses();
        SicknessAdapter adapter = new SicknessAdapter(this, sicknessList2);
        listSickness = (ListView)findViewById(R.id.listView1);
        View header = getLayoutInflater().inflate(R.layout.listview_header_row, null);
        listSickness.addHeaderView(header);
        listSickness.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guest_sickness, menu);
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
