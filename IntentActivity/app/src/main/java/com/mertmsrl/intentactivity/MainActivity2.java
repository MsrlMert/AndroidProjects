package com.mertmsrl.intentactivity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    ListView listViewCities;
    ArrayList<Cities> cities;
    LayoutInflater inflater;
    Context context = this;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        init();
        setCitiesData();

        CustomAdapter customAdapter = new CustomAdapter(cities, inflater, context);

        listViewCities.setAdapter(customAdapter);

        listViewListener();

    }

    public void init() {
        listViewCities = findViewById(R.id.listViewCities);
        cities = new ArrayList<>();

    }

    public void setCitiesData(){
        cities.add(new Cities("Istanbul",R.drawable.istanbul,20000));
        cities.add(new Cities("Ankara",R.drawable.ankara,8000));
        cities.add(new Cities("Izmır",R.drawable.izmir,15000));
        cities.add(new Cities("Istanbul",R.drawable.istanbul,20000));
        cities.add(new Cities("Ankara",R.drawable.ankara,8000));
        cities.add(new Cities("Izmır",R.drawable.izmir,15000));
        cities.add(new Cities("Istanbul",R.drawable.istanbul,20000));
        cities.add(new Cities("Ankara",R.drawable.ankara,8000));
        cities.add(new Cities("Izmır",R.drawable.izmir,15000));
        cities.add(new Cities("Istanbul",R.drawable.istanbul,20000));
        cities.add(new Cities("Ankara",R.drawable.ankara,8000));
        cities.add(new Cities("Izmır",R.drawable.izmir,15000));
        cities.add(new Cities("Istanbul",R.drawable.istanbul,20000));
        cities.add(new Cities("Ankara",R.drawable.ankara,8000));
        cities.add(new Cities("Izmır",R.drawable.izmir,15000));
        cities.add(new Cities("Istanbul",R.drawable.istanbul,20000));
        cities.add(new Cities("Ankara",R.drawable.ankara,8000));
        cities.add(new Cities("Izmır",R.drawable.izmir,15000));
        cities.add(new Cities("Istanbul",R.drawable.istanbul,20000));
        cities.add(new Cities("Ankara",R.drawable.ankara,8000));
        cities.add(new Cities("Izmır",R.drawable.izmir,15000));
        cities.add(new Cities("Istanbul",R.drawable.istanbul,20000));
        cities.add(new Cities("Ankara",R.drawable.ankara,8000));
        cities.add(new Cities("Izmır",R.drawable.izmir,15000));
    }
    public void listViewListener(){
        listViewCities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(context,CitiesPicturesActivity.class);

                intent.putExtra("cityPictureId",cities.get(position).getImageId());
                startActivity(intent);
            }
        });
    }

}