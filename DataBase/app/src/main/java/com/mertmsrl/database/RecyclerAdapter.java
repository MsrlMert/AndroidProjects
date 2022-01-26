package com.mertmsrl.database;/*


 */

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.HeroesVH>{
    ArrayList<String> heroes ;
    ArrayList<Bitmap> heroesImages;
    LayoutInflater layoutInflater;
    Context context;


    public RecyclerAdapter(ArrayList<String> heroes, ArrayList<Bitmap> heroesImages, Context context) {
        this.heroes = heroes;
        this.heroesImages = heroesImages;
        this.context = context;
    }

    @NonNull
    @Override
    public HeroesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recycler_row, parent, false);
        return new HeroesVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroesVH holder, int position) {
        holder.textViewHeroes.setText(heroes.get(position));
        holder.imageView.setImageBitmap(heroesImages.get(position));
    }

    @Override
    public int getItemCount() {
        return heroes.size();
    }

    public class HeroesVH extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textViewHeroes;
        public HeroesVH(@NonNull View itemView) {
            super(itemView);
            textViewHeroes = itemView.findViewById(R.id.recyclerViewTextView);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}



