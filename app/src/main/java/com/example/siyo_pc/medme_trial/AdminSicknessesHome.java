package com.example.siyo_pc.medme_trial;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class AdminSicknessesHome extends ActionBarActivity {

    Button btnAdd, btnUpdate, btnViewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sicknesses_home);

        btnAdd = (Button)findViewById(R.id.btnAdminAddSickness);
        btnUpdate = (Button)findViewById(R.id.btnAdminUpdateSickness);
        btnViewAll = (Button)findViewById(R.id.btnAdminViewAllSickness);

        addNextActivityOnClickListener(btnAdd, AdminSicknessesAdd.class);
        addNextActivityOnClickListener(btnUpdate, AdminSicknessesUpdate.class);
        addNextActivityOnClickListener(btnViewAll, AdminSicknessesViewAll.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_sicknesses_home, menu);
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