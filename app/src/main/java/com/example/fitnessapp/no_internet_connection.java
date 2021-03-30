package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class no_internet_connection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet_connection);
    }

    //When user press on back button the activity will be put in the background of the phone
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}