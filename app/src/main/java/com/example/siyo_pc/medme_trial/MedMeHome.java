package com.example.siyo_pc.medme_trial;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

import java.net.URI;

public class MedMeHome extends ActionBarActivity
    /*implements MedMeHomeFragment.OnFragmentInteractionListener, MedMeStartFragment.OnFragmentInteractionListener*/{

    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_me_home);
    }

    /*@Override
    public void onNavigationDrawerItemSelected(int position){
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = MedMeStartFragment.newInstance(position + 1);
                break;
            case 1:
                fragment = MedMeHomeFragment.newInstance(position + 1);
                break;
        }

        onSectionAttached(position);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }

    public void onSectionAttached(int number){
        switch (number+1) {
            case 1:
                mTitle = getString(R.string.title_activity_med_me_start);
                break;
            case 2:
                mTitle = getString(R.string.title_activity_med_me_home);
                break;
        }
    }

    public void restoreActionBar(){
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_med_me_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public void onFragmentInteraction(Uri uri){
        //what should this activbity do when fragment interacts with it
    }*/
}
