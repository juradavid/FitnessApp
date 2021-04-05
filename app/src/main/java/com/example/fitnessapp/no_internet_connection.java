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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.muddzdev.styleabletoast.StyleableToast;

public class no_internet_connection extends AppCompatActivity {

    Button tryButton;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet_connection);

        sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
        System.out.println(sharedPreferences.getString("lastActivity","") + " activity");
        tryButton = findViewById(R.id.try_again);
        tryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Network.isConnectedToInternet(getApplicationContext())){
                    String lastActivity = sharedPreferences.getString("lastActivity","");
                    try {
                        Class classByName = Class.forName(lastActivity);
                        Intent intent = new Intent(getApplicationContext(), classByName);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("lastActivity","");
                        editor.commit();
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        finish();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        System.out.println("A APARUT O EROARE");
                    }
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("lastActivity","");
                    editor.commit();
                }else{
                    StyleableToast.makeText(getApplicationContext(),"Check your internet connection", Toast.LENGTH_SHORT,R.style.noInternetConnection).show();
                }
            }
        });
    }

    //When user press on back button the activity will be put in the background of the phone
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
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
}