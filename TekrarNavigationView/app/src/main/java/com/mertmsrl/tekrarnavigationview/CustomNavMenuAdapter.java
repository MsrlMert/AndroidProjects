package com.mertmsrl.tekrarnavigationview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomNavMenuAdapter extends BaseAdapter {
    ArrayList<CustomNavMenuRowItem> arrayList;
    LayoutInflater inflater;
    Context context;

    public CustomNavMenuAdapter(ArrayList<CustomNavMenuRowItem> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_nav_menu_row, null, false);

        ImageView imageView = view.findViewById(R.id.custom_nav_menu_row_image_view);
        TextView textView = view.findViewById(R.id.custom_nav_menu_row_text_view);

        imageView.setImageResource((int) arrayList.get(position).getIconId());
        textView.setText(arrayList.get(position).getIconName());



        return view;
    }
}
