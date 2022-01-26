package com.mertmsrl.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener ,TextView.OnEditorActionListener{

//    TextView bilmeceSonuc;
//    Button buttonAnswer;
//    static int count = 0;
    Characters character;
    Button fight, eat, sleep;
    TextView characterInfo;
    TextView game;
    EditText characterName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        bilmeceSonuc = findViewById(R.id.textViewAnswer);
//        buttonAnswer =findViewById(R.id.ButtonShowAnswer);
//        buttonAnswer.setOnClickListener(this::onClick);
        character =  new Characters();
        character.weight = 10;
        character.fightForce = 100;
        game = findViewById(R.id.textViewPrint);
        characterName = findViewById(R.id.editTextCharacterName);
        fight = findViewById(R.id.buttonFight);
        eat = findViewById(R.id.buttonEat);
        sleep = findViewById(R.id.buttonSleep);
        characterInfo = findViewById(R.id.textViewCharacterInfo);
        fight.setOnClickListener(this::onClick);
        eat.setOnClickListener(this::onClick);
        sleep.setOnClickListener(this::onClick);
        game.setText("Oyun Başlıyor. Eylem Seçiniz");
        characterName.setOnEditorActionListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == eat.getId()){
            character.eatFood();
        }
        if (v.getId() == sleep.getId()){
            character.sleep();
        }
        if (v.getId() == fight.getId()){
            character.fight();
        }
        characterInfo.setText(character.toString());
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        character.name = characterName.getText().toString();
        game.setText(characterName.getText());
        characterInfo.setText(character.toString());
        characterInfo.setVisibility(View.VISIBLE);
        return true;
    }
}