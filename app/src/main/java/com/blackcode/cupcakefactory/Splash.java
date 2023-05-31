package com.blackcode.cupcakefactory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        // Create a new Handler to delay the start of MainActivity
        new Handler().postDelayed(() -> {
            // Start MainActivity after SPLASH_TIME_OUT
            Intent i = new Intent(Splash.this, LoginActivity.class);
            startActivity(i);

            // Close this activity
            finish();
        }, SPLASH_TIME_OUT);
    }
}