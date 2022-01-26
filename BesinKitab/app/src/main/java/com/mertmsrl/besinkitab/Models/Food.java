package com.mertmsrl.besinkitab.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Food {
    @ColumnInfo(name = "isim")
    @SerializedName("isim")
    private String foodName;

    @ColumnInfo(name = "kalori")
    @SerializedName("kalori")
    private String foodCalorie;

    @ColumnInfo(name = "karbonhidrat")
    @SerializedName("karbonhidrat")
    private String foodCarbohydrate;

    @ColumnInfo(name = "protein")
    @SerializedName("protein")
    private String foodProtein;

    @ColumnInfo(name = "yag")
    @SerializedName("yag")
    private String foodOil;

    @ColumnInfo(name = "gorsel")
    @SerializedName("gorsel")
    private String foodImg;

    @PrimaryKey(autoGenerate = true)
    public long uuid = 0;


    public Food(String foodName, String foodCalorie, String foodCarbohydrate, String foodProtein, String foodOil, String foodImg) {
        this.foodName = foodName;
        this.foodCalorie = foodCalorie;
        this.foodCarbohydrate = foodCarbohydrate;
        this.foodProtein = foodProtein;
        this.foodOil = foodOil;
        this.foodImg = foodImg;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodCalorie() {
        return foodCalorie;
    }

    public void setFoodCalorie(String foodCalorie) {
        this.foodCalorie = foodCalorie;
    }

    public String getFoodCarbohydrate() {
        return foodCarbohydrate;
    }

    public void setFoodCarbohydrate(String foodCarbohydrate) {
        this.foodCarbohydrate = foodCarbohydrate;
    }

    public String getFoodProtein() {
        return foodProtein;
    }

    public void setFoodProtein(String foodProtein) {
        this.foodProtein = foodProtein;
    }

    public String getFoodOil() {
        return foodOil;
    }

    public void setFoodOil(String foodOil) {
        this.foodOil = foodOil;
    }

    public String getFoodImg() {
        return foodImg;
    }

    public void setFoodImg(String foodImg) {
        this.foodImg = foodImg;
    }
}
