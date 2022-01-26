package com.mertmsrl.myapplast;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    Switch switchRadio;
    MediaPlayer mediaPlayerMusic;
    ToggleButton toggleButtonMusic;
    ImageView imageViewMusicPic;
    SeekBar seekBarMusicProgress;
    TextView textViewVolume;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switchRadio= findViewById(R.id.switchMusic);
        mediaPlayerMusic = MediaPlayer.create(getApplicationContext(),R.raw.song);
        imageViewMusicPic = findViewById(R.id.imageViewMusicPic);
        toggleButtonMusic = findViewById(R.id.toggleBtnMusic);
        seekBarMusicProgress = findViewById(R.id.seekBarMusic);
        textViewVolume = findViewById(R.id.textViewVolume);
        toggleButtonMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    imageViewMusicPic.setVisibility(View.VISIBLE);
                }
                else{
                    imageViewMusicPic.setVisibility(View.INVISIBLE);
                }
            }
        });
        seekBarMusicProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewVolume.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    public void clickSwitch(View view) {
        if (!switchRadio.isChecked()){
            mediaPlayerMusic.start();
        }
        else{
            mediaPlayerMusic.pause();
        }
    }

    public void clickSeekBar(View view) {

    }
}