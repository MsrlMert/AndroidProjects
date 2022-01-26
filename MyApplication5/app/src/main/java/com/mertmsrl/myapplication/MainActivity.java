package com.mertmsrl.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter arrayAdapter;
    Spinner spinner;
    String[] cities = {"Esenler", "Esenyurt", "Kadıköy", "Beylikdüzü", "Güngören"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        define();
        addAdapter();
        addAdapterToSpinner();

    }

    public void define() {
        spinner = findViewById(R.id.spinner);
    }

    public void addAdapter() {
        arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, cities);
    }

    public void addAdapterToSpinner() {
        spinner.setAdapter(arrayAdapter);
    }

    public void giveInfo() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}