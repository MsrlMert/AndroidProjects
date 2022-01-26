package com.mertmsrl.intentactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity6shared extends AppCompatActivity {

    Button btnSave, btnGetValue, btnClear, btnRemove;
    EditText editTextName;
    TextView textViewName;
    CheckBox checkboxRequest;
    ShredPreferences shredPreferences;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity6_shared);

        init();


    }

    public void init(){
        btnSave = findViewById(R.id.btnSave);
        btnGetValue = findViewById(R.id.btnGetValue);
        btnRemove = findViewById(R.id.btnRemove);
        btnClear = findViewById(R.id.btnClear);

        editTextName = findViewById(R.id.editTextName);

        textViewName = findViewById(R.id.textViewName);

        checkboxRequest = findViewById(R.id.checkboxRequest);

        shredPreferences = new ShredPreferences();
    }

    public void btnListener(){
        btnSaveListener();
        btnGetValueListener();
        btnClearListener();
        btnRemoveListener();
    }


    public void btnSaveListener(){
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRequestCheckBoxChecked()){
                    shredPreferences.save(context,"name",editTextName.getText().toString());
                    Toast.makeText(context, editTextName.getText().toString()+" is saved", Toast.LENGTH_LONG).show();
                    checkboxRequest.setChecked(false);
                    editTextName.setText("");
                }
            }
        });
    }

    public void btnGetValueListener(){
        btnGetValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRequestCheckBoxChecked()) {
                    textViewName.setVisibility(View.VISIBLE);
                    String name = shredPreferences.getValue(context, "name");
                    textViewName.setText(name);
                }
            }
        });
    }

    public void btnClearListener(){
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRequestCheckBoxChecked()) {
                    shredPreferences.clear(context, "name");
                }
            }
        });
    }

    public void btnRemoveListener(){
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRequestCheckBoxChecked()) {
                    shredPreferences.remove(context, "name");
                }
            }
        });
    }



    public boolean isRequestCheckBoxChecked(){
        return checkboxRequest.isChecked();
    }
}