package com.example.siyo_pc.medme_trial;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.classes.MM_Person;
import com.example.siyo_pc.medme_trial.db.JSON_Handler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class LogIn extends AppCompatActivity{
    Button btnBack, btnLogIn;
    EditText edtEmail, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        addButtonEvents();
    }

    //setting listeners for buttons
    public void addButtonEvents(){
        btnBack = (Button)findViewById(R.id.btnLogInBack);
        btnLogIn = (Button)findViewById(R.id.btnLogIn);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInPerson();
            }
        });
        previousActivity(btnBack);
    }

    //go to next activity based on button selection
    public void addNextActivityOnClickListener(View view, final Class nextClass) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), nextClass);
                startActivity(intent);
            }
        });
    }

    //go back to previous activity
    public void previousActivity(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(Start.class);
            }
        });
    }

    //overriding default hardware back button to custom implementation
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

    //normal empty fields checks
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

    //logging in a user
    private void logInPerson() {
        edtEmail = (EditText)findViewById(R.id.edtLogInEmail);
        edtPassword = (EditText)findViewById(R.id.edtLogInPassword);

        try {
            boolean checkData = checkFields(edtEmail, edtPassword);

            if (checkData) {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("personEmailAddress", edtEmail.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("personPassword", edtPassword.getText().toString()));


                try {
                    DataAccessLogIn taskLogIn = new DataAccessLogIn(this, "http://www.ssimayi-medme.co.za/login.php", nameValuePairs);
                    taskLogIn.execute();
                }
                catch (Exception e){
                    Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Oops. Something went wrong and we will get to it very soon.", Toast.LENGTH_LONG).show();
        }
    }

    //method to send user to the appropriate activity based on level of secuirity
    private void userHome(MM_Person user) {

        switch (Integer.parseInt(user.GetPersonRoleID())) {
            case 1: {
                //Admin activity
                Intent intent = new Intent(this, AdminHome.class);
                intent.putExtra("userCred", user);
                startActivity(intent);
            } break;

            case 2: {
                //Health Care Taker activity
                Intent intent = new Intent(this, HCWHome.class);
                intent.putExtra("userCred", user);
                startActivity(intent);
            } break;

            case 3: {
                //Guest activity
                Intent intent = new Intent(this, GuestHome.class);
                intent.putExtra("userCred", user);
                startActivity(intent);
            } break;
        }
    }

    //internal async task class for secure log in
    private class DataAccessLogIn extends AsyncTask<Void, Void, List<JSONObject>> {

        private ProgressDialog progressDialog;
        private Context context;
        private String url;
        private List<NameValuePair> params = new ArrayList<NameValuePair>();
        private String message;
        private JSON_Handler jsonHandler = new JSON_Handler();

        public List<JSONObject> jsonObjectList = new ArrayList<>();

        public DataAccessLogIn(Context context, String url, List<NameValuePair> params) {
            this.context = context;
            this.url = url;
            this.params = params;
        }

        protected void onPreExecute() {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Checking user credentials ...");
            progressDialog.show();
        }

        @Override
        protected List<JSONObject> doInBackground(Void... param) {
            try {
                String result = jsonHandler.getJSONFromUrl(url, params);
                StringTokenizer tokens = new StringTokenizer(result, "###");
                String sObjects = tokens.nextToken();
                String sMessage = tokens.nextToken();

                //getting the message
                JSONObject jsonResponseMessage = new JSONObject((sMessage));
                JSONArray jArrayMessage = jsonResponseMessage.getJSONArray("message");

                JSONObject jsonMessageObj = jArrayMessage.getJSONObject(0);
                message = jsonMessageObj.getString("message");

                //getting the json object
                JSONObject jsonResponse = new JSONObject((sObjects));
                JSONArray jArray = jsonResponse.getJSONArray("finalFetch");

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jsonObject = jArray.getJSONObject(i);
                    jsonObjectList.add(jsonObject);
                }

            } catch (Exception e) {
                Log.e("log_tag", "Error in parsing data ");
            }
            return jsonObjectList;
        }

        @Override
        protected void onPostExecute(List<JSONObject> result) {
            progressDialog.dismiss();
            super.onPostExecute(result);

            if (message.equals("Logged in.")) {

                try {
                    JSONObject jObject = result.get(0);
                    String personEmail = jObject.getString("PersonEmailAddress");
                    String personRole = jObject.getString("PersonRoleID");

                    MM_Person user = new MM_Person(personEmail, personRole);

                    userHome(user);
                } catch (JSONException e) {

                }
            } else {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
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
