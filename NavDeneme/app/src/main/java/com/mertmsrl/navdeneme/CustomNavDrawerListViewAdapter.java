package com.mertmsrl.navdeneme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CustomNavDrawerListViewAdapter extends BaseAdapter {

    ArrayList<NavigationItemClass> navigationItemClassArrayList = new ArrayList<>();
    LayoutInflater inflater;
    Context context;

    public CustomNavDrawerListViewAdapter(ArrayList<NavigationItemClass> navigationItemClassArrayList, Context context) {
        this.navigationItemClassArrayList = navigationItemClassArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return navigationItemClassArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return navigationItemClassArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_nav_drawer_list_view,null,false);

        ImageView imageViewIcon = view.findViewById(R.id.custom_nav_drawer_list_view_image_view);
        TextView textView = view.findViewById(R.id.custom_nav_drawer_list_view_text_view);

        imageViewIcon.setImageResource((int) navigationItemClassArrayList.get(position).getIconId());
        textView.setText(navigationItemClassArrayList.get(position).getName());



        return view;
    }
}
