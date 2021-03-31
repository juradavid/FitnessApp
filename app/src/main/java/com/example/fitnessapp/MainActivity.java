package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import static android.view.WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;

public class MainActivity extends AppCompatActivity {

    public static String SHARED_PREFS = "com.example.fitnessapp";
    public static boolean CONNECTION = true;
    DevicePolicyManager mDevicePolicyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //We check if the user use the light mode or the night mode, after we save this in one variable
        checkUserMode();

        setContentView(R.layout.activity_main);

        //We check if the user have internet connection
        checkConnection();

        //After 5 seconds from the last command the new activity well be launched
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(CONNECTION) {
                    Intent intent = new Intent(getApplicationContext(), first_page.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_out_left);
                }else{
                    Intent intent = new Intent(getApplicationContext(), no_internet_connection.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_out_left);
                }
            }
        },5000);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        checkUserMode();
        Handler fHandler = new Handler();
        fHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkConnection();
            }
        },2000);
        super.onRestart();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if(hasFocus){
            checkUserMode();
            Handler fHandler = new Handler();
            fHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkConnection();
                }
            },5000);
        }
        super.onWindowFocusChanged(hasFocus);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void checkUserMode(){
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
    }

    public void checkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(!isConnected){
            CONNECTION = false;
        }
    }
}