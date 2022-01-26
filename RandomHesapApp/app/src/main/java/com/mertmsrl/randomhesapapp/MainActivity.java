package com.mertmsrl.randomhesapapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ToggleButton toggleButtonAdd, toggleButtonDecrease,toggleButtonMultiple,toggleButtonDivide;
    TextView textViewQuestion;
    EditText editTextPredict;
    ArrayList<String> operationTypes ;
    Button buttonGetQuestion, buttonMakePredict;
    Random random;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        operationTypes = new ArrayList<>();
        toggleButtonAdd = findViewById(R.id.toggleButtonAdd);
        toggleButtonDecrease = findViewById(R.id.toggleButtonDecrease);
        toggleButtonMultiple = findViewById(R.id.toggleButtonMultiple);
        toggleButtonDivide = findViewById(R.id.toggleButtonDivide);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        editTextPredict = findViewById(R.id.editTextPrediction);
        toggleButtonAdd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    operationTypes.add("+");
                else
                    operationTypes.remove("+");

            }
        });
        toggleButtonDecrease.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    operationTypes.add("-");
                else
                    operationTypes.remove("-");

            }
        });
        toggleButtonMultiple.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    operationTypes.add("*");
                else
                    operationTypes.remove("*");

            }
        });
        toggleButtonDivide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    operationTypes.add("/");
                else
                    operationTypes.remove("/");

            }
        });
    }
    public void buttonClick(View view){

        switch (view.getId()){
            case R.id.buttonGetQuestion:
                break;

            case R.id.buttonMakePredict:
                break;
        }
    }

    public void setQuestion(){
        int randomIndex = generateRandomNum();
        
    }
    public int generateRandomNum(){
        random = new Random();
        return random.nextInt(10-1)+1;
    }


    public void goToSecondActivity(View view) {
        Intent intent = new Intent(MainActivity.this,MainActivity2.class);
        intent.putExtra("message","mert");
        startActivity(intent);
    }
}