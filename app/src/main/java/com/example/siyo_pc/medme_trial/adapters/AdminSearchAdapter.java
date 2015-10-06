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
import com.example.siyo_pc.medme_trial.classes.MM_Search;
import com.example.siyo_pc.medme_trial.classes.MM_Sickness;
import com.example.siyo_pc.medme_trial.db.XML_Entry;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdminSearchAdapter extends ArrayAdapter<XML_Entry>{

    private Context context;
    ArrayList<XML_Entry> data = null;
    MM_Person userLoggedIn = null;

    public AdminSearchAdapter(Context context, ArrayList<XML_Entry> data, MM_Person userLogged) {
        super(context, 0, data);
        this.context = context;
        this.data = data;
        this.userLoggedIn = userLogged;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final XML_Entry search = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_search_row, parent, false);
        }

        TextView tvSearchType = (TextView)convertView.findViewById(R.id.tvSearchType);
        TextView tvSearchDescription = (TextView)convertView.findViewById(R.id.tvSearchDescription);
        tvSearchType.setText(search.getFl());

        if (!search.getDt().equals(null))
            tvSearchDescription.setText(search.getDt());
        else if (!search.getSx().equals(null))
            tvSearchDescription.setText(search.getSx());

        /*tvName.setOnClickListener(new TextView.OnClickListener() {
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
        });*/

        return convertView;
    }
}
