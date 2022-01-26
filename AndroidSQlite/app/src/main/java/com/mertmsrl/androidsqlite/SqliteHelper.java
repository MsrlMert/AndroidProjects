package com.mertmsrl.androidsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqliteHelper extends SQLiteOpenHelper {


    final private String TABLE_NAME = "books";
    final private String ID = "id";
    final private String BOOK_NAME = "bookName";
    final private String AUTHOR = "bookAuthor";


    public SqliteHelper(@Nullable Context context) {
        super(context, "booksDb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = " CREATE TABLE " + TABLE_NAME + "(id integer primary key autoincrement, "+
                                        BOOK_NAME+" text not null ,"+
                                        AUTHOR+" text not null)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
