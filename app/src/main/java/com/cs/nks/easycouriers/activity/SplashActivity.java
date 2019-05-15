package com.cs.nks.easycouriers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.util.UTIL;


public class SplashActivity extends AppCompatActivity {

    boolean BackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        Handler handler;
        Runnable myRunnable;
        handler = new Handler();
        myRunnable = new Runnable() {
            public void run() {
                // do something
                //1000 milli second=1 second

                //UTIL.printKeyHash(SplashActivity.this);

                if (UTIL.isLogin(SplashActivity.this)) {
                if(!BackPressed) {
                    startActivity(new Intent(SplashActivity.this, ActivityWithNavigationMenu.class));

                    finish();
                }
                } else {
                if(!BackPressed) {
                   // startActivity(new Intent(SplashActivity.this, Tab_Login_Register_Activity.class));
                    startActivity(new Intent(SplashActivity.this, ActivityWithNavigationMenu.class));

                    finish();
                }
                }


            }
        };
        handler.postDelayed(myRunnable, 3000);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BackPressed = true;
    }


}
