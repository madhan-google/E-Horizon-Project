package com.codekiller.ehorizon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.main_image);
        textView = findViewById(R.id.main_text);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                YoYo.with(Techniques.SlideInUp).duration(1500).playOn(textView);
            }
        }, 1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                YoYo.with(Techniques.SlideInDown).duration(1500).playOn(imageView);
            }
        }, 1);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//            }
//        }).start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                Intent intent = ;
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        }, 1500);
    }
}