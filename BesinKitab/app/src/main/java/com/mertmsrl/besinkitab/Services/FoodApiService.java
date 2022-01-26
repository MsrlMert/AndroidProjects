package com.mertmsrl.besinkitab.Services;

import com.mertmsrl.besinkitab.Models.Food;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodApiService {

    // private String BASE_URL = https://raw.githubusercontent.com/
    // atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    private String BASE_URL = "https://raw.githubusercontent.com/";
    FoodApi api = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(FoodApi.class);

    public Single<List<Food>> getData(){
        return api.getFood();
    }

}
