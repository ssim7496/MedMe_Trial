package com.example.siyo_pc.medme_trial;

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
        /*MM_Sickness[] sicknessList = {
                new MM_Sickness("A.I.D.S"),
                new MM_Sickness("Breast Cancer"),
                new MM_Sickness("Cervical Cancer"),
                new MM_Sickness("Cholera"),
                new MM_Sickness("Ebola"),
                new MM_Sickness("Hepatitis"),
                new MM_Sickness("Herpes"),
                new MM_Sickness("Testicular Cancer"),
        };*/

        ArrayList<MM_Sickness> sicknessList2 = medMeDB.GetAllSicknesses();
        SicknessAdapter adapter = new SicknessAdapter(this, sicknessList2);
        listSickness = (ListView)findViewById(R.id.listView1);
        //View header = (View)getLayoutInflater().inflate(R.layout.listview_header_row, null);
        View header = getLayoutInflater().inflate(R.layout.listview_header_row, null);
        listSickness.addHeaderView(header);
        listSickness.setAdapter(adapter);

        /*List<MM_Sickness> sicknessList2 = medMeDB.GetAllSicknesses();

        SicknessAdapter adapter = new SicknessAdapter(this, R.layout.listview_item_row, sicknessList);
        listSickness = (ListView)findViewById(R.id.listView1);

        View header = (View)getLayoutInflater().inflate(R.layout.listview_header_row, null);
        listSickness.addHeaderView(header);
        listSickness.setAdapter(adapter);*/

        //SicknessAdapter adapter = new SicknessAdapter(this, android.R.simple_list_item_1, sicknessList);

        /* Works but is very simple
        listSickness = (ListView)findViewById(R.id.listView1);
        ArrayAdapter<MM_Sickness> adapter = new ArrayAdapter<MM_Sickness>(this, android.R.layout.simple_list_item_1, sicknessList);
        listSickness.setAdapter(adapter);*/
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
