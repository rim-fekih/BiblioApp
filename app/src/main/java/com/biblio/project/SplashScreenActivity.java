package com.biblio.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {

    LottieAnimationView lottieAnimationView ;

    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        lottieAnimationView = findViewById(R.id.animation);

        lottieAnimationView.animate().translationY(2000).setDuration(1000).setStartDelay(4000);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run(){
                Intent intent = new Intent(SplashScreenActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        },2500);
    }
}