package com.timeset.codechallenge.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.timeset.codechallenge.R;


public class LaunchScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);

        launchAndOpenMain();
    }

    /* Thread to show launcher layout for 2.5 seconds */
    private void launchAndOpenMain(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(LaunchScreenActivity.this, MainActivity.class);
                LaunchScreenActivity.this.startActivity(i);
                LaunchScreenActivity.this.finish();
            }
        },2500);
    }
}
