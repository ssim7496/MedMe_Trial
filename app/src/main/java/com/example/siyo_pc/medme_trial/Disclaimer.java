package com.example.siyo_pc.medme_trial;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Disclaimer extends AppCompatActivity {

    Button btnAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclaimer);

        btnAccept = (Button)findViewById(R.id.btnDisclaimerAccept);
        final Context context = this;

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Start.class);
                startActivity(intent);
            }
        });
    }
}
