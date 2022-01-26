package com.mertmsrl.besinkitab.Services;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.mertmsrl.besinkitab.Models.Food;

import java.util.List;

@Dao
public interface FoodDao {

    // Data Access Object

    // returns id of items
    @Insert
    long insertAll(Food food);

    @Query(value = "SELECT * FROM Food")
    List<Food> getAllFood();


    @Query(value = "SELECT * FROM Food WHERE uuid = :foodId")
    Food getSingleFood(double foodId);

    @Query(value = "DELETE FROM Food")
    void deleteAllFood();
}
