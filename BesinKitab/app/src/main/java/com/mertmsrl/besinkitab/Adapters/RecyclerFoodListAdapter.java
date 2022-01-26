package com.mertmsrl.besinkitab.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mertmsrl.besinkitab.Activities.MainActivity;
import com.mertmsrl.besinkitab.Fragments.FoodListFragment;
import com.mertmsrl.besinkitab.Models.Food;
import com.mertmsrl.besinkitab.R;

import java.util.ArrayList;

public class RecyclerFoodListAdapter extends RecyclerView.Adapter<RecyclerFoodListAdapter.ViewHolder> {

    ArrayList<Food> foodArrayList;
    Context context;
    LayoutInflater inflater;

    public RecyclerFoodListAdapter(ArrayList<Food> foodArrayList, Context context) {
        this.foodArrayList = foodArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(R.mipmap.ic_launcher_round);
        holder.foodName.setText(foodArrayList.get(position).getFoodName());
        holder.foodCalorie.setText(foodArrayList.get(position).getFoodCalorie());

        Glide.with(context)
                .load(foodArrayList.get(position).getFoodImg())
                .placeholder(R.mipmap.ic_launcher_round)
                .into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return foodArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView foodName, foodCalorie;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.recycler_view_row_food_img);
            foodName = itemView.findViewById(R.id.recycler_view_row_food_name);
            foodCalorie = itemView.findViewById(R.id.recycler_view_row_food_calorie);

        }
    }
}
