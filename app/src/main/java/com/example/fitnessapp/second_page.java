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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.muddzdev.styleabletoast.StyleableToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;

public class second_page extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    TextView sHeader;
    CheckBox checkBox;
    Button registerButton;

    EditText username;
    EditText email;
    EditText password;
    EditText phone;

    int isActive = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);

        sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
        sHeader = findViewById(R.id.sHeader);
        checkBox = findViewById(R.id.checkBox);
        registerButton = findViewById(R.id.registerButton);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);

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

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()){
                        register();
                }else{
                    StyleableToast.makeText(getApplicationContext(),"Check the information your provided", R.style.noInternetConnection, Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    public void register(){
        String postUrl = "http://167.99.246.91/fitnessapp/register.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {
            postData.put("username", username.getText().toString());
            postData.put("email", email.getText().toString());
            postData.put("password", password.getText().toString());
            postData.put("phone", phone.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    System.out.println(response.getString("result"));
                    if(response.getString("result").equals("trimis")){
                        StyleableToast.makeText(getApplicationContext(),"Contul a fost creat cu succes", Toast.LENGTH_SHORT).show();
                    }
                    else if(response.getString("result").equals("inregistrat")){
                        StyleableToast.makeText(getApplicationContext(),"Email-ul este deja folosit", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                StyleableToast.makeText(getApplicationContext(),"Este o problema la server", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
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