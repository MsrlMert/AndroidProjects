package com.mertmsrl.besinkitab.Services;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mertmsrl.besinkitab.Models.Food;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {Food.class}, version = 1)
public abstract class FoodDatabase extends RoomDatabase {

    private static FoodDatabase INSTANCE;

    public abstract FoodDao foodDao();

    public static FoodDatabase getDbInstance(Context context){

        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, FoodDatabase.class,"foodDb").build();


        }
        return INSTANCE;
    }



}
