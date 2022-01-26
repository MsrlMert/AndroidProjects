package com.mertmsrl.besinkitab.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;

import com.mertmsrl.besinkitab.Fragments.FoodListFragment;
import com.mertmsrl.besinkitab.R;

public class MainActivity extends AppCompatActivity {

    Context context = this;
    FragmentManager manager;
    FoodListFragment foodListFragment;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getSupportFragmentManager();
        foodListFragment = new FoodListFragment(context);
        fragmentTransaction = manager.beginTransaction();

        fragmentTransaction.replace(R.id.activity_main_frame_layout, foodListFragment);
        fragmentTransaction.commit();
    }


}