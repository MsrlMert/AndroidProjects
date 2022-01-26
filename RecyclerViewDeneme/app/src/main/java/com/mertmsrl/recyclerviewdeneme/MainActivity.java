package com.mertmsrl.recyclerviewdeneme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText editText;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        editText = findViewById(R.id.editTextMessage);
        btn = findViewById(R.id.btnSend);
        Products product = new Products("Fe", "samsung",1500,2016);
        Products product1 = new Products("s5", "apple",1600,2017);
        Products product2 = new Products("s98", "samsung",6900,2016);
        Products product3 = new Products("Fe", "apple",4500,2011);

        ArrayList<Products> products = new ArrayList<>();
        products.add(product);
        products.add(product1);
        products.add(product2);
        products.add(product3);

        CustomRecyclerViewAdapter adapter = new CustomRecyclerViewAdapter(products,this);
        recyclerView.setAdapter(adapter);



    }
}