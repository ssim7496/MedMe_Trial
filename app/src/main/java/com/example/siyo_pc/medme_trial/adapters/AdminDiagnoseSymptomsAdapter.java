package com.example.siyo_pc.medme_trial.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.siyo_pc.medme_trial.AdminSymptomView;
import com.example.siyo_pc.medme_trial.R;
import com.example.siyo_pc.medme_trial.classes.MM_Person;

import java.util.ArrayList;

public class AdminDiagnoseSymptomsAdapter extends ArrayAdapter<String> {

    private Context context;
    ArrayList<String> data = null;
    MM_Person userLoggedIn = null;

    public AdminDiagnoseSymptomsAdapter(Context context, ArrayList<String> data, MM_Person userLoggedIn) {
        super(context, 0, data);
        this.context = context;
        this.data = data;
        this.userLoggedIn = userLoggedIn;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String symptom = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_item_row, parent, false);
        }

        TextView tvName = (TextView)convertView.findViewById(R.id.txtLstTitle);
        tvName.setText(symptom);

        return convertView;
    }
}
