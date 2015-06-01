package com.example.siyo_pc.medme_trial;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.siyo_pc.medme_trial.adapters.SicknessAdapter;
import com.example.siyo_pc.medme_trial.classes.MM_Sickness;


public class Sickness extends ActionBarActivity {

    private ListView listSickness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sickness);
        fillSicknessList();
    }

    public void fillSicknessList(){
        MM_Sickness sicknessList[] = new MM_Sickness[]{
                new MM_Sickness("A.I.D.S"),
                new MM_Sickness("Breast Cancer"),
                new MM_Sickness("Cervical Cancer"),
                new MM_Sickness("Cholera"),
                new MM_Sickness("Ebola"),
                new MM_Sickness("Hepatitis"),
                new MM_Sickness("Herpes"),
                new MM_Sickness("Testicular Cancer"),
        };

        SicknessAdapter adapter = new SicknessAdapter(this, R.layout.listview_item_row, sicknessList);
        listSickness = (ListView)findViewById(R.id.listView1);

        View header = (View)getLayoutInflater().inflate(R.layout.listview_header_row, null);
        listSickness.addHeaderView(header);
        listSickness.setAdapter(adapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sickness, menu);
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
