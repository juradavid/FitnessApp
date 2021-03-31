package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;

public class no_internet_connection extends AppCompatActivity {

    public static boolean CONNECTION = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet_connection);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    //When user press on back button the activity will be put in the background of the phone
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("PAUSE");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("RESUME");
    }


    public void checkUserMode(){
        SharedPreferences.Editor editor = getSharedPreferences(MainActivity.SHARED_PREFS,MODE_PRIVATE).edit();
        int nightModeFlags =
                getApplicationContext().getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch(nightModeFlags){
            case Configuration.UI_MODE_NIGHT_YES:
                editor.putString("app_mode", "night");
                editor.commit();
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                editor.putString("app_mode", "light");
                editor.commit();
                break;
        }
    }

    public void checkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(isConnected) {
            CONNECTION = true;
        }
    }
}