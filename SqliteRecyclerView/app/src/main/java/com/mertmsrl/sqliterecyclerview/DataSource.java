package com.mertmsrl.sqliterecyclerview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DataSource {
    SQLiteDatabase sqLiteDatabase;
    SqliteHelper sqliteHelper;
    Context context;

    public DataSource(Context context) {
        this.context = context;
        sqliteHelper = new SqliteHelper(context, "books", null, 1);
    }

    public void openConn(){
        sqLiteDatabase = sqliteHelper.getWritableDatabase();
    }

    public void closeConn(){
        sqliteHelper.close();
    }

    public void addBookToDb(String bookName, String bookAuthor){
        ContentValues contentValues = new ContentValues();
        openConn();
        contentValues.put("bookName", bookName);
        contentValues.put("bookAuthor",bookAuthor);

        sqLiteDatabase.insert("books", null, contentValues);
        closeConn();
    }

    public ArrayList<Books> getAllData(){
        openConn();
        ArrayList<Books> booksArrayList = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query("books", null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            int id = cursor.getInt(0);
            String bookName = cursor.getString(1);
            String bookAuthor = cursor.getString(2);
            booksArrayList.add(new Books(id, bookName, bookAuthor));

            cursor.moveToNext();
        }
        closeConn();
        return booksArrayList;
    }
}
