package com.example.siyo_pc.medme_trial.db;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class AsyncAdminSearch extends AsyncTask<String, Void, String> {

    private Context context;
    private ProgressDialog progressDialog;
    private JSON_Handler jsonHandler = new JSON_Handler();
    private String url;
    private String message;
    public List<JSONObject> jsonObjectList = new ArrayList<>();
    public AsyncSearchResponse callBack = null;

    public AsyncAdminSearch(AsyncSearchResponse callBack, Context context) {
        this.callBack = callBack;
        this.url = "http://ssimayi-medme.co.za/searchWebster.php?word=";
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Refreshing information");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String result = null;
        try {

            try {
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url + params[0]);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                result = EntityUtils.toString(httpEntity);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*JSONObject jsonResponse = new JSONObject((result));
            JSONArray jArray = jsonResponse.getJSONArray("@attributes");*/

            /*for (int i = 0; i < jArray.length(); i++) {
                JSONObject jsonObject = jArray.getJSONObject(i);
                jsonObjectList.add(jsonObject);
            }*/
        } catch (Exception e) {
            Log.e("log_tag", "Error in parsing data ");
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        progressDialog.dismiss();
        callBack.onTaskCompleted(result);

    }
}

 /*class XML extends java.lang.Object {
    public XML()
    {

    }

     public static java.lang.String escape(java.lang.String string)
     {

     }

     public static void noSpace(java.lang.String string)
             throws JSONException
     {

     }

     public static java.lang.Object stringToValue(java.lang.String string)
     {

     }

     public static JSONObject toJSONObject(java.lang.String string)
             throws JSONException
     {

     }

     public static java.lang.String toString(java.lang.Object object)
             throws JSONException
     {

     }

     public static java.lang.String toString(java.lang.Object object, java.lang.String tagName)
             throws JSONException
     {

     }
}*/