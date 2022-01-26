package com.mertmsrl.cardgame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.appcompat.widget.AppCompatDrawableManager;

public class Cards extends androidx.appcompat.widget.AppCompatButton {
    boolean isCardSelected = false;
    int frontImageId;
    int backgroundId;
    Drawable front;
    Drawable back;
    @SuppressLint("RestrictedApi")
    public Cards(Context context, int id) {
        super(context);
        backgroundId = R.drawable.circle;

        if (id % 8 == 0){
            frontImageId = R.drawable.arrow;
        }
        if (id % 8 == 1){
            frontImageId = R.drawable.love;
        }
        if (id % 8 == 2){
            frontImageId =R.drawable.parabola;
        }
        if (id % 8 == 3){
            frontImageId = R.drawable.rectangle;
        }
        if (id % 8 == 4){
            frontImageId = R.drawable.star;
        }
        if (id % 8 == 5){
            frontImageId = R.drawable.triangle;
        }
        if (id % 8 == 6){
            frontImageId = R.drawable.up;
        }
        if (id % 8 == 7){
            frontImageId = R.drawable.comment;
        }

        front = AppCompatDrawableManager.get().getDrawable(context, frontImageId);
        back = AppCompatDrawableManager.get().getDrawable(context, backgroundId);
        setBackground(back);


    }

    public void changeCardFace(){
//        if (!isCardSelected){
//            setBackground(front);
//            isCardSelected = true;
//        }
//        else{
//            setBackground(back);
//            isCardSelected = false;
//        }
        if (isCardSelected == false){
            setBackground(front);
            isCardSelected =true;
        }
        else{
            setBackground(back);
            isCardSelected = false;
        }
    }

}
