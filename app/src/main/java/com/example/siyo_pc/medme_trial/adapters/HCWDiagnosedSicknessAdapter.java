package com.example.siyo_pc.medme_trial.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.siyo_pc.medme_trial.AdminSicknessView;
import com.example.siyo_pc.medme_trial.HCWSicknessView;
import com.example.siyo_pc.medme_trial.R;
import com.example.siyo_pc.medme_trial.classes.MM_DiagnosedSymptoms;
import com.example.siyo_pc.medme_trial.classes.MM_Person;
import com.example.siyo_pc.medme_trial.classes.MM_Sickness;

import java.util.ArrayList;

public class HCWDiagnosedSicknessAdapter extends ArrayAdapter<MM_Sickness> {

    private Context context;
    ArrayList<MM_Sickness> data = null;
    ArrayList<MM_DiagnosedSymptoms> symptomData = null;
    MM_Person userLoggedIn = null;

    public HCWDiagnosedSicknessAdapter(Context context, ArrayList<MM_Sickness> data, MM_Person userLogged, ArrayList<MM_DiagnosedSymptoms> symptomData) {
        super(context, 0, data);
        this.context = context;
        this.data = data;
        this.userLoggedIn = userLogged;
        this.symptomData = symptomData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MM_Sickness sickness = getItem(position);
        MM_DiagnosedSymptoms symptom = symptomData.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_diagnosed_sickness, parent, false);
        }

        TextView tvSicknessNameName = (TextView)convertView.findViewById(R.id.txtDiagnosedSicknessName);
        tvSicknessNameName.setText(sickness.GetSicknessName());
        TextView tvDiagnosedSymptoms = (TextView)convertView.findViewById(R.id.txtSymptomsCountSelected);
        tvDiagnosedSymptoms.setText(Integer.toString(symptom.GetDiagnosedSymptoms()));
        TextView tvTotalSymptoms = (TextView)convertView.findViewById(R.id.txtSymptomsCountTotal);
        tvTotalSymptoms.setText(Integer.toString(symptom.GetAllSymptoms()));
        TextView tvAllSymptoms = (TextView)convertView.findViewById(R.id.txtTotalSymptoms);
        tvAllSymptoms.setText(Integer.toString(symptom.GetTotalSymptomsForSickness()));
        /*TextView tvPercentage = (TextView)convertView.findViewById(R.id.txtPercentage);
        tvPercentage.setText(Double.toString(symptom.GetPercentage()) + "% likely");*/

        tvSicknessNameName.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HCWSicknessView.class);
                Bundle bundle = intent.getExtras();
                intent.putExtra("sickness", Integer.toString(sickness.GetSicknessID()));
                intent.putExtra("userCred", userLoggedIn);
                Intent i = new Intent("com.example.siyo_pc.medme_trial.adapters").putExtra("sicknessItem", Integer.toString(sickness.GetSicknessID()));
                context.sendBroadcast(i);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}