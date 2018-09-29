 package com.defvest.devfestnorth.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.defvest.devfestnorth.R;

import java.util.Timer;
import java.util.TimerTask;

 public class WelcomeScreen extends AppCompatActivity {
     long Lokaci = 5000;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        Timer gudu = new Timer();
        TimerTask ShowSplash = new TimerTask(){
            public void run(){
                finish();
                Intent nextIntent = new Intent(WelcomeScreen.this,MainActivity.class);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                startActivity(nextIntent);

            }
        };
        gudu.schedule(ShowSplash, Lokaci);

    }
}
