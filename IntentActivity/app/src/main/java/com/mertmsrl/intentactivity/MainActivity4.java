package com.mertmsrl.intentactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity4 extends AppCompatActivity {

    EditText editTextWhatAreYouLooking;
    ListView listViewProducts;
    List<String> products ;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        init();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context,R.layout.support_simple_spinner_dropdown_item,products);
        listViewProducts.setAdapter(arrayAdapter);
        editTextListener();
    }

    public void init(){
        editTextWhatAreYouLooking = findViewById(R.id.editTextWhatAreYouLooking);

        listViewProducts = findViewById(R.id.listViewProducts);

        products = new ArrayList<>();

        products.add("Apple");
        products.add("Samsung");
        products.add("Huawei");
        products.add("LG");
        products.add("Asus");
        products.add("Xiaomi");
        products.add("Hp");
        products.add("General Mobile");
    }


    public void editTextListener(){
        editTextWhatAreYouLooking.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                List<String> tempList = new ArrayList<>();
                String pressedString = s.toString().toUpperCase();
                for (String product : products){
                    if (product.toUpperCase().contains(pressedString)){
                        tempList.add(product);
                    }
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context,R.layout.support_simple_spinner_dropdown_item,tempList);
                listViewProducts.setAdapter(arrayAdapter);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}