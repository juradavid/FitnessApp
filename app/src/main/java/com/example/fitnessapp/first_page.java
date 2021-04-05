package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class first_page extends AppCompatActivity {

    public static boolean CONNECTION;

    private boolean isActive;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Button sign_in_email;
    TextView sTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        sharedPreferences =  getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);

        sTextView = findViewById(R.id.sTextView);
        SpannableString spannableString = new SpannableString(sTextView.getText());
        spannableString.setSpan(new ForegroundColorSpan(Color.rgb(146,227,169)),10,21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD),10,21,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sTextView.setText(spannableString);

        if(!Network.isConnectedToInternet(getApplicationContext())){
            String activityName = this.getClass().getCanonicalName();
            editor = sharedPreferences.edit();
            editor.putString("lastActivity",activityName);
            editor.commit();
            Intent intent = new Intent(getApplicationContext(),no_internet_connection.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }

        sign_in_email = findViewById(R.id.sign_in_email);
        sign_in_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), second_page.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                overridePendingTransition(R.anim.slide_right, R.anim.slide_out_left);
                startActivity(intent);
            }
        });
    }

    private void content(){
        if(isActive) {
            if(!hasWindowFocus()){
                if(!Network.isConnectedToInternet(getApplicationContext())){
                    String activityName = this.getClass().getCanonicalName();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("lastActivity", activityName);
                    editor.commit();
                    isActive = false;
                    Intent intent = new Intent(getApplicationContext(),no_internet_connection.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                }
            }
            refresh(500);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(!Network.isConnectedToInternet(getApplicationContext())){
            sharedPreferences =  getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
            editor = sharedPreferences.edit();
            String activityName = this.getClass().getCanonicalName();
            editor.putString("lastActivity", activityName);
            editor.commit();
            Intent intent = new Intent(getApplicationContext(),no_internet_connection.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }
    }

    private void refresh(int miliseconds){
        final Handler fHandler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                content();
            }
        };

        fHandler.postDelayed(runnable, miliseconds);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(!hasFocus){
            isActive = true;
            content();
        }else{
            isActive = false;
        }
    }


    //When user press on back button the activity will be put in the background of the phone
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}