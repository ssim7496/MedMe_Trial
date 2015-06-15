package com.example.siyo_pc.medme_trial;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.siyo_pc.medme_trial.classes.MM_Sickness;
import com.example.siyo_pc.medme_trial.db.MedMe_Helper;


public class GuestSicknessView extends ActionBarActivity {

    MedMe_Helper medMeDB = null;
    TextView sicknessTitle, sicknessDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_sickness_view);

        sicknessTitle = (TextView)findViewById(R.id.tvSicknessTitle);
        sicknessDesc = (TextView)findViewById(R.id.tvSicknessDescription);

        Intent intent = getIntent();
        String sick = intent.getStringExtra("sickness");

        medMeDB = new MedMe_Helper(this);
        MM_Sickness sickness = medMeDB.GetSicknessByID(Integer.parseInt(sick));

        sicknessTitle.setText(sickness.GetSicknessName());
        sicknessDesc.setText(sickness.GetSicknessDesc());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guest_sickness_view, menu);
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
