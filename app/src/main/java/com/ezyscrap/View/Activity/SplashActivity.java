package com.ezyscrap.View.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ezyscrap.AppUtils.SharedPref;
import com.ezyscrap.R;

public class SplashActivity extends AppCompatActivity {

    SharedPref pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        pref = new SharedPref(SplashActivity.this);

        System.out.println("******gcm******"+SharedPref.getPreferences().getString(SharedPref.GCMREGID,""));
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                if (pref.getString("isLogin","").equalsIgnoreCase("yes")){
                    startActivity(new Intent(SplashActivity.this,HomeScreen.class));
                    finish();
                }else if (pref.getString("isFlagSet","").equalsIgnoreCase("yes")){
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(SplashActivity.this, ActivityChooser.class));
                    finish();
                }


            }
        }, 2000);
    }
}
