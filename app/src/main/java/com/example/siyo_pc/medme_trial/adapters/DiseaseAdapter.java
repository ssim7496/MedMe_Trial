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
import com.example.siyo_pc.medme_trial.R;
import com.example.siyo_pc.medme_trial.classes.MM_Disease;
import com.example.siyo_pc.medme_trial.classes.MM_Sickness;
import com.example.siyo_pc.medme_trial.db.MedMe_Helper;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DiseaseAdapter extends ArrayAdapter<MM_Disease>{

    Context context;
    ArrayList<MM_Disease> data = null;

    public DiseaseAdapter(Context context, ArrayList<MM_Disease> data) {
        super(context, 0, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MM_Disease disease = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_item_row, parent, false);
        }

        TextView tvName = (TextView)convertView.findViewById(R.id.txtTitle);
        tvName.setText(disease.GetDiseaseName());

        tvName.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), disease.GetDiseaseName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, GuestSicknessView.class);
                Bundle bundle = intent.getExtras();
                intent.putExtra("disease", Integer.toString(disease.GetDiseaseID()));
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
