package com.example.machinetest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = findViewById(R.id.imageView);


        int splashTimeOut = 2000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(i);
                finish();
            }
        }, splashTimeOut);

        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.splash);
        imageView.startAnimation(myanim);
    }
}