package com.example.siyo_pc.medme_trial.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.siyo_pc.medme_trial.AdminSymptomsUpdate;
import com.example.siyo_pc.medme_trial.R;
import com.example.siyo_pc.medme_trial.classes.MM_Symptom;
import com.example.siyo_pc.medme_trial.db.MedMe_Helper;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SymptomSpinnerAdapter extends ArrayAdapter<MM_Symptom>{

    Context context;
    int resource;
    ArrayList<MM_Symptom> data = null;

    public SymptomSpinnerAdapter(Context context, int resource, ArrayList<MM_Symptom> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        final MM_Symptom symptom = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item_row, parent, false);
        }

        TextView tvName = (TextView)convertView.findViewById(R.id.itemName);
        tvName.setText(symptom.GetSymptomName());

        tvName.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(context, AdminSymptomsUpdate.class);
                Bundle bundle = intent.getExtras();
                intent.putExtra("symptom", Integer.toString(symptom.GetSymptomID()));
                context.startActivity(intent);*/
                Intent i = new Intent("com.example.siyo_pc.medme_trial.adapters").putExtra("symptomItem2", Integer.toString(symptom.GetSymptomID()));
                context.sendBroadcast(i);
            }
        });

        return convertView;
    }
}