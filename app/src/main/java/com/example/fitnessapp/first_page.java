package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class first_page extends AppCompatActivity {

    View vector;
    RelativeLayout rectangle;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
    }

    @Override
    protected void onStart() {
        //We apply the light or night graphics
        vector = findViewById(R.id.vector_1);
        rectangle = findViewById(R.id.rectangle_1);
        sharedPreferences =  getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
        String app_mode = sharedPreferences.getString("app_mode", "");
        if(app_mode.equals("light")){
            vector.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.vector_light));
            rectangle.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangle_light));
        }
        super.onStart();
    }

    //When user press on back button the activity will be put in the background of the phone
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public void finish() {
        super.finish();
    }
}