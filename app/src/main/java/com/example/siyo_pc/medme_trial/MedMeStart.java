package com.example.siyo_pc.medme_trial;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MedMeStart extends ActionBarActivity {

    Button btnSearch, btnDiagnose, btnDiseases, btnSymptoms, btnTerminology;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_me_start);

        btnSearch = (Button)findViewById(R.id.btnSearch);
        btnDiagnose = (Button)findViewById(R.id.btnDiagnose);
        btnDiseases = (Button)findViewById(R.id.btnDiseases);
        btnSymptoms = (Button)findViewById(R.id.btnSymptoms);
        btnTerminology = (Button)findViewById(R.id.btnTerminology);

        ArrayList<Button> buttonList = new ArrayList<Button>();
        buttonList.add(btnSearch);
        buttonList.add(btnDiagnose);
        buttonList.add(btnDiseases);
        buttonList.add(btnSymptoms);
        buttonList.add(btnTerminology);

        for (Button button : buttonList){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button button = (Button)v;

                    Context context = getApplicationContext();
                    CharSequence text = button.getText().toString();

                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_med_me_start, menu);
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
