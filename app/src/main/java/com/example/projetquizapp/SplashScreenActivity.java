package com.example.projetquizapp;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new CountDownTimer(3000,1000){
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                SharedPreferences sharedPreferences = getSharedPreferences("ProjetQuizzApp",MODE_PRIVATE);
                if(sharedPreferences.getString("USER","").isEmpty()){
                    startActivity(new Intent(SplashScreenActivity.this,LoginActivity.class));
                }else{
                    startActivity(new Intent(SplashScreenActivity.this,TopicActivity.class));
                }
                SplashScreenActivity.this.finish();
            }
        }.start();

    }

}