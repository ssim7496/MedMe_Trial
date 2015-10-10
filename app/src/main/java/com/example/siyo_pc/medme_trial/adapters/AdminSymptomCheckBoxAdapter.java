package com.example.siyo_pc.medme_trial.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.siyo_pc.medme_trial.AdminSymptomsUpdate;
import com.example.siyo_pc.medme_trial.R;
import com.example.siyo_pc.medme_trial.classes.MM_Person;
import com.example.siyo_pc.medme_trial.classes.MM_Symptom;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdminSymptomCheckBoxAdapter extends ArrayAdapter<MM_Symptom> {

    private Context context;
    ArrayList<MM_Symptom> data = null;
    MM_Person userLoggedIn = null;

    public AdminSymptomCheckBoxAdapter(Context context, ArrayList<MM_Symptom> data,  MM_Person userLoggedIn) {
        super(context, 0, data);
        this.context = context;
        this.data = data;
        this.userLoggedIn = userLoggedIn;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        final MM_Symptom symptom = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_diagnose_item, parent, false);
        }

        CheckBox cbSymptom = (CheckBox)convertView.findViewById(R.id.cbSymptom);
        //cbSymptom.setText(symptom.GetSymptomName());

        /*tvName.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//*Intent intent = new Intent(context, AdminSymptomsUpdate.class);
                Bundle bundle = intent.getExtras();
                intent.putExtra("symptom", Integer.toString(symptom.GetSymptomID()));
                context.startActivity(intent);*//*
                Intent i = new Intent("com.example.siyo_pc.medme_trial.adapters").putExtra("symptomItem2", Integer.toString(symptom.GetSymptomID()));
                context.sendBroadcast(i);
            }
        });*/

        return convertView;
    }
}