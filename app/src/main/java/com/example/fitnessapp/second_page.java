package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class second_page extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    TextView sHeader;
    CheckBox checkBox;

    int isActive = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);

        sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
        sHeader = findViewById(R.id.sHeader);
        checkBox = findViewById(R.id.checkBox);

        String tempText = (String) sHeader.getText();
        SpannableString ssHeader = new SpannableString(tempText);
        ssHeader.setSpan(new UnderlineSpan(),32,39, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssHeader.setSpan(new ForegroundColorSpan(Color.rgb(146,227,169)),32,39,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssHeader.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(getApplicationContext(), first_page.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                overridePendingTransition(R.anim.slide_right, R.anim.slide_out_left);
                startActivity(intent);
            }
        },32,39,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sHeader.setText(ssHeader);
        sHeader.setMovementMethod(LinkMovementMethod.getInstance());
        sHeader.setHighlightColor(Color.TRANSPARENT);

        tempText = (String) checkBox.getText();
        SpannableString sCheckBox = new SpannableString(tempText);
        sCheckBox.setSpan(new UnderlineSpan(),13,37,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sCheckBox.setSpan(new ForegroundColorSpan(Color.rgb(146,227,169)), 13,37,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sCheckBox.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(getApplicationContext(), first_page.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                overridePendingTransition(R.anim.slide_right, R.anim.slide_out_left);
                startActivity(intent);
            }
        },13,37,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        checkBox.setText(sCheckBox);
        checkBox.setMovementMethod(LinkMovementMethod.getInstance());
        checkBox.setHighlightColor(Color.TRANSPARENT);

        if(!Network.isConnectedToInternet(getApplicationContext())){
            String activityName = this.getClass().getCanonicalName();
            editor = sharedPreferences.edit();
            editor.putString("lastActivity", activityName);
            editor.commit();
            Intent intent = new Intent(getApplicationContext(), no_internet_connection.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }
    }

    private void content(){
        if(isActive == 1) {
            if (!hasWindowFocus()) {
                if (!Network.isConnectedToInternet(getApplicationContext())) {
                    String activityName = this.getClass().getCanonicalName();
                    sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString("lastActivity", activityName);
                    editor.commit();
                    isActive = 0;
                    Intent intent = new Intent(getApplicationContext(), no_internet_connection.class);
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

    private void refresh(int miliseconds){
        Handler fHandler = new Handler();

        Runnable fRunnable = new Runnable() {
            @Override
            public void run() {
                content();
            }
        };

        fHandler.postDelayed(fRunnable,miliseconds);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(!hasFocus){
            isActive = 1;
            content();
        }else{
            isActive = 0;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("SALUT");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(!Network.isConnectedToInternet(getApplicationContext())){
            String activityName = this.getClass().getCanonicalName();
            sharedPreferences =  getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString("lastActivity", activityName);
            editor.commit();
            Intent intent = new Intent(getApplicationContext(), no_internet_connection.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), first_page.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        overridePendingTransition(R.anim.slide_left, R.anim.slide_out_right);
        startActivity(intent);
    }
}