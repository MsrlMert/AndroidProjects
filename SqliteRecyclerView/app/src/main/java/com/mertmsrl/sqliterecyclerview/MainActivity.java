package com.mertmsrl.sqliterecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    EditText editTextBookName, editTextBookAuthor;
    RecyclerViewAdapter adapter;
    DataSource dataSource;
    Context context = this;
    ArrayList<Books> booksArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setRecyclerViewData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                booksArrayList = dataSource.getAllData();
                adapter = new RecyclerViewAdapter(booksArrayList, context);
                recyclerView.setAdapter(adapter);
                Toast.makeText(context, "Merhaba", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);

            }
        });
    }

    public void init() {
        recyclerView = findViewById(R.id.main_activity_recycler_view);

        editTextBookName = findViewById(R.id.main_activity_edit_text_book_name);
        editTextBookAuthor = findViewById(R.id.main_activity_edit_text_book_author);

        dataSource = new DataSource(context);

        swipeRefreshLayout  = findViewById(R.id.refresh_layout);

    }

    public void btnAddListener(View view) {
        String bookName = editTextBookName.getText().toString();
        String bookAuthor = editTextBookAuthor.getText().toString();

        dataSource.addBookToDb(bookName, bookAuthor);
        Toast.makeText(context, String.valueOf(booksArrayList.size()), Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();

        adapter.notifyDataSetChanged();

    }

    public void setRecyclerViewData() {
        booksArrayList = dataSource.getAllData();
        adapter = new RecyclerViewAdapter(booksArrayList, context);
        Toast.makeText(context, String.valueOf(booksArrayList.size()), Toast.LENGTH_SHORT).show();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }



}