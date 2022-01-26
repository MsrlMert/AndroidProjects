package com.mertmsrl.cardgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.Random;


public class SecondWindow extends AppCompatActivity implements View.OnClickListener {

    static Random random = new Random();
    GridLayout gridLayout;
    static Cards[] cards = new Cards[16];
    static int beforeCardFrontId = 0;
    static int count = 0;
    static Cards[] cardList = new Cards[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_window);
        Intent i = getIntent();
        String s = i.getStringExtra("message");
        TextView tv = findViewById(R.id.textView2);
        tv.setText(s);

        gridLayout = findViewById(R.id.gridLayout);
        int count = 0;
        for (int j = 0; j < 16; j++) {
            cards[j] = new Cards(this,j);
            cards[j].setOnClickListener(this::onClick);
        }
        for (int j = 0; j < 16; j++) {
            int randomIndex = random.nextInt(15-0+1);
            Cards tempCard = cards[randomIndex];
            cards[randomIndex] = cards[j];
            cards[j] = tempCard;
        }
        for (int j = 0; j <16; j++) {
            gridLayout.addView(cards[j]);
        }
    }


    @Override
    public void onClick(View v) {
        Cards selectedCard = (Cards) v;
        selectedCard.changeCardFace();

        if (count == 0){
            beforeCardFrontId = selectedCard.frontImageId;
            System.out.println("sıfır "+beforeCardFrontId);
            cardList[0] = selectedCard;
            count ++;
        }
        else{
            System.out.println(beforeCardFrontId+" "+selectedCard.frontImageId);
            cardList[1] = selectedCard;
            if (beforeCardFrontId == selectedCard.frontImageId){
                System.out.println("Match Two Cards");
                count = 0;
            }

            else{
                System.out.println("Şimdi Çalışıyor");
                System.out.println("ilk kart"+cardList[0].frontImageId);
                try {
                    System.out.println("ilk kart"+cardList[0].frontImageId+" ikinci kart " +cardList[1].frontImageId);
                    cardList[0].changeCardFace();
                    cardList[1].changeCardFace();

                }
                catch (Exception e){
                    System.out.println("Kart bulunamadı");

                }


                count = 0;
            }


        }

    }
}