package com.mertmsrl.besinkitab.ViewModel;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mertmsrl.besinkitab.Models.Food;
import com.mertmsrl.besinkitab.Services.FoodApiService;
import com.mertmsrl.besinkitab.Services.FoodDao;
import com.mertmsrl.besinkitab.Services.FoodDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public class FoodListViewModel extends ViewModel {
    public MutableLiveData<List<Food>> mutableLiveData = new MutableLiveData<>();;
    List<Food> foodsList = new ArrayList<>();
    List<Food> addedFoodsList = new ArrayList<>();
    static  List<Food> staticFoodList = new ArrayList<>();

    FoodApiService apiService = new FoodApiService();
    CompositeDisposable disposable = new CompositeDisposable();





    public FoodListViewModel() {

    }

    public void setFoodListData(Context context){
        getDataFromInternet(context);
    }
    public void addNewFood(String foodName){
        Food food = new Food(foodName,"150","120","180","450","default");

        addedFoodsList.add(food);

    }

    public void getDataFromInternet(Context context){

        disposable.add(
                apiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Food>>() {
                    @Override
                    public void onSuccess(@NonNull List<Food> foods) {
                        // Success
                        staticFoodList = foods;
//                        new StoreInSqlite(context).doInBackground(foods);

                        mutableLiveData.setValue(foods);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        // Error
                        e.printStackTrace();
                    }
                })
        );
    }

}

class StoreInSqlite extends AsyncTask<List<Food>, String, List<Long>>{

    Context context;
    public StoreInSqlite(Context context) {
        this.context = context;
    }

    @Override
    protected List<Long> doInBackground(List<Food>... lists) {
        FoodDatabase db = FoodDatabase.getDbInstance(context);
        FoodDao foodDao = db.foodDao();
        List<Long> rowIdList = new ArrayList<>();

        for (Food food : lists[0]){
            long id = foodDao.insertAll(food);
            rowIdList.add(id);
        }
        return rowIdList;
    }



}

