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
import com.example.siyo_pc.medme_trial.db.BusinessLogic;
import com.example.siyo_pc.medme_trial.db.CallBackTaskCompleted;
import com.example.siyo_pc.medme_trial.db.JSON_Handler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogIn extends AppCompatActivity {

    Button btnBack, btnLogIn;
    EditText edtEmail, edtPassword;
    List<Object> objectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
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
                edtEmail = (EditText)findViewById(R.id.edtLogInEmail);
                edtPassword = (EditText)findViewById(R.id.edtLogInPassword);

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("personEmailAddress", edtEmail.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("personPassword", edtPassword.getText().toString()));

                DataAccessLogIn taskLogIn = new DataAccessLogIn(this, this, "http://www.ssimayi-medme.co.za/login.php", nameValuePairs);
                taskLogIn.execute();

                if (objectList.size() > 0) {
                    Intent intent = new Intent(this, GuestHome.class);
                    startActivity(intent);
                }
            }
        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), "Oops. Something went wrong and we will get to it very soon.", Toast.LENGTH_LONG).show();
        }
    }

    public void updateList(List<Object> objects) {
        objectList = new ArrayList<>();
        objectList = objects;
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

    public class DataAccessLogIn extends AsyncTask<Void, Void, Void> {

        private ProgressDialog progressDialog;
        private Context context;
        private String url;
        private List<NameValuePair> params = new ArrayList<NameValuePair>();
        private String message;
        private JSON_Handler jsonHandler = new JSON_Handler();
        private LogIn logInActivity;

        public List<Object> jsonObjectList = new ArrayList<Object>();

        public DataAccessLogIn(LogIn logInActivity, Context context, String url, List<NameValuePair> params) {
            this.context = context;
            this.url = url;
            this.params = params;
            this.logInActivity = logInActivity;
        }

        protected void onPreExecute() {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("We're working on it ...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... param) {
            try {
                String result = jsonHandler.getJSONFromUrl(url, params);
                StringTokenizer tokens = new StringTokenizer(result, "###");
                String sObjects = tokens.nextToken();
                String sMessage = tokens.nextToken();

                JSONObject jsonResponse = new JSONObject((sObjects));
                JSONArray jArray = jsonResponse.getJSONArray("finalFetch");
                JSONObject jsonResponseMessage = new JSONObject((sMessage));
                JSONArray jArrayMessage = jsonResponseMessage.getJSONArray("message");

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jsonObject = jArray.getJSONObject(i);
                    jsonObjectList.add(jsonObject);
                }

                message = jArrayMessage.getJSONObject(0).getString("message");
            } catch (Exception e) {
                Log.e("log_tag", "Error in parsing data ");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            logInActivity.updateList(jsonObjectList);

            //Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            super.onPostExecute(result);
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }

    /*public class DataAccessLogIn extends AsyncTask<Void, Void, List<Object>> {

        private ProgressDialog progressDialog;
        private String url;
        private List<NameValuePair> params = new ArrayList<NameValuePair>();
        private String message;
        public List<Object> jsonObjectList = new ArrayList<Object>();
        private JSON_Handler jsonHandler = new JSON_Handler();
        private Context context;

        public DataAccessLogIn(Context context, String url, List<NameValuePair> params) {
            this.context = context;
            this.url = url;
            this.params = params;
        }

        public DataAccessLogIn(String url, List<NameValuePair> params) {
            this.url = url;
            this.params = params;
        }

        public DataAccessLogIn(String url) {
            this.url = url;
        }

        protected void onPreExecute() {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("We're working on it ...");
            progressDialog.show();
        }

        @Override
        protected List<Object> doInBackground(Void... param) {
            try {
                String result = jsonHandler.getJSONFromUrl(url, params);
                StringTokenizer tokens = new StringTokenizer(result, "###");
                String sObjects = tokens.nextToken();
                String sMessage = tokens.nextToken();

                JSONObject jsonResponse = new JSONObject((sObjects));
                JSONArray jArray = jsonResponse.getJSONArray("finalFetch");
                JSONObject jsonResponseMessage = new JSONObject((sMessage));
                JSONArray jArrayMessage = jsonResponseMessage.getJSONArray("message");

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jsonObject = jArray.getJSONObject(i);
                    jsonObjectList.add(jsonObject);
                }

                message = jArrayMessage.getJSONObject(0).getString("message");
            } catch (Exception e) {
                Log.e("log_tag", "Error in parsing data ");
            }
            return jsonObjectList;
        }

        @Override
        protected void onPostExecute(List<Object> objects) {
            //objectList = objects;
            //bllClass.objectLists = objects;

            //objectLists = objects;
            Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            super.onPostExecute(objects);
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }*/

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
