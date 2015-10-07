package com.example.siyo_pc.medme_trial;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.adapters.AdminSearchAdapter;
import com.example.siyo_pc.medme_trial.classes.MM_Person;
import com.example.siyo_pc.medme_trial.db.AsyncAdminSearch;
import com.example.siyo_pc.medme_trial.db.AsyncSearchResponse;
import com.example.siyo_pc.medme_trial.db.AsyncTaskResponse;
import com.example.siyo_pc.medme_trial.db.XML_Entry;
import com.example.siyo_pc.medme_trial.db.XML_EntryList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class AdminSearch extends AppCompatActivity implements AsyncSearchResponse{

    MM_Person userLoggedIn;

    private ListView listSearchResults;
    private Button btnAdminSearch;
    private EditText edtAdminSearchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_search);

        try {
            Intent intent = getIntent();
            userLoggedIn = (MM_Person) intent.getParcelableExtra("userCred");
        } catch (Exception e) {

        }

        if (userLoggedIn == null) {
            Toast.makeText(this, "Access restricted! No user is logged in", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Start.class);
            startActivity(intent);
        } else {
            btnAdminSearch = (Button)findViewById(R.id.btnAdminSearch);
            edtAdminSearchText = (EditText)findViewById(R.id.edtAdminSearchText);
            listSearchResults = (ListView)findViewById(R.id.listViewAdminSearchResults);
            View header = getLayoutInflater().inflate(R.layout.listview_search_header, null);
            listSearchResults.addHeaderView(header);
            addButtonEvents();
        }

    }

    public void addButtonEvents(){
        final Context context = this;
        final AsyncSearchResponse asyncSearchResponse = this;

        btnAdminSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = edtAdminSearchText.getText().toString();
                if ((searchText.trim()).equals("") || (searchText.trim()).length() < 1) {
                    Toast.makeText(context, "Please enter some text to search for.", Toast.LENGTH_LONG).show();
                } else {
                    new AsyncAdminSearch(asyncSearchResponse, context).execute(searchText);
                }
            }
        });
        //underConstruction(btnRegister);
        //underConstruction(btnLogIn);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AdminHome.class);
        intent.putExtra("userCred", userLoggedIn);
        startActivity(intent);
    }

    @Override
    public void onTaskCompleted(String xmlSearch) {
        XML_EntryList entryList = new XML_EntryList(this, xmlSearch);
        ArrayList<XML_Entry> searchResults = entryList.showNow();
        fillSearchList(searchResults);
    }

    private void fillSearchList(ArrayList<XML_Entry> searchResults) {
        if (searchResults != null) {
            AdminSearchAdapter adapter = new AdminSearchAdapter(this, searchResults, userLoggedIn);

            listSearchResults.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_search, menu);
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
