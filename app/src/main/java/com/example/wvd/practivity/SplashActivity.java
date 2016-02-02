package com.example.wvd.practivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SplashActivity extends Activity {

    private TextView splash_text;
    private Handler handler;

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_screen);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/lobster.ttf");

        splash_text = (TextView) findViewById(R.id.splash_text);
        splash_text.setTypeface(custom_font);

        /* previous code */

        //launch the next screen after delay
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                // close this activity
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
