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

import com.example.siyo_pc.medme_trial.AdminSicknessView;
import com.example.siyo_pc.medme_trial.GuestSicknessView;
import com.example.siyo_pc.medme_trial.R;
import com.example.siyo_pc.medme_trial.classes.MM_Person;
import com.example.siyo_pc.medme_trial.classes.MM_Sickness;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdminDiagnosedSicknessAdapter extends ArrayAdapter<MM_Sickness> {

    private Context context;
    ArrayList<MM_Sickness> data = null;
    MM_Person userLoggedIn = null;
    private int diagnosedSymptoms;
    private int totalSymptoms;

    public AdminDiagnosedSicknessAdapter(Context context, ArrayList<MM_Sickness> data, MM_Person userLogged, int diagnosedSymptoms, int totalSymptoms) {
        super(context, 0, data);
        this.context = context;
        this.data = data;
        this.userLoggedIn = userLogged;
        this.diagnosedSymptoms = diagnosedSymptoms;
        this.totalSymptoms = totalSymptoms;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MM_Sickness sickness = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_diagnosed_sickness, parent, false);
        }

        TextView tvSicknessNameName = (TextView)convertView.findViewById(R.id.txtDiagnosedSicknessName);
        tvSicknessNameName.setText(sickness.GetSicknessName());
        TextView tvDiagnosedSymptoms = (TextView)convertView.findViewById(R.id.txtSymptomsCountSelected);
        tvDiagnosedSymptoms.setText(Integer.toString(diagnosedSymptoms));
        TextView tvTotalSymptoms = (TextView)convertView.findViewById(R.id.txtSymptomsCountTotal);
        tvTotalSymptoms.setText(Integer.toString(totalSymptoms));

        tvSicknessNameName.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AdminSicknessView.class);
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
