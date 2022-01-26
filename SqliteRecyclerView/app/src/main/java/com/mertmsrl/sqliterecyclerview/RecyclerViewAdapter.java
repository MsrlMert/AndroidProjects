package com.mertmsrl.sqliterecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    ArrayList<Books> booksArrayList;
    Context context;
    LayoutInflater inflater;

    public RecyclerViewAdapter(ArrayList<Books> booksArrayList, Context context) {
        this.booksArrayList = booksArrayList;
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
        String bookName = booksArrayList.get(position).getBookName();
        String bookAuthor = booksArrayList.get(position).getBookAuthor();

        holder.textViewBookName.setText(bookName);
        holder.textViewBookAuthor.setText(bookAuthor);

    }

    @Override
    public int getItemCount() {
        return booksArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewBookName, textViewBookAuthor;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewBookName = itemView.findViewById(R.id.recycler_view_row_book_name);
            textViewBookAuthor = itemView.findViewById(R.id.recycler_view_row_book_author);

        }
    }
}
