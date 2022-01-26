package com.mertmsrl.intentactivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CustomAdapter extends BaseAdapter {
    ArrayList<Cities> citiesArrayList;
    LayoutInflater inflater;
    Context context;


    public CustomAdapter(ArrayList<Cities> citiesArrayList, LayoutInflater inflater, Context context) {
        this.citiesArrayList = citiesArrayList;
        this.inflater = inflater;
        this.context = context;
    }

    @Override
    public int getCount() {
        return citiesArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return citiesArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_listview,null);

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textView = view.findViewById(R.id.textView);


//        imageView.setImageResource(citiesArrayList.get(position).getImageId());
        imageView.setImageResource(R.mipmap.ic_launcher);

        textView.setText(citiesArrayList.get(position).getCityName());


        return view;
    }
}
