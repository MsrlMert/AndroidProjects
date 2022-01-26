package com.mertmsrl.androidsqlite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BookInfo extends AppCompatActivity {

    TextView textViewBookActivityBookName, textViewBookActivityBookAuthor;
    EditText editTextBookActivityNewBookName, editTextBookActivityNewBookAuthor;
    Button btnBookActivityUpdate, btnBookActivityCancel;
    long currBookId;
    Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        init();
        setTextViewsData();
        btnBookActivityUpdateListener();
        btnBookActivityCancelListener();
    }

    public void init() {
        textViewBookActivityBookName = findViewById(R.id.textViewBookActivityBookName);
        textViewBookActivityBookAuthor = findViewById(R.id.textViewBookActivityBookAuthor);

        editTextBookActivityNewBookName = findViewById(R.id.editTextBookActivityNewBookName);
        editTextBookActivityNewBookAuthor = findViewById(R.id.editTextBookActivityNewBookAuthor);

        btnBookActivityUpdate = findViewById(R.id.btnBookActivityUpdate);
        btnBookActivityCancel = findViewById(R.id.btnBookActivityCancel);
    }

    public void btnBookActivityUpdateListener() {
        btnBookActivityUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(editTextBookActivityNewBookName.getText().toString()) &&
                        !TextUtils.isEmpty(editTextBookActivityNewBookAuthor.getText().toString())) {

                    String newBookName = editTextBookActivityNewBookName.getText().toString();
                    String newBookAuthor = editTextBookActivityNewBookAuthor.getText().toString();
                    DataSource dataSource = new DataSource(context);
                    dataSource.updateBook(currBookId, newBookName, newBookAuthor);

//                    MainActivity mainActivity = new MainActivity();
//                    mainActivity.setListViewData();

                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);

                }

            }
        });
    }

    public void btnBookActivityCancelListener(){
        btnBookActivityCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);

                startActivity(intent);
            }
        });
    }

    public void setTextViewsData() {
        Bundle bundle = getIntent().getExtras();
        String currBookName = bundle.getString("keyBookName");
        String currBookAuthor = bundle.getString("keyBookAuthor");
        currBookId = bundle.getLong("keyBookId");

        textViewBookActivityBookName.setText(currBookName);
        textViewBookActivityBookAuthor.setText(currBookAuthor);

    }


}
