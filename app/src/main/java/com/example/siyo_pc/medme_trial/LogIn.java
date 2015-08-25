package com.example.siyo_pc.medme_trial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.classes.MM_Person;
import com.example.siyo_pc.medme_trial.db.BusinessLogic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogIn extends AppCompatActivity {

    Button btnBack, btnLogIn;
    EditText edtEmail, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        getSupportActionBar().hide();
        addButtonEvents();
    }

    public void addButtonEvents(){
        btnBack = (Button)findViewById(R.id.btnLogInBack);
        btnLogIn = (Button)findViewById(R.id.btnLogIn);

        //addNextActivityOnClickListener(btnRegister, RegisterUser.class);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInPerson();
            }
        });
        previousActivity(btnBack);
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

    public void previousActivity(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(Start.class);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed(Start.class);

        }

        return super.onKeyDown(keyCode, event);
    }

    public void onBackPressed(final Class previousClass) {
        Intent myIntent = new Intent(getApplicationContext(), previousClass);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(myIntent);
        finish();
        return;
    }

    private void logInPerson() {
        edtEmail = (EditText)findViewById(R.id.edtLogInEmail);
        edtPassword = (EditText)findViewById(R.id.edtLogInPassword);

        try {
            boolean checkData = checkFields(edtEmail, edtPassword);

            if (checkData) {
                MM_Person person = new MM_Person();
                person.SetPersonEmailAddress(edtEmail.getText().toString());
                person.SetPersonPassword(edtPassword.getText().toString());

                BusinessLogic bll = new BusinessLogic(this);
                bll.logInPerson(person);

                Intent intent = new Intent(getApplicationContext(), GuestHome.class);
                startActivity(intent);
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Oops. Something went wrong and we will get to it very soon.", Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkFields(EditText edtEmail, EditText edtPassword) {
        boolean result = false;

        //perform empty fields check
        boolean emptyEmail = checkEmpty(edtEmail);
        boolean emptyPassword = checkEmpty(edtPassword);

        if (emptyEmail == true || emptyPassword == true) {
            Toast.makeText(getApplicationContext(), "Please fill in all fields.", Toast.LENGTH_LONG).show();
            result = false;
        } else {
            result = true;
        }
        return  result;
    }

    private boolean checkEmpty(EditText value) {
        boolean result = false;

        if ("".equals(value.getText().toString().trim()) || value.getText().toString().trim() == null){
            result = true;
        } else {
            result = false;
        }

        return result;
    }

    //default methods
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_in, menu);
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
