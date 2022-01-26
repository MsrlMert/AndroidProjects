package com.mertmsrl.teknodaterest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    List<ListViewDataCity> listViewDataCityList;
    Context context;


    public ListViewAdapter(List<ListViewDataCity> weatherDataClassList, Context context) {
        this.listViewDataCityList = weatherDataClassList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listViewDataCityList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewDataCityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.listviewlayout,parent,false);

        TextView textViewWeatherStateName = convertView.findViewById(R.id.textViewCityWeatherStateName);
        TextView textViewMaxTemp = convertView.findViewById(R.id.textViewCityMaxTemp);
        TextView textViewMinTemp = convertView.findViewById(R.id.textViewCityMinTemp);
        TextView textViewApplicableDate = convertView.findViewById(R.id.textViewCityApplicableDate);



        textViewWeatherStateName.setText("Weather State Name :"+listViewDataCityList.get(position).getWeatherStateName());
        textViewMaxTemp.setText("Max Temp : "+listViewDataCityList.get(position).getMaxTemp());
        textViewMinTemp.setText("Min Temp : "+listViewDataCityList.get(position).getMinTemp());
        textViewApplicableDate.setText("Applicable Date : "+listViewDataCityList.get(position).getApplicableDate());

        return convertView;
    }
}
