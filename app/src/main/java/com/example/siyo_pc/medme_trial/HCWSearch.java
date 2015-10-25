package com.example.siyo_pc.medme_trial;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.adapters.AdminSearchAdapter;
import com.example.siyo_pc.medme_trial.classes.MM_Person;
import com.example.siyo_pc.medme_trial.db.AsyncAdminSearch;
import com.example.siyo_pc.medme_trial.db.AsyncSearchResponse;
import com.example.siyo_pc.medme_trial.db.XML_Entry;
import com.example.siyo_pc.medme_trial.db.XML_EntryList;

import java.util.ArrayList;

public class HCWSearch extends AppCompatActivity implements AsyncSearchResponse {

    MM_Person userLoggedIn;

    private ListView listSearchResults;
    private Button btnGuestSearch;
    private EditText edtGuestSearchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hcwsearch);

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
            btnGuestSearch = (Button)findViewById(R.id.btnHCWSearch);
            edtGuestSearchText = (EditText)findViewById(R.id.edtHCWSearchText);
            listSearchResults = (ListView)findViewById(R.id.listViewHCWSearchResults);
            View header = getLayoutInflater().inflate(R.layout.listview_search_header, null);
            listSearchResults.addHeaderView(header);
            addButtonEvents();
        }

    }

    public void addButtonEvents(){
        final Context context = this;
        final AsyncSearchResponse asyncSearchResponse = this;

        btnGuestSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = edtGuestSearchText.getText().toString();
                if ((searchText.trim()).equals("") || (searchText.trim()).length() < 1) {
                    Toast.makeText(context, "Please enter some text to search for.", Toast.LENGTH_LONG).show();
                } else if (searchText.contains(" ")){
                    Toast.makeText(context, "Please search for a single word only.", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        new AsyncAdminSearch(asyncSearchResponse, context).execute(searchText);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Oops. Something went wrong and we will get to it very soon.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        //underConstruction(btnRegister);
        //underConstruction(btnLogIn);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HCWHome.class);
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
}
