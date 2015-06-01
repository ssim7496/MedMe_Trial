package com.example.siyo_pc.medme_trial.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.R;
import com.example.siyo_pc.medme_trial.classes.MM_Sickness;

public class SicknessAdapter extends ArrayAdapter<MM_Sickness>{

    Context context;
    int layoutResourceId;
    MM_Sickness[] data = null;

    public SicknessAdapter(Context context, int layoutResourceId, MM_Sickness[] data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
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

        MM_Sickness sickness = data[position];
        holder.txtTitle.setText(sickness.getSicknessName());

        /*holder.txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), sickness.getSicknessName(), Toast.LENGTH_SHORT);
            }
        });*/
        //holder.imgIcon.setImageResource(weather.icon);

        return row;
    }


    static class SicknessHolder{
        //ImageView imgIcon;
        TextView txtTitle;
    }
}
