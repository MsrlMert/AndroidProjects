package com.mertmsrl.cardgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button button;
    EditText editTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.ButtonGame);
        button.setOnClickListener(this::onClick);
        editTextName = findViewById(R.id.editTextPersonName);


    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(MainActivity.this,SecondWindow.class);
        i.putExtra("message",editTextName.getText().toString());
        startActivity(i);
    }
}




















