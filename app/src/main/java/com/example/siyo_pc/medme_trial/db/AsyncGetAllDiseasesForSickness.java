package com.example.siyo_pc.medme_trial.db;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AsyncGetAllDiseasesForSickness extends AsyncTask<Void, Void, List<JSONObject>> {

    private Context context;
    private ProgressDialog progressDialog;
    private JSON_Handler jsonHandler = new JSON_Handler();
    private String url;
    private String message;
    public List<JSONObject> jsonObjectList = new ArrayList<>();
    public AsyncTaskResponse callBack = null;
    private ArrayList<NameValuePair> params = new ArrayList<>();

    public AsyncGetAllDiseasesForSickness(AsyncTaskResponse callBack, Context context, ArrayList<NameValuePair> params) {
        this.callBack = callBack;
        this.url = "http://ssimayi-medme.co.za/getAllDiseasesForSickness.php";
        this.context = context;
        this.params = params;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Refreshing information");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected List<JSONObject> doInBackground(Void... param) {
        try {

            String result = jsonHandler.getJSONFromUrl(url, params );

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

    }
}