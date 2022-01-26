package com.mertmsrl.database;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        // Data Preparation

        ArrayList<String> heroes = new ArrayList<>();

        heroes.add("Batman");
        heroes.add("Superman");
        heroes.add("Ironman");
        heroes.add("Aquaman");
        heroes.add("Spiderman");
        heroes.add("Batman");
        heroes.add("Superman");
        heroes.add("Ironman");
        heroes.add("Aquaman");
        heroes.add("Spiderman");
        heroes.add("Batman");
        heroes.add("Superman");
        heroes.add("Ironman");
        heroes.add("Aquaman");
        heroes.add("Spiderman");

        // Not Good Application

        Bitmap bitmapBatman = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.batman);
        Bitmap bitmapSuperman = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.superman);
        Bitmap bitmapIronman = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ironman);
        Bitmap bitmapAquaman = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.aquaman);
        Bitmap bitmapSpiderman = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.spiderman);



        ArrayList<Bitmap> heroesImages = new ArrayList<>();
        heroesImages.add(bitmapBatman);
        heroesImages.add(bitmapSuperman);
        heroesImages.add(bitmapIronman);
        heroesImages.add(bitmapAquaman);
        heroesImages.add(bitmapSpiderman);
        heroesImages.add(bitmapBatman);
        heroesImages.add(bitmapSuperman);
        heroesImages.add(bitmapIronman);
        heroesImages.add(bitmapAquaman);
        heroesImages.add(bitmapSpiderman);
        heroesImages.add(bitmapBatman);
        heroesImages.add(bitmapSuperman);
        heroesImages.add(bitmapIronman);
        heroesImages.add(bitmapAquaman);
        heroesImages.add(bitmapSpiderman);




        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(heroes,heroesImages,context);
        recyclerView.setAdapter(recyclerAdapter);





    }


}