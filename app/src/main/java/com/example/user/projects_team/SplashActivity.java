package com.example.user.projects_team;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by user on 2016-08-06.
 */
public class SplashActivity extends Activity
{
    @Override
    protected  void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 2초 기다리기
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                //메인 액티비티로
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);

                finish();
            }
        }, 2000);
    }
}
