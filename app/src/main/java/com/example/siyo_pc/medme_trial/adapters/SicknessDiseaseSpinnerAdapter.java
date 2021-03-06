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
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.AdminDiseasesUpdate;
import com.example.siyo_pc.medme_trial.AdminSicknessesAdd;
import com.example.siyo_pc.medme_trial.R;
import com.example.siyo_pc.medme_trial.classes.MM_Disease;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SicknessDiseaseSpinnerAdapter extends ArrayAdapter<MM_Disease>{

    Context context;
    int resource;
    ArrayList<MM_Disease> data = null;

    public SicknessDiseaseSpinnerAdapter(Context context, int resource, ArrayList<MM_Disease> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        final MM_Disease disease = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item_row, parent, false);
        }

        final TextView tvName = (TextView)convertView.findViewById(R.id.itemName);
        tvName.setText(disease.GetDiseaseName());

        tvName.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent("com.example.siyo_pc.medme_trial.adapters").putExtra("diseaseItem", Integer.toString(disease.GetDiseaseID()));
                context.sendBroadcast(i);
            }
        });

        return convertView;
    }
}