package com.example.siyo_pc.medme_trial.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.siyo_pc.medme_trial.AdminDiseaseView;
import com.example.siyo_pc.medme_trial.R;
import com.example.siyo_pc.medme_trial.classes.MM_Disease;
import com.example.siyo_pc.medme_trial.classes.MM_Person;

import java.util.ArrayList;

public class AdminDiseaseAdapter extends ArrayAdapter<MM_Disease>{

    private Context context;
    ArrayList<MM_Disease> data = null;
    MM_Person userLoggedIn = null;

    public AdminDiseaseAdapter(Context context, ArrayList<MM_Disease> data, MM_Person userLoggedIn) {
        super(context, 0, data);
        this.context = context;
        this.data = data;
        this.userLoggedIn = userLoggedIn;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MM_Disease disease = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_item_row, parent, false);
        }

        TextView tvName = (TextView)convertView.findViewById(R.id.txtLstTitle);
        tvName.setText(disease.GetDiseaseName());

        tvName.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AdminDiseaseView.class);
                Bundle bundle = intent.getExtras();
                intent.putExtra("disease", Integer.toString(disease.GetDiseaseID()));
                intent.putExtra("userCred", userLoggedIn);
                Intent i = new Intent("com.example.siyo_pc.medme_trial.adapters").putExtra("diseaseItem", Integer.toString(disease.GetDiseaseID()));
                context.sendBroadcast(i);
                context.startActivity(intent);

            }
        });

        return convertView;
    }
}
