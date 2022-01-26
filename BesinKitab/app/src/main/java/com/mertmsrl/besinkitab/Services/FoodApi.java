package com.mertmsrl.besinkitab.Services;

import com.mertmsrl.besinkitab.Models.Food;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface FoodApi {


    // private String BASE_URL = https://raw.githubusercontent.com/
    // atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
    Single<List<Food>> getFood();
}
