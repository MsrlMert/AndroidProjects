package com.mertmsrl.androidsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DataSource {
    SQLiteDatabase sqLiteDatabase;
    SqliteHelper sqliteHelper;

    public DataSource(Context context) {
        sqliteHelper = new SqliteHelper(context);
    }

    public void openConn() {
        sqLiteDatabase = sqliteHelper.getWritableDatabase();

    }

    public void closeConn() {
        sqliteHelper.close();
    }


    public void addBookToDb(Books book) {
        // android in  sqlite için kendi yöntemi kullanıldı.

        openConn();
        ContentValues contentValues = new ContentValues();

        contentValues.put("bookName", book.getBookName());
        contentValues.put("bookAuthor", book.getBookAuthor());

        sqLiteDatabase.insert("books", null, contentValues);
        closeConn();

    }

    public List<Books> getAllData() {
        openConn();
        List<Books> books = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("books", null, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String bookName = cursor.getString(1);
            String bookAuthor = cursor.getString(2);
            books.add(new Books(id, bookName, bookAuthor));
            cursor.moveToNext();
        }

        closeConn();
        return books;
    }

    public void deleteBook(Books book) {
        openConn();
        long id = book.getBookId();

        sqLiteDatabase.delete("books", "id=" + id, null);
        closeConn();
    }

    public void updateBook(long bookId, String newBookName, String newBookAuthor){
        openConn();

        ContentValues contentValues = new ContentValues();
        contentValues.put("bookName",newBookName);
        contentValues.put("bookAuthor",newBookAuthor);

        sqLiteDatabase.update("books",contentValues,"id="+ bookId,null);

    }

    public long findRowCount() {
        openConn();
        long count = DatabaseUtils.queryNumEntries(sqLiteDatabase, "books");
        sqLiteDatabase.close();
        closeConn();
        return count;
    }
}
