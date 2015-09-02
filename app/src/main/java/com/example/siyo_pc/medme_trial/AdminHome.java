package com.example.siyo_pc.medme_trial;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.classes.MM_Disease;
import com.example.siyo_pc.medme_trial.classes.MM_Person;
import com.example.siyo_pc.medme_trial.classes.MM_Sickness;
import com.example.siyo_pc.medme_trial.classes.MM_Symptom;
import com.example.siyo_pc.medme_trial.db.AsyncTaskResponse;
import com.example.siyo_pc.medme_trial.db.BusinessLogic;
import com.example.siyo_pc.medme_trial.db.JSON_Handler;
import com.example.siyo_pc.medme_trial.db.MedMe_Helper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class AdminHome extends ActionBarActivity{

    MedMe_Helper medMeDB = null;
    Button btnSearch, btnDiagnose, btnDiseases, btnSymptoms, btnTerminology, btnSicknesses;
    MM_Person userLoggedIn;

    String jsonResult;
    JSONArray jArray = null;
    List<MM_Disease> diseaseList = null;
    List<MM_Symptom> symptomList = null;
    List<MM_Sickness> sicknessList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        try {
            Intent intent = getIntent();
            userLoggedIn = (MM_Person) intent.getParcelableExtra("userCred");
        } catch (Exception e) {

        }

        /*if (userLoggedIn == null) {
            Toast.makeText(this, "Access restricted! No user is logged in", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Start.class);
            startActivity(intent);
        } else {*/
            diseaseList = new ArrayList<>();
            sicknessList = new ArrayList<>();
            symptomList = new ArrayList<>();
            //getAllDiseases();
            //getAllSymptoms();
            //getAllSicknesses();

            medMeDB = new MedMe_Helper(this);

            btnSearch = (Button)findViewById(R.id.btnAdminSearch);
            btnDiagnose = (Button)findViewById(R.id.btnAdminDiagnose);
            btnDiseases = (Button)findViewById(R.id.btnAdminDiseases);
            btnSymptoms = (Button)findViewById(R.id.btnAdminSymptoms);
            btnTerminology = (Button)findViewById(R.id.btnAdminTerminology);
            btnSicknesses = (Button)findViewById(R.id.btnAdminSicknesses);

            addNextActivityOnClickListener(btnDiseases, AdminDiseasesHome.class);
            //addNextActivityOnClickListener(btnSearch, GuestSearch.class);
            //addNextActivityOnClickListener(btnDiagnose, GuestDiagnose.class);
            addNextActivityOnClickListener(btnSymptoms, AdminSymptomsHome.class);
            //addNextActivityOnClickListener(btnTerminology, GuestTerminology.class);
            addNextActivityOnClickListener(btnSicknesses, AdminSicknessesHome.class);
            //addNextActivityOnClickListener(btnHelp, GuestHelp.class);

            //underConstruction(btnDiseases);
            underConstruction(btnSearch);
            underConstruction(btnDiagnose);
            //underConstruction(btnSymptoms);
            underConstruction(btnTerminology);
            //underConstruction(btnSicknesses);
        //}

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

    public void underConstruction(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Currently under construction ... ", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*finish();*/
                finishAffinity();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void getAllDiseases() {
        class GetDiseaseData extends  AsyncTask<String, Void, String>{

            @Override
            protected String doInBackground(String... params) {
                InputStream is = null;
                String result  = null;

                try {
                    DefaultHttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://ssimayi-medme.co.za/getAllDiseases.php");

                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    is = httpEntity.getContent();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    BufferedReader reader =  new BufferedReader(new InputStreamReader(is));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    is.close();
                    result = sb.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return  result;
            }

            @Override
            protected void onPostExecute(String result) {
                jsonResult = result;
                showDiseaseList();
            }
        }

        GetDiseaseData dataAccess = new GetDiseaseData();
        dataAccess.execute();
    }

    protected void showDiseaseList() {
        try{
            JSONObject jObject = new JSONObject(jsonResult);
            jArray = jObject.getJSONArray("finalFetch");

            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jsonDisease = jArray.getJSONObject(i);
                int diseaseID = Integer.parseInt(jsonDisease.getString("DiseaseID"));
                String diseaseName = jsonDisease.getString("DiseaseName");
                String diseaseDesc = jsonDisease.getString("DiseaseDesc");

                MM_Disease disease = new MM_Disease();
                disease.SetDiseaseID(diseaseID);
                disease.SetDiseaseName(diseaseName);
                disease.SetDiseaseDesc(diseaseDesc);
                diseaseList.add(disease);
            }

            this.diseaseList = retrieveDiseaseList(diseaseList);

        } catch (Exception e) {

        }
    }

    protected List<MM_Disease> retrieveDiseaseList(List<MM_Disease> diseases){
        return diseases;
    }

    public void getAllSymptoms() {
        class GetSymptomData extends  AsyncTask<String, Void, String>{

            @Override
            protected String doInBackground(String... params) {
                InputStream is = null;
                String result  = null;

                try {
                    DefaultHttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://ssimayi-medme.co.za/getAllSymptoms.php");

                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    is = httpEntity.getContent();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    BufferedReader reader =  new BufferedReader(new InputStreamReader(is));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    is.close();
                    result = sb.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return  result;
            }

            @Override
            protected void onPostExecute(String result) {
                jsonResult = result;
                showSymptomList();
            }
        }

        GetSymptomData dataAccess = new GetSymptomData();
        dataAccess.execute();
    }

    protected void showSymptomList() {
        try{
            JSONObject jObject = new JSONObject(jsonResult);
            jArray = jObject.getJSONArray("finalFetch");

            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jsonSymptom = jArray.getJSONObject(i);
                int symptomID = Integer.parseInt(jsonSymptom.getString("SymptomID"));
                String symptomName = jsonSymptom.getString("SymptomName");
                String symptomDesc = jsonSymptom.getString("SymptomDesc");

                MM_Symptom symptom = new MM_Symptom();
                symptom.SetSymptomID(symptomID);
                symptom.SetSymptomName(symptomName);
                symptom.SetSymptomDesc(symptomDesc);
                symptomList.add(symptom);
            }

        } catch (Exception e) {

        }
    }

    public void getAllSicknesses() {
        class GetSicknessData extends  AsyncTask<String, Void, String>{

            @Override
            protected String doInBackground(String... params) {
                InputStream is = null;
                String result  = null;

                try {
                    DefaultHttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://ssimayi-medme.co.za/getAllSicknesses.php");

                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    is = httpEntity.getContent();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    BufferedReader reader =  new BufferedReader(new InputStreamReader(is));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    is.close();
                    result = sb.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return  result;
            }

            @Override
            protected void onPostExecute(String result) {
                jsonResult = result;
                showSicknessList();
            }
        }

        GetSicknessData dataAccess = new GetSicknessData();
        dataAccess.execute();
    }

    protected void showSicknessList() {
        try{
            JSONObject jObject = new JSONObject(jsonResult);
            jArray = jObject.getJSONArray("finalFetch");

            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jsonSickness = jArray.getJSONObject(i);
                int sicknessID = Integer.parseInt(jsonSickness.getString("SicknessID"));
                String sicknessName = jsonSickness.getString("SicknessName");
                String sicknessDesc = jsonSickness.getString("SicknessDesc");

                MM_Sickness sickness = new MM_Sickness();
                sickness.SetSicknessID(sicknessID);
                sickness.SetSicknessName(sicknessName);
                sickness.SetSicknessDesc(sicknessDesc);
                sicknessList.add(sickness);
            }

        } catch (Exception e) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_home, menu);
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

    /*private class DataAccessAllDisease extends AsyncTask<Void, Void, List<JSONObject>> {

        private ProgressDialog progressDialog;
        private Context context;
        private String url;
        private String message;
        private JSON_Handler jsonHandler = new JSON_Handler();
        public AsyncTaskResponse delegate = null;

        public List<JSONObject> jsonObjectList = new ArrayList<>();

        public DataAccessAllDisease(Context context, String url, AsyncTaskResponse asyncResponse) {
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
    }*/
}
