package com.mertmsrl.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText editTextName, editTextSurname, editTextAge, editTextPassword;
    CheckBox checkBoxProgramming, checkBoxSport, checkBoxMusic;
    RadioButton radioButtonMan, radioButtonWoman;
    Button btnFinishRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Edit text Bağlama
        editTextName = findViewById(R.id.edit_text_name);
        editTextSurname = findViewById(R.id.edit_text_surname);
        editTextAge = findViewById(R.id.edit_text_age);
        editTextPassword = findViewById(R.id.edit_text_password);

        // CheckBox
        checkBoxProgramming = findViewById(R.id.checkbox_hobby_programming);
        checkBoxSport = findViewById(R.id.checkbox_hobby_sport);
        checkBoxMusic = findViewById(R.id.checkbox_hobby_music);

        // Radio button
        radioButtonMan = findViewById(R.id.radiobutton_gender_man);
        radioButtonWoman = findViewById(R.id.radiobutton_gender_woman);

        // button
        btnFinishRegister = findViewById(R.id.button_finish_register);

    }

    public void finishRegister(View view) {
        String strEditTextName = editTextName.getText().toString();
        String strEditTextSurname = editTextSurname.getText().toString();
        String strEditTextAge = editTextAge.getText().toString();
        String strEditTextPassword = editTextPassword.getText().toString();

        if (!TextUtils.isEmpty(strEditTextName) && !TextUtils.isEmpty(strEditTextSurname) && !TextUtils.isEmpty(strEditTextAge) && !TextUtils.isEmpty(strEditTextPassword)) {
            String outPut = strEditTextName+"\n"+strEditTextSurname+"\n"+strEditTextAge+"\n"+strEditTextPassword+"\n";
            if (checkBoxProgramming.isChecked()){
                outPut += "Hobby Programming ";
            }
            if (checkBoxSport.isChecked()){
                outPut += " Hobby Sport ";
            }
            if (checkBoxMusic.isChecked()){
                outPut += " Hobby Music ";
            }
            if (radioButtonMan.isChecked()){
                outPut += "\nGender is Man";
            }
            else if (radioButtonWoman.isChecked()){
                outPut += "\nGender is Woman";
            }
            System.out.println(outPut);
        }

    }



}