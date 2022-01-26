package com.mertmsrl.androidsqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomListViewAdapter extends BaseAdapter {
    List<Books> booksList ;
    Context context;
    LayoutInflater inflater;
    static int count = 0;

    public CustomListViewAdapter(List<Books> booksList, Context context) {
        this.booksList = booksList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return booksList.size();
    }

    @Override
    public Object getItem(int position) {
        return booksList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_books_listview, null);

        TextView textViewListRowId = view.findViewById(R.id.textViewListRowId);
        TextView textViewBookId = view.findViewById(R.id.textViewCustomBookId);
        TextView textViewBookName = view.findViewById(R.id.textViewCustomBookName);
        TextView textViewBookAuthor = view.findViewById(R.id.textViewCustomBookAuthor);


        textViewListRowId.setText(String.valueOf(position+1));
        textViewBookId.setText(String.valueOf(booksList.get(position).getBookId()));
        textViewBookName.setText(booksList.get(position).getBookName());
        textViewBookAuthor.setText(booksList.get(position).getBookAuthor());
        count++;
        return view;
    }
}
