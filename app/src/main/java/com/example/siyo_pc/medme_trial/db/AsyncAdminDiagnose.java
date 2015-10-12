package com.example.siyo_pc.medme_trial.db;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.classes.MM_Symptom;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AsyncAdminDiagnose extends AsyncTask<Void, Void, List<JSONObject>> {

    private Context context;
    private ProgressDialog progressDialog;
    private JSON_Handler jsonHandler = new JSON_Handler();
    private String url;
    private String message;
    public List<JSONObject> jsonObjectList = new ArrayList<>();
    public AsyncTaskResponse callBack = null;
    private ArrayList<MM_Symptom> params = new ArrayList<>();

    public AsyncAdminDiagnose(AsyncTaskResponse callBack, Context context, ArrayList<MM_Symptom> params) {
        this.callBack = callBack;
        this.url = "http://ssimayi-medme.co.za/diagnoseAdmin.php";
        this.context = context;
        this.params = params;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Diagnosing ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected List<JSONObject> doInBackground(Void... param) {
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            String symptomIDs = "";

            for (int i = 0; i < params.size(); i++) {
                symptomIDs += Integer.toString(params.get(i).GetSymptomID()) + "#";
            }
            nameValuePairs.add(new BasicNameValuePair("symptomsSelected", symptomIDs));

            String result = jsonHandler.getJSONFromUrl(url, nameValuePairs );
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
        progressDialog.dismiss();
        callBack.onTaskCompleted(result, 1);
        //Toast.makeText(context, Integer.toString(result.size()) + " sicknesses", Toast.LENGTH_LONG).show();
    }

}
