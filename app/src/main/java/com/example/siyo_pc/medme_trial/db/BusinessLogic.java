package com.example.siyo_pc.medme_trial.db;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.GuestHome;
import com.example.siyo_pc.medme_trial.classes.MM_Person;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

public class BusinessLogic{
    Context context;
    private List<NameValuePair> params = new ArrayList<NameValuePair>();
    public JSON_Handler jsonHandler = new JSON_Handler();
    //public List<Object> objectLists = new ArrayList<Object> ();
    public List<Object> objectLists;
    private String urlGetAllPeople = "http://ssimayi-medme.co.za/test.php";
    private String urlAddPerson = "http://www.ssimayi-medme.co.za/insertPerson.php";
    private String urlLogin = "http://www.ssimayi-medme.co.za/login.php";
    private String urlTestFetch = "http://ssimayi-medme.co.za/testFetch.php";

    public BusinessLogic(Context context) {
        objectLists = new ArrayList<>();
        this.context = context;
    }

    public void addPerson(MM_Person person) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("personName", person.GetPersonName()));
        nameValuePairs.add(new BasicNameValuePair("personSurname", person.GetPersonSurname()));
        nameValuePairs.add(new BasicNameValuePair("personEmailAddress", person.GetPersonEmailAddress()));
        nameValuePairs.add(new BasicNameValuePair("personCellNumber", person.GetPersonCellphoneNumber()));
        nameValuePairs.add(new BasicNameValuePair("personPassword", person.GetPersonPassword()));
        nameValuePairs.add(new BasicNameValuePair("personRecoveryQuestion", person.GetPersonRecoveryQuestion()));
        nameValuePairs.add(new BasicNameValuePair("personRecoveryAnswer", person.GetPersonRecoveryAnswer()));

        DataAccessLayerOperational dataAccess = new DataAccessLayerOperational(urlAddPerson, nameValuePairs);
        dataAccess.execute();
    }

    public class DataAccessLayerOperational extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog = new ProgressDialog(context);
        private List<NameValuePair> params = new ArrayList<NameValuePair>();
        private String url;
        private String message;

        public DataAccessLayerOperational(String url, List<NameValuePair> params) {
            this.url = url;
            this.params = params;
        }
        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Processing ...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... param) {
            try {
                String result = jsonHandler.getJSONFromUrl(url, params);

                JSONObject jsonResponse = new JSONObject((result));
                JSONArray jArray = jsonResponse.getJSONArray("final");
                JSONObject jsonMessage = jArray.getJSONObject(0);
                message = jsonMessage.getString("message");
            } catch (Exception e) {
                Log.e("log_tag", "Error in parsing data ");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String file_url)
        {
            progressDialog.dismiss();
            Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

    }

    public class DataAccessLayerRetrieval extends AsyncTask<Void, Void, List<Object>>{

        private ProgressDialog progressDialog = new ProgressDialog((context));
        private String url;
        private List<NameValuePair> params = new ArrayList<NameValuePair>();
        private String message;
        public List<Object> jsonObjectList = new ArrayList<Object>();
        private BusinessLogic bllClass;

        public DataAccessLayerRetrieval(String url, List<NameValuePair> params, BusinessLogic bllClass) {
            this.url = url;
            this.params = params;
            this.bllClass = bllClass;
        }

        public DataAccessLayerRetrieval(String url) {
            this.url = url;
        }

        protected void onPreExecute() {
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
            Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
            progressDialog.dismiss();

            super.onPostExecute(objects);
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }

    /*class DataAccessLayerMessageRetrieval extends AsyncTask<Void, Void, String> {

        private ProgressDialog progressDialog = new ProgressDialog((context));
        private String url;
        private List<NameValuePair> params = new ArrayList<NameValuePair>();
        private String message;

        public DataAccessLayerMessageRetrieval(String url, List<NameValuePair> params) {
            this.url = url;
            this.params = params;
        }

        public DataAccessLayerMessageRetrieval(String url) {
            this.url = url;
        }

        protected void onPreExecute() {
            progressDialog.setMessage("Doing something ...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... param) {
            try {
                String result = jsonHandler.getJSONFromUrl(url, params);

                JSONObject jsonResponse = new JSONObject((result));
                JSONArray jArray = jsonResponse.getJSONArray("result");

                JSONObject jsonObject = jArray.getJSONObject(0);
                message = jsonObject.getString("message");

            } catch (Exception e) {
                Log.e("log_tag", "Error in parsing data ");
            }
            return message;
        }
        @Override
        protected void onPostExecute(String returned) {
            Toast.makeText(context.getApplicationContext(), returned, Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

    }*/
}
