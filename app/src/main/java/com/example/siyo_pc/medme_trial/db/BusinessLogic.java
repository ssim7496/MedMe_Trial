package com.example.siyo_pc.medme_trial.db;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.GuestHome;
import com.example.siyo_pc.medme_trial.Start;
import com.example.siyo_pc.medme_trial.classes.MM_Disease;
import com.example.siyo_pc.medme_trial.classes.MM_Person;
import com.example.siyo_pc.medme_trial.classes.MM_Symptom;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

public class BusinessLogic{
    Context context;
    private List<NameValuePair> params = new ArrayList<NameValuePair>();
    public JSON_Handler jsonHandler = new JSON_Handler();
    public List<JSONObject> currentList;
    private String urlGetAllPeople = "http://ssimayi-medme.co.za/test.php";

    private String urlAddPerson = "http://www.ssimayi-medme.co.za/insertPerson.php";
    private String urlAddDisease = "http://www.ssimayi-medme.co.za/insertDisease.php";
    private String urlAddSymptom = "http://www.ssimayi-medme.co.za/insertSymptom.php";

    public List<MM_Disease> diseaseList;

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

        DataAccessLayerOperational dataAccess = new DataAccessLayerOperational(urlAddPerson, nameValuePairs, Start.class);
        dataAccess.execute();
    }

    public void addDisease(MM_Disease disease) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("diseaseName", disease.GetDiseaseName()));
        nameValuePairs.add(new BasicNameValuePair("diseaseDesc", disease.GetDiseaseDesc()));
        nameValuePairs.add(new BasicNameValuePair("greekName", disease.GetGreekName()));

        //DataAccessLayerOperational dataAccess = new DataAccessLayerOperational(urlAddDisease, nameValuePairs);
        //dataAccess.execute();
    }

    public void addSymptom(MM_Symptom symptom) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("symptomName", symptom.GetSymptomName()));
        nameValuePairs.add(new BasicNameValuePair("symptomDesc", symptom.GetSymptomDesc()));
        nameValuePairs.add(new BasicNameValuePair("greekName", symptom.GetGreekName()));

        //DataAccessLayerOperational dataAccess = new DataAccessLayerOperational(urlAddDisease, nameValuePairs);
        //dataAccess.execute();
    }

    public class DataAccessLayerOperational extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog = new ProgressDialog(context);
        private List<NameValuePair> params = new ArrayList<NameValuePair>();
        private String url;
        private String message;
        private Class nextActivity;

        public DataAccessLayerOperational(String url, List<NameValuePair> params, Class nextActivity) {
            this.url = url;
            this.params = params;
            this.nextActivity = nextActivity;
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
            return message;
        }

        @Override
        protected void onPostExecute(String retrievedMessage)
        {
            progressDialog.dismiss();
            Toast.makeText(context.getApplicationContext(), retrievedMessage, Toast.LENGTH_LONG).show();

            if (retrievedMessage.equals("You have been successfully registered.")) {
                Intent intent = new Intent(context.getApplicationContext(), nextActivity);
                context.startActivity(intent);
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

    }
}
