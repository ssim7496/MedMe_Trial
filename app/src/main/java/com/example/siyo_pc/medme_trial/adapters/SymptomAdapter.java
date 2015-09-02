package com.example.siyo_pc.medme_trial.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.GuestSicknessView;
import com.example.siyo_pc.medme_trial.GuestSymptomView;
import com.example.siyo_pc.medme_trial.R;
import com.example.siyo_pc.medme_trial.classes.MM_Disease;
import com.example.siyo_pc.medme_trial.classes.MM_Sickness;
import com.example.siyo_pc.medme_trial.classes.MM_Symptom;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SymptomAdapter extends ArrayAdapter<MM_Symptom>{

    private Context context;
    ArrayList<MM_Symptom> data = null;

    public SymptomAdapter(Context context, ArrayList<MM_Symptom> data) {
        super(context, 0, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MM_Symptom symptom = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_item_row, parent, false);
        }

        TextView tvName = (TextView)convertView.findViewById(R.id.txtTitle);
        tvName.setText(symptom.GetSymptomName());

        tvName.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GuestSymptomView.class);
                Bundle bundle = intent.getExtras();
                intent.putExtra("symptom", Integer.toString(symptom.GetSymptomID()));
                Intent i = new Intent("com.example.siyo_pc.medme_trial.adapters").putExtra("symptomItem", Integer.toString(symptom.GetSymptomID()));
                context.sendBroadcast(i);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
