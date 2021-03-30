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

public class MainActivity extends AppCompatActivity {

    public static String SHARED_PREFS = "com.example.fitnessapp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //After 5 seconds from the last command the new activity well be launched
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), first_page.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right, R.anim.slide_out_left);
                finish();
            }
        },5000);
    }

    @Override
    protected void onStart() {

        //We check if the user use the light mode or the night mode, after we save this in one variable
        SharedPreferences.Editor editor = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE).edit();
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

        //We check if the user have internet connection
        ConnectivityManager connectivityManager = (ConnectivityManager)getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(!isConnected){
            Intent intent = new Intent(getApplicationContext(), no_internet_connection.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else{
            super.onStart();
        }
    }


    //Back button is disable
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
    }
}