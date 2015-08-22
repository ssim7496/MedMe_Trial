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

import com.example.siyo_pc.medme_trial.db.BusinessLogic;


public class Start extends ActionBarActivity {

    Button btnAdmin, btnGuest, btnNurse, btnDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getSupportActionBar().hide();
        addOnClickListener();
    }

    public void addOnClickListener(){
        btnAdmin = (Button)findViewById(R.id.btnAdmin);
        btnGuest = (Button)findViewById(R.id.btnGuest);
        btnNurse = (Button)findViewById(R.id.btnNurse);
        btnDoctor = (Button)findViewById(R.id.btnDoctor);

        addNextActivityOnClickListener(btnAdmin, AdminHome.class);
        addNextActivityOnClickListener(btnGuest, GuestHome.class);
        //addNextActivityOnClickListener(btnNurse, GuestHome.class);
        //addNextActivityOnClickListener(btnDoctor, GuestHome.class);
        //underConstruction(btnAdmin);
        //underConstruction(btnGuest);
        //underConstruction(btnNurse);
        underConstruction(btnDoctor);

        btnNurse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPeople();
            }
        });
    }

    private void getPeople() {
        BusinessLogic bll = new BusinessLogic(this);
        bll.getPeopleList();
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
