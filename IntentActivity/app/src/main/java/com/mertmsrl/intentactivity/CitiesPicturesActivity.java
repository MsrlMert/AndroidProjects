package com.mertmsrl.intentactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CitiesPicturesActivity extends AppCompatActivity{

    ImageView imageView;
    Button btnReturnMainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities_pictures);
        init();
        setImagePicture();
        btnListener();
    }

    public void init(){
        imageView = findViewById(R.id.imageViewCitiesPictures);
        btnReturnMainActivity = findViewById(R.id.btnReturnMainActivity);

    }

    public void setImagePicture(){
        Intent intent = getIntent();
        int pictureId = intent.getIntExtra("cityPictureId",-1);

        imageView.setImageResource(pictureId);

    }
    public void btnListener(){
        btnReturnMainActivityListener();
    }




    public void btnReturnMainActivityListener(){
        btnReturnMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CitiesPicturesActivity.this,MainActivity.class);

                startActivity(intent);
            }
        });
    }
}