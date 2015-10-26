package com.example.siyo_pc.medme_trial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.classes.MM_Person;

public class HelpSearching extends AppCompatActivity {

    MM_Person userLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_searching);

        try {
            Intent intent = getIntent();
            userLoggedIn = (MM_Person) intent.getParcelableExtra("userCred");
        } catch (Exception e) {

        }

        if (userLoggedIn == null) {
            Toast.makeText(this, "Access restricted! No user is logged in", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Start.class);
            startActivity(intent);
        } else {

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Help.class);
        intent.putExtra("userCred", userLoggedIn);
        startActivity(intent);
    }
}
