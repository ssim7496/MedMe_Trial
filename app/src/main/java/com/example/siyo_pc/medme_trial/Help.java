package com.example.siyo_pc.medme_trial;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.classes.MM_Person;

public class Help extends AppCompatActivity {

    Button btnAdding, btnUpdating, btnViewing, btnDiagnosing, btnSearching;

    MM_Person userLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

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
            btnAdding = (Button)findViewById(R.id.btnHelpAdd);
            btnUpdating = (Button)findViewById(R.id.btnHelpUpdate);
            btnViewing = (Button)findViewById(R.id.btnHelpViewingInformation);
            btnDiagnosing = (Button)findViewById(R.id.btnHelpDiagnosing);
            btnSearching = (Button)findViewById(R.id.btnHelpSearching);

            addNextActivityOnClickListener(btnAdding, HelpAdding.class);
            addNextActivityOnClickListener(btnUpdating, HelpUpdating.class);
            addNextActivityOnClickListener(btnViewing, HelpViewing.class);
            addNextActivityOnClickListener(btnDiagnosing, HelpDiagnosing.class);
            addNextActivityOnClickListener(btnSearching, HelpSearching.class);
        }
    }

    private void addNextActivityOnClickListener(View view, final Class nextClass) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), nextClass);
                intent.putExtra("userCred", userLoggedIn);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        switch (Integer.parseInt(userLoggedIn.GetPersonRoleID())) {
            case 1: {
                //Admin activity
                Intent intent = new Intent(this, AdminHome.class);
                intent.putExtra("userCred", userLoggedIn);
                startActivity(intent);
            } break;

            case 2: {
                //Health Care Taker activity
                Intent intent = new Intent(this, HCWHome.class);
                intent.putExtra("userCred", userLoggedIn);
                startActivity(intent);
            } break;

            case 3: {
                //Guest activity
                Intent intent = new Intent(this, GuestHome.class);
                intent.putExtra("userCred", userLoggedIn);
                startActivity(intent);
            } break;
        }
    }
}
