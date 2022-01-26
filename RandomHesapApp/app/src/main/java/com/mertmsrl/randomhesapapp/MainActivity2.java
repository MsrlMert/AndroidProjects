package com.mertmsrl.randomhesapapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    private TextView textViewUser;
    private Button buttonStartCounter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        textViewUser = findViewById(R.id.textViewUser);
        String user = intent.getStringExtra("message");
        textViewUser.setText(user);
        buttonStartCounter = findViewById(R.id.buttonStartCounter);


    }

    public void startCounter(View view) {
        new CountDownTimer(10100,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                textViewUser.setText(String.valueOf(millisUntilFinished));
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }
}