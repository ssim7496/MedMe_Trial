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
import com.example.siyo_pc.medme_trial.classes.MM_Sickness;
import com.example.siyo_pc.medme_trial.db.MedMe_Helper;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SicknessAdapter extends ArrayAdapter<MM_Sickness>{

    Context context;
    int layoutResourceId;
    //MM_Sickness[] data = null;
    ArrayList<MM_Sickness> data = null;

    //public SicknessAdapter(Context context, int layoutResourceId, MM_Sickness[] data) {
    public SicknessAdapter(Context context, ArrayList<MM_Sickness> data) {
        //super(context, layoutResourceId, data);
        super(context, 0, data);
        this.context = context;
        //this.layoutResourceId = layoutResourceId;
        this.data = data;
        //this.data = data;
    }

    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        SicknessHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new SicknessHolder();
            //holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);

            row.setTag(holder);
        }
        else
        {
            holder = (SicknessHolder)row.getTag();
        }

        final MM_Sickness sickness = data[position];
        holder.txtTitle.setText(sickness.GetSicknessName());

        holder.txtTitle.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), sickness.GetSicknessName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, GuestSicknessView.class);

                context.startActivity(intent);
            }
        });
        //holder.imgIcon.setImageResource(weather.icon);

        return row;
    }*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MM_Sickness sickness = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_item_row, parent, false);
        }

        TextView tvName = (TextView)convertView.findViewById(R.id.txtTitle);
        tvName.setText(sickness.GetSicknessName());

        tvName.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), sickness.GetSicknessName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, GuestSicknessView.class);
                Bundle bundle = intent.getExtras();
                intent.putExtra("sickness", Integer.toString(sickness.GetID()));
                //intent.putExtra("sickness", );
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    static class SicknessHolder{
        //ImageView imgIcon;
        TextView txtTitle;
    }

    /*put after the intent
    Bundle bundle = intent.getExtras();
                MedMe_Helper db = new MedMe_Helper(context);
                MM_Sickness sickness2 = db.GetSicknessByID(2);

                intent.putExtra("sickness", Integer.toString(sickness2.GetID()));*/
    //intent.putExtra("sickness", );
}
