package com.example.siyo_pc.medme_trial;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.classes.MM_Disease;
import com.example.siyo_pc.medme_trial.db.BusinessLogic;
import com.example.siyo_pc.medme_trial.db.XML_EntryList;

import java.util.ArrayList;
import java.util.List;


public class Start extends ActionBarActivity {

    Button btnRegister, btnLogIn;
    public List<MM_Disease> diseaseList = new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getSupportActionBar().hide();
        addButtonEvents();
    }

    public void addButtonEvents(){
        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnLogIn = (Button)findViewById(R.id.btnLogIn);

        addNextActivityOnClickListener(btnRegister, RegisterUser.class);
        //addNextActivityOnClickListener(btnLogIn, LogIn.class);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yes();
            }
        });
        //underConstruction(btnRegister);
        //underConstruction(btnLogIn);
    }

    private void yes() {
        String xml = "<entry_list version=\"1.0\"><entry id=\"{h,1}sex\"><hw>sex</hw><pr>ˈseks</pr><sound><wav>sex00001.wav</wav><wpr>!seks</wpr></sound><fl>noun</fl><def><sensb><sens><sn>1</sn><dt>either of the two major forms of individuals that occur in many species and that are distinguished respectively as male or female</dt></sens></sensb><sensb><sens><sn>2</sn><dt>the sum of the structural, functional, and behavioral characteristics of living things that are involved in reproduction by two interacting parents and that distinguish males and females</dt></sens></sensb><sensb><sens><sn>3 a</sn><dt>sexually motivated phenomena or behavior</dt></sens><sens><sn>b</sn><dt><sx>SEXUAL INTERCOURSE</sx></dt></sens></sensb></def></entry><entry id=\"{h,2}sex\"><hw>sex</hw><fl>transitive verb</fl><def><sensb><sens><dt>to identify the sex of <vi>techniques for <it>sex</it><it>ing</it> human embryos</vi></dt></sens></sensb></def></entry></entry_list>";
        XML_EntryList entryList = new XML_EntryList(this, xml);
        entryList.showNow();
    }

    public void addNextActivityOnClickListener(View view, final Class nextClass) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), nextClass);
                startActivity(intent);
            }
        });
    }

    public void underConstruction(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Currently under construction ... ", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
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
