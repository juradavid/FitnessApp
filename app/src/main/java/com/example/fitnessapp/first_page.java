package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class first_page extends AppCompatActivity {

    View background;

    SharedPreferences sharedPreferences;

    public static boolean CONNECTION = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        //We apply the light or night graphics
        background = findViewById(R.id.background);
        sharedPreferences =  getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
        String app_mode = sharedPreferences.getString("app_mode", "");
        if(app_mode.equals("light")){
            background.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_light));
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        while(CONNECTION && !hasFocus){
            checkConnection();
        }
        if(!CONNECTION){
            Intent intent = new Intent(getApplicationContext(), no_internet_connection.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    //When user press on back button the activity will be put in the background of the phone
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
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