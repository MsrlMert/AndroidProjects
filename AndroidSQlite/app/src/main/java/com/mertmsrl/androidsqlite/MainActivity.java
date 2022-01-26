package com.mertmsrl.androidsqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editTextBookName, editTextBookAuthor, editTextNumber;
    Button btnAddBook, btnDeleteBook;
    ListView listViewBooks;
    List<Books> booksList;
    DataSource dataSource;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setListViewData();
        btnAddBookListener();
        btnDeleteBookListener();
        listViewBooksListener();
    }

    public void init() {
        editTextBookName = findViewById(R.id.editTextBookName);
        editTextBookAuthor = findViewById(R.id.editTextBookAuthor);
        editTextNumber = findViewById(R.id.editTextNumber);

        btnAddBook = findViewById(R.id.btnAddBook);
        btnDeleteBook = findViewById(R.id.btnDeleteBook);

        listViewBooks = findViewById(R.id.listViewBooks);

        booksList = new ArrayList<>();

        dataSource = new DataSource(context);
    }

    public void btnAddBookListener() {
        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(editTextBookName.getText().toString()) && !TextUtils.isEmpty(editTextBookAuthor.getText().toString())) {
                    dataSource.openConn();
                    String bookName = editTextBookName.getText().toString();
                    String bookAuthor = editTextBookAuthor.getText().toString();


                    long rowCount = dataSource.findRowCount();

                    Books book = new Books(rowCount, bookName, bookAuthor);

                    dataSource.addBookToDb(book);

                    editTextBookAuthor.setText("");
                    editTextBookName.setText("");

                    setListViewData();
                }
            }
        });
    }

    public void btnDeleteBookListener() {

        btnDeleteBook.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + editTextNumber.getText().toString()));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show();

                }
                return false;
            }
        });
    }

    public void setListViewData() {
        DataSource dataSource = new DataSource(context);
        booksList = dataSource.getAllData();
        CustomListViewAdapter customListViewAdapter = new CustomListViewAdapter(booksList, context);

        listViewBooks.setAdapter(customListViewAdapter);
    }

    public void listViewBooksListener() {
        listViewBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Books book = (Books) parent.getItemAtPosition(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                AlertDialog alertDialog = builder.create();

                LayoutInflater inflater = LayoutInflater.from(context);
                View viewAlert = inflater.inflate(R.layout.custom_delete_alert, null);

                Button btnAlertDeleteYes = viewAlert.findViewById(R.id.btnAlertDeleteYes);
                Button btnAlertDeleteNo = viewAlert.findViewById(R.id.btnAlertDeleteNo);
                Button btnAlertUpdate = viewAlert.findViewById(R.id.btnAlertUpdate);

                alertDialog.setView(viewAlert);
                alertDialog.show();

                btnAlertDeleteYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dataSource.deleteBook(book);
                        setListViewData();
                        alertDialog.cancel();
                    }
                });

                btnAlertDeleteNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });

                btnAlertUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, BookInfo.class);
                        String bookName = book.getBookName();
                        String bookAuthor = book.getBookAuthor();
                        long bookId = book.getBookId();

                        Bundle bundle = new Bundle();
                        bundle.putString("keyBookName", bookName);
                        bundle.putString("keyBookAuthor", bookAuthor);
                        bundle.putLong("keyBookId", bookId);

                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                });

            }
        });
    }


}