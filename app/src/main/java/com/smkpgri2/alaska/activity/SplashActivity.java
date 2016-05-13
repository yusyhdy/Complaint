package com.smkpgri2.alaska.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;

import com.smkpgri2.alaska.entity.Complaint;

/**
 * Created by smkpgri2 on 12/05/16.
 */
public class SplashActivity extends AppCompatActivity {


    private final int SPLASH_DISPLAY_LENGHT = 5000;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.activity_splash);
  /*
   * membuat HomeActivity
   */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = null;

                mainIntent = new Intent(SplashActivity.this, LoginActivity.class);

                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
    }
}
