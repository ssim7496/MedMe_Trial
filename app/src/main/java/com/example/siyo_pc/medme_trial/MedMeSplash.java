package com.example.siyo_pc.medme_trial;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

public class MedMeSplash extends ActionBarActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.med_me_splash);
        getSupportActionBar().hide();

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(4000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(MedMeSplash.this, Start.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
