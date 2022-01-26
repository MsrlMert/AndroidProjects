package com.mertmsrl.againdatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import io.realm.Realm;

public class Adapter extends BaseAdapter {

    Realm realm;
    List<Person> list;
    Context context;

    public Adapter(List<Person> list, Context context, Realm realm) {
        this.list = list;
        this.context = context;
        this.realm = realm;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        list = realm.where(Person.class).findAll();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.bottomlayout, parent, false);
        TextView personUserName = convertView.findViewById(R.id.textViewBottomUserName);
        TextView personUserPassword = convertView.findViewById(R.id.textViewBottomUserPassword);
        TextView personName = convertView.findViewById(R.id.textViewBottomPersonName);
        TextView personGender = convertView.findViewById(R.id.textViewBottomUserGender);

        personUserName.setText(list.get(position).getPersonUserName());
        personUserPassword.setText(list.get(position).getPersonPassword());
        personGender.setText(list.get(position).getPersonGender());
        personName.setText(list.get(position).getPersonName());


        return convertView;
    }
}
