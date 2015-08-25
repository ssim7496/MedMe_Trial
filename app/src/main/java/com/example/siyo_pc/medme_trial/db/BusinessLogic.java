package com.example.siyo_pc.medme_trial.db;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.classes.MM_Person;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BusinessLogic {
    Context context;
    private List<NameValuePair> params = new ArrayList<NameValuePair>();
    public JSON_Handler jsonHandler = new JSON_Handler();
    private List<JSONObject> objectList = new ArrayList<JSONObject> ();
    private String urlGetAllPeople = "http://ssimayi-medme.co.za/test.php";
    private String urlAddPerson = "http://www.ssimayi-medme.co.za/insertPerson.php";
    private String urlLogin = "http://www.ssimayi-medme.co.za/loginPerson.php";
    private String urlTestFetch = "http://ssimayi-medme.co.za/testFetch.php";

    public BusinessLogic(Context context) {
        this.context = context;
    }

    /*public ArrayList<MM_Person> getPeopleList() {
        ArrayList<MM_Person> peopleList = new ArrayList<MM_Person>();

        params.add(new BasicNameValuePair("", ""));


        try {
            //String hi = dataAccess.execute(new String[]{urlGetAllPeople}).get();
            //new DataAccessLayer(urlGetAllPeople, params).execute("");
            String x = "Hi";
        } catch (Exception e) {

        }
        return  peopleList;
    }*/
    public void getPeopleList() {
        DataAccessLayerRetrieval dataRetrieval = new DataAccessLayerRetrieval(urlTestFetch);
        dataRetrieval.execute();

        List<MM_Person> personList = new ArrayList<MM_Person>();
        /*for (JSONObject json : objectList) {
            MM_Person person = new MM_Person();
            String id = json.getString("A");
        }*/

       /* for (int i = 0; i < objectList.size(); i++) {
            //JSON conversion
        }*/
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

    public void logInPerson(MM_Person person) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("personEmailAddress", person.GetPersonEmailAddress()));
        nameValuePairs.add(new BasicNameValuePair("personPassword", person.GetPersonPassword()));

        DataAccessLayerMessageRetrieval dataAccess = new DataAccessLayerMessageRetrieval(urlLogin, nameValuePairs);
        dataAccess.execute();
    }

    /*public MM_Person getPerson() {
        MM_Person person = new MM_Person();

        return  person;
    }*/

    class DataAccessLayerOperational extends AsyncTask<String, Void, String> {
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

    class DataAccessLayerRetrieval extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog = new ProgressDialog((context));
        private String url;
        private List<NameValuePair> params = new ArrayList<NameValuePair>();
        private String message;

        public DataAccessLayerRetrieval(String url, List<NameValuePair> params) {
            this.url = url;
            this.params = params;
        }

        public DataAccessLayerRetrieval(String url) {
            this.url = url;
        }

        protected void onPreExecute() {
            progressDialog.setMessage("Doing something ...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... param) {
            try {
                String result = jsonHandler.getJSONFromUrl(url, params);

                JSONObject jsonResponse = new JSONObject((result));
                JSONArray jArray = jsonResponse.getJSONArray("finalFetch");
                JSONArray jArrayMessage = jsonResponse.getJSONArray("result");

                for (int i = 0; i < jArray.length(); i++) {
                    /*JSONObject jObject = null;
                    jObject = jArray.getJSONObject(i);

                    String id = jObject.getString("PersonID");
                    String name = jObject.getString("PersonName");
                    String desc = jObject.getString("PersonSurname");*/
                    JSONObject jsonObject = jArray.getJSONObject(i);
                    objectList.add(jsonObject);
                }
            } catch (Exception e) {
                Log.e("log_tag", "Error in parsing data ");
            }
            return null;
        }
        @Override
        protected void onPostExecute(String file_url) {
            Toast.makeText(context.getApplicationContext(), Integer.toString(objectList.size()), Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

    }

    class DataAccessLayerMessageRetrieval extends AsyncTask<String, Void, String> {

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
        protected String doInBackground(String... param) {
            try {
                String result = jsonHandler.getJSONFromUrl(url, params);

                JSONObject jsonResponse = new JSONObject((result));
                JSONArray jArray = jsonResponse.getJSONArray("result");

                JSONObject jsonObject = jArray.getJSONObject(0);
                message = jsonObject.getString("message");

            } catch (Exception e) {
                Log.e("log_tag", "Error in parsing data ");
            }
            return null;
        }
        @Override
        protected void onPostExecute(String file_url) {
            Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

    }
}
