package com.mertmsrl.besinkitab.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mertmsrl.besinkitab.Adapters.RecyclerFoodListAdapter;
import com.mertmsrl.besinkitab.Models.Food;
import com.mertmsrl.besinkitab.R;
import com.mertmsrl.besinkitab.ViewModel.FoodListViewModel;

import java.util.ArrayList;


public class FoodListFragment extends Fragment{

    FoodListViewModel foodListViewModel;
    Context context;
    LinearLayoutManager layoutManager;
    SwipeRefreshLayout swipeRefreshLayout;

    RecyclerFoodListAdapter adapter;

    RecyclerView recyclerView;
    EditText editTextNewFood;
    Button btnSubmit;


    public FoodListFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food_list, container, false);

        recyclerView = view.findViewById(R.id.fragment_food_recycler_view);
        layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        editTextNewFood = view.findViewById(R.id.fragment_food_edit_text);
        btnSubmit = view.findViewById(R.id.fragment_food_btn_submit);
        swipeRefreshLayout = view.findViewById(R.id.fragment_food_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editTextNewFood.getText().toString())){
                    foodListViewModel.addNewFood(editTextNewFood.getText().toString());
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        foodListViewModel = new ViewModelProvider(this).get(FoodListViewModel.class);

        observeLiveData();

    }

    public void observeLiveData(){
        foodListViewModel.setFoodListData(context);
        foodListViewModel.mutableLiveData.observe(getViewLifecycleOwner(), foods -> {
            adapter = new RecyclerFoodListAdapter((ArrayList<Food>) foods, context);
            recyclerView.setAdapter(adapter);
        });
    }

}