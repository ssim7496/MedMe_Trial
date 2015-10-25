package com.example.siyo_pc.medme_trial.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.siyo_pc.medme_trial.GuestSymptomView;
import com.example.siyo_pc.medme_trial.R;
import com.example.siyo_pc.medme_trial.classes.MM_Person;
import com.example.siyo_pc.medme_trial.classes.MM_Symptom;

import java.util.ArrayList;

public class SymptomAdapter extends ArrayAdapter<MM_Symptom>{

    private Context context;
    ArrayList<MM_Symptom> data = null;
    MM_Person userLoggedIn = null;

    public SymptomAdapter(Context context, ArrayList<MM_Symptom> data, MM_Person userLoggedIn) {
        super(context, 0, data);
        this.context = context;
        this.data = data;
        this.userLoggedIn = userLoggedIn;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MM_Symptom symptom = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_item_row, parent, false);
        }

        TextView tvName = (TextView)convertView.findViewById(R.id.txtLstTitle);
        tvName.setText(symptom.GetSymptomName());

        tvName.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GuestSymptomView.class);
                Bundle bundle = intent.getExtras();
                intent.putExtra("symptom", Integer.toString(symptom.GetSymptomID()));
                intent.putExtra("userCred", userLoggedIn);
                Intent i = new Intent("com.example.siyo_pc.medme_trial.adapters").putExtra("symptomItem", Integer.toString(symptom.GetSymptomID()));
                context.sendBroadcast(i);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
