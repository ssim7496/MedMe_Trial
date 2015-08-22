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

import com.example.siyo_pc.medme_trial.AdminDiseasesUpdate;
import com.example.siyo_pc.medme_trial.R;
import com.example.siyo_pc.medme_trial.classes.MM_Disease;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DiseaseSpinnerAdapter extends ArrayAdapter<MM_Disease>{

    Context context;
    int resource;
    ArrayList<MM_Disease> data = null;

    public DiseaseSpinnerAdapter(Context context, int resource, ArrayList<MM_Disease> data) {
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

        TextView tvName = (TextView)convertView.findViewById(R.id.itemName);
        tvName.setText(disease.GetDiseaseName());

        tvName.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(context, AdminDiseasesUpdate.class);
                Bundle bundle = intent.getExtras();
                intent.putExtra("disease", Integer.toString(disease.GetDiseaseID()));
                context.startActivity(intent);*/
                Intent i = new Intent("com.example.siyo_pc.medme_trial.adapters").putExtra("diseaseItem2", Integer.toString(disease.GetDiseaseID()));
                context.sendBroadcast(i);
            }
        });

        return convertView;
    }
}