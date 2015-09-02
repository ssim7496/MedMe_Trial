package com.example.siyo_pc.medme_trial.db;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.GuestHome;
import com.example.siyo_pc.medme_trial.classes.MM_Disease;
import com.example.siyo_pc.medme_trial.classes.MM_Person;
import com.example.siyo_pc.medme_trial.classes.MM_Symptom;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

public class BusinessLogic implements  AsyncTaskResponse{
    Context context;
    private List<NameValuePair> params = new ArrayList<NameValuePair>();
    public JSON_Handler jsonHandler = new JSON_Handler();
    //public List<Object> objectLists = new ArrayList<Object> ();
    public List<JSONObject> currentList;
    private String urlGetAllPeople = "http://ssimayi-medme.co.za/test.php";

    private String urlAddPerson = "http://www.ssimayi-medme.co.za/insertPerson.php";
    private String urlAddDisease = "http://www.ssimayi-medme.co.za/insertDisease.php";
    private String urlAddSymptom = "http://www.ssimayi-medme.co.za/insertSymptom.php";

    private String urlLogin = "http://www.ssimayi-medme.co.za/login.php";
    private String urlGetAllSicknesses = "http://ssimayi-medme.co.za/getAllSicknesses.php";
    private String urlGetAllDiseases = "http://ssimayi-medme.co.za/getAllDiseases.php";
    private String urlGetAllSymptoms = "http://ssimayi-medme.co.za/getAllSymptoms.php";
    public List<MM_Disease> diseaseList = new ArrayList<>();

    public BusinessLogic(Context context) {
        currentList = new ArrayList<>();
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

    public void addDisease(MM_Disease disease) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("diseaseName", disease.GetDiseaseName()));
        nameValuePairs.add(new BasicNameValuePair("diseaseDesc", disease.GetDiseaseDesc()));
        nameValuePairs.add(new BasicNameValuePair("greekName", disease.GetGreekName()));

        DataAccessLayerOperational dataAccess = new DataAccessLayerOperational(urlAddDisease, nameValuePairs);
        dataAccess.execute();
    }

    public void addSymptom(MM_Symptom symptom) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("symptomName", symptom.GetSymptomName()));
        nameValuePairs.add(new BasicNameValuePair("symptomDesc", symptom.GetSymptomDesc()));
        nameValuePairs.add(new BasicNameValuePair("greekName", symptom.GetGreekName()));

        DataAccessLayerOperational dataAccess = new DataAccessLayerOperational(urlAddDisease, nameValuePairs);
        dataAccess.execute();
    }

    public void getAllDisease() {
    //public List<MM_Disease> getAllDisease() {
        DataAccessFetchAll2 dataAccess = new DataAccessFetchAll2(context, urlGetAllDiseases, this);
        dataAccess.execute();

        /*try {
            for (int i = 0; i < currentList.size(); i++) {
                JSONObject jObject = currentList.get(0);
                int diseaseID = Integer.parseInt(jObject.getString("DiseaseID"));
                String diseaseName = jObject.getString("DiseaseName");
                String diseaseDesc = jObject.getString("DiseaseDesc");

                MM_Disease disease = new MM_Disease();
                disease.SetDiseaseID(diseaseID);
                disease.SetDiseaseName(diseaseName);
                disease.SetDiseaseDesc(diseaseDesc);
            }
        } catch (Exception e) {
            Toast.makeText(context.getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }*/
        //Toast.makeText(context, Integer.toString(currentList.size()), Toast.LENGTH_LONG).show();

        //return diseaseList;
    }

    @Override
    public void onTaskCompleted(List<JSONObject> objectList) {
        try{
            currentList = objectList;

            for (int i = 0; i < currentList.size(); i++) {
                JSONObject jObject = currentList.get(0);
                int diseaseID = Integer.parseInt(jObject.getString("DiseaseID"));
                String diseaseName = jObject.getString("DiseaseName");
                String diseaseDesc = jObject.getString("DiseaseDesc");

                MM_Disease disease = new MM_Disease();
                disease.SetDiseaseID(diseaseID);
                disease.SetDiseaseName(diseaseName);
                disease.SetDiseaseDesc(diseaseDesc);
                diseaseList.add(disease);
            }
        } catch (Exception e) {
            Toast.makeText(context.getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
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

    public class DataAccessFetchAll2 extends AsyncTask<Void, Void, List<JSONObject>> {

        private ProgressDialog progressDialog;
        private Context context;
        private String url;
        private String message;
        private JSON_Handler jsonHandler = new JSON_Handler();
        public AsyncTaskResponse delegate = null;

        public List<JSONObject> jsonObjectList = new ArrayList<>();

        public DataAccessFetchAll2(Context context, String url, AsyncTaskResponse asyncResponse) {
            this.context = context;
            this.url = url;
            this.delegate = asyncResponse;
        }

        protected void onPreExecute() {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("We are almost done ...");
            progressDialog.show();
        }

        @Override
        protected List<JSONObject> doInBackground(Void... param) {
            try {
                String result = jsonHandler.getJSONFromUrl(url);

                JSONObject jsonResponse = new JSONObject((result));
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
            delegate.onTaskCompleted(result);
            progressDialog.dismiss();
            super.onPostExecute(result);
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
