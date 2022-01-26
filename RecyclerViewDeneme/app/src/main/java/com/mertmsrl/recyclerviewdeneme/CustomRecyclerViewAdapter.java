package com.mertmsrl.recyclerviewdeneme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.ViewHolder> {

    ArrayList<Products> products = new ArrayList<>();
    Context context;
    LayoutInflater inflater;

    public CustomRecyclerViewAdapter(ArrayList<Products> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_recycler_view, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textViewProductName.setText(products.get(position).getProductName());
        holder.textViewProductBrand.setText(products.get(position).getProductBrand());
        holder.textViewProductPrice.setText(String.valueOf(products.get(position).getProductPrice()));
        holder.textViewProductYear.setText(String.valueOf(products.get(position).getProductYear()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;
        TextView textViewProductName, textViewProductBrand, textViewProductPrice, textViewProductYear;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linearLayout);

            textViewProductName = itemView.findViewById(R.id.textViewProductName);
            textViewProductBrand = itemView.findViewById(R.id.textViewProductBrand);
            textViewProductPrice = itemView.findViewById(R.id.textViewProductPrice);
            textViewProductYear = itemView.findViewById(R.id.textViewProductYear);

        }
    }
}
